package com.jlfex.hermes.main;

import java.awt.image.BufferedImage;
import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.mail.EmailService;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.utils.MailUtuils;
import com.jlfex.hermes.main.freemark.ModelLoader;
import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserProperties.Auth;
import com.jlfex.hermes.model.UserProperties.IdType;
import com.jlfex.hermes.model.hermes.BranchBank;
import com.jlfex.hermes.service.AreaService;
import com.jlfex.hermes.service.BankService;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.UserLogService;
import com.jlfex.hermes.service.UserService;

@Controller
@RequestMapping("/userIndex")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private Producer captchaProducer;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private BankService bankService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private UserLogService userLogService;
	@Autowired
	private UserInfoService userInfoService;

	private static final String COMPANY_NAME = "app.company.name";
	private static final String WEBSITE = "app.website";
	private static final String COMPANY_PNAME = "app.operation.name";
	private static final String COMPANY_NICK_NAME = "app.operation.nickname";

	/**
	 * 登录界面
	 * 
	 * @return
	 */
	@RequestMapping("skipSignIn")
	public String skipSignIn(Model model) {
		model.addAttribute("loginPicture", contentService.findOneByCode(HermesConstants.INDEX_LOGIN));
		model.addAttribute("token", App.current().getToken());
		return "user/sign-in";
	}

	/**
	 * 注册界面
	 * 
	 * @return
	 */
	@RequestMapping("regNow")
	public String regNow(Model model) {
		model.addAttribute("registerPicture", contentService.findOneByCode(HermesConstants.INDEX_REGISTER));
		model.addAttribute("token", App.current().getToken());
		return "user/signup";
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/signUp")
	public String signUp(User user, Model model, String token, HttpServletRequest request) {
		String commonMessage = "";
		if (Strings.empty(token)) {
			Logger.info("注册失败,令牌不能为空!");
			model.addAttribute("errMsg", "注册失败,令牌不能为空!");
			return "user/signup-success";
		}
		if (user != null) {
			user.propertyTrim();
		}
		String verificationCode = (String) request.getSession().getAttribute("capText");
		try {
			checkMatterData(verificationCode, user);
		} catch (Exception e) {
			Logger.info(e.getMessage());
			model.addAttribute("errMsg", e.getMessage());
			return "user/signup";
		}
		try {
			userService.signUp(user);
		} catch (Exception e) {
			commonMessage = "注册失败,数据保存异常";
			Logger.error(commonMessage, e);
			model.addAttribute("errMsg", commonMessage);
			return "user/signup";
		}
		model.addAttribute("email", user.getEmail());
		try {
			String generateMail = ModelLoader.process("mail_active.ftl", userService.getActiveMailModel(user, request));
			emailService.sendEmail(user.getEmail(), "注册用户激活", generateMail);
		} catch (Exception e) {
			commonMessage = "激活邮件发送失败,请重新发送!";
			Logger.error(commonMessage, e);
			model.addAttribute("errMsg", commonMessage);
			return "user/signup-success";
		}
		return "user/signup-success";
	}

	/**
	 * 重要 信息 校验
	 * 
	 * @param verificationCode
	 * @param user
	 * @throws Exception
	 */
	private void checkMatterData(String verificationCode, User user) throws Exception {
		if (!verificationCode.equalsIgnoreCase(user.getVerificationCode())) {
			String var = "验证码不匹配";
			Logger.info(var + ": 系统：" + verificationCode + "___用户:" + user.getVerificationCode());
			throw new Exception(var);
		}
		boolean flagEmail = userService.isExistentEmail(user.getEmail());
		if (flagEmail) {
			throw new Exception("邮箱已使用");
		}
		boolean flagPhone = userService.checkPhone(user.getCellphone());
		if (!flagPhone) {
			throw new Exception("手机已使用");
		}
		User existUser = userService.getUserByAccount(user.getAccount());
		if (existUser != null) {
			throw new Exception("昵称已使用");
		}
	}

	/**
	 * 查看昵称是否被占用
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping("checkAccount")
	@ResponseBody
	public JSONObject checkAccount(String account) {
		User user = userService.getUserByAccount(account);
		JSONObject jsonObj = new JSONObject();
		if (user != null) {
			jsonObj.put("account", false);
		} else {
			jsonObj.put("account", true);
		}

		return jsonObj;
	}

	/**
	 * 检测手机号是否可以使用
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("checkCellphone")
	@ResponseBody
	public JSONObject checkCellphone(String cellphone) {
		JSONObject jsonObj = new JSONObject();
		if (userService.checkPhone(cellphone)) {
			jsonObj.put("cellphone", true);
		} else {
			jsonObj.put("cellphone", false);
		}

		return jsonObj;
	}

	/**
	 * signIn 登录
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/signIn")
	@ResponseBody
	public Result<?> signIn(User user) {
		return userService.signIn(user);
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@RequestMapping("/signOut")
	public String signOut() {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		try {
			userLogService.saveUserLog(user, UserLog.LogType.SIGNOUT);
			App.current().setUser(null);

		} catch (Exception e) {
			Logger.error("用户：" + curUser.getAccount() + "登出失败");
		}

		return "redirect:/index";
	}

	@RequestMapping("resendMail")
	public String resendMail(String email, Model model) throws Exception {
		boolean flag = MailUtuils.isValidMail(email);
		if (!flag) {
			throw new Exception();
		}
		model.addAttribute("email", email);
		return "user/resendMail";
	}

	/**
	 * 重新发送激活邮件
	 * 
	 * @param email
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @since 2014年12月30日20:24:59
	 */
	@RequestMapping("sendActiveMailAgain")
	public String sendActiveMailAgain(String email, Model model, HttpServletRequest request) throws Exception {
		String commonMessage = "";
		boolean flag = MailUtuils.isValidMail(email);
		if (!flag) {
			throw new Exception();
		}
		User user = userService.loadByEmail(email);
		try {
			String generateMail = ModelLoader.process("mail_active.ftl", userService.getActiveMailModel(user, request));
			emailService.sendEmail(user.getEmail(), "注册用户激活", generateMail);
		} catch (Exception e) {
			commonMessage = "激活邮件发送失败,请重新发送!";
			Logger.error(commonMessage, e);
			model.addAttribute("errMsg", commonMessage);
		}
		model.addAttribute("email", email);
		return "user/signup-success";
	}

	/**
	 * 激活邮件
	 * 
	 * @param userId
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/activeMail")
	public String activeMail(@RequestParam("uuId") String userId, @RequestParam("validateCode") String validateCode, HttpServletRequest request, Model model) {
		Result<?> result = userService.activeMail(userId, validateCode);
		if (result.getType().equals(Type.SUCCESS)) {
			model.addAttribute("message", result.getFirstMessage());
			model.addAttribute("userId", userId);
			model.addAttribute("cellphone", userService.loadById(userId).getCellphone());
			return "user/emailActive";
		} else if (result.getType().equals(Type.WARNING)) {
			model.addAttribute("message", App.message("message.sign.email.expire", ""));
			model.addAttribute("email", result.getData());
			return "user/emailExpire";
		} else {
			model.addAttribute("message", result.getFirstMessage());
			return "user/emailActive";
		}

	}

	/**
	 * 手机认证页面
	 * 
	 * @return
	 */
	@RequestMapping("/authCellPhone")
	public String authCellPhone(@RequestParam("email") String email, Model model) {
		User user = userService.loadByEmail(email);
		UserProperties userPro = userService.loadPropertiesByUserId(user.getId());
		Result<?> result = userService.signIn(user);
		model.addAttribute("message", result.getFirstMessage());
		model.addAttribute("userId", user.getId());
		model.addAttribute("cellphone", userService.loadById(user.getId()).getCellphone());
		model.addAttribute("email", email);
		if (!userPro.getAuthCellphone().equals(Auth.PASS) && App.config("auth.cellphone.switch").trim().equals(HermesConstants.SWITCH_FLAG_ZERO)) {
			return "user/authCellPhone";
		} else if (!userPro.getAuthName().equals(Auth.PASS) && App.config("auth.realname.switch").trim().equals(HermesConstants.SWITCH_FLAG_ZERO)) {
			return "redirect:/userIndex/authName";
		} else if (!userPro.getAuthBankcard().equals(Auth.PASS)) {
			return "redirect:/userIndex/authBankCard";
		} else {
			return "redirect:/invest/index";
		}
	}

	/**
	 * 实名认证页面
	 * 
	 * @return
	 */
	@RequestMapping("/authName")
	public String authName(@RequestParam("email") String email, Model model) {
		User user = userService.loadByEmail(email);
		// 证件类型
		Map<Object, String> idTypeMap = Dicts.elements(IdType.class);
		UserProperties userPro = userService.loadPropertiesByUserId(user.getId());
		model.addAttribute("idTypeMap", idTypeMap);
		model.addAttribute("userId", user.getId());
		model.addAttribute("email", email);
		model.addAttribute("userProperties", userService.loadPropertiesByUserId(user.getId()));
		if (!userPro.getAuthName().equals(Auth.PASS) && App.config("auth.realname.switch").trim().equals(HermesConstants.SWITCH_FLAG_ZERO)) {
			return "user/realNameApprove";
		} else {
			return "redirect:/userIndex/authBankCard";
		}

	}

	/**
	 * 银行卡认证页面
	 * 
	 * @return
	 */
	@RequestMapping("/authBankCard")
	public String authBankcard(@RequestParam("email") String email, Model model) {
		User user = userService.loadByEmail(email);
		UserProperties userPro = userService.loadPropertiesByUserId(user.getId());
		model.addAttribute("userId", user.getId());
		model.addAttribute("banks", bankService.findAll());// 查询所有银行信息
		model.addAttribute("area", JSON.toJSONString(areaService.getAllChildren(null)));
		model.addAttribute("realName", userService.loadPropertiesByUserId(user.getId()).getRealName());// 获取持卡人的真实姓名
		model.addAttribute("userProperties", userService.loadPropertiesByUserId(user.getId()));// 获取持卡人的真实姓名
		if (StringUtils.isNotEmpty(userPro.getAuthBankcard()) && !userPro.getAuthBankcard().equals(Auth.PASS)) {
			return "user/bindBank";
		} else if (StringUtils.isEmpty(userPro.getAuthBankcard())) {
			return "user/bindBank";
		} else {
			return "forward:/invest/index";
		}

	}

	/**
	 * 查询支行数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findBranchBankByBankAndCity")
	@ResponseBody
	public List<BranchBank> findBranchBankByBankAndCity(@RequestParam("bankId") String bankId, @RequestParam("cityId") String cityId) {
		List<BranchBank> list = null;
		Bank bank = bankService.findOne(bankId);
		Area area = areaService.loadById(cityId);
		try {
			list = bankService.findByBranchBankAndCity(bank.getName(), area.getName() + HermesConstants.PROVINCE_SUFFIX);
		} catch (Exception e) {
			Logger.error("获取支行信息失败!", e);
		}
		return list;
	}

	/**
	 * 检测手机号是否已被绑定
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("checkPhone")
	@ResponseBody
	public Result<String> checkPhone(@RequestParam("phone") String phone, Model model) {
		Result<String> result = new Result<String>();
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		try {
			boolean flagPhone = userService.checkPhone(phone);
			if (!flagPhone) {
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				result.addMessage(App.message("当前手机号已被绑定"));
			}
		} catch (Exception e) {
			Logger.info(e.getMessage());
			model.addAttribute("errMsg", e.getMessage());
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(App.message("校验失败"));
		}
		return result;
	}

	/**
	 * 找回密码
	 * 
	 * @return
	 */
	@RequestMapping("retrivePwd")
	public String retrivePwd(Model model) {
		model.addAttribute("token", App.current().getToken());
		return "user/retrievePwdStep1";
	}

	/**
	 * 邮箱是否存在
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("isExistentEmail")
	@ResponseBody
	public JSONObject isExistentEmail(String email) {
		JSONObject jsonObj = new JSONObject();
		if (userService.isExistentEmail(email)) {
			jsonObj.put("email", false);
		} else {
			jsonObj.put("email", true);
		}
		return jsonObj;
	}

	/**
	 * 验证码 输入是否正确
	 * 
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping("checkVerifiedCode")
	@ResponseBody
	public JSONObject checkVerifiedCode(String captcha, HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		String verificationCode = (String) request.getSession().getAttribute("capText");
		if (!verificationCode.equalsIgnoreCase(captcha)) {
			jsonObj.put("captcha", false);
		} else {
			jsonObj.put("captcha", true);
		}
		return jsonObj;
	}

	/**
	 * 邮箱是否被激活
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("isActiveEmail")
	@ResponseBody
	public JSONObject isActiveEmail(String email) {
		JSONObject jsonObj = new JSONObject();
		if (userService.checkEmail(email)) {
			jsonObj.put("email", true);
		} else {
			jsonObj.put("email", false);
		}
		return jsonObj;
	}

	/**
	 * 发送重置密码的邮件
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendResetPwdEmail")
	public String sendResetPwdEmail(String email, String token, Model model, HttpServletRequest request) throws Exception {
		try {
			if (Strings.empty(token)) {
				throw new ServerException("发送重置密码的邮件: 令牌不能为空");
			}
			if (!MailUtuils.isValidMail(email)) {
				throw new Exception("无效的邮件地址");
			}
			String generateMail = ModelLoader.process("mail_pwdForget.ftl", userService.getResetPwdEmailModel(email, request));
			emailService.sendEmail(email, "密码重置", generateMail);
		} catch (Exception e) {
			String commonMessage = "激活邮件发送失败,请重新发送!";
			Logger.error(commonMessage, e);
			model.addAttribute("errMsg", commonMessage);
		}
		model.addAttribute("email", email);
		return "user/retrievePwdStep2";
	}

	/**
	 * 处理找回密码邮件
	 * 
	 * @param userId
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping("handleRetrive")
	public String handleRetrieveMail(@RequestParam("uuId") String userId, @RequestParam("validateCode") String validateCode, HttpServletRequest request, Model model) {
		Result<?> result = userService.handleRetrieveMail(userId, validateCode);
		if (result.getType().equals(Type.SUCCESS)) {
			model.addAttribute("uuid", userId);
			return "user/retrievePwdStep3";
		} else if (result.getType().equals(Type.WARNING)) {
			model.addAttribute("message", App.message("message.retrieve.email.expire", ""));
			return "user/emailExpire";
		} else {
			model.addAttribute("message", result.getFirstMessage());
			return "user/emailActive";
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("resetPwd")
	public String resetPwd(User user) {
		userService.retrievePwd(user.getId(), user.getSignPassword());
		return "user/retrievePwdStep4";
	}

	/**
	 * 生成验证码图片
	 * 
	 * @param request
	 * @param response
	 * @time :2014年12月29日10:44:24
	 */
	@RequestMapping("generatorCode")
	@ResponseBody
	public void getValidatePic(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");
			String capText = captchaProducer.createText();
			Logger.debug(request.getSession().getId() + ":" + capText);
			request.getSession().setAttribute("capText", capText);
			BufferedImage bi = captchaProducer.createImage(capText);
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			try {
				out.flush();
			} finally {
				out.close();
			}
		} catch (Exception e) {
			Logger.error("生产验证码异常：", e);
		}
	}

	/**
	 * 注册协议
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/registerProtocol")
	public String registerProtocol(Model model) {
		model.addAttribute("operator", App.config(COMPANY_NAME)); // 平台运营方
		model.addAttribute("platformName", App.config(COMPANY_PNAME));
		model.addAttribute("platformNetAddr", App.config(WEBSITE));
		model.addAttribute("platformNickName", App.config(COMPANY_NICK_NAME));
		return "agree/registerProtocol";
	}

	/**
	 * 咨询服务协议
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/counselProtocol")
	public String counselProtocol(Model model) {
		model.addAttribute("platformName", App.config(COMPANY_PNAME));
		return "agree/counselServeProtocol";
	}

}

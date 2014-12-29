package com.jlfex.hermes.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.UserBasic;

@Controller
@RequestMapping("/userIndex")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private Producer captchaProducer;

	/**
	 * 登录界面
	 * @return
	 */
	@RequestMapping("skipSignIn")
	public String skipSignIn() {
		return "user/sign-in";
	}

	/**
	 * 注册界面
	 * 
	 * @return
	 */
	@RequestMapping("regNow")
	public String regNow() {
		return "user/signup";
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/signUp")
	public String signUp(User user, Model model, HttpServletRequest request) {
		Result result = new Result();
		String verificationCode = (String) request.getSession().getAttribute("capText");  
		if(!verificationCode.equalsIgnoreCase(user.getVerificationCode())){
			Logger.info("验证码不匹配:"+verificationCode+"___"+user.getVerificationCode());
			model.addAttribute("errVerifiedCode", "验证码有误");
			return "user/signup";
		}
		boolean flagEmail = userService.isExistentEmail(user.getEmail());
		if (!flagEmail) {
			userService.signUp(user);
			// user=userService.loadByEmail(user.getEmail());
			model.addAttribute("user", user);
		}
		return "user/signup-comp";
	}

	/**
	 * 完善注册信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("supplement")
	public String supplement(UserBasic userBasic, Model model, HttpServletRequest req) {
		User user_acc = userService.getUserByAccount(userBasic.getAccount());
		boolean acc = false;
		if (user_acc == null) {
			acc = true;
		}
		boolean cellPhone = userService.checkPhone(userBasic.getCellphone());
		if (acc && cellPhone) {
			userService.signSupplement(userBasic, req);
		}
		model.addAttribute("email", userBasic.getEmail());
		return "user/signup-success";
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
	 * 登录
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/signIn")
	@ResponseBody
	public Result signIn(User user) {
		Result result = userService.signIn(user);
		return result;
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@RequestMapping("/signOut")
	public String signOut() {
		App.current().setUser(null);
		return "redirect:/index";
	}

	@RequestMapping("resendMail")
	public String resendMail(String email, Model model) {
		model.addAttribute("email", email);
		return "user/resendMail";
	}

	/**
	 * 重新发送激活邮件
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("sendActiveMailAgain")
	public String sendActiveMailAgain(String email, Model model, HttpServletRequest req) {
		User user = userService.loadByEmail(email);
		userService.sendActiveMail(user, req);
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
		Result result = userService.activeMail(userId, validateCode);
		if (result.getType().equals(Type.SUCCESS)) {
			model.addAttribute("message", result.getFirstMessage());
			return "user/emailActive";
		} else if (result.getType().equals(Type.WARNING)) {
			model.addAttribute("message", App.message("message.sign.email.expire", null));
			model.addAttribute("email", result.getData());
			return "user/emailExpire";
		} else {
			model.addAttribute("message", result.getFirstMessage());
			return "user/emailActive";
		}

	}

	/**
	 * 找回密码
	 * 
	 * @return
	 */
	@RequestMapping("retrivePwd")
	public String retrivePwd() {
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
		JSONObject jsonObj=new JSONObject();
		if (userService.isExistentEmail(email)) {
			jsonObj.put("email", false);
		} else {
			jsonObj.put("email", true);
		}
		return jsonObj;
	}
	/**
	 * 验证码 输入是否正确
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping("checkVerifiedCode")
	@ResponseBody
	public JSONObject checkVerifiedCode(String captcha,HttpServletRequest request) {
		JSONObject jsonObj=new JSONObject();
		String verificationCode = (String) request.getSession().getAttribute("capText");  
		if(!verificationCode.equalsIgnoreCase(captcha)){
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
	 */
	@RequestMapping("sendResetPwdEmail")
	public String sendResetPwdEmail(String email, Model model, HttpServletRequest req) {
		userService.sendResetPwdEmail(email, req);
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
		Result result = userService.handleRetrieveMail(userId, validateCode);
		if (result.getType().equals(Type.SUCCESS)) {
			model.addAttribute("uuid", userId);
			return "user/retrievePwdStep3";
		} else if (result.getType().equals(Type.WARNING)) {
			model.addAttribute("message", App.message("message.retrieve.email.expire", null));
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
	 * @param request
	 * @param response
	 * @time :2014年12月29日10:44:24
	 */
	@RequestMapping("generatorCode")
	@ResponseBody
	public void getValidatePic(HttpServletRequest request, HttpServletResponse response)  {
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
			Logger.error("生产验证码异常：",e);
		}
	}
	
}

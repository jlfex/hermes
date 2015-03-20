package com.jlfex.hermes.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserProperties.IdType;
import com.jlfex.hermes.service.AreaService;
import com.jlfex.hermes.service.AuthService;
import com.jlfex.hermes.service.BankService;
import com.jlfex.hermes.service.UserService;

/**
 * 认证中心
 * 
 * 
 * @author Aether
 * @version 1.0, 2014-1-3
 * @since 1.0
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;
	@Autowired
	private BankService bankService;
	@Autowired
	private AreaService areaService;

	/**
	 * 用户认证中心
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("approve")
	public String approve(Model model) {
		AppUser curUser = App.current().getUser();
		if (curUser == null) {
			return "user/signin";
		}
		model.addAttribute("email", curUser.getAccount());
		// 用户属性信息
		UserProperties userPro = authService.findByUserId(curUser.getId());
		model.addAttribute("userPro", userPro);
		// 证件类型
		Map<Object, String> idTypeMap = Dicts.elements(IdType.class);
		model.addAttribute("idTypeMap", idTypeMap);
		model.addAttribute("phoneSwitch", authService.isAuth("auth.cellphone.switch"));
		model.addAttribute("idSwitch", authService.isAuth("auth.realname.switch"));
		return "account/approve";
	}

	@RequestMapping("phone")
	public String phone() {
		return "account/approve-phone";
	}

	@RequestMapping("identity")
	public String identity(Model model) {
		// 证件类型
		Map<Object, String> idTypeMap = Dicts.elements(IdType.class);
		model.addAttribute("idTypeMap", idTypeMap);
		return "account/approve-identity";
	}

	/**
	 * 实名认证
	 * 
	 * @param cellphone
	 * @return
	 */
	@RequestMapping("realNameApprove/{userId}")
	public String realNameApprove(@PathVariable("userId") String userId, Model model) {
		// 证件类型
		Map<Object, String> idTypeMap = Dicts.elements(IdType.class);
		model.addAttribute("idTypeMap", idTypeMap);
		model.addAttribute("userId", userId);
		return "user/realNameApprove";
	}

	/**
	 * 获取手机验证码
	 * 
	 * @param cellphone
	 * @return
	 */
	@RequestMapping("sendPhoneCode/{cellphone}")
	@ResponseBody
	public Result sendPhoneCode(@PathVariable("cellphone") String cellphone) {
		AppUser curUser = App.current().getUser();
		return authService.sendAuthCodeByPhone(curUser.getId(), cellphone);
	}

	/**
	 * 获取手机验证码
	 * 
	 * @param cellphone
	 *            userId
	 * @return
	 */
	@RequestMapping("sendphoneCode/{userId}")
	@ResponseBody
	public Result sendPhoneCode(String cellphone, @PathVariable("userId") String userId) {
		return authService.sendAuthCodeByPhone(userId, cellphone);
	}

	/**
	 * 认证手机号
	 * 
	 * @param cellphone
	 * @param validateCode
	 * @return
	 */
	@RequestMapping("authCellphone/{userId}")
	@ResponseBody
	public Result authCellphone(String cellphone, String validateCode, @PathVariable("userId") String userId) {
		// AppUser curUser = App.current().getUser();
		return authService.authPhone(userId, cellphone, validateCode);
	}

	/**
	 * 实名认证
	 * 
	 * @param realName
	 * @param idType
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("authId/{userId}")
	@ResponseBody
	public Result authId(String realName, String idType, String idNumber, @PathVariable("userId") String userId) {
		// AppUser curUser = App.current().getUser();
		return authService.authIdentify(userId, realName, idType, idNumber);
	}

	/**
	 * 转向绑定银行卡页面
	 * 
	 * @return
	 */
	@RequestMapping("bindBank/{userId}")
	public String bindBank(@PathVariable("userId") String userId, Model model) {
		model.addAttribute("userId", userId);
		model.addAttribute("banks", bankService.findAll());// 查询所有银行信息
		model.addAttribute("area", JSON.toJSONString(areaService.getAllChildren(null)));
		model.addAttribute("realName", userService.loadPropertiesByUserId(userId).getRealName());// 获取持卡人的真实姓名
		return "user/bindBank";
	}

	/**
	 * 根据地区父级查找子级
	 * 
	 */
	@RequestMapping("/findAreaByParentId")
	@ResponseBody
	public List<Area> findAreaByParent(@RequestParam(required = true) String parentId) {
		return areaService.loadByParentId(parentId);
	}

	/**
	 * 处理绑定银行卡业务
	 * 
	 * @return
	 */
	@RequestMapping("handerBindBank/{userId}")
	@ResponseBody
	public Result handerBindBank(@PathVariable("userId") String userId, HttpServletRequest request) {
		String bankId = request.getParameter("bankId");
		String cityId = request.getParameter("cityId");
		String deposit = request.getParameter("deposit");
		String account = request.getParameter("account");
		String isdefault = request.getParameter("isdefault");
		// AppUser curUser = App.current().getUser();
		return authService.bindBank(userId, bankId, cityId, deposit, account, isdefault);
	}
}

package com.jlfex.hermes.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserProperties.IdType;
import com.jlfex.hermes.service.AuthService;

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
		model.addAttribute("phoneSwitch",authService.isAuth("auth.cellphone.switch"));
		model.addAttribute("idSwitch",authService.isAuth("auth.realname.switch"));
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
	 * 认证手机号
	 * 
	 * @param cellphone
	 * @param validateCode
	 * @return
	 */
	@RequestMapping("authCellphone")
	@ResponseBody
	public Result authCellphone(String cellphone, String validateCode) {
		AppUser curUser = App.current().getUser();
		return authService.authPhone(curUser.getId(), cellphone, validateCode);
	}

	/**
	 * 实名认证
	 * 
	 * @param realName
	 * @param idType
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("authId")
	@ResponseBody
	public Result authId(String realName, String idType, String idNumber) {
		AppUser curUser = App.current().getUser();
		return authService.authIdentify(curUser.getId(), realName, idType, idNumber);
	}
}

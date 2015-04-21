package com.jlfex.hermes.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 应用控制器
 */
@Controller
@RequestMapping("/app")
public class AppController {

	/**
	 * 跳转登录
	 * 
	 * @return
	 */
	@RequestMapping("/sign")
	public String sign() {
		return "app/sign";
	}
	
	/**
	 * 登录
	 * 
	 * @return
	 */
	@RequestMapping("/signIn")
	public String signIn() {
		return "app/sign-in";
	}
}

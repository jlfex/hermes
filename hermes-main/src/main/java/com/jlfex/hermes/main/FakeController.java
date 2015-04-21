package com.jlfex.hermes.main;

import java.util.Arrays;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jlfex.hermes.common.Logger;

/**
 * 模拟控制器
 */
@Controller
@RequestMapping("/fake")
public class FakeController {

	/**
	 * 支付
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/payment")
	public String payment(HttpServletRequest request, Model model) {
		for (Entry<String, String[]> entry: request.getParameterMap().entrySet()) {
			Logger.debug(entry.getKey() + ": " + Arrays.asList(entry.getValue()));
		}
		
		model.addAttribute("amount", request.getParameter("amount"));
		model.addAttribute("sequence", request.getParameter("sequence"));
		model.addAttribute("returnUrl", request.getParameter("return_url"));
		model.addAttribute("notifyUrl", request.getParameter("notify_url"));
		return "fake/payment";
	}
}

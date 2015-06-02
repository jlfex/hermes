package com.jlfex.hermes.console.systemlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.service.UserLogService;
import com.jlfex.hermes.service.pojo.UserLogVo;

/**
 * 用户日志控制器
 * 
 * @author jswu
 *
 */
@Controller
@RequestMapping("/userLog")
public class UserLogController {
	@Autowired
	private UserLogService userLogService;

	/**
	 * 日志结果列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		Object type =	Dicts.elements(UserLog.LogType.class).entrySet();
		model.addAttribute("type", type);
		return "systemlog/userLogIndex";
	}

	/**
	 * 用户日志数据
	 * 
	 * @param userLogVo
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/userLogData")
	public String userLogData(UserLogVo userLogVo, String page, Model model) {
		try {
			Page<UserLog> userLogList = userLogService.queryByCondition(userLogVo, page, HermesConstants.DEFAULT_PAGE_SIZE);
			model.addAttribute("userLogList", userLogList);
		} catch (Exception e) {
			Logger.error("用户日志列表查询异常:", e);
			model.addAttribute("userLogList", null);
		}
		return "systemlog/userLogData";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		try {
			model.addAttribute("userLog", userLogService.findOne(id));
		} catch (Exception e) {
			Logger.error("用户日志明细异常：", e);
		}
		return "systemlog/userLogDetail";
	}
}

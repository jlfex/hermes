package com.jlfex.hermes.console.systemlog;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.service.LoanLogService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.LoanLogVo;

/**
 * 投标日志控制器
 * 
 * @author jswu
 *
 */
@Controller
@RequestMapping("/loanLog")
public class LoanLogController {
	@Autowired
	private LoanLogService loanLogService;
	@Autowired
	private UserService userService;

	/**
	 * 日志结果列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		Set<Entry<Object, String>> type = Dicts.elements(LoanLog.Type.class).entrySet();
		
		Set<Entry<Object, String>> leaveEntries = new HashSet<Map.Entry<Object,String>>();
		
		for (Entry<Object, String> entry : type) {
			String key = entry.getKey().toString();
			if(key.equals(LoanLog.Type.ADVANCE) || key.equals(LoanLog.Type.MANUAL_FAILURE)) {
				leaveEntries.add(entry);
			}
		}
		type.removeAll(leaveEntries);
		model.addAttribute("type", type);
		return "systemlog/loanLogIndex";
	}

	/**
	 * 投标日志数据
	 * 
	 * @param loanLogVo
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/loanLogData")
	public String userLogData(LoanLogVo loanLogVo, String page, Model model) {
		try {
			Page<LoanLog> loanLogList = loanLogService.queryByCondition(loanLogVo, page, HermesConstants.DEFAULT_PAGE_SIZE);
			for (LoanLog loanLog : loanLogList.getContent()) {
				User user = userService.loadById(loanLog.getUser());
				loanLog.setUserName(user.getAccount());
			}
			model.addAttribute("loanLogList", loanLogList);
		} catch (Exception e) {
			Logger.error("投标日志列表查询异常:", e);
			model.addAttribute("loanLogList", null);
		}
		return "systemlog/loanLogData";
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
			model.addAttribute("loanLog", loanLogService.findOne(id));
		} catch (Exception e) {
			Logger.error("投标日志明细异常：", e);
		}
		return "systemlog/loanLogDetail";
	}
}

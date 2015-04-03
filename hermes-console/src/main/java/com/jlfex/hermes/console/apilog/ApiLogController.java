package com.jlfex.hermes.console.apilog;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.service.CreditorService;
import com.jlfex.hermes.service.apiLog.ApiLogService;
import com.jlfex.hermes.service.pojo.yltx.ApiLogVo;

/**
 * 日志控制器
 * 
 * @author lishunfeng
 * @since 1.0
 */
@Controller
@RequestMapping("/apiLog")
public class ApiLogController {
	@Autowired
	private ApiLogService apiLogService;

	/**
	 * 日志结果列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("status", Dicts.elements(ApiLog.DealResult.class).entrySet());
		return "apilog/apiLogIndex";
	}
	/**
	 * 债权导入 加载列表
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/apiLogdata")
	public String logdata(ApiLogVo apiLogVo,String page, Model model) {
		try {
			String size = "10";
			Page<ApiLog> obj = apiLogService.queryByCondition(apiLogVo, page, size);
			model.addAttribute("apiLogList", obj);
		} catch (Exception e) {
			Logger.error("外围日志列表查询异常:", e);
			model.addAttribute("apiLogList", null);
		}
		return "apilog/apiLogData";
	}

	/**
	 * 日志明细 查询
	 * 
	 * @param page
	 * @param size
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/apiLogDetail/{id}")
	public String repayPlanDetail(@PathVariable("id") String id, Model model) {
		try {
			model.addAttribute("apiLog", apiLogService.findOne(id)); 
		} catch (Exception e) {
			Logger.error("外围日志明细异常：", e);
		}
		return "apilog/apiLogDetail";
	}

}

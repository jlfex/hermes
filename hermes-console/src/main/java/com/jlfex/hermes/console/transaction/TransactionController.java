package com.jlfex.hermes.console.transaction;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.service.TransactionService;

/**
 * 
 * @author jswu
 *
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	/**
	 * 索引
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("today", Calendars.date());
		return "transaction/index";
	}

	/**
	 * 获取数据表格
	 * 
	 * @param name
	 * @param beginDate
	 * @param endDate
	 * @param status
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/table")
	public String table(String email, String beginDate, String endDate, String remark, Integer page, Integer size, Model model) {
		try {
			Date sDate = DateUtils.parseDate(beginDate, "yyyy-MM-dd");
			Date eDate = DateUtils.addHours(DateUtils.parseDate(endDate, "yyyy-MM-dd"), 24) ;
			model.addAttribute("rechargeList", transactionService.findRechargeByEmailAndDateBetweenAndRemark(email, sDate, eDate, remark, page, size));
		} catch (Exception e) {
			Logger.error("资金明细：获取数据表格失败");
		}
		
		return "transaction/table";
	}
}

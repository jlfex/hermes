package com.jlfex.hermes.console;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.Withdraw;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.UserManageService;
import com.jlfex.hermes.service.WithdrawService;

/**
 * 提现控制器
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-20
 * @since 1.0
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController {

	/** 提现业务接口 */
	@Autowired
	private WithdrawService withdrawService;

	/** 用户个人信息接口 */
	@Autowired
	private UserInfoService userInfoService;
	/** 交易流水信息接口 */
	@Autowired
	private TransactionService transactionServiceImpl;
	/** 账户管理接口 */
	@Autowired
	private UserManageService userManageServiceImpl;

	private static final String CROP_USER_ID = "crop";
	/**
	 * 索引
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("status", Dicts.elements(Withdraw.Status.class).entrySet());
		model.addAttribute("today", Calendars.date());
		return "withdraw/index";
	}

	/**
	 * 数据表格
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
	public String table(String name, String beginDate, String endDate, String status, Integer page, Integer size, Model model) {
		model.addAttribute("withdraw", withdrawService.findByNameAndDateBetweenAndStatus(name, beginDate, endDate, status, page, size));
		return "withdraw/table";
	}

	/**
	 * 风险金账户
	 * 
	 */
	@RequestMapping("/riskAccount")
	public String riskAccount(Integer page, Integer size, Model model) {
		List<String> types = new ArrayList<String>();
		types.add(Transaction.Type.IN);
		types.add(Transaction.Type.OUT);
		BigDecimal riskIn = new BigDecimal(0);
		BigDecimal riskOut = new BigDecimal(0);
		try {
			UserAccount account = userManageServiceImpl.findByUserIdAndType(CROP_USER_ID, UserAccount.Type.RISK);
			List<Transaction> trans = transactionServiceImpl.findBySourceUserAccountAndTypeIn(CROP_USER_ID, types);
			if (trans != null && trans.size() > 0) {
				for (Transaction tran : trans) {
					if (Transaction.Type.IN.equals(tran.getType())) {
						riskIn = riskIn.add(tran.getAmount());
					} else {
						riskOut = riskOut.add(tran.getAmount());
					}
				}
			}
			model.addAttribute("riskIn", riskIn);
			model.addAttribute("riskOut", riskOut);
			model.addAttribute("riskAmount", account.getBalance());
		} catch (Exception e) {
			Logger.info("风险金账户查询异常：", e);
			return "redirect:/home";
		}
		return "withdraw/riskAccount";
	}

	@RequestMapping("/riskAccountData")
	public String riskAccountData(Integer page, Integer size, Model model) {
		List<String> types = new ArrayList<String>();
		types.add(Transaction.Type.IN);
		types.add(Transaction.Type.OUT);
		try {
			model.addAttribute("transaction", transactionServiceImpl.findByUserIdAndDateType(CROP_USER_ID, page, size, types));
		} catch (Exception e) {
			Logger.info("风险金账户查询异常：", e);
			return "redirect:/home";
		}
		return "withdraw/riskAccountData";
	}

	/**
	 * 明细
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		// 查询数据
		Withdraw withdraw = withdrawService.loadById(id);
		UserProperties prop = userInfoService.getProByUser(withdraw.getUser());

		// 渲染视图
		model.addAttribute("withdraw", withdraw);
		model.addAttribute("prop", prop);
		model.addAttribute("wait", Withdraw.Status.WAIT);
		model.addAttribute("success", Withdraw.Status.SUCCESS);
		model.addAttribute("failure", Withdraw.Status.FAILURE);
		return "withdraw/detail";
	}

	/**
	 * 处理
	 * 
	 * @param id
	 * @param status
	 * @param remark
	 * @param model
	 * @return
	 */
	@RequestMapping("/deal")
	public String deal(String id, String status, String remark, Model model) {
		withdrawService.deal(id, status, remark);
		return detail(id, model);
	}
}

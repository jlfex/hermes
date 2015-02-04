package com.jlfex.hermes.console;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Label;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAudit;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserContacter;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserImage.Type;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LabelService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.UserManageService;
import com.jlfex.hermes.service.pojo.LoanUserBasic;

@Controller
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private UserManageService userManageService;
	@Autowired
	private InvestService investService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private TransactionService transactionService;

	/**
	 * 借款信息查询页面
	 * 
	 * @returno
	 */
	@RequestMapping("/index")
	public String index() {

		return "loan/index";
	}

	/**
	 * 借款查询数据结果页面
	 * 
	 * @param loanNo
	 * @param cellphone
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/loandata")
	public String loandata(String loanNo, String cellphone, String page, String size, Model model) {
		model.addAttribute("loan", loanService.findByLoanNoAndCellphone(loanNo, cellphone, page, size));
		return "loan/loandata";
	}

	/**
	 * 借款细数据结果页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/loandetail/{id}")
	public String loandetail(@PathVariable("id") String id, Model model) {
		Loan loan = loanService.loadById(id);
		Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
		UserProperties userProperties = userInfoService.loadPropertiesByUserId(loan.getUser().getId());
		model.addAttribute("userProperties", userProperties);
		if (null == dictionary) {
			model.addAttribute("purpose", loan.getPurpose());
		} else {
			model.addAttribute("purpose", dictionary.getName());
		}

		model.addAttribute("product", loan.getProduct());
		model.addAttribute("repay", loan.getProduct().getRepay());

		model.addAttribute("loan", loan);
		List<LoanAudit> loanAuditList = loanService.findLoanAuditByLoan(loan);
		model.addAttribute("loanaudits", loanAuditList);
		List<Invest> invests = investService.findByLoan(loan);
		model.addAttribute("invests", invests);
		List<LoanRepay> loanRepayList = repayService.getloanRepayRecords(loan);
		model.addAttribute("loanRepays", loanRepayList);
		return "loan/loandetail";
	}

	/**
	 * 借款审核查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/loanaudit")
	public String loanaudit() {

		return "loan/loanaudit";
	}

	/**
	 * 借款满标放款查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/loanfullaudit")
	public String loanfullaudit() {

		return "loan/loanfullaudit";
	}

	/**
	 * 借款审核查询数据结果页面
	 * 
	 * @param loanNo
	 * @param account
	 * @param startAmount
	 * @param endAmount
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/loanauditdata")
	public String loanauditdata(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, Integer page, Integer size, Model model) {
		model.addAttribute("loanaudit", loanService.findByLoanNoAndAccountAndAmountBetweenAndStatus(loanNo, account, startAmount, endAmount, page, size, Loan.Status.AUDIT_FIRST, Loan.Status.AUDIT_FINAL));
		return "loan/loanauditdata";
	}

	/**
	 * 借款满标审核查询数据结果页面
	 * 
	 * @param loanNo
	 * @param account
	 * @param startAmount
	 * @param endAmount
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/loanfullauditdata")
	public String loanfullauditdata(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, String page, String size, Model model) {
		model.addAttribute("loanfullaudit", loanService.findByLoanNoAndNickAndAmountBetweenAndStatus(loanNo, account, startAmount, endAmount, Loan.Status.FULL, page, size));

		return "loan/loanfullauditdata";
	}

	/**
	 * 借款初级审核明细数据结果页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/firstauditdetail/{id}")
	public String firstauditdetail(@PathVariable("id") String id, Model model) {

		Loan loan = loanService.loadById(id);
		model.addAttribute("loan", loan);
		Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
		model.addAttribute("purpose", dictionary.getName());
		model.addAttribute("product", loan.getProduct());
		model.addAttribute("repay", loan.getProduct().getRepay());
		model.addAttribute("user", loan.getUser());
		String labelStr = App.config("user.auth.labels");
		String[] labelNames = labelStr.split(",");
		List<Label> labels = labelService.findByNames(labelNames);
		model.addAttribute("labels", labels);

		model.addAttribute("loanUserId", loan.getUser().getId());
		model.addAttribute("loanId", id);
		return "loan/firstauditdetail";
	}

	/**
	 * 借款终级审核明细数据结果页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/finalauditdetail/{id}")
	public String finalauditdetail(@PathVariable("id") String id, Model model) {

		Loan loan = loanService.loadById(id);
		model.addAttribute("loan", loan);
		Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
		model.addAttribute("purpose", dictionary.getName());
		model.addAttribute("product", loan.getProduct());
		model.addAttribute("repay", loan.getProduct().getRepay());
		model.addAttribute("user", loan.getUser());
		String labelStr = App.config("user.auth.labels");
		String[] labelNames = labelStr.split(",");
		List<Label> labels = labelService.findByNames(labelNames);
		model.addAttribute("labels", labels);

		model.addAttribute("loanUserId", loan.getUser().getId());
		model.addAttribute("loanId", id);
		return "loan/finalauditdetail";
	}

	/**
	 * 借款满标审核明细数据结果页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/fullauditdetail/{id}")
	public String fullauditdetail(@PathVariable("id") String id, Model model) {

		Loan loan = loanService.loadById(id);
		model.addAttribute("loan", loan);
		Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
		if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
			model.addAttribute("purpose", loan.getPurpose());
		} else {
			model.addAttribute("purpose", dictionary.getName());
		}
		model.addAttribute("product", loan.getProduct());
		model.addAttribute("repay", loan.getProduct().getRepay());
		model.addAttribute("user", loan.getUser());
		String labelStr = App.config("user.auth.labels");
		String[] labelNames = labelStr.split(",");
		List<Label> labels = labelService.findByNames(labelNames);
		model.addAttribute("labels", labels);

		model.addAttribute("loanUserId", loan.getUser().getId());
		model.addAttribute("loanId", id);
		return "loan/fullauditdetail";
	}

	@RequestMapping("loadPicture/{loanUserId}/{labelId}")
	public String loadPicture(@PathVariable("loanUserId") String loanUserId, @PathVariable("labelId") String labelId, Model model) {
		List<UserImage> images = userManageService.loadImageByUserAndLabelAndType(loanUserId, labelId, Type.AUTH);
		model.addAttribute("images", images);
		model.addAttribute("labelId", labelId);
		return "loan/picture";
	}

	@RequestMapping("getUserBasic/{loanUserId}")
	public String getUserBasic(@PathVariable("loanUserId") String loanUserId, Model model) {
		Logger.info("getUserBasic start ");
		LoanUserBasic loanUserBasic = loanService.loadLoanUserBasicByUserId(loanUserId);

		model.addAttribute("loanUserBasic", loanUserBasic);
		return "loan/userbasic";
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserJob/{loanUserId}")
	public String getUserJob(@PathVariable("loanUserId") String loanUserId, Model model) {
		Logger.info("getUserJob start ");
		User user = userInfoService.findByUserId(loanUserId);
		List<UserJob> userJobList = userInfoService.findJobByUserId(user);

		model.addAttribute("userJobs", userJobList);
		return "loan/userjob";
	}

	@RequestMapping("getUserHourse/{loanUserId}")
	public String getUserHourse(@PathVariable("loanUserId") String loanUserId, Model model) {
		Logger.info("getUserHourse start ");
		User user = userInfoService.findByUserId(loanUserId);
		List<UserHouse> userHouseList = userInfoService.findHouseByUserId(user);

		model.addAttribute("userHouses", userHouseList);
		return "loan/userhourse";
	}

	@RequestMapping("getUserCar/{loanUserId}")
	public String getUserCar(@PathVariable("loanUserId") String loanUserId, Model model) {
		Logger.info("getUserCar start ");
		User user = userInfoService.findByUserId(loanUserId);
		List<UserCar> userCarList = userInfoService.findCarByUserId(user);

		model.addAttribute("userCars", userCarList);
		return "loan/usercar";
	}

	@RequestMapping("getUserContacter/{loanUserId}")
	public String getUserContacter(@PathVariable("loanUserId") String loanUserId, Model model) {
		Logger.info("getUserContacter start ");
		User user = userInfoService.findByUserId(loanUserId);
		List<UserContacter> userContacterList = userInfoService.findContacterByUserId(user);

		model.addAttribute("userContacters", userContacterList);
		return "loan/usercontacter";
	}

	@RequestMapping("/firstaudit")
	@ResponseBody
	public Result firstaudit(String loanId, String status, String remark, String fixAmount, Model model) {
		Result result = new Result();
		BigDecimal finalFixAmount = BigDecimal.ZERO;
		Loan loan = loanService.loadById(loanId);
		if (Strings.empty(fixAmount)) {
			finalFixAmount = loan.getAmount();
		} else {
			finalFixAmount = new BigDecimal(fixAmount);
		}
		Logger.info("loanId:" + loanId + ",status:" + status + ",finalFixAmount:" + finalFixAmount + ",remark :" + remark);
		Loan loanResult = null;
		// 初审通过
		if (Strings.equals(status, "00")) {
			loanResult = loanService.firstAudit(loan, true, finalFixAmount, remark);
			// 初审驳回
		} else if (Strings.equals(status, "01")) {
			loanResult = loanService.firstAudit(loan, false, finalFixAmount, remark);
		}
		if (loanResult != null) {
			result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		} else {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
		}
		return result;
	}

	@RequestMapping("/finalaudit")
	@ResponseBody
	public Result finalaudit(String loanId, String status, String remark, String fixAmount, String labels, Model model) {

		Result result = new Result();
		BigDecimal finalFixAmount = BigDecimal.ZERO;

		Loan loan = loanService.loadById(loanId);
		if (Strings.empty(fixAmount)) {
			finalFixAmount = loan.getAmount();
		} else {
			finalFixAmount = new BigDecimal(fixAmount);
		}
		Logger.info("loanId:" + loanId + ",status:" + status + ",finalFixAmount:" + finalFixAmount + ",remark :" + remark + ",labels:" + labels);
		List<String> labelList = Strings.split(labels, ",");

		Loan loanResult = null;
		// 初审通过
		if (Strings.equals(status, "00")) {
			loanResult = loanService.finalAudit(loan, true, finalFixAmount, remark, labelList);
			// 终审驳回
		} else if (Strings.equals(status, "01")) {
			loanResult = loanService.finalAudit(loan, false, finalFixAmount, remark, labelList);
		}
		if (loanResult != null) {
			result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		} else {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
		}
		return result;
	}
   /**
    * 满标处理
    * @param loanId
    * @param status
    * @param remark
    * @return
    */
	@RequestMapping("/fullaudit")
	@ResponseBody
	public Result fullaudit(String loanId, String status, String remark) {
		Result result = new Result();
		Logger.info("loanId:" + loanId + ",status:" + status + ",remark :" + remark);
		Loan loanResult = null;
		try {
			if(Strings.equals(status, "00")) { // 满标放款
				loanResult = loanService.loanOut(loanId, remark, true);
			}else if (Strings.equals(status, "01")) {// 满标流标
				loanResult = loanService.loanOut(loanId, remark, false);
			}
			if (loanResult != null) {
				result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
			}else{
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			}
		} catch (Exception e) {
			Logger.error("满标处理异常：",e);
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
		}
		return result;
	}

	// /**
	// * 借款初审通过
	// *
	// * @param loanId
	// * @param fixAmount
	// * @param memo
	// * @return
	// */
	// @RequestMapping("/firstauditpass")
	// @ResponseBody
	// public Result firstauditpass(String loanId, String fixAmount, String
	// memo) {
	// Result result = new Result();
	// BigDecimal finalFixAmount = BigDecimal.ZERO;
	//
	// Loan loan = loanService.loadById(loanId);
	// if (Strings.empty(fixAmount)) {
	// finalFixAmount = loan.getAmount();
	// } else {
	// finalFixAmount = new BigDecimal(fixAmount);
	// }
	// Logger.info("loanId:" + loanId + ",finalFixAmount:" + finalFixAmount +
	// ",memo :" + memo);
	//
	// Loan loanResult = loanService.firstAudit(loan, true, finalFixAmount,
	// memo);
	// if (loanResult != null) {
	// result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
	// } else {
	// result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
	// }
	// return result;
	//
	// }

	// /**
	// * 借款终审通过
	// *
	// * @param loanId
	// * @param fixAmount
	// * @param memo
	// * @return
	// */
	// @RequestMapping("/finalauditpass")
	// @ResponseBody
	// public Result firstauditpass(String loanId, String fixAmount, String
	// memo, String labels) {
	// Result result = new Result();
	// BigDecimal finalFixAmount = BigDecimal.ZERO;
	//
	// Loan loan = loanService.loadById(loanId);
	// if (Strings.empty(fixAmount)) {
	// finalFixAmount = loan.getAmount();
	// } else {
	// finalFixAmount = new BigDecimal(fixAmount);
	// }
	// Logger.info("loanId:" + loanId + ",finalFixAmount:" + finalFixAmount +
	// ",memo :" + memo + ",labels:" + labels);
	// List<String> labelList = Strings.split(labels, ",");
	//
	// Loan loanResult = loanService.finalAudit(loan, true, finalFixAmount,
	// memo, labelList);
	// if (loanResult != null) {
	// result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
	// } else {
	// result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
	// }
	// return result;
	//
	// }

	// /**
	// * 借款初审拒绝
	// *
	// * @param loanId
	// * @param fixAmount
	// * @param memo
	// * @return
	// */
	// @RequestMapping("/firstauditreject")
	// @ResponseBody
	// public Result firstauditreject(String loanId, String fixAmount, String
	// memo) {
	// Result result = new Result();
	// BigDecimal finalFixAmount = BigDecimal.ZERO;
	// Loan loan = loanService.loadById(loanId);
	// if (Strings.empty(fixAmount)) {
	// finalFixAmount = loan.getAmount();
	// } else {
	// finalFixAmount = new BigDecimal(fixAmount);
	// }
	// Logger.info("loanId:" + loanId + ",finalFixAmount:" + finalFixAmount +
	// ",memo :" + memo);
	//
	// Loan loanResult = loanService.firstAudit(loan, false, finalFixAmount,
	// memo);
	// if (loanResult != null) {
	// result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
	// } else {
	// result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
	// }
	// return result;
	//
	// }

	// /**
	// * 借款终审拒绝
	// *
	// * @param loanId
	// * @param fixAmount
	// * @param memo
	// * @return
	// */
	// @RequestMapping("/finalauditreject")
	// @ResponseBody
	// public Result finalauditreject(String loanId, String fixAmount, String
	// memo) {
	// Result result = new Result();
	// BigDecimal finalFixAmount = BigDecimal.ZERO;
	// Loan loan = loanService.loadById(loanId);
	// if (Strings.empty(fixAmount)) {
	// finalFixAmount = loan.getAmount();
	// } else {
	// finalFixAmount = new BigDecimal(fixAmount);
	// }
	// Logger.info("loanId:" + loanId + ",finalFixAmount:" + finalFixAmount +
	// ",memo :" + memo);
	//
	// Loan loanResult = loanService.finalAudit(loan, false, finalFixAmount,
	// memo, null);
	// if (loanResult != null) {
	// result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
	// } else {
	// result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
	// }
	// return result;
	//
	// }

	// /**
	// * 满标放款通过
	// *
	// * @param loanId
	// * @param memo
	// * @return
	// */
	// @RequestMapping("/fullauditpass")
	// @ResponseBody
	// public Result fullauditpass(String loanId, String memo) {
	// Result result = new Result();
	//
	// Logger.info("loanId:" + loanId + ",memo :" + memo);
	//
	// Loan loanResult = loanService.loanOut(loanId, memo, true);
	// if (loanResult != null) {
	// result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
	// } else {
	// result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
	// }
	// return result;
	//
	// }
	//
	// /**
	// * 满标放款拒绝
	// *
	// * @param loanId
	// * @param memo
	// * @return
	// */
	// @RequestMapping("/fullauditreject")
	// @ResponseBody
	// public Result fullauditreject(String loanId, String memo) {
	// Result result = new Result();
	//
	// Logger.info("loanId:" + loanId + ",memo :" + memo);
	//
	// Loan loanResult = loanService.loanOut(loanId, memo, false);
	// if (loanResult != null) {
	// result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
	// } else {
	// result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
	// }
	// return result;
	//
	// }

	@RequestMapping("/checkMoneyMore")
	@ResponseBody
	public JSONObject checkMoneyMore(BigDecimal fixAmount, String loanId) {
		JSONObject jsonObj = new JSONObject();
		if (fixAmount != null) {
			Logger.info("fixAmount:" + fixAmount + "loanId:" + loanId);
			Loan loan = loanService.loadById(loanId);
			BigDecimal loanAmount = loan.getAmount();
			Logger.info("loanAmount:" + loanAmount);
			// 大于返回false提示不成功信息
			if (fixAmount.compareTo(loanAmount) == 1) {
				jsonObj.put("fixAmount", false);
			} else {
				jsonObj.put("fixAmount", true);
			}
		} else {
			jsonObj.put("fixAmount", true);
		}
		return jsonObj;
	}

	/**
	 * 债权标 :还款
	 * 
	 * @param repayid
	 * @return
	 */
	@RequestMapping("/repayment/{repayid}")
	public String repayment(@PathVariable("repayid") String repayid, Model model) {
		Logger.info("还款操作: repayid:" + repayid);
		String code = "", msg = "";
		try {
			String loanRepayId = loanService.queryLoanRepayId(repayid);
			boolean repaymentResult = repayService.repayment(loanRepayId);
			if (repaymentResult) {
				// 更新债权明细表中状态
				loanService.updateCreditRepayPlanStatus(repayid, CreditRepayPlan.Status.ALREADY_PAY);
				Logger.info("债权还款操作 成功：债权还款明细id=" + repayid);
				code = "00";
				msg = "还款成功";
			} else {
				code = "01";
				msg = "还款失败";
			}
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
			Logger.error("债权标 :还款", e);
		}
		model.addAttribute("code", code);
		model.addAttribute("msg", msg);
		return "loan/creditRepayResult";
	}

	/**
	 * 债权人账户 线下充值
	 * 
	 * @param creditorId
	 * @param amount
	 * @param model
	 * @return
	 */
	@RequestMapping("/goCharge/{creditorId}")
	public String creditorCharge(@PathVariable("creditorId") String creditorId, Model model) {
		if (Strings.empty(creditorId)) {
			throw new ServiceException("入参：债权人ID 为空");
		}
		UserAccount userAccount = loanService.queryUserAccountByCreditorId(creditorId);
		if (userAccount == null) {
			Logger.info("债权人账户线下充值操作: 根据债权人ID=" + creditorId + ",没有获取账户信息");
		}
		model.addAttribute("account", userAccount);
		return "loan/loanCharge";
	}

	@RequestMapping("/charge")
	public String charge(String amount, String accountId, Model model) {
		String eL = "^//d*[1-9]//d*$";// 正整数
		if (Strings.empty(amount) || Pattern.compile(eL).matcher(amount).matches()) {
			throw new ServiceException("充值操作:金额=" + amount + ",不是正整数");
		}
		if (Strings.empty(accountId)) {
			throw new ServiceException("充值操作:债权人ID 为空");
		}
		UserAccount userAccount = loanService.accountCharge(amount, accountId);
		model.addAttribute("account", userAccount);
		return "loan/loanCharge";
	}

}

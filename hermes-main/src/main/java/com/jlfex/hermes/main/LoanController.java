package com.jlfex.hermes.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.pojo.InvestInfo;
import com.jlfex.hermes.service.pojo.LoanInfo;
import com.jlfex.hermes.service.pojo.ProductInfo;

/**
 * 
 * 借款控制器
 * 
 * @author Ray
 * @version 1.0, 2014-1-2
 * @since 1.0
 */
@Controller
@RequestMapping("/loan")
public class LoanController {

	/** 借款服务接口 */
	@Autowired
	private LoanService loanService;

	/** 产品服务接口 */
	@Autowired
	private ProductService productService;
	/** 产品服务接口 */
	@Autowired
	private RepayService repayService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private InvestService investService;

	private static final String COMPANY_NAME = "app.company.name";
	private static final String COMPANY_ADDRESS = "app.company.address";

	/**
	 * 索引
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("nav", IndexController.HomeNav.LOAN);
		model.addAttribute("products", productService.getAll());
		model.addAttribute("purposes", dictionaryService.getByTypeCode("loan_purpose"));
		return "loan/index";
	}
	
	@RequestMapping("/test")
	public String test(Model model) {

		List<Loan> loanList = loanService.findByStatus(Loan.Status.FULL);
		model.addAttribute("loans", loanList);
		// 返回视图
		return "loan/test";
	}

	@RequestMapping("/testSubmit")
	public String testSubmit() {
		// Properties properties =new Properties();
		// properties.setName("投标金额倍数");
		// properties.setCode("invest.bid.multiple");
		// properties.setValue("50");
		// propertiesService.save(properties);
		// Logger.info("loanid:" + loanid);
		// loanService.loanOut(loanid, null, true);
		// User user = userInfoService.findByUserId("crop");
		// UserAccount userAccount=new UserAccount();
		// userAccount.setUser(user);
		// userAccount.setBalance(BigDecimal.ZERO);
		// userAccount.setMinus("00");
		// userAccount.setStatus("00");
		// userAccount.setType("15");
		// userAccount.setVersion(0L);
		// userAccountRepository.save(userAccount);
		return "0";
	}

	/**
	 * 我的借款
	 * 
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/myloan")
	public String myloan(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		List<LoanInfo> loanInfoList = loanService.findByUser(user);
		int loanSuccessCount = 0;
		BigDecimal loanAmount = BigDecimal.ZERO;
		for (LoanInfo loanInfo : loanInfoList) {
			if (Loan.Status.REPAYING.equals(loanInfo.getStatus()) || Loan.Status.COMPLETED.equals(loanInfo.getStatus())) {
				loanSuccessCount = loanSuccessCount + 1;
				loanAmount = loanAmount.add(Numbers.parseCurrency(loanInfo.getAmount()));
			}
		}
		int loanCount = loanInfoList.size();
		model.addAttribute("loanSuccessCount", loanSuccessCount);
		model.addAttribute("loanCount", loanCount);
		model.addAttribute("loanAmount", loanAmount);
		model.addAttribute("loans", loanInfoList);
		model.addAttribute("nav", "loan");

		// 返回视图
		return "loan/myloan";
	}

	/**
	 * 还款明细
	 * 
	 * @param loanid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/myloaninfo/{loanid}")
	public String myloaninfo(@PathVariable("loanid") String loanid, Model model) {
		App.checkUser();
		Loan loan = loanService.loadById(loanid);
		List<LoanRepay> loanRepayList = repayService.getloanRepayRecords(loan);
		model.addAttribute("loan", loan);
		model.addAttribute("product", loan.getProduct());
		model.addAttribute("repay", loan.getProduct().getRepay());
		Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
		model.addAttribute("purpose", dictionary.getName());
		model.addAttribute("loanRepays", loanRepayList);
		model.addAttribute("nav", "loan");

		// 返回视图
		return "loan/myloaninfo";
	}

	//
	// /**
	// * 还款
	// *
	// * @param request
	// * @return
	// */
	// @RequestMapping("/repayment/{repay}")
	// public String repayment(@PathVariable("repay") String repayid, Model
	// model) {
	// Result result = new Result();
	//
	// App.checkUser();
	// boolean repaymentResult =repayService.repayment(repayid);
	// if (repaymentResult) {
	// result.setType(Type.SUCCESS);
	// } else {
	// result.setType(Type.FAILURE);
	// }
	// return result;
	// }

	/**
	 * 还款操作
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/repayment/{repayid}")
	@ResponseBody
	public Result repayment(@PathVariable("repayid") String repayid) {
		Result result = new Result();
		Logger.info("repayid:" + repayid);
		try {
			boolean repaymentResult = repayService.repayment(repayid);
			if (repaymentResult) {
				result.setType(Type.SUCCESS);
			} else {
				result.setType(Type.FAILURE);
			}
		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex);
			result.setType(Type.FAILURE);
		}
		return result;
	}

	@RequestMapping("/repaymentsuccess")
	public String repaymentsuccess(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		UserAccount userAccount = userInfoService.loadAccountByUserAndType(user, UserAccount.Type.CASH);
		model.addAttribute("nav", "loan");
		model.addAttribute("balance", userAccount.getBalance());
		// 返回视图
		return "loan/myloanrepaysuccess";
	}

	@RequestMapping("/repaymentfailure")
	public String repaymentfailure(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		UserAccount userAccount = userInfoService.loadAccountByUserAndType(user, UserAccount.Type.CASH);
		model.addAttribute("nav", "loan");
		model.addAttribute("balance", userAccount.getBalance());
		// 返回视图
		return "loan/myloanrepayfailure";
	}

	/**
	 * 选择借款条件
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/display")
	public String display(Model model) {
		// App.checkUser();
		model.addAttribute("nav", "loan");
		List<ProductInfo> productList = productService.findAll();
		model.addAttribute("products", productList);
		model.addAttribute("productSize", productList.size());
		return "loan/display";
	}

	/**
	 * 生成借款方案
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/loanprogram")
	public String loanprogram(String amount, String period, String rate, String productId, String productName, String purposeName, String purposeId, String repayName, String repayId, Model model) {
	    try {
			App.checkUser();
		} catch (Exception e) {
			return "redirect:/userIndex/skipSignIn";
		}
		Logger.info("amount:" + amount + ",period:" + period + ",rate :" + rate + ",productId :" + productId + ",productName :" + productName + ",purposeName :" + purposeName + ",purposeId :"
				+ purposeId + ",repayName :" + repayName + ",repayId :" + repayId);
		model.addAttribute("amount", Numbers.toCurrency(new Double(amount)));
		model.addAttribute("rate", Numbers.toPercent(new Double(rate) / 100));
		model.addAttribute("period", period);

		model.addAttribute("productId", productId);
		model.addAttribute("productName", productName);
		model.addAttribute("purposeName", purposeName);
		model.addAttribute("purposeId", purposeId);
		model.addAttribute("repayName", repayName);
		model.addAttribute("repayId", repayId);
		Loan loan = new Loan();
		Product product = productService.loadById(productId);
		loan.setManageFee(product.getManageFee());
		loan.setManageFeeType(product.getManageFeeType());
		loan.setAmount(new BigDecimal(amount));
		loan.setPeriod(new Integer(period));
		loan.setRate(new BigDecimal(rate).divide(new BigDecimal(100)));
		Repay repayInfo = repayService.loadById(repayId);
		loan.setRepay(repayInfo);
		List<LoanRepay> repayPlanList = loanService.getRepayPlan(loan);
		model.addAttribute("repayPlans", repayPlanList);
		model.addAttribute("nav", "loan");
		return "loan/loanprogram";
	}

	/**
	 * 借款确认
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/programconfirm")
	@ResponseBody
	public Result programconfirm(String amount, String period, String rate, String productId, String purposeId, String repayId, String remark) {
		Result result = new Result();
		try {
			App.checkUser();
			Logger.info("amount:" + amount + "period:" + period + "rate:" + rate + "productId:" + productId + "purposeId:" + purposeId + "repayId:" + repayId + "description:" + remark);
			Loan loan = new Loan();
			loan.setAmount(new BigDecimal(amount.replaceAll(",", "")));
			loan.setPeriod(new Integer(period));
			loan.setRate(new BigDecimal(rate.replace("%", "")).divide(new BigDecimal(100)));
			Repay repayInfo = repayService.loadById(repayId);
			loan.setRepay(repayInfo);
			Product productInfo = productService.loadById(productId);
			loan.setProduct(productInfo);
			loan.setPurpose(purposeId);
			loan.setDescription(remark);
			AppUser curUser = App.current().getUser();
			User user = userInfoService.findByUserId(curUser.getId());
			loan.setUser(user);
			Loan loanNew = loanService.save(loan);
			if (loanNew != null) {
				result.setType(Type.SUCCESS);
			} else {
				result.setType(Type.FAILURE);
			}
		} catch (Exception ex) {
			Logger.error("借款发布异常:", ex);
			result.setType(Type.FAILURE);
			return result;
		}
		return result;
	}

	@RequestMapping("/loansuccess")
	public String loansuccess(Model model) {
		App.checkUser();
		model.addAttribute("nav", "loan");
		// 返回视图
		return "loan/loansuccess";
	}
    /**
     * 借款：生成借款协议
     * @param loanId
     * @param model
     * @return
     */
	@RequestMapping("/deal/{loanId}")
	public String deal(@PathVariable("loanId") String loanId, Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		model.addAttribute("curUser", curUser);
		if (!Strings.blank(loanId)) {
			Loan loan = loanService.loadById(loanId);
			if (loan != null) {
				UserProperties loanUserProperties = userInfoService.loadPropertiesByUserId(loan.getUser().getId());
				model.addAttribute("loan", loan);
				Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
				model.addAttribute("purpose", dictionary.getName());
				model.addAttribute("product", loan.getProduct());
				model.addAttribute("repay", loan.getProduct().getRepay());
				model.addAttribute("user", loan.getUser());
				model.addAttribute("loanUserProperties", loanUserProperties);

				List<Invest> invests = investService.findByLoan(loan);
				List<InvestInfo> investInfoList = new ArrayList<InvestInfo>();
				BigDecimal sumInvestAmount = BigDecimal.ZERO;
				InvestInfo investInfo = null;
				for (Invest invest : invests) {
					investInfo = new InvestInfo();
					investInfo.setAccount(invest.getUser().getAccount());
					UserProperties investUserProperties = userInfoService.loadPropertiesByUserId(invest.getUser().getId());
					investInfo.setRealName(investUserProperties.getRealName());
					investInfo.setAmount(invest.getAmount());
					investInfo.setPeriod(invest.getLoan().getPeriod());
					investInfoList.add(investInfo);
					sumInvestAmount = sumInvestAmount.add(invest.getAmount());
				}
				// List<LoanRepay> repayPlan = loanService.getRepayPlan(loan);
				model.addAttribute("sumInvestAmount", sumInvestAmount);
				model.addAttribute("invests", investInfoList);
				List<LoanRepay> loanRepayList = repayService.findLoanRepay(loan);
				if (loanRepayList.size() > 0 && loanRepayList.get(0) != null) {
					model.addAttribute("loanRepay", loanRepayList.get(0));
				}
				// 取放款时间
				LoanLog loanLogLOAN = loanService.loadLogByLoanIdAndType(loanId, LoanLog.Type.LOAN);
				if (loanLogLOAN != null && loanLogLOAN.getDatetime() != null) {
					model.addAttribute("loantime", loanLogLOAN.getDatetime());
				}
			}
			model.addAttribute("now", Calendars.date());

			String companyName = App.config(COMPANY_NAME);
			String companyAddress = App.config(COMPANY_ADDRESS);
			model.addAttribute("companyName", companyName);
			model.addAttribute("companyAddress", companyAddress);

		}
		// 返回视图
		return "loan/deal";
	}
	/**
	 * 理财协议
	 * @param model
	 * @return
	 */
	@RequestMapping("/funanceProtocol")
	public String funanceProtocol(Model model) {
		String companyName = App.config(COMPANY_NAME);
		model.addAttribute("date", Calendars.date());
		model.addAttribute("emial", "hermes");
		model.addAttribute("companyName", companyName);
		return "loan/funanceProtocol";
	}
}

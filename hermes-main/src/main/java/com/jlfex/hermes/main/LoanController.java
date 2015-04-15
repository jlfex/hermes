package com.jlfex.hermes.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.BankAccount;
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
import com.jlfex.hermes.repository.AreaRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.service.BankAccountService;
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
	private UserPropertiesRepository userPropertiesRepository;
	@Autowired
	private InvestService investService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired 
	private AreaRepository areaRepository;

	private static final String COMPANY_NAME = "app.company.name";
	private static final String COMPANY_ADDRESS = "app.company.address";
	private static final String COMPANY_PNAME = "app.operation.name";

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

	/**
	 * 我的借款
	 * 
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/myloan/table")
	public String myloanTable(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		Page<LoanInfo> loanInfoList = loanService.findByUser(user,page,size);
		model.addAttribute("loans", loanInfoList);
		return "loan/myloan-table";
	}

	/**
	 * 我的借款
	 * 
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/myloan")
	public String myloan(Model model,Integer page, Integer size) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		Page<LoanInfo> loanInfoList = loanService.findByUser(user,page,size);
		int loanSuccessCount = 0;
		BigDecimal loanAmount = BigDecimal.ZERO;
		for (LoanInfo loanInfo : loanInfoList) {
			if (Loan.Status.REPAYING.equals(loanInfo.getStatus()) || Loan.Status.COMPLETED.equals(loanInfo.getStatus())) {
				loanSuccessCount = loanSuccessCount + 1;
				loanAmount = loanAmount.add(Numbers.parseCurrency(loanInfo.getAmount()));
			}
		}
		int loanCount = loanInfoList.getSize();
		model.addAttribute("loanSuccessCount", loanSuccessCount);
		model.addAttribute("loanCount", loanCount);
		model.addAttribute("loanAmount", loanAmount);
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
	public String myloaninfo(@PathVariable("loanid") String loanid, Model model,Integer page, Integer size) {
		App.checkUser();
		Loan loan = loanService.loadById(loanid);
		Page<LoanRepay> loanRepayList = repayService.getloanRepayRecords(loan,page,size);
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
		Logger.info("生成借款方案参数: amount:" + amount + ",period:" + period + ",rate :" + rate + ",productId :" + productId + ",productName :" + productName + ",purposeName :" + purposeName + ",purposeId :" + purposeId + ",repayName :" + repayName + ",repayId :" + repayId);
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
			loan.setLoanKind(Loan.LoanKinds.NORML_LOAN);
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
	 * 
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
				String purpose = "";
				if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
					purpose = loan.getPurpose();
				} else {
					Dictionary dictionary = dictionaryService.loadById(loan.getPurpose());
					purpose = dictionary.getName();
				}
				model.addAttribute("purpose", purpose);
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
		return "agree/loan";
	}

	/**
	 * 理财协议
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/funanceProtocol")
	public String funanceProtocol(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		model.addAttribute("curUser", userPropertiesRepository.findByUserId(curUser.getId()));
		model.addAttribute("now", Calendars.date());
		String companyName = App.config(COMPANY_NAME);
		String companyAddress = App.config(COMPANY_ADDRESS);
		String pname = App.config(COMPANY_PNAME);
		model.addAttribute("companyName", companyName);
		model.addAttribute("companyAddress", companyAddress);
		model.addAttribute("pname", pname);
		return "agree/finance";
	}

	@RequestMapping("/loanagree")
	public String loanAgree(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		model.addAttribute("curUser", userPropertiesRepository.findByUserId(curUser.getId()));
		model.addAttribute("now", Calendars.date());
		String companyName = App.config(COMPANY_NAME);
		String companyAddress = App.config(COMPANY_ADDRESS);
		model.addAttribute("companyName", companyName);
		model.addAttribute("companyAddress", companyAddress);
		return "agree/loan";
	}
	/**
	 * 支付委托协议
	 * @param model
	 * @return
	 */
	@RequestMapping("/payEntrustProtocol")
	public String payEntrustProtocol(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		BankAccount bankAccount = null;
		String city = null,province=null; 
		List<BankAccount> bankAccountList = bankAccountService.findByUserIdAndStatus(App.current().getUser().getId(), BankAccount.Status.ENABLED);
		if (bankAccountList == null || bankAccountList.size() != 1) {
			Logger.info("投标异常：没有找到理财人有效的银行卡信息");
		} else {
			bankAccount = bankAccountList.get(0);
			city = bankAccount.getCity().getName();
			province = areaRepository.findOne(bankAccount.getCity().getParentId()).getName();
		}
		model.addAttribute("user", userPropertiesRepository.findByUserId(curUser.getId()));
		model.addAttribute("nowDate", Calendars.date());
		model.addAttribute("bankAccount", bankAccount);
		model.addAttribute("city", city);
		model.addAttribute("province", province);
		return "agree/payEntrustProtocol";
	}
}

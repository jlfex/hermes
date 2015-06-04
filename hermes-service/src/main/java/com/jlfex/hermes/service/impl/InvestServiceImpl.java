package com.jlfex.hermes.service.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;

import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.constant.HermesEnum.P2ZJBank;
import com.jlfex.hermes.common.constant.HermesEnum.P2ZJIdType;
import com.jlfex.hermes.common.constant.HermesEnum.PPOrg;
import com.jlfex.hermes.common.constant.HermesEnum.Tx1361Status;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.Loan.Status;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.PPLimit;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.model.yltx.JlfexOrder;
import com.jlfex.hermes.repository.AreaRepository;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.CommonRepository.Script;
import com.jlfex.hermes.repository.CreditInfoRepository;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.repository.InvestRepository;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.repository.LoanRepayRepository;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.repository.PPLimitRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserImageRepository;
import com.jlfex.hermes.repository.UserLogRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;
import com.jlfex.hermes.repository.n.LoanNativeRepository;
import com.jlfex.hermes.service.BankAccountService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.api.yltx.JlfexService;
import com.jlfex.hermes.service.apiLog.ApiLogService;
import com.jlfex.hermes.service.cfca.CFCAOrderService;
import com.jlfex.hermes.service.cfca.ThirdPPService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.finance.FinanceOrderService;
import com.jlfex.hermes.service.financePlan.FinanceRepayPlanService;
import com.jlfex.hermes.service.loanRepay.LoanRepayService;
import com.jlfex.hermes.service.order.jlfex.JlfexOrderService;
import com.jlfex.hermes.service.pojo.InvestInfo;
import com.jlfex.hermes.service.pojo.LoanInfo;
import com.jlfex.hermes.service.pojo.yltx.request.OrderPayRequestVo;
import com.jlfex.hermes.service.pojo.yltx.response.OrderPayResponseVo;
import com.jlfex.hermes.service.pojo.yltx.response.OrderResponseVo;
import com.jlfex.hermes.service.pojo.yltx.response.OrderVo;
import com.jlfex.hermes.service.userAccount.UserAccountService;

/**
 * 
 * 理财业务实现
 */
@Service
@Transactional
public class InvestServiceImpl implements InvestService {
	@Autowired
	private InvestRepository investRepository;

	@Autowired
	private InvestProfitRepository investProfitRepository;

	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;

	@Autowired
	private LoanNativeRepository loanNativeRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanLogRepository loanLogRepository;

	@Autowired
	private UserLogRepository userLogRepository;

	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private TransactionService transactionService;
	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 用户图片信息仓库 */
	@Autowired
	private UserImageRepository userImageRepository;
	@Autowired
	private CreditInfoRepository creditInfoRepository;
	@Autowired
	private FinanceOrderService financeOrderService;
	@Autowired
	private JlfexService jlfexService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private JlfexOrderService jlfexOrderService;
	@Autowired
	private FinanceRepayPlanService financeRepayPlanService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private CFCAOrderService cFCAOrderService;
	@Autowired
	private CFCAOrderRepository cFCAOrderRepository;
	@Autowired
	private ThirdPPService thirdPPService;
	@Autowired
	private PPLimitRepository pPLimitRepository;
	@Autowired
	private ApiLogService apiLogService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private LoanRepayService loanRepayService;
	@Autowired
	private LoanRepayRepository loanRepayRepository;

	@Override
	public Invest save(Invest invest) {
		// 保存数据并返回
		return investRepository.save(invest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.InvestService#LoadById(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Invest loadById(String id) {
		return investRepository.findOne(id);
	}

	private String getCondition(String purpose, String raterange, String periodrange, String repay, String orderByField, String orderByDirection, String loanKind, Map<String, Object> params) {
		StringBuilder condition = new StringBuilder();

		if (!Strings.empty(purpose) && !Strings.equals(purpose, "不限")) {
			condition.append(" and hd.id = :purpose");
			params.put("purpose", purpose);
		}
		if (!Strings.empty(raterange) && !Strings.equals(raterange, "不限")) {
			getRateCondition(raterange, params, condition);
		}
		if (!Strings.empty(periodrange) && !Strings.equals(periodrange, "不限")) {
			getPeriodCondition(periodrange, params, condition);
		}
		if (!Strings.empty(repay) && !Strings.equals(repay, "不限")) {
			condition.append(" and hr.id = :repay");
			params.put("repay", repay);
		}
		if (Loan.LoanKinds.NORML_LOAN.equals(loanKind)) {
			if (!Strings.empty(loanKind)) {
				condition.append(" and hl.loan_kind = :loanKind");
				params.put("loanKind", loanKind);
			}
		} else {
			if (!Strings.empty(loanKind)) {
				condition.append(" and hl.loan_kind = :loanKind");
				params.put("loanKind", loanKind);
				condition.append(" or hl.loan_kind = :loanKind2");
				params.put("loanKind2", Loan.LoanKinds.YLTX_ASSIGN_LOAN);
			}
		}
		if (!Strings.empty(orderByField)) {
			if (orderByField.equalsIgnoreCase("rate"))
				condition.append(" order by hl.rate ");
			else if (orderByField.equalsIgnoreCase("period"))
				condition.append(" order by hl.period ");
		} else {
			if (Strings.empty(orderByDirection)) {
				condition.append(" order by hl.status asc,hl.datetime desc");
			} else {
				condition.append(" order by (hl.proceeds/hl.amount)");
			}
		}
		if (!Strings.empty(orderByDirection)) {
			condition.append(orderByDirection);
		} else {
			if (!Strings.empty(orderByField)) {
				condition.append(" asc");
			}
		}
		return condition.toString();
	}

	private void getRateCondition(String raterange, Map<String, Object> params, StringBuilder condition) {

		if (Strings.equals(raterange, "10%以下")) {
			condition.append(" and hl.rate >= :startRate  and hl.rate< :endRate");
			params.put("startRate", 0);
			params.put("endRate", 0.09);

		} else if (Strings.equals(raterange, "10%-12%")) {
			condition.append(" and hl.rate >= :startRate  and hl.rate< :endRate");
			params.put("startRate", 0.1);
			params.put("endRate", 0.12);
		} else if (Strings.equals(raterange, "12%-15%")) {
			condition.append(" and hl.rate >= :startRate  and hl.rate< :endRate");
			params.put("startRate", 0.12);
			params.put("endRate", 0.15);
		} else if (Strings.equals(raterange, "15%以上")) {
			condition.append(" and hl.rate >= :startRate");
			params.put("startRate", 0.15);
		}
	}

	private void getPeriodCondition(String raterange, Map<String, Object> params, StringBuilder condition) {

		if (Strings.equals(raterange, "3个月内")) {
			condition.append(" and hl.period >= :startPeriod  and hl.period< :endPeriod");
			params.put("startPeriod", 0);
			params.put("endPeriod", 3);

		} else if (Strings.equals(raterange, "3-6个月")) {
			condition.append(" and hl.period >= :startPeriod  and hl.period< :endPeriod");
			params.put("startPeriod", 3);
			params.put("endPeriod", 6);
		} else if (Strings.equals(raterange, "6-12个月")) {
			condition.append(" and hl.period >= :startPeriod  and hl.period< :endPeriod");
			params.put("startPeriod", 6);
			params.put("endPeriod", 12);
		} else if (Strings.equals(raterange, "12个月以上")) {
			condition.append(" and hl.period >= :startPeriod");
			params.put("startPeriod", 12);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestService#findByJointSql(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Page<LoanInfo> findByJointSql(String purpose, String raterange, String periodrange, String repay, String page, String size, String orderByField, String orderByDirection, String loanKind) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlSearchByLoan = commonRepository.readScriptFile(Script.searchByLoan);

		String sqlCountSearchByLoan = commonRepository.readScriptFile(Script.countSearchByLoan);
		String condition = getCondition(purpose, raterange, periodrange, repay, orderByField, orderByDirection, loanKind, params);
		sqlSearchByLoan = String.format(sqlSearchByLoan, condition);
		sqlCountSearchByLoan = String.format(sqlCountSearchByLoan, condition);

		// 初始化
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")));
		List<?> listCount = commonRepository.findByNativeSql(sqlCountSearchByLoan, params);
		Long total = Long.parseLong(String.valueOf(listCount.get(0)));
		List<?> list = commonRepository.findByNativeSql(sqlSearchByLoan, params, pageable.getOffset(), pageable.getPageSize());
		List<LoanInfo> loans = new ArrayList<LoanInfo>();
		for (int i = 0; i < list.size(); i++) {
			LoanInfo loanInfo = new LoanInfo();
			Object[] object = (Object[]) list.get(i);
			User loanUser = userRepository.findOne(String.valueOf(object[0]));
			UserImage userImage = userImageRepository.findByUserAndType(loanUser, UserImage.Type.AVATAR);
			if (userImage != null) {
				loanInfo.setAvatar(userImage.getImage());
			}
			loanInfo.setPurpose(String.valueOf(object[1]));
			loanInfo.setAmount(Numbers.toCurrency(new Double(String.valueOf(object[2]))));
			loanInfo.setRate(Numbers.percentRateOfDecimal(String.valueOf(object[3])));
			loanInfo.setPeriod(String.valueOf(object[4]));
			loanInfo.setRemain(Numbers.toCurrency(new Double(String.valueOf(object[6]))));
			loanInfo.setProgress(String.valueOf(object[7]));
			loanInfo.setRepayName(String.valueOf(object[8]));
			loanInfo.setStatus(String.valueOf(object[9]));
			loanInfo.setId(String.valueOf(object[10]));
			loans.add(loanInfo);
		}
		// 返回结果
		Page<LoanInfo> pageLoanInfo = new PageImpl<LoanInfo>(loans, pageable, total);
		return pageLoanInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.InvestService#findByLoan(com.jlfex.hermes
	 * .model.Loan)
	 */
	@Override
	public List<Invest> findByLoan(Loan loan) {
		return investRepository.findByLoan(loan);
	}
	@Override
	public Page<Invest> queryByLoan(final Loan loan, Integer page, Integer size) {		
		// 初始化
		Pageable pageable = Pageables.pageable(page, size, Direction.DESC, "updateTime");
		// 查询数据并返回结果
        return investRepository.findByLoan(loan, pageable);
	}
	/**
	 * 投标: 普通标 外部债权标
	 * 
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> bid(String loanId, User investUser, BigDecimal investAmount, String otherRepay) throws Exception {
		Map<String, String> backMap = new HashMap<String, String>();
		int updateRecord = loanNativeRepository.updateProceeds(loanId, investAmount);
		Logger.info("投标操作：loanId=%s,投标金额=%s", loanId, investAmount.toString());
		if (updateRecord == 1) {
			Loan loan = loanRepository.findOne(loanId);
			// 判断假如借款金额与已筹金额相等，更新状态为满标
			if (loan.getAmount().compareTo(loan.getProceeds()) == 0) {
				loan.setStatus(Loan.Status.FULL);
				loanRepository.save(loan);
				// 插入借款日志表(满标)
				saveLoanLog(investUser, investAmount, loan, LoanLog.Type.FULL, "投标成功");
			}
			// 投标：普通标 和 外部债权
			if (Loan.LoanKinds.NORML_LOAN.equals(loan.getLoanKind()) || Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
				// 投标冻结
				transactionService.freeze(Transaction.Type.FREEZE, investUser.getId(), investAmount, loanId, "投标冻结");
				backMap.put("code", "00");
				backMap.put("msg", "成功");
			} else {
				Logger.error("无效的标类型：loanKind=" + loan.getLoanKind());
				backMap.put("code", "99");
				backMap.put("msg", "无效的标类型：loanKind=" + loan.getLoanKind());
				return backMap;
			}
			// 保存理财信息
			saveInvestRecord(investUser, investAmount, otherRepay, loan, Invest.Status.FREEZE);
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, "投标成功");
		} else {
			String var = "投标操作：剩余金额不足。loanId=" + loanId + ",投标金额=" + investAmount.toString();
			Logger.info(var);
			backMap.put("code", "99");
			backMap.put("msg", var);
		}
		return backMap;
	}

	/**
	 * 易联标：下单支付
	 */
	@Override
	public OrderPayResponseVo createJlfexOrder(String loanId, User investUser, BigDecimal investAmount) throws Exception {
		Loan loan = loanRepository.findOne(loanId);
		// 判断是否有在途单
		List<JlfexOrder> payingOrderList = jlfexOrderService.queryByInvestUserAndPayStatus(investUser, HermesConstants.PAY_WAIT_CONFIRM);
		if(payingOrderList !=null && payingOrderList.size() >0){
		  throw new Exception("请稍候操作，您已经有"+payingOrderList.size()+"个投标操作付款确认中。");
		}
		// 判断标剩余金额是否足够
		BigDecimal remain = Numbers.parseCurrency(loan.getRemain());
		if (remain.compareTo(investAmount) < 0) {
			throw new Exception("投标失败，标的可投金额不足，剩余金额：" + remain + " < 投标金额:" + investAmount);
		}
		// 判断假如借款金额与已筹金额相等，更新状态为满标
		if (loan.getAmount().compareTo(loan.getProceeds()) == 0) {
			loan.setStatus(Loan.Status.FULL);
			loanRepository.save(loan);
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.FULL, "投标成功");
		}
		BankAccount bankAccount = null;
		FinanceOrder finaceOrder = financeOrderService.queryById(loan.getCreditIndex());
		List<BankAccount> bankAccountList = bankAccountService.findByUserIdAndStatus(investUser.getId(), BankAccount.Status.ENABLED);
		if (bankAccountList == null || bankAccountList.size() != 1) {
			Logger.info("投标异常：没有找到理财人有效的银行卡信息");
		} else {
			bankAccount = bankAccountList.get(0);
		}
		OrderPayRequestVo reqVo = buildOrderPayReqVo(investAmount, investUser, bankAccount, finaceOrder);
		// 调用下单并支付接口
		Map<String, String> orderPayMap = jlfexService.createOrderAndPay(reqVo);
		if (!HermesConstants.CODE_00.equals(orderPayMap.get("code"))) {
			throw new Exception(orderPayMap.get("msg"));
		}
		return JSON.parseObject(orderPayMap.get("msg"), OrderPayResponseVo.class);
	}

	/**
	 * 易联标： 投标
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String jlfexBid(String loanId, User investUser, BigDecimal investAmount, OrderPayResponseVo responseVo) throws Exception {
		String resultFlag = "99";
		Loan loan = loanRepository.findOne(loanId);
		if (responseVo == null) {
			saveInvestRecord(investUser, investAmount, null, loan, Invest.Status.FAIL);
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, "投标失败");
			return "投标失败,订单信息为空";
		}
		Logger.info("易联债权标投标:开始 ：loanNo=" + loan.getLoanNo() + ", 下单并支付接口返回:订单状态=" + responseVo.getOrderStatus() + ",支付状态=" + responseVo.getPayStatus());
		FinanceOrder finaceOrder = financeOrderService.queryById(loan.getCreditIndex());
		// 1:撤单
		String assetCode = getAssetCodeOfOrder(responseVo.getOrderCode().trim());
		if (HermesConstants.ORDER_WAIT_PAY.equals(responseVo.getOrderStatus()) && HermesConstants.PAY_FAIL.equals(responseVo.getPayStatus())) {
			jlfexService.revokeOrder(responseVo.getOrderCode());
			// 保存理财信息
			Invest badInvest = saveInvestRecord(investUser, investAmount, null, loan, Invest.Status.FAIL);
			jlfexOrderService.saveOrder(transOrderVo2Entity(responseVo, finaceOrder, badInvest, assetCode, JlfexOrder.Status.CANCEL));
			// 保存充值失败记录
			UserAccount cropAccount = userAccountService.findByUserIdAndType(HermesConstants.CROP_USER_ID, UserAccount.Type.ZHONGJIN_FEE);
			UserAccount investAccount = userAccountService.findByUserAndType(investUser, UserAccount.Type.CASH);
			transactionService.addCashAccountRecord(Transaction.Type.CHARGE, cropAccount, investAccount, investAmount, investUser.getId(), "JLfex代扣充值失败");
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, "投标失败");
			Logger.info("撤单成功!");
			return "订单支付失败";
		}
		// 2:成功
		Invest invest = null;
		if (HermesConstants.ORDER_PAYING.equals(responseVo.getOrderStatus().trim()) && HermesConstants.PAY_SUC.equals(responseVo.getPayStatus().trim())) {
			// 保存理财信息
			invest = saveInvestRecord(investUser, investAmount, null, loan, Invest.Status.FREEZE);
			transactionService.cropAccountToJlfexPay(Transaction.Type.CHARGE, investUser, UserAccount.Type.JLFEX_FEE, investAmount, investUser.getId(), "JLfex代扣充值成功");
			transactionService.freeze(Transaction.Type.FREEZE, investUser.getId(), investAmount, loanId, "投标冻结");
			// 保存操作日志
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, "投标成功");
			resultFlag = "00";
			Logger.info("资金流水记录成功  理财ID investId=" + invest.getId());
			int updateRecord = loanNativeRepository.updateProceeds(loanId, investAmount);
			if (updateRecord != 1) {
				throw new Exception("投标失败,当前标 可投金额不足,投标金额: " + investAmount + " > 可投金额：" + loan.getAmount().subtract(loan.getProceeds()));
			}
		} else {
			// 3:处理中
			// 保存理财信息
			resultFlag = "01";
			invest = saveInvestRecord(investUser, investAmount, null, loan, Invest.Status.WAIT);
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, "投标支付结果待确认中");
			Logger.info("支付状态 待确认中   理财ID investId=" + invest.getId());
		}
		// 保存理财收益信息
		saveInvestProfit(invest, repayService.findByLoanAndStatus(loan, LoanRepay.RepayStatus.WAIT));
		// 保存订单
		jlfexOrderService.saveOrder(transOrderVo2Entity(responseVo, finaceOrder, invest, assetCode, JlfexOrder.Status.WAIT_DEAL));
		// 判断假如借款金额与已筹金额相等，更新状态为满标
		if (loan.getAmount().compareTo(loan.getProceeds()) == 0) {
			loan.setStatus(Loan.Status.FULL);
			loanRepository.save(loan);
			saveLoanLog(investUser, investAmount, loan, LoanLog.Type.FULL, "投标满标");
		}

		return resultFlag;
	}

	/**
	 * 获取 订单 对应的 资产Id
	 * 
	 * @param orderCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getAssetCodeOfOrder(String orderCode) throws Exception {
		String result = jlfexService.queryOrderStatus(orderCode);
		OrderResponseVo responVo = JSON.parseObject(result, OrderResponseVo.class);
		List<OrderVo> orderVoList = responVo.getContent();
		if (orderVoList == null || orderVoList.size() != 1) {
			throw new Exception("根据orderCode=" + orderCode + " jlfex接口返回订单条数不唯一");
		}
		OrderVo vo = orderVoList.get(0);
		Logger.info("订单编号：" + orderCode + ",对应的资产编号为：" + vo.getChildAssetId().trim());
		return vo.getChildAssetId().trim();
	}

	/**
	 * 保存投标理财收益
	 * 
	 * @param invest
	 * @param loanRepayList
	 * @throws Exception
	 */
	@Override
	public void saveInvestProfit(Invest invest, List<LoanRepay> loanRepayList) throws Exception {
		if (loanRepayList == null || loanRepayList.size() == 0) {
			throw new Exception("投标收益保存异常：标还款计划明细为空");
		}
		List<InvestProfit> investProfitList = new ArrayList<InvestProfit>();
		for (LoanRepay loanRepay : loanRepayList) {
			InvestProfit profit = new InvestProfit();
			profit.setUser(invest.getUser());
			profit.setInvest(invest);
			profit.setLoanRepay(loanRepay);
			profit.setDate(loanRepay.getPlanDatetime());
			profit.setAmount(loanRepay.getAmount().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
			profit.setPrincipal(loanRepay.getPrincipal().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
			profit.setInterest(loanRepay.getInterest().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
			profit.setOverdueInterest(loanRepay.getOverdueInterest().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
			profit.setStatus(InvestProfit.Status.WAIT);
			investProfitList.add(profit);
		}
		investProfitRepository.save(investProfitList);
	}

	/**
	 * 易联标 规则： 理财产品募资开始时间之后，募资截止日期的中午12点之前发起
	 * 
	 * @param loan
	 * @return
	 */
	@Override
	public boolean checkValid(Loan loan) {
		boolean normal = false;
		try {
			FinanceOrder financeOrder = financeOrderService.queryById(loan.getCreditIndex());
			Date nowDate = new Date();
			Date RaisingBeginDate = financeOrder.getRaiseStartTime();
			Date RaisingEndDate = financeOrder.getRaiseEndTime();
			if (RaisingBeginDate.before(nowDate) && nowDate.before(RaisingEndDate)) {
				normal = true;
			}
		} catch (Exception e) {
			Logger.error("易联标投标规则判断异常:", e);
		}
		return normal;
	}

	/**
	 * 创建下单支付 请求 VO
	 * 
	 * @param invest
	 * @param bankAccount
	 * @param finaceOrder
	 * @return
	 * @throws Exception
	 */
	public OrderPayRequestVo buildOrderPayReqVo(BigDecimal bidAmount, User user, BankAccount bankAccount, FinanceOrder finaceOrder) throws Exception {
		String subbranchCity = bankAccount.getCity().getName();
		String subbranchProvince = areaRepository.findOne(bankAccount.getCity().getParentId()).getName();
		UserProperties userProperties = userPropertiesRepository.findByUser(user);
		OrderPayRequestVo reqVo = new OrderPayRequestVo();
		reqVo.setName(bankAccount.getName());
		reqVo.setCertiNum(userProperties.getIdNumber());
		reqVo.setCertiType(userProperties.getIdTypeName());
		reqVo.setFinanceProductId(finaceOrder.getUniqId());
		reqVo.setOrderAmt(bidAmount.setScale(2, RoundingMode.HALF_EVEN));
		reqVo.setBankName(bankAccount.getBank().getName());
		reqVo.setSubbranchProvince(subbranchProvince.replace(HermesConstants.PROVINCE_SUFFIX, ""));
		reqVo.setSubbranchCity(subbranchCity.replace(HermesConstants.PROVINCE_SUFFIX, ""));
		reqVo.setBankSubbranch(bankAccount.getDeposit());
		reqVo.setBankAccount(bankAccount.getAccount());
		return reqVo;
	}

	/**
	 * 保存 jlfex订单
	 * 
	 * @param vo
	 * @param financeOrder
	 * @param invest
	 * @return
	 * @throws Exception
	 */
	public JlfexOrder transOrderVo2Entity(OrderPayResponseVo vo, FinanceOrder financeOrder, Invest invest, String assetCode, String status) throws Exception {
		JlfexOrder entity = new JlfexOrder();
		entity.setFinanceOrder(financeOrder);
		entity.setInvest(invest);
		entity.setUser(invest.getUser());
		entity.setOrderCode(vo.getOrderCode());
		entity.setGuaranteePdfId(vo.getGuaranteePdfId());
		entity.setLoanPdfId(vo.getLoanPdfId());
		entity.setPaymentPdfId(vo.getPaymentPdfId());
		entity.setOrderStatus(vo.getOrderStatus());
		entity.setPayStatus(vo.getPayStatus());
		entity.setStatus(status);
		entity.setOrderAmount(invest.getAmount());
		entity.setAssetCode(assetCode);
		return entity;
	}

	/**
	 * 插入投资表
	 * 
	 * @param investUser
	 * @param investAmount
	 * @param otherRepay
	 * @param loan
	 */
	public Invest saveInvestRecord(User investUser, BigDecimal investAmount, String otherRepay, Loan loan, String status) {
		Invest invest = new Invest();
		invest.setAmount(investAmount);
		invest.setOtherRepay(otherRepay == null ? "" : otherRepay);
		BigDecimal ratio = investAmount.divide(loan.getAmount(), 8, RoundingMode.HALF_DOWN);
		invest.setRatio(ratio);
		invest.setUser(investUser);
		invest.setLoan(loan);
		invest.setDatetime(new Date());
		invest.setStatus(status);
		return investRepository.save(invest);
	}
	
	/**
	 * 插入借款日志表
	 * 
	 * @param investUser
	 * @param investAmount
	 * @param loan
	 * @param type
	 */
	@Override
	public void saveLoanLog(User investUser, BigDecimal investAmount, Loan loan, String type, String remark) throws Exception {
		LoanLog loanLog = new LoanLog();
		loanLog.setLoan(loan);
		loanLog.setUser(investUser.getId());
		loanLog.setDatetime(new Date());
		loanLog.setType(type);
		loanLog.setAmount(investAmount);
		loanLog.setRemark(remark);
		loanLogRepository.save(loanLog);
	}

	/**
	 * 插入用户日志表
	 * 
	 * @param investUser
	 */
	@Override
	public void saveUserLog(User investUser,String logType) throws Exception {
		UserLog userLog = new UserLog();
		userLog.setUser(investUser);
		userLog.setDatetime(new Date());
		userLog.setType(logType);
		userLogRepository.save(userLog);
	}

	/**
	 * 投标时：自己的借款标自己不能投资
	 * 
	 * @param loanId
	 * @param investUser
	 * @return
	 */
	@Override
	public boolean bidAuthentication(String loanId, User investUser) {
		boolean flag = true;
		Loan loan = loanRepository.findOne(loanId);
		if (loan != null && loan.getUser() != null && investUser != null) {
			User loanUser = loan.getUser();
			if (loanUser.getId().equals(investUser.getId())) {
				flag = false;
			}
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.InvestService#loadCountByUserAndStatus(com
	 * .jlfex.hermes.model.User, java.lang.String[])
	 */
	@Override
	public Long loadCountByUserAndStatus(User user, String... status) {
		return investRepository.loadCountByUserAndStatus(user, Arrays.asList(status));
	}

	/**
	 * 读取字典名称
	 * 
	 * @param id
	 * @return
	 */
	private String getDictionaryName(String id) {
		Dictionary dictionary = dictionaryRepository.findOne(id);
		if (dictionary == null)
			return null;
		return dictionary.getName();
	}

	@Override
	public Page<InvestInfo> findByUser(final User user,final List<String> loanKindList,Integer page, Integer size) {
		Pageable pageable = Pageables.pageable(page, size);
		List<InvestInfo> investinfoList = new ArrayList<InvestInfo>();
		Page<Invest> investList = investRepository.findAll(new Specification<Invest>() {			
			@Override
			public Predicate toPredicate(Root<Invest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = root.get("loan").get("loanKind").in(loanKindList);
				Predicate p2 = cb.equal(root.get("user"), user) ;				
				return cb.and(p1,p2);
			}
		}, pageable);		
		InvestInfo investInfo = null;
		for (Invest invest : investList) {
			investInfo = new InvestInfo();
			investInfo.setId(invest.getId());
			String purpose = "";
			String loanStatus = invest.getLoan().getLoanKind();
			if (Loan.LoanKinds.NORML_LOAN.equals(loanStatus)) {
				purpose = getDictionaryName(invest.getLoan().getPurpose());
			} else {
				purpose = invest.getLoan().getPurpose();
			}
			investInfo.setPurpose(purpose);
			investInfo.setRate(invest.getLoan().getRateFormat());
			investInfo.setAmount(invest.getAmount());
			investInfo.setPeriod(invest.getLoan().getPeriod());
			investInfo.setStatus(invest.getStatus());
			investInfo.setLoanKind(loanStatus);
			investInfo.setLoanNo(invest.getLoan().getLoanNo());
			List<InvestProfit> investProfitList = investProfitRepository.findByInvest(invest);
			BigDecimal shouldReceivePI = BigDecimal.ZERO;
			BigDecimal receivedPI = BigDecimal.ZERO;
			BigDecimal waitReceivePI = BigDecimal.ZERO;
			for (InvestProfit investProfit : investProfitList) {
				shouldReceivePI = shouldReceivePI.add(investProfit.getAmount());
				// 待收本息
				if (InvestProfit.Status.WAIT.equals(investProfit.getStatus())) {
					waitReceivePI = waitReceivePI.add(investProfit.getAmount());
				} else {
					receivedPI = receivedPI.add(investProfit.getPrincipal()).add(investProfit.getInterest());
				}
			}
			investInfo.setShouldReceivePI(Numbers.toCurrency(shouldReceivePI.doubleValue()));
			investInfo.setWaitReceivePI(Numbers.toCurrency(waitReceivePI.doubleValue()));
			investInfo.setReceivedPI(Numbers.toCurrency(receivedPI.doubleValue()));
			JlfexOrder jlfexOrder = jlfexOrderService.findByInvest(invest.getId());
			if(jlfexOrder!=null){
			   investInfo.setLoanPdfId(jlfexOrder.getLoanPdfId());
			   investInfo.setGuaranteePdfId(jlfexOrder.getGuaranteePdfId());
			}
			investinfoList.add(investInfo);
		}
		Page<InvestInfo> pageInvest = new PageImpl<InvestInfo>(investinfoList, pageable, investList.getTotalElements());
		return pageInvest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.InvestService#loadInvestRecordByUser(java.
	 * lang.String)
	 */
	@Override
	public Page<InvestInfo> loadInvestRecordByUser(String userId, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		List<InvestInfo> investinfoList = new ArrayList<InvestInfo>();
		List<Invest> investList = investRepository.findByUserIdOrderByStatusAscDatetimeDesc(userId);
		InvestInfo investInfo = null;
		for (Invest invest : investList) {
			investInfo = new InvestInfo();
			investInfo.setId(invest.getId());
			investInfo.setApplicationNo(invest.getLoan().getProduct().getCode() + "-" + invest.getLoan().getLoanNo());
			investInfo.setPurpose(getDictionaryName(invest.getLoan().getPurpose()));
			investInfo.setRate(invest.getLoan().getRateFormat());
			investInfo.setAmount(invest.getAmount());
			investInfo.setPeriod(invest.getLoan().getPeriod());
			investInfo.setStatus(invest.getStatusName());
			BigDecimal rate = invest.getLoan().getRate().divide(new BigDecimal(12), 10, RoundingMode.DOWN).multiply(new BigDecimal(invest.getLoan().getPeriod()));
			BigDecimal interest = invest.getAmount().multiply(rate);
			investInfo.setExpectProfit(Numbers.toCurrency(invest.getAmount().add(interest)));
			investInfo.setDatetime(invest.getDatetime());
			investinfoList.add(investInfo);
		}
		Long total = Long.valueOf(investinfoList.size());
		Page<InvestInfo> pageInvest = new PageImpl<InvestInfo>(investinfoList, pageable, total);
		return pageInvest;
	}

	@Override
	public boolean processAutoBidFailure(Loan loan) {
		// 借款表投标改为自动流标
		int success = loanNativeRepository.updateStatus(loan.getId(), Status.BID, Status.FAILURE_AUTO);
		if (success == 1) {
			// 债权标流标 更新债权信息 状态
			if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
				creditAutoBidDeal(loan);
			}
			List<Invest> investList = investRepository.findByLoan(loan);
			for (Invest invest : investList) {
				// 理财表冻结状态改为借款流标状态
				if (Strings.equals(invest.getStatus(), Invest.Status.FREEZE)) {
					invest.setStatus(Invest.Status.FAILURE);
					// 投标解冻
					transactionService.unfreeze(Transaction.Type.UNFREEZE, invest.getUser(), invest.getAmount(), invest.getId(), "投标解冻");
				}
			}
			investRepository.save(investList);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 债权标 流标后 更新债权信息 状态
	 * @param loan
	 */
	public void creditAutoBidDeal(Loan loan) {
		try {
			if (loan != null) {
				String status = CrediteInfo.Status.WAIT_ASSIGN;
				CrediteInfo creditInfo = creditInfoRepository.findOne(loan.getCreditIndex());
				Date deadTime = creditInfo.getDeadTime();
				if (deadTime != null) {
					if (deadTime.before(new Date())) {
						status = CrediteInfo.Status.FAIL_ASSIGNING;
					}
				}
				creditInfo.setStatus(status);
			} else {
				Logger.warn("债权标 流标后 更新债权信息状态:标信息为空");
			}
		} catch (Exception e) {
			Logger.error("债权标 流标更新债权信息 状态异常:", e);
		}
	}

	/**
	 * 我的理财： 标列表 综合查询
	 */
	@Override
	public Page<LoanInfo> investIndexLoanList(String page, String size, String loanKind) {
		Map<String, Object> params = new HashMap<String, Object>();
		String purpose = "", raterange = "", periodrange = "", repay = "", orderByField = "", orderByDirection = ""; // 页面调整中，过滤条件
		String sqlSearchByLoan = null, sqlCountSearchByLoan = null;
		if (Loan.LoanKinds.NORML_LOAN.equals(loanKind)) {
			sqlSearchByLoan = commonRepository.readScriptFile(Script.searchByLoan);
			sqlCountSearchByLoan = commonRepository.readScriptFile(Script.countSearchByLoan);
		} else if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loanKind)) {
			sqlSearchByLoan = commonRepository.readScriptFile(Script.searchByLoanAssign);
			sqlCountSearchByLoan = commonRepository.readScriptFile(Script.countSearchByLoanAssign);
		} else {
			throw new ServiceException("无效的标类型：" + loanKind);
		}
		String condition = getCondition(purpose, raterange, periodrange, repay, orderByField, orderByDirection, loanKind, params);
		sqlSearchByLoan = String.format(sqlSearchByLoan, condition);
		sqlCountSearchByLoan = String.format(sqlCountSearchByLoan, condition);
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")));
		List<?> listCount = commonRepository.findByNativeSql(sqlCountSearchByLoan, params);
		Long total = 0l;
		if (listCount.size() != 0) {
			total = Long.parseLong(String.valueOf(listCount.get(0)));
		}

		List<?> list = commonRepository.findByNativeSql(sqlSearchByLoan, params, pageable.getOffset(), pageable.getPageSize());
		List<LoanInfo> loans = new ArrayList<LoanInfo>();
		for (int i = 0; i < list.size(); i++) {
			LoanInfo loanInfo = new LoanInfo();
			Object[] object = (Object[]) list.get(i);
			User loanUser = userRepository.findOne(String.valueOf(object[0]));
			UserImage userImage = userImageRepository.findByUserAndType(loanUser, UserImage.Type.AVATAR);
			if (userImage != null) {
				loanInfo.setAvatar(userImage.getImage());
			}
			loanInfo.setPurpose(String.valueOf(object[1]));
			loanInfo.setAmount(Numbers.toCurrency(new Double(String.valueOf(object[2]))));
			loanInfo.setRate(Numbers.percentRateOfDecimal(String.valueOf(object[3])));
			loanInfo.setPeriod(String.valueOf(object[4]));
			loanInfo.setRemain(Numbers.toCurrency(new Double(String.valueOf(object[6]))));
			loanInfo.setProgress(String.valueOf(object[7]));
			loanInfo.setRepayName(String.valueOf(object[8]));
			loanInfo.setStatus(String.valueOf(object[9]));
			loanInfo.setId(String.valueOf(object[10]));
			if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loanKind)) {
				String purposeStr = String.valueOf(object[11]);
				loanInfo.setPurpose((purposeStr != null && purposeStr.length() > 4) ? (purposeStr.substring(0, 4) + "...") : purposeStr);
				if (Loan.LoanKinds.YLTX_ASSIGN_LOAN.equals(String.valueOf(object[13]))) {
					Calendar deadDate = Calendar.getInstance();
		            try {
		    			deadDate.setTime(Calendars.parse(HermesConstants.FORMAT_19, String.valueOf(object[14])));
		    		} catch (ParseException e) {
		    			Logger.error("债权判断是否过期异常",e);
		    		}
		    		Calendar nowDate = Calendar.getInstance();
		    		loanInfo.setOutOfDate(deadDate.getTimeInMillis() < nowDate.getTimeInMillis());
				}
			}
			loanInfo.setApplicationNo(String.valueOf(object[12]));
			loanInfo.setLoanKind(String.valueOf(object[13]));
			loans.add(loanInfo);
		}
		// 返回结果
		Page<LoanInfo> pageLoanInfo = new PageImpl<LoanInfo>(loans, pageable, total);
		return pageLoanInfo;
	}

	/**
	 * 判断用户投资普通标或债券标时现金账户余额是否充足
	 * 
	 * @param investAmount
	 * @return
	 */
	public boolean isBalanceEnough(BigDecimal investAmount) {
		UserAccount cashAccount = userAccountService.findByUserIdAndType(App.current().getUser().getId(), UserAccount.Type.CASH);
		if (investAmount.compareTo(cashAccount.getBalance()) > 0 && UserAccount.Minus.INMINUS.equals(cashAccount.getMinus())) {
			return false;
		}
		return true;
	}

	/**
	 * 投标并支付
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> bid2Pay(String loanId, User investUser, BigDecimal investAmount, String otherRepay) throws Exception {
		Map<String, String> backMap = new HashMap<String, String>();
		int updateRecord = loanNativeRepository.updateProceeds(loanId, investAmount);

		Logger.info("投标操作：loanId=%s,投标金额=%s", loanId, investAmount.toString());
		Loan loan = loanRepository.findOne(loanId);
		Tx1361Response response = null;
		Invest invest = null;
		String serialNo = cFCAOrderService.genSerialNo(HermesConstants.PRE_IN);
		// 请求日志
		Map<String, String> recodeMap = new HashMap<String, String>();
		ApiLog apiLog = null;
		try {
			if (updateRecord == 1) {
				BankAccount bankAccount = bankAccountService.findOneByUserIdAndStatus(investUser.getId(), BankAccount.Status.ENABLED);
				UserProperties userProperties = userPropertiesRepository.findByUser(investUser);
				UserAccount cashAccount = userAccountRepository.findByUserAndType(investUser, UserAccount.Type.CASH);

				Tx1361Request tx1361Request = cFCAOrderService.buildTx1361Request(investUser, investAmount.subtract(cashAccount == null ? BigDecimal.ZERO : cashAccount.getBalance()), bankAccount, userProperties, serialNo);
				recodeMap.put("interfaceMethod", HermesConstants.ZJ_INTERFACE_TX1361);
				response = thirdPPService.invokeTx1361(tx1361Request);
				recodeMap.put("requestMsg", tx1361Request.getRequestPlainText());
				apiLog = cFCAOrderService.recordApiLog(recodeMap);
				BigDecimal addAmount = investAmount.subtract(cashAccount == null ? BigDecimal.ZERO : cashAccount.getBalance());
				if (response.getCode().equals(HermesConstants.CFCA_SUCCESS_CODE)) {
					if (response.getStatus() == Tx1361Status.IN_PROCESSING.getStatus()) {
						invest = this.saveInvestRecord(investUser, investAmount, otherRepay, loan, Invest.Status.WAIT);
						transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, investUser, UserAccount.Type.ZHONGJIN_FEE, addAmount, invest.getId(), Transaction.Status.WAIT);
						backMap.put("code", Tx1361Status.IN_PROCESSING.getStatus().toString());
						backMap.put("msg", "您的投标并支付申请已提交成功，正在确认中！");
						this.saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, LoanLog.Status.WAIT);
					} else if (response.getStatus() == Tx1361Status.WITHHOLDING_SUCC.getStatus()) {
						invest = this.saveInvestRecord(investUser, investAmount, otherRepay, loan, Invest.Status.FREEZE);
						// 投标冻结
						transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, investUser, UserAccount.Type.ZHONGJIN_FEE, addAmount, invest.getId(), Transaction.Status.RECHARGE_SUCC);
						transactionService.freeze(Transaction.Type.FREEZE, investUser.getId(), investAmount, loanId, "投标冻结");
						backMap.put("code", Tx1361Status.WITHHOLDING_SUCC.getStatus().toString());
						backMap.put("msg", "您已投标并支付成功！");
						this.saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, LoanLog.Status.FREEZE);

						// 判断假如借款金额与已筹金额相等，更新状态为满标
						if (loan.getAmount().compareTo(loan.getProceeds()) == 0) {
							loan.setStatus(Loan.Status.FULL);
							loanRepository.save(loan);
							// 插入借款日志表(满标)
							this.saveLoanLog(investUser, investAmount, loan, LoanLog.Type.FULL, "投标成功");
						}
					} else {
						backMap.put("code", Tx1361Status.WITHHOLDING_FAIL.getStatus().toString());
						backMap.put("msg", "投标并支付失败！");

						invest = this.saveInvestRecord(investUser, investAmount, otherRepay, loan, Invest.Status.FAIL);
						transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, investUser, UserAccount.Type.ZHONGJIN_FEE, addAmount, invest.getId(), Transaction.Status.RECHARGE_FAIL);
						loanNativeRepository.updateProceeds(loanId, investAmount.multiply(new BigDecimal(-1)));
						this.saveLoanLog(investUser, investAmount, loan, LoanLog.Type.INVEST, LoanLog.Status.FAIL);
					}
				} else {
					backMap.put("code", Tx1361Status.WITHHOLDING_FAIL.getStatus().toString());
					backMap.put("msg", response.getMessage());
					invest = this.saveInvestRecord(investUser, investAmount, otherRepay, loan, Invest.Status.FAIL);
					transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, investUser, UserAccount.Type.ZHONGJIN_FEE, addAmount, invest.getId(), Transaction.Status.RECHARGE_FAIL);
					loanNativeRepository.updateProceeds(loanId, investAmount.multiply(new BigDecimal(-1)));
				}
				cFCAOrderService.genCFCAOrder(response, invest, investAmount, serialNo, CFCAOrder.Type.BID);
			} else {
				String var = "投标操作：剩余金额不足。loanId=" + loanId + ",投标金额=" + investAmount.toString();
				Logger.info(var);
				backMap.put("code", "99");
				backMap.put("msg", var);
				loanNativeRepository.updateProceeds(loanId, investAmount.multiply(new BigDecimal(-1)));
			}
			apiLog.setDealFlag(ApiLog.DealResult.SUC);
		} catch (Exception e) {
			Logger.error("投标并支付出现异常"+e.getMessage());
		}

		apiLog.setResponseMessage(response.getResponsePlainText());
		apiLog.setResponseTime(new Date());
		apiLogService.saveApiLog(apiLog);

		return backMap;
	}

	/**
	 * 保存订单记录
	 * 
	 * @param response
	 * @param invest
	 * @param investAmount
	 * @param txSN
	 */
	@Transactional
	public void genCFCAOrder(Tx1361Response response, Invest invest, BigDecimal investAmount, String txSN) {
		CFCAOrder cfcaOrder = new CFCAOrder();
		cfcaOrder.setAmount(investAmount);
		cfcaOrder.setInvest(invest);
		cfcaOrder.setBankTxTime(response.getBankTxTime());
		cfcaOrder.setCode(response.getCode());
		cfcaOrder.setMessage(response.getMessage());
		cfcaOrder.setOrderNo(response.getOrderNo());
		cfcaOrder.setResponseCode(response.getResponseCode());
		cfcaOrder.setResponseMessage(response.getResponseMessage());
		cfcaOrder.setStatus(response.getStatus());
		cfcaOrder.setTxSN(txSN);
		cfcaOrder.setUser(invest.getUser());
		cfcaOrder.setType(CFCAOrder.Type.BID);
		cFCAOrderRepository.save(cfcaOrder);
	}

	/**
	 * 生成1361请求报文
	 * 
	 * @param investUser
	 * @param investAmount
	 * @param bankAccount
	 * @param userProperties
	 * @return
	 */
	public Tx1361Request buildTx1361Request(User investUser, BigDecimal investAmount, BankAccount bankAccount, UserProperties userProperties, String txSn) {
		Tx1361Request tx1361Request = new Tx1361Request();
		tx1361Request.setInstitutionID(App.config(HermesConstants.CFCA_INSTITUTION_ID_CODE));
		tx1361Request.setTxSN(txSn);
		tx1361Request.setOrderNo(App.config(HermesConstants.CFCA_MARKET_ORDER_NO_CODE));
		tx1361Request.setAmount(investAmount.multiply(new BigDecimal(100)).longValue());
		tx1361Request.setBankID(Dicts.name(bankAccount.getBank().getName(), "", P2ZJBank.class));
		tx1361Request.setAccountName(bankAccount.getName());
		tx1361Request.setAccountNumber(bankAccount.getAccount());
		tx1361Request.setBranchName(bankAccount.getDeposit());
		tx1361Request.setProvince(bankAccount.getCity().getName());
		tx1361Request.setAccountType(11);
		tx1361Request.setCity(bankAccount.getCity().getName());
		tx1361Request.setPhoneNumber(investUser.getCellphone());
		tx1361Request.setEmail(investUser.getEmail());
		tx1361Request.setIdentificationNumber(userProperties.getIdNumber());
		tx1361Request.setIdentificationType(Dicts.name(userProperties.getIdType(), P2ZJIdType.class));

		return tx1361Request;
	}

	public ApiLog recordApiLog(ApiConfig apiConfig, Map<String, String> map) {
		ApiLog apiLog = new ApiLog();
		apiLog.setApiConfig(apiConfig);
		apiLog.setCreator(HermesConstants.PLAT_MANAGER);
		apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
		apiLog.setRequestTime(new Date());
		apiLog.setDealFlag(ApiLog.DealResult.FAIL);// 默认
		if (!Strings.empty(map.get("interfaceMethod"))) {
			apiLog.setInterfaceName(map.get("interfaceMethod"));
		}
		if (!Strings.empty(map.get("requestMsg"))) {
			apiLog.setRequestMessage(map.get("requestMsg"));
		}
		if (!Strings.empty(map.get("serialNo"))) {
			apiLog.setSerialNo(map.get("serialNo"));
		}
		if (!Strings.empty(map.get("exception"))) {
			apiLog.setException(map.get("exception"));
		}
		try {
			return apiLogService.saveApiLog(apiLog);
		} catch (Exception e) {
			Logger.error("接口日志对象保存异常：", e);
			return null;
		}
	}

	/**
	 * 
	 * 单笔限额是否合法
	 * 
	 * @param investAmount
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Result isSingleLimitValid(BigDecimal investAmount) {
		Result result = new Result();

		AppUser curUser = App.current().getUser();
		User user = userRepository.findOne(curUser.getId());
		BankAccount bankAccount = bankAccountService.findOneByUserIdAndStatus(user.getId(), BankAccount.Status.ENABLED);
		PPLimit ppLimit = pPLimitRepository.findOneByPpOrgAndBank(PPOrg.ZJ.name(), bankAccount.getBank());

		if (investAmount.compareTo(ppLimit.getSingleLimit()) > 0) {
			result.setType(Type.FAILURE);
			result.addMessage(0, "超过单笔限额！");

			return result;
		}

		result.setType(Type.SUCCESS);
		return result;
	}

	/**
	 * 当日限额是否合法
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Result isDayLimitValid(BigDecimal investAmount) {
		Result result = new Result();

		AppUser curUser = App.current().getUser();
		User user = userRepository.findOne(curUser.getId());
		BankAccount bankAccount = bankAccountService.findOneByUserIdAndStatus(user.getId(), BankAccount.Status.ENABLED);
		PPLimit ppLimit = pPLimitRepository.findOneByPpOrgAndBank(PPOrg.ZJ.name(), bankAccount.getBank());

		Date startDate = DateUtils.truncate(new Date(), Calendar.DATE);
		Date endDate = DateUtils.addDays(startDate, 24);
		BigDecimal totalAmount = cFCAOrderRepository.countTodayTotalAmountByUser(user.getId(), startDate, endDate);

		if (ppLimit.getDayTotalLimit() != null && totalAmount.compareTo(ppLimit.getDayTotalLimit()) > 0) {
			result.setType(Type.FAILURE);
			result.addMessage(0, "超过日累计限额！");

			return result;
		}
		result.setType(Type.SUCCESS);
		return result;
	}

	/**
	 * 根据标+状态 获取理财信息
	 */
	@Override
	public List<Invest> findByLoanAndStatus(Loan loan, String status) throws Exception {
		return investRepository.findByLoanAndStatus(loan, status);
	}
	
	/**
	 * 根据借款获取理财信息
	 */
	@Override
	public List<InvestInfo> findInvestInfoByLoan(Loan loan) {
		List<Invest> investList = investRepository.findByLoan(loan);
		List<LoanRepay> loanRepayList = loanRepayRepository.findByLoan(loan);
		LoanRepay firstRepay = null;
		if(loanRepayList!=null && loanRepayList.size() > 0){
			firstRepay = loanRepayList.get(loanRepayList.size()-1);
		}
		List<InvestInfo> investInfoList = new ArrayList<InvestInfo>();
		InvestInfo investInfo = null;
		for (Invest invest : investList) {
			investInfo = new InvestInfo();
			UserProperties userPps = userPropertiesRepository.findByUserId(invest.getUser().getId());
			investInfo.setRealName(userPps.getRealName());
			investInfo.setAmount(invest.getAmount());
			if(firstRepay!=null){
				investInfo.setExpectProfit(Numbers.toCurrency(firstRepay.getAmount().multiply(invest.getRatio()).setScale(2, RoundingMode.HALF_EVEN)));
			}else{
				investInfo.setExpectProfit("0");
			}
			investInfoList.add(investInfo);
		}
		return investInfoList;
	}
	
	/**
	 * 获取理财信息
	 */
	@Override
	public List<InvestInfo> findInvestInfoByInvest(Invest invest) {
		List<LoanRepay> loanRepayList = loanRepayRepository.findByLoan(invest.getLoan());
		LoanRepay firstRepay = null;
		if(loanRepayList!=null && loanRepayList.size() > 0){
			firstRepay = loanRepayList.get(loanRepayList.size()-1);
		}
		List<InvestInfo> investInfoList = new ArrayList<InvestInfo>();
		InvestInfo investInfo = new InvestInfo();
		UserProperties userPps = userPropertiesRepository.findByUserId(invest.getUser().getId());
		investInfo.setRealName(userPps.getRealName());
		investInfo.setAmount(invest.getAmount());
		if(firstRepay!=null){
			investInfo.setExpectProfit(Numbers.toCurrency(firstRepay.getAmount().multiply(invest.getRatio()).setScale(2, RoundingMode.HALF_EVEN)));
		}else{
			investInfo.setExpectProfit("0");
		}
		investInfoList.add(investInfo);
		return investInfoList;
	}
}

package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.model.Loan.Status;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.repository.InvestRepository;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.repository.UserImageRepository;
import com.jlfex.hermes.repository.UserLogRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.CommonRepository.Script;
import com.jlfex.hermes.repository.n.LoanNativeRepository;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.pojo.InvestInfo;
import com.jlfex.hermes.service.pojo.LoanInfo;

/**
 * 
 * 理财业务实现
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
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

	/** 用户图片信息仓库 */
	@Autowired
	private UserImageRepository userImageRepository;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestService#findByUser(java.lang.String)
	 */

	// @Override
	// public List<Invest> findByUser(User user) {
	//
	// List<Invest> invests = investRepository.findByUser(user);
	// return invests;
	// }

	private String getCondition(String purpose, String raterange, String periodrange, String repay, String orderByField, String orderByDirection,String loanKind, Map<String, Object> params) {
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
		if (!Strings.empty(loanKind)) {
			condition.append(" and hl.loan_kind = :loanKind");
			params.put("loanKind", loanKind);
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
	public Page<LoanInfo> findByJointSql(String purpose, String raterange, String periodrange, String repay, String page, String size, String orderByField, String orderByDirection,String loanKind) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlSearchByLoan = commonRepository.readScriptFile(Script.searchByLoan);

		String sqlCountSearchByLoan = commonRepository.readScriptFile(Script.countSearchByLoan);
		String condition = getCondition(purpose, raterange, periodrange, repay, orderByField, orderByDirection,loanKind, params);
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
			loanInfo.setRate(Numbers.toPercent(new Double(String.valueOf(object[3]))));
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
	 * @see
	 * com.jlfex.hermes.service.InvestService#findByLoan(com.jlfex.hermes
	 * .model.Loan)
	 */
	@Override
	public List<Invest> findByLoan(Loan loan) {
		List<Invest> invests = investRepository.findByLoan(loan);
		return invests;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.InvestService#bid(java.lang.String,
	 * com.jlfex.hermes.model.User, java.math.BigDecimal, java.lang.String)
	 */
	@Override
	public boolean bid(String loanId, User investUser, BigDecimal investAmount, String otherRepay) {
		int updateRecord = loanNativeRepository.updateProceeds(loanId, investAmount);
		if (updateRecord == 1) {
			Loan loan = loanRepository.findOne(loanId);
			// 判断假如借款金额与已筹金额相等，更新状态为满标
			if (loan.getAmount().compareTo(loan.getProceeds()) == 0) {
				loan.setStatus(Loan.Status.FULL);
				loanRepository.save(loan);

				// 插入借款日志表(满标)
				LoanLog loanLog = new LoanLog();
				loanLog.setLoan(loan);
				loanLog.setUser(investUser.getId());
				loanLog.setDatetime(new Date());
				loanLog.setType(LoanLog.Type.FULL);
				loanLog.setAmount(investAmount);
				loanLogRepository.save(loanLog);
			}

			// 插入投资表
			Invest invest = new Invest();
			invest.setAmount(investAmount);
			invest.setOtherRepay(otherRepay);

			BigDecimal ratio = investAmount.divide(loan.getAmount(), 8, RoundingMode.HALF_DOWN);
			invest.setRatio(ratio);
			invest.setUser(investUser);
			invest.setLoan(loan);
			invest.setDatetime(new Date());
			invest.setStatus(Invest.Status.FREEZE);
			investRepository.save(invest);

			// 投标冻结
			transactionService.freeze(Transaction.Type.FREEZE, investUser.getId(), investAmount, loanId, "投标冻结");

			// 插入借款日志表
			LoanLog loanLog = new LoanLog();
			loanLog.setLoan(loan);
			loanLog.setUser(investUser.getId());
			loanLog.setDatetime(new Date());
			loanLog.setType(LoanLog.Type.INVEST);
			loanLog.setAmount(investAmount);
			loanLogRepository.save(loanLog);

			// 插入用户日志表
			UserLog userLog = new UserLog();
			userLog.setUser(investUser);
			userLog.setDatetime(new Date());
			userLog.setType(UserLog.LogType.INVEST);
			userLogRepository.save(userLog);
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestService#loadCountByUserAndStatus(com
	 * .jlfex.hermes.model.User, java.lang.String[])
	 */
	@Override
	public Long loadCountByUserAndStatus(User user, String... status) {
		return investRepository.loadCountByUserAndStatus(user, Arrays.asList(status));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestService#findSumProfitByUser(com.jlfex
	 * .hermes.model.User)
	 */
	// @Override
	// public List<Map<String, Object>> findSumProfitByUser(User user) {
	// List<Invest> investList = investRepository.findByUser(user);
	// List<Map<String, Object>> invests = getSumAttributes(investList);
	// return invests;
	// }
	//
	// private List<Map<String, Object>> getSumAttributes(List<Invest> invests)
	// {
	// List<Map<String, Object>> obj = new ArrayList<Map<String, Object>>();
	//
	// for (int i = 0; i < invests.size(); i++) {
	// Map<String, Object> attributes = new HashMap<String, Object>();
	// Logger.info("id: %s,amount :%s", invests.get(i).getId(),
	// invests.get(i).getAmount());
	// Invest invest = invests.get(i);
	// Loan loan = invest.getLoan();
	// attributes.put("id", invest.getId());
	// Dictionary dictionary = dictionaryRepository.findOne(loan.getPurpose());
	// attributes.put("purpose", dictionary.getName());
	// attributes.put("amount", invest.getAmount());
	// attributes.put("rate", loan.getRate());
	// attributes.put("period", loan.getPeriod());
	// BigDecimal amountSum =
	// investProfitRepository.loadAmountSumByInvest(invest);
	// if (amountSum == null)
	// amountSum = BigDecimal.ZERO;
	// attributes.put("amountSum", amountSum);
	// BigDecimal waitSum =
	// investProfitRepository.loadSumAllProfitAndPrincipalByInvestAndInStatus(invest,
	// Arrays.asList(InvestProfit.Status.WAIT));
	// if (waitSum == null)
	// waitSum = BigDecimal.ZERO;
	// attributes.put("waitSum", waitSum);
	// BigDecimal receivedSum =
	// investProfitRepository.loadSumAllProfitAndPrincipalByInvestAndInStatus(invest,
	// Arrays.asList(new String[] { InvestProfit.Status.ALREADY,
	// InvestProfit.Status.OVERDUE, InvestProfit.Status.ADVANCE }));
	// if (receivedSum == null)
	// receivedSum = BigDecimal.ZERO;
	// attributes.put("receivedSum", receivedSum);
	// attributes.put("statusname", invest.getStatusName());
	// obj.add(attributes);
	// }
	//
	// // 返回结果
	// return obj;
	// }

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
	public List<InvestInfo> findByUser(User user,String loanKind) {
		List<InvestInfo> investinfoList = new ArrayList<InvestInfo>();
		List<Invest> investList = investRepository.findByUserAndLoanKind(loanKind, user);
		InvestInfo investInfo = null;
		for (Invest invest : investList) {
			investInfo = new InvestInfo();
			investInfo.setId(invest.getId());
			investInfo.setPurpose(getDictionaryName(invest.getLoan().getPurpose()));
			investInfo.setRate(Numbers.toPercent(invest.getLoan().getRate().doubleValue()));
			investInfo.setAmount(invest.getAmount());
			investInfo.setPeriod(invest.getLoan().getPeriod());
			investInfo.setStatus(invest.getStatus());
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
			investinfoList.add(investInfo);
		}
		return investinfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestService#loadInvestRecordByUser(java.
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
			investInfo.setRate(Numbers.toPercent(invest.getLoan().getRate().doubleValue()));
			investInfo.setAmount(invest.getAmount());
			investInfo.setPeriod(invest.getLoan().getPeriod());
			investInfo.setStatus(invest.getStatusName());
			List<InvestProfit> investProfitList = investProfitRepository.findByInvest(invest);
			BigDecimal expectProfit = BigDecimal.ZERO;
			BigDecimal rate = invest.getLoan().getRate().divide(new BigDecimal(12), 10, RoundingMode.HALF_UP).multiply(new BigDecimal(invest.getLoan().getPeriod()));
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

}

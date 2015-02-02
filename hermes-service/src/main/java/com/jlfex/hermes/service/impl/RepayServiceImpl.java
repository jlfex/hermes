package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;










import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.support.spring.SpringWebApp;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanOverdue;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.InvestProfit.Status;
import com.jlfex.hermes.model.LoanLog.Type;
import com.jlfex.hermes.model.LoanRepay.RepayStatus;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.repository.LoanOverdueRepository;
import com.jlfex.hermes.repository.LoanRepayRepository;
import com.jlfex.hermes.repository.RepayRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.n.LoanNativeRepository;
import com.jlfex.hermes.repository.n.LoanRepayNativeRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.repay.RepayMethod;

/**
 * 
 * 还款方式业务实现
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
 */
@Service
@Transactional
public class RepayServiceImpl implements RepayService {

	/**
	 * 
	 */
	//@PersistenceContext
	//private EntityManager em;
	
	/** 还款方式信息仓库 */
	@Autowired
	private RepayRepository repayRepository;

	/** 还款计划信息仓库 */
	@Autowired
	private LoanRepayRepository loanRepayRepository;

	/** 还款计划信息仓库扩展 */
	@Autowired
	private LoanRepayNativeRepository loanRepayNativeRepository;

	/** 借款逾期信息仓库 */
	@Autowired
	private LoanOverdueRepository loanOverdueRepository;

	/** 理财收益信息仓库 */
	@Autowired
	private InvestProfitRepository investProfitRepository;

	/** 用户账户仓库 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/** 交易服务 */
	@Autowired
	private TransactionService transactionService;

	/** 借款日志仓库 */
	@Autowired
	private LoanLogRepository loanLogRepository;

	/** 借款信息仓库扩展 */
	@Autowired
	private LoanNativeRepository loanNativeRepository;
	@Autowired
	private CreditInfoService  creditInfoService;

	private static final String CACHE_LOAN_OVERDUE_PREFIX = "com.jlfex.hermes.cache.loan.overdue.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.RepayService#save(com.jlfex.hermes.model
	 * .Repay)
	 */
	@Override
	public Repay save(Repay repay) {
		// 保存数据并返回
		return repayRepository.save(repay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.RepayService#loadById(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Repay loadById(String id) {
		return repayRepository.findOne(id);
	}

	/**
	 * 此方法用于获取还款记录及最新一期待还
	 * 
	 * @param loan
	 * @return
	 */
	public List<LoanRepay> getloanRepayRecords(Loan loan) {
		List<LoanRepay> loanRepayList = findLoanRepay(loan);
		List<LoanRepay> loanRepayRecords = new ArrayList<LoanRepay>();
		int count = loanRepayList.size();
		// 判断假如有做过放款操作，即loan_repay表中有纪录
		if (count > 0) {
			LoanRepay unRepay = null;
			loanRepayRecords.add(unRepay);
			for (LoanRepay loanRepay : loanRepayList) {
				// 假如正常还款或者逾期还款，
				if (LoanRepay.RepayStatus.NORMAL.equals(loanRepay.getStatus()) || LoanRepay.RepayStatus.OVERDUE_REPAY.equals(loanRepay.getStatus())) {
					loanRepayRecords.add(loanRepay);
					count--;
				}
			}
			// 判断是否存在还未还款的 ，等于0表示全都还款(包括正常和逾期的)了
			if (count > 0) {
				// 获取当期未还
				unRepay = loanRepayList.get(count - 1);
			//	Date now = new Date();
//				// 如果计划时间小于等于当前时间，为正常还款
//				if (unRepay.getPlanDatetime().getTime() <= now.getTime()) {
//					// 计算逾期天数
//					int overdueDay = getOverdueDays(unRepay.getPlanDatetime(), now);
//					// 计算逾期等级
//					Integer rank = (overdueDay - 1) / 30 + 1;
//					LoanOverdue loanOverdue = loanOverdueRepository.findByLoanAndRank(loan, rank);
//					// 根据逾期等级获取费率，如获取为空，则表示坏账，获取为非空，采用获取费率计算
//					if (loanOverdue == null) {
//						loanOverdue = loanOverdueRepository.findByLoanAndRank(loan, 0);
//					}
//					RepayMethod repayMethod = getRepayMethod(loan.getRepay().getId());
//				
//					// 计算借款人逾期管理费
//					BigDecimal overduePenalty = repayMethod.getOverduePenalty(overdueDay, loan.getPeriod() - unRepay.getSequence() + 1, unRepay.getOtherAmount(), loanOverdue.getPenalty());
//					// 计算逾期罚息
//					BigDecimal overdueInterest = repayMethod.getOverdueInterest(overdueDay, loan.getPeriod() - unRepay.getSequence() + 1, unRepay.getAmount(), loanOverdue.getInterest());
//					
//					
//					unRepay.setOverdueDays(unRepay.getOverdueDays());
//					unRepay.setOverdueInterest(unRepay.getOverdueInterest());
//					unRepay.setOverduePenalty(unRepay.getOverduePenalty());
//					//em.clear();
//				}
				loanRepayRecords.set(0, unRepay);
			} else {
				loanRepayRecords.remove(0);
			}
			return loanRepayRecords;
		} else {
			return loanRepayRecords;
		}
	}

	/**
	 * 通过借款信息获取还款计划
	 */
	public List<LoanRepay> findLoanRepay(Loan loan) {
		return loanRepayRepository.findByLoan(loan);
	}

	/**
	 * 还款实现
	 * 
	 * @param loan
	 * @param sequence
	 */
	@Override
	@Transactional
	public boolean repayment(String id) {
		LoanRepay loanRepay = loanRepayRepository.findOne(id);
		// 判断假如借款还款状态为正常还款或者逾期还款，说明已经还过了，无须再还
		if (Strings.equals(loanRepay.getStatus(), RepayStatus.NORMAL) || Strings.equals(loanRepay.getStatus(), RepayStatus.OVERDUE_REPAY)) {
			return false;
		}
		// 取得借款还款的最大期数
		Integer loanMaxSequence = loanRepayRepository.findMaxSequenceByloan(loanRepay.getLoan());
		int success;
		Date now = new Date();
		Loan loan = loanRepay.getLoan();
		BigDecimal amount = BigDecimal.ZERO; // 总还款=总本金+总利息+管理费+逾期违约金+逾期罚息
		BigDecimal principal = BigDecimal.ZERO; // 总本金
		BigDecimal interest = BigDecimal.ZERO; // 总利息
		// 还月缴管理费
		transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.LOAN_MONTHLY_FEE, loanRepay.getOtherAmount(), loanRepay.getId(), "缴纳公司月缴管理费");
		// 如果状态为等待还款，为正常还款
		if (Strings.equals(loanRepay.getStatus(), RepayStatus.WAIT)) {
			List<InvestProfit> investProfitList = investProfitRepository.findByLoanRepay(loanRepay);
			for (InvestProfit investProfit : investProfitList) {
				// 还本金
				transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investProfit.getPrincipal(), investProfit.getId(), "正常还本金");
				principal = principal.add(investProfit.getPrincipal());
				// 还利息
				transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investProfit.getInterest(), investProfit.getId(), "正常还利息");
				interest = interest.add(investProfit.getInterest());
				// 更新理财收益表
				investProfit.setStatus(InvestProfit.Status.ALREADY);
				investProfitRepository.save(investProfit);
			}
			// 总还款=总本金+总利息+管理费
			// amount =
			// amount.add(principal).add(interest).add(loanRepay.getOtherAmount());

			// 总还款=总本金+总利息
			amount = amount.add(principal).add(interest);

			loanRepay.setRepayDatetime(now);
			loanRepay.setAmount(amount);
			loanRepay.setPrincipal(principal);
			loanRepay.setInterest(interest);
			loanRepay.setStatus(RepayStatus.NORMAL);
			// 更新还款计划表状态，从待还款 至 正常还款
			success = loanRepayNativeRepository.updateStatus(loanRepay, RepayStatus.WAIT);
			if (success != 1) {
				throw new ServiceException("normal repay fail!");
				// 未成功执行，回滚
				// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}

		} else if (Strings.equals(loanRepay.getStatus(), RepayStatus.OVERDUE)) {
			// 计算逾期天数
			int overdueDay = getOverdueDays(loanRepay.getPlanDatetime(), now);
			// 计算逾期等级
			Integer rank = (overdueDay - 1) / 30 + 1;
			LoanOverdue loanOverdue = loanOverdueRepository.findByLoanAndRank(loan, rank);
			// 根据逾期等级获取费率，如获取为空，则表示坏账，默认rank为0的表示坏账的费率，获取为非空，采用获取费率计算
			if (loanOverdue == null) {
				loanOverdue = loanOverdueRepository.findByLoanAndRank(loan, 0);
			}
			RepayMethod repayMethod = getRepayMethod(loan.getRepay().getId());
			// 计算借款人逾期违约金
			BigDecimal overduePenalty = repayMethod.getOverduePenalty(overdueDay, loan.getPeriod() - loanRepay.getSequence() + 1, loanRepay.getOtherAmount(), loanOverdue.getPenalty());
			// 计算逾期罚息
			BigDecimal overdueInterest = repayMethod.getOverdueInterest(overdueDay, loan.getPeriod() - loanRepay.getSequence() + 1, loanRepay.getAmount(), loanOverdue.getInterest());

			BigDecimal allInvestOverDueInterest = BigDecimal.ZERO;
			// 还逾期违约金
			transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.OVERDUE_FEE, overduePenalty, loanRepay.getId(), "还逾期管理费");
			List<InvestProfit> investProfitList = investProfitRepository.findByLoanRepay(loanRepay);
			for (InvestProfit investProfit : investProfitList) {
				BigDecimal investOverDueInterest = investProfit.getInvest().getRatio().multiply(overdueInterest).setScale(2, BigDecimal.ROUND_DOWN); // 单个人的逾期罚息=借款人的逾期罚息X占比（截取2位小数）
				// 假如此笔投标已经垫付，则还款还给垫付账户
				if (Status.ADVANCE.equals(investProfit.getStatus())) {
					transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.RISK, investProfit.getPrincipal(), loanRepay.getId(), "已垫付客户还本金到风险金");
					principal = principal.add(investProfit.getPrincipal());
					transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.RISK, investProfit.getInterest(), loanRepay.getId(), "已垫付客户还利息到风险金");
					interest = interest.add(investProfit.getInterest());
					transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.RISK, investOverDueInterest, loanRepay.getId(), "已垫付客户还预期罚息到风险金");
					allInvestOverDueInterest = allInvestOverDueInterest.add(investOverDueInterest);
				} else {
					// 还本金
					transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investProfit.getPrincipal(), investProfit.getId(), "还本金");
					principal = principal.add(investProfit.getPrincipal());
					// 还利息
					transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investProfit.getInterest(), investProfit.getId(), "还利息");
					interest = interest.add(investProfit.getInterest());
					// 还逾期罚息
					transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investOverDueInterest, investProfit.getId(), "还逾期罚息");
					allInvestOverDueInterest = allInvestOverDueInterest.add(investOverDueInterest);

					// 更新理财收益表
					investProfit.setAmount(amount);
					investProfit.setStatus(com.jlfex.hermes.model.InvestProfit.Status.ALREADY); // 状态更新为已收款
					investProfit.setOverdueInterest(overdueInterest); // 逾期利息
					investProfitRepository.save(investProfit);
				}
			}
			// 总还款=总本金+总利息+管理费+逾期违约金+逾期罚息
			amount = amount.add(principal).add(interest).add(loanRepay.getOtherAmount()).add(overduePenalty).add(overdueInterest);
			loanRepay.setRepayDatetime(now);
			loanRepay.setAmount(amount);
			loanRepay.setPrincipal(principal);
			loanRepay.setInterest(interest);
			loanRepay.setOverdueDays(overdueDay);
			loanRepay.setOverdueInterest(allInvestOverDueInterest);
			loanRepay.setOverduePenalty(overduePenalty);
			loanRepay.setStatus(RepayStatus.OVERDUE_REPAY);
			success = loanRepayNativeRepository.updateStatus(loanRepay, RepayStatus.OVERDUE);
			if (success != 1) {
				throw new ServiceException("overdue repay fail!");
				// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		// 判断假如借款最大还款期数等于该笔的还款期数,说明是最后一笔还款,改该笔借款的状态为完成
		if (loanMaxSequence == loanRepay.getSequence()) {
			success = loanNativeRepository.updateStatus(loanRepay.getLoan().getId(), Loan.Status.REPAYING, Loan.Status.COMPLETED);
			if (success != 1) {
				throw new ServiceException("loanNativeRepository updateStatus from REPAYING  to COMPLETED Error!");
			}
			// 插入借款日志
			saveLoanLog(loan.getUser().getId(), loanRepay.getLoan(), Type.COMPLETE, loanRepay.getAmount(), "借款完成");
			// 还款完成时 更新 债权
			if(Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getStatus())){
				updateCreditInfoFull(loan);
			}
		}
		// 插入借款日志
		saveLoanLog(loan.getUser().getId(), loanRepay.getLoan(), Type.REPAY, loanRepay.getAmount(), "借款还款");
		return true;
	}
	
	public void updateCreditInfoFull(Loan loan){
		if(loan != null && loan.getCreditInfoId() !=null){
			try {
				CrediteInfo creditInfo = creditInfoService.findById(loan.getCreditInfoId());
				creditInfo.setStatus(CrediteInfo.Status.REPAY_FIINISH);
				creditInfoService.save(creditInfo);
			} catch (Exception e) {
				Logger.error("更新债权状态为："+CrediteInfo.Status.REPAY_FIINISH+"。异常,债权标Id="+loan.getId());
			}
		}
	}

	/**
	 * 插入借款日志
	 * 
	 * @param user
	 * @param loan
	 * @param type
	 * @param amount
	 * @param remark
	 */
	public void saveLoanLog(String user, Loan loan, String type, BigDecimal amount, String remark) {
		LoanLog loanLog = new LoanLog();
		loanLog.setUser(user);
		loanLog.setLoan(loan);
		loanLog.setDatetime(new Date());
		loanLog.setType(type);
		loanLog.setAmount(amount);
		loanLog.setRemark(remark);
		loanLogRepository.save(loanLog);
	}

	/**
	 * 计算逾期天数 （ 逾期天数=计划还款日-当前日期）
	 * 
	 * @param repayDay
	 * @param edit_date
	 * @return
	 */
	public static int getOverdueDays(Date repayDay, Date now) {
		long temp = 0;
		if (repayDay.getTime() >= now.getTime()) {
			return 0;
		} else {
			temp = now.getTime() - repayDay.getTime();
		}
		return (int) (temp / 86400000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.RepayService#getRepayMethod(java.lang.String)
	 */
	@Override
	public RepayMethod getRepayMethod(String repayId) {
		Repay repay = repayRepository.findOne(repayId);
		if (repay == null)
			throw new ServiceException("repay is invaild.");

		try {
			return SpringWebApp.getBean(repay.getCode(), RepayMethod.class);
		} catch (Exception e) {
			try {
				return RepayMethod.class.cast(Class.forName(repay.getClazz()).newInstance());
			} catch (InstantiationException ex) {
				throw new ServiceException("cannot get class instance '" + repay.getClazz() + "'", ex);
			} catch (IllegalAccessException ex) {
				throw new ServiceException("cannot get class instance '" + repay.getClazz() + "'", ex);
			} catch (ClassNotFoundException ex) {
				throw new ServiceException("cannot get class instance '" + repay.getClazz() + "'", ex);
			}
		}
	}

	@Override
	public List<Repay> findAll() {
		return repayRepository.findAll();
	}

	@Override
	public boolean updateStatus(String id, String fromStatus, String toStatus) {
		int success = loanRepayNativeRepository.updateStatus(id, fromStatus, toStatus);
		if (success == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<LoanRepay> findByPlanDatetimeBetweenAndStatus(Date startPlanDatetime, Date endPlanDatetime, String status) {
		return loanRepayRepository.findByPlanDatetimeBetweenAndStatus(startPlanDatetime, endPlanDatetime, status);
	}

	@Override
	public List<LoanRepay> findByStatus(String status) {
		return loanRepayRepository.findByStatus(status);
	}

	@Override
	public boolean overdueCalc(LoanRepay loanRepay) {
		if (!Strings.equals(loanRepay.getStatus(), RepayStatus.OVERDUE)) {
			return false;
		}
		Date now = new Date();
		int success;
		Loan loan = loanRepay.getLoan();
		// 计算逾期天数
		int overdueDay = getOverdueDays(loanRepay.getPlanDatetime(), now);
		// 计算逾期等级
		Integer rank = (overdueDay - 1) / 30 + 1;
		if (Caches.get(CACHE_LOAN_OVERDUE_PREFIX + loanRepay.getId() + "." + rank) == null) {
			LoanOverdue loanOverdue = loanOverdueRepository.findByLoanAndRank(loan, rank);
			// 根据逾期等级获取费率，如获取为空，则表示坏账，默认rank为0的表示坏账的费率，获取为非空，采用获取费率计算
			if (loanOverdue == null) {
				loanOverdue = loanOverdueRepository.findByLoanAndRank(loan, 0);
			}
			Caches.set(CACHE_LOAN_OVERDUE_PREFIX + loanRepay.getId() + "." + rank, loanOverdue, "1d");
		}
		RepayMethod repayMethod = getRepayMethod(loan.getRepay().getId());
		LoanOverdue loanOverdue = Caches.get(CACHE_LOAN_OVERDUE_PREFIX + loanRepay.getId() + "." + rank, LoanOverdue.class);
		if (loanOverdue != null) {
			// 计算借款人逾期管理费
			BigDecimal overduePenalty = repayMethod.getOverduePenalty(overdueDay, loan.getPeriod() - loanRepay.getSequence() + 1, loanRepay.getOtherAmount(), loanOverdue.getPenalty());
			// 计算逾期罚息
			BigDecimal overdueInterest = repayMethod.getOverdueInterest(overdueDay, loan.getPeriod() - loanRepay.getSequence() + 1, loanRepay.getAmount(), loanOverdue.getInterest());
			BigDecimal allInvestOverDueInterest = BigDecimal.ZERO;
			List<InvestProfit> investProfitList = investProfitRepository.findByLoanRepay(loanRepay);
			for (InvestProfit investProfit : investProfitList) {
				BigDecimal investOverDueInterest = investProfit.getInvest().getRatio().multiply(overdueInterest).setScale(2, BigDecimal.ROUND_DOWN); // 单个人的逾期罚息=借款人的逾期罚息X占比（截取2位小数）
				allInvestOverDueInterest = allInvestOverDueInterest.add(investOverDueInterest);
			}
			loanRepay.setOverdueDays(overdueDay);
			loanRepay.setOverdueInterest(allInvestOverDueInterest);
			loanRepay.setOverduePenalty(overduePenalty);
			success = loanRepayNativeRepository.overdueCalc(loanRepay, RepayStatus.OVERDUE);
			if (success != 1) {
				throw new ServiceException("overdue calc fail!");
			} else {
				return true;
			}
		} else {
			return false;
		}

	}

	@Override
	public boolean autoRepayment(String id) {
		LoanRepay loanRepay = loanRepayRepository.findOne(id);
		// 判断假如借款还款状态为正常还款说明已经还过了，无须再还
		if (Strings.equals(loanRepay.getStatus(), RepayStatus.NORMAL)) {
			return false;
		}
		// 取得借款还款的最大期数
		Integer loanMaxSequence = loanRepayRepository.findMaxSequenceByloan(loanRepay.getLoan());
		int success;
		Date now = new Date();
		Loan loan = loanRepay.getLoan();
		BigDecimal amount = BigDecimal.ZERO; // 总还款=总本金+总利息+管理费+逾期违约金+逾期罚息
		BigDecimal principal = BigDecimal.ZERO; // 总本金
		BigDecimal interest = BigDecimal.ZERO; // 总利息
		// 还月缴管理费
		transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.LOAN_MONTHLY_FEE, loanRepay.getOtherAmount(), loanRepay.getId(), "自动还款_缴纳公司月缴管理费");
		// 如果状态为等待还款，为正常还款
		if (Strings.equals(loanRepay.getStatus(), RepayStatus.WAIT)) {
			List<InvestProfit> investProfitList = investProfitRepository.findByLoanRepay(loanRepay);
			for (InvestProfit investProfit : investProfitList) {
				// 还本金
				transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investProfit.getPrincipal(), investProfit.getId(), "自动还款_正常还本金");
				principal = principal.add(investProfit.getPrincipal());
				// 还利息
				transactionService.transact(Transaction.Type.OUT, loan.getUser(), investProfit.getUser(), investProfit.getInterest(), investProfit.getId(), "自动还款_正常还利息");
				interest = interest.add(investProfit.getInterest());
				// 更新理财收益表
				investProfit.setStatus(InvestProfit.Status.ALREADY);
				investProfitRepository.save(investProfit);
			}
			// 总还款=总本金+总利息
			amount = amount.add(principal).add(interest);
			loanRepay.setRepayDatetime(now);
			loanRepay.setAmount(amount);
			loanRepay.setPrincipal(principal);
			loanRepay.setInterest(interest);
			loanRepay.setStatus(RepayStatus.NORMAL);
			// 更新还款计划表状态，从待还款 至 正常还款
			success = loanRepayNativeRepository.updateStatus(loanRepay, RepayStatus.WAIT);
			if (success != 1) {
				throw new ServiceException("auto normal repay fail!");
				// 未成功执行，回滚
				// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}

		}
		// 判断假如借款最大还款期数等于该笔的还款期数,说明是最后一笔还款,改该笔借款的状态为完成
		if (loanMaxSequence == loanRepay.getSequence()) {
			success = loanNativeRepository.updateStatus(loanRepay.getLoan().getId(), Loan.Status.REPAYING, Loan.Status.COMPLETED);
			if (success != 1) {
				throw new ServiceException("loanNativeRepository updateStatus from REPAYING  to COMPLETED Error!");
			}
			// 插入借款日志
			saveLoanLog(loan.getUser().getId(), loanRepay.getLoan(), Type.COMPLETE, loanRepay.getAmount(), "借款自动还款完成");
		}
		// 插入借款日志
		saveLoanLog(loan.getUser().getId(), loanRepay.getLoan(), Type.REPAY, loanRepay.getAmount(), "借款自动还款");
		return true;
	}
    /**
     * 根据名称 和 状态   获取还款方式
     */
	@Override
	public List<Repay> findByNameAndStatusIn(String name, String... status) throws Exception {
		return repayRepository.findByNameAndStatusIn(name, Arrays.asList(status));
	}
}

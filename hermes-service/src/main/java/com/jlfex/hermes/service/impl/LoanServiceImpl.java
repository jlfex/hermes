package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.dict.Bool;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Label;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.Loan.Status;
import com.jlfex.hermes.model.LoanAudit;
import com.jlfex.hermes.model.LoanAuth;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanLog.Type;
import com.jlfex.hermes.model.LoanOverdue;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.LoanRepay.RepayStatus;
import com.jlfex.hermes.model.Model;
import com.jlfex.hermes.model.ProductOverdue;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserAddress;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserEducation;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.CommonRepository.Script;
import com.jlfex.hermes.repository.CreditInfoRepository;
import com.jlfex.hermes.repository.CreditorRepayPlanRepository;
import com.jlfex.hermes.repository.CreditorRepository;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.repository.InvestRepository;
import com.jlfex.hermes.repository.LabelRepository;
import com.jlfex.hermes.repository.LoanAuditReository;
import com.jlfex.hermes.repository.LoanAuthRepository;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.repository.LoanOverdueRepository;
import com.jlfex.hermes.repository.LoanRepayRepository;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.repository.ProductOverdueRepository;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserAddressRepository;
import com.jlfex.hermes.repository.UserCarRepository;
import com.jlfex.hermes.repository.UserEducationRepository;
import com.jlfex.hermes.repository.UserHouseRepository;
import com.jlfex.hermes.repository.UserJobRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.n.LoanNativeRepository;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.common.Query;
import com.jlfex.hermes.service.pojo.LoanAuditInfo;
import com.jlfex.hermes.service.pojo.LoanInfo;
import com.jlfex.hermes.service.pojo.LoanStatusCount;
import com.jlfex.hermes.service.pojo.LoanUserBasic;
import com.jlfex.hermes.service.pojo.LoanUserInfo;
import com.jlfex.hermes.service.repay.RepayMethod;

/**
 * 
 * 借款业务实现
 * 
 * @author Ray
 * @version 1.0, 2013-12-23
 * @since 1.0
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

	/** 当前日期 */
	private static String today;

	private final static String CACHE_LOAN_SEQUENCE = "com.jlfex.cache.loansequence";

	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;

	/** 借款信息仓库 */
	@Autowired
	private LoanRepository loanRepository;

	/** 借款逾期信息仓库 */
	@Autowired
	private LoanOverdueRepository loanOverdueRepository;

	/** 产品逾期信息仓库 */
	@Autowired
	private ProductOverdueRepository productOverdueRepository;

	/** 借款信息仓库扩展 */
	@Autowired
	private LoanNativeRepository loanNativeRepository;

	/** 借款日志仓库 */
	@Autowired
	private LoanLogRepository loanLogRepository;

	/** 还款计划信息仓库 */
	@Autowired
	private LoanRepayRepository loanRepayRepository;

	/** 理财仓库 */
	@Autowired
	private InvestRepository investRepository;

	/** 理财收益信息仓库 */
	@Autowired
	private InvestProfitRepository investProfitRepository;

	/** 字典信息仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;

	/** 用户业务接口 */
	@Autowired
	private UserService userService;

	/** 还款方式业务接口 */
	@Autowired
	private RepayService repayServiceImpl;

	/** 用户账户业务接口 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/** 用户账户业务接口 */
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 用户业务接口 */
	@Autowired
	private UserRepository userRepository;

	/** 用户工作业务接口 */
	@Autowired
	private UserJobRepository userJobRepository;
	/** 用户学历 */
	@Autowired
	private UserEducationRepository userEducationRepository;

	@Autowired
	private UserHouseRepository userHouseRepository;

	@Autowired
	private UserCarRepository userCarRepository;

	/** 用户地址 */
	@Autowired
	private UserAddressRepository userAddressRepository;

	/** 审核日志仓库 */
	@Autowired
	private LoanAuditReository loanAuditReository;

	/** 交易服务 */
	@Autowired
	private TransactionService transactionService;

	/** 借款认证仓库 */
	@Autowired
	private LoanAuthRepository loanAuthRepository;

	/** 标签仓库 */
	@Autowired
	private LabelRepository labelRepository;

	/** 利率仓库 */
	@Autowired
	private RateRepository rateRepository;
	/** 债权信息仓库 */
	@Autowired
	private CreditInfoRepository creditInfoRepository;
	/** 债权还款明细 信息仓库 */
	@Autowired
	private CreditorRepayPlanRepository creditorRepayPlanRepository;
	/** 债权人 信息仓库 */
	@Autowired
	private CreditorRepository creditorRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.LoanService#findAll()
	 */
	@Override
	public List<Loan> findAll() {
		return loanRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.LoanService#countByStatus(java.lang.String[])
	 */
	@Override
	@Transactional(readOnly = true)
	public List<LoanStatusCount> countByStatus(String... status) {
		// 初始化
		List<LoanStatusCount> counts = new LinkedList<LoanStatusCount>();
		List<Object> data = new ArrayList<Object>();
		Map<String, Object> params = new HashMap<String, Object>();

		// 查询数据并处理
		data.addAll(commonRepository.findByNativeFile(Script.countByLoanOverdue, params));
		params.put("status", Arrays.asList(status));
		data.addAll(commonRepository.findByNativeFile(Script.countByLoanStatus, params));
		for (Object obj : data) {
			Object[] objs = Object[].class.cast(obj);
			LoanStatusCount count = new LoanStatusCount();
			count.setStatus(String.class.cast(objs[0]));
			count.setCount(Number.class.cast(objs[1]).longValue());
			counts.add(count);
		}

		// 返回结果
		return counts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.LoanService#countByStatus()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<LoanStatusCount> countByStatus() {
		return countByStatus(Loan.Status.AUDIT_FIRST, Loan.Status.AUDIT_FINAL, Loan.Status.FULL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.LoanService#loadById(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Loan loadById(String id) {
		return loanRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.LoanService#findByStatus(java.lang.Integer,
	 * java.lang.String[])
	 */
	@Override
	public List<Loan> findByStatus(Integer limit, String... status) {
		// 验证数据有效性
		Assert.notNull(status, "status is null.");

		// 分页查询数据
		Pageable pageable = new PageRequest(0, limit, Direction.DESC, "datetime");
		Page<Loan> page = loanRepository.findByStatusIn(Arrays.asList(status), pageable);

		// 返回结果
		return page.getContent();
	}

	@Override
	public List<Loan> findByKindAndStatus(Integer limit, String loanKind, String... status) {
		// 验证数据有效性
		if (status == null || status.length == 0) {
			throw new ServiceException("状态为空");
		}
		// 分页查询数据
		Pageable pageable = new PageRequest(0, limit, Direction.DESC, "datetime");
		Page<Loan> page = loanRepository.findByloanKindAndStatusIn(loanKind, Arrays.asList(status), pageable);

		// 返回结果
		return page.getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.LoanService#findForIndex()
	 */
	@Override
	public List<LoanInfo> findForIndex(String loanKind) {
		// 初始化
		Integer size = Integer.valueOf(App.config("index.loan.size", "10"));
		List<LoanInfo> loans = new ArrayList<LoanInfo>(size);

		// 查询招标中记录
		loans.addAll(toInfos(findByKindAndStatus(size, loanKind, Loan.Status.BID)));
		Logger.debug("find %d loans by status bid.", loans.size());

		// 判断是否已经满足记录条数
		// 当不满足条数要求时查询以已完成记录补充
		if (loans.size() < size) {
			loans.addAll(toInfos(findByKindAndStatus(size - loans.size(), loanKind, Loan.Status.COMPLETED)));
		}
		// 返回结果
		return loans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.LoanService#findByLoanNoAndUserNameAndAmountRange
	 * (java.lang.String, java.lang.String, java.math.BigDecimal,
	 * java.math.BigDecimal)
	 */
	@Override
	public List<Loan> findByLoanNoAndUserNameAndAmountRange(String loanNo, String userName, BigDecimal minAmount, BigDecimal maxAmount) {
		return null;
	}

	@Override
	public Loan audit(String id, String remark, BigDecimal amount, boolean isPass) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 满标处理
	 */
	@Override
	public Loan loanOut(String id, String remark, boolean isPass) throws Exception {
		Logger.info("满标处理开始：loan_id =" + id + ", 处理方式：isPass=" + isPass + ",remark=" + remark);
		Loan loan = loanRepository.findOne(id);
		if (isPass) {
			int success = loanNativeRepository.updateStatus(id, Status.FULL, Status.REPAYING); // 借款状态更新:
																								// 满标
																								// to
																								// 还款中
			if (success > 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("lendingDate", Calendars.format("yyyy-MM-dd", new Date()));
				List<Invest> investList = new ArrayList<Invest>();
				List<InvestProfit> investProfitList = new ArrayList<InvestProfit>();
				if (Loan.LoanKinds.NORML_LOAN.equals(loan.getLoanKind())) {
					Map<String, List> resultMap = normalLoanDeal(loan, map);
					if (resultMap == null) {
						throw new Exception("满标处理:普通标loanid=" + loan.getId() + ", 处理异常");
					}
					investList = resultMap.get("resultMap");
					investProfitList = resultMap.get("investProfitList");
				} else if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
					Map<String, List> resultMap = outCreditDeal(loan);
					if (resultMap == null) {
						throw new Exception("满标处理:债权标loanid=" + loan.getId() + ", 处理异常");
					}
					investList = resultMap.get("resultMap");
					investProfitList = resultMap.get("investProfitList");
				} else {
					throw new Exception("id=" + loan.getId() + ",无效的标类型：loan_kind=" + loan.getLoanKind());
				}
				Map<String, String> resultMap = loanOutfeeToPlat(loan);
				String remark_loan = "借款金额 :" + loan.getAmount() + ",借款手续费  :" + resultMap.get("loanFee") + ",风险金管理费  :" + resultMap.get("riskFee");
				recordOutLoanLog(loan, LoanLog.Type.LOAN, remark_loan);
				investProfitRepository.save(investProfitList);
				investRepository.save(investList);
			} else {
				throw new ServiceException("借款状态更新: 满标 to还款中异常");
			}
		} else {
			// 借款状态： 满标 to 满标流标
			int success = loanNativeRepository.updateStatus(id, Status.FULL, Status.FAILURE_FULL);
			if (success > 0) {
				// 记录借款日志--满标流标
				recordOutLoanLog(loan, LoanLog.Type.FULL_FAILURE, "满标流标");
				List<Invest> investList = investRepository.findByLoan(loan);
				// 解冻
				for (Invest invest : investList) {
					invest.setStatus(Invest.Status.FAILURE);
					transactionService.unfreeze(Transaction.Type.UNFREEZE, invest.getUser(), invest.getAmount(), invest.getId(), "满标流标解冻投标金额");
				}
				investRepository.save(investList);
			} else {
				throw new ServiceException("满标流标失败");
			}
		}
		return loan;
	}

	/**
	 * 普通标满标: 生成还款计划、收益计划、转账
	 * 
	 * @param loan
	 * @param paramMap
	 * @return
	 */
	public Map<String, List> normalLoanDeal(Loan loan, Map<String, String> paramMap) {
		Map<String, List> resultMap = new HashMap<String, List>();
		List<LoanRepay> loanRepayList = new ArrayList<LoanRepay>();
		List<Invest> investList = new ArrayList<Invest>();
		List<InvestProfit> investProfitList = new ArrayList<InvestProfit>();
		// 1: 生成借款还款计划
		loanRepayList = repayServiceImpl.getRepayMethod(loan.getRepay().getId()).generatePlan(loan, paramMap);
		loanRepayRepository.save(loanRepayList);
		// 2: 生成理财收益计划
		investList = investRepository.findByLoan(loan);
		InvestProfit investProfit = null;
		for (Invest invest : investList) {
			invest.setStatus(Invest.Status.COMPLETE);
			// 解冻
			transactionService.unfreeze(Transaction.Type.UNFREEZE, invest.getUser(), invest.getAmount(), invest.getId(), "放款解冻投标金额");
			// 转账
			transactionService.transact(Transaction.Type.OUT, invest.getUser(), loan.getUser(), invest.getAmount(), invest.getId(), "放款理财人到借款人");
			for (LoanRepay loanRepay : loanRepayList) {
				investProfit = new InvestProfit();
				investProfit.setUser(invest.getUser());
				investProfit.setInvest(invest);
				investProfit.setLoanRepay(loanRepay);
				investProfit.setDate(loanRepay.getPlanDatetime());
				investProfit.setAmount(loanRepay.getAmount().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
				investProfit.setPrincipal(loanRepay.getPrincipal().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
				investProfit.setInterest(loanRepay.getInterest().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
				investProfit.setOverdueInterest(loanRepay.getOverdueInterest().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
				investProfit.setStatus(InvestProfit.Status.WAIT);
				investProfitList.add(investProfit);
			}
		}
		resultMap.put("investProfitList", investProfitList);
		resultMap.put("investList", investList);
		return resultMap;
	}

	/**
	 * 债权标满标: 生成还款计划、收益计划、转账
	 * 
	 * @param loan
	 * @return
	 */
	public Map<String, List> outCreditDeal(Loan loan) {
		Map<String, List> resultMap = new HashMap<String, List>();
		List<LoanRepay> loanRepayList = new ArrayList<LoanRepay>();
		List<Invest> investList = new ArrayList<Invest>();
		List<InvestProfit> investProfitList = new ArrayList<InvestProfit>();
		CrediteInfo creditInfo = creditInfoRepository.findOne(loan.getCreditInfoId());
		creditInfo.setStatus(CrediteInfo.Status.REPAYING); // 更新还款中
		creditInfo = creditInfoRepository.save(creditInfo);
		List<CreditRepayPlan> creditRepayPlanList = creditorRepayPlanRepository.findByCreditInfoAscPeriod(creditInfo);
		// 1: 生成借款还款计划
		for (CreditRepayPlan plan : creditRepayPlanList) {
			LoanRepay loanRepay = new LoanRepay();
			loanRepay.setLoan(loan);
			loanRepay.setSequence(plan.getPeriod());
			loanRepay.setPlanDatetime(plan.getRepayPlanTime());
			loanRepay.setAmount(plan.getRepayAllmount());
			loanRepay.setPrincipal(plan.getRepayPrincipal());
			loanRepay.setInterest(plan.getRepayInterest());
			loanRepay.setOtherAmount(BigDecimal.ZERO); // 月缴管理费
			loanRepay.setOverdueDays(0);
			loanRepay.setOverdueInterest(BigDecimal.ZERO);
			loanRepay.setOverduePenalty(BigDecimal.ZERO);
			loanRepay.setStatus(plan.getStatus());
			loanRepayList.add(loanRepay);
		}
		loanRepayRepository.save(loanRepayList);
		// 2:生成理财收益计划
		investList = investRepository.findByLoan(loan);
		InvestProfit investProfit = null;
		for (Invest invest : investList) {
			invest.setStatus(Invest.Status.COMPLETE);
			// 解冻
			transactionService.unfreeze(Transaction.Type.UNFREEZE, invest.getUser(), invest.getAmount(), invest.getId(), "放款解冻投标金额");
			// 转账
			transactionService.transact(Transaction.Type.OUT, invest.getUser(), loan.getUser(), invest.getAmount(), invest.getId(), "放款理财人到借款人");
			for (LoanRepay loanRepay : loanRepayList) {
				if (LoanRepay.RepayStatus.WAIT.equals(loanRepay.getStatus())) {
					investProfit = new InvestProfit();
					investProfit.setUser(invest.getUser());
					investProfit.setInvest(invest);
					investProfit.setLoanRepay(loanRepay);
					investProfit.setDate(loanRepay.getPlanDatetime());
					investProfit.setAmount(loanRepay.getAmount().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
					investProfit.setPrincipal(loanRepay.getPrincipal().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
					investProfit.setInterest(loanRepay.getInterest().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
					investProfit.setOverdueInterest(loanRepay.getOverdueInterest().multiply(invest.getRatio().setScale(2, RoundingMode.HALF_UP)));
					investProfit.setStatus(InvestProfit.Status.WAIT);
					investProfitList.add(investProfit);
				}
			}
		}
		resultMap.put("investProfitList", investProfitList);
		resultMap.put("investList", investList);
		return resultMap;
	}

	/**
	 * 满标：平台收取 借款手续费 、风险金
	 * 
	 * @param loan
	 */
	public Map<String, String> loanOutfeeToPlat(Loan loan) {
		// 获取: 借款利率 、风险金
		Map<String, String> resultMap = new HashMap<String, String>();
		Rate rateLoan = rateRepository.findByProductAndType(loan.getProduct(), Rate.RateType.LOAN);
		Rate rateRisk = rateRepository.findByProductAndType(loan.getProduct(), Rate.RateType.RISK);
		BigDecimal loanFee = BigDecimal.ZERO;
		BigDecimal riskFee = BigDecimal.ZERO;
		// 平台账户 收取 借款手续费
		if (rateLoan == null) {
			throw new ServiceException("借款手续费为空");
		}
		if (rateRisk == null) {
			throw new ServiceException("风险金费用为空");
		}
		loanFee = (loan.getAmount().multiply(rateLoan.getRate())).setScale(2, RoundingMode.HALF_UP);
		riskFee = (loan.getAmount().multiply(rateRisk.getRate())).setScale(2, RoundingMode.HALF_UP);
		// 平台账户 收取 借款手续费
		transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.LOAN_FEE, loanFee, loan.getId(), "借款手续费扣除");
		// 平台账户 收取 风险金费用
		transactionService.toCropAccount(Transaction.Type.OUT, loan.getUser(), UserAccount.Type.RISK, riskFee, loan.getId(), "风险金管理费扣除");
		resultMap.put("loanFee", loanFee.toString());
		resultMap.put("riskFee", riskFee.toString());
		return resultMap;
	}

	/**
	 * 满标：记录 日志
	 * 
	 * @param loan
	 * @param loanFee
	 * @param riskFee
	 */
	public void recordOutLoanLog(Loan loan, String status, String remark) {
		LoanLog loanLog = new LoanLog();
		loanLog.setUser(loan.getUser().getId());
		loanLog.setLoan(loan);
		loanLog.setDatetime(new Date());
		loanLog.setType(status);
		loanLog.setAmount(loan.getAmount());
		loanLog.setRemark(remark);
		loanLogRepository.save(loanLog);
	}

	@Override
	public Loan demand(String id, String date, String type, String remark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanLog> findLogByLoanId(String loanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanLog> findLogByLoanIdAndType(String... type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanLog loadLogByLoanIdAndType(String loanId, String type) {
		return loanLogRepository.findByLoanIdAndType(loanId, type);
	}

	/**
	 * 前台借款生成
	 */
	@Override
	public Loan save(Loan loan) throws Exception {
		Date now = new Date();
		// 借款编号生成策略
		if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
			// 债权标
			loan.setLoanNo(generateLoanNo());
		} else {
			// 普通标
			loan.setLoanNo(generateLoanNo());
			loan.setStatus(Loan.Status.AUDIT_FIRST);
			loan.setProceeds(BigDecimal.ZERO);
			loan.setDeadline(loan.getProduct().getDeadline());
			loan.setManageFee(loan.getProduct().getManageFee());
			loan.setManageFeeType(loan.getProduct().getManageFeeType());
		}
		// 借款保存
		loan.setDatetime(now);
		Loan reloan = loanRepository.save(loan);

		// 普通标： 通过借款获取产品逾期等级信息
		if (Loan.LoanKinds.NORML_LOAN.equals(loan.getLoanKind())) {
			List<ProductOverdue> productOverdueList = productOverdueRepository.findByProduct(loan.getProduct());
			List<LoanOverdue> loanOverdueList = new ArrayList<LoanOverdue>(productOverdueList.size());
			LoanOverdue loanOverdue = null;
			for (ProductOverdue productOverdue : productOverdueList) {
				loanOverdue = new LoanOverdue();
				loanOverdue.setLoan(reloan);
				loanOverdue.setRank(productOverdue.getRank());
				loanOverdue.setInterest(productOverdue.getInterest());
				loanOverdue.setPenalty(productOverdue.getPenalty());
				loanOverdueList.add(loanOverdue);
			}
			// 将产品逾期信息转换后绑定到借款逾期信息上
			loanOverdueRepository.save(loanOverdueList);
		}
		// 记录借款日志
		LoanLog loanLog = new LoanLog();
		loanLog.setUser(loan.getUser().getId());
		loanLog.setLoan(reloan);
		loanLog.setDatetime(now);
		if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
			loanLog.setType(Type.START_INVEST);
		} else {
			loanLog.setType(Type.RELEASE);
		}
		loanLog.setAmount(loan.getAmount());
		// 由于已经生成借款，故将理财人变更为借款人
		UserProperties up = userPropertiesRepository.findByUser(loan.getUser());
		if (!Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
			// 普通标更新 用户属性为 借款人
			up.setIsMortgagor(Bool.TRUE);
			userPropertiesRepository.save(up);
		}
		// 记录借款日志
		loanLogRepository.save(loanLog);
		return reloan;
	}

	/**
	 * 转换对象
	 * 
	 * @param loan
	 * @return
	 */
	protected LoanInfo toInfo(Loan loan) {
		LoanInfo info = new LoanInfo();

		info.setId(loan.getId());
		info.setInvester(loan.getUser().getAccount());
		info.setAvatar(userService.getAvatar(loan.getUser(), UserImage.Type.AVATAR));
		if (Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())) {
			String purpose = loan.getPurpose();
			info.setPurpose((purpose != null && purpose.length() > 4) ? (purpose.substring(0, 4) + "...") : purpose);
		} else {
			info.setPurpose(getDictionaryName(loan.getPurpose()));
		}
		info.setRate(Numbers.toPercent(loan.getRate().doubleValue()));
		info.setAmount(Numbers.toCurrency(loan.getAmount().doubleValue()));
		info.setPeriod(String.valueOf(loan.getPeriod()));
		info.setRemain(loan.getRemain());
		info.setProgress(String.valueOf(loan.getProgress()));
		info.setStatus(Strings.equals(Loan.Status.BID, loan.getStatus()) ? LoanInfo.Status.BID : LoanInfo.Status.COMPLETED);

		return info;
	}

	/**
	 * 转换对象
	 * 
	 * @param loans
	 * @return
	 */
	protected List<LoanInfo> toInfos(List<Loan> loans) {
		List<LoanInfo> infos = new ArrayList<LoanInfo>(loans.size());
		for (Loan loan : loans)
			infos.add(toInfo(loan));
		return infos;
	}

	/**
	 * 读取字典名称
	 * 
	 * @param id
	 * @return
	 */
	protected String getDictionaryName(String id) {
		Dictionary dictionary = dictionaryRepository.findOne(id);
		if (dictionary == null)
			return null;
		return dictionary.getName();
	}

	/**
	 * 通过用户读取借款信息列表
	 * 
	 * @param user
	 */
	@Override
	public List<LoanInfo> findByUser(User user) {
		List<LoanInfo> loaninfoList = new ArrayList<LoanInfo>();
		List<Loan> loanList = loanRepository.findByUser(user);
		LoanInfo loanInfo = null;
		for (Loan loan : loanList) {
			loanInfo = new LoanInfo();
			loanInfo.setId(loan.getId());
			loanInfo.setPurpose(getDictionaryName(loan.getPurpose()));
			loanInfo.setRate(Numbers.toPercent(loan.getRate().doubleValue()));
			loanInfo.setAmount(Numbers.toCurrency(loan.getAmount().doubleValue()));
			loanInfo.setPeriod(loan.getPeriod().toString());
			loanInfo.setStatus(loan.getStatus());
			List<LoanRepay> loanRepayList = loanRepayRepository.findByLoan(loan);
			BigDecimal repayPI = BigDecimal.ZERO;
			BigDecimal repayedPI = BigDecimal.ZERO;
			BigDecimal unRepayPI = BigDecimal.ZERO;
			for (LoanRepay loanRepay : loanRepayList) {
				repayPI = repayPI.add(loanRepay.getAmount());
				// 等待还款
				if (RepayStatus.WAIT.equals(loanRepay.getStatus()) || RepayStatus.OVERDUE.equals(loanRepay.getStatus())) {
					unRepayPI = unRepayPI.add(loanRepay.getAmount());
				} else {
					repayedPI = repayedPI.add(loanRepay.getPrincipal()).add(loanRepay.getInterest());
				}
			}
			loanInfo.setRepayPI(Numbers.toCurrency(repayPI.doubleValue()));
			loanInfo.setRepayedPI(Numbers.toCurrency(repayedPI.doubleValue()));
			loanInfo.setUnRepayPI(Numbers.toCurrency(unRepayPI.doubleValue()));
			loaninfoList.add(loanInfo);
		}
		return loaninfoList;
	}

	@Override
	public List<LoanRepay> getRepayPlan(Loan loan) {
		RepayMethod repayMethod = repayServiceImpl.getRepayMethod(loan.getRepay().getId());
		List<LoanRepay> loanRepayList = repayMethod.generatePlan(loan, null);
		BigDecimal firstRepay = repayMethod.getAllPT(loan);
		BigDecimal unRepay = firstRepay;
		for (LoanRepay loanRepay : loanRepayList) {
			unRepay = unRepay.subtract(loanRepay.getAmount());
			loanRepay.setUnRepay(unRepay);
		}
		LoanRepay first = new LoanRepay();
		first.setUnRepay(firstRepay);
		loanRepayList.add(0, first); // 首行插入总计还款额
		return loanRepayList;
	}

	/**
	 * 生成借款编号
	 * 
	 * @return
	 */
	public synchronized String generateLoanNo() {
		String maxLoanNo = null;
		String date = Calendars.format("yyyyMMdd");
		List<Loan> loanList = loanRepository.findAllOrderByLoanNo();
		if (loanList != null && loanList.size() > 0) {
			maxLoanNo = loanList.get(0).getLoanNo();
			Logger.info("当前数据库中最大编号：loanNo=%s", Long.valueOf(maxLoanNo));
			if (!Strings.empty(maxLoanNo) && maxLoanNo.length() == 12) {
				today = maxLoanNo.substring(0, 8);
				maxLoanNo = maxLoanNo.substring(8);
				Caches.set(CACHE_LOAN_SEQUENCE, Long.valueOf(maxLoanNo));
			}
		}
		if (maxLoanNo == null) {
			today = date;
			Caches.set(CACHE_LOAN_SEQUENCE, 0);
		} else {
			Date dbDate = null, nowDate = null;
			try {
				dbDate = Calendars.parse("yyyyMMdd", today);
				nowDate = Calendars.parse("yyyyMMdd", date);
				if (dbDate.before(nowDate)) {
					today = date;
					Caches.set(CACHE_LOAN_SEQUENCE, 0);
				}
			} catch (ParseException e) {
				Logger.error("生成loanNo时：格式化时间异常：dbDate=" + dbDate + ",nowDate=" + date);
				today = date;
			}
		}
		Long seq = Caches.incr(CACHE_LOAN_SEQUENCE, 1); // 递增缓存数据
		Logger.info("新创建的债权编号：loanNo=%s", String.format("%s%04d", today, seq));
		return String.format("%s%04d", today, seq);
	}

	@Override
	public List<Loan> findByStatus(String status) {
		return loanRepository.findByStatus(status);
	}

	@Override
	public LoanUserInfo loadLoanUserInfoByUserId(String userId) {
		User user = userRepository.findOne(userId);
		LoanUserInfo loanUserInfo = new LoanUserInfo();

		// 获取用户账户
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.CASH);
		if (userAccount != null) {
			loanUserInfo.setBalance(userAccount.getBalance());
		}
		// 设置大头像
		String lgAvatar = userService.getAvatar(user, UserImage.Type.AVATAR_LG);
		if (!Strings.empty(lgAvatar)) {
			loanUserInfo.setLgAvatar(lgAvatar);
		}
		// 获取用户属性
		UserProperties userProperties = userPropertiesRepository.findByUser(user);
		if (userProperties != null) {
			loanUserInfo.setGenderName(userProperties.getGenderName());
			loanUserInfo.setMarriedName(userProperties.getMarriedName());
			loanUserInfo.setAge(userProperties.getAge());
			loanUserInfo.setAuthEmail(userProperties.getAuthEmail());
			loanUserInfo.setAuthCellphone(userProperties.getAuthCellphone());
			loanUserInfo.setAuthName(userProperties.getAuthName());
		}
		// 获取用户工作
		List<UserJob> userJobList = userJobRepository.findByUser(user);
		if (userJobList != null && userJobList.size() > 0) {
			UserJob userJob = userJobList.get(0);
			if (userJob != null) {
				loanUserInfo.setAnnualSalary(userJob.getAnnualSalary());
				loanUserInfo.setPosition(userJob.getPosition());
				loanUserInfo.setScaleName(userJob.getScaleName());
				loanUserInfo.setProperties(userJob.getProperties());
				loanUserInfo.setPropertiesName(userJob.getPropertiesName());
			}
		}
		// 获取用户教育信息
		UserEducation userEdu = userEducationRepository.findByUserIdAndType(userId, UserEducation.Type.HIGHEST);
		if (userEdu != null) {
			loanUserInfo.setSchool(userEdu.getSchool());
			loanUserInfo.setYear(userEdu.getYear());
			loanUserInfo.setDegree(userEdu.getDegree());
			loanUserInfo.setDegreeName(userEdu.getDegreeName());
		}

		List<UserHouse> userHouseList = userHouseRepository.findByUser(user);

		loanUserInfo.setHasHouse("无 ");
		loanUserInfo.setHasHouseMortgage("无 ");

		for (int i = 0; i < userHouseList.size(); i++) {
			UserHouse userHouse = userHouseList.get(i);
			if (userHouse != null)
				loanUserInfo.setHasHouse("有");
			if (Strings.equals(userHouse.getMortgage(), UserHouse.Mortgage.NO)) {
				loanUserInfo.setHasHouseMortgage("有");
				break;
			}
		}
		List<UserCar> userCarList = userCarRepository.findByUser(user);
		loanUserInfo.setHasCar("无 ");
		loanUserInfo.setHasCarMortgage("无 ");
		for (int i = 0; i < userCarList.size(); i++) {
			UserCar userCar = userCarList.get(i);
			if (userCar != null)
				loanUserInfo.setHasCar("有");
			if (Strings.equals(userCar.getMortgage(), UserCar.Mortgage.NO)) {
				loanUserInfo.setHasCarMortgage("有");
				break;
			}
		}

		return loanUserInfo;
	}

	@Override
	public LoanUserBasic loadLoanUserBasicByUserId(String userId) {
		User user = userRepository.findOne(userId);
		LoanUserBasic loanUserBasic = new LoanUserBasic();

		loanUserBasic.setAccount(user.getAccount());
		loanUserBasic.setEmail(user.getEmail());
		loanUserBasic.setCellphone(user.getCellphone());
		// 获取用户属性
		UserProperties userProperties = userPropertiesRepository.findByUser(user);
		if (userProperties != null) {
			loanUserBasic.setRealName(userProperties.getRealName());
			loanUserBasic.setGenderName(userProperties.getGenderName());
			loanUserBasic.setMarriedName(userProperties.getMarriedName());
			loanUserBasic.setAge(userProperties.getAge());
			loanUserBasic.setAuthEmail(userProperties.getAuthEmail());
			loanUserBasic.setAuthCellphone(userProperties.getAuthCellphone());
			loanUserBasic.setAuthName(userProperties.getAuthName());
			loanUserBasic.setIdNumber(userProperties.getIdNumber());
			loanUserBasic.setIdTypeName(userProperties.getIdTypeName());
			loanUserBasic.setChildren(userProperties.getChildren());

		}
		// 获取用户教育信息
		UserEducation userEdu = userEducationRepository.findByUserIdAndType(userId, UserEducation.Type.HIGHEST);
		if (userEdu != null) {
			loanUserBasic.setSchool(userEdu.getSchool());
			loanUserBasic.setYear(userEdu.getYear());
			loanUserBasic.setDegree(userEdu.getDegree());
			loanUserBasic.setDegreeNumber(userEdu.getDegree());
			loanUserBasic.setDegreeName(userEdu.getDegreeName());
		}

		UserAddress userAdd = userAddressRepository.findByUserIdAndType(userId, UserAddress.Type.COMMON);
		if (userAdd != null) {
			loanUserBasic.setAddress(userAdd.getAddress());
			loanUserBasic.setPhone(userAdd.getPhone());
			loanUserBasic.setZip(userAdd.getZip());
		}
		return loanUserBasic;
	}

	@Override
	public Loan audit(Loan loan, Boolean isPass, String type, BigDecimal amount, String remark) {
		Date now = new Date();
		// 借款日志
		LoanLog loanLog = new LoanLog();
		loanLog.setUser(loan.getUser().getId());
		loanLog.setLoan(loan);
		loanLog.setDatetime(now);
		loanLog.setAmount(amount);
		// 审核日志
		LoanAudit loanAudit = new LoanAudit();
		loanAudit.setLoan(loan);
		loanAudit.setAuditor(Model.getCurrentUserId());
		loanAudit.setDatetime(now);
		loanAudit.setRemark(remark);
		// 等级初审
		if (type.equals(LoanAudit.Type.FIRST_AUDIT)) {
			loanAudit.setType(LoanAudit.Type.FIRST_AUDIT);
			// 审核通过
			if (isPass) {
				// 改变借款状态:等待初审进入等待终审,如有借款金额变动，则同时更新变动金额
				int success = loanNativeRepository.updateStatusAndAmount(loan.getId(), Loan.Status.AUDIT_FIRST, Loan.Status.AUDIT_FINAL, amount);
				if (success <= 0) {
					throw new ServiceException("audit status change fail!");
				}
				loanAudit.setStatus(LoanAudit.Status.PASS);
				loanLog.setType(Type.FIRST_AUDIT_PASS);
			} else {
				int success = loanNativeRepository.updateStatus(loan.getId(), Loan.Status.AUDIT_FIRST, Loan.Status.REJECT_AUDIT_FIRST);
				if (success <= 0) {
					throw new ServiceException("audit status change fail!");
				}
				loanAudit.setStatus(LoanAudit.Status.REJECT);
				loanLog.setType(Type.FIRST_AUDIT_REJECT);
			}
			// 等级终审
		} else if (type.equals(LoanAudit.Type.FINALL_AUDIT)) {
			loanAudit.setType(LoanAudit.Type.FINALL_AUDIT);
			// 审核通过
			if (isPass) {
				// 改变借款状态:等待终审进入招标中,如有借款金额变动，则同时更新变动金额
				int success = loanNativeRepository.updateStatusAndAmount(loan.getId(), Loan.Status.AUDIT_FINAL, Loan.Status.BID, amount);
				if (success <= 0) {
					throw new ServiceException("final audit status change fail!");
				}
				loanAudit.setStatus(LoanAudit.Status.PASS);
				loanLog.setType(Type.FINALL_AUDIT_PASS);
			} else {
				int success = loanNativeRepository.updateStatus(loan.getId(), Loan.Status.AUDIT_FINAL, Loan.Status.REJECT_AUDIT_FINAL);
				if (success <= 0) {
					throw new ServiceException("final audit status change fail!");
				}
				loanAudit.setStatus(LoanAudit.Status.REJECT);
				loanLog.setType(Type.FINAL_AUDIT_REJECT);
			}
		}
		// 记录借款日志、审核日志
		loanLogRepository.save(loanLog);
		loanAuditReository.save(loanAudit);
		return loan;
	}

	@Override
	public Page<Loan> findByLoanNoAndAccountAndAmountBetweenAndStatus(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, Integer page, Integer size, String... status) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		Query query = new Query("from Loan where 1 = 1");

		// 查询数据
		query.and("loanNo = :loanNo", "loanNo", loanNo, !Strings.empty(loanNo));
		query.and("user.account like :account", "account", "%" + account + "%", !Strings.empty(account));
		query.and("amount >= :startAmount", "startAmount", startAmount, startAmount != null);
		query.and("amount <= :endAmount", "endAmount", endAmount, startAmount != null);
		query.and("status in :statusList", "statusList", Arrays.asList(status), true);
		query.order("datetime asc");

		Long total = commonRepository.count(query.getCount(), query.getParams());
		List<Loan> loans = commonRepository.pageByJpql(query.getJpql(), query.getParams(), pageable.getOffset(), pageable.getPageSize(), Loan.class);

		// 返回结果
		return new PageImpl<Loan>(loans, pageable, total);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.LoanService#loadLoanRecordByUser(java.lang
	 * .String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<LoanInfo> loadLoanRecordByUser(String userId, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);

		List<LoanInfo> loaninfoList = new ArrayList<LoanInfo>();
		List<Loan> loanList = loanRepository.findByUserIdOrderByDatetimeDesc(userId);
		LoanInfo loanInfo = null;
		for (Loan loan : loanList) {
			loanInfo = new LoanInfo();
			loanInfo.setId(loan.getId());
			loanInfo.setPurpose(getDictionaryName(loan.getPurpose()));
			loanInfo.setRate(Numbers.toPercent(loan.getRate().doubleValue()));
			loanInfo.setAmount(Numbers.toCurrency(loan.getAmount().doubleValue()));
			loanInfo.setPeriod(loan.getPeriod().toString());
			loanInfo.setStatus(loan.getStatusName());
			loanInfo.setDatetime(loan.getDatetime());
			List<LoanRepay> loanRepayList = loanRepayRepository.findByLoan(loan);
			BigDecimal repayPI = BigDecimal.ZERO;
			BigDecimal repayedPI = BigDecimal.ZERO;
			BigDecimal unRepayPI = BigDecimal.ZERO;
			for (LoanRepay loanRepay : loanRepayList) {
				repayPI = repayPI.add(loanRepay.getAmount());
				// 等待还款
				if (RepayStatus.WAIT.equals(loanRepay.getStatus()) || RepayStatus.OVERDUE.equals(loanRepay.getStatus())) {
					unRepayPI = unRepayPI.add(loanRepay.getAmount());
				} else {
					repayedPI = repayedPI.add(loanRepay.getPrincipal()).add(loanRepay.getInterest());
				}
			}
			loanInfo.setRepayAmount(Numbers.toCurrency(repayedPI));
			loanInfo.setApplicationNo(loan.getProduct().getCode() + "-" + loan.getLoanNo());
			loanInfo.setRepay(loan.getRepay().getName());
			loaninfoList.add(loanInfo);
		}
		Long total = Long.valueOf(loaninfoList.size());
		Page<LoanInfo> pageLoan = new PageImpl<LoanInfo>(loaninfoList, pageable, total);
		return pageLoan;
	}

	private String getCondition(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, String status, Map<String, Object> params) {
		StringBuilder condition = new StringBuilder();

		if (!Strings.empty(loanNo)) {
			condition.append(" and hl.loan_no = :loanNo");
			params.put("loanNo", loanNo);
		}
		if (!Strings.empty(account)) {
			condition.append(" and hu.account = :account");
			params.put("account", account);
		}
		if (startAmount != null) {
			condition.append(" and hl.amount >= :startAmount");
			params.put("startAmount", startAmount);
		}
		if (endAmount != null) {
			condition.append(" and hl.amount <= :endAmount");
			params.put("endAmount", endAmount);
		}
		if (!Strings.empty(status)) {
			condition.append(" and hl.status = :status");
			params.put("status", status);
		}
		return condition.toString();
	}

	private String getCondition(String loanNo, String cellphone, Map<String, Object> params) {
		StringBuilder condition = new StringBuilder();

		if (!Strings.empty(loanNo)) {
			condition.append(" and hl.loan_no = :loanNo");
			params.put("loanNo", loanNo);
		}
		if (!Strings.empty(cellphone)) {
			condition.append(" and hu.cellphone = :cellphone");
			params.put("cellphone", cellphone);
		}
		return condition.toString();
	}

	@Override
	public Page<LoanAuditInfo> findByLoanNoAndNickAndAmountBetweenAndStatus(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, String status, String page, String size) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlSearchByLoanAudit = commonRepository.readScriptFile(Script.searchByLoanAudit);

		String sqlCountSearchByLoanAudit = commonRepository.readScriptFile(Script.countSearchByLoanAudit);
		String condition = getCondition(loanNo, account, startAmount, endAmount, status, params);
		sqlSearchByLoanAudit = String.format(sqlSearchByLoanAudit, condition);
		sqlCountSearchByLoanAudit = String.format(sqlCountSearchByLoanAudit, condition);

		// 初始化
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "5")));

		List<?> listCount = commonRepository.findByNativeSql(sqlCountSearchByLoanAudit, params);
		Long total = Long.parseLong(String.valueOf(listCount.get(0)));
		List<?> list = commonRepository.findByNativeSql(sqlSearchByLoanAudit, params, pageable.getOffset(), pageable.getPageSize());
		List<LoanAuditInfo> loans = new ArrayList<LoanAuditInfo>();
		for (int i = 0; i < list.size(); i++) {
			LoanAuditInfo loanAuditInfo = new LoanAuditInfo();
			Object[] object = (Object[]) list.get(i);
			loanAuditInfo.setAccount(String.valueOf(object[0]));
			loanAuditInfo.setLoanNo(String.valueOf(object[1]));
			loanAuditInfo.setAmount(Numbers.parseCurrency(String.valueOf(object[2])));
			LoanLog loanLogFull = loanLogRepository.findByLoanIdAndType(String.valueOf(object[3]), LoanLog.Type.FULL);
			if (loanLogFull != null && loanLogFull.getDatetime() != null) {
				loanAuditInfo.setFullDateTime(loanLogFull.getDatetime());
			}
			loanAuditInfo.setId(String.valueOf(object[3]));
			loans.add(loanAuditInfo);
		}
		// 返回结果
		Page<LoanAuditInfo> pageLoanAuditInfo = new PageImpl<LoanAuditInfo>(loans, pageable, total);
		return pageLoanAuditInfo;
	}

	@Override
	public Page<LoanAuditInfo> findByLoanNoAndCellphone(String loanNo, String cellphone, String page, String size) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlSearchByLoanAudit = commonRepository.readScriptFile(Script.searchByLoanAudit);
		String sqlCountSearchByLoanAudit = commonRepository.readScriptFile(Script.countSearchByLoanAudit);
		String condition = getCondition(loanNo, cellphone, params);
		sqlSearchByLoanAudit = String.format(sqlSearchByLoanAudit, condition);
		sqlCountSearchByLoanAudit = String.format(sqlCountSearchByLoanAudit, condition);
		// 初始化
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "5")));
		List<?> listCount = commonRepository.findByNativeSql(sqlCountSearchByLoanAudit, params);
		Long total = 0l;
		if (listCount.size() != 0) {
			total = Long.parseLong(String.valueOf(listCount.get(0)));
		}
		List<?> list = commonRepository.findByNativeSql(sqlSearchByLoanAudit, params, pageable.getOffset(), pageable.getPageSize());
		List<LoanAuditInfo> loans = new ArrayList<LoanAuditInfo>();
		for (int i = 0; i < list.size(); i++) {
			LoanAuditInfo loanAuditInfo = new LoanAuditInfo();
			Object[] object = (Object[]) list.get(i);
			loanAuditInfo.setLoanNo(String.valueOf(object[1]));
			if (object[5] != null) {
				loanAuditInfo.setRealName(String.valueOf(object[5]));
			}
			loanAuditInfo.setAmount(Numbers.parseCurrency(String.valueOf(object[2])));
			loanAuditInfo.setCellphone(String.valueOf(object[6]));
			loanAuditInfo.setRate(Numbers.toPercent(new Double(String.valueOf(object[8]))));
			loanAuditInfo.setPeriod(Integer.valueOf(String.valueOf(object[7])));
			try {
				loanAuditInfo.setDatetime(Calendars.parseDateTime(String.valueOf(object[9])));
			} catch (ParseException e) {
				loanAuditInfo.setDatetime(null);

			}
			loanAuditInfo.setId(String.valueOf(object[3]));
			loanAuditInfo.setStatus(String.valueOf(object[4]));
			loanAuditInfo.setLoanKind(String.valueOf(object[10]));// 标类型
			loans.add(loanAuditInfo);
		}
		// 返回结果
		Page<LoanAuditInfo> pageLoanAuditInfo = new PageImpl<LoanAuditInfo>(loans, pageable, total);
		return pageLoanAuditInfo;
	}

	/**
	 * 初审
	 */
	@Override
	public Loan firstAudit(Loan loan, Boolean isPass, BigDecimal amount, String remark) {
		Date now = new Date();
		// 借款日志
		LoanLog loanLog = new LoanLog();
		loanLog.setUser(loan.getUser().getId());
		loanLog.setLoan(loan);
		loanLog.setDatetime(now);
		loanLog.setAmount(amount);
		// 审核日志
		LoanAudit loanAudit = new LoanAudit();
		loanAudit.setLoan(loan);
		loanAudit.setAuditor(Model.getCurrentUserId());
		loanAudit.setDatetime(now);
		loanAudit.setRemark(remark);
		// 等级初审
		loanAudit.setType(LoanAudit.Type.FIRST_AUDIT);
		// 审核通过
		if (isPass) {
			// 改变借款状态:等待初审进入等待终审,如有借款金额变动，则同时更新变动金额
			int success = loanNativeRepository.updateStatusAndAmount(loan.getId(), Loan.Status.AUDIT_FIRST, Loan.Status.AUDIT_FINAL, amount);
			if (success <= 0) {
				throw new ServiceException("audit status change fail!");
			}
			loanAudit.setStatus(LoanAudit.Status.PASS);
			loanLog.setType(Type.FIRST_AUDIT_PASS);
		} else {
			int success = loanNativeRepository.updateStatus(loan.getId(), Loan.Status.AUDIT_FIRST, Loan.Status.REJECT_AUDIT_FIRST);
			if (success <= 0) {
				throw new ServiceException("audit status change fail!");
			}
			loanAudit.setStatus(LoanAudit.Status.REJECT);
			loanLog.setType(Type.FIRST_AUDIT_REJECT);
		}
		// 记录借款日志、审核日志
		loanLogRepository.save(loanLog);
		loanAuditReository.save(loanAudit);
		return loan;
	}

	/**
	 * 终审
	 */
	@Override
	public Loan finalAudit(Loan loan, Boolean isPass, BigDecimal amount, String remark, List<String> labelList) {
		Date now = new Date();
		// 借款日志
		LoanLog loanLog = new LoanLog();
		loanLog.setUser(loan.getUser().getId());
		loanLog.setLoan(loan);
		loanLog.setDatetime(now);
		loanLog.setAmount(amount);
		// 借款日志（开始招标的）
		LoanLog loanStartInvestLog = new LoanLog();
		loanStartInvestLog.setUser(loan.getUser().getId());
		loanStartInvestLog.setLoan(loan);
		loanStartInvestLog.setDatetime(now);
		loanStartInvestLog.setAmount(amount);
		// 审核日志
		LoanAudit loanAudit = new LoanAudit();
		loanAudit.setLoan(loan);
		loanAudit.setAuditor(Model.getCurrentUserId());
		loanAudit.setDatetime(now);
		loanAudit.setRemark(remark);

		loanAudit.setType(LoanAudit.Type.FINALL_AUDIT);
		// 审核通过
		if (isPass) {
			// 改变借款状态:等待终审进入招标中,如有借款金额变动，则同时更新变动金额
			int success = loanNativeRepository.updateStatusAndAmount(loan.getId(), Loan.Status.AUDIT_FINAL, Loan.Status.BID, amount);
			if (success <= 0) {
				throw new ServiceException("final audit status change fail!");
			}
			loanAudit.setStatus(LoanAudit.Status.PASS);
			loanLog.setType(Type.FINALL_AUDIT_PASS);
			loanStartInvestLog.setType(Type.START_INVEST);
			// 只有审核通过才纪录开始投标的日志
			loanLogRepository.save(loanStartInvestLog);

			List<LoanAuth> loanAuthList = new ArrayList<LoanAuth>();
			LoanAuth loanAuth = null;
			// 遍历认证标签
			for (String label : labelList) {
				int pos = label.indexOf('&');
				String labelId = label.substring(0, pos);
				String labelStatus = label.substring(pos + 1);
				loanAuth = new LoanAuth();
				Label labelfind = labelRepository.findOne(labelId);
				if (labelfind != null) {
					loanAuth.setLabel(labelfind);
					loanAuth.setLoan(loan);
					loanAuth.setStatus(labelStatus);
					loanAuthList.add(loanAuth);
				}
			}
			loanAuthRepository.save(loanAuthList);
		} else {
			int success = loanNativeRepository.updateStatus(loan.getId(), Loan.Status.AUDIT_FINAL, Loan.Status.REJECT_AUDIT_FINAL);
			if (success <= 0) {
				throw new ServiceException("final audit status change fail!");
			}
			loanAudit.setStatus(LoanAudit.Status.REJECT);
			loanLog.setType(Type.FINAL_AUDIT_REJECT);
		}
		// 记录借款日志、审核日志
		loanLogRepository.save(loanLog);
		loanAuditReository.save(loanAudit);
		return loan;
	}

	@Override
	public List<LoanAudit> findLoanAuditByLoan(Loan loan) {
		return loanAuditReository.findByLoan(loan);
	}

	@Override
	public List<LoanAuth> findLoanAuthByLoan(Loan loan) {
		return loanAuthRepository.findByLoan(loan);
	}

	@Override
	public boolean updateStatus(String id, String fromStatus, String toStatus) {
		int success = loanNativeRepository.updateStatus(id, fromStatus, toStatus);
		if (success == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据： 导入还款明细id 获取 loan 还款计划表 id
	 */
	@Override
	public String queryLoanRepayId(String creditRepayPlanId) throws Exception {
		int sequnce = 0; // 还款期数
		CreditRepayPlan repayPlan = creditorRepayPlanRepository.findOne(creditRepayPlanId);
		if (repayPlan != null) {
			sequnce = repayPlan.getPeriod();
			if (sequnce <= 0) {
				throw new Exception("债权还款明细:还款期数:period=" + sequnce + ",不正确");
			}
			String creditInfoId = repayPlan.getCrediteInfo().getId();
			List<Loan> waitRepayCreditlist = loanRepository.findByCreditInfoAndLoanKindAndStatus(creditInfoId, Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN, Loan.Status.REPAYING);
			if (waitRepayCreditlist == null || waitRepayCreditlist.size() == 0) {
				throw new Exception("根据：债权id=" + creditInfoId + ",查询到状态为:待还款,的债权标为空,债权标当前状态： " + repayPlan.getCrediteInfo().getStatusName());
			} else if (waitRepayCreditlist.size() > 1) {
				throw new Exception("根据：债权id=" + creditInfoId + ", 查状到态为：待还款, 的债权标个数不唯一");
			}
			LoanRepay loanRepay = loanRepayRepository.findByLoanAndSequence(waitRepayCreditlist.get(0), sequnce);
			if (loanRepay == null) {
				throw new Exception("根据：债权id=" + creditInfoId + ", 期数=" + sequnce + ",没有查到还款明细");
			}
			return loanRepay.getId();
		} else {
			throw new Exception("还款明细id=" + creditRepayPlanId + ",没有对应信息。");
		}
	}

	/**
	 * 更新 债权还款明细
	 * 
	 * @param repayid
	 * @return
	 */
	@Override
	public boolean updateCreditRepayPlanStatus(String repayid, String status) {
		if (Strings.empty(status) || Strings.empty(repayid)) {
			throw new ServiceException("更新债权还款明细状态：入参为空，repayid=" + repayid + ", status=" + status);
		}
		CreditRepayPlan repayPlan = creditorRepayPlanRepository.findOne(repayid);
		if (repayPlan == null) {
			throw new ServiceException("根据：id=" + repayid + ", 没有找到债权导入明细");
		}
		repayPlan.setStatus(status);
		repayPlan = creditorRepayPlanRepository.save(repayPlan);
		if (repayPlan != null) {
			Logger.info("id=" + repayid + ",更新债权还款明细状态=" + status + "成功!");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据：债权人id 获取 现金账户信息
	 */
	@Override
	public UserAccount queryUserAccountByCreditorId(String creditorId) {
		Creditor creditor = creditorRepository.findById(creditorId);
		return userAccountRepository.findByUserIdAndType(creditor.getUser().getId(), UserAccount.Type.CASH);
	}

	/**
	 * 债权账户虚拟线下充值
	 */
	@Override
	public UserAccount accountCharge(String amount, String accountId) {
		BigDecimal addAmount = new BigDecimal(amount);
		User user = userAccountRepository.findOne(accountId).getUser();
		transactionService.cropAccountToCreditorOutline(Transaction.Type.CHARGE, user, UserAccount.Type.PAYMENT, addAmount, "债权人线下充值", "债权人线下充值");
		return userAccountRepository.findOne(accountId);
	}

	/**
	 * 根据状态 获取操作日志
	 */
	@Override
	public List<LoanLog> loadLogByLoanIdAndTypeIn(Loan loan, List<String> typeList) {
		return loanLogRepository.findByLoanAndTypeIn(loan, typeList);
	}
}

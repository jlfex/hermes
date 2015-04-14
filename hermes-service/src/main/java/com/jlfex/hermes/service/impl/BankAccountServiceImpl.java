package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.constant.HermesEnum.Tx1361Status;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.support.spring.SpringWebApp;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.Withdraw;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.AreaRepository;
import com.jlfex.hermes.repository.BankAccountRepository;
import com.jlfex.hermes.repository.BankRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.WithdrawRepository;
import com.jlfex.hermes.service.BankAccountService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.apiLog.ApiLogService;
import com.jlfex.hermes.service.cfca.CFCAOrderService;
import com.jlfex.hermes.service.cfca.ThirdPPService;
import com.jlfex.hermes.service.withdraw.WithdrawFee;

/**
 * 银行账户业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-02
 * @since 1.0
 */
@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

	private static final String PROP_WITHDRAW_FEE_NAME = "fee.withdraw.name";
	private static final String PROP_WITHDRAW_FEE_CONFIG = "fee.withdraw.config";

	/** 银行账户仓库 */
	@Autowired
	private BankAccountRepository bankAccountRepository;

	/** 银行信息仓库 */
	@Autowired
	private BankRepository bankRepository;

	/** 地区信息仓库 */
	@Autowired
	private AreaRepository areaRepository;

	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;

	/** 用户属性仓库 */
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 提现信息仓库 */
	@Autowired
	private WithdrawRepository withdrawRepository;

	/** 交易业务接口 */
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private CFCAOrderService cFCAOrderService;

	@Autowired
	private ThirdPPService thirdPPService;

	@Autowired
	private ApiLogService apiLogService;
	
	@Autowired
	private InvestService investService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.BankAccountService#findByUserIdAndStatus(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BankAccount> findByUserIdAndStatus(String userId, String status) {
		return bankAccountRepository.findByUserIdAndStatus(userId, status);
	}

	@Override
	public List<BankAccount> findByUserId(String userId) {
		List<BankAccount> l = bankAccountRepository.findByUserId(userId);
		for (BankAccount a : l) {
			a.getCity().setParent(areaRepository.findOne(a.getCity().getParentId()));
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.BankAccountService#findEnbaled()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BankAccount> findEnbaled() {
		return findByUserIdAndStatus(App.user().getId(), BankAccount.Status.ENABLED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.BankAccountService#save(com.jlfex.hermes.model
	 * .BankAccount)
	 */
	@Override
	public BankAccount save(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.BankAccountService#save(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BankAccount save(String bankId, String cityId, String deposit, String account) {
		// 查询基础数据
		User user = userRepository.findOne(App.user().getId());
		Bank bank = bankRepository.findOne(bankId);
		Area city = areaRepository.findOne(cityId);
		UserProperties userProperties = userPropertiesRepository.findByUser(user);

		// 设置对象并保存
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUser(user);
		bankAccount.setBank(bank);
		bankAccount.setCity(city);
		bankAccount.setDeposit(deposit);
		bankAccount.setName(userProperties.getRealName());
		bankAccount.setAccount(account);
		bankAccount.setStatus(BankAccount.Status.ENABLED);

		// 返回数据
		return save(bankAccount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.BankAccountService#findEnabledBank()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Bank> findEnabledBank() {
		return bankRepository.findByStatus(Bank.Status.ENBALED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.BankAccountService#loadBankById(java.lang.String
	 * )
	 */
	@Override
	@Transactional(readOnly = true)
	public Bank loadBankById(String id) {
		return bankRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.BankAccountService#calcWithdrawFee(java.lang
	 * .Double)
	 */
	@Override
	public Result<String> calcWithdrawFee(Double amount) {
		// 初始化
		// 加载实现类
		Result<String> result = new Result<String>();
		WithdrawFee withdrawFee = SpringWebApp.getBean(App.config(PROP_WITHDRAW_FEE_NAME), WithdrawFee.class);

		// 计算费用
		try {
			BigDecimal amt = Numbers.currency(amount);
			BigDecimal fee = withdrawFee.calcFee(amt, App.config(PROP_WITHDRAW_FEE_CONFIG));
			BigDecimal sum = amt.add(fee);

			result.setType(Result.Type.SUCCESS);
			result.addMessage(Numbers.toCurrency(fee));
			result.addMessage(Numbers.toCurrency(sum));
		} catch (ServiceException e) {
			result.setType(Result.Type.FAILURE);
			result.addMessage(App.message(e.getKey()));
			Logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ServiceException se = new ServiceException(e.getMessage(), e);
			result.setType(Result.Type.FAILURE);
			result.addMessage(App.message(se.getKey()));
			Logger.error(se.getMessage(), se);
		}

		// 返回结果
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.BankAccountService#addWithdraw(java.lang.String,
	 * java.lang.Double)
	 */
	@Override
	public Withdraw addWithdraw(String bankAccountId, Double amount) {
		// 验证数据
		Assert.notEmpty(bankAccountId, "bank account id is empty.", "account.fund.withdraw.failure.bankaccount");
		Assert.notNull(amount, "amount is null.", "account.fund.withdraw.failure.amount");

		// 初始化并加载数据项
		Withdraw withdraw = new Withdraw();
		BankAccount bankAccount = bankAccountRepository.findOne(bankAccountId);
		User user = userRepository.findOne(App.user().getId());
		WithdrawFee withdrawFee = SpringWebApp.getBean(App.config(PROP_WITHDRAW_FEE_NAME), WithdrawFee.class);

		// 设置数据并保存数据
		withdraw.setUser(user);
		withdraw.setBankAccount(bankAccount);
		withdraw.setDatetime(new Date());
		withdraw.setAmount(Numbers.currency(amount));
		withdraw.setFee(withdrawFee.calcFee(withdraw.getAmount(), App.config(PROP_WITHDRAW_FEE_CONFIG)));
		withdraw.setStatus(Withdraw.Status.WAIT);
		withdrawRepository.save(withdraw);

		// 冻结金额
		transactionService.freeze(Transaction.Type.FREEZE, user, withdraw.getAmount().add(withdraw.getFee()), withdraw.getId(), App.message("transaction.withdraw.freeze"));

		// 返回结果
		return withdraw;
	}

	@Override
	public BankAccount loadBankAccountById(String id) {
		BankAccount bankAccount = bankAccountRepository.findOne(id);
		bankAccount.getCity().setParent(areaRepository.findOne(bankAccount.getCity().getParentId()));
		return bankAccount;
	}

	@Override
	public BankAccount findOneByUserIdAndStatus(String userId, String status) {
		// TODO Auto-generated method stub
		return bankAccountRepository.findOneByUserIdAndStatus(userId, status);
	}

	/**
	 * 中金充值
	 * @param amount
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Result zjCharge(BigDecimal amount) {
		Result result = new Result();
		
		User user = userRepository.findOne(App.user().getId());
		BankAccount bankAccount = this.findOneByUserIdAndStatus(user.getId(), BankAccount.Status.ENABLED);
		UserProperties userProperties = userPropertiesRepository.findByUser(user);
		// 请求日志
		Map<String, String> recodeMap = new HashMap<String, String>();
		Tx1361Response response = null;
		try {
			String txSN = cFCAOrderService.genOrderTxSN();
			Tx1361Request tx1361Request = cFCAOrderService.buildTx1361Request(user, amount, bankAccount, userProperties, txSN);
			recodeMap.put("interfaceMethod", HermesConstants.ZJ_INTERFACE_TX1361);
			recodeMap.put("requestMsg", tx1361Request.getRequestPlainText());
			ApiLog apiLog = cFCAOrderService.recordApiLog(recodeMap);
			response = thirdPPService.invokeTx1361(tx1361Request);
			CFCAOrder cfcaOrder = cFCAOrderService.genCFCAOrder(response, user, amount, txSN, CFCAOrder.Type.RECHARGE);
			if (response.getCode().equals(HermesConstants.CFCA_SUCCESS_CODE)) {
				if (response.getStatus() == Tx1361Status.IN_PROCESSING.getStatus()) {
					transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, user, UserAccount.Type.ZHONGJIN_FEE, amount, cfcaOrder.getId(), Transaction.Status.WAIT);
					result.setType(Type.WITHHOLDING_PROCESSING);
					result.addMessage(0,"充值处理中");
				} else if (response.getStatus() == Tx1361Status.WITHHOLDING_SUCC.getStatus()) {
					transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, user, UserAccount.Type.ZHONGJIN_FEE, amount, cfcaOrder.getId(), Transaction.Status.RECHARGE_SUCC);
					result.setType(Type.SUCCESS);
					result.addMessage(0,"充值成功");
				} else {
					transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, user, UserAccount.Type.ZHONGJIN_FEE, amount, cfcaOrder.getId(), Transaction.Status.RECHARGE_FAIL);
					result.setType(Type.FAILURE);
					result.addMessage(0,"充值失败");
				}

				investService.saveUserLog(user);
			} else {
				result.setType(Type.FAILURE);
				result.addMessage(0, response.getResponseMessage());
			}
			apiLog.setResponseMessage(response.getResponsePlainText());
			apiLog.setResponseTime(new Date());
			apiLogService.saveApiLog(apiLog);
		} catch (Exception e) {
			result.setType(Type.FAILURE);
			result.addMessage(0, response.getResponseMessage());
		}

		return result;
	}
}

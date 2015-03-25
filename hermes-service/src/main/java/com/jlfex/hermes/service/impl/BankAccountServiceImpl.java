package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.support.spring.SpringWebApp;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.Withdraw;
import com.jlfex.hermes.repository.AreaRepository;
import com.jlfex.hermes.repository.BankAccountRepository;
import com.jlfex.hermes.repository.BankRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.WithdrawRepository;
import com.jlfex.hermes.service.BankAccountService;
import com.jlfex.hermes.service.TransactionService;
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
		Assert.notEmpty(userId, "user id is empty.");
		Assert.notEmpty(status, "status is empty.");
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
	public BankAccount findByIsDefault(Boolean isDefault) {
		// TODO Auto-generated method stub
		return bankAccountRepository.findByIsDefault(isDefault);
	}

}

package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Withdraw;

/**
 * 银行账户业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-02
 * @since 1.0
 */
public interface BankAccountService {

	/**
	 * 通过用户编号和状态查询银行账户
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<BankAccount> findByUserIdAndStatus(String userId, String status);

	/**
	 * 通过用户编号查询银行账户
	 * 
	 * @param userId
	 * @return
	 */
	public List<BankAccount> findByUserId(String userId);

	/**
	 * 查询有效银行账户
	 * 
	 * @return
	 */
	public List<BankAccount> findEnbaled();

	/**
	 * 保存银行账户
	 * 
	 * @param bankAccount
	 * @return
	 */
	public BankAccount save(BankAccount bankAccount);

	/**
	 * 保存银行账户
	 * 
	 * @param bankId
	 * @param cityId
	 * @param deposit
	 * @param account
	 * @return
	 */
	public BankAccount save(String bankId, String cityId, String deposit, String account);

	/**
	 * 查询有效银行
	 * 
	 * @return
	 */
	public List<Bank> findEnabledBank();

	/**
	 * 通过编号加载银行
	 * 
	 * @param id
	 * @return
	 */
	public Bank loadBankById(String id);

	/**
	 * 通过编号加载银行账户
	 * 
	 * @param id
	 * @return
	 */
	public BankAccount loadBankAccountById(String id);

	/**
	 * 计算提现手续费
	 * 
	 * @param amount
	 * @return
	 */
	public Result<String> calcWithdrawFee(Double amount);

	/**
	 * 添加提现信息
	 * 
	 * @param bankAccountId
	 * @param amount
	 * @return
	 */
	public Withdraw addWithdraw(String bankAccountId, Double amount);
	
	public BankAccount findOneByUserIdAndStatus(String userId,String status);
}

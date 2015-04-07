package com.jlfex.hermes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;

/**
 * 交易业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-27
 * @since 1.0
 */
public interface TransactionService {

	/**
	 * 通过用户编号/时间范围以及类型查询<br>
	 * 不填类型默认认为是所有可用类型
	 * 
	 * @param userId
	 * @param types
	 * @return
	 */
	public List<Transaction> findByUserIdAndDateBetweenAndTypes(String userId, String beginDate, String endDate, String... types);

	/**
	 * 通过用户编号/时间范围以及类型分页查询<br>
	 * 不填类型默认认为是所有可用类型
	 * 
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @param page
	 * @param size
	 * @param types
	 * @return
	 */
	public Page<Transaction> findByUserIdAndDateBetweenAndTypes(String userId, String beginDate, String endDate, Integer page, Integer size, String... types);

	/**
	 * 冻结资金
	 * 
	 * @param type
	 * @param user
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> freeze(String type, User user, BigDecimal amount, String reference, String remark);

	/**
	 * 冻结资金
	 * 
	 * @param type
	 * @param userId
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> freeze(String type, String userId, BigDecimal amount, String reference, String remark);

	/**
	 * 解冻资金
	 * 
	 * @param type
	 * @param user
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> unfreeze(String type, User user, BigDecimal amount, String reference, String remark);

	/**
	 * 解冻资金
	 * 
	 * @param type
	 * @param userId
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> unfreeze(String type, String userId, BigDecimal amount, String reference, String remark);

	/**
	 * 转账至公司账户
	 * 
	 * @param type
	 * @param user
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> toCropAccount(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * 转账至公司账户
	 * 
	 * @param type
	 * @param userId
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> toCropAccount(String type, String userId, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * 从公司账户转出
	 * 
	 * @param type
	 * @param user
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> fromCropAccount(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * 从公司账户转出
	 * 
	 * @param type
	 * @param userId
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> fromCropAccount(String type, String userId, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * 公司账户互转
	 * 
	 * @param type
	 * @param sourceType
	 * @param targetType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> betweenCropAccount(String type, String sourceType, String targetType, BigDecimal amount, String reference, String remark);

	/**
	 * 交易
	 * 
	 * @param type
	 * @param sourceUser
	 * @param targetUser
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> transact(String type, User sourceUser, User targetUser, BigDecimal amount, String reference, String remark);

	/**
	 * 交易
	 * 
	 * @param type
	 * @param sourceUserId
	 * @param targetUserId
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> transact(String type, String sourceUserId, String targetUserId, BigDecimal amount, String reference, String remark);

	/**
	 * 交易
	 * 
	 * @param type
	 * @param sourceUserAccount
	 * @param targetUserAccount
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	public List<Transaction> transact(String type, UserAccount sourceUserAccount, UserAccount targetUserAccount, BigDecimal amount, String reference, String remark);

	/**
	 * 风险金交易流水
	 * 
	 * 
	 */
	public Page<Transaction> findByUserIdAndDateType(String userId, Integer page, Integer size, List<String> types);

	public List<Transaction> findBySourceUserAccountAndTypeIn(String userId, List<String> types);

	/**
	 * 现金账户充值流水
	 * 
	 * @param type
	 * @param targetUserAccount
	 * @param amount
	 * @param reference
	 * @param remark
	 */
	public void addCashAccountRecord(String type, UserAccount sourceUserAccount, UserAccount targetUserAccount, BigDecimal amount, String reference, String remark);

	/**
	 * 债权人 账户线下充值
	 * 
	 * @param type
	 * @param user
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	List<Transaction> cropAccountToCreditorOutline(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * JLfex代扣 充值
	 * 
	 * @param type
	 * @param user
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 * @return
	 */
	List<Transaction> cropAccountToJlfexPay(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * 中金 代扣 充值
	 * 
	 * @param type
	 * @param user
	 * @param cropAccountType
	 * @param amount
	 * @param reference
	 * @param remark
	 *            充值结果
	 * @return
	 */
	List<Transaction> cropAccountToZJPay(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark);

	/**
	 * 根据中金返回结果记账
	 * 
	 * @param type
	 * @param sourceUserAccount
	 * @param targetUserAccount
	 * @param amount
	 * @param reference
	 * @param remark
	 *            充值结果
	 * @return
	 */
	public List<Transaction> transactToZJPay(String type, UserAccount sourceUserAccount, UserAccount targetUserAccount, BigDecimal amount, String reference, String remark);
}

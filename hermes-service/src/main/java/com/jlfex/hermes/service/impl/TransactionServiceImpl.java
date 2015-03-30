package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.repository.TransactionRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.common.Pageables;

/**
 * 交易业务实验
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-04
 * @since 1.0
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Integer TYPE_SPAN = 50;
	private static final String CROP_USER_ID = "crop";

	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;

	/** 用户账户信息仓库 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/** 交易流水信息仓库 */
	@Autowired
	private TransactionRepository transactionRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.TransactionService#
	 * findByUserIdAndDateBetweenAndTypes(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public List<Transaction> findByUserIdAndDateBetweenAndTypes(String userId, String beginDate, String endDate, String... types) {
		// 初始化
		Date begin = Calendars.parseBeginDateTime(beginDate);
		Date end = Calendars.parseEndDateTime(endDate);
		List<String> typeList = (types.length > 0) ? Arrays.asList(types) : getDefaultTypes();
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.CASH);

		// 查询数据并返回结果
		return transactionRepository.findBySourceUserAccountAndTypeInAndDatetimeBetween(userAccount, typeList, begin, end);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.TransactionService#
	 * findByUserIdAndDateBetweenAndTypes(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Integer, java.lang.Integer,
	 * java.lang.String[])
	 */
	@Override
	public Page<Transaction> findByUserIdAndDateBetweenAndTypes(String userId, String beginDate, String endDate, Integer page, Integer size, String... types) {
		// 初始化
		Date begin = Calendars.parseBeginDateTime(beginDate);
		Date end = Calendars.parseEndDateTime(endDate);
		List<String> typeList = (types.length > 0) ? Arrays.asList(types) : getDefaultTypes();
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.CASH);
		Pageable pageable = Pageables.pageable(page, size, Direction.DESC, "datetime");

		// 查询数据并返回结果
		return transactionRepository.findBySourceUserAccountAndTypeInAndDatetimeBetween(userAccount, typeList, begin, end, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.TransactionService#freeze(java.lang.String,
	 * java.lang.String, java.math.BigDecimal, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> freeze(String type, String userId, BigDecimal amount, String reference, String remark) {
		User user = userRepository.findOne(userId);
		return freeze(type, user, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.TransactionService#freeze(java.lang.String,
	 * com.jlfex.hermes.model.User, java.math.BigDecimal, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> freeze(String type, User user, BigDecimal amount, String reference, String remark) {
		UserAccount cashAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount frozenAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.FREEZE);
		return transact(type, cashAccount, frozenAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#unfreeze(java.lang.String,
	 * java.lang.String, java.math.BigDecimal, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> unfreeze(String type, String userId, BigDecimal amount, String reference, String remark) {
		User user = userRepository.findOne(userId);
		return unfreeze(type, user, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#unfreeze(java.lang.String,
	 * com.jlfex.hermes.model.User, java.math.BigDecimal, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Transaction> unfreeze(String type, User user, BigDecimal amount, String reference, String remark) {
		UserAccount cashAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount frozenAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.FREEZE);
		return transact(type, frozenAccount, cashAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#toCropAccount(java.lang.String
	 * , java.lang.String, java.lang.String, java.math.BigDecimal,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> toCropAccount(String type, String userId, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, userAccount, cropAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#toCropAccount(java.lang.String
	 * , com.jlfex.hermes.model.User, java.lang.String, java.math.BigDecimal,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> toCropAccount(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, userAccount, cropAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#fromCropAccount(java.lang
	 * .String, java.lang.String, java.lang.String, java.math.BigDecimal,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> fromCropAccount(String type, String userId, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, cropAccount, userAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#fromCropAccount(java.lang
	 * .String, com.jlfex.hermes.model.User, java.lang.String,
	 * java.math.BigDecimal, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> fromCropAccount(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, cropAccount, userAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#betweenCropAccount(java.lang
	 * .String, java.lang.String, java.lang.String, java.math.BigDecimal,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> betweenCropAccount(String type, String sourceType, String targetType, BigDecimal amount, String reference, String remark) {
		UserAccount sourceUserAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, sourceType);
		UserAccount targetUserAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, targetType);
		return transact(type, sourceUserAccount, targetUserAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#transact(java.lang.String,
	 * java.lang.String, java.lang.String, java.math.BigDecimal,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> transact(String type, String sourceUserId, String targetUserId, BigDecimal amount, String reference, String remark) {
		UserAccount sourceUserAccount = userAccountRepository.findByUserIdAndType(sourceUserId, UserAccount.Type.CASH);
		UserAccount targetUserAccount = userAccountRepository.findByUserIdAndType(targetUserId, UserAccount.Type.CASH);
		return transact(type, sourceUserAccount, targetUserAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#transact(java.lang.String,
	 * com.jlfex.hermes.model.User, com.jlfex.hermes.model.User,
	 * java.math.BigDecimal, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> transact(String type, User sourceUser, User targetUser, BigDecimal amount, String reference, String remark) {
		UserAccount sourceUserAccount = userAccountRepository.findByUserAndType(sourceUser, UserAccount.Type.CASH);
		UserAccount targetUserAccount = userAccountRepository.findByUserAndType(targetUser, UserAccount.Type.CASH);
		return transact(type, sourceUserAccount, targetUserAccount, amount, reference, remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#transact(java.lang.String,
	 * com.jlfex.hermes.model.UserAccount, com.jlfex.hermes.model.UserAccount,
	 * java.math.BigDecimal, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> transact(String type, UserAccount sourceUserAccount, UserAccount targetUserAccount, BigDecimal amount, String reference, String remark) {
		// 验证参数
		Assert.notEmpty(type, "type is empty.", "exception.transaction.type.empty");
		Assert.notNull(sourceUserAccount, "source user account is null", "exception.transaction.user.account.null");
		Assert.equals(User.Status.ENABLED, sourceUserAccount.getUser().getStatus(), "source user is not enabled.", "exception.transaction.user.status");
		Assert.equals(UserAccount.Status.VALID, sourceUserAccount.getStatus(), "source user account is not enabled.", "exception.transaction.user.account.status");
		Assert.notNull(targetUserAccount, "target user account is null", "exception.transaction.user.account.null");
		Assert.equals(User.Status.ENABLED, targetUserAccount.getUser().getStatus(), "target user is not enabled.", "exception.transaction.user.status");
		Assert.equals(UserAccount.Status.VALID, targetUserAccount.getStatus(), "target user account is not enabled.", "exception.transaction.user.account.status");
		Assert.notNull(amount, "amount is null", "exception.transaction.amount.null");

		// 判断账户余额是否可用
		// 当交易金额大于账户余额且账户不允许为负时异常处理
		if (amount.compareTo(sourceUserAccount.getBalance()) > 0 && UserAccount.Minus.INMINUS.equals(sourceUserAccount.getMinus())) {
			throw new ServiceException(String.format("交易异常：账户ID='%s',账户余额= %s, 小于  %s", sourceUserAccount.getId(), sourceUserAccount.getBalance(), amount), "exception.transaction.balance");
		}

		// 交易流水
		Transaction source = new Transaction();
		source.setSourceUserAccount(sourceUserAccount);
		source.setTargetUserAccount(targetUserAccount);
		source.setReference(reference);
		source.setType(type);
		source.setDatetime(new Date());
		source.setAmount(amount.negate());
		source.setSourceBeforeBalance(sourceUserAccount.getBalance());
		source.setTargetBeforeBalance(targetUserAccount.getBalance());
		source.setRemark(remark);

		Transaction target = new Transaction();
		target.setSourceUserAccount(targetUserAccount);
		target.setTargetUserAccount(sourceUserAccount);
		target.setReference(reference);
		target.setType(String.valueOf(Integer.valueOf(type) + TYPE_SPAN));
		target.setDatetime(source.getDatetime());
		target.setAmount(amount);
		target.setSourceBeforeBalance(targetUserAccount.getBalance());
		target.setTargetBeforeBalance(sourceUserAccount.getBalance());
		target.setRemark(remark);

		// 计算金额
		sourceUserAccount.setBalance(sourceUserAccount.getBalance().subtract(amount));
		targetUserAccount.setBalance(targetUserAccount.getBalance().add(amount));
		source.setSourceAfterBalance(sourceUserAccount.getBalance());
		source.setTargetAfterBalance(targetUserAccount.getBalance());
		target.setSourceAfterBalance(targetUserAccount.getBalance());
		target.setTargetAfterBalance(sourceUserAccount.getBalance());

		// 保存数据
		userAccountRepository.save(sourceUserAccount);
		userAccountRepository.save(targetUserAccount);
		transactionRepository.save(source);
		transactionRepository.save(target);

		// 返回结果
		return Arrays.asList(source, target);
	}

	/**
	 * 读取默认类型
	 * 
	 * @return
	 */
	protected List<String> getDefaultTypes() {
		Set<Object> keys = Dicts.elements(Transaction.Type.class, Transaction.Type.REVERSE_FREEZE, Transaction.Type.UNFREEZE).keySet();
		List<String> types = new ArrayList<String>(keys.size());
		for (Object key : keys) {
			types.add(String.class.cast(key));
		}
		return types;
	}

	/**
	 * 风险金流水
	 * 
	 * 
	 */
	@Override
	public Page<Transaction> findByUserIdAndDateType(String userId, Integer page, Integer size, List<String> types) {
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.RISK);
		Pageable pageable = Pageables.pageable(page, size, Direction.DESC, "datetime");
		return transactionRepository.findBySourceUserAccountAndTypeIn(userAccount, types, pageable);

	}

	@Override
	public List<Transaction> findBySourceUserAccountAndTypeIn(String userId, List<String> types) {
		UserAccount userAccount = userAccountRepository.findByUserIdAndType(userId, UserAccount.Type.RISK);
		return transactionRepository.findBySourceUserAccountAndTypeIn(userAccount, types);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.TransactionService#AddCashAccount(java.lang.
	 * String, com.jlfex.hermes.model.UserAccount, java.math.BigDecimal,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void AddCashAccount(String type, UserAccount sourceUserAccount, BigDecimal amount, String reference, String remark) {
		Transaction transaction = new Transaction();
		transaction.setType(type);
		transaction.setSourceUserAccount(sourceUserAccount);
		transaction.setAmount(amount);
		transaction.setReference(reference);
		transaction.setRemark(remark);
		transaction.setDatetime(new Date());
		transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> cropAccountToCreditorOutline(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, cropAccount, userAccount, amount, reference, remark);
	}
	
	@Override
	public List<Transaction> cropAccountToJlfexPay(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, cropAccount, userAccount, amount, reference, remark);
	}
	
	@Override
	public List<Transaction> cropAccountToZJPay(String type, User user, String cropAccountType, BigDecimal amount, String reference, String remark) {
		UserAccount userAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
		UserAccount cropAccount = userAccountRepository.findByUserIdAndType(CROP_USER_ID, cropAccountType);
		return transact(type, cropAccount, userAccount, amount, reference, remark);
	}
}

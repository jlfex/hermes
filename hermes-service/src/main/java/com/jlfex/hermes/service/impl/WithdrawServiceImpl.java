package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.Withdraw;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.WithdrawRepository;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.WithdrawService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.common.Query;

/**
 * 提现业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-21
 * @since 1.0
 */
@Service
@Transactional
public class WithdrawServiceImpl implements WithdrawService {

	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;
	
	/** 提现信息仓库 */
	@Autowired
	private WithdrawRepository withdrawRepository;
	
	/** 交易业务接口 */
	@Autowired
	private TransactionService transactionService;
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.WithdrawService#loadById(java.lang.String)
	 */
	@Override
	public Withdraw loadById(String id) {
		return withdrawRepository.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.WithdrawService#findByNameAndDateBetweenAndStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<Withdraw> findByNameAndDateBetweenAndStatus(String name, String beginDate, String endDate, String status, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		Query query = new Query("from Withdraw where 1 = 1");
		
		// 查询数据
		query.and("bankAccount.name like :name", "name", "%" + name + "%", !Strings.empty(name));
		query.and("datetime between :beginDate and :endDate", Query.dateBetween(beginDate, endDate), (!Strings.empty(beginDate) && !Strings.empty(endDate)));
		query.and("status = :status", "status", status, !Strings.empty(status));
		query.order("datetime desc");
		
		Long total = commonRepository.count(query.getCount(), query.getParams());
		List<Withdraw> withdraws = commonRepository.pageByJpql(query.getJpql(), query.getParams(), pageable.getOffset(), pageable.getPageSize(), Withdraw.class);
		
		// 返回结果
		return new PageImpl<Withdraw>(withdraws, pageable, total);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.WithdrawService#deal(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Withdraw deal(String id, String status, String remark) {
		// 读取提现记录
		Withdraw withdraw = withdrawRepository.findOne(id);
		
		// 判断记录状态
		if (!Strings.equals(Withdraw.Status.WAIT, withdraw.getStatus())) throw new ServiceException("withdraw status '" + withdraw.getStatus() + "' is illegal.");
		
		// 修改提现记录
		withdraw.setStatus(status);
		withdraw.setRemark(remark);
		withdrawRepository.save(withdraw);
		
		// 根据状态结果进行不同处理
		// 若处理成功则解冻并扣款
		// 若处理失败则解冻并扣除手续费
		if (Strings.equals(Withdraw.Status.SUCCESS, status)) {
			transactionService.unfreeze(Transaction.Type.UNFREEZE, withdraw.getUser(), withdraw.getAmount().add(withdraw.getFee()), withdraw.getId(), App.message("transaction.withdraw.success.unfreeze"));
			transactionService.toCropAccount(Transaction.Type.OUT, withdraw.getUser(), UserAccount.Type.WITHDRAW, withdraw.getAmount(), withdraw.getId(), App.message("transaction.withdraw.amount"));
			transactionService.toCropAccount(Transaction.Type.OUT, withdraw.getUser(), UserAccount.Type.WITHDRAW_FEE, withdraw.getFee(), withdraw.getId(), App.message("transaction.withdraw.fee"));
		} else if (Strings.equals(Withdraw.Status.FAILURE, status)) {
			transactionService.unfreeze(Transaction.Type.UNFREEZE, withdraw.getUser(), withdraw.getAmount().add(withdraw.getFee()), withdraw.getId(), App.message("transaction.withdraw.failure.unfreeze"));
			transactionService.toCropAccount(Transaction.Type.OUT, withdraw.getUser(), UserAccount.Type.WITHDRAW_FEE, withdraw.getFee(), withdraw.getId(), App.message("transaction.withdraw.fee"));
		} else {
			throw new ServiceException("status '" + status + "' is not defined.");
		}
		
		// 返回结果
		return withdraw;
	}
}

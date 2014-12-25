package com.jlfex.hermes.repository.n;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Model;

/**
 * 还款计划仓库扩展接口实现
 * 
 * 
 * @author Ray
 * @version 1.0, 2013-12-30
 * @since 1.0
 */
@Component
public class LoanRepayNativeRepositoryImpl implements LoanRepayNativeRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public int updateStatus(LoanRepay loanRepay, String formStatus) {

		String sql = "update hm_loan_repay l set l.repay_datetime = ? , l.amount = ? ,l.principal = ? ,l.interest = ? , l.overdue_days = ? ,l.overdue_interest = ? ,l.overdue_penalty = ? , l.status = ? ,l.updater = ? ,l.update_time = ? where l.id = ? and l.status = ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, loanRepay.getRepayDatetime());
		query.setParameter(2, loanRepay.getAmount());
		query.setParameter(3, loanRepay.getPrincipal());
		query.setParameter(4, loanRepay.getInterest());
		query.setParameter(5, loanRepay.getOverdueDays());
		query.setParameter(6, loanRepay.getOverdueInterest());
		query.setParameter(7, loanRepay.getOverduePenalty());
		query.setParameter(8, loanRepay.getStatus());
		query.setParameter(9, Model.getCurrentUserId());
		query.setParameter(10, new Date());
		query.setParameter(11, loanRepay.getId());
		query.setParameter(12, formStatus);
		return query.executeUpdate();
	}

	@Override
	public int updateStatus(String id, String fromStatus, String toStatus) {
		String sql = "update hm_loan_repay l set l.status = ? where l.id= ? and l.status = ? ";
		return em.createNativeQuery(sql).setParameter(1, toStatus).setParameter(2, id).setParameter(3, fromStatus).executeUpdate();

	}
	

	@Override
	public int overdueCalc(LoanRepay loanRepay, String formStatus) {
		String sql = "update hm_loan_repay l set  l.overdue_days = ? ,l.overdue_interest = ? ,l.overdue_penalty = ?,l.update_time = ?   where l.id = ? and l.status = ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, loanRepay.getOverdueDays());
		query.setParameter(2, loanRepay.getOverdueInterest());
		query.setParameter(3, loanRepay.getOverduePenalty());
		query.setParameter(4, new Date());
		query.setParameter(5, loanRepay.getId());
		query.setParameter(6, formStatus);
		return query.executeUpdate();
	}
}

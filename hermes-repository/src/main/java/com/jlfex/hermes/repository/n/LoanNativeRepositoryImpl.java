package com.jlfex.hermes.repository.n;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jlfex.hermes.model.Loan;

/**
 * 
 * 借款信息仓库扩展接口实现
 * @since 1.0
 */
@Component
public class LoanNativeRepositoryImpl implements LoanNativeRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public int updateStatus(String id, String fromStatus, String toStatus) {
		String sql = "update hm_loan l set l.status = ? where l.id= ? and l.status = ? ";
		return em.createNativeQuery(sql).setParameter(1, toStatus).setParameter(2, id).setParameter(3, fromStatus).executeUpdate();

	}
	
	@Override
	public int updateStatusAndAmount(String id,String fromStatus,String toStatus,BigDecimal amount){
		String sql = "update hm_loan l set l.status = ?,l.amount = ? where l.id= ? and l.status = ? ";
		return em.createNativeQuery(sql).setParameter(1, toStatus).setParameter(2, amount).setParameter(3, id).setParameter(4, fromStatus).executeUpdate();
	}

	@Override
	public int updateProceeds(String id, BigDecimal amount) {
		String sql = "update hm_loan l set l.proceeds = l.proceeds +  ? where l.id= ? and l.status = " + Loan.Status.BID + " and l.amount - l.proceeds >= ?";
		return em.createNativeQuery(sql).setParameter(1, amount).setParameter(2, id).setParameter(3, amount).executeUpdate();

	}

}

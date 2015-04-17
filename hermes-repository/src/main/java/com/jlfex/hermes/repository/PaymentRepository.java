package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Payment;

/**
 * 支付信息仓库
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment> {

	/**
	 * 通过序号查询
	 * 
	 * @param sequence
	 * @return
	 */
	public Payment findBySequence(Long sequence);
}

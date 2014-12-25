package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Payment;

/**
 * 支付信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-09
 * @since 1.0
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

	/**
	 * 通过序号查询
	 * 
	 * @param sequence
	 * @return
	 */
	public Payment findBySequence(Long sequence);
}

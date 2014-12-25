package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.PaymentLog;

/**
 * 支付日志信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-10
 * @since 1.0
 */
@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, String> {

}

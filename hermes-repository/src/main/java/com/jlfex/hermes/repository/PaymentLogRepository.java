package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.PaymentLog;

/**
 * 支付日志信息仓库
 */
@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, String>, JpaSpecificationExecutor<PaymentLog>{

}

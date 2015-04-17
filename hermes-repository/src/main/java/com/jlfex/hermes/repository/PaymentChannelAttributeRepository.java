package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.PaymentChannel;
import com.jlfex.hermes.model.PaymentChannelAttribute;

/**
 * 支付渠道参数信息仓库
 */
@Repository
public interface PaymentChannelAttributeRepository extends JpaRepository<PaymentChannelAttribute, String>, JpaSpecificationExecutor<PaymentChannelAttribute> {

	/**
	 * 通过渠道查询渠道参数
	 * 
	 * @param channel
	 * @return
	 */
	public List<PaymentChannelAttribute> findByChannel(PaymentChannel channel);
}

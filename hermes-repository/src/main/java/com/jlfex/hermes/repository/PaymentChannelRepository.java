package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.PaymentChannel;

/**
 * 支付渠道信息仓库
 * 
 */
@Repository
public interface PaymentChannelRepository extends JpaRepository<PaymentChannel, String>, JpaSpecificationExecutor<PaymentChannel> {

	/**
	 * 通过类型和状态查询支付信息渠道
	 * 
	 * @param type
	 * @param status
	 * @return
	 */
	@Query("from PaymentChannel pc where pc.type = ?1 and pc.status = ?2 order by pc.order")
	public List<PaymentChannel> findByTypeAndStatus(String type, String status);
}

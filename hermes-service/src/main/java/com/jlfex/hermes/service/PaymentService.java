package com.jlfex.hermes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.web.RequestParam;
import com.jlfex.hermes.model.Payment;
import com.jlfex.hermes.model.PaymentChannel;
import com.jlfex.hermes.model.PaymentLog;
import com.jlfex.hermes.service.payment.PaymentChannelImpl;

/**
 * 支付业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-08
 * @since 1.0
 */
public interface PaymentService {

	public static final String TYPE_RETURN	= PaymentLog.Method.RETURN;
	public static final String TYPE_NOTIFY	= PaymentLog.Method.NOTIFY;
	
	/**
	 * 分组查询所有支付渠道信息
	 * 
	 * @return
	 */
	public Map<String, List<PaymentChannel>> findChannelByGroupAll();
	
	/**
	 * 通过类型和状态查询支付渠道信息
	 * 
	 * @param type
	 * @param status
	 * @return
	 */
	public List<PaymentChannel> findChannelByTypeAndStatus(String type, String status);
	
	/**
	 * 通过类型查询有效支付渠道信息
	 * 
	 * @param type
	 * @return
	 */
	public List<PaymentChannel> findEnabledChannelByType(String type);
	
	/**
	 * 通过渠道查询参数映射
	 * 
	 * @param paymentChannel
	 * @return
	 */
	public Map<String, String> findAttributeMapByChannel(PaymentChannel paymentChannel);
	
	/**
	 * 计算充值手续费
	 * 
	 * @param amount
	 * @return
	 */
	public BigDecimal calcChargeFee(Double amount);
	
	/**
	 * 保存
	 * 
	 * @param payment
	 * @return
	 */
	public Payment save(Payment payment);
	
	/**
	 * 保存
	 * 
	 * @param channel
	 * @param amount
	 * @return
	 */
	public Payment save(String channel, Double amount);
	
	/**
	 * 支付回调
	 * 
	 * @param id
	 * @param param
	 * @param type
	 * @return
	 */
	public Result<Payment> callback(String id, RequestParam param, String type);
	
	/**
	 * 读取支付表单
	 * 
	 * @param id
	 * @param remoteIp
	 * @return
	 */
	public String getPaymentForm(String id, String remoteIp);
	
	/**
	 * 读取支付渠道实现
	 * 
	 * @param channel
	 * @return
	 */
	public PaymentChannelImpl getPaymentChannelImpl(PaymentChannel channel);
	
	/**
	 * 读取序号
	 * 
	 * @return
	 */
	public Long getSequence();
}

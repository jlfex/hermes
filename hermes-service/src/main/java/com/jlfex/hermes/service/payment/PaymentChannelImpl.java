package com.jlfex.hermes.service.payment;

import java.util.Map;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.web.RequestParam;
import com.jlfex.hermes.model.Payment;

/**
 * 支付渠道实现接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-17
 * @since 1.0
 */
public interface PaymentChannelImpl {

	/**
	 * 生成表单
	 * 
	 * @param payment
	 * @param attrs
	 * @param returnUrl
	 * @param notifyUrl
	 * @param remoteIp
	 * @return
	 */
	public PaymentForm generateForm(Payment payment, Map<String, String> attrs, String returnUrl, String notifyUrl, String remoteIp);
	
	/**
	 * 返回处理
	 * 
	 * @param param
	 * @param attrs
	 * @return
	 */
	public Result<Payment> doReturn(RequestParam param, Map<String, String> attrs);
	
	/**
	 * 通知处理
	 * 
	 * @param param
	 * @param attrs
	 * @return
	 */
	public Result<Payment> doNotify(RequestParam param, Map<String, String> attrs);
}

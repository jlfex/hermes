package com.jlfex.hermes.service.payment;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.web.RequestParam;
import com.jlfex.hermes.model.Payment;

/**
 * 模拟支付渠道实现
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-09
 * @since 1.0
 */
@Component("fakePaymentChannelImpl")
public class FakePaymentChannelImpl implements PaymentChannelImpl {

	private static final String PATTERN_AMOUNT		= "#0.00";
	
	private static final String ATTR_URL			= "url";
	
	private static final String VALUE_SEQUENCE		= "sequence";
	private static final String VALUE_AMOUNT		= "amount";
	private static final String VALUE_RETURN_URL	= "return_url";
	private static final String VALUE_NOTIFY_URL	= "notify_url";
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.payment.PaymentChannelImpl#generateForm(com.jlfex.hermes.model.Payment, java.util.Map, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PaymentForm generateForm(Payment payment, Map<String, String> attrs, String returnUrl, String notifyUrl, String remoteIp) {
		PaymentForm form = new PaymentForm(attrs.get(ATTR_URL));
		form.add(VALUE_SEQUENCE, payment.getId());
		form.add(VALUE_AMOUNT, Numbers.getNumberFormat(PATTERN_AMOUNT).format(payment.getAmount().add(payment.getFee())));
		form.add(VALUE_RETURN_URL, returnUrl);
		form.add(VALUE_NOTIFY_URL, notifyUrl);
		return form;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.payment.PaymentChannelImpl#doReturn(com.jlfex.hermes.common.web.RequestParam, java.util.Map)
	 */
	@Override
	public Result<Payment> doReturn(RequestParam param, Map<String, String> attrs) {
		Payment payment = new Payment();
		payment.setId(param.getValue("sequence"));
		payment.setStatus(Payment.Status.SUCCESS);
		return new Result<Payment>(Result.Type.SUCCESS, "", payment);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.payment.PaymentChannelImpl#doNotify(com.jlfex.hermes.common.web.RequestParam, java.util.Map)
	 */
	@Override
	public Result<Payment> doNotify(RequestParam param, Map<String, String> attrs) {
		Result<Payment> result = doReturn(param, attrs);
		result.addMessage(0, "1");
		return result;
	}
}

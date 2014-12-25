package com.jlfex.hermes.service.payment;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.RequestParam;
import com.jlfex.hermes.model.Payment;

/**
 * 国付宝支付渠道实现
 * 
 * @author ultrafrog
 * @version 1.0, 2014-02-17
 * @since 1.0
 */
@Component("goPaymentChannelImpl")
public class GoPaymentChannelImpl implements PaymentChannelImpl {

	private static final String POST_URL			= "postUrl";
	private static final String VERSION				= "version";
	private static final String CHARSET				= "charset";
	private static final String LANGUAGE			= "language";
	private static final String SIGN_TYPE			= "signType";
	private static final String TRAN_CODE			= "tranCode";
	private static final String MERCHANT_ID			= "merchantID";
	private static final String MER_ORDER_NUM		= "merOrderNum";
	private static final String TRAN_AMT			= "tranAmt";
	private static final String FEE_AMT				= "feeAmt";
	private static final String CURRENCY_TYPE		= "currencyType";
	private static final String FRONT_MER_URL		= "frontMerUrl";
	private static final String BACKGROUND_MER_URL	= "backgroundMerUrl";
	private static final String TRAN_DATE_TIME		= "tranDateTime";
	private static final String VIR_CARD_NO_IN		= "virCardNoIn";
	private static final String TRAN_IP				= "tranIP";
	private static final String IS_REPEAT_SUBMIT	= "isRepeatSubmit";
	private static final String GOODS_NAME			= "goodsName";
	private static final String GOODS_DETAIL		= "goodsDetail";
	private static final String BUYER_NAME			= "buyerName";
	private static final String BUYER_CONTACT		= "buyerContact";
	private static final String MER_REMARK_1		= "merRemark1";
	private static final String MER_REMARK_2		= "merRemark2";
	private static final String GOPAY_SERVER_TIME	= "gopayServerTime";
	//private static final String BANK_CODE			= "bankCode";
	//private static final String USER_TYPE			= "userType";
	private static final String RESP_CODE			= "respCode";
	//private static final String MSG_EXT			= "msgExt";
	private static final String ORDER_ID			= "orderId";
	private static final String GOPAY_OUT_ORDER_ID	= "gopayOutOrderId";
	//private static final String TRAN_FINISH_TIME	= "tranFinishTime";
	private static final String VERFICATION_CODE	= "VerficationCode";
	private static final String SIGN_VALUE			= "signValue";
	
	private static final String RESP_CODE_SUCCESS	= "0000";
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.payment.PaymentChannelImpl#generateForm(com.jlfex.hermes.model.Payment, java.util.Map, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PaymentForm generateForm(Payment payment, Map<String, String> attrs, String returnUrl, String notifyUrl, String remoteIp) {
		// 设置参数
		PaymentForm form = new PaymentForm(attrs.get(POST_URL));
		form.add(VERSION, attrs.get(VERSION));
		form.add(CHARSET, attrs.get(CHARSET));
		form.add(LANGUAGE, attrs.get(LANGUAGE));
		form.add(SIGN_TYPE, attrs.get(SIGN_TYPE));
		form.add(TRAN_CODE, attrs.get(TRAN_CODE));
		form.add(MERCHANT_ID, attrs.get(MERCHANT_ID));
		form.add(MER_ORDER_NUM, String.valueOf(payment.getSequence()));
		form.add(TRAN_AMT, Numbers.getNumberFormat("#0.00").format(payment.getAmount().add(payment.getFee())));
		form.add(FEE_AMT, "");
		form.add(CURRENCY_TYPE, attrs.get(CURRENCY_TYPE));
		form.add(FRONT_MER_URL, returnUrl);
		form.add(BACKGROUND_MER_URL, notifyUrl);
		form.add(TRAN_DATE_TIME, Calendars.format("yyyyMMddHHmmss"));
		form.add(VIR_CARD_NO_IN, attrs.get(VIR_CARD_NO_IN));
		form.add(TRAN_IP, remoteIp);
		form.add(IS_REPEAT_SUBMIT, attrs.get(IS_REPEAT_SUBMIT));
		form.add(GOODS_NAME, attrs.get(GOODS_NAME));
		form.add(GOODS_DETAIL, attrs.get(GOODS_NAME));
		form.add(BUYER_NAME, "");
		form.add(BUYER_CONTACT, "");
		form.add(MER_REMARK_1, payment.getId());
		form.add(MER_REMARK_2, "");
		form.add(SIGN_VALUE, sign(form.get(VERSION), form.get(TRAN_CODE), form.get(MERCHANT_ID), form.get(MER_ORDER_NUM), 
				form.get(TRAN_AMT), form.get(FEE_AMT), form.get(TRAN_DATE_TIME), form.get(FRONT_MER_URL), form.get(BACKGROUND_MER_URL), 
				"", "", form.get(TRAN_IP), "", "", attrs.get(VERFICATION_CODE)));
		
		// 返回结果
		return form;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.payment.PaymentChannelImpl#doReturn(com.jlfex.hermes.common.web.RequestParam, java.util.Map)
	 */
	@Override
	public Result<Payment> doReturn(RequestParam param, Map<String, String> attrs) {
		// 初始化
		Payment payment = new Payment();
		payment.setId(param.getValue(MER_REMARK_1));
		
		// 验证数据签名
		String sign = sign(param.getValue(VERSION), param.getValue(TRAN_CODE), param.getValue(MERCHANT_ID), param.getValue(MER_ORDER_NUM), 
				param.getValue(TRAN_AMT), param.getValue(FEE_AMT), param.getValue(TRAN_DATE_TIME), param.getValue(FRONT_MER_URL), param.getValue(BACKGROUND_MER_URL), 
				param.getValue(ORDER_ID), param.getValue(GOPAY_OUT_ORDER_ID), param.getValue(TRAN_IP), param.getValue(RESP_CODE), "", attrs.get(VERFICATION_CODE));
		if (!Strings.equals(sign, param.getValue(SIGN_VALUE))) return new Result<Payment>(Type.FAILURE, "sign is valid.", payment);
		
		// 判断响应信息
		if (!Strings.equals(RESP_CODE_SUCCESS, param.getValue(RESP_CODE))) return new Result<Payment>(Type.FAILURE, param.getValue(RESP_CODE), payment);
		
		// 响应成功
		return new Result<Payment>(Type.SUCCESS, RESP_CODE_SUCCESS, payment);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.payment.PaymentChannelImpl#doNotify(com.jlfex.hermes.common.web.RequestParam, java.util.Map)
	 */
	@Override
	public Result<Payment> doNotify(RequestParam param, Map<String, String> attrs) {
		Result<Payment> result = doReturn(param, attrs);
		result.addMessage(0, String.format("RespCode=%s|JumpURL=%s", (result.success() ? "0000" : "9999"), param.getValue(FRONT_MER_URL)));
		return result;
	}
	
	/**
	 * 签名
	 * 
	 * @param version
	 * @param tranCode
	 * @param merchantId
	 * @param merOrderNum
	 * @param tranAmt
	 * @param feeAmt
	 * @param tranDateTime
	 * @param frontMerUrl
	 * @param backgroundMerUrl
	 * @param orderId
	 * @param gopayOutOrderId
	 * @param tranIp
	 * @param respCode
	 * @param gopayServerTime
	 * @param verficationCode
	 * @return
	 */
	protected String sign(String version, String tranCode, String merchantId, String merOrderNum, String tranAmt, String feeAmt, String tranDateTime,
			String frontMerUrl, String backgroundMerUrl, String orderId, String gopayOutOrderId, String tranIp, String respCode, String gopayServerTime,
			String verficationCode) {
		String sign = String.format("%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]%s=[%s]", 
				VERSION, version,
				TRAN_CODE, tranCode,
				MERCHANT_ID, merchantId,
				MER_ORDER_NUM, merOrderNum,
				TRAN_AMT, tranAmt,
				FEE_AMT, feeAmt,
				TRAN_DATE_TIME, tranDateTime,
				FRONT_MER_URL, frontMerUrl,
				BACKGROUND_MER_URL, backgroundMerUrl,
				ORDER_ID, orderId,
				GOPAY_OUT_ORDER_ID, gopayOutOrderId,
				TRAN_IP, tranIp,
				RESP_CODE, respCode,
				GOPAY_SERVER_TIME, gopayServerTime);
		Logger.info("gopay sign string: %s", sign);
		return Strings.md5(sign + VERFICATION_CODE + "=[" + verficationCode + "]");
	}
}

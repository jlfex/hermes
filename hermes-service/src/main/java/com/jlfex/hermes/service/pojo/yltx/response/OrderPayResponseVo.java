package com.jlfex.hermes.service.pojo.yltx.response;
import java.io.Serializable;
import com.jlfex.hermes.common.utils.Strings;


public class OrderPayResponseVo  implements Serializable {

	/**
	 * 下单并支付接口响应实体
	 */
	private static final long serialVersionUID = 3185383668661579875L;

	private  String orderCode;			//订单编号
	private  String guaranteePdfId;		//担保承诺函编号
	private  String loanPdfId;			//债权转让及回购协议编号
	private  String paymentPdfId;		//支付协议编号
	private  String orderStatus;		//订单状态
	private  String payStatus;			//支付状态
	
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getGuaranteePdfId() {
		return guaranteePdfId;
	}
	public void setGuaranteePdfId(String guaranteePdfId) {
		this.guaranteePdfId = guaranteePdfId;
	}
	public String getLoanPdfId() {
		return loanPdfId;
	}
	public void setLoanPdfId(String loanPdfId) {
		this.loanPdfId = loanPdfId;
	}
	public String getPaymentPdfId() {
		return paymentPdfId;
	}
	public void setPaymentPdfId(String paymentPdfId) {
		this.paymentPdfId = paymentPdfId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public void  toTrim(){
		if(Strings.notEmpty(orderCode)){
			orderCode = orderCode.trim();
		}
		if(Strings.notEmpty(guaranteePdfId)){
			guaranteePdfId = guaranteePdfId.trim();
		}
		if(Strings.notEmpty(loanPdfId)){
			loanPdfId = loanPdfId.trim();
		}
		if(Strings.notEmpty(paymentPdfId)){
			paymentPdfId = paymentPdfId.trim();
		}
		if(Strings.notEmpty(orderStatus)){
			orderStatus = orderStatus.trim();
		}
		if(Strings.notEmpty(payStatus)){
			payStatus = payStatus.trim();
		}
	}
	
}

package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单查询结果
 * @author Administrator
 *
 */
public class OrderVo extends OrderResponseVo implements Serializable  {

	
	private static final long serialVersionUID = 3371539797766830988L;
	
	private  String 		name;					//姓名
	private  String 		financeProductId;		//理财产品编号
	private  BigDecimal		orderAmt;				//订单金额
	private  Date 			orderCreateDate;		//订单创建日期
	private  String 		orderStatus;			//订单状态
	private  String 		loanFile;				//债权转让协议编号
	private  String 		guaranteeFile;			//担保确认函编号
	private  String 		paymentAgreeFile;		//支付协议编号
	private  String 		orderCode;				//订单编号
	private  String 		payStatus;				//支付状态
	private  String 		childAssetId;			//子资产编号
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFinanceProductId() {
		return financeProductId;
	}
	public void setFinanceProductId(String financeProductId) {
		this.financeProductId = financeProductId;
	}
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
	public Date getOrderCreateDate() {
		return orderCreateDate;
	}
	public void setOrderCreateDate(Date orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getLoanFile() {
		return loanFile;
	}
	public void setLoanFile(String loanFile) {
		this.loanFile = loanFile;
	}
	public String getGuaranteeFile() {
		return guaranteeFile;
	}
	public void setGuaranteeFile(String guaranteeFile) {
		this.guaranteeFile = guaranteeFile;
	}
	public String getPaymentAgreeFile() {
		return paymentAgreeFile;
	}
	public void setPaymentAgreeFile(String paymentAgreeFile) {
		this.paymentAgreeFile = paymentAgreeFile;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getChildAssetId() {
		return childAssetId;
	}
	public void setChildAssetId(String childAssetId) {
		this.childAssetId = childAssetId;
	}
	
	
	
	
	

	
}

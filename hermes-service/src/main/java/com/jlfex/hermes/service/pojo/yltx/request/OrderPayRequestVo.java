package com.jlfex.hermes.service.pojo.yltx.request;

import java.math.BigDecimal;

public class OrderPayRequestVo  {

	/**
	 * 开发平台下单并支付 请求报文模型
	 */

	private  String name;				//姓名 				必填项	30		
	private  String certiNum;			//证件号				必填项    18
	private  String certiType;			//证件类型			必填项    10
	private  String financeProductId;	//理财产品编号		必填项    19
	private  BigDecimal orderAmt;		//订单金额			必填项  (17,2)
	private  String bankName;			//开户行				必填项    18
	private  String subbranchProvince;	//开户行所在省份		必填项    10
	private  String subbranchCity;		//开户行所在城市		必填项	10
	private  String bankSubbranch;		//开户支行			必填项	30
	private  String bankAccount;		//银行卡号			必填项	25
	private  String address;			//地址					可选项 100
	private  String postalCode;			//邮编					可选项 6
	private  String mobile;				//手机号   				可选项11
	private  String orderSn;			//订单流水  			必填项  HERMES +12位递增序列。
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertiNum() {
		return certiNum;
	}
	public void setCertiNum(String certiNum) {
		this.certiNum = certiNum;
	}
	public String getCertiType() {
		return certiType;
	}
	public void setCertiType(String certiType) {
		this.certiType = certiType;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getSubbranchProvince() {
		return subbranchProvince;
	}
	public void setSubbranchProvince(String subbranchProvince) {
		this.subbranchProvince = subbranchProvince;
	}
	public String getSubbranchCity() {
		return subbranchCity;
	}
	public void setSubbranchCity(String subbranchCity) {
		this.subbranchCity = subbranchCity;
	}
	public String getBankSubbranch() {
		return bankSubbranch;
	}
	public void setBankSubbranch(String bankSubbranch) {
		this.bankSubbranch = bankSubbranch;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	

}

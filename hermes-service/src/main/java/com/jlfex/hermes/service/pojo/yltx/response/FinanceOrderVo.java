package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jlfex.hermes.common.utils.Strings;

public class FinanceOrderVo implements  Serializable {

	/**
	 * 理财产品订单明细
	 */
	private static final long serialVersionUID = 6298634387872143959L;
	
	private String financeProductName ;          //理财产品名称
	private BigDecimal interestRate;		     //年化利率
	private String     timeLimit;				 //期限
	private BigDecimal limit;				     //发布金额
	private BigDecimal labelVolmoney;            //成交金额
	private BigDecimal purchaseAmount;		     //起购金额
	private String assetRating;					 //风险评级
	private String assetGuaranteeRate;			 //资产保证费率
	private String assetGuarantee;				 //资产保证方式
	private String managementRate;				 //投资管理费率
	private Date   dateOfValue;					 //起息日期
	private String fundPurpose;					 //资金用途
	private String institutionInstruction;       //机构介绍
	private String taxExplain;					 //税务说明
	private String riskTip;						 //风险提示
	private String status;						 //理财产品状态
	private String id;							 //理财产品编号
	private Date raiseStartTime; 				 //募资开始时间
	private Date raiseEndTime; 				     //募资截止时间
	private List<AssetVo> assetsList;            //资产信息
	
	
	public String getFinanceProductName() {
		return financeProductName;
	}
	public void setFinanceProductName(String financeProductName) {
		this.financeProductName = financeProductName;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public BigDecimal getLimit() {
		return limit;
	}
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public String getAssetRating() {
		return assetRating;
	}
	public void setAssetRating(String assetRating) {
		this.assetRating = assetRating;
	}
	public String getAssetGuaranteeRate() {
		return assetGuaranteeRate;
	}
	public void setAssetGuaranteeRate(String assetGuaranteeRate) {
		this.assetGuaranteeRate = assetGuaranteeRate;
	}
	public String getAssetGuarantee() {
		return assetGuarantee;
	}
	public void setAssetGuarantee(String assetGuarantee) {
		this.assetGuarantee = assetGuarantee;
	}
	public String getManagementRate() {
		return managementRate;
	}
	public void setManagementRate(String managementRate) {
		this.managementRate = managementRate;
	}
	public Date getDateOfValue() {
		return dateOfValue;
	}
	public void setDateOfValue(Date dateOfValue) {
		this.dateOfValue = dateOfValue;
	}
	public String getFundPurpose() {
		return fundPurpose;
	}
	public void setFundPurpose(String fundPurpose) {
		this.fundPurpose = fundPurpose;
	}
	public String getInstitutionInstruction() {
		return institutionInstruction;
	}
	public void setInstitutionInstruction(String institutionInstruction) {
		this.institutionInstruction = institutionInstruction;
	}
	public String getTaxExplain() {
		return taxExplain;
	}
	public void setTaxExplain(String taxExplain) {
		this.taxExplain = taxExplain;
	}
	public String getRiskTip() {
		return riskTip;
	}
	public void setRiskTip(String riskTip) {
		this.riskTip = riskTip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getRaiseStartTime() {
		return raiseStartTime;
	}
	public void setRaiseStartTime(Date raiseStartTime) {
		this.raiseStartTime = raiseStartTime;
	}
	public Date getRaiseEndTime() {
		return raiseEndTime;
	}
	public void setRaiseEndTime(Date raiseEndTime) {
		this.raiseEndTime = raiseEndTime;
	}
	public List<AssetVo> getAssetsList() {
		return assetsList;
	}
	public void setAssetsList(List<AssetVo> assetsList) {
		this.assetsList = assetsList;
	}
	public BigDecimal getLabelVolmoney() {
		return labelVolmoney;
	}
	public void setLabelVolmoney(BigDecimal labelVolmoney) {
		this.labelVolmoney = labelVolmoney;
	}
	
	
	public  void toTrim(){
		if(Strings.notEmpty(financeProductName)){
			this.financeProductName = financeProductName.trim();
		}
		if(Strings.notEmpty(timeLimit)){
			timeLimit = timeLimit.trim();
		}
		if(Strings.notEmpty(assetRating)){
			this.assetRating = assetRating.trim();
		}
		if(Strings.notEmpty(assetGuaranteeRate)){
			this.assetGuaranteeRate = assetGuaranteeRate.trim();
		}
		if(Strings.notEmpty(assetGuarantee)){
			this.assetGuarantee = assetGuarantee.trim();
		}
		if(Strings.notEmpty(managementRate)){
			this.managementRate = managementRate.trim();
		}
		if(Strings.notEmpty(fundPurpose)){
			this.fundPurpose = fundPurpose.trim();
		}
		if(Strings.notEmpty(institutionInstruction)){
			this.institutionInstruction = institutionInstruction.trim();
		}
		if(Strings.notEmpty(taxExplain)){
			this.taxExplain = taxExplain.trim();
		}
		if(Strings.notEmpty(riskTip)){
			this.riskTip = riskTip.trim();
		}
		if(Strings.notEmpty(status)){
			this.status = status.trim();
		}
		if(Strings.notEmpty(id)){
			this.id = id.trim();
		}
		
	}
}

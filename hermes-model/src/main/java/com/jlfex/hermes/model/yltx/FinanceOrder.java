package com.jlfex.hermes.model.yltx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Model;

@Entity
@Table(name = "hm_finance_order" , uniqueConstraints={@UniqueConstraint(columnNames = {"uniq_id" })}) 
@JsonIgnoreProperties(value = {"assetsList" })
public class FinanceOrder extends Model{

	/**
	 * 理财产品 模型
	 */
	private static final long serialVersionUID = -8190623834091788691L;
	
	@Column(name = "finance_name", length=200)
	private String financeProductName ;          //理财产品名称
	@Column(name = "interest_rate", precision=16, scale=6,nullable=false)
	private BigDecimal interestRate;		     //年化利率 
	@Column(name = "time_limit")
	private String timeLimit;					 //期限
	@Column(name = "limit_amount", nullable=false)
	private BigDecimal limit;					 //发布金额
	@Column(name = "labelVol_money")
	private BigDecimal labelVolmoney;            //成交金额
	@Column(name = "purchase_amount")
	private BigDecimal purchaseAmount;		     //起购金额
	@Column(name = "asset_rating")
	private String assetRating;					 //风险评级
	@Column(name = "asset_guarantee_rate")
	private String assetGuaranteeRate;			 //资产保证费率
	@Column(name = "asset_guarantee")
	private String assetGuarantee;				 //资产保证方式
	@Column(name = "management_rate")
	private String managementRate;				 //投资管理费率
	@Column(name = "dateof_value",nullable=false)
	private Date   dateOfValue;					 //起息日期
	@Column(name = "fund_purpose")
	private String fundPurpose;					 //资金用途
	@Column(name = "institution_instruction")
	private String institutionInstruction;       //机构介绍
	@Column(name = "tax_explain")
	private String taxExplain;					 //税务说明
	@Column(name = "risk_tip")
	private String riskTip;						 //风险提示
	@Column(name = "status")
	private String status;						 //理财产品状态
	@Column(name = "uniq_id" , nullable = false)
	private String  uniqId;					     //理财产品编号
	@Column(name = "raise_start_time")
	private Date raiseStartTime; 				 //募资开始时间
	@Column(name = "raise_end_time")
	private Date raiseEndTime; 				     //募资截止时间
	@OneToMany(mappedBy = "financeOrder", fetch = FetchType.LAZY)
	private Set<Asset> assetsList = new  HashSet<Asset>() ;            //资产信息
	@Column(name = "deal_status")
	private String  dealStatus;					  //处理状态

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

	public BigDecimal getLabelVolmoney() {
		return labelVolmoney;
	}

	public void setLabelVolmoney(BigDecimal labelVolmoney) {
		this.labelVolmoney = labelVolmoney;
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

	
	public String getUniqId() {
		return uniqId;
	}

	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
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

	public Set<Asset> getAssetsList() {
		return assetsList;
	}

	public void setAssetsList(Set<Asset> assetsList) {
		this.assetsList = assetsList;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	
	/**
	 * 理财信息 处理状态
	 * @author Administrator
	 *
	 */
	public static final class DealStatus {
		@Element("未处理")
		public static final String WAIT_DEAL 			= "00";
		@Element("发布成功")
		public static final String DEAL_SUC 			= "01";
		@Element("发布失败")
		public static final String DEAL_FAIL 			= "02";
		
	}
	
	
	
	

}

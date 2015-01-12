package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 外部：债权信息 模型
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "hm_credite_info")
public class CrediteInfo extends Model {

	private static final long serialVersionUID = 2519547104206211678L;

	/** 债权人 */
	@ManyToOne
	@JoinColumn(name = "creditor")
	private Creditor creditor;
	// 债权编号
	@Column(name = "acedite_code")
	private String crediteCode;
	// 债权类型
	@Column(name = "acedite_type")
	private String crediteType;
	// 借款人(债权的原始借款人)
	@Column(name = "borrower")
	private String borrower;
	// 借款人证件类型
	@Column(name = "cert_type")
	private String certType;
	// 借款人证件号码
	@Column(name = "certificate_no")
	private String certificateNo;
	// 行业
	@Column(name = "work_type")
	private String workType;
	// 省份
	@Column(name = "province")
	private String province;
	// 城市
	@Column(name = "city")
	private String city;
	// 借款金额
	@Column(name = "amount")
	private BigDecimal amount;
	// 年利率
	@Column(name = "rate")
	private String rate;
	// 借款期限
	@Column(name = "period")
	private String period;
	// 资金用途
	@Column(name = "purpose")
	private String purpose;
	// 还款方式
	@Column(name = "pay_type")
	private String payType;
	// 债权到期日
	@Column(name = "dead_time")
	private String dead_time;
	// 放款日 (债权对应的原始借款人还款)
	@Column(name = "business_time")
	private String businessTime;
	@Column(name = "status")
	private String status;
	// 招标结束日期
	@Column(name = "bid_end_time")
	private Date bidEndTime;
	// 导入结果
	@Transient
	private String dealResult;
	// 备注
	@Transient
	private String remark;

	public String getCrediteCode() {
		return crediteCode;
	}

	public void setCrediteCode(String crediteCode) {
		this.crediteCode = crediteCode;
	}

	public String getCrediteType() {
		return crediteType;
	}

	public void setCrediteType(String crediteType) {
		this.crediteType = crediteType;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getDead_time() {
		return dead_time;
	}

	public void setDead_time(String dead_time) {
		this.dead_time = dead_time;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Creditor getCreditor() {
		return creditor;
	}

	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	public static final class Status {
		@Element("等待转让")
		public static final String WAIT_ASSIGN = "00";
		@Element("转让中")
		public static final String ASSIGNING = "01";
		@Element("转让成功")
		public static final String SUCC_ASSIGN = "02";
		@Element("转让失败")
		public static final String FAIL_ASSIGN = "03";

	}

}

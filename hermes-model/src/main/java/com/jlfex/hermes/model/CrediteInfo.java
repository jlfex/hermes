package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
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
	//债权编号
	@Column(name = "credit_code")
	private String crediteCode;
	//债权类型
	@Column(name = "credite_type")
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
	private BigDecimal rate ;
	//借款期限
	@Column(name = "period")
	private Integer  period ;
	//借款用途
	@Column(name = "purpose")
	private String purpose;
	// 还款方式
	@Column(name = "pay_type")
	private String payType;
	// 债权到期日
	@Column(name = "dead_time")
	private Date deadTime ;
	//放款日 (债权对应的原始借款人还款)
	@Column(name = "business_time")
	private Date businessTime ;
	@Column(name = "status")
	private String status ;
	//资金用途
	@Column(name = "amount_aim")
	private String  amountAim;
	//产品介绍
	@Column(name = "product_desc")
	private String produnctDesc;
	//担保方式
	@Column(name = "assure_type")
	private String  assureType;
	// 招标结束日期
	@Column(name = "bid_end_time")
	private Date bidEndTime;
	// 备注
	@Column(name = "remark")
	private String remark;
	// 债权 借款管理费
	@Column(name = "manage_fee")
	private BigDecimal manageFee;
	
	@Transient
	private String  bidEndTimeStr;
	@Transient
	private String  creditorName;
	@Transient
	private String  ratePercent;
	
	

    
	
	public Creditor getCreditor() {
		return creditor;
	}

	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}

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

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
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

	public Date getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}

	public Date getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmountAim() {
		return amountAim;
	}

	public void setAmountAim(String amountAim) {
		this.amountAim = amountAim;
	}

	public String getProdunctDesc() {
		return produnctDesc;
	}

	public void setProdunctDesc(String produnctDesc) {
		this.produnctDesc = produnctDesc;
	}

	public String getAssureType() {
		return assureType;
	}

	public void setAssureType(String assureType) {
		this.assureType = assureType;
	}

	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
	public String getBidEndTimeStr() {
		return bidEndTimeStr;
	}

	public void setBidEndTimeStr(String bidEndTimeStr) {
		this.bidEndTimeStr = bidEndTimeStr;
	}
    
	public BigDecimal getManageFee() {
		return manageFee;
	}

	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}
	
	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	

	public String getRatePercent() {
		if(rate !=null){
			rate = rate.multiply(new BigDecimal(100));
		}
		return rate.toString();
	}

	/**
	 * 读取当前用户名称
	 * 
	 * @return
	 */
	public String getCurrentUserName() {
		try {
			AppUser user = App.user();
			String name = App.user().getName();
			return name;
		} catch (Exception e) {
			Logger.warn("can not get current user id for reason: " + e.getMessage());
			return null;
		}
	}

	public static final class Status {
		@Element("待发售")
		public static final String WAIT_ASSIGN = "00";
		@Element("已发售")
		public static final String ASSIGNING = "01";
		@Element("转让成功")
		public static final String SUCC_ASSIGN = "02";
		@Element("转让失败")
		public static final String FAIL_ASSIGN = "03";
		@Element("导入失败")
		public static final String IMP_FAIL = "04";
	}

}

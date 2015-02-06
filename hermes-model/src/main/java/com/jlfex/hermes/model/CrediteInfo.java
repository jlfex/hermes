package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	// 债权编号
	@Column(name = "credit_code")
	private String crediteCode;
	// 债权类型
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
	//原始  借款金额 
	@Column(name = "amount")
	private BigDecimal amount;
	//转让 金额 
	@Column(name = "sell_amount")
	private BigDecimal sellAmount;
	// 年利率
	@Column(name = "rate")
	private BigDecimal rate;
	// 原始 ： 借款期限
	@Column(name = "period")
	private Integer period;
	// 招标期限
	private Integer deadLine;
	// 剩余期数
	@Column(name = "term_num")
	private Integer termNum;
	// 借款用途
	@Column(name = "purpose")
	private String purpose;
	// 还款方式
	@Column(name = "pay_type")
	private String payType;
	// 债权到期日
	@Column(name = "dead_time")
	private Date deadTime;
	// 放款日 (债权对应的原始借款人还款)
	@Column(name = "business_time")
	private Date businessTime;
	@Column(name = "status")
	private String status;
	// 资金用途
	@Column(name = "amount_aim")
	private String amountAim;
	// 产品介绍
	@Column(name = "product_desc")
	private String productDesc;
	// 担保方式
	@Column(name = "assure_type")
	private String assureType;
	// 招标结束日期
	@Column(name = "bid_end_time")
	private Date bidEndTime;
	// 转让日期
	@Column(name = "assign_time")
	private Date assignTime;
	// 备注
	@Column(name = "remark")
	private String remark;
	// 债权 借款管理费
	@Column(name = "manage_fee")
	private BigDecimal manageFee;

	public Integer getTermNum() {
		return termNum;
	}

	public void setTermNum(Integer termNum) {
		this.termNum = termNum;
	}

	@Transient
	private String bidEndTimeStr;
	@Transient
	private String creditorName;
	@Transient
	private String ratePercent;
	@Transient
	private String beginDate; // 开始时间 用于检索导入时间段
	@Transient
	private String endDate; // 结束时间 用于检索导入时间段

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
	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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

	public Integer getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Integer deadLine) {
		this.deadLine = deadLine;
	}

	public String getRatePercent() {
		if (rate != null) {
			rate = rate.multiply(new BigDecimal(100));
		}
		return rate.toString();
	}

	public String getDeadTimeFormate() {
		String dateStr = "";
		if (deadTime != null) {
			dateStr = new SimpleDateFormat("yyyy-MM-dd").format(deadTime);
		}
		return dateStr;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
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

	public Boolean getOutOfDate() {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(deadTime);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MILLISECOND, 0);
		return c1.getTimeInMillis() < c2.getTimeInMillis();

	}

	public static final class Status {
		@Element("待发售")
		public static final String WAIT_ASSIGN = "00";
		@Element("投标中")
		public static final String BIDING = "01";
		@Element("还款中")
		public static final String REPAYING = "02";
		@Element("已还清")
		public static final String REPAY_FIINISH = "03";
		@Element("验证失败")
		public static final String IMP_FAIL = "04";
		@Element("已过期")
		public static final String FAIL_ASSIGNING = "05";
	}

}

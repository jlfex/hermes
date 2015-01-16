package com.jlfex.hermes.console.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jlfex.hermes.common.dict.Element;


/**
 * 外部债权导入：债权信息 vo
 * @author Administrator
 *
 */

public class CreditInfoVo implements Serializable    {

	private static final long serialVersionUID = -1577366798088643327L;
	
	private String  creditorNo; 	 //债权人编号
	private String  creditCode; 	 //债权编号
	private String  creditKind; 	 //债权类型
	private String  borrower;  	     //借款人
	private String  certType; 	     //借款人证件类型
	private String  certificateNo;   //借款人证件号码
	private String  workType ;       //行业
	private String  province ;       //省份
	private String  city ;           //城市
	private BigDecimal  amount;      //借款金额
	private BigDecimal  rate ;       //年利率
	private String  period;          //借款期限
	private String  purpose ;        //借款用途
	private String  payType;         //还款方式
	private Date    deadTime;        //债权到期日
	private Date    businessTime;    //放款日
	private String  status;      //导入结果
	private String  remark;          //备注
	private String  fileName;        //文件名称
	
	
	
	public String getCreditorNo() {
		return creditorNo;
	}
	public void setCreditorNo(String creditorNo) {
		this.creditorNo = creditorNo;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getCreditKind() {
		return creditKind;
	}
	public void setCreditKind(String creditKind) {
		this.creditKind = creditKind;
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
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static final class  Status{
		@Element("格式正常")
		public static final String VALID = "00";
		@Element("格式异常")
		public static final String INVALID = "01";
	}
	
}

package com.jlfex.hermes.service.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.Loan;

public class LoanAuditInfo implements Serializable {

	private static final long serialVersionUID = 8821546602704510226L;
	/** 昵称 */
	private String id;
	/** 昵称 */
	private String account;
	/** 借款编号 */
	private String loanNo;
	/** 借款金额 */
	private BigDecimal amount;
	/** 借款满标时间 */
	private Date fullDateTime;
	/** 借款状态 */
	private String status;
	/** 借款人真实姓名 */
	private String realName;
	/** 借款人手机号码 */
	private String cellphone;
	/** 借款人期限 */
	private Integer period;
	/** 借款人利率 */
	private String rate;
	/** 借款发布日期 */
	private Date datetime;
	/** 标的类型 */
	private String loanKind;

	public String getStatusName() {
		return Dicts.name(status, "-", Loan.Status.class);
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getFullDateTime() {
		return fullDateTime;
	}

	public void setFullDateTime(Date fullDateTime) {
		this.fullDateTime = fullDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}

}

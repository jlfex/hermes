package com.jlfex.hermes.service.pojo;

import java.io.Serializable;
import java.util.Date;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.InvestProfit;

/**
 * 理财收益信息
 * 
 * @author chenqi
 * @version 1.0, 2014-01-07
 * @since 1.0
 */
public class InvestProfitInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7557617187719673945L;

	/** 期数 */
	private String sequence;

	/** 期限 */
	private String period;

	/** 预计还款日 */
	private Date planDatetime;

	/** 待收总额 */
	private String amount;

	/** 待收本金 */
	private String principal;

	/** 待收利息 */
	private String interest;

	/** 罚息 */
	private String overdueInterest;

	/** 预期天数 */
	private String overdueDays;
	
	/** 状态*/
	private String status;
	
	/** 标的类型*/
	private String loanKind;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return Dicts.name(status, status, InvestProfit.Status.class);
	}
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getPlanDatetime() {
		return planDatetime;
	}

	public void setPlanDatetime(Date planDatetime) {
		this.planDatetime = planDatetime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(String overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public String getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getLoanKind() {
		return loanKind;
	}
	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}
	
}

package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

public class LoanLogVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8730412227028335291L;

	/**
	 * 标编号
	 */
	private String loanNo;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 开始时间
	 */
	private String beginDate;

	/**
	 * 结束时间
	 */
	private String endDate;

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}

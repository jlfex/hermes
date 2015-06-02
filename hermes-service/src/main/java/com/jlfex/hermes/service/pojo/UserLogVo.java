package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

public class UserLogVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1683000205765560756L;
	
	/**
	 * 邮件
	 */
	private String email;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = -6137546174092886755L;

	/** 编号 */
	private String id;
	/** 昵称 */
	private String account;
	/** 手机号码 */
	private String cellphone;
	/** 姓名 */
	private String realName;
	/** 总金额 */
	private String total;
	/** 冻结金额 */
	private String freeze;
	/** 可用金额 */
	private String free;

	/** 用户类型 */
	private String type;
	/**
	 * 读取
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置
	 * 
	 * @param account
	 * @see #account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #cellphone
	 */
	public String getCellphone() {
		return cellphone;
	}

	/**
	 * 设置
	 * 
	 * @param cellphone
	 * @see #cellphone
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置
	 * 
	 * @param realName
	 * @see #realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * 设置
	 * 
	 * @param total
	 * @see #total
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #freeze
	 */
	public String getFreeze() {
		return freeze;
	}

	/**
	 * 设置
	 * 
	 * @param freeze
	 * @see #freeze
	 */
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #free
	 */
	public String getFree() {
		return free;
	}

	/**
	 * 设置
	 * 
	 * @param free
	 * @see #free
	 */
	public void setFree(String free) {
		this.free = free;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(String type) {
		this.type = type;
	}


}

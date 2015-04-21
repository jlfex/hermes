package com.jlfex.hermes.common;

import java.io.Serializable;

/**
 * 应用用户
 */
public class AppUser implements Serializable {

	private static final long serialVersionUID = 4096472565439610067L;

	/** 编号 */
	private String id;
	
	/** 帐号 */
	private String account;
	
	/** 名称 */
	private String name;
	
	/**
	 * 构造函数
	 */
	public AppUser() {}
	
	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param account
	 * @param name
	 */
	public AppUser(String id, String account, String name) {
		this.id = id;
		this.account = account;
		this.name = name;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param account
	 */
	public AppUser(String id, String account) {
		this(id, account, null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param id
	 */
	public AppUser(String id) {
		this(id, null);
	}

	/**
	 * 读取编号
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取帐号
	 * 
	 * @return
	 * @see #account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置帐号
	 * 
	 * @param account
	 * @see #account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getId();
	}
}

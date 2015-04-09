package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 银行帐号信息模型
 */
@Entity
@Table(name = "hm_bank_account")
public class BankAccount extends Model {

	private static final long serialVersionUID = -8229728021471501358L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	/** 银行 */
	@ManyToOne
	@JoinColumn(name = "bank")
	private Bank bank;

	/** 城市 */
	@ManyToOne
	@JoinColumn(name = "city")
	private Area city;

	/** 开户支行 */
	@Column(name = "deposit")
	private String deposit;

	/** 开户人 */
	@Column(name = "name")
	private String name;

	/** 帐号 */
	@Column(name = "account")
	private String account;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/**
	 * 读取用户
	 * 
	 * @return
	 * @see #user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param user
	 * @see #user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 读取银行
	 * 
	 * @return
	 * @see #bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * 设置银行
	 * 
	 * @param bank
	 * @see #bank
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * 读取城市
	 * 
	 * @return
	 * @see #city
	 */
	public Area getCity() {
		return city;
	}

	/**
	 * 设置城市
	 * 
	 * @param city
	 * @see #city
	 */
	public void setCity(Area city) {
		this.city = city;
	}

	/**
	 * 读取开户行
	 * 
	 * @return
	 * @see #deposit
	 */
	public String getDeposit() {
		return deposit;
	}

	/**
	 * 设置开户行
	 * 
	 * @param deposit
	 * @see #deposit
	 */
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	/**
	 * 读取开户人
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置开户人
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取尾号
	 * 
	 * @return
	 */
	public String getAccountLast() {
		if (!Strings.empty(account)) {
			return account.substring(account.length() - 4, account.length());
		}
		return null;
	}

	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 状态
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-13
	 * @since 1.0
	 */
	public static final class Status {

		@Element("有效")
		public static final String ENABLED = "00";

		@Element("无效")
		public static final String DISABLED = "99";
	}
}

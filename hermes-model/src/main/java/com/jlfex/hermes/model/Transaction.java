package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 交易流水信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_transaction")
public class Transaction extends Model {
	
	private static final long serialVersionUID = 7986413260426209969L;

	/** 来源账户 */
	@ManyToOne
	@JoinColumn(name = "source_user_account")
	private UserAccount sourceUserAccount;
	
	/** 目标账户 */
	@ManyToOne
	@JoinColumn(name = "target_user_account")
	private UserAccount targetUserAccount;
	
	/** 关系 */
	@Column(name = "reference")
	private String reference;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;
	
	/** 金额 */
	@Column(name = "amount")
	private BigDecimal amount;
	
	/** 来源账户交易前余额 */
	@Column(name = "source_before_balance")
	private BigDecimal sourceBeforeBalance;
	
	/** 来源账户交易后余额 */
	@Column(name = "source_after_balance")
	private BigDecimal sourceAfterBalance;
	
	/** 目标账户交易前余额 */
	@Column(name = "target_before_balance")
	private BigDecimal targetBeforeBalance;
	
	/** 目标账户交易后余额 */
	@Column(name = "target_after_balance")
	private BigDecimal targetAfterBalance;
	
	/** 备注 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 读取来源账户
	 * 
	 * @return
	 * @see #sourceUserAccount
	 */
	public UserAccount getSourceUserAccount() {
		return sourceUserAccount;
	}
	
	/**
	 * 设置来源账户
	 * 
	 * @param sourceUserAccount
	 * @see #sourceUserAccount
	 */
	public void setSourceUserAccount(UserAccount sourceUserAccount) {
		this.sourceUserAccount = sourceUserAccount;
	}
	
	/**
	 * 读取目标账户
	 * 
	 * @return
	 * @see #targetUserAccount
	 */
	public UserAccount getTargetUserAccount() {
		return targetUserAccount;
	}
	
	/**
	 * 设置目标账户
	 * 
	 * @param targetUserAccount
	 * @see #targetUserAccount
	 */
	public void setTargetUserAccount(UserAccount targetUserAccount) {
		this.targetUserAccount = targetUserAccount;
	}
	
	/**
	 * 读取关系
	 * 
	 * @return
	 * @see #reference
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 * 设置关系
	 * 
	 * @param reference
	 * @see #reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 读取日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}
	
	/**
	 * 设置日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 读取金额
	 * 
	 * @return
	 * @see #amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/**
	 * 设置金额
	 * 
	 * @param amount
	 * @see #amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * 读取来源账户交易前余额
	 * 
	 * @return
	 * @see #sourceBeforeBalance
	 */
	public BigDecimal getSourceBeforeBalance() {
		return sourceBeforeBalance;
	}
	
	/**
	 * 设置来源账户交易前余额
	 * 
	 * @param sourceBeforeBalance
	 * @see #sourceBeforeBalance
	 */
	public void setSourceBeforeBalance(BigDecimal sourceBeforeBalance) {
		this.sourceBeforeBalance = sourceBeforeBalance;
	}
	
	/**
	 * 读取来源账户交易后余额
	 * 
	 * @return
	 * @see #sourceAfterBalance
	 */
	public BigDecimal getSourceAfterBalance() {
		return sourceAfterBalance;
	}
	
	/**
	 * 设置来源账户交易后余额
	 * 
	 * @param sourceAfterBalance
	 * @see #sourceAfterBalance
	 */
	public void setSourceAfterBalance(BigDecimal sourceAfterBalance) {
		this.sourceAfterBalance = sourceAfterBalance;
	}
	
	/**
	 * 读取目标账户交易前余额
	 * 
	 * @return
	 * @see #targetBeforeBalance
	 */
	public BigDecimal getTargetBeforeBalance() {
		return targetBeforeBalance;
	}
	
	/**
	 * 设置目标账户交易前余额
	 * 
	 * @param targetBeforeBalance
	 * @see #targetBeforeBalance
	 */
	public void setTargetBeforeBalance(BigDecimal targetBeforeBalance) {
		this.targetBeforeBalance = targetBeforeBalance;
	}
	
	/**
	 * 读取目标账户交易后余额
	 * 
	 * @return
	 * @see #targetAfterBalance
	 */
	public BigDecimal getTargetAfterBalance() {
		return targetAfterBalance;
	}
	
	/**
	 * 设置目标账户交易后余额
	 * 
	 * @param targetAfterBalance
	 * @see #targetAfterBalance
	 */
	public void setTargetAfterBalance(BigDecimal targetAfterBalance) {
		this.targetAfterBalance = targetAfterBalance;
	}
	
	/**
	 * 读取备注
	 * 
	 * @return
	 * @see #remark
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * 设置备注
	 * 
	 * @param remark
	 * @see #remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 读取类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * 类型
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-12
	 * @since 1.0
	 */
	public static final class Type {
		
		@Element("冻结")
		public static final String FREEZE			= "00";
		
		@Element("解冻")
		public static final String UNFREEZE			= "01";
		
		@Element("充值")
		public static final String CHARGE			= "02";
		
		@Element("提现")
		public static final String WITHDRAW			= "03";
		
		@Element("支出")
		public static final String OUT				= "10";
		
		@Element("冻结")
		public static final String REVERSE_FREEZE	= "50";
		
		@Element("解冻")
		public static final String REVERSE_UNFREEZE	= "51";
		
		@Element("充值")
		public static final String REVERSE_CHARGE	= "52";
		
		@Element("提现")
		public static final String REVERSE_WITHDRAW	= "53";
		
		@Element("收入")
		public static final String IN				= "60";
	}
	
	public static final class Status {
		@Element("充值成功")
		public static final String RECHARGE_SUCC = "充值成功";
		@Element("充值中")
		public static final String WAIT = "充值中";
		@Element("充值失败")
		public static final String RECHARGE_FAIL = "充值失败";
	}
}

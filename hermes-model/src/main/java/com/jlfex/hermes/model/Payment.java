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
 * 支付信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-09
 * @since 1.0
 */
@Entity
@Table(name = "hm_payment")
public class Payment extends Model {

	private static final long serialVersionUID = -3124106924699782792L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	/** 渠道 */
	@ManyToOne
	@JoinColumn(name = "channel")
	private PaymentChannel channel;

	/** 时间 */
	@Column(name = "`datetime`")
	private Date datetime;

	/** 序号 */
	@Column(name = "sequence")
	private Long sequence;

	/** 金额 */
	@Column(name = "amount")
	private BigDecimal amount;

	/** 手续费 */
	@Column(name = "fee")
	private BigDecimal fee;

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
	 * 读取渠道
	 * 
	 * @return
	 * @see #channel
	 */
	public PaymentChannel getChannel() {
		return channel;
	}

	/**
	 * 设置渠道
	 * 
	 * @param channel
	 * @see #channel
	 */
	public void setChannel(PaymentChannel channel) {
		this.channel = channel;
	}

	/**
	 * 读取时间
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置时间
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * 读取序号
	 * 
	 * @return
	 * @see #sequence
	 */
	public Long getSequence() {
		return sequence;
	}

	/**
	 * 设置序号
	 * 
	 * @param sequence
	 * @see #sequence
	 */
	public void setSequence(Long sequence) {
		this.sequence = sequence;
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
	 * 读取手续费
	 * 
	 * @return
	 * @see #fee
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * 设置手续费
	 * 
	 * @param fee
	 * @see #fee
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
	 * @author ultrafrog
	 * @version 1.0, 2014-01-09
	 * @since 1.0
	 */
	public static final class Status {

		@Element("等待支付")
		public static final String WAIT = "00";

		@Element("等待反馈")
		public static final String PAIED = "01";

		@Element("成功")
		public static final String SUCCESS = "10";

		@Element("失败")
		public static final String FAILURE = "20";
	}
}

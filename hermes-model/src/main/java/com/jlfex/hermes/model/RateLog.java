package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 费率日志信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_rate_log")
public class RateLog extends Model {

	private static final long serialVersionUID = 331419377631418120L;

	/** 费率 */
	@ManyToOne
	@JoinColumn(name = "rate")
	private Rate rate;

	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;

	/** 变更前 */
	@Column(name = "before_")
	private BigDecimal before;

	/** 变更后 */
	@Column(name = "after")
	private BigDecimal after;

	/**
	 * 读取费率
	 * 
	 * @return
	 * @see #rate
	 */
	public Rate getRate() {
		return rate;
	}

	/**
	 * 设置费率
	 * 
	 * @param rate
	 * @see #rate
	 */
	public void setRate(Rate rate) {
		this.rate = rate;
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
	 * 读取变更前
	 * 
	 * @return
	 * @see #before
	 */
	public BigDecimal getBefore() {
		return before;
	}

	/**
	 * 设置变更前
	 * 
	 * @param before
	 * @see #before
	 */
	public void setBefore(BigDecimal before) {
		this.before = before;
	}

	/**
	 * 读取变更后
	 * 
	 * @return
	 * @see #after
	 */
	public BigDecimal getAfter() {
		return after;
	}

	/**
	 * 设置变更后
	 * 
	 * @param after
	 * @see #after
	 */
	public void setAfter(BigDecimal after) {
		this.after = after;
	}
}

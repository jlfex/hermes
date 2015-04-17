package com.jlfex.hermes.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 第三方支付限额
 * 
 * @author jswu
 *
 */
@Entity
@Table(name = "hm_pp_limit")
public class PPLimit extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6160483837241846775L;
	
	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;
	
	private String ppOrg;
	
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getPpOrg() {
		return ppOrg;
	}

	public void setPpOrg(String ppOrg) {
		this.ppOrg = ppOrg;
	}

	/**
	 * 代笔限额
	 */
	@Column(name = "single_limit")
	private BigDecimal singleLimit;

	/**
	 * 日累计限额
	 */
	@Column(name = "day_total_limit")
	private BigDecimal dayTotalLimit;
	
	/**
	 * 读取单笔限额
	 * 
	 * @return
	 */
	public BigDecimal getSingleLimit() {
		return singleLimit;
	}

	/**
	 * 设置单笔限额
	 * 
	 * @param singleLimit
	 */
	public void setSingleLimit(BigDecimal singleLimit) {
		this.singleLimit = singleLimit;
	}

	/**
	 * 读取日累计限额
	 * 
	 * @return
	 */
	public BigDecimal getDayTotalLimit() {
		return dayTotalLimit;
	}

	/**
	 * 设置日累计限额
	 * 
	 * @param dayTotalLimit
	 */
	public void setDayTotalLimit(BigDecimal dayTotalLimit) {
		this.dayTotalLimit = dayTotalLimit;
	}
}

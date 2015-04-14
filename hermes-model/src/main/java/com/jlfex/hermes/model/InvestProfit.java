package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 理财收益信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_invest_profit")
public class InvestProfit extends Model {
	
	private static final long serialVersionUID = -1706053977367451550L;
	
	public InvestProfit(){
		
	}
	public InvestProfit(BigDecimal interestAmount,BigDecimal overdueAmount){
		 this.interestAmount = interestAmount;
		 this.overdueAmount = overdueAmount;
	}
	
	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/** 理财 */
	@ManyToOne
	@JoinColumn(name = "invest")
	private Invest invest;
	
	/** 借款还款 */
	@ManyToOne
	@JoinColumn(name = "loan_repay")
	private LoanRepay loanRepay;
	
	/** 日期 */
	@Column(name = "date")
	private Date date;
	
	/** 总金额 */
	@Column(name = "amount")
	private BigDecimal amount;
	
	/** 本金 */
	@Column(name = "principal")
	private BigDecimal principal;
	
	/** 利息 */
	@Column(name = "interest")
	private BigDecimal interest;
	
	/** 逾期利息 */
	@Column(name = "overdue_interest")
	private BigDecimal overdueInterest;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	// 统计 总收益
	@Transient
	private BigDecimal allAmount ;
	// 统计 利息收益
	@Transient
	private BigDecimal interestAmount;
	// 统计 逾期收益
	@Transient
	private BigDecimal overdueAmount;
	
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
	 * 读取理财
	 * 
	 * @return
	 * @see #invest
	 */
	public Invest getInvest() {
		return invest;
	}

	/**
	 * 设置理财
	 * 
	 * @param invest
	 * @see #invest
	 */
	public void setInvest(Invest invest) {
		this.invest = invest;
	}
	
	/**
	 * 读取借款还款
	 * 
	 * @return
	 * @see #loanRepay
	 */
	public LoanRepay getLoanRepay() {
		return loanRepay;
	}
	
	/**
	 * 设置借款还款
	 * 
	 * @param loanRepay
	 * @see #loanRepay
	 */
	public void setLoanRepay(LoanRepay loanRepay) {
		this.loanRepay = loanRepay;
	}
	
	/**
	 * 读取日期
	 * 
	 * @return
	 * @see #date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * 设置日期
	 * 
	 * @param date
	 * @see #date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * 读取总金额
	 * 
	 * @return
	 * @see #amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/**
	 * 设置总金额
	 * 
	 * @param amount
	 * @see #amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * 读取本金
	 * 
	 * @return
	 * @see #principal
	 */
	public BigDecimal getPrincipal() {
		return principal;
	}
	
	/**
	 * 设置本金
	 * 
	 * @param principal
	 * @see #principal
	 */
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	
	/**
	 * 读取利息
	 * 
	 * @return
	 * @see #interest
	 */
	public BigDecimal getInterest() {
		return interest;
	}
	
	/**
	 * 设置利息
	 * 
	 * @param interest
	 * @see #interest
	 */
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	/**
	 * 读取逾期利息
	 * 
	 * @return
	 * @see #overdueInterest
	 */
	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}
	
	/**
	 * 设置逾期利息
	 * 
	 * @param overdueInterest
	 * @see #overdueInterest
	 */
	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
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
	 * 
	 * @author Aether
	 * @return description:取得状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}
	
	
	public BigDecimal getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}
	public BigDecimal getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}
	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	


	/**
	 * 借款状态
	 * 
	 * @date: 2013-11-12 下午2:43:14 operation by: description:
	 */
	public static final class Status {
		@Element("待收款")
		public static final String WAIT = "00";

		@Element("已收款")
		public static final String ALREADY = "01";

		@Element("已收逾期款")
		public static final String OVERDUE = "02";

		@Element("已收垫付款")
		public static final String ADVANCE = "03";
	}

}

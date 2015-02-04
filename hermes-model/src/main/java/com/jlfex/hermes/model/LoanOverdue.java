package com.jlfex.hermes.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 借款逾期费率信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Entity
@Table(name = "hm_loan_overdue")
public class LoanOverdue extends Model {

	private static final long serialVersionUID = -3550683333306990012L;

	/** 借款 */
	@ManyToOne
	@JoinColumn(name = "loan")
	private Loan loan;
	
	/** 等级 */
	@Column(name = "rank")
	private Integer rank;
	
	/** 罚息 */
	@Column(name = "interest", precision=16, scale=8,nullable=false)
	private BigDecimal interest;
	
	/** 违约金 */
	@Column(name = "penalty", precision=16, scale=8, nullable=false)
	private BigDecimal penalty;

	/**
	 * 读取借款
	 * 
	 * @return
	 * @see #loan
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * 设置借款
	 * 
	 * @param loan
	 * @see #loan
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	/**
	 * 读取等级
	 * 
	 * @return
	 * @see #rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * 设置等级
	 * 
	 * @param rank
	 * @see #rank
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * 读取罚息
	 * 
	 * @return
	 * @see #interest
	 */
	public BigDecimal getInterest() {
		return interest;
	}

	/**
	 * 设置罚息
	 * 
	 * @param interest
	 * @see #interest
	 */
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	/**
	 * 读取违约金
	 * 
	 * @return
	 * @see #penalty
	 */
	public BigDecimal getPenalty() {
		return penalty;
	}

	/**
	 * 设置违约金
	 * 
	 * @param penalty
	 * @see #penalty
	 */
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
}

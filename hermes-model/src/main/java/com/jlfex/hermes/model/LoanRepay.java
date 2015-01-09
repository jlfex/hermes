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
 * 借款还款信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_loan_repay")
public class LoanRepay extends Model {
	
	private static final long serialVersionUID = -4142723479134081564L;

	/** 借款 */
	@ManyToOne
	@JoinColumn(name = "loan")
	private Loan loan;
	
	/** 期数 */
	@Column(name = "sequence")
	private Integer sequence;
	
	/** 计划日期 */
	@Column(name = "plan_datetime")
	private Date planDatetime;
	
	/** 还款日期 */
	@Column(name = "repay_datetime")
	private Date repayDatetime;
	
	/** 总金额 */
	@Column(name = "amount")
	private BigDecimal amount;
	
	/** 本金 */
	@Column(name = "principal")
	private BigDecimal principal;
	
	/** 利息 */
	@Column(name = "interest")
	private BigDecimal interest;
	
	/** 逾期天数 */
	@Column(name = "overdue_days")
	private Integer overdueDays;
	
	/** 逾期罚息 */
	@Column(name = "overdue_interest")
	private BigDecimal overdueInterest;
	
	/** 逾期违约金 */
	@Column(name = "overdue_penalty")
	private BigDecimal overduePenalty;
	
	/** 其他金额 */
	@Column(name = "other_amount")
	private BigDecimal otherAmount;
	
	/** 备注 */
	@Column(name = "remark")
	private String remark;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	
	/**剩余未还本息*/
	@Transient
	private BigDecimal unRepay;
	
	//导入结果
	@Transient
	private String import_result;
	
		
	
	
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
	 * 读取期数
	 * 
	 * @return
	 * @see #sequence
	 */
	public Integer getSequence() {
		return sequence;
	}
	
	/**
	 * 设置期数
	 * 
	 * @param sequence
	 * @see #sequence
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * 读取计划日期
	 * 
	 * @return
	 * @see #planDatetime
	 */
	public Date getPlanDatetime() {
		return planDatetime;
	}
	
	/**
	 * 设置计划日期
	 * 
	 * @param planDatetime
	 * @see #planDatetime
	 */
	public void setPlanDatetime(Date planDatetime) {
		this.planDatetime = planDatetime;
	}
	
	/**
	 * 读取还款日期
	 * 
	 * @return
	 * @see #repayDatetime
	 */
	public Date getRepayDatetime() {
		return repayDatetime;
	}
	
	/**
	 * 设置还款日期
	 * 
	 * @param repayDatetime
	 * @see #repayDatetime
	 */
	public void setRepayDatetime(Date repayDatetime) {
		this.repayDatetime = repayDatetime;
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
	 * 读取逾期天数
	 * 
	 * @return
	 * @see #overdueDays
	 */
	public Integer getOverdueDays() {
		return overdueDays;
	}
	
	/**
	 * 设置逾期天数
	 * 
	 * @param overdueDays
	 * @see #overdueDays
	 */
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	
	/**
	 * 读取逾期罚息
	 * 
	 * @return
	 * @see #overdueInterest
	 */
	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}
	
	/**
	 * 设置逾期罚息
	 * 
	 * @param overdueInterest
	 * @see #overdueInterest
	 */
	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}
	
	/**
	 * 读取逾期违约金
	 * 
	 * @return
	 * @see #overduePenalty
	 */
	public BigDecimal getOverduePenalty() {
		return overduePenalty;
	}
	
	/**
	 * 设置逾期违约金
	 * 
	 * @param overduePenalty
	 * @see #overduePenalty
	 */
	public void setOverduePenalty(BigDecimal overduePenalty) {
		this.overduePenalty = overduePenalty;
	}
	
	/**
	 * 读取其他金额
	 * 
	 * @return
	 * @see #otherAmount
	 */
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}
	
	/**
	 * 设置其他金额
	 * 
	 * @param otherAmount
	 * @see #otherAmount
	 */
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
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
	
	
	
	public BigDecimal getUnRepay() {
		return unRepay;
	}

	public void setUnRepay(BigDecimal unRepay) {
		this.unRepay = unRepay;
	}

	/**
	 * 
	 * @author Aether
	 * @date 2013-11-12 下午2:14:01
	 * @return description:获取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, RepayStatus.class);
	}
	
	
	public String getImport_result() {
		return import_result;
	}

	public void setImport_result(String import_result) {
		this.import_result = import_result;
	}


	/**
	 * 还款状态
	 * 
	 * @author Aether
	 * @date: 2013-11-12 下午2:12:36 operation by: description:
	 */
	public static final class RepayStatus {
		
		@Element("等待还款")
		public static final String WAIT = "00";

		@Element("正常还款")
		public static final String NORMAL = "10";

		@Element("逾期还款")
		public static final String OVERDUE_REPAY = "11";

//		@Element("逾期后垫付还款")
//		public static final String OVERDUE_ADVANCEREPAY = "12";

		@Element("逾期")
		public static final String OVERDUE = "20";

//		@Element("逾期垫付")
//		public static final String OVERDUE_ADVANCE = "30";
	}
}

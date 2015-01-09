package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Numbers;

/**
 * 借款信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_loan")
public class Loan extends Model {
	
	

	private static final long serialVersionUID 	= -2896977254468300664L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/** 产品 */
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	/** 还款方式 */
	@ManyToOne
	@JoinColumn(name = "repay")
	private Repay repay;
	
	/** 借款用途 */
	@Column(name = "purpose")
	private String purpose;
	
	/** 借款编号 */
	@Column(name = "loan_no")
	private String loanNo;
	
	/** 金额 */
	@Column(name = "amount")
	private BigDecimal amount;
	
	/** 已筹金额 */
	@Column(name = "proceeds")
	private BigDecimal proceeds;
	
	/** 期限 */
	@Column(name = "period")
	private Integer period;
	
	/** 利率 */
	@Column(name = "rate")
	private BigDecimal rate;
	
	/** 招标期限 */
	@Column(name = "deadline")
	private Integer deadline;
	
	/** 发布日期 */
	@Column(name = "datetime")
	private Date datetime;
	
	/** 描述 */
	@Column(name = "description")
	private String description;
	
	/** 借款管理费 */
	@Column(name = "manage_fee")
	private BigDecimal manageFee;
	
	/** 借款管理费类型 */
	@Column(name = "manage_fee_type")
	private String manageFeeType;
	
	/** 备注 */
	@Column(name = "remark")
	private String remark;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 标 类型 :*/
	@Column(name = "loan_kind",length=2)
	private String loanKind;
	
	/** 标 对应的债权id: 普通标：该字段可为空 */
	@Column(name = "creditor_id", length=50)
	private String creditorId;
	
	
	/** 还款信息列表 */
	@Transient
	private List<LoanRepay> repays = new LinkedList<LoanRepay>();
	
	/** 业务日期，目前是自动任务跑批的时候设置借款的最后截止日期 */
	@Transient
	private Date businessDate;
	
	
	
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
	 * 读取产品
	 * 
	 * @return
	 * @see #product
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * 设置产品
	 * 
	 * @param product
	 * @see #product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * 读取还款方式
	 * 
	 * @return
	 * @see #repay
	 */
	public Repay getRepay() {
		return repay;
	}
	
	/**
	 * 设置还款方式
	 * 
	 * @param repay
	 * @see #repay
	 */
	public void setRepay(Repay repay) {
		this.repay = repay;
	}
	
	/**
	 * 读取借款用途
	 * 
	 * @return
	 * @see #purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	
	/**
	 * 设置借款用途
	 * 
	 * @param purpose
	 * @see #purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	/**
	 * 读取借款编号
	 * 
	 * @return
	 * @see #loanNo
	 */
	public String getLoanNo() {
		return loanNo;
	}
	
	/**
	 * 设置借款编号
	 * 
	 * @param loanNo
	 * @see #loanNo
	 */
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
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
	 * 读取已筹金额
	 * 
	 * @return
	 * @see #proceeds
	 */
	public BigDecimal getProceeds() {
		return (proceeds == null) ? BigDecimal.ZERO : proceeds;
	}
	
	/**
	 * 设置已筹金额
	 * 
	 * @param proceeds
	 * @see #proceeds
	 */
	public void setProceeds(BigDecimal proceeds) {
		this.proceeds = proceeds;
	}
	
	/**
	 * 读取期限
	 * 
	 * @return
	 * @see #period
	 */
	public Integer getPeriod() {
		return period;
	}
	
	/**
	 * 设置期限
	 * 
	 * @param period
	 * @see #period
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	/**
	 * 读取利率
	 * 
	 * @return
	 * @see #rate
	 */
	public BigDecimal getRate() {
		return rate;
	}
	
	/**
	 * 设置利率
	 * 
	 * @param rate
	 * @see #rate
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	/**
	 * 读取招标期限
	 * 
	 * @return
	 * @see #deadline
	 */
	public Integer getDeadline() {
		return deadline;
	}
	
	/**
	 * 设置招标期限
	 * 
	 * @param deadline
	 * @see #deadline
	 */
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}
	
	/**
	 * 读取发布日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}
	
	/**
	 * 设置发布日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 读取描述
	 * 
	 * @return
	 * @see #description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 设置描述
	 * 
	 * @param description
	 * @see #description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 读取借款管理费
	 * 
	 * @see #manageFee
	 */
	public BigDecimal getManageFee() {
		return manageFee;
	}

	/**
	 * 设置借款管理费
	 * 
	 * @param manageFee
	 * @see #manageFee
	 */
	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	/**
	 * 读取借款管理费类型
	 * 
	 * @return
	 * @see #manageFeeType
	 */
	public String getManageFeeType() {
		return manageFeeType;
	}

	/**
	 * 设置借款管理费类型
	 * 
	 * @param manageFeeType
	 * @see #manageFeeType
	 */
	public void setManageFeeType(String manageFeeType) {
		this.manageFeeType = manageFeeType;
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
	
	/**
	 * 读取还款信息列表
	 * 
	 * @return
	 * @see #repays
	 */
	public List<LoanRepay> getRepays() {
		return repays;
	}
	
	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, "-", Status.class);
	}
	/**
	 * 读取标类型名称
	 * @return
	 */
	public String getloanKindName() {
		return Dicts.name(loanKind, "-", LoanKinds.class);
	}


	/**
	 * 累加已筹金额
	 * 
	 * @param amount
	 * @return
	 */
	public BigDecimal addProceeds(BigDecimal amount) {
		proceeds = getProceeds().add(amount);
		return proceeds;
	}
	
	/**
	 * 读取进度
	 * 
	 * @return
	 */
	public Integer getProgress() {
		return getProceeds().multiply(BigDecimal.valueOf(100)).divide(getAmount(), 0, RoundingMode.DOWN).intValue();
	}
	
	/**
	 * 读取可投金额
	 * 
	 * @return
	 */
	public String getRemain() {
		return Numbers.toCurrency(getAmount().subtract(getProceeds()).doubleValue());
	}
	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	
	
	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}




	/**
	 * 借款状态
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-12
	 * @since 1.0
	 */
	public static final class Status {
		
		@Element("等待初审")
		public static final String AUDIT_FIRST 			= "00";

		@Element("等待终审")
		public static final String AUDIT_FINAL 			= "01";

		@Element("招标中")
		public static final String BID 					= "10";

		@Element("满标")
		public static final String FULL 				= "11";

		@Element("还款中")
		public static final String REPAYING 			= "12";

		@Element("初审驳回")
		public static final String REJECT_AUDIT_FIRST 	= "20";

		@Element("终审驳回")
		public static final String REJECT_AUDIT_FINAL 	= "21";

		@Element("自动流标")
		public static final String FAILURE_AUTO 		= "30";

		@Element("手动流标")
		public static final String FAILURE_MANUAL 		= "31";

		@Element("满标流标")
		public static final String FAILURE_FULL 		= "32";

		@Element("完成")
		public static final String COMPLETED 			= "99";
	}
	/**
	 * 标： 类型
	 * @author Administrator
	 *
	 */
	public static final class LoanKinds {
		@Element("普通标")
		public static final String NORML_LOAN 			= "00";
		@Element("外部债权标")
		public static final String OUTSIDE_ASSIGN_LOAN 	= "01";
		@Element("平台内债权标")
		public static final String PLATFORM_ASSIGN_LOAN = "02";

	}
}

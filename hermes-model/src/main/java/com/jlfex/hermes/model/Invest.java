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
 * 理财信息模型
 */
@Entity
@Table(name = "hm_invest")
public class Invest extends Model {

	private static final long serialVersionUID = -6955704217088973736L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	/** 借款 */
	@ManyToOne
	@JoinColumn(name = "loan")
	private Loan loan;

	/** 投资金额 */
	@Column(name = "amount")
	private BigDecimal amount;

	/** 投资日期 */
	@Column(name = "datetime")
	private Date datetime;

	/** 所占比例 */
	@Column(name = "ratio", precision=16, scale=8,nullable=false)
	private BigDecimal ratio;

	/** 垫付 */
	@Column(name = "other_repay")
	private String otherRepay;

	/** 备注 */
	@Column(name = "remark")
	private String remark;

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
	 * 读取投资金额
	 * 
	 * @return
	 * @see #amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置投资金额
	 * 
	 * @param amount
	 * @see #amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 读取投资日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置投资日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * 读取所占比例
	 * 
	 * @return
	 * @see #ratio
	 */
	public BigDecimal getRatio() {
		return ratio;
	}

	/**
	 * 设置所占比例
	 * 
	 * @param ratio
	 * @see #ratio
	 */
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	/**
	 * 读取垫付
	 * 
	 * @return
	 * @see #otherRepay
	 */
	public String getOtherRepay() {
		return otherRepay;
	}

	/**
	 * 设置垫付
	 * 
	 * @param otherRepay
	 * @see #otherRepay
	 */
	public void setOtherRepay(String otherRepay) {
		this.otherRepay = otherRepay;
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
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 状态
	 */
	public static final class Status {
		@Element("冻结中")
		public static final String FREEZE = "00";
		@Element("完成投标")
		public static final String COMPLETE = "10";
		@Element("借款流标")
		public static final String FAILURE = "20";
		@Element("处理中")
		public static final String WAIT = "98";
		@Element("投标失败")
		public static final String FAIL = "99";
	}

}

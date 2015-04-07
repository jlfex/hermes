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
 * 借款日志模型
 * @since 1.0
 */
@Entity
@Table(name = "hm_loan_log")
public class LoanLog extends Model {

	private static final long serialVersionUID = -7794761102491258115L;

	/** 用户 */
	@Column(name = "user")
	private String user;

	/** 借款 */
	@ManyToOne
	@JoinColumn(name = "loan")
	private Loan loan;

	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;

	/** 类型 */
	@Column(name = "type")
	private String type;

	/** 金额 */
	@Column(name = "amount")
	private BigDecimal amount;

	/** 备注 */
	@Column(name = "remark")
	private String remark;
	
	@Transient
	private String userName;

	/**
	 * 读取用户
	 * 
	 * @return
	 * @see #user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param user
	 * @see #user
	 */
	public void setUser(String user) {
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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @author Aether
	 * @date 2013-11-12 下午2:23:29
	 * @return description:取得借款类型名称
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * 借款日志类型
	 * 
	 * @author Aether
	 * @date: 2013-11-12 下午2:22:33
	 */
	public static final class Type {
		@Element("发布借款")
		public static final String RELEASE = "00";

		@Element("初审通过")
		public static final String FIRST_AUDIT_PASS = "01";

		@Element("终审通过")
		public static final String FINALL_AUDIT_PASS = "02";

		@Element("投标")
		public static final String INVEST = "03";

		@Element("放款")
		public static final String LOAN = "04";

		@Element("还款")
		public static final String REPAY = "05";

		@Element("垫付")
		public static final String ADVANCE = "06";

		@Element("初审驳回")
		public static final String FIRST_AUDIT_REJECT = "20";

		@Element("终审驳回")
		public static final String FINAL_AUDIT_REJECT = "21";

		@Element("自动流标")
		public static final String AUTO_FAILURE = "22";

		@Element("手动流标")
		public static final String MANUAL_FAILURE = "23";

		@Element("满标流标")
		public static final String FULL_FAILURE = "24";

		@Element("完成")
		public static final String COMPLETE = "90";

		@Element("开始招标")
		public static final String START_INVEST = "07";

		@Element("满标")
		public static final String FULL = "08";
		
		@Element("债权发售")
		public static final String  SELL_CREDIT= "30";
	}
	
	public static final class Status {
		@Element("冻结中")
		public static final String FREEZE = "冻结中";
		@Element("投标确认中")
		public static final String WAIT = "投标确认中";
		@Element("投标失败")
		public static final String FAIL = "投标失败";
	}
}

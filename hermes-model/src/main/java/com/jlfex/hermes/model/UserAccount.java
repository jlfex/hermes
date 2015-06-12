package com.jlfex.hermes.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户账户信息模型
 *
 */
@Entity
@Table(name = "hm_user_account")
public class UserAccount extends Model {

	private static final long serialVersionUID = 6095017641526244164L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/** 余额 */
	@Column(name = "balance")
	private BigDecimal balance;

	/** 负数 */
	@Column(name = "minus_")
	private String minus;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/** 类型 */
	@Column(name = "type")
	private String type;

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
	 * 读取余额
	 * 
	 * @return
	 * @see #balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置余额
	 * 
	 * @param balance
	 * @see #balance
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * 读取负数
	 * 
	 * @return
	 * @see #minus
	 */
	public String getMinus() {
		return minus;
	}

	/**
	 * 设置负数
	 * 
	 * @param minus
	 * @see #minus
	 */
	public void setMinus(String minus) {
		this.minus = minus;
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
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
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
	 * 可否为负
	 */
	public static final class Minus {

		@Element("否")
		public static final String INMINUS = "00";

		@Element("是")
		public static final String MINUS = "01";
	}

	/**
	 * 类型
	 */
	public static final class Type {

		@Element("现金账户")
		public static final String CASH = "00";

		@Element("冻结账户")
		public static final String FREEZE = "99";

		@Element("支付账户")
		public static final String PAYMENT = "10";

		@Element("支付手续费账户")
		public static final String PAYMENT_FEE = "11";

		@Element("提现账户")
		public static final String WITHDRAW = "12";

		@Element("提现手续费账户")
		public static final String WITHDRAW_FEE = "13";

		@Element("借款手续费账户")
		public static final String LOAN_FEE = "14";

		@Element("风险金账户")
		public static final String RISK = "15";

		@Element("月缴管理费账户")
		public static final String LOAN_MONTHLY_FEE = "16";

		@Element("逾期管理费账户")
		public static final String OVERDUE_FEE = "17";
		
		@Element("Jlfex代扣账户")
		public static final String JLFEX_FEE =  "18";
		
		@Element("中金代扣账户")
		public static final String ZHONGJIN_FEE = "19";

	}

	/**
	 * 状态
	 */
	public static final class Status {

		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}
}

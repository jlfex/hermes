package com.jlfex.hermes.model.cfca;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Model;
import com.jlfex.hermes.model.User;
/**
 * 中金订单
 * @author Administrator
 *
 */
@Entity
@Table(name = "hm_order_cfca")
public class CFCAOrder extends Model {

	
	private static final long serialVersionUID = 509403950180191714L;

	@OneToOne
	@JoinColumn(name = "invest_id")
	private Invest invest;

	/**
	 * 响应码
	 */
	private String code;

	/**
	 * 响应消息
	 */
	private String message;
	/**
	 * 交易金额
	 */
	private BigDecimal amount;

	/**
	 * 市场订单号
	 */
	private String orderNo;

	/**
	 * 交易流水
	 */
	private String txSN;

	/**
	 * 交易状态
	 */
	private Integer status;

	/**
	 * 银行处理时间
	 */
	private String bankTxTime;

	/**
	 * 响应代码
	 */
	private String responseCode;

	/**
	 * 响应消息
	 */
	private String responseMessage;

	/**
	 * 类型：00充值，01投标
	 */
	private String type;

	/**
	 * 费
	 */
	private BigDecimal fee;

	/**
	 * 投资人
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Invest getInvest() {
		return invest;
	}

	public void setInvest(Invest invest) {
		this.invest = invest;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTxSN() {
		return txSN;
	}

	public void setTxSN(String txSN) {
		this.txSN = txSN;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankTxTime() {
		return bankTxTime;
	}

	public void setBankTxTime(String bankTxTime) {
		this.bankTxTime = bankTxTime;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public static final class Type {
		@Element("充值")
		public static final String RECHARGE = "00";

		@Element("投标")
		public static final String BID = "01";
		
		@Element("结算")
		public static final String CLEAR = "02";
	}
	
	/**
	 * 订单清算状态
	 * @author Administrator
	 *
	 */
	public static final class ClearStatus {
		@Element("订单生成")  
		public static final int CLEAR_INIT = 0;
		@Element("已经受理")  
		public static final int CLEAR_RECEIVE = 10;
		@Element("正在结算")
		public static final int CLEAR_DOING = 30;
		@Element("已经执行")
		public static final int CLEAR_FINISH = 40;
	}
	
	/**
	 * 订单代扣状态
	 * @author Administrator
	 *
	 */ 
	public static final class BuckleStatus {
		@Element("正在处理")  
		public static final int BUCKLE_RECEIVE = 20;
		@Element("代扣成功")
		public static final int BUCKLE_SUC = 30;
		@Element("代扣失败")
		public static final int BUCKLE_FAIL = 40;
	}
	
}

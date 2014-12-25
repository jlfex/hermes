package com.jlfex.hermes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 支付日志信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-09
 * @since 1.0
 */
@Entity
@Table(name = "hm_payment_log")
public class PaymentLog extends Model {

	private static final long serialVersionUID = 7573731553682983910L;

	/** 支付 */
	@ManyToOne
	@JoinColumn(name = "payment")
	private Payment payment;
	
	/** 时间 */
	@Column(name = "datetime")
	private Date datetime;
	
	/** 方式 */
	@Column(name = "method")
	private String method;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 原始数据 */
	@Column(name = "raw")
	private String raw;

	/**
	 * 读取支付
	 * 
	 * @return
	 * @see #payment
	 */
	public Payment getPayment() {
		return payment;
	}

	/**
	 * 设置支付
	 * 
	 * @param payment
	 * @see #payment
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	/**
	 * 读取时间
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置时间
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * 读取方式
	 * 
	 * @return
	 * @see #method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置方式
	 * 
	 * @param method
	 * @see #method
	 */
	public void setMethod(String method) {
		this.method = method;
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
	 * 读取原始数据
	 * 
	 * @return
	 * @see #raw
	 */
	public String getRaw() {
		return raw;
	}

	/**
	 * 设置原始数据
	 * 
	 * @param raw
	 * @see #raw
	 */
	public void setRaw(String raw) {
		this.raw = raw;
	}
	
	/**
	 * 读取方式名称
	 * 
	 * @return
	 */
	public String getMethodName() {
		return Dicts.name(method, method, Method.class);
	}
	
	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Payment.Status.class);
	}
	
	/**
	 * 方式
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-01-09
	 * @since 1.0
	 */
	public static final class Method {
		
		@Element("同步返回")
		public static final String RETURN	= "00";
		
		@Element("异步通知")
		public static final String NOTIFY	= "01";
	}
}

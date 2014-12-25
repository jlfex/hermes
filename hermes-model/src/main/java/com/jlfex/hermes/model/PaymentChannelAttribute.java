package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 支付渠道参数信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_payment_channel_attribute")
public class PaymentChannelAttribute extends Model {
	
	private static final long serialVersionUID = 5135309848568301455L;

	/** 支付网关 */
	@ManyToOne
	@JoinColumn(name = "channel")
	private PaymentChannel channel;
	
	/** 名称 */
	@Column(name = "name")
	private String name;
	
	/** 代码 */
	@Column(name = "code")
	private String code;
	
	/** 内容 */
	@Column(name = "value")
	private String value;
	
	/**
	 * 读取支付网关
	 * 
	 * @return
	 * @see #channel
	 */
	public PaymentChannel getChannel() {
		return channel;
	}
	
	/**
	 * 设置支付网关
	 * 
	 * @param channel
	 * @see #channel
	 */
	public void setChannel(PaymentChannel channel) {
		this.channel = channel;
	}
	
	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 读取内容
	 * 
	 * @return
	 * @see #value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * 设置内容
	 * 
	 * @param value
	 * @see #value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}

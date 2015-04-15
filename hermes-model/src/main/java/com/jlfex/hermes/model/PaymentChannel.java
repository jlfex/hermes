package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 支付渠道信息模型
 * @since 1.0
 */
@Entity
@Table(name = "hm_payment_channel")
public class PaymentChannel extends Model {

	private static final long serialVersionUID = -6578898210640754801L;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 副称 */
	@Column(name = "subname")
	private String subname;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 图片 */
	@Column(name = "logo", length = 40000)
	private String logo;

	/** 顺序 */
	@Column(name = "order_")
	private Integer order;

	/** 实现类 */
	@Column(name = "clazz")
	private String clazz;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/** 类型 */
	@Column(name = "type")
	private String type;

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
	 * 读取副称
	 * 
	 * @return
	 * @see #subname
	 */
	public String getSubname() {
		return subname;
	}

	/**
	 * 设置副称
	 * 
	 * @param subname
	 * @see #subname
	 */
	public void setSubname(String subname) {
		this.subname = subname;
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
	 * 读取图片
	 * 
	 * @return
	 * @see #logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置图片
	 * 
	 * @param logo
	 * @see #logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 读取顺序
	 * 
	 * @return
	 * @see #order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置顺序
	 * 
	 * @param order
	 * @see #order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * 读取实现类
	 * 
	 * @return
	 * @see #class
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * 设置实现类
	 * 
	 * @param clazz
	 * @see #clazz
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
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
	 * 状态
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-13
	 * @since 1.0
	 */
	public static final class Status {

		@Element("有效")
		public static final String ENABLED = "00";

		@Element("无效")
		public static final String DISABLED = "99";
	}

	/**
	 * 类型
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-12
	 * @since 1.0
	 */
	public static final class Type {

		@Element("支付平台")
		public static final String THIRDPP = "00";

		@Element("支付银行")
		public static final String DIRECT = "01";
	}
}

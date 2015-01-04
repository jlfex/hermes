package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 字典信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_dictionary")
public class Dictionary extends Model {

	private static final long serialVersionUID = 188695956404918637L;

	/** 类型 */
	@ManyToOne
	@JoinColumn(name = "type")
	private DictionaryType type;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 顺序 */
	@Column(name = "order_")
	private Integer order;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public DictionaryType getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(DictionaryType type) {
		this.type = type;
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
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-13
	 * @since 1.0
	 */
	public static final class Status {

		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}
}

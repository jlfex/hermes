package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统属性信息模型
 */
@Entity
@Table(name = "hm_properties")
public class Properties extends Model {
	
	private static final long serialVersionUID = -7689344513377810104L;

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
	 * 构造函数
	 */
	public Properties() {}
	
	/**
	 * 构造函数
	 * 
	 * @param name
	 * @param code
	 * @param value
	 */
	public Properties(String name, String code, String value) {
		this.name = name;
		this.code = code;
		this.value = value;
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

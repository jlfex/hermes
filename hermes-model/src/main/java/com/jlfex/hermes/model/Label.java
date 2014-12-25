package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 标签信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-19
 * @since 1.0
 */
@Entity
@Table(name = "hm_label")
public class Label extends Model {

	private static final long serialVersionUID = -7110815903341187115L;

	/** 名称 */
	@Column(name = "name")
	private String name;

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
}

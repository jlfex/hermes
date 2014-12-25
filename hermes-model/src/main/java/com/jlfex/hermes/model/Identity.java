package com.jlfex.hermes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 编号<br>
 * 数据主键
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-08
 * @since 1.0
 */
@MappedSuperclass
public class Identity implements Serializable {

	private static final long serialVersionUID = -3477072123817232611L;
	
	/** 编号 */
	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private String id;

	/**
	 * 读取编号
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
	}
}

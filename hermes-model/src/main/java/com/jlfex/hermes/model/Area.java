package com.jlfex.hermes.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 地区信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-11
 * @since 1.0
 */
@Entity
@Table(name = "hm_area")
public class Area extends Model {
	
	private static final long serialVersionUID = -3102239093539351500L;

	/** 上级编号 */
	@Column(name = "parent")
	private String parentId;
	
	/** 名称 */
	@Column(name = "name")
	private String name;
	
	/** 代码 */
	@Column(name = "code")
	private String code;
	
	/** 上级 */
	@Transient
	private Area parent;
	
	/** 子集 */
	@Transient
	private List<Area> children = new LinkedList<Area>();
	
	/**
	 * 读取上级编号
	 * 
	 * @return
	 * @see #parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置上级编号
	 * 
	 * @param parentId
	 * @see #parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	 * 读取上级
	 * 
	 * @return
	 * @see #parent
	 */
	public Area getParent() {
		return parent;
	}
	
	/**
	 * 设置上级
	 * 
	 * @param parent
	 * @see #parent
	 */
	public void setParent(Area parent) {
		this.parent = parent;
	}
	
	/**
	 * 读取子集
	 * 
	 * @return
	 * @see #children
	 */
	public List<Area> getChildren() {
		return children;
	}
}

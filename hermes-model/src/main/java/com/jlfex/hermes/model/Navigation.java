package com.jlfex.hermes.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlfex.hermes.common.web.WebApp;

/**
 * 导航信息模型
 */
@Entity
@Table(name = "hm_navigation", uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
public class Navigation extends Model {

	private static final long serialVersionUID = 842883061234188375L;
	private static final String PREFIX = "@";

	/** 父级 */
	@ManyToOne
	@JoinColumn(name = "parent")
	@JsonIgnore
	private Navigation parent;

	/** 类型 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type")
	@JsonIgnore
	private Dictionary type;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 副称 */
	@Column(name = "subname")
	private String subname;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 地址 */
	@Column(name = "path")
	private String path;

	/** 目标 */
	@Column(name = "target")
	private String target;

	/** 顺序 */
	@Column(name = "order_")
	private Integer order;

	/** 子集 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@OrderBy(value = " order asc")
	private List<Navigation> children = new LinkedList<Navigation>();

	/**
	 * 某一角色是否含有该权限
	 */
	@Transient
	private boolean isHavingByRole = false;

	/**
	 * 读取父级
	 * 
	 * @return
	 * @see #parent
	 */
	public Navigation getParent() {
		return parent;
	}

	/**
	 * 设置父级
	 * 
	 * @param parent
	 * @see #parent
	 */
	public void setParent(Navigation parent) {
		this.parent = parent;
	}

	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public Dictionary getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(Dictionary type) {
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
	 * 读取地址
	 * 
	 * @return
	 * @see #path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置地址
	 * 
	 * @param path
	 * @see #path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 读取目标
	 * 
	 * @return
	 * @see #target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 设置目标
	 * 
	 * @param target
	 * @see #target
	 */
	public void setTarget(String target) {
		this.target = target;
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
	 * 读取子集
	 * 
	 * @return
	 * @see #children
	 */
	public List<Navigation> getChildren() {
		return children;
	}

	/**
	 * 判断是否应用路径
	 * 
	 * @return
	 */
	public boolean isAppPath() {
		return path.startsWith(PREFIX);
	}

	/**
	 * 读取格式化后路径
	 * 
	 * @return
	 */
	public String getFormattedPath() {
		return isAppPath() ? WebApp.getAppPath() + path.substring(PREFIX.length()) : path;
	}

	/**
	 * 读取去掉AppPath后的路径
	 * 
	 * @return
	 */
	public String getTruePath() {
		return isAppPath() ? path.substring(PREFIX.length()) : path;
	}

	/**
	 * 
	 * @return
	 */
	@Transient
	public String getTypeCode() {
		return this.getType() != null ? this.getType().getCode() : "";
	}

	public boolean isHavingByRole() {
		return isHavingByRole;
	}

	public void setHavingByRole(boolean isHavingByRole) {
		this.isHavingByRole = isHavingByRole;
	}
}

package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 角色资源关系信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-10
 * @since 1.0
 */
@Entity
@Table(name = "hm_role_resource")
public class RoleResource extends Model {

	private static final long serialVersionUID = 1081918363900800008L;

	/** 角色 */
	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;
	
	/** 资源 */
	@Column(name = "resource")
	private String resource;
	
	/** 类型 */
	@Column(name = "type")
	private String type;

	/**
	 * 读取角色
	 * 
	 * @return
	 * @see #role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * 设置角色
	 * 
	 * @param role
	 * @see #role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 读取资源
	 * 
	 * @return
	 * @see #resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * 设置资源
	 * 
	 * @param resource
	 * @see #resource
	 */
	public void setResource(String resource) {
		this.resource = resource;
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
	 * 读取类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		return Dicts.name(type, Type.class);
	}
	
	/**
	 * 类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-12-10
	 * @since 1.0
	 */
	public static final class Type {
		
		@Element("导航")
		public static final String NAV 			= "navigationResource";
		
		@Element("借款状态")
		public static final String LOAN_STATUS	= "loanStatusResource";
	}
}

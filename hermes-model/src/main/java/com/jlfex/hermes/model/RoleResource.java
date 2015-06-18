package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 角色资源关系信息模型
 */
@Entity
@Table(name = "hm_role_resource")
public class RoleResource extends Model {

	private static final long serialVersionUID = 1081918363900800008L;

	/** 角色 */
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	/** 资源 */
	@Column(name = "resource_id")
	private String resource;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	@Column(name = "status")
	private String status = HermesConstants.VALID;

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
	 * 获取状态
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		return Dicts.name(type, type,Type.class);
	}
	
	/**
	 * 读取状态名称
	 * 
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}
	
	/**
	 * 类型
	 */
	public static final class Type {
		
		@Element("导航")
		public static final String NAV 			= "navigationResource";
		
		@Element("借款状态")
		public static final String LOAN_STATUS	= "loanStatusResource";
		
		@Element("后台权限")
		public static final String BACK_PRIVILEGE = "01";
	}
	

	/**
	 * 接口状态
	 *
	 */
	public static final class Status {
		@Element("有效")
		public static final String  VALID = "0";
		@Element("无效")
		public static final String  INVALID = "1";

	}

}

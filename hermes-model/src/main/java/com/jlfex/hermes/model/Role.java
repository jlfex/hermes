package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 角色信息模型
 */
@Entity
@Table(name = "hm_role")
public class Role extends Model {

	private static final long serialVersionUID = 1212385232176773526L;
	private static final String PREFIX = "ROLE_";

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 状态 */
	@Column(name = "status")
	private String status = Role.Status.ENABLED;

	/** 类型 */
	@Column(name = "type")
	private String type;
	
	/** 所属用户角色id */
	@Transient
	private String belongUserRoleId;
	
	@Transient
	private String creatorName;
	

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBelongUserRoleId() {
		return belongUserRoleId;
	}

	public void setBelongUserRoleId(String belongUserRoleId) {
		this.belongUserRoleId = belongUserRoleId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		if (Strings.notEmpty(status)) {
			return Dicts.name(status, Status.class);
		} else {
			return null;
		}

	}

	public String getTypeName() {
		if (Strings.notEmpty(type)) {
			return Dicts.name(type, Type.class);
		} else {
			return null;
		}
	}

	/**
	 * 读取授权代码
	 * 
	 * @return
	 */
	public String getAuthCode() {
		return PREFIX + code.toUpperCase();
	}

	/**
	 * 状态
	 */
	public static final class Status {

		@Element("正常")
		public static final String ENABLED = "00";

		@Element("隐藏")
		public static final String HIDDEN = "88";

		@Element("无效")
		public static final String DISABLED = "99";
	}

	/**
	 * 角色类型
	 */
	public static final class Type {
		@Element("软件模式")
		public static final String SOFT_MODEL = "00";
		@Element("系统权限")
		public static final String SYS_AUTH = "01";
	}
}

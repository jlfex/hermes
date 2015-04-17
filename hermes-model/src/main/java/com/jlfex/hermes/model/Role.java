package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 角色信息模型
 */
@Entity
@Table(name = "hm_role")
public class Role extends Model {

	private static final long serialVersionUID 	= 1212385232176773526L;
	private static final String PREFIX			= "ROLE_";
	
	/** 名称 */
	@Column(name = "name")
	private String name;
	
	/** 代码 */
	@Column(name = "code")
	private String code;
	
	/** 状态 */
	@Column(name = "status")
	private String status;

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
	
	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, Status.class);
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
		public static final String ENABLED	= "00";
		
		@Element("隐藏")
		public static final String HIDDEN	= "88";
		
		@Element("无效")
		public static final String DISABLED	= "99";
	}
}

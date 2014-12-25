package com.jlfex.hermes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户认证信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-23
 * @since 1.0
 */
@Entity
@Table(name = "hm_user_auth")
public class UserAuth extends Model {

	private static final long serialVersionUID = 251083716484859753L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/** 代码 */
	@Column(name = "code")
	private String code;
	
	/** 过期时间 */
	@Column(name = "expire")
	private Date expire;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	/** 状态 */
	@Column(name = "status")
	private String status;

	/**
	 * 读取用户
	 * 
	 * @return
	 * @see #user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param user
	 * @see #user
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * 读取过期时间
	 * 
	 * @return
	 * @see #expire
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param expire
	 * @see #expire
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
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
	 * 
	 * @author Aether
	 * @date 2013-12-23 下午3:40:57
	 * @return
	 * @description:读取类型名称
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * 
	 * @author Aether
	 * @date 2013-12-23 下午3:41:39
	 * @return
	 * @description:读取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	public static final class Type {
		@Element("EMAIL")
		public static final String EMAIL = "00";

		@Element("SMS")
		public static final String SMS = "01";
	}

	public static final class Status {
		@Element("已验证")
		public static final String VERIFY = "00";

		@Element("未验证")
		public static final String WAITVERIFY = "01";

		@Element("过期")
		public static final String OVERDUE = "99";
	}
}

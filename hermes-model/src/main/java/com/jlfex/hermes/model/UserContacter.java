package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户联系人信息模型
 * @since 1.0
 */
@Entity
@Table(name = "hm_user_contacter")
public class UserContacter extends Model {
	
	private static final long serialVersionUID = 9070090976628421448L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	/** 姓名 */
	@Column(name = "name")
	private String name;
	
	/** 关系 */
	@Column(name = "relationship")
	private String relationship;
	
	/** 地址 */
	@Column(name = "address")
	private String address;
	
	/** 电话 */
	@Column(name = "phone")
	private String phone;
	
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
	 * 读取姓名
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置姓名
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 读取关系
	 * 
	 * @return
	 * @see #relationship
	 */
	public String getRelationship() {
		return relationship;
	}
	
	/**
	 * 设置关系
	 * 
	 * @param relationship
	 * @see #relationship
	 */
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	/**
	 * 读取地址
	 * 
	 * @return
	 * @see #address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 设置地址
	 * 
	 * @param address
	 * @see #address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 读取电话
	 * 
	 * @return
	 * @see #phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 设置电话
	 * 
	 * @param phone
	 * @see #phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return description:获取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 状态
	 */
	public static final class Status {
		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}
}

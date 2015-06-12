package com.jlfex.hermes.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户角色关系信息模型
 */
@Entity
@Table(name = "hm_user_role")
public class UserRole extends Model {

	private static final long serialVersionUID = -7685004036387712253L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	/** 角色 */
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

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
}

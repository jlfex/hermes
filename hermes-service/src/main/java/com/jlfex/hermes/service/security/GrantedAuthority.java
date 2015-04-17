package com.jlfex.hermes.service.security;

import com.jlfex.hermes.model.Role;

/**
 * 授权信息
 */
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

	private static final long serialVersionUID = -1610664672669197223L;
	
	/** 权限 */
	private String authority;
	
	/**
	 * 构造函数
	 * 
	 * @param role
	 */
	public GrantedAuthority(Role role) {
		if (role != null) authority = role.getAuthCode();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		return authority;
	}
}

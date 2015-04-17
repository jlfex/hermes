package com.jlfex.hermes.service.security;
import java.util.Collection;
import java.util.LinkedList;
import org.springframework.security.core.GrantedAuthority;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.User;

/**
 * 用户明细
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

	private static final long serialVersionUID = -7455301076168577956L;

	/** 用户信息 */
	private User user;
	
	/** 权限集合 */
	private Collection<GrantedAuthority> authorities;
	
	/**
	 * 构造函数
	 * 
	 * @param user
	 */
	public UserDetails(User user) {
		this.user = user;
	}
	
	/**
	 * 读取用户信息
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return user.getAccount();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return user.getSignPassword();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return !User.Status.DISABLED.equals(user.getStatus());
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !User.Status.FROZEN.equals(user.getStatus());
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(authorities==null)
		{
			setAuthorities();
		}
		return authorities;
	}
	
	/**
	 * 设置权限
	 */
	protected void setAuthorities() {
		authorities = new LinkedList<GrantedAuthority>();
		try {
			for (Role role: user.getRoles()) {
				authorities.add(new com.jlfex.hermes.service.security.GrantedAuthority(role));
			}
		} catch (NullPointerException e) {
			Logger.warn("user %s has no role.", user.getEmail());
		}
	}
}

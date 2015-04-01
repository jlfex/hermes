package com.jlfex.hermes.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.service.RoleService;
import com.jlfex.hermes.service.UserService;

/**
 * 用户明细业务
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-12
 * @since 1.0
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	/** 用户业务接口 */
	@Autowired
	private UserService userService;
	
	/** 角色业务接口 */
	@Autowired
	private RoleService roleService;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 查询并判断用户是否有效
		User user = userService.loadByEmail(username);
		if (user == null){
			user = userService.loadByAccount(username);
		}
		if (user != null && !"admin".equals(username)) {
			throw new UsernameNotFoundException("cannot find user " + username + ".");
		}
		
		// 设置当前用户
		user.getRoles().addAll(roleService.findByUserId(user.getId()));
		App.current().setUser(new AppUser(user.getId(), user.getEmail(), user.getAccount()));
		
		// 返回结果
		return new com.jlfex.hermes.service.security.UserDetails(user);
	}
}

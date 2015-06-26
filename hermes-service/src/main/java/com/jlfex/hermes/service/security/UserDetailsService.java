package com.jlfex.hermes.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.service.RoleService;
import com.jlfex.hermes.service.UserService;

/**
 * 用户明细业务
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
		User  user = null;
		List<User> userList = userService.loadByAccountAndStatus(username, User.Status.ENABLED);
		if(userList !=null && userList.size() ==1){
			user = userList.get(0);
		}else{
			Logger.info("用户登录失败：当前用户状态异常或不存在：username="+username);
			throw new UsernameNotFoundException("用户状态异常");
		}
		if (user == null) {
			Logger.info("找不到后台用户信息: %s",username);
			throw new UsernameNotFoundException("cannot find user " + username + ".");
		}
		//判断 用户是类型 及状态 是否是状态正常的后台用户
		if(!(User.Type.ADMIN.equals(user.getType()) || 
		    User.Type.NORMAL_ADMIN.equals(user.getType())) &&
			User.Status.ENABLED.equals(user.getStatus())){
			Logger.info("无效的后台用户: %s,类型：%s,状态：%s",username,user.getTypeName(),user.getStatusName() );
			throw new UsernameNotFoundException("无效的用户: " + username + ".");
		}
		// 设置当前用户
		user.getRoles().addAll(roleService.findByUserId(user.getId()));
		App.current().setUser(new AppUser(user.getId(), user.getEmail(), user.getAccount()));
		// 返回结果
		return new com.jlfex.hermes.service.security.UserDetails(user);
	}
}

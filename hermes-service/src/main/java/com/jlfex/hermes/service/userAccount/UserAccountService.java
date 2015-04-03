package com.jlfex.hermes.service.userAccount;

import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;


/**
 * 用户账户  业务
 * @author Administrator
 *
 */
public interface UserAccountService {
	
	
	/**
	 * 通过用户编号和类型查询用户账户
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public UserAccount findByUserIdAndType(String userId, String type);
	
	/**
	 * 通过用户及类型查询用户账户
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	public UserAccount findByUserAndType(User user, String type);
}

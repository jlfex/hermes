package com.jlfex.hermes.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.service.pojo.UserBasic;

/**
 * 用户业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-12
 * @since 1.0
 */
public interface UserService {

	/**
	 * 通过编号加载用户
	 * 
	 * @param id
	 * @return
	 */
	public User loadById(String id);
	
	/**
	 * 通过邮件加载用户
	 * 
	 * @param email
	 * @return
	 */
	public User loadByEmail(String email);
	
	/**
	 * 通过帐号加载用户
	 * 
	 * @param account
	 * @return
	 */
	public User loadByAccount(String account);
	
	/**
	 * 通过输入查询用户<br>
	 * 输入内容可能是帐号、邮件、手机或者实名<br>
	 * 均为模糊查询
	 * 
	 * @param input
	 * @return
	 */
	public List<User> findByInput(String input);
	
	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param original
	 * @param reset
	 * @return
	 */
	public User resetPassword(String userId, String original, String reset);

	/**
	 * 检查Email是否被激活
	 * 
	 * @param email
	 * @return <code>true</code>为激活
	 */
	public boolean checkEmail(String email);

	/**
	 * 检查手机号是否被认证
	 * 
	 * @param phone
	 * @return
	 */
	public boolean checkPhone(String phone);

	/**
	 * 注册新用户
	 * 
	 * @param user
	 */
	public void signUp(User user)  throws Exception ;

	/**
	 * 完善注册信息
	 * 
	 * @param user
	 */
	//public String signSupplement(UserBasic userBasic, HttpServletRequest req);

	/**
	 * 获取发送激活邮件模板
	 * 
	 * @param user
	 */
	public String getActiveMailModel(User user, HttpServletRequest req);

	/**
	 * 处理激活邮件
	 * 
	 * @param userId
	 * @param validateCode
	 */
	public Result<?> activeMail(String userId, String validateCode);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public Result<?> signIn(User user);

	/**
	 * 判断邮箱 是否已经使用
	 * @param email
	 * @return
	 */
	public boolean isExistentEmail(String email);

	/**
	 * 发送重置密码邮件
	 * 
	 * @param email
	 */
	public void sendResetPwdEmail(String email, HttpServletRequest req);

	/**
	 * 密码重置邮件处理
	 * 
	 * @param userId
	 * @param validCode
	 * @return
	 */
	public Result<?> handleRetrieveMail(String userId, String validCode);

	/**
	 * 用户登出
	 * 
	 * @param user
	 */
	public void signOut();

	/**
	 * 找回密码
	 * 
	 * @param userId
	 * @param newPwd
	 */
	public void retrievePwd(String userId, String newPwd);
	
	/**
	 * 读取头像
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	public String getAvatar(User user, String type);

	/**
	 * 根据account（昵称）查找用户
	 * 
	 * @param account
	 * @return
	 */
	public User getUserByAccount(String account);
	
	/**
	 * 通过用户编号加载用户属性
	 * 
	 * @param userId
	 * @return
	 */
	public UserProperties loadPropertiesByUserId(String userId);
}

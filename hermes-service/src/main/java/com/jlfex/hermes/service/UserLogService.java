package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.service.pojo.UserLogVo;

/**
 * 用户日志接口
 */
public interface UserLogService {
	public void saveUserLog(UserLog userLog);

	/**
	 * 根据用户 获取操作日志
	 * 
	 * @param user
	 * @return
	 */
	public List<UserLog> queryUserLogByuser(User user);

	/**
	 * 分页查询用户日志
	 * 
	 * @param userLogVo
	 *            查询条件vo
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public Page<UserLog> queryByCondition(UserLogVo userLogVo, String page, String size) throws Exception;

	/**
	 * 根据id获取用户日志
	 * 
	 * @param id
	 * @return
	 */
	public UserLog findOne(String id);
}

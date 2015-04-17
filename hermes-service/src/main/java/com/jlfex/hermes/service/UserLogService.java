package com.jlfex.hermes.service;
import java.util.List;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserLog;

/**
 * 用户日志接口
 */
public interface UserLogService {
	public void saveUserLog(UserLog userLog);
	/**
	 * 根据用户 获取操作日志
	 * @param user
	 * @return
	 */
	public List<UserLog> queryUserLogByuser(User user);
}

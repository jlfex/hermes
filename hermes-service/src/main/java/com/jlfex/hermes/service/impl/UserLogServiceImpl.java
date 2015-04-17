package com.jlfex.hermes.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.repository.UserLogRepository;
import com.jlfex.hermes.service.UserLogService;


public class UserLogServiceImpl implements UserLogService {
	/** 用户日志仓库 */
	@Autowired
	private UserLogRepository userLogRepository;

	@Override
	public void saveUserLog(UserLog userLog) {
		userLogRepository.save(userLog);

	}
    /**
     * 获取用户操作日志 用户
     */
	@Override
	public List<UserLog> queryUserLogByuser(User user) {
		return userLogRepository.findByUser(user);
	}
}

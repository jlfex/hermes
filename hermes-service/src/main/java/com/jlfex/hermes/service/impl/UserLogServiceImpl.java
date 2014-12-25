package com.jlfex.hermes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

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
}

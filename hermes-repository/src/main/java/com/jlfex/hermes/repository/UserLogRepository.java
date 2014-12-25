package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlfex.hermes.model.UserLog;

/**
 * 用户日志仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
public interface UserLogRepository extends JpaRepository<UserLog, String> {

}

package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserLog;

/**
 * 用户日志仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
public interface UserLogRepository extends JpaRepository<UserLog, String>, JpaSpecificationExecutor<UserLog> {
    /**
     * 根据用户查收日志
     * @param user
     * @return
     */
	public List<UserLog>  findByUser(User user);
}

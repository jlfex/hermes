package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserJob;

/**
 * 用户工作信息仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Repository
public interface UserJobRepository extends JpaRepository<UserJob, String> {
	/**
	 * 根据用户查找相关的工作信息
	 * 
	 * @param user
	 * @return
	 */
	List<UserJob> findByUser(User user);

	/**
	 * 根据用户id查找相关的工作信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserJob> findByUserId(String userId);
}

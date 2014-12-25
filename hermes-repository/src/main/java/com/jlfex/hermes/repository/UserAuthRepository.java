package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAuth;

/**
 * 用户认证仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {

	/**
	 * 根据用户和验证码
	 * 
	 * @param user
	 * @param code
	 * @return
	 */
	UserAuth findByUserAndCodeOrderByCreateTimeDesc(User user, String code);

}

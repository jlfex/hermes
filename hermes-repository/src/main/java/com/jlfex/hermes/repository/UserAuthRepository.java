package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAuth;

/**
 * 用户认证仓库
 */
@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String>, JpaSpecificationExecutor<UserAuth> {

	/**
	 * 根据用户和验证码
	 * 
	 * @param user
	 * @param code
	 * @return
	 */
	UserAuth findByUserAndCodeOrderByCreateTimeDesc(User user, String code);

}

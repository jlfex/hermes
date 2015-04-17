package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserContacter;

/**
 * 用户联系人信息仓库
 */
@Repository
public interface UserContacterRepository extends JpaRepository<UserContacter, String>, JpaSpecificationExecutor<UserContacter> {
	/**
	 * 根据用户查找联系人信息
	 * 
	 * @param user
	 * @return
	 */
	public List<UserContacter> findByUser(User user);

	/**
	 * 根据用户id查找联系人信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserContacter> findByUserId(String userId);
}

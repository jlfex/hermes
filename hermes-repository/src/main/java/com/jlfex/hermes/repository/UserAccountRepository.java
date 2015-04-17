package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;

/**
 * 用户账户信息仓库
 * 
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {
	
	/**
	 * 根据用户查找账户
	 * 
	 * @param user
	 * @return
	 */
	public List<UserAccount> findByUser(User user);
	
	/**
	 * 通过用户编号查询用户账户
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserAccount> findByUserId(String userId);
	
	/**
	 * 通过用户及类型查询用户账户
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	public UserAccount findByUserAndType(User user, String type);
	
	/**
	 * 通过用户编号和类型查询用户账户
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public UserAccount findByUserIdAndType(String userId, String type);
}

package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;

/**
 * 用户信息仓库
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	/** 
	 * 根据email查询用户
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);

	/**
	 * 根据phone查询用户
	 * 
	 * @param cellPhone
	 * @return
	 */
	List<User> findByCellphone(String cellPhone);

	/**
	 * 根据昵称查询用户
	 * 
	 * @param account
	 * @return
	 */
	User findByAccount(String account);
	/**
	 * 根据创建者获取用户
	 * @param creator
	 * @return
	 */
	List<User> findByCreator(String creator);
	/**
	 * 根据用户和状态
	 * @param account
	 * @param status
	 * @return
	 */
	List<User> findByAccountAndStatus(String account,String status);
}

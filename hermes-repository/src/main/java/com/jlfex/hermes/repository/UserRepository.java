package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.User;

/**
 * 用户信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-14
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

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
}

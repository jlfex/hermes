package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserCar;

/**
 * 用户车辆信息仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Repository
public interface UserCarRepository extends JpaRepository<UserCar, String> {
	/**
	 * 根据用户查找车辆信息
	 * 
	 * @param user
	 * @return
	 */
	public List<UserCar> findByUser(User user);

	/**
	 * 根据用户id查找车辆信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserCar> findByUserId(String userId);
}

package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlfex.hermes.model.UserAddress;

/**
 * 用户地址仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2014-1-2
 * @since 1.0
 */
public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
	/**
	 * 通过用户编号和类型查找用户地址信息
	 * 
	 * @param userId
	 * @return
	 */
	UserAddress findByUserIdAndType(String userId, String type);
}

package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jlfex.hermes.model.UserAddress;

/**
 * 用户地址仓库
 */
public interface UserAddressRepository extends JpaRepository<UserAddress, String>, JpaSpecificationExecutor<UserAddress> {
	/**
	 * 通过用户编号和类型查找用户地址信息
	 * 
	 * @param userId
	 * @return
	 */
	UserAddress findByUserIdAndType(String userId, String type);
}

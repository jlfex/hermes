package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jlfex.hermes.model.UserRole;

/**
 * 用户角色仓库
 */
public interface UserRoleRepository extends JpaRepository<UserRole, String>, JpaSpecificationExecutor<UserRole> {

	/**
	 * 通过用户编号查询所有角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserRole> findByUserId(String userId);
}

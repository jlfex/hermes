package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jlfex.hermes.model.Role;
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
	/**
	 * 根据用户Id + 角色类型  获取用户角色
	 * @param userId
	 * @param type
	 * @return
	 */
	@Query("from UserRole  where user.id =:userId and role.type = :type")
	public List<UserRole> findByUserIdAndRoleType(@Param("userId") String userId, @Param("type") String type );
	
	/**
	 * 根据用户角色查询用户
	 * @param role
	 * @return
	 */
	public List<UserRole> findByRole(Role role);
	
	
}

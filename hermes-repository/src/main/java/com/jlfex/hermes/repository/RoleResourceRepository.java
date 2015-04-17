package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;

/**
 * 角色资源关系仓库
 */
@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResource, String>, JpaSpecificationExecutor<RoleResource> {

	/**
	 * 通过资源和类型查询数据
	 * 
	 * @param resource
	 * @param type
	 * @return
	 */
	public List<RoleResource> findByResourceAndType(String resource, String type);
	
	/**
	 * 通过角色列表和类型查询数据
	 * 
	 * @param role
	 * @param type
	 * @return
	 */
	public List<RoleResource> findByRoleInAndType(List<Role> role, String type);
	
	/**
	 * 通过类型查询数据
	 * 
	 * @param type
	 * @return
	 */
	public List<RoleResource> findByType(String type);
}

package com.jlfex.hermes.repository.role;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Role;

/**
 * 角色
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
	public Role findOneByCode(String code);
	 /**
	 * 状态 + 类型  获取角色列表
	 * @param type
	 * @param status
	 * @return
	 */
	 List<Role> findByTypeAndStatus(String type,String status);
	 /**
	  * 状态 + 类型 + 创建者 获取角色列表
	  * @param type
	  * @param status
	  * @param creator
	  * @return
	  */
	 List<Role> findByTypeAndStatusAndCreator(String type,String status,String creator);
}

package com.jlfex.hermes.service.userRole;
import java.util.List;
import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.service.pojo.privilege.user.UserRoleVo;


/**
 * 用户角色
 * @author Administrator
 *
 */
public interface UserRoleService {

	/**
	 * 根据用户id 获取所有角色
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserRole> findByUserId(String userId) throws Exception ;
    /**
     * 查询用户角色列表
     * @param userRoleVo
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
	public Page<User> queryByCondition(UserRoleVo userRoleVo, String page, String size) throws Exception;
    /**
     * 查询角色列表
     * @param userRoleVo
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
	public Page<Role> queryRoleListByCondition(UserRoleVo userRoleVo, String page, String size) throws Exception;
	/**
	 * 用户添加角色
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public UserRole save(UserRole entity) throws Exception;
	/**
	 * 根据用户Id + 角色类型  获取角色列表
	 * @param userId
	 * @param roleType
	 * @return
	 * @throws Exception
	 */
	public List<UserRole> findByUserIdAndRoleType(String userId, String roleType) throws Exception;
	/**
	 * 删除用户角色关系
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleUserRoleById(String id) throws Exception;

}

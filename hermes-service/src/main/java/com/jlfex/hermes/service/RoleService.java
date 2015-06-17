package com.jlfex.hermes.service;
import java.util.List;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;

/**
 * 角色业务接口
 */
public interface RoleService {

	/**
	 * 通过用户查询相关角色
	 * 
	 * @param user
	 * @return
	 */
	public List<Role> findByUserId(String userId);
	
	/**
	 * 通过资源和类型查询角色
	 * 
	 * @param resource
	 * @param type
	 * @return
	 */
	public List<Role> findByResourceAndType(String resource, String type);
	
	/**
	 * 通过类型查询资源
	 * 
	 * @param type
	 * @return
	 */
	public List<RoleResource> findRoleResourceByType(String type);
	
	/**
	 * 通过用户编号和类型查询资源
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<String> findResourceByUserIdAndType(String userId, String type);

	/**
	 * 根据类型+状态 获取角色列表
	 * @param type
	 * @return
	 */
	public  List<Role> findByTypeAndStatus(String type, String status);
    /**
     * 根据类型+状态+创建者  获取角色列表
     * @param type
     * @param status
     * @param creator
     * @return
     */
	public  List<Role> findByTypeAndStatusAndCreator(String type, String status, String creator);
	/**
	 * 根据id 获取角色
	 * @param id
	 * @return
	 */
	public Role findById(String id);
	
	
	
}

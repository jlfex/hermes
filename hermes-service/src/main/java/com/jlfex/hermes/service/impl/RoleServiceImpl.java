package com.jlfex.hermes.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.service.RoleService;

/**
 * 角色业务实现
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	/** 角色资源关系仓库 */
	@Autowired
	private RoleResourceRepository roleResourceRepository;
	
	/** 用户角色仓库 */
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.RoleService#findByUserId(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> findByUserId(String userId) {
		return getRolesFromUserRoles(userRoleRepository.findByUserId(userId));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.RoleService#findByResourceAndType(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> findByResourceAndType(String resource, String type) {
		return getRolesFromRoleResources(roleResourceRepository.findByResourceAndType(resource, type));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.RoleService#findRoleResourceByType(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<RoleResource> findRoleResourceByType(String type) {
		return roleResourceRepository.findByType(type);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.RoleService#findResourceByUserIdAndType(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<String> findResourceByUserIdAndType(String userId, String type) {
		List<Role> roles = getRolesFromUserRoles(userRoleRepository.findByUserId(userId));
		return getResourcesFromRoleResources(roleResourceRepository.findByRoleInAndType(roles, type));
	}
	
	/**
	 * 从角色资源关系列表中读取角色列表
	 * 
	 * @param roleResources
	 * @return
	 */
	protected List<Role> getRolesFromRoleResources(List<RoleResource> roleResources) {
		Map<String, Role> map = new HashMap<String, Role>(roleResources.size());
		for (RoleResource roleResource: roleResources) map.put(roleResource.getRole().getId(), roleResource.getRole());
		return new ArrayList<Role>(map.values());
	}
	
	/**
	 * 从角色资源关系列表中读取资源列表
	 * 
	 * @param roleResources
	 * @return
	 */
	protected List<String> getResourcesFromRoleResources(List<RoleResource> roleResources) {
		Map<String, String> map = new HashMap<String, String>(roleResources.size());
		for (RoleResource roleResource: roleResources) map.put(roleResource.getResource(), roleResource.getResource());
		return new ArrayList<String>(map.values());
	}
	
	/**
	 * 从用户角色关系列表中读取角色列表
	 * 
	 * @param userRoles
	 * @return
	 */
	protected List<Role> getRolesFromUserRoles(List<UserRole> userRoles) {
		Map<String, Role> map = new HashMap<String, Role>(userRoles.size());
		for (UserRole userRole: userRoles) map.put(userRole.getRole().getId(), userRole.getRole());
		return new ArrayList<Role>(map.values());
	}
}

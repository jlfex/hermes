package com.jlfex.hermes.service.role;

import java.util.List;

/**
 * 
 * @author jswu
 *
 */
public interface RoleResourceService {
	/**
	 * 获取RoleResource
	 * 
	 * @return
	 */
	public List<String> getFrontIndexRoleResource();

	/**
	 * 获取后台权限
	 * 
	 * @return
	 */
	public List<String> getBackRoleResource();
}

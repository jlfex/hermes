package com.jlfex.hermes.service.role;

import java.util.List;

import com.jlfex.hermes.model.Dictionary;

/**
 * 
 * @author jswu
 *
 */
public interface RoleResourceService {
	/**
	 * 获取软件模式(对应Role的软件模式)的权限
	 * @param type
	 * @return
	 */
	public List<String> getSoftModelRoleResource(Dictionary type);

	/**
	 * 获取后台权限
	 * 
	 * @return
	 */
	public List<String> getBackRoleResource();
}

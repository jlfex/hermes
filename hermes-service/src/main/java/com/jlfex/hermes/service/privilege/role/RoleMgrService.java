package com.jlfex.hermes.service.privilege.role;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.User;

/**
 * 权限： 角色管理
 * 
 * @author Administrator
 *
 */
public interface RoleMgrService {
	public Page<Role> findRoleList(final String code, final String name, String page, String size,User creator);

	/**
	 * 是否是合法的code
	 * 
	 * @param addOrEdit
	 *            对象唯一id,如果对象不存在则存入-1
	 * @param code
	 * @return
	 */
	public boolean isValidCode(String addOrEdit, String code);
}

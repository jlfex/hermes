package com.jlfex.hermes.service.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.service.UserInfoService;

@Service
@Transactional
public class RoleResourceServiceImpl implements RoleResourceService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;
	@Autowired
	private RoleResourceRepository roleResourceRepository;
	@Autowired
	private NavigationRepository navigationRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private DictionaryRepository dictionaryRepository;

	public static Map<String, List<String>> backRoleResourceMap = new ConcurrentHashMap<String, List<String>>();

	/**
	 * 获取软件模式(对应Role的软件模式)的权限
	 * 
	 * @param type
	 *            console(后台菜单权限)、ftont_nav(前台菜单权限)
	 * @return
	 */
	@Override
	public List<String> getSoftModelRoleResource(Dictionary type) {
		User user = userRepository.findByAccount(HermesConstants.PLAT_MANAGER);
		List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
		List<String> indexRoleResource = new ArrayList<String>();

		// 平台用户角色
		List<Role> roles = new ArrayList<Role>();
		for (UserRole userRole : userRoles) {
			roles.add(userRole.getRole());
		}

		// 当前用户角色资源
		List<RoleResource> nowUserResources = new ArrayList<RoleResource>();
		AppUser curUser = App.current().getUser();
		User nowUser = null;
		if (curUser != null) {
			nowUser = userInfoService.findByUserId(curUser.getId());
		}

		if (userRoles != null) {
			DictionaryType dictionaryType = dictionaryTypeRepository.findByCode(HermesConstants.DIC_NAV);
			List<RoleResource> roleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(roles, dictionaryType.getId(), HermesConstants.VALID);
			// 当前用户是否是平台用户
			boolean flag = false;
			if (nowUser == null || nowUser.getAccount().equals(HermesConstants.PLAT_MANAGER)) {
				flag = true;
			}

			// 平台和当前用户角色资源的交集
			List<RoleResource> platAndCurUserRoleResources = new ArrayList<RoleResource>();

			if (flag) {
				platAndCurUserRoleResources = roleResources;
			} else {
				List<UserRole> nowUserRoles = userRoleRepository.findByUserId(nowUser.getId());
				// 当前用户角色
				List<Role> nowRoles = new ArrayList<Role>();
				for (UserRole userRole : nowUserRoles) {
					nowRoles.add(userRole.getRole());
				}

				if (nowRoles != null && nowRoles.size() == 1) {
					nowUserResources = roleResourceRepository.findByRoleInAndTypeAndStatus(nowRoles, RoleResource.Type.BACK_PRIVILEGE, HermesConstants.VALID);
				}

				for (RoleResource roleResource : roleResources) {
					for (RoleResource nowResource : nowUserResources) {
						if (nowResource.getResource().equals(roleResource.getResource())) {
							platAndCurUserRoleResources.add(roleResource);
							break;
						}
					}
				}

			}

			for (RoleResource roleResource : platAndCurUserRoleResources) {
				Navigation navigation = navigationRepository.findOne(roleResource.getResource());
				if (navigation.getType().getId().equals(type.getId())) {
					indexRoleResource.add(navigation.getCode());
				}
			}
		}

		return indexRoleResource;
	}

	/**
	 * 获取某一角色(对应Role的系统权限)的权限，针对后端
	 */
	@Override
	public List<String> getBackRoleResource() {
		AppUser curUser = App.current().getUser();
		List<String> backRoleResources = new ArrayList<String>();
		if (curUser != null) {
			User user = userInfoService.findByUserId(curUser.getId());
			if (user.getAccount().equals(HermesConstants.PLAT_MANAGER)) {
				if (backRoleResourceMap.containsKey(HermesConstants.PLAT_MANAGER)) {
					return backRoleResourceMap.get(HermesConstants.PLAT_MANAGER);
				} else {
					Dictionary dictionary = dictionaryRepository.findByCodeAndStatus(HermesConstants.DIC_CONSOLE, Dictionary.Status.VALID);
					backRoleResources = this.getSoftModelRoleResource(dictionary);
					backRoleResourceMap.put(HermesConstants.PLAT_MANAGER, backRoleResources);
				}
			} else {
				List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
				List<Role> roles = new ArrayList<Role>();
				for (UserRole userRole : userRoles) {
					roles.add(userRole.getRole());
				}

				if (backRoleResourceMap.containsKey(roles.get(0).getCode())) {
					return backRoleResourceMap.get(roles.get(0).getCode());
				} else {
					if (roles != null && roles.size() > 0) {
						List<RoleResource> roleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(roles, RoleResource.Type.BACK_PRIVILEGE, RoleResource.Status.VALID);
						for (RoleResource roleResource : roleResources) {
							Navigation navigation = navigationRepository.findOne(roleResource.getResource());
							if (navigation != null) {
								backRoleResources.add(navigation.getCode());
							}
						}
					}

					backRoleResourceMap.put(roles.get(0).getCode(), backRoleResources);

				}
			}
		}

		return backRoleResources;
	}

	/**
	 * 清除后台角色权限库
	 * 
	 * @param roleCode
	 */
	public static void clearBackRoleResourceMap(String roleCode) {
		if (Strings.notEmpty(roleCode)) {
			if (backRoleResourceMap.containsKey(roleCode.trim())) {
				backRoleResourceMap.remove(roleCode.trim());
				Logger.info("后台角色权限集合缓存Filter已经清理完毕");
			}
		}
	}
}

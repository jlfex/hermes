package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
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
import com.jlfex.hermes.service.NavigationService;
import com.jlfex.hermes.service.UserInfoService;

/**
 * 导航业务实现
 */
@Service
@Transactional
public class NavigationServiceImpl implements NavigationService {

	private static final String CODE_DICTIONARY_NAVIGATION = "nav";

	/** 导航信息仓库 */
	@Autowired
	private NavigationRepository navigationRepository;

	/** 字典信息仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;

	@Autowired
	private RoleResourceRepository roleResourceRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserInfoService userInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.NavigationService#loadById(java.lang.String)
	 */
	@Override
	public Navigation loadById(String id) {
		return navigationRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.NavigationService#findRootByTypeCode(java.lang
	 * .String)
	 */
	@Override
	public List<Navigation> findRootByTypeCode(String typeCode) {
		if (Strings.empty(typeCode)) {
			throw new ServiceException("导航类型码：typeCode为空");
		}
		AppUser curUser = App.current().getUser();
		List<Navigation> secNavigations = new ArrayList<Navigation>();
		if (curUser != null) {
			User user = userInfoService.findByUserId(curUser.getId());
			List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
			List<Role> roles = new ArrayList<Role>();
			for (UserRole userRole : userRoles) {
				roles.add(userRole.getRole());
			}
			
			List<RoleResource> roleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(roles, RoleResource.Type.BACK_PRIVILEGE, HermesConstants.VALID);
			Dictionary type = dictionaryRepository.findByTypeCodeAndCode(CODE_DICTIONARY_NAVIGATION, typeCode);
			Navigation navigation = navigationRepository.findOneByCode(HermesConstants.ROOT);
			List<Navigation> navigations = navigationRepository.findByParentAndTypeOrderByOrderAsc(navigation, type);
			
			if (user.getAccount().equals(HermesConstants.PLAT_MANAGER)) {
				for (Navigation navigation2 : navigations) {
					secNavigations.add(navigation2);
				}

			} else {
				for (Navigation navigation2 : navigations) {
					for (RoleResource resource : roleResources) {
						if (resource.getResource().equals(navigation2.getId())) {
							secNavigations.add(navigation2);
						}
					}
				}
			}
		}

		return secNavigations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.NavigationService#findByParent(com.jlfex.hermes
	 * .model.Navigation)
	 */
	@Override
	public List<Navigation> findByParent(Navigation parent) {
		if (parent == null) {
			throw new ServiceException("导航：parent为空");
		}
		List<Navigation> navigations = navigationRepository.findByParent(parent);
		return navigations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.NavigationService#findByParentId(java.lang.String
	 * )
	 */
	@Override
	public List<Navigation> findByParentId(String parentId) {
		if (Strings.empty(parentId)) {
			throw new ServiceException("导航：parentId为空");
		}
		Navigation parent = navigationRepository.findOne(parentId);
		return findByParent(parent);
	}
}

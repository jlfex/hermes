package com.jlfex.hermes.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.constant.HermesEnum.NavigationEnum;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.UserRoleRepository;

/**
 * 
 * @author jswu
 *
 */
public class MenuInterceptor extends HandlerInterceptorAdapter {
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

	private String mappingURL;

	public String getMappingURL() {
		return mappingURL;
	}

	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}

	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
		modelAndView.addObject("a", "a");
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		Model model = (Model) request.getAttribute("model");
		this.getMenuAuth(model);
		if (mappingURL != null && url.matches(url)) {

		}
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	private void getMenuAuth(Model model) {
		User user = userRepository.findByAccount(HermesConstants.PLAT_MANAGER);
		List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

		List<Role> roles = new ArrayList<Role>();
		for (UserRole userRole : userRoles) {
			roles.add(userRole.getRole());
			
		}

		if (userRoles != null) {
			DictionaryType dictionaryType = dictionaryTypeRepository.findByCode(HermesConstants.DIC_NAV);
			List<RoleResource> roleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(roles, dictionaryType.getId(), HermesConstants.VALID);
			Navigation homeNavigation = navigationRepository.findOneByCode(NavigationEnum.front_home.name());
			Navigation investNavigation = navigationRepository.findOneByCode(NavigationEnum.front_invest.name());
			Navigation loadNavigation = navigationRepository.findOneByCode(NavigationEnum.front_loan.name());
			Navigation accountNavigation = navigationRepository.findOneByCode(NavigationEnum.front_account_center.name());
			Navigation helpNavigation = navigationRepository.findOneByCode(NavigationEnum.front_help.name());

			for (RoleResource roleResource : roleResources) {
				if (homeNavigation.getId().equals(roleResource.getResource())) {
					model.addAttribute("home", true);
				}

				if (investNavigation.getId().equals(roleResource.getResource())) {
					model.addAttribute("invest", true);
				}

				if (loadNavigation.getId().equals(roleResource.getResource())) {
					model.addAttribute("loan", true);
				}

				if (accountNavigation.getId().equals(roleResource.getResource())) {
					model.addAttribute("account", true);
				}

				if (helpNavigation.getId().equals(roleResource.getResource())) {
					model.addAttribute("help", true);
				}
			}

			model.addAttribute("roleResources", roleResources);
		}
	}
}

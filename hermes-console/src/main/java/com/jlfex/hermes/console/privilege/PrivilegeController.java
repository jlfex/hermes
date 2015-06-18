package com.jlfex.hermes.console.privilege;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.service.NavigationService;
import com.jlfex.hermes.service.RoleService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.privilege.role.RoleMgrService;

/**
 * 权限管理
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/privilege")
public class PrivilegeController {
	@Autowired
	private RoleMgrService roleMgrService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private NavigationService navigationService;
	@Autowired
	private NavigationRepository navigationRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private RoleResourceRepository roleResourceRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	 * 角色管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/roleIndex")
	public String index(Model model) {
		return "privilege/role/index";
	}

	/**
	 * 编辑 角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrEdit/{id}")
	public String addOrEdit(@PathVariable("id") String id, Model model) {
		if (!id.equals(HermesConstants.OBJECT_NOT_EXIST)) {
			Role role = roleService.findOne(id);
			model.addAttribute("role", role);
		}

		return "privilege/role/addOrEdit";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") String id) {
		Result result = new Result();
		Role role = roleService.findOne(id);
		role.setStatus(HermesConstants.INVALID);

		Role role2 = roleService.save(role);
		if (role2 != null) {
			result.setType(Type.SUCCESS);
			result.addMessage("删除成功");
		} else {
			result.setType(Type.FAILURE);
			result.addMessage("删除失败");
		}

		return result;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/saveOrEdit")
	@ResponseBody
	public Result saveOrEdit(Role role) {
		Result result = new Result();
		try {
			if (role.getId() != null) {
				Role older = roleService.findOne(role.getId());
				BeanUtils.copyProperties(older, role);
				roleService.save(older);
				result.addMessage("编辑成功");
			} else {
				roleService.save(role);
				result.addMessage("新增成功");
			}
			result.setType(Type.SUCCESS);

		} catch (Exception e) {
			result.addMessage(role.getId() == null ? "新增失败" : "编辑失败");
		}

		return result;
	}

	/**
	 * 查询
	 * 
	 * @param creditorName
	 * @param cellphone
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String loandata(String code, String name, String page, String size, Model model) {
		model.addAttribute("lists", roleMgrService.findRoleList(code, name, page, size));
		return "privilege/role/data";
	}

	/**
	 * 判断Role的code是否合法
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/isValidCode")
	@ResponseBody
	public Result isValidCode(String addOrEdit, String code) {
		Result result = new Result();

		boolean isValid = roleMgrService.isValidCode(addOrEdit, code);
		if (isValid) {
			result.setType(Type.SUCCESS);
		} else {
			result.setType(Type.FAILURE);
		}

		return result;
	}

	/**
	 * 去往设置权限
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goPrivilege/{id}")
	public String goPrivilege(@PathVariable("id") String id, Model model) {
		Role role = roleService.findOne(id);
		model.addAttribute("role", role);

		return "privilege/role/setPrivilege";
	}

	/**
	 * 设置权限
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/setPrivilege")
	@ResponseBody
	public Result setPrivilege(@RequestParam("privileges") String privileges, @RequestParam("roleId") String roleId) {
		Result result = new Result();

		try {
			Role role = roleService.findOne(roleId);
			List<RoleResource> roleResources = new ArrayList<RoleResource>();

			List<RoleResource> havingRoleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(Arrays.asList(role), RoleResource.Type.BACK_PRIVILEGE, HermesConstants.VALID);
			for (RoleResource roleResource : havingRoleResources) {
				roleResource.setStatus(HermesConstants.INVALID);
			}
			roleResourceRepository.save(havingRoleResources);
			
			String[] privilegesArray = privileges.split(",");
			for (String navigation : privilegesArray) {
				RoleResource resource = new RoleResource();
				resource.setRole(role);
				resource.setResource(navigation);
				resource.setType(RoleResource.Type.BACK_PRIVILEGE);

				roleResources.add(resource);
			}
			roleResourceRepository.save(roleResources);
			result.setType(Type.SUCCESS);
			result.addMessage("设置权限成功");
		} catch (Exception e) {
			result.setType(Type.FAILURE);
			result.addMessage("设置权限失败");
		}

		return result;
	}

	/**
	 * 获取所有权限
	 * 
	 * @return
	 */
	@RequestMapping("/getPrivileges/{id}")
	@ResponseBody
	public Navigation getPrivileges(@PathVariable("id") String id) {
		Navigation navigation = navigationRepository.findOneByCode(HermesConstants.ROOT);
		Role role = roleService.findOne(id);
		List<RoleResource> roleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(Arrays.asList(role), RoleResource.Type.BACK_PRIVILEGE, HermesConstants.VALID);
		navigation.setHavingByRole(true);
		if(roleResources != null && roleResources.size() > 0) {
			this.setSomeRolePrivilege(navigation.getChildren(), roleResources);
		}
		
		return navigation;
	}

	/**
	 * 获取某一角色的权限
	 * 
	 * @return
	 */
	@RequestMapping("/getRoleResourceByRoles/{id}")
	@ResponseBody
	public List<RoleResource> getRoleResourceByRoles(@PathVariable("id") String id) {

		Role role = roleService.findOne(id);
		List<RoleResource> roleResources = roleResourceRepository.findByRoleInAndTypeAndStatus(Arrays.asList(role), RoleResource.Type.BACK_PRIVILEGE, HermesConstants.VALID);

		return roleResources;
	}

	public void setSomeRolePrivilege(List<Navigation> navigations, List<RoleResource> roleResources) {
		for (Navigation navigation : navigations) {
			for (RoleResource roleResource : roleResources) {
				if (navigation.getId().equals(roleResource.getResource())) {
					navigation.setHavingByRole(true);
				}
			}

			if (navigation.getChildren() != null && navigation.getChildren().size() > 0) {
				setSomeRolePrivilege(navigation.getChildren(), roleResources);
			}
		}
	}
}

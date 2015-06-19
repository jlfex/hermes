package com.jlfex.hermes.console.privilege;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.model.Dictionary;
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
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.privilege.user.UserRoleVo;
import com.jlfex.hermes.service.role.RoleResourceService;
import com.jlfex.hermes.service.userRole.UserRoleService;

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
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleResourceService roleResourceService;

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
				older.setType(Role.Type.SYS_AUTH);
				roleService.save(older);
				result.addMessage("编辑成功");
			} else {
				role.setType(Role.Type.SYS_AUTH);
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
		Dictionary dictionary = dictionaryRepository.findByCodeAndStatus(HermesConstants.DIC_CONSOLE, Dictionary.Status.VALID);
		// 获取后台软件模式
		List<String> consoneSoftModel = roleResourceService.getSoftModelRoleResource(dictionary);
		this.setSoftModelNavigation(navigation.getChildren(), consoneSoftModel);
		
		navigation.setHavingByRole(true);
		if (roleResources != null && roleResources.size() > 0) {
			this.setSomeRolePrivilege(navigation.getChildren(), roleResources);
		}

		return navigation;
	}
	
	/**
	 * 某一软件模式下所有菜单
	 * 
	 * @param children
	 * @param consoneSoftModel
	 */
	private void setSoftModelNavigation(List<Navigation> children, List<String> consoneSoftModel) {
		for (Navigation navigation : children) {
			// 是否含有该软件模式
			boolean flag = false;
			for (String softModel : consoneSoftModel) {
				if (navigation.getCode().equals(softModel)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				navigation.setHavingBySoftModel(false);
			}

			if (navigation.getChildren() != null && navigation.getChildren().size() > 0) {
				setSoftModelNavigation(navigation.getChildren(), consoneSoftModel);
			}
		}
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

	/**
	 * 用户管理-首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userIndex")
	public String userIndex(Model model) {
		return "privilege/user/index";
	}

	/**
	 * 用户管理列表
	 * 
	 * @param userRoleVo
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userMgrData")
	public String userMgrData(UserRoleVo userRoleVo, String page, Model model) {
		try {
			String size = "10";
			Page<User> userList = userRoleService.queryByCondition(userRoleVo, page, size);
			model.addAttribute("userList", userList);
		} catch (Exception e) {
			Logger.error("接口列表查询异常:", e);
		}
		return "privilege/user/data";
	}

	/**
	 * go:添加后台管理用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "goAddUser")
	public String goAddUser() {
		return "privilege/user/add";
	}

	/**
	 * go:添加用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveUser")
	public String addUser(Model model, UserRoleVo userRoleVo) {
		Map<String, String> result = userService.saveConsoleManager(userRoleVo);
		String resultMsg = null;
		if ("00".equals(result.get("code"))) {
			resultMsg = "保存成功";
		} else {
			resultMsg = result.get("msg");
		}
		model.addAttribute("msg", resultMsg);
		return "privilege/user/index";
	}

	/**
	 * go: 修改用户
	 * 
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value = "goModifyUser")
	public String goModify(Model model, UserRoleVo userRoleVo) {
		if (userRoleVo != null && Strings.notEmpty(userRoleVo.getId())) {
			model.addAttribute("user", userService.loadById(userRoleVo.getId().trim()));
			return "privilege/user/modify";
		} else {
			model.addAttribute("msg", "没有选择到有效的数据");
			return "privilege/user/index";
		}
	}

	/**
	 * 后台用户删除
	 * 
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value = "delUser")
	public String delUser(Model model, UserRoleVo userRoleVo) {
		if (userRoleVo != null && Strings.notEmpty(userRoleVo.getId())) {
			try {
				User user = userService.loadById(userRoleVo.getId().trim());
				List<UserRole> userRoleList = userRoleService.findByUserId(user.getId());
				if (userRoleList != null && userRoleList.size() > 0) {
					model.addAttribute("msg", "删除失败：请先删除用户下的角色，再进行删除操作!");
				} else {
					user.setStatus(User.Status.DISABLED);
					User entity = userService.updateUser(user);
					if (entity == null) {
						model.addAttribute("msg", "删除失败,请重新操作");
					}
				}
			} catch (Exception e) {
				Logger.error("后台用户删除异常，userId=%s", userRoleVo.getId(), e);
			}
		} else {
			model.addAttribute("msg", "删除失败");
		}
		return "privilege/user/index";
	}

	/**
	 * go:角色授权
	 * 
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value = "goRoleImpower")
	public String goRoleImpower(Model model, UserRoleVo userRoleVo) {
		model.addAttribute("userId", userRoleVo.getId());
		return "privilege/user/roleIndex";
	}

	/**
	 * go:角色授权
	 * 
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value = "userRoleData")
	public String userRoleData(Model model, String page, UserRoleVo userRoleVo) {
		try {
			String size = "10";
			// 获取角色列表
			Page<Role> roles = userRoleService.queryRoleListByCondition(userRoleVo, page, size);
			if (roles != null && roles.getContent() != null && roles.getContent().size() > 0) {
				for (Role role : roles.getContent()) {
					if (Strings.notEmpty(role.getCreator())) {
						User user = userService.loadById(role.getCreator().trim());
						if (user != null) {
							role.setCreatorName(user.getAccount());
						}
					}
				}
			}
			List<Role> ownedRoles = getOwnedRoleList(userRoleVo.getUserId().trim());
			model.addAttribute("ownedRoles", ownedRoles);
			model.addAttribute("roles", roles);
			model.addAttribute("userId", userRoleVo.getUserId().trim());
		} catch (Exception e) {
			Logger.error("接口列表查询异常:", e);
		}
		return "privilege/user/roleData";
	}

	/**
	 * 获取已有角色列表
	 * 
	 * @param userRoleVo
	 * @return
	 * @throws Exception
	 */
	public List<Role> getOwnedRoleList(String curUserid) throws Exception {
		List<UserRole> ownedUserRoles = userRoleService.findByUserId(curUserid);
		List<Role> ownedRoles = null;
		if (ownedUserRoles != null && ownedUserRoles.size() > 0) {
			ownedRoles = new ArrayList<Role>();
			for (UserRole obj : ownedUserRoles) {
				Role role = obj.getRole();
				if (Strings.notEmpty(role.getCreator())) {
					User user = userService.loadById(role.getCreator().trim());
					if (user != null) {
						role.setCreatorName(user.getAccount());
					}
				}
				role.setBelongUserRoleId(obj.getId());
				ownedRoles.add(role);
			}
		}
		return ownedRoles;
	}

	/**
	 * 用户授权角色 条件：同类型角色的角色只能添加一次
	 * 
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value = "impowerUser")
	public String impowerUser(Model model, UserRoleVo userRoleVo) {
		try {
			User user = userService.loadById(userRoleVo.getUserId().trim());
			Role role = roleService.findById(userRoleVo.getId().trim());
			List<UserRole> userRoleList = userRoleService.findByUserIdAndRoleType(user.getId(), role.getType());
			if (userRoleList != null && userRoleList.size() > 0) {
				model.addAttribute("msg", "授权失败：不能添加多个同类型角色(角色冲突)");
			} else {
				if (role != null) {
					UserRole entity = new UserRole();
					entity.setCreator(App.current().getUser().getId());
					entity.setRole(role);
					entity.setUpdater(App.current().getUser().getId());
					entity.setUser(user);
					userRoleService.save(entity);
					model.addAttribute("msg", "授权成功");
				}
			}
			model.addAttribute("userId", userRoleVo.getUserId().trim());
		} catch (Exception e) {
			Logger.error("用户授权角色异常：", e);
			model.addAttribute("msg", "授权异常");
		}
		return "privilege/user/roleIndex";
	}

	@RequestMapping(value = "delRole")
	public String delRole(Model model, UserRoleVo userRoleVo) {
		try {
			if (Strings.notEmpty(userRoleVo.getId().trim())) {
				userRoleService.deleUserRoleById(userRoleVo.getId().trim());
				model.addAttribute("msg", "删除角色成功");
			} else {
				model.addAttribute("msg", "删除失败,用户角色信息为空");
			}
			model.addAttribute("userId", userRoleVo.getUserId().trim());
		} catch (Exception e) {
			Logger.error("删除角色异常：用户角色Id=%s", userRoleVo.getId(), e);
			model.addAttribute("msg", "删除角色异常");
		}
		return "privilege/user/roleIndex";
	}
}

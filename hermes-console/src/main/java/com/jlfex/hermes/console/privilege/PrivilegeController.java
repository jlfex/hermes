package com.jlfex.hermes.console.privilege;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.service.RoleService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.privilege.user.UserRoleVo;
import com.jlfex.hermes.service.userRole.UserRoleService;

/**
 * 权限管理
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/privilege")
public class PrivilegeController {

	@Autowired
	private  UserService  userService ;
	@Autowired
	private  UserRoleService userRoleService;
	@Autowired
	private  RoleService roleService;
	
	
	/**
	 * 用户管理-首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="userIndex")
	public String  userIndex(Model model){
		return "privilege/user/index";
	}
	/**
	 * 用户管理列表
	 * @param userRoleVo
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="userMgrData")
	public String  userMgrData(UserRoleVo userRoleVo, String page, Model model){
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
	 * @return
	 */
	@RequestMapping(value="goAddUser")
	public String  goAddUser(){
		return "privilege/user/add";
	}
	/**
	 * go:添加用户
	 * @return
	 */
	@RequestMapping(value="saveUser")
	public String  addUser(Model model, UserRoleVo userRoleVo){
		Map<String,String> result = userService.saveConsoleManager(userRoleVo);
		String resultMsg = null;
		if("00".equals(result.get("code"))){
			resultMsg="保存成功";
		}else{
			resultMsg=result.get("msg");
		}
		model.addAttribute("msg", resultMsg);
		return "privilege/user/index";
	}
	/**
	 * go: 修改用户
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value="goModifyUser")
	public String  goModify(Model model, UserRoleVo userRoleVo){
		if(userRoleVo !=null && Strings.notEmpty(userRoleVo.getId()) ){
			 model.addAttribute("user", userService.loadById(userRoleVo.getId().trim()));
			 return "privilege/user/modify";
		}else{
			 model.addAttribute("msg", "没有选择到有效的数据");
			 return "privilege/user/index";
		}
	}
	/**
	 * 后台用户删除
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value="delUser")
	public String  delUser(Model model, UserRoleVo userRoleVo){
		if(userRoleVo !=null && Strings.notEmpty(userRoleVo.getId()) ){
			 try {
				User user = userService.loadById(userRoleVo.getId().trim());
				List<UserRole>  userRoleList = userRoleService.findByUserId(user.getId());
				if(userRoleList!=null && userRoleList.size() > 0){
					 model.addAttribute("msg", "删除失败：请先删除用户下的角色，再进行删除操作!");
				}else{
					user.setStatus(User.Status.DISABLED);
					User entity = userService.updateUser(user);
					if(entity==null){
						 model.addAttribute("msg", "删除失败,请重新操作");
					}
				}
			} catch (Exception e) {
				Logger.error("后台用户删除异常，userId=%s",userRoleVo.getId(),e );
			}
		}else{
			 model.addAttribute("msg", "删除失败");
		}
		return "privilege/user/index";
	}
	
	/**
	 * go:角色授权
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value="goRoleImpower")
	public String  goRoleImpower(Model model, UserRoleVo userRoleVo){
		 model.addAttribute("userId", userRoleVo.getId());
		 return "privilege/user/roleIndex";
	}
	/**
	 * go:角色授权
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value="userRoleData")
	public String  userRoleData(Model model,String page, UserRoleVo userRoleVo){
		try {
			String size = "10";
			//获取角色列表
			Page<Role> roles = userRoleService.queryRoleListByCondition(userRoleVo, page, size);
			if(roles!=null && roles.getContent() !=null && roles.getContent().size() > 0){
				for(Role role : roles.getContent()){
					if(Strings.notEmpty(role.getCreator())){
						User user = userService.loadById(role.getCreator().trim());
						if(user!=null){
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
	 * @param userRoleVo
	 * @return
	 * @throws Exception
	 */
	public List<Role> getOwnedRoleList(String  curUserid) throws Exception {
		List<UserRole>  ownedUserRoles = userRoleService.findByUserId(curUserid);
		List<Role>  ownedRoles = null;
		if(ownedUserRoles!=null && ownedUserRoles.size() > 0){
			ownedRoles = new ArrayList<Role>();
			for(UserRole obj: ownedUserRoles){
				Role role = obj.getRole();
				if(Strings.notEmpty(role.getCreator())){
					User user = userService.loadById(role.getCreator().trim());
					if(user!=null){
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
	 * 用户授权角色
	 * 条件：同类型角色的角色只能添加一次
	 * @param model
	 * @param userRoleVo
	 * @return
	 */
	@RequestMapping(value="impowerUser")
	public String  impowerUser(Model model, UserRoleVo userRoleVo){
		 try {
			 User user = userService.loadById(userRoleVo.getUserId().trim());
			 Role role = roleService.findById(userRoleVo.getId().trim());
			 List<UserRole> userRoleList =  userRoleService.findByUserIdAndRoleType(user.getId(),role.getType());
		     if(userRoleList!=null && userRoleList.size() > 0){
		    	 model.addAttribute("msg", "授权失败：不能添加多个同类型角色(角色冲突)");
		     }else{
		    	 if(role!=null){
					 UserRole entity = new  UserRole();
					 entity.setCreator(App.current().getUser().getId());
					 entity.setRole(role);
					 entity.setUpdater(App.current().getUser().getId());
					 entity.setUser(user);
					 userRoleService.save(entity);
					 model.addAttribute("msg", "授权成功");
				 }
		     }
			 model.addAttribute("userId", userRoleVo.getUserId().trim());
		 }catch(Exception e) {
				Logger.error("用户授权角色异常：", e);
				model.addAttribute("msg", "授权异常");
		 }
		 return "privilege/user/roleIndex";
	}
	
	@RequestMapping(value="delRole")
	public String  delRole(Model model, UserRoleVo userRoleVo){
		 try {
			 if(Strings.notEmpty(userRoleVo.getId().trim())){
				 userRoleService.deleUserRoleById(userRoleVo.getId().trim());
				 model.addAttribute("msg", "删除角色成功");
			 }else{
				 model.addAttribute("msg", "删除失败,用户角色信息为空");
			 }
			 model.addAttribute("userId", userRoleVo.getUserId().trim());
		 }catch(Exception e) {
				Logger.error("删除角色异常：用户角色Id=%s",userRoleVo.getId() , e);
				model.addAttribute("msg", "删除角色异常");
		 }
		 return "privilege/user/roleIndex";
	}
	
	
}

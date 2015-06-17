package com.jlfex.hermes.service.userRole;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.repository.role.RoleRepository;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.privilege.user.UserRoleVo;

/**
 * 用户角色
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
    private  UserRoleRepository   userRoleRepository;
	@Autowired
	private  UserRepository      userRepository;
	@Autowired
	private  RoleRepository   roleRepository;
	@Autowired
	private UserService userService;
	
	/**
	 * 根据用户id 获取所有角色
	 */
	@Override
	public List<UserRole> findByUserId(String userId) throws Exception {
		
		return userRoleRepository.findByUserId(userId);
	}
	@Override
	public List<UserRole> findByUserIdAndRoleType(String userId,String roleType) throws Exception {
		return userRoleRepository.findByUserIdAndRoleType(userId,roleType);
	}
	/**
	 * 用户权限管理
	 */
	@Override
	public Page<User> queryByCondition(final UserRoleVo userRoleVo, String page, String size) throws Exception {
		 Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC,  "createTime"));
		 Page<User> userListPage=  userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(userRoleVo.getCreator())) {
					list.add(cb.equal(root.<String>get("creator"),  userRoleVo.getCreator().trim()));
				}
				if (StringUtils.isNotEmpty(userRoleVo.getUserName())) {
					list.add(cb.like(root.<String>get("account"), "%"+userRoleVo.getUserName().trim()+"%"));
				}
				User currentUser = userService.loadById(App.current().getUser().getId());
				//超级管理员获取所有角色,否则获取自己创建的角色
				if(!HermesConstants.PLAT_MANAGER.equals(currentUser.getAccount())){
					list.add(cb.equal(root.<String>get("creator"),  currentUser.getId()));
				}
				list.add(cb.equal(root.<String>get("type"),    User.Type.NORMAL_ADMIN));
				list.add(cb.equal(root.<String>get("status"),  User.Status.ENABLED));
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		},pageable);
		if(userListPage!=null && userListPage.getContent()!=null && userListPage.getContent().size() > 0){
			List<User>  userList = userListPage.getContent();
			for(User user : userList){
				if(user!=null){
					try{
					 user.setRoles(getRoles(userRoleRepository.findByUserId(user.getId())));
					}catch(Exception e){
						Logger.error("根据userId=%s 获取角色信息异常",user.getId()  ,e);
						continue;
					}
				}
			}
		}
		return userListPage;
	}
	
	/**
	 * 获取权限
	 * @param userRoles
	 * @return
	 */
	public List<Role>  getRoles(List<UserRole> userRoles){
		List<Role> roles = null;
		if(userRoles!=null && userRoles.size() > 0){
			roles = new ArrayList<Role>();
			for(UserRole userRole : userRoles){
				roles.add(userRole.getRole());
			}
		}
		return roles;
	}
	
	/**
	 * 角色列表
	 */
	@Override
	public Page<Role> queryRoleListByCondition(final UserRoleVo userRoleVo, String page, String size) throws Exception {
		 Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC,  "createTime"));
		 return roleRepository.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(userRoleVo.getRoleName())) {
					list.add(cb.like(root.<String>get("name"), "%"+userRoleVo.getRoleName().trim()+"%"));
				}
				User currentUser = userService.loadById(App.current().getUser().getId());
				//超级管理员获取所有角色,否则获取自己创建的角色
				if(!HermesConstants.PLAT_MANAGER.equals(currentUser.getAccount())){
					list.add(cb.equal(root.<String>get("creator"),  currentUser.getId()));
				}
				list.add(cb.equal(root.<String>get("type"),  Role.Type.SYS_AUTH));
				list.add(cb.equal(root.<String>get("status"), Role.Status.ENABLED));
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		},pageable);
	}
	/**
	 * 用户添加 角色
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserRole save(UserRole entity) throws Exception {
		return userRoleRepository.save(entity);
	}
	/**
	 * 删除无效的用户角色关系
	 */
	@Override
	public void deleUserRoleById(String id) throws Exception {
		 userRoleRepository.delete(id);
	}
}

package com.jlfex.hermes.service.pojo.privilege.user;

import java.io.Serializable;
import java.util.List;
import com.jlfex.hermes.model.Role;

/**
 * 用户角色Vo
 * @author Administrator
 *
 */
public class UserRoleVo implements Serializable {

	private static final long serialVersionUID = -2612123382795785802L;
	
	private  String   id ;
	private  String   userName;
	private  String   userPwd;
	private  String   originalPwd;   //原始密码
	private  String   remark;
	private  String   creator;
	private  List<Role>  roles ;
	private  String   roleName;
	private  String   userId;  
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOriginalPwd() {
		return originalPwd;
	}
	public void setOriginalPwd(String originalPwd) {
		this.originalPwd = originalPwd;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

}

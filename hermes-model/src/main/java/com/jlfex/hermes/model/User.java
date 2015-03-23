package com.jlfex.hermes.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 用户模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-08
 * @since 1.0
 */
@Entity
@Table(name = "hm_user")
public class User extends Model {

	private static final long serialVersionUID = -72297788360323888L;

	/** 帐号 */
	@Column(name = "account")
	private String account;

	/** 邮件 */
	@Column(name = "email")
	private String email;

	/** 手机 */
	@Column(name = "cellphone")
	private String cellphone;

	/** 登录密码 */
	@Column(name = "sign_password")
	private String signPassword;

	/** 支付密码 */
	@Column(name = "pay_password")
	private String payPassword;

	/** 状态 */
	@Column(name = "status")
	private String status;
	/** 认证状态 */
	@Column(name = "is_authentic")
	private Boolean isAuthentic;

	/** 类型 */
	
	@Column(name = "type")
	private String type;

	/** 角色列表 */
	@Transient
	private List<Role> roles = new LinkedList<Role>();
	/** 验证码 */
	@Transient
	private String verificationCode;
	/** 真实姓名 **/
	@Transient
	private String realName;

	/**
	 * 读取帐号
	 * 
	 * @return
	 * @see #account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置帐号
	 * 
	 * @param account
	 * @see #account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 读取邮件
	 * 
	 * @return
	 * @see #email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮件
	 * 
	 * @param email
	 * @see #email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 读取手机
	 * 
	 * @return
	 * @see #cellphone
	 */
	public String getCellphone() {
		return cellphone;
	}

	/**
	 * 设置手机
	 * 
	 * @param cellphone
	 * @see #cellphone
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * 读取登录密码
	 * 
	 * @return
	 * @see #signPassword
	 */
	public String getSignPassword() {
		return signPassword;
	}

	/**
	 * 设置登录密码
	 * 
	 * @param signPassword
	 * @see #signPassword
	 */
	public void setSignPassword(String signPassword) {
		this.signPassword = signPassword;
	}

	/**
	 * 读取支付密码
	 * 
	 * @return
	 * @see #payPassword
	 */
	public String getPayPassword() {
		return payPassword;
	}

	/**
	 * 设置支付密码
	 * 
	 * @param payPassword
	 * @see #payPassword
	 */
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	/**
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 读取角色列表
	 * 
	 * @return
	 * @see #roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 读取类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * 状态
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-08
	 * @since 1.0
	 */
	public static final class Status {

		@Element("正常")
		public static final String ENABLED = "00";

		@Element("未激活")
		public static final String INACTIVATE = "01";

		@Element("冻结")
		public static final String FROZEN = "88";

		@Element("注销")
		public static final String DISABLED = "99";


	}

	/**
	 * 类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-08
	 * @since 1.0
	 */
	public static final class Type {

		@Element("客户")
		public static final String CLIENT = "00";

		@Element("管理员")
		public static final String ADMIN = "10";

		@Element("根")
		public static final String ROOT = "99";

		@Element("债权人")
		public static final String CREDIT = "20";
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Boolean getIsAuthentic() {
		return isAuthentic;
	}

	public void setIsAuthentic(Boolean isAuthentic) {
		this.isAuthentic = isAuthentic;
	}

	/**
	 * 去除空格
	 */
	public void propertyTrim() {
		if (!Strings.empty(account)) {
			account = account.trim();
		}
		if (!Strings.empty(email)) {
			email = email.trim();
		}
		if (!Strings.empty(cellphone)) {
			cellphone = cellphone.trim();
		}
		if (!Strings.empty(signPassword)) {
			signPassword = signPassword.trim();
		}
		if (!Strings.empty(payPassword)) {
			payPassword = payPassword.trim();
		}
		if (!Strings.empty(verificationCode)) {
			verificationCode = verificationCode.trim();
		}
		if (!Strings.empty(realName)) {
			realName = realName.trim();
		}
	}
}

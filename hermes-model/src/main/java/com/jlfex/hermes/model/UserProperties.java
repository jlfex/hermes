package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户属性模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-08
 * @since 1.0
 */
@Entity
@Table(name = "hm_user_properties")
public class UserProperties extends Model {

	private static final long serialVersionUID = 1752709914205688349L;

	/** 真实姓名 */
	@Column(name = "real_name")
	private String realName;
	
	/** 性别 */
	@Column(name = "gender")
	private String gender;
	
	/** 年龄 */
	@Column(name = "age")
	private Integer age;
	
	/** 婚姻状况 */
	@Column(name = "married")
	private String married;
	
	/** 子女数量 */
	@Column(name = "children")
	private Integer children;
	
	/** 证件号码 */
	@Column(name = "id_number")
	private String idNumber;
	
	/** 证件类型 */
	@Column(name = "id_type")
	private String idType;
	
	/** 是否借款人 */
	@Column(name = "is_mortgagor")
	private String isMortgagor;
	
	/** 实名认证 */
	@Column(name = "auth_name")
	private String authName;
	
	/** 手机认证 */
	@Column(name = "auth_cellphone")
	private String authCellphone;
	
	/** 邮件认证 */
	@Column(name = "auth_email")
	private String authEmail;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/**
	 * 读取真实姓名
	 * 
	 * @return
	 * @see #realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置真实姓名
	 * 
	 * @param realName
	 * @see #realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 读取性别
	 * 
	 * @return
	 * @see #gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 * 
	 * @param gender
	 * @see #gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * 读取年龄
	 * 
	 * @return
	 * @see #age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * 设置
	 * 
	 * @param age
	 * @see #age
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #married
	 */
	public String getMarried() {
		return married;
	}

	/**
	 * 设置年龄
	 * 
	 * @param married
	 * @see #married
	 */
	public void setMarried(String married) {
		this.married = married;
	}

	/**
	 * 读取子女数量
	 * 
	 * @return
	 * @see #children
	 */
	public Integer getChildren() {
		return children;
	}

	/**
	 * 设置子女数量
	 * 
	 * @param children
	 * @see #children
	 */
	public void setChildren(Integer children) {
		this.children = children;
	}

	/**
	 * 读取证件号码
	 * 
	 * @return
	 * @see #idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * 设置证件号码
	 * 
	 * @param idNumber
	 * @see #idNumber
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * 读取证件类型
	 * 
	 * @return
	 * @see #idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * 设置证件类型
	 * 
	 * @param idType
	 * @see #idType
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * 读取是否借款人
	 * 
	 * @return
	 * @see #isMortgagor
	 */
	public String getIsMortgagor() {
		return isMortgagor;
	}

	/**
	 * 设置是否借款人
	 * 
	 * @param isMortgagor
	 * @see #isMortgagor
	 */
	public void setIsMortgagor(String isMortgagor) {
		this.isMortgagor = isMortgagor;
	}
	
	/**
	 * 读取实名认证
	 * 
	 * @return
	 * @see #authName
	 */
	public String getAuthName() {
		return authName;
	}

	/**
	 * 设置实名认证
	 * 
	 * @param authName
	 * @see #authName
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * 读取手机认证
	 * 
	 * @return
	 * @see #authCellphone
	 */
	public String getAuthCellphone() {
		return authCellphone;
	}

	/**
	 * 设置手机认证
	 * 
	 * @param authCellphone
	 * @see #authCellphone
	 */
	public void setAuthCellphone(String authCellphone) {
		this.authCellphone = authCellphone;
	}

	/**
	 * 读取邮件认证
	 * 
	 * @return
	 * @see #authEmail
	 */
	public String getAuthEmail() {
		return authEmail;
	}

	/**
	 * 设置邮件认证
	 * 
	 * @param authEmail
	 * @see #authEmail
	 */
	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}
	
	/**
	 * 读取用户
	 * 
	 * @return
	 * @see #user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param user
	 * @see #user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 读取性别名称
	 * 
	 * @return
	 */
	public String getGenderName() {
		return Dicts.name(gender, gender, Gender.class);
	}
	
	/**
	 * 读取婚姻状况名称
	 * 
	 * @return
	 */
	public String getMarriedName() {
		return Dicts.name(married, married, Married.class);
	}
	
	/**
	 * 读取证件类型名称
	 * 
	 * @return
	 */
	public String getIdTypeName() {
		return Dicts.name(idType, idType, IdType.class);
	}
	
	/**
	 * 读取实名认证名称
	 * 
	 * @return
	 */
	public String getAuthNameName() {
		return Dicts.name(authName, authName, Auth.class);
	}
	
	/**
	 * 读取手机认证名称
	 * 
	 * @return
	 */
	public String getAuthCellphoneName() {
		return Dicts.name(authCellphone, authCellphone, Auth.class);
	}
	
	/**
	 * 读取邮件认证名称
	 * 
	 * @return
	 */
	public String getAuthEmailName() {
		return Dicts.name(authEmail, authEmail, Auth.class);
	}
	
	/**
	 * 性别
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-08
	 * @since 1.0
	 */
	public static final class Gender {
		
		@Element("男")
		public static final String MALE		= "00";
		
		@Element("女")
		public static final String FEMALE	= "01";
	}
	
	/**
	 * 婚姻状况
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-08
	 * @since 1.0
	 */
	public static final class Married {
		
		@Element("未婚")
		public static final String UNMARRIED	= "00";
		
		@Element("已婚")
		public static final String MARRIED		= "01";
		
		@Element("离异")
		public static final String DIVORCED		= "02";
		
		@Element("丧偶")
		public static final String WIDOWED		= "03";
	}
	
	/**
	 * 证件类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-08
	 * @since 1.0
	 */
	public static final class IdType {
		
		@Element("身份证")
		public static final String IDENTITY	= "00";
		
		@Element("护照")
		public static final String PASSPORT	= "01";
	}
	
	/**
	 * 认证
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-08
	 * @since 1.0
	 */
	public static final class Auth {
		
		@Element("尚未认证")
		public static final String WAIT		= "00";
		
		@Element("通过认证")
		public static final String PASS		= "10";
		
		@Element("认证失败")
		public static final String FAILURE	= "20";
	}

	/**
	 * 是否为借款人
	 * 
	 * 
	 * @author Aether
	 * @version 1.0, 2014-2-13
	 * @since 1.0
	 */
	public static final class Mortgagor {

		@Element("全部")
		public static final String ALL = "00";
		@Element("借款人")
		public static final String LOAN = "01";
	}
}

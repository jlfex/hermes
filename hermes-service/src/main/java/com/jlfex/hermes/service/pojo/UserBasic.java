package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

public class UserBasic implements Serializable {

	private static final long serialVersionUID = 2738596796196493684L;

	/** 编号 */
	private String id;

	/** 邮箱 */
	private String email;

	/** 真实姓名 */
	private String realName;

	/** 性别 */
	private String gender;

	/** 年龄 */
	private Integer age;

	/** 婚姻状况 */
	private String married;

	/** 子女数量 */
	private Integer children;

	/** 证件号码 */
	private String idNumber;

	/** 证件类型 */
	private String idType;

	/** 手机号码 */
	private String cellphone;

	/** 省份 */
	private String province;

	/** 城市 */
	private String city;

	/** 区县 */
	private String county;

	/** 地址 */
	private String address;

	/** 毕业学校 */
	private String school;

	/** 毕业年份 */
	private String year;

	/** 最高学历 */
	private String degree;

	/** 昵称 */
	private String account;

	/** 用户状态 */
	private String status;
	/** 实名认证 */
	private String authName;

	/** 手机认证 */
	private String authCellphone;

	/** 邮箱认证 */
	private String authEmail;
	/**
	 * 读取编号
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * 读取手机号码
	 * 
	 * @return
	 * @see #cellphone
	 */
	public String getCellphone() {
		return cellphone;
	}

	/**
	 * 设置手机号码
	 * 
	 * @param cellphone
	 * @see #cellphone
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * 读取省份
	 * 
	 * @return
	 * @see #province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 设置省份
	 * 
	 * @param province
	 * @see #province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 读取城市
	 * 
	 * @return
	 * @see #city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置城市
	 * 
	 * @param city
	 * @see #city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 读取区县
	 * 
	 * @return
	 * @see #county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * 设置区县
	 * 
	 * @param county
	 * @see #county
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * 读取地址
	 * 
	 * @return
	 * @see #address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 * @see #address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 读取毕业学校
	 * 
	 * @return
	 * @see #school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * 设置毕业学校
	 * 
	 * @param school
	 * @see #school
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * 读取毕业年份
	 * 
	 * @return
	 * @see #year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * 设置毕业年份
	 * 
	 * @param year
	 * @see #year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 读取学历
	 * 
	 * @return
	 * @see #degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * 设置学历
	 * 
	 * @param degree
	 * @see #degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * 读取昵称
	 * 
	 * @return
	 * @see #account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置昵称
	 * 
	 * @param account
	 * @see #account
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * 读取
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #authName
	 */
	public String getAuthName() {
		return authName;
	}

	/**
	 * 设置
	 * 
	 * @param authName
	 * @see #authName
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #authCellphone
	 */
	public String getAuthCellphone() {
		return authCellphone;
	}

	/**
	 * 设置
	 * 
	 * @param authCellphone
	 * @see #authCellphone
	 */
	public void setAuthCellphone(String authCellphone) {
		this.authCellphone = authCellphone;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #authEmail
	 */
	public String getAuthEmail() {
		return authEmail;
	}

	/**
	 * 设置
	 * 
	 * @param authEmail
	 * @see #authEmail
	 */
	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
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
		public static final String MALE = "00";

		@Element("女")
		public static final String FEMALE = "01";
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
		public static final String UNMARRIED = "00";

		@Element("已婚")
		public static final String MARRIED = "01";

		@Element("离异")
		public static final String DIVORCED = "02";

		@Element("丧偶")
		public static final String WIDOWED = "03";
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
		public static final String IDENTITY = "00";

		@Element("护照")
		public static final String PASSPORT = "01";
	}

}

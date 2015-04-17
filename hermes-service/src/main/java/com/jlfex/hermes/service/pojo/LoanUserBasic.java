package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

/**
 * 后台页面借款相关的用户基本信息
 */
public class LoanUserBasic implements Serializable {

	
	private static final long serialVersionUID = 7129694625043492677L;

	/** 昵称 */
	private String account;

	/** 真实姓名 */
	private String realName;

	/** 证件号码 */
	private String idNumber;

	/** 证件类型 */
	private String idType;

	/** 证件类型 */
	private String idTypeName;

	/** 手机号码 */
	private String cellphone;

	/** 邮箱 */
	private String email;

	/** 性别 */
	private String gender;

	/** 性别 */
	private String genderName;

	/** 年龄 */
	private Integer age;

	/** 婚姻状况 */
	private String married;

	/** 婚姻状况 */
	private String marriedName;

	/** 子女数量 */
	private Integer children;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zip;

	/** 固定电话 */
	private String phone;

	/** 最高学历 */
	private String degree;
	
	

	/** 最高学历名字 */
	private String degreeName;
	/** 毕业学校 */
	private String school;

	/** 学历编号 */
	private String degreeNumber;

	/** 毕业年份 */
	private String year;

	/** 实名认证 */
	private String authName;

	/** 手机认证 */
	private String authCellphone;

	/** 邮件认证 */
	private String authEmail;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDegreeNumber() {
		return degreeNumber;
	}

	public void setDegreeNumber(String degreeNumber) {
		this.degreeNumber = degreeNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthCellphone() {
		return authCellphone;
	}

	public void setAuthCellphone(String authCellphone) {
		this.authCellphone = authCellphone;
	}

	public String getAuthEmail() {
		return authEmail;
	}

	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getMarriedName() {
		return marriedName;
	}

	public void setMarriedName(String marriedName) {
		this.marriedName = marriedName;
	}

	public String getIdTypeName() {
		return idTypeName;
	}

	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}
	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
}

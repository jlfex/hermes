package com.jlfex.hermes.service.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 前台页面借款相关的用户信息
 */
public class LoanUserInfo implements Serializable {

	
	private static final long serialVersionUID = -1716009642124198032L;

	/** 余额 */
	private BigDecimal balance;

	/** 性别 */
	private String gender;

	/** 性别 */
	private String genderName;

	/** 婚姻状况 */
	private String married;

	/** 婚姻状况 */
	private String marriedName;

	/** 年龄 */
	private Integer age;

	/** 年收入 */
	private BigDecimal annualSalary;

	/** 职位 */
	private String position;

	/** 公司规模 */
	private String scale;

	/** 公司规模 */
	private String scaleName;

	/** 公司性质 */
	private String properties;
	
	/** 公司性质 */
	private String propertiesName;

	/** 实名认证 */
	private String authName;

	/** 手机认证 */
	private String authCellphone;

	/** 邮件认证 */
	private String authEmail;

	/** 毕业学校 */
	private String school;

	/** 毕业年份 */
	private String year;

	/** 最高学历 */
	private String degree;

	/** 最高学历名字 */
	private String degreeName;
	
	
	/** 是否有房 */
	private String hasHouse;

	/** 房产是否有按揭 */
	private String hasHouseMortgage;

	/** 是否有车 */
	private String hasCar;
	
	/** 是否有车 */
	private String lgAvatar;

	public String getLgAvatar() {
		return lgAvatar;
	}

	public void setLgAvatar(String lgAvatar) {
		this.lgAvatar = lgAvatar;
	}

	/** 车产是否有按揭 */
	private String hasCarMortgage;

	public BigDecimal getBalance() {
		return balance;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
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

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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

	public String getScaleName() {
		return scaleName;
	}

	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}

	

	public String getHasCar() {
		return hasCar;
	}

	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}

	
	public String getHasHouse() {
		return hasHouse;
	}

	public void setHasHouse(String hasHouse) {
		this.hasHouse = hasHouse;
	}

	public String getHasHouseMortgage() {
		return hasHouseMortgage;
	}

	public void setHasHouseMortgage(String hasHouseMortgage) {
		this.hasHouseMortgage = hasHouseMortgage;
	}

	public String getHasCarMortgage() {
		return hasCarMortgage;
	}

	public void setHasCarMortgage(String hasCarMortgage) {
		this.hasCarMortgage = hasCarMortgage;
	}
	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}
	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

}

package com.jlfex.hermes.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户工作信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_user_job")
public class UserJob extends Model {
	
	private static final long serialVersionUID = -2254091184949825699L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/** 公司名称 */
	@Column(name = "name")
	private String name;
	
	/** 公司性质 */
	@Column(name = "properties")
	private String properties;
	
	/** 公司规模 */
	@Column(name = "scale")
	private String scale;
	
	/** 公司注册资金 */
	@Column(name = "registered_capital")
	private BigDecimal registeredCapital;
	
	/** 公司营业执照 */
	@Column(name = "license")
	private String license;
	
	/** 公司地址 */
	@Column(name = "address")
	private String address;
	
	/** 公司电话 */
	@Column(name = "phone")
	private String phone;
	
	/** 职位 */
	@Column(name = "position")
	private String position;
	
	/** 年收入 */
	@Column(name = "annual_salary")
	private BigDecimal annualSalary;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	
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
	 * 读取公司名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置公司名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 读取公司性质
	 * 
	 * @return
	 * @see #properties
	 */
	public String getProperties() {
		return properties;
	}
	
	/**
	 * 设置公司性质
	 * 
	 * @param properties
	 * @see #properties
	 */
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	/**
	 * 读取公司规模
	 * 
	 * @return
	 * @see #scale
	 */
	public String getScale() {
		return scale;
	}
	
	/**
	 * 设置公司规模
	 * 
	 * @param scale
	 * @see #scale
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	/**
	 * 读取公司注册资金
	 * 
	 * @return
	 * @see #registeredCapital
	 */
	public BigDecimal getRegisteredCapital() {
		return registeredCapital;
	}
	
	/**
	 * 设置公司注册资金
	 * 
	 * @param registeredCapital
	 * @see #registeredCapital
	 */
	public void setRegisteredCapital(BigDecimal registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	
	/**
	 * 读取公司营业执照
	 * 
	 * @return
	 * @see #license
	 */
	public String getLicense() {
		return license;
	}
	
	/**
	 * 设置公司营业执照
	 * 
	 * @param license
	 * @see #license
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	
	/**
	 * 读取公司地址
	 * 
	 * @return
	 * @see #address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 设置公司地址
	 * 
	 * @param address
	 * @see #address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 读取公司电话
	 * 
	 * @return
	 * @see #phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 设置公司电话
	 * 
	 * @param phone
	 * @see #phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * 读取职位
	 * 
	 * @return
	 * @see #position
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * 设置职位
	 * 
	 * @param position
	 * @see #position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * 读取年收入
	 * 
	 * @return
	 * @see #annualSalary
	 */
	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}
	
	/**
	 * 设置年收入
	 * 
	 * @param annualSalary
	 * @see #annualSalary
	 */
	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
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
	 * 
	 * @author Aether
	 * @date 2013-11-12 下午2:56:35
	 * @return description:读取企业性质名称
	 */
	public String getPropertiesName() {
		return Dicts.name(properties, properties, Enterprise.class);
	}

	/**
	 * 
	 * @author Aether
	 * @date 2013-11-12 下午2:57:38
	 * @return description:读取公司规模名称
	 */
	public String getScaleName() {
		return Dicts.name(scale, scale, Scale.class);
	}

	/**
	 * 
	 * @author Aether
	 * @date 2013-11-12 下午2:58:17
	 * @return description:读取类型名称
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * 
	 * @author Aether
	 * @date 2013-11-12 下午2:59:10
	 * @return description:读取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 企业性质
	 * 
	 * @author Aether
	 * @date: 2013-11-12 上午11:24:04
	 */
	public static final class Enterprise{
		@Element("央企")
		public static final String CENTRAL="00";
		
		@Element("国企")
		public static final String STATEOWNED="01";
		
		@Element("私营")
		public static final String PRIVATE="02";
		
		@Element("合资")
		public static final String JOINVENTURE="03";
		
		@Element("外资")
		public static final String FOREIGN="04";
	}

	/**
	 * 公司规模
	 * 
	 * @author Aether
	 * @date: 2013-11-12 上午11:29:08
	 */
	public static final class Scale {
		@Element("0-50人")
		public static final String ZERO_FIFTY = "00";

		@Element("50-100人")
		public static final String FIFTY_HUNDRED = "01";
	}

	/**
	 * 人员类型
	 * 
	 * @author Aether
	 * @date: 2013-11-12 上午11:32:52
	 */
	public static final class Type {
		@Element("企业主")
		public static final String ENTREPRENEUR = "00";

		@Element("雇员")
		public static final String EMPLOYEE = "01";
	}

	/**
	 * 工作状态
	 * 
	 * @author Aether
	 * @date: 2013-11-12 上午11:35:40
	 */
	public static final class Status {
		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}
}

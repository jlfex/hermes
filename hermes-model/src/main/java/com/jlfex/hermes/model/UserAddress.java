package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户联系地址信息模型
 */
@Entity
@Table(name = "hm_user_address")
public class UserAddress extends Model {
	
	private static final long serialVersionUID = -1809398549061825277L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	/** 省份 */
	@Column(name = "province")
	private String province;
	
	/** 城市 */
	@Column(name = "city")
	private String city;
	
	/** 区县 */
	@Column(name = "county")
	private String county;
	
	/** 地址 */
	@Column(name = "address")
	private String address;
	
	/** 邮编 */
	@Column(name = "zip")
	private String zip;
	
	/** 固定电话 */
	@Column(name = "phone")
	private String phone;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	
	/**
	 * 读取用户
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * 设置用户
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * 读取邮编
	 * 
	 * @return
	 * @see #zip
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * 设置邮编
	 * 
	 * @param zip
	 * @see #zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
	 * 读取固定电话
	 * 
	 * @return
	 * @see #phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 设置固定电话
	 * 
	 * @param phone
	 * @see #phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return description:获取类型名称
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * @return description:获取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 地址联系类型
	 */
	public static final class Type {
		@Element("普通")
		public static final String COMMON = "00";

		@Element("账单")
		public static final String BILL = "01";
	}

	/**
	 * 状态
	 */
	public static final class Status {
		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}

}

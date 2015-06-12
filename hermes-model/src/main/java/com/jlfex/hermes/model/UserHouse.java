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
 * 用户房产信息模型
 */
@Entity
@Table(name = "hm_user_house")
public class UserHouse extends Model {
	
	private static final long serialVersionUID = -8558929451795995597L;

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
	
	/** 产证编号 */
	@Column(name = "certificate")
	private String certificate;
	
	/** 产证面积 */
	@Column(name = "area")
	private BigDecimal area;
	
	/** 建成年份 */
	@Column(name = "year")
	private String year;
	
	/** 按揭 */
	@Column(name = "mortgage")
	private String mortgage;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 临时存储地址信息 */
	private transient String addressDetail;
	
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
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
	 * 读取产证编号
	 * 
	 * @return
	 * @see #certificate
	 */
	public String getCertificate() {
		return certificate;
	}
	
	/**
	 * 设置产证编号
	 * 
	 * @param certificate
	 * @see #certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	/**
	 * 读取产证面积
	 * 
	 * @return
	 * @see #area
	 */
	public BigDecimal getArea() {
		return area;
	}
	
	/**
	 * 设置产证面积
	 * 
	 * @param area
	 * @see #area
	 */
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	
	/**
	 * 读取建成年份
	 * 
	 * @return
	 * @see #year
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * 设置建成年份
	 * 
	 * @param year
	 * @see #year
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * 读取按揭
	 * 
	 * @return
	 * @see #mortgage
	 */
	public String getMortgage() {
		return mortgage;
	}
	
	/**
	 * 设置按揭
	 * 
	 * @param mortgage
	 * @see #mortgage
	 */
	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
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
	 * @return description:读取按揭的名称
	 */
	public String getMortageName() {
		return Dicts.name(mortgage, mortgage, Mortgage.class);
	}

	/**
	 * @return description:取得状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 房产是否按揭
	 */
	public static final class Mortgage {
		@Element("有按揭")
		public static final String NO = "00";

		@Element("无按揭")
		public static final String YES = "01";
	}

	/**
	 * 房产状态
	 */
	public static final class Status {
		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}

}

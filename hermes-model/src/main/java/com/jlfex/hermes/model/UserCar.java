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
 * 用户车辆信息模型
 */
@Entity
@Table(name = "hm_user_car")
public class UserCar extends Model {
	
	private static final long serialVersionUID = -9092035686242361659L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	/** 品牌 */
	@Column(name = "brand")
	private String brand;
	
	/** 购入年份 */
	@Column(name = "purchase_year")
	private String purchaseYear;
	
	/** 购入价格 */
	@Column(name = "purchase_amount")
	private BigDecimal purchaseAmount;
	
	/** 牌照 */
	@Column(name = "licence_plate")
	private String licencePlate;
	
	/** 按揭 */
	@Column(name = "mortgage")
	private String mortgage;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	
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
	 * 读取品牌
	 * 
	 * @return
	 * @see #brand
	 */
	public String getBrand() {
		return brand;
	}
	
	/**
	 * 设置品牌
	 * 
	 * @param brand
	 * @see #brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/**
	 * 读取购入年份
	 * 
	 * @return
	 * @see #purchaseYear
	 */
	public String getPurchaseYear() {
		return purchaseYear;
	}
	
	/**
	 * 设置购入年份
	 * 
	 * @param purchaseYear
	 * @see #purchaseYear
	 */
	public void setPurchaseYear(String purchaseYear) {
		this.purchaseYear = purchaseYear;
	}
	
	/**
	 * 读取购入价格
	 * 
	 * @return
	 * @see #purchaseAmount
	 */
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	
	/**
	 * 设置购入价格
	 * 
	 * @param purchaseAmount
	 * @see #purchaseAmount
	 */
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	
	/**
	 * 读取牌照
	 * 
	 * @return
	 * @see #licencePlate
	 */
	public String getLicencePlate() {
		return licencePlate;
	}
	
	/**
	 * 设置牌照
	 * 
	 * @param licencePlate
	 * @see #licencePlate
	 */
	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
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
	 * @return description:获取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * @return description:获取是否按揭名称
	 */
	public String getMortgageName() {
		return Dicts.name(mortgage, mortgage, Mortgage.class);
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

	/**
	 * 汽车是否按揭
	 */
	public static final class Mortgage {
		@Element("有按揭")
		public static final String NO = "00";

		@Element("无按揭")
		public static final String YES = "01";
	}
}

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
 * 产品信息模型
 */
@Entity
@Table(name = "hm_product")
public class Product extends Model {

	private static final long serialVersionUID = 1059913779334140588L;

	/** 还款方式 */
	@ManyToOne
	@JoinColumn(name = "repay")
	private Repay repay;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 金额 */
	@Column(name = "amount")
	private String amount;

	/** 期限 */
	@Column(name = "period")
	private String period;

	/** 利率 */
	@Column(name = "rate")
	private String rate;

	/** 招标期限 */
	@Column(name = "deadline")
	private Integer deadline;

	/** 图片 */
	@Column(name = "logo")
	private String logo;

	/** 描述 */
	@Column(name = "description")
	private String description;

	/** 视图 */
	@Column(name = "view")
	private String view;

	/** 借款管理费 */
	@Column(name = "manage_fee")
	private BigDecimal manageFee;

	/** 借款管理费类型 */
	@Column(name = "manage_fee_type")
	private String manageFeeType;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/** 期限类型 */
	@Column(name = "periodType")
	private String periodType;

	/** 产品用途 */
	@ManyToOne
	@JoinColumn(name = "purpose")
	private Dictionary purpose;

	/** 担保方式 */
	@ManyToOne
	@JoinColumn(name = "guarantee")
	private Dictionary guarantee;

	/** 起投金额 */
	@Column(name = "starting_amt")
	private BigDecimal startingAmt;

	/**
	 * 读取还款方式
	 * 
	 * @return
	 * @see #repay
	 */
	public Repay getRepay() {
		return repay;
	}

	/**
	 * 设置还款方式
	 * 
	 * @param repay
	 * @see #repay
	 */
	public void setRepay(Repay repay) {
		this.repay = repay;
	}

	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取金额
	 * 
	 * @return
	 * @see #amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 设置金额
	 * 
	 * @param amount
	 * @see #amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 读取期限
	 * 
	 * @return
	 * @see #period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * 设置期限
	 * 
	 * @param period
	 * @see #period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * 读取利率
	 * 
	 * @return
	 * @see #rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * 设置利率
	 * 
	 * @param rate
	 * @see #rate
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * 读取招标期限
	 * 
	 * @return
	 * @see #deadline
	 */
	public Integer getDeadline() {
		return deadline;
	}

	/**
	 * 设置招标期限
	 * 
	 * @param deadline
	 * @see #deadline
	 */
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	/**
	 * 读取图片
	 * 
	 * @return
	 * @see #logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置图片
	 * 
	 * @param logo
	 * @see #logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 读取描述
	 * 
	 * @return
	 * @see #description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 * @see #description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 读取视图
	 * 
	 * @return
	 * @see #view
	 */
	public String getView() {
		return view;
	}

	/**
	 * 设置视图
	 * 
	 * @param view
	 * @see #view
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * 读取借款管理费
	 * 
	 * @return
	 * @see #manageFee
	 */
	public BigDecimal getManageFee() {
		return manageFee;
	}

	/**
	 * 设置借款管理费
	 * 
	 * @param manageFee
	 * @see #manageFee
	 */
	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	/**
	 * 读取借款管理费类型
	 * 
	 * @return
	 * @see #manageFeeType
	 */
	public String getManageFeeType() {
		return manageFeeType;
	}

	/**
	 * 设置借款管理费类型
	 * 
	 * @param manageFeeType
	 * @see #manageFeeType
	 */
	public void setManageFeeType(String manageFeeType) {
		this.manageFeeType = manageFeeType;
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

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public Dictionary getPurpose() {
		return purpose;
	}

	public void setPurpose(Dictionary purpose) {
		this.purpose = purpose;
	}

	public Dictionary getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Dictionary guarantee) {
		this.guarantee = guarantee;
	}

	public BigDecimal getStartingAmt() {
		return startingAmt;
	}

	public void setStartingAmt(BigDecimal startingAmt) {
		this.startingAmt = startingAmt;
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
	 * 状态
	 */
	public static final class Status {

		@Element("有效")
		public static final String VALID = "00";
		
		@Element("债权标虚拟产品状态")
		public static final String  VIRTUAL_CREDIT_PROCD= "01";
		
		@Element("易联标虚拟产品状态")
		public static final String  YLTX_CREDIT_PROCD= "02";

		@Element("无效")
		public static final String INVALID = "99";
	}
}

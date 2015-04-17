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
 * 费率信息模型
 */
@Entity
@Table(name = "hm_rate")
public class Rate extends Model {
	
	private static final long serialVersionUID = 2272527733485636987L;

	/** 产品 */
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	/** 费率 */
	@Column(name = "rate")
	private BigDecimal rate;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	
	/**
	 * 读取产品
	 * 
	 * @return
	 * @see #product
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * 设置产品
	 * 
	 * @param product
	 * @see #product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * 读取费率
	 * 
	 * @return
	 * @see #rate
	 */
	public BigDecimal getRate() {
		return rate;
	}
	
	/**
	 * 设置费率
	 * 
	 * @param rate
	 * @see #rate
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
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
		return Dicts.name(type, type, RateType.class);
	}

	/**
	 * 费率类型
	 */
	public static final class RateType {
		@Element("充值费率")
		public static final String RECHARGE = "00";

		@Element("提现费率")
		public static final String WITHDRAW = "01";

		@Element("月缴管理费")
		public static final String MONTH = "02";
		
		@Element("借款手续费费率")
		public static final String LOAN	= "03";
		
		@Element("风险金费率")
		public static final String RISK	= "04";
	}
}

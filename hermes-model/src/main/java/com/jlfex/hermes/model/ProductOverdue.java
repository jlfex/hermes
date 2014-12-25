package com.jlfex.hermes.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 产品逾期费率信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Entity
@Table(name = "hm_product_overdue")
public class ProductOverdue extends Model {

	private static final long serialVersionUID = -6869398854872692628L;

	/** 产品 */
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	/** 等级 */
	@Column(name = "rank")
	private Integer rank;
	
	/** 罚息 */
	@Column(name = "interest")
	private BigDecimal interest;
	
	/** 违约金 */
	@Column(name = "penalty")
	private BigDecimal penalty;

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
	 * 读取等级
	 * 
	 * @return
	 * @see #rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * 设置等级
	 * 
	 * @param rank
	 * @see #rank
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * 读取罚息
	 * 
	 * @return
	 * @see #interest
	 */
	public BigDecimal getInterest() {
		return interest;
	}

	/**
	 * 设置罚息
	 * 
	 * @param interest
	 * @see #interest
	 */
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	/**
	 * 读取违约金
	 * 
	 * @return
	 * @see #penalty
	 */
	public BigDecimal getPenalty() {
		return penalty;
	}

	/**
	 * 设置违约金
	 * 
	 * @param penalty
	 * @see #penalty
	 */
	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
}

package com.jlfex.hermes.service.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Repay;

/**
 * 
 * 产品信息
 * 
 * @author Ray
 * @version 1.0, 2014-1-4
 * @since 1.0
 */
public class ProductInfo implements Serializable{

	/**  */
	private static final long serialVersionUID = 2963146787556121409L;
	
	/** 编号 */
	private String id;
	
	private String name;
	
	private String minAmount;
	
	private String maxAmount;
	
	private String amount;
	
	private String minPeriod;
	
	private String maxPeriod;
	
	private String period;
	
	private String minRate;
	
	private String maxRate;
	
	private String rate;
	
	private List<Repay> repayMethod;
	
	private List<Dictionary> loanUse;
	
	private String purpose;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getMinPeriod() {
		return minPeriod;
	}

	public void setMinPeriod(String minPeriod) {
		this.minPeriod = minPeriod;
	}

	public String getMaxPeriod() {
		return maxPeriod;
	}

	public void setMaxPeriod(String maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	public String getMinRate() {
		return minRate;
	}

	public void setMinRate(String minRate) {
		this.minRate = minRate;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public List<Repay> getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(List<Repay> repayMethod) {
		this.repayMethod = repayMethod;
	}

	public List<Dictionary> getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(List<Dictionary> loanUse) {
		this.loanUse = loanUse;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	
}

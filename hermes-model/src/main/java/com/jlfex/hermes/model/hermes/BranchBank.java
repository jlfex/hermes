package com.jlfex.hermes.model.hermes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.Model;
/**
 * 支行信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_branch_bank")
public class BranchBank extends Model{
	
	private static final long serialVersionUID = 5689593171313318346L;
	/** 开户行所在城市 */
	@Column(name = "city_name")
	private String cityName;
	
	/** 开户行 */
	@Column(name = "branch_bank_name")
	private String branchBankName;

	/** 银行名称 */
	@Column(name = "bank_name")
	private String bankName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}

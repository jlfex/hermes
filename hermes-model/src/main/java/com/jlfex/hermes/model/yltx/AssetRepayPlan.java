package com.jlfex.hermes.model.yltx;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.model.Model;

@Entity
@Table(name = "hm_asset_repay_plan")
public class AssetRepayPlan extends Model{

	/**
	 * 资产 还款计划 模型
	 */
	private static final long serialVersionUID = -8325901215015180673L; 

	
	@ManyToOne
	@JoinColumn(name = "asset_id")         
	private  Asset   asset;					        //所属债权
	@Column(name =   "uniq_id")
	private  String  uniqId;				        //原始债权编号
	private  int  period;					        //期数
	@Column(name =   "repayment_date")
	private  Date  repaymentDate;				    //应还日期
	@Column(name =   "repayment_principal")
	private  BigDecimal  repaymentPrincipal;		//应还本金
	@Column(name =   "repayment_interest")
	private  BigDecimal  repaymentInterest;			//应还利息
	@Column(name =   "repayment_money")
	private  BigDecimal  repaymentMoney;			//应还金额
	@Column(name =   "residue_principal")
	private  BigDecimal  residuePrincipal;			//剩余本金
	
	
	
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public Date getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public BigDecimal getRepaymentPrincipal() {
		return repaymentPrincipal;
	}
	public void setRepaymentPrincipal(BigDecimal repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}
	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}
	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}
	public BigDecimal getRepaymentMoney() {
		return repaymentMoney;
	}
	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}
	public BigDecimal getResiduePrincipal() {
		return residuePrincipal;
	}
	public void setResiduePrincipal(BigDecimal residuePrincipal) {
		this.residuePrincipal = residuePrincipal;
	}
	
	
	
	
}

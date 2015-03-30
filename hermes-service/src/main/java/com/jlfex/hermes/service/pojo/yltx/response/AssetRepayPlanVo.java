package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AssetRepayPlanVo  implements  Serializable {

	/**
	 * 债权还款计划
	 */
	private static final long serialVersionUID = 5022618956635363487L;

	private  String      uniqId;				    //唯一标示
	private  int         period;					//期数
	private  Date        repaymentDate;				//应还日期
	private  BigDecimal  repaymentPrincipal;		//应还本金
	private  BigDecimal  repaymentInterest;			//应还利息
	private  BigDecimal  repaymentMoney;			//应还金额
	private  BigDecimal  residuePrincipal;			//剩余本金
	
	
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

package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RepayPlanVo  implements  Serializable {

	/**
	 * 债权还款计划
	 */
	private static final long serialVersionUID = 5022618956635363487L;

	private  String      id;				        //唯一标示
	private  int         period;					//期数
	private  Date        repaymentDate;				//应还日期
	private  BigDecimal  repaymentPrincipal;		//应还本金
	private  BigDecimal  repaymentInterest;			//应还利息
	private  BigDecimal  repaymentMoney;			//应还金额
	private  BigDecimal  residuePrincipal;			//剩余本金
	private  String      repaymentState;            //状态
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getRepaymentState() {
		return repaymentState;
	}
	public void setRepaymentState(String repaymentState) {
		this.repaymentState = repaymentState;
	}
	
	
	
}

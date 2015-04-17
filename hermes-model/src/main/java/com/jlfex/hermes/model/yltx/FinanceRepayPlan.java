package com.jlfex.hermes.model.yltx;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.model.Model;
/**
 * 理财产品  还款计划 模型
 */
@Entity
@Table(name = "hm_finance_repay_plan")
public class FinanceRepayPlan extends Model{

	private static final long serialVersionUID = 766692299088960261L;
	
	@ManyToOne
	@JoinColumn(name = "finance_order_id")         
	private  FinanceOrder   financeOrder;		    //所属债权
	private  int      period;					    //期数
	@Column(name =   "repayment_date")
	private  Date     repaymentDate;			    //应还日期
	@Column(name =   "repayment_principal")
	private  BigDecimal  repaymentPrincipal;		//应还本金
	@Column(name =   "old_principal")
	private  BigDecimal  oldPrincipal;		        //原始应还本金
	@Column(name =   "repayment_interest")
	private  BigDecimal  repaymentInterest;			//应还利息
	@Column(name =   "old_interest")
	private  BigDecimal  oldInterest;			    //原始应还利息
	@Column(name =   "repayment_money")
	private  BigDecimal  repaymentMoney;			//应还金额
	@Column(name =   "old_money")
	private  BigDecimal  oldMoney;			        //原始应还金额
	@Column(name =   "residue_principal")
	private  BigDecimal  residuePrincipal;			//剩余本金
	@Column(name =   "uniq_id")
	private  String   uniqId ;                      //唯一标识   
	
	
	public FinanceOrder getFinanceOrder() {
		return financeOrder;
	}
	public void setFinanceOrder(FinanceOrder financeOrder) {
		this.financeOrder = financeOrder;
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
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public BigDecimal getOldPrincipal() {
		return oldPrincipal;
	}
	public void setOldPrincipal(BigDecimal oldPrincipal) {
		this.oldPrincipal = oldPrincipal;
	}
	public BigDecimal getOldInterest() {
		return oldInterest;
	}
	public void setOldInterest(BigDecimal oldInterest) {
		this.oldInterest = oldInterest;
	}
	public BigDecimal getOldMoney() {
		return oldMoney;
	}
	public void setOldMoney(BigDecimal oldMoney) {
		this.oldMoney = oldMoney;
	}
	
	
	
	
}

package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Loan.Status;


/**
 * 外部：债权 还款明细 实体
 * @author Administrator
 *
 */
@Entity
@Table(name = "hm_credit_repay_plan")
public class CreditRepayPlan  extends Model {

	
	private static final long serialVersionUID = 4521119988264059769L;
	
	
	@ManyToOne
	@JoinColumn(name = "creditorId")
	private Creditor creditor;                  //所属债权人
	@ManyToOne
	@JoinColumn(name = "creditInfoId")          //所属债权
	private CrediteInfo  crediteInfo; 
	private int          period; 	            //期数
	private Date         repayTime;  	        //还款日期
	private Date         repayPlanTime;          //计划还款日期
	private BigDecimal   repayPrincipal;        //应还本金
	private BigDecimal   repayInterest; 	    //应还利息
	private BigDecimal   repayAllmount;         //应还总额
	private BigDecimal   remainPrincipal;       //剩余本金
	private String       status;                //处理结果
	private String       remark;                //备注
	
	public Creditor getCreditor() {
		return creditor;
	}
	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}
	public CrediteInfo getCrediteInfo() {
		return crediteInfo;
	}
	public void setCrediteInfo(CrediteInfo crediteInfo) {
		this.crediteInfo = crediteInfo;
	}
	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public Date getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}
	public Date getRepayPlanTime() {
		return repayPlanTime;
	}
	public void setRepayPlanTime(Date repayPlanTime) {
		this.repayPlanTime = repayPlanTime;
	}
	public BigDecimal getRepayPrincipal() {
		return repayPrincipal;
	}
	public void setRepayPrincipal(BigDecimal repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
	public BigDecimal getRepayInterest() {
		return repayInterest;
	}
	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}
	public BigDecimal getRepayAllmount() {
		return repayAllmount;
	}
	public void setRepayAllmount(BigDecimal repayAllmount) {
		this.repayAllmount = repayAllmount;
	}
	public BigDecimal getRemainPrincipal() {
		return remainPrincipal;
	}
	public void setRemainPrincipal(BigDecimal remainPrincipal) {
		this.remainPrincipal = remainPrincipal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, "-", Status.class);
	}
	
	public static final class Status{
		@Element("未还款")
		public static final String  WAIT_PAY			= "00";
		@Element("已还款")
		public static final String  ALREADY_PAY  		= "01";
		
	}
	
	
}

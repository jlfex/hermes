package com.jlfex.hermes.console.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jlfex.hermes.common.dict.Element;


/**
 * 外部债权导入：还款计划  vo
 * @author Administrator
 *
 */

public class RepayPlanVo implements Serializable    {

	
	private static final long serialVersionUID = 7830641634590682351L;
	
	private String  creditorNo; 	   //债权人编号
	private String  creditCode; 	   //债权编号
	private int    period; 	           //期数
	private Date    repayTime;  	   //应还日期
	private BigDecimal  repayPrincipal;    //应还本金
	private BigDecimal  repayInterest; 	   //应还利息
	private BigDecimal  repayAllmount;     //应还总额
	private BigDecimal  remainPrincipal;   //剩余本金
	private String  status;                //处理结果
	private String  remark;                //备注
	private String  fileName;              //文件名称
	
	
	public String getCreditorNo() {
		return creditorNo;
	}
	public void setCreditorNo(String creditorNo) {
		this.creditorNo = creditorNo;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public static final class  Status{
		@Element("格式正常")
		public static final String VALID = "00";
		@Element("格式异常")
		public static final String INVALID = "01";
	}
	

	
	
	
	

	
}

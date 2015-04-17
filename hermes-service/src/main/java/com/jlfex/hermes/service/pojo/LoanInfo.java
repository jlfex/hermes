package com.jlfex.hermes.service.pojo;
import java.io.Serializable;
import java.util.Date;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Loan;

/**
 * 借款信息
 */

public class LoanInfo implements Serializable {

	private static final long serialVersionUID = 2738596796196493682L;

	/** 编号 */
	private String id;
	
	/** 理财人 */
	private String invester;
	
	/** 头像 */
	private String avatar;
	
	/** 借款用途 */
	private String purpose;
	
	/** 年利率 */
	private String rate;
	
	/** 借款金额 */
	private String amount;
	
	/** 期限 */
	private String period;
	
	/** 剩余金额 */
	private String remain;
	
	/** 进度 */
	private String progress;
	
	/** 状态 */
	private String status;
	
	/**偿还本息*/
	private String repayPI;
	
	/**已还本息*/
	private String repayedPI;
	
	/**未还本息*/
	private String unRepayPI;
	
	/**还款名称*/
	private String repayName;
	
	/** 借款编号 */
	private String applicationNo;
	
	/** 还款方式 */
	private String repay;

	/** 应还金额 */
	private String repayAmount;

	/** 借款日期 */
	private Date datetime;
	//标类型
	private String loanKind;
	//招标截止期限
	private boolean outOfDate;
	
	/**
	 * 读取编号
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取理财人
	 * 
	 * @return
	 * @see #invester
	 */
	public String getInvester() {
		return invester;
	}

	/**
	 * 设置理财人
	 * 
	 * @param invester
	 * @see #invester
	 */
	public void setInvester(String invester) {
		this.invester = invester;
	}

	/**
	 * 读取头像
	 * 
	 * @return
	 * @see #avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * 设置头像
	 * 
	 * @param avatar
	 * @see #avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 读取借款用途
	 * 
	 * @return
	 * @see #purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * 设置借款用途
	 * 
	 * @param purpose
	 * @see #purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 读取年利率
	 * 
	 * @return
	 * @see #rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * 设置年利率
	 * 
	 * @param rate
	 * @see #rate
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * 读取借款金额
	 * 
	 * @return
	 * @see #amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 设置借款金额
	 * 
	 * @param amount
	 * @see #amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 读取期限
	 * 
	 * @return
	 * @see #period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * 设置期限
	 * 
	 * @param period
	 * @see #period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * 读取剩余金额
	 * 
	 * @return
	 * @see #remain
	 */
	public String getRemain() {
		return remain;
	}

	/**
	 * 设置剩余金额
	 * 
	 * @param remain
	 * @see #remain
	 */
	public void setRemain(String remain) {
		this.remain = remain;
	}

	/**
	 * 读取进度
	 * 
	 * @return
	 * @see #progress
	 */
	public String getProgress() {
		return progress;
	}

	/**
	 * 设置进度
	 * 
	 * @param progress
	 * @see #progress
	 */
	public void setProgress(String progress) {
		this.progress = progress;
	}

	/**
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, Status.class);
	}
	public String getRepayPI() {
		return repayPI;
	}

	public void setRepayPI(String repayPI) {
		this.repayPI = repayPI;
	}

	public String getRepayedPI() {
		return repayedPI;
	}

	public void setRepayedPI(String repayedPI) {
		this.repayedPI = repayedPI;
	}

	public String getUnRepayPI() {
		return unRepayPI;
	}

	public void setUnRepayPI(String unRepayPI) {
		this.unRepayPI = unRepayPI;
	}


	public String getRepayName() {
		return repayName;
	}

	public void setRepayName(String repayName) {
		this.repayName = repayName;
	}

	
	/**
	 * 读取
	 * 
	 * @return
	 * @see #applicationNo
	 */
	public String getApplicationNo() {
		return applicationNo;
	}

	/**
	 * 设置
	 * 
	 * @param applicationNo
	 * @see #applicationNo
	 */
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	/**
	 * 读取还款方式
	 * 
	 * @return
	 * @see #repay
	 */
	public String getRepay() {
		return repay;
	}

	/**
	 * 设置还款方式
	 * 
	 * @param repay
	 * @see #repay
	 */
	public void setRepay(String repay) {
		this.repay = repay;
	}

	/**
	 * 读取还款方式
	 * 
	 * @return
	 * @see #repayAmount
	 */
	public String getRepayAmount() {
		return repayAmount;
	}

	/**
	 * 设置还款方式
	 * 
	 * @param repayAmount
	 * @see #repayAmount
	 */
	public void setRepayAmount(String repayAmount) {
		this.repayAmount = repayAmount;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	

	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getLoanStatusName() {
		return Dicts.name(status, Loan.Status.class);
	}
	
	
	public String getLoanKind() {
		return loanKind;
	}

	public void setLoanKind(String loanKind) {
		this.loanKind = loanKind;
	}


	public boolean isOutOfDate() {
		return outOfDate;
	}

	public void setOutOfDate(boolean outOfDate) {
		this.outOfDate = outOfDate;
	}


	/**
	 * 状态
	 */
	public static final class Status {
		
		@Element("投标")
		public static final String BID			= "00";
		
		@Element("已完成")
		public static final String COMPLETED	= "99";
	}

	
	
	
	

}

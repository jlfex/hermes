package com.jlfex.hermes.service.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.Invest;

/**
 * 理财信息
 * 
 * @author chenqi
 * @version 1.0, 2014-01-04
 * @since 1.0
 */
public class InvestInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5435624359489652025L;

	/** 编号 */
	private String id;

	/** 借款用途 */
	private String purpose;

	/** 协议编号 */
	private String applicationNo;

	/** 年利率 */
	private String rate;

	/** 借款金额 */
	private BigDecimal amount;

	/** 期限 */
	private Integer period;

	/** 剩余金额 */
	private String remain;

	/** 进度 */
	private String progress;

	/** 状态 */
	private String status;

	/** 应收本息 */
	private String shouldReceivePI;

	/** 已收本息 */
	private String receivedPI;

	/** 待收本息 */
	private String waitReceivePI;

	/** 预期收益 */
	private String expectProfit;
	
	/** 投标日期 */
	private Date datetime;
	
	/** 真实姓名 */
	private String realName;
	
	/** 用户昵称*/
	private String account;
	
	


	/**
	 * 读取理财状态名称
	 * 
	 * @return
	 */
	public String getInvestStatusName() {
		return Dicts.name(status, Invest.Status.class);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 读取协议编号
	 * 
	 * @return
	 * @see #applicationNo
	 */
	public String getApplicationNo() {
		return applicationNo;
	}

	/**
	 * 设置协议编号
	 * 
	 * @param applicationNo
	 * @see #applicationNo
	 */
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getRemain() {
		return remain;
	}

	public void setRemain(String remain) {
		this.remain = remain;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShouldReceivePI() {
		return shouldReceivePI;
	}

	public void setShouldReceivePI(String shouldReceivePI) {
		this.shouldReceivePI = shouldReceivePI;
	}

	public String getReceivedPI() {
		return receivedPI;
	}

	public void setReceivedPI(String receivedPI) {
		this.receivedPI = receivedPI;
	}
	public String getWaitReceivePI() {
		return waitReceivePI;
	}

	public void setWaitReceivePI(String waitReceivePI) {
		this.waitReceivePI = waitReceivePI;
	}

	/**
	 * 读取
	 * 
	 * @return
	 * @see #expectProfit
	 */
	public String getExpectProfit() {
		return expectProfit;
	}

	/**
	 * 设置
	 * 
	 * @param expectProfit
	 * @see #expectProfit
	 */
	public void setExpectProfit(String expectProfit) {
		this.expectProfit = expectProfit;
	}

	/**
	 * 读取投标日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置投标日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}

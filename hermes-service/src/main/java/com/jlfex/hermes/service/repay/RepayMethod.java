package com.jlfex.hermes.service.repay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;

/**
 * 还款方式接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-17
 * @since 1.0
 */
public interface RepayMethod {

	
	/**
	 * 生成还款计划
	 * @param loan
	 * @param params
	 */
	public List<LoanRepay> generatePlan(Loan loan, Map<String, String> params);
	
	/**
	 * 获取还款计划
	 * @param loan
	 * @param params
	 * @param num
	 * @return
	 */
//	public List<LoanRepay> getPlan(Loan loan, Map<String, String> params, Integer... num);
	
	/**
	 * 获取收益
	 * @param loan
	 * @param params
	 * @param amount
	 * @return
	 */
	public BigDecimal getProceeds(Loan loan, Map<String, String> params, BigDecimal amount); 
	
	/**
	 * 获取逾期罚息
	 * @param overdueDay
	 * @param term
	 * @param monthRepayPrincipalIinterest
	 * @param rate
	 * @return
	 */
	public BigDecimal getOverdueInterest(int overdueDay,int term,BigDecimal monthRepayPrincipalIinterest,BigDecimal rate);
	
	
	/**
	 * 获取逾期违约金
	 * @param overdueDay
	 * @param term
	 * @param manageFee
	 * @param rate
	 * @return
	 */
	public BigDecimal getOverduePenalty(int overdueDay,int term,BigDecimal manageFee,BigDecimal rate);
	
		
	
	void getTiqian(Loan loan, Map<String, String> params);
	
	/**
	 * 获取借款本息和
	 * @param loan
	 * @return
	 */
	public BigDecimal getAllPT(Loan loan);
}

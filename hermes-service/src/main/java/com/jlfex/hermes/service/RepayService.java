package com.jlfex.hermes.service;

import java.util.Date;
import java.util.List;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.service.repay.RepayMethod;

/**
 * 还款方式业务接口
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
 */
public interface RepayService {
	/**
	 * 保存还款方式对象
	 * 
	 * @param invest
	 * @return
	 */
	public Repay save(Repay repay);

	/**
	 * 通过编号查询还款方式
	 * 
	 * @param id
	 * @return
	 */
	public Repay loadById(String id);

	/**
	 * 通过借款ID进行还款
	 * 
	 * @param id
	 */
	public boolean repayment(String id);

	/**
	 * 通过借款信息获取还款计划列表
	 * 
	 * @param loan
	 * @return
	 */
	public List<LoanRepay> findLoanRepay(Loan loan);

	/**
	 * 逾期还款实现
	 * 
	 * @param loanRepay
	 *            还款计划对象（单期）
	 */
	// public void overdueRepayment(LoanRepay loanRepay);

	/**
	 * 读取还款方法
	 * 
	 * @param repayId
	 * @return
	 */
	public RepayMethod getRepayMethod(String repayId);

	/**
	 * 此方法用于获取还款记录及最新一期待还
	 * 
	 * @param loan
	 * @return
	 */
	public List<LoanRepay> getloanRepayRecords(Loan loan);

	/**
	 * 查询所有还款方式
	 * 
	 * @return
	 */
	public List<Repay> findAll();
	
	/**
	 * 变更借款还款表的状态
	 * 
	 * @param id
	 */
	public boolean updateStatus(String id, String fromStatus, String toStatus);

	/**
	 *  通过计划日期区间和状态获取相关的借款还款记录
	 * @param startPlanDatetime
	 * @param endPlanDatetime
	 * @param status
	 * @return
	 */
	public List<LoanRepay> findByPlanDatetimeBetweenAndStatus(Date startPlanDatetime,Date endPlanDatetime,String status);

	
	/**
	 * 	通过状态获取相关的借款还款记录
	 * @param status
	 * @return
	 */
	public List<LoanRepay> findByStatus(String status);
	/**
	 * 通过传入借款还款id,进行逾期相关金额的计算，更新借款还款表
	 * 
	 * @param id
	 */
	public boolean overdueCalc(LoanRepay loanRepay);
	
	/**
	 * 通过借款ID进行自动还款
	 * 
	 * @param id
	 */
	public boolean autoRepayment(String id);
	
	/**
	 * 根据名称 和 状态   获取还款方式
	 * @param name
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<Repay> findByNameAndStatusIn(String name,String... status)  throws Exception;
	
}

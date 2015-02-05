package com.jlfex.hermes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;

/**
 * 还款计划仓库
 * 
 * @author Ray
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Repository
public interface LoanRepayRepository extends JpaRepository<LoanRepay, String> {

	/**
	 * 通过借款和期数加载还款计划
	 * 
	 * @param loanId
	 * @param sequence
	 * @return
	 */
	public LoanRepay findByLoanAndSequence(Loan loan, Integer sequence);

	/**
	 * 通过借款信息获取还款列表
	 * 
	 * @param loan
	 * @return
	 */
	@Query("from LoanRepay where loan = ?1 order by sequence desc")
	public List<LoanRepay> findByLoan(Loan loan);

	/**
	 * 通过借款信息获取该笔借款最大还款期数
	 * 
	 * @param loan
	 * @return
	 */
	@Query("select max(sequence) from LoanRepay where loan = ?1 ")
	public Integer findMaxSequenceByloan(Loan loan);

	/**
	 * 通过借款计划时间区间和状态获取还款列表
	 * 
	 * @param loan
	 * @return
	 */
	public List<LoanRepay> findByPlanDatetimeBetweenAndStatus(Date startPlanDatetime, Date endPlanDatetime, String status);

	
	/**
	 * 通过状态获取还款列表
	 * 
	 * @param loan
	 * @return
	 */
	public List<LoanRepay> findByStatus(String status);
	/**
	 * 根据标+状态 获取还款计划
	 * @param loan
	 * @param status
	 * @return
	 */
	public List<LoanRepay> findByLoanAndStatus(Loan loan, String status);
}

package com.jlfex.hermes.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.User;

/**
 * 
 * 理财收益信息仓库
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
 */
@Repository
public interface InvestProfitRepository extends JpaRepository<InvestProfit, String> {

	/**
	 * 通过还款计划表获取相应理财收益列表
	 * 
	 * @param loanRepay
	 * @return
	 */
	public List<InvestProfit> findByLoanRepay(LoanRepay loanRepay);

	/**
	 * 通过用户id和状态统计所有收益和
	 * 
	 * @param user
	 * @param statusList
	 * @return
	 */
	@Query("select sum(interest) +  sum(overdueInterest) from InvestProfit where user =  ?1 and status in ?2")
	public BigDecimal loadSumAllProfitByUserAndInStatus(User user, List<String> statusList);

	/**
	 * 通过用户id和状态统计所有利息和
	 * 
	 * @param user
	 * @param statusList
	 * @return
	 */
	@Query("select sum(interest) from InvestProfit where user =  ?1 and status in ?2")
	public BigDecimal loadInterestSumByUserAndInStatus(User user, List<String> statusList);

	/**
	 * 通过用户id和状态统计所有罚息和
	 * 
	 * @param user
	 * @param statusList
	 * @return
	 */
	@Query("select sum(overdueInterest) from InvestProfit where user =  ?1 and status in ?2")
	public BigDecimal loadOverdueInterestSumByUserAndInStatus(User user, List<String> statusList);

	/**
	 * 通过理财id统计所有应收本息和
	 * 
	 * @param user
	 * @param statusList
	 * @return
	 */
	@Query("select sum(amount) from InvestProfit where invest =  ?1")
	public BigDecimal loadAmountSumByInvest(Invest invest);

	/**
	 * 通过理财id和状态统计所有已收本息或者待收本息和
	 * 
	 * @param user
	 * @param statusList
	 * @return
	 */
	@Query("select sum(principal)+sum(interest)+sum(overdueInterest) from InvestProfit where invest =  ?1 and status in ?2")
	public BigDecimal loadSumAllProfitAndPrincipalByInvestAndInStatus(Invest invest, List<String> statusList);

	/**
	 * 通过理财信息获取理财收益列表
	 * 
	 * @param invest
	 * @return
	 */
	@Query("from InvestProfit ip where ip.invest = ?1 order by ip.loanRepay.sequence desc")
	public List<InvestProfit> findByInvest(Invest invest);
	
	/**
	 * 债权标    统计 所有罚息 , 利息
	 * @param profitStatusList
	 * @param loanKind
	 * @param user
	 * @return
	 */
	@Query("SELECT new InvestProfit(SUM(t.interest) , SUM(t.overdueInterest))  FROM InvestProfit t where  t.status in ?1  and  t.invest.loan.loanKind = ?2 and  t.user =?3  ")
	public InvestProfit sumAllProfitByAssignLoan(List<String> profitStatusList, String loanKind, User user);
	
//	/**
//	 * 债权表：获取收益
//	 * @param invest
//	 * @param loanKind
//	 * @return
//	 */
//	@Query("from InvestProfit ip where ip.invest = ?1 and ip.loanRepay.loan.loanKind=?2 order by ip.loanRepay.sequence desc")
//	public List<InvestProfit> findByInvest(Invest invest,String loanKind);

}

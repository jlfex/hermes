package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.User;

/**
 * 
 * 借款信息仓库
 * 
 * @author Ray
 * @version 1.0, 2013-12-23
 * @since 1.0
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {

	/**
	 * 通过状态查询借款
	 * 
	 * @param status
	 * @return
	 */
	public List<Loan> findByStatusIn(List<String> status);
	
	/**
	 * 通过状态分页查询借款
	 * 
	 * @param status
	 * @param pageable
	 * @return
	 */
	public Page<Loan> findByStatusIn(List<String> status, Pageable pageable);
	
	
	/**
	 * 通过用户查找借款
	 * @param user
	 * @return
	 */
	@Query("from Loan  where user = ?1 order by datetime desc")
	public List<Loan> findByUser(User user);
	
	/**
	 * 获取最大的 标 编号
	 * @return
	 */
	@Query("select t from Loan t  where t.loanNo = (select max(t1.loanNo) from Loan t1 where t1.loanNo is not null) ")
	public List<Loan> findAllOrderByLoanNo();
	
	
	
	
	
	/**
	 * 通过状态查询借款
	 * 
	 * @param status
	 * @return
	 */
	public List<Loan> findByStatus(String status);
	
	/**
	 * 通过用户编号查找借款
	 * 
	 * @param userId
	 * @return
	 */
	public List<Loan> findByUserIdOrderByDatetimeDesc(String userId);
	
	/**
	 * 标的： 类型 和 状态 列表
	 * @return
	 */
	
	public Page<Loan> findByloanKindInAndStatusIn(List<String> loanKinds, List<String> status, Pageable pageable);
	/**
	 * 根据标类型 +状态+ 债权信息 
	 * @param creditInfoId
	 * @param loanKind
	 * @param status
	 * @return
	 */
	@Query("select  t from Loan  t  where t.creditInfoId = ?1 and t.loanKind = ?2 and t.status = ?3 ")
	public List<Loan> findByCreditInfoAndLoanKindAndStatus(String creditInfoId, String loanKind, String status );
	/**
	 * 根据标类型+ 债权信息 
	 * @param creditInfoId
	 * @param loanKind
	 * @param status
	 * @return
	 */
	@Query("select  t from Loan  t  where t.creditInfoId = ?1 and t.loanKind = ?2  and t.status !='30' ")
	public List<Loan> findByCreditInfoAndLoanKind(String creditInfoId, String loanKind );
}

package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Creditor;

/**
 * 债权信息 仓库
 * @author Administrator
 *
 */
@Repository
public interface CreditorInfoRepository extends JpaRepository<Creditor, String> {

	/**
	 * 通过状态查询债权信息
	 * 
	 * @param status
	 * @return
	 */
	public List<Creditor> findByStatusIn(List<String> status);
	
	@Query("select a from Creditor a where a.id = ?1") 
	public Creditor findById(String id); 
	
//    @Query("select a from Creditor a where a. > ?1") 
//	public Page<Creditor> findByBalanceGreaterThan(Integer balance,Pageable pageable); 


	

	
//	
//	/**
//	 * 通过用户查找借款
//	 * @param user
//	 * @return
//	 */
//	@Query("from Loan  where user = ?1 order by datetime desc")
//	public List<Loan> findByUser(User user);
//	
//	/**
//	 * 按照借款编号倒叙排列	
//	 * @return
//	 */
//	@Query("from Loan order by loanNo desc")
//	public List<Loan> findAllOrderByLoanNo();
//	
//	
//	
//	
//	
//	/**
//	 * 通过状态查询借款
//	 * 
//	 * @param status
//	 * @return
//	 */
//	public List<Loan> findByStatus(String status);
//	
//	/**
//	 * 通过用户编号查找借款
//	 * 
//	 * @param userId
//	 * @return
//	 */
//	public List<Loan> findByUserIdOrderByDatetimeDesc(String userId);
//	
//	/**
//	 * 按照 类型 和 状态 倒叙排列	
//	 * @return
//	 */
//	@Query("from Loan  where  status = ?1 and loanKind = ?2 order by loanNo desc")
//	public List<Loan> findLoanByStatusAndKind(String status, String loanKind );
//
//	
}

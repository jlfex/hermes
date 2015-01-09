package com.jlfex.hermes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.UserAccount;

/**
 * 交易流水信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-06
 * @since 1.0
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	/**
	 * 通过来源账户和类型列表以及日期范围查询交易
	 * 
	 * @param userAccount
	 * @param types
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Query("from Transaction tx where tx.sourceUserAccount = ?1 and type in (?2) and datetime between ?3 and ?4 order by datetime desc")
	public List<Transaction> findBySourceUserAccountAndTypeInAndDatetimeBetween(UserAccount userAccount, List<String> types, Date beginDate, Date endDate);
	
	/**
	 * 通过来源账户和类型列表以及日期范围分页查询交易
	 * 
	 * @param userAccount
	 * @param types
	 * @param beginDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	public Page<Transaction> findBySourceUserAccountAndTypeInAndDatetimeBetween(UserAccount userAccount, List<String> types, Date beginDate, Date endDate, Pageable pageable);
	
	/**
	 * 通过账户查询风险金的所有转入转出 
	 */
	@Query("from Transaction tx where tx.sourceUserAccount = ?1 or targetUserAccount = ?2 and type in (?3) ")
	public List<Transaction> findByUserAccountAndTypeIn(UserAccount sourceuserAccount,UserAccount targetuserAccount, List<String> types);
	
	public Page<Transaction> findBySourceUserAccountOrTargetUserAccountAndTypeIn(UserAccount sourceuserAccount,UserAccount targetuserAccount,List<String> types, Pageable pageable);
}

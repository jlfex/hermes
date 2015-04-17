package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.BankAccount;

/**
 * 银行账户仓库
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String>, JpaSpecificationExecutor<BankAccount> {

	/**
	 * 通过用户编号和状态查询银行账户
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<BankAccount> findByUserIdAndStatus(String userId, String status);

	/**
	 * 通过用户编号查询银行账户
	 * 
	 * @param userId
	 * @return
	 */
	public List<BankAccount> findByUserId(String userId);
	public BankAccount findOneByUserIdAndStatus(String userId,String status);

}

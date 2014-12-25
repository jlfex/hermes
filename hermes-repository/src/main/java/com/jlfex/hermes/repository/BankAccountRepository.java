package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.BankAccount;

/**
 * 银行账户仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-02
 * @since 1.0
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

	/**
	 * 通过用户编号和状态查询银行账户
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<BankAccount> findByUserIdAndStatus(String userId, String status);
}

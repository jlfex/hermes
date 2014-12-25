package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Bank;

/**
 * 银行信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-02
 * @since 1.0
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, String> {

	/**
	 * 通过状态查询银行
	 * 
	 * @param status
	 * @return
	 */
	public List<Bank> findByStatus(String status);
}

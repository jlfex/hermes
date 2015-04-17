package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Bank;

/**
 * 银行信息仓库
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, String>, JpaSpecificationExecutor<Bank> {
	/**
	 * 通过状态查询银行
	 * 
	 * @param status
	 * @return
	 */
	public List<Bank> findByStatus(String status);
}

package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Withdraw;

/**
 * 提现信息仓库
 */
@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, String>, JpaSpecificationExecutor<Withdraw> {

	/**
	 * 根据流水号 查询提现记录
	 * @param serialNo
	 * @return
	 */
	Withdraw  findBySerialNo(String serialNo);
}

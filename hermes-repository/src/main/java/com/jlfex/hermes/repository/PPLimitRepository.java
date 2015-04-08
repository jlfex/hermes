package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.PPLimit;

/**
 * 地区信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-03
 * @since 1.0
 */
@Repository
public interface PPLimitRepository extends JpaRepository<PPLimit, String>, JpaSpecificationExecutor<PPLimit> {
	PPLimit findOneByPpOrgAndBank(String ppOrg,Bank bank);
}

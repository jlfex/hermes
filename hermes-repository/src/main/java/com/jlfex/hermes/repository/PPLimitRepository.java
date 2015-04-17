package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.PPLimit;

/**
 * 中金限额仓库
 */
@Repository
public interface PPLimitRepository extends JpaRepository<PPLimit, String>, JpaSpecificationExecutor<PPLimit> {
	PPLimit findOneByPpOrgAndBank(String ppOrg,Bank bank);
}

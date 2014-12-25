package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Repay;


/**
 * 
 *还款方式信息仓库
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
 */
@Repository
public interface RepayRepository  extends JpaRepository<Repay, String> {

	
}

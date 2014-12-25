package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Withdraw;

/**
 * 提现信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-04
 * @since 1.0
 */
@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, String> {

}

package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Repay;

/**
 *还款方式信息仓库
 */
@Repository
public interface RepayRepository  extends JpaRepository<Repay, String>, JpaSpecificationExecutor<Repay> {

	public List<Repay> findByNameAndStatusIn(String name, List<String> articleStatus) ;
}

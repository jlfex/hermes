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

	/**
	 * 根据名称和状态
	 * @param name
	 * @param articleStatus
	 * @return
	 */
	public List<Repay> findByNameAndStatusIn(String name, List<String> articleStatus) ;
	/**
	 * 根据编码和状态
	 * @param code
	 * @param Status
	 * @return
	 */
	public List<Repay> findByCodeAndStatus(String code, String Status) ;
}

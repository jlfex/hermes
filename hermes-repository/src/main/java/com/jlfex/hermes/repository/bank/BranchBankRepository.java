package com.jlfex.hermes.repository.bank;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.hermes.BranchBank;

/**
 * 支行信息仓库
 */
@Repository
public interface BranchBankRepository extends JpaRepository<BranchBank, String>, JpaSpecificationExecutor<BranchBank>{
	/**
	 * 通过银行名称及城市查询支行信息
	 * 
	 * @param status
	 * @return
	 */
	@Query("from BranchBank t where t.bankName = ?1 and t.cityName = ?2")
	public List<BranchBank> findByBranchBankAndCity(String bankName,String cityName);

}

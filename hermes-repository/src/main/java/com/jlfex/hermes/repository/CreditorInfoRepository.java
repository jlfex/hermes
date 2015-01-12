package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Creditor;

/**
 * 债权信息 仓库
 * @author Administrator
 *
 */
@Repository
public interface CreditorInfoRepository extends JpaRepository<Creditor, String> {

	/**
	 * 通过状态查询债权信息
	 * 
	 * @param status
	 * @return
	 */
	public List<Creditor> findByStatusIn(List<String> status);
	
	@Query("select a from Creditor a where a.id = ?1") 
	public Creditor findById(String id); 

}

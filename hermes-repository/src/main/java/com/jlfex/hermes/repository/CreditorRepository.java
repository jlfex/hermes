package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Creditor;

/**
 * 债权人信息 仓库
 * 
 * @author Administrator
 * 
 */
@Repository
public interface CreditorRepository extends JpaRepository<Creditor, String>, JpaSpecificationExecutor<Creditor> {


	@Query("select t from Creditor t  where t.creditorNo = (select max(t1.creditorNo) from Creditor t1 where t1.creditorNo is not null) ")
	public List<Creditor> findMaxCredtorNo();
	
	@Query("select t from Creditor t where t.id = ?1")
	public Creditor findAllById(String id );
	
	@Query("select t from Creditor t where t.creditorNo =?1")
	public Creditor  findByCredtorNo(String creditorNo);

	@Query("select a from Creditor a where a.id = ?1")
	public Creditor findById(String id);

	public List<Creditor> findByStatusIn(List<String> status);
	
	@Query("select t from Creditor t where t.originNo =?1")
	public Creditor  findByOriginNo(String originNo);


}

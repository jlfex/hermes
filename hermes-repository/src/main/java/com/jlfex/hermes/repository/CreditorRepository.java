package com.jlfex.hermes.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Creditor;

/**
 * 债权人信息  仓库
 * @author Administrator
 *
 */
@Repository
public interface CreditorRepository extends JpaRepository<Creditor, String> ,JpaSpecificationExecutor<Creditor>{

	@Query("select t from Creditor t order by t.creditorNo desc")
	public List<Creditor> findAllByCredtorNo();
	
	@Query("select t from Creditor t where t.id = ?1")
	public Creditor findAllById(String id );
}

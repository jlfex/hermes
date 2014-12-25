package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAudit;

/**
 * 
 * 审核日志仓库
 * 
 * @author Ray
 * @version 1.0, 2014-1-23
 * @since 1.0
 */
@Repository
public interface LoanAuditReository extends JpaRepository<LoanAudit, String>{
	
	
	@Query("from LoanAudit where loan = ?1 order by create_time asc")
	public  List<LoanAudit> findByLoan(Loan loan);
}

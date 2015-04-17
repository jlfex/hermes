package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAudit;

/**
 * 审核日志仓库
 */
@Repository
public interface LoanAuditReository extends JpaRepository<LoanAudit, String>, JpaSpecificationExecutor<LoanAudit> {

	@Query("from LoanAudit where loan = ?1 order by create_time asc")
	public List<LoanAudit> findByLoan(Loan loan);

	@Query("from LoanAudit t where t.loan = ?1 and t.type = ?2 and t.status = ?3 order by create_time asc")
	public LoanAudit findByLoanAndTypeAndStatus(Loan loan, String type, String status);

}

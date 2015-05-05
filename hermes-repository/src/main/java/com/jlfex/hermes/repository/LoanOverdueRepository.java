package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanOverdue;

/**
 * 借款逾期等级仓库
 */
@Repository
public interface LoanOverdueRepository  extends JpaRepository<LoanOverdue, String>, JpaSpecificationExecutor<LoanOverdue>{
	
	public LoanOverdue findByLoanAndRank(Loan loan,Integer rank);

	public List<LoanOverdue> findByLoan(Loan loan);
}

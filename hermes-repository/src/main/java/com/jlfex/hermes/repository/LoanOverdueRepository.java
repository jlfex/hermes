package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanOverdue;

/**
 * 
 * 借款逾期等级仓库
 * 
 * @author Ray
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Repository
public interface LoanOverdueRepository  extends JpaRepository<LoanOverdue, String>{
	
	public LoanOverdue findByLoanAndRank(Loan loan,Integer rank);

}

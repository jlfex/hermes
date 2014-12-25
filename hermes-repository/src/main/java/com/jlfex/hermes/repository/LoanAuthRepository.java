package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAuth;

/**
 * 
 * 借款认证仓库
 * 
 * @author chenqi
 * @version 1.0, 2014-02-08
 * @since 1.0
 */
@Repository
public interface LoanAuthRepository extends JpaRepository<LoanAuth, String> {
	
	@Query("from LoanAuth where loan = ?1 order by create_time asc")
	public  List<LoanAuth> findByLoan(Loan loan);
}

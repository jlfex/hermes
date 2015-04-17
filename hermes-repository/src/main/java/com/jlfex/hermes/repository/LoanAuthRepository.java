package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAuth;

/**
 * 借款认证仓库
 */
@Repository
public interface LoanAuthRepository extends JpaRepository<LoanAuth, String>, JpaSpecificationExecutor<LoanAuth> {
	
	@Query("from LoanAuth where loan = ?1 order by create_time asc")
	public  List<LoanAuth> findByLoan(Loan loan);
}

package com.jlfex.hermes.service.loanRepay;
import java.util.List;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;

/**
 * 借款 还款计划
 * @author Administrator
 *
 */
public interface LoanRepayService {

  
   
	/**
	 * 根据标+状态 获取还款计划
	 * @param loan
	 * @param status
	 * @return
	 */
	public List<LoanRepay> findByLoanAndStatus(Loan loan, String status);

}

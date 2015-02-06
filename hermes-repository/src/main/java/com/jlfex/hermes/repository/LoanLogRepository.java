package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;

@Repository
public interface LoanLogRepository  extends JpaRepository<LoanLog, String>, JpaSpecificationExecutor<LoanLog> {
	
	
	/**
	 * 通过借款Id和类型查找借款日志对象
	 * @param loanId
	 * @param type
	 * @return 返回单个借款日志对象
	 */
	public LoanLog findByLoanIdAndType(String loanId,String type);
	
	
	/**
	 * 通过借款Id和类型查找借款日志对象List
	 * @param loanId
	 * @param type
	 * @return 返回单个借款日志
	 */
	public List<LoanLog> findByLoanAndTypeIn(Loan loan,List<String> type);
	/**
	 * 通过loan 查找借款日志对象List
	 * @param loanId
	 * @param type
	 * @return 返回单个借款日志List
	 * @author Administrator
	 */
	public List<LoanLog> findByLoan(Loan loan);

}

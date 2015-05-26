package com.jlfex.hermes.service.loanRepay;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.repository.LoanRepayRepository;

/**
 * 借款还款计划
 * @author Administrator
 *
 */
@Service
@Transactional
public class LoanRepayServiceImpl implements  LoanRepayService {

	@Autowired
	private LoanRepayRepository loanRepayRepository;

	/**
	 *根据标+状态 获取还款计划
	 */
	@Override
	public List<LoanRepay> findByLoanAndStatus(Loan loan, String status) {
		return loanRepayRepository.findByLoanAndStatus(loan, status);
	}
    /**
     * 根据loan + 期数 获取还款计划
     */
	@Override
	public LoanRepay findByLoanAndSequence(Loan loan, Integer sequence) {
		return loanRepayRepository.findByLoanAndSequence(loan, sequence);
	}

	
	

}

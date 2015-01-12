package com.jlfex.hermes.service;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAudit;

public interface LoanAuditService {
	/**
	 * 通过 类型 and 状态查询借款
	 */
	public LoanAudit findByLoanAndTypeAndStatus(Loan loan, String loanKind, String status);

}

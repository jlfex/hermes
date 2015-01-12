package com.jlfex.hermes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAudit;
import com.jlfex.hermes.repository.LoanAuditReository;
import com.jlfex.hermes.service.LoanAuditService;

@Service
@Transactional
public class LoanAuditServiceImpl implements LoanAuditService {
	@Autowired
	private LoanAuditReository loanAuditReository;

	@Override
	public LoanAudit findByLoanAndTypeAndStatus(Loan loan, String type, String status) {
		return loanAuditReository.findByLoanAndTypeAndStatus(loan, type, status);
	}
}

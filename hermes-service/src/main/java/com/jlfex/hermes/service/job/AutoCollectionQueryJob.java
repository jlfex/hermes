package com.jlfex.hermes.service.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cfca.payment.api.tx.Tx1362Request;
import cfca.payment.api.tx.Tx1362Response;

import com.jlfex.hermes.common.constant.HermesEnum.Tx1361Status;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.InvestRepository;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.repository.TransactionRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.cfca.ThirdPPService;
import com.jlfex.hermes.service.job.Job.Result;

/**
 * 自动代收查询
 * 
 * @author wujinsong
 *
 */
@Component("autoCollectionQueryJob")
public class AutoCollectionQueryJob extends Job {
	@Autowired
	private CFCAOrderRepository cFCAOrderRepository;
	@Autowired
	private ThirdPPService thirdPPService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private InvestRepository investRepository;
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private LoanRepository loanRepository;

	@Override
	public Result run() {
		try {
			List<CFCAOrder> cfcaOrders = cFCAOrderRepository.findAllByStatus(Tx1361Status.IN_PROCESSING.getStatus());
			for (CFCAOrder cfcaOrder : cfcaOrders) {
				Tx1362Request request = new Tx1362Request();
				request.setInstitutionID("001155");
				request.setTxSN(cfcaOrder.getTxSN());
				Tx1362Response response = (Tx1362Response) thirdPPService.invokeTx1362(request);
				if (response.getStatus() != Tx1361Status.WITHHOLDING_SUCC.getStatus()) {
					Transaction transaction = transactionRepository.findOneByReferenceAndType(cfcaOrder.getInvest().getId(), Transaction.Type.CHARGE);
					transaction.setRemark(Transaction.Status.RECHARGE_SUCC);
					User user = cfcaOrder.getInvest().getUser();
					UserAccount cashAccount = userAccountRepository.findByUserAndType(user, UserAccount.Type.CASH);
					cashAccount.setBalance(cashAccount.getBalance().add(cfcaOrder.getAmount()));
					userAccountRepository.save(cashAccount);
					transactionService.freeze(Transaction.Type.FREEZE, user.getId(), cfcaOrder.getInvest().getAmount(), cfcaOrder.getInvest().getLoan().getId(), "投标冻结");
					cfcaOrder.setStatus(Tx1361Status.WITHHOLDING_SUCC.getStatus());
					Invest invest = investRepository.findOne(cfcaOrder.getInvest().getId());
					invest.setStatus(Invest.Status.FREEZE);
					investRepository.save(invest);
					cFCAOrderRepository.save(cfcaOrder);
					transactionRepository.save(transaction);
				} else if (response.getStatus() == Tx1361Status.WITHHOLDING_SUCC.getStatus()) {
					Transaction transaction = transactionRepository.findOneByReferenceAndType(cfcaOrder.getInvest().getId(), Transaction.Type.CHARGE);
					transaction.setRemark(Transaction.Status.RECHARGE_FAIL);
					Invest invest = investRepository.findOne(cfcaOrder.getInvest().getId());
					invest.setStatus(Invest.Status.FAIL);
					Loan loan = loanRepository.findOne(cfcaOrder.getInvest().getLoan().getId());
					loan.setProceeds(loan.getAmount().subtract(cfcaOrder.getInvest().getAmount()));
					cfcaOrder.setStatus(Tx1361Status.WITHHOLDING_FAIL.getStatus());

					investRepository.save(invest);
					cFCAOrderRepository.save(cfcaOrder);
					transactionRepository.save(transaction);
				}
			}
			return new Result(true, true, "");
		} catch (Exception e) {
			throw e;
		}
	}
}

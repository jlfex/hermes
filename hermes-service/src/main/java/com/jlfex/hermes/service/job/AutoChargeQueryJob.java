package com.jlfex.hermes.service.job;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cfca.payment.api.tx.Tx1362Request;
import cfca.payment.api.tx.Tx1362Response;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.constant.HermesEnum.Tx1361Status;
import com.jlfex.hermes.common.exception.ServiceException;
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
import com.jlfex.hermes.service.job.Job;

/**
 * 中金充值订单状态同步JOB
 * 
 * @author wujinsong
 *
 */
@Component("autoChargeQueryJob")
public class AutoChargeQueryJob extends Job {
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
	@Autowired
	private InvestService investService;

	@Override
	public Result run() {
		String JobDESC = "中金充值订单状态同步JOB：";
		try {
			Logger.info(JobDESC+"开始....");
			List<CFCAOrder> cfcaOrders = cFCAOrderRepository.findAllByStatusAndType(Tx1361Status.IN_PROCESSING.getStatus(), CFCAOrder.Type.RECHARGE);
			for (CFCAOrder cfcaOrder : cfcaOrders) {
				Tx1362Request request = new Tx1362Request();
				request.setInstitutionID(App.config(HermesConstants.CFCA_INSTITUTION_ID_CODE));
				request.setTxSN(cfcaOrder.getTxSN());
				Tx1362Response response = (Tx1362Response) thirdPPService.invokeTx1362(request);
				User user = cfcaOrder.getUser();
				if(response != null){
					if (response.getStatus() == Tx1361Status.WITHHOLDING_SUCC.getStatus()) {
						transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, user, UserAccount.Type.ZHONGJIN_FEE, cfcaOrder.getAmount().add(cfcaOrder.getFee()), cfcaOrder.getId(), Transaction.Status.RECHARGE_SUCC);
						transactionService.toCropAccount(Transaction.Type.CHARGE, user, UserAccount.Type.PAYMENT_FEE, cfcaOrder.getFee(), cfcaOrder.getId(), "充值手续费");
						cfcaOrder.setStatus(Tx1361Status.WITHHOLDING_SUCC.getStatus());
						cFCAOrderRepository.save(cfcaOrder);
					} else if (response.getStatus() == Tx1361Status.WITHHOLDING_FAIL.getStatus()) {
						transactionService.cropAccountToZJPay(Transaction.Type.CHARGE, user, UserAccount.Type.ZHONGJIN_FEE, cfcaOrder.getAmount(), cfcaOrder.getId(), Transaction.Status.RECHARGE_FAIL);
						cfcaOrder.setStatus(Tx1361Status.WITHHOLDING_FAIL.getStatus());
						cFCAOrderRepository.save(cfcaOrder);
					}
				}else{
					
				}
			}
			return new Result(true, true, "");
		} catch (Exception e) {
			ServiceException exception = new ServiceException(e.getMessage(), e);
			throw exception;
		}
	}
}

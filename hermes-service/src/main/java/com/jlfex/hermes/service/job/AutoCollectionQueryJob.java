package com.jlfex.hermes.service.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;







import cfca.payment.api.tx.Tx1362Request;
import cfca.payment.api.tx.Tx1362Response;

import com.jlfex.hermes.common.constant.HermesEnum.Tx1361Status;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;
import com.jlfex.hermes.service.cfca.ThirdPPService;

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

	@Override
	public Result run() {
		List<CFCAOrder> cfcaOrders = cFCAOrderRepository.findAllByStatus(Tx1361Status.IN_PROCESSING.getStatus());
		for (CFCAOrder cfcaOrder : cfcaOrders) {
			Tx1362Request request = new Tx1362Request();
			request.setInstitutionID("001155");
			request.setTxSN(cfcaOrder.getTxSN());
			Tx1362Response response = (Tx1362Response) thirdPPService.invokeTx1362(request);
			if(response.getStatus() == Tx1361Status.WITHHOLDING_SUCC.getStatus()) {
				
			}
		}
		
		return null;
	}
}

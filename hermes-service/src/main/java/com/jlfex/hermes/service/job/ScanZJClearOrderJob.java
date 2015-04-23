package com.jlfex.hermes.service.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cfca.payment.api.tx.Tx1350Response;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.cfca.CFCAOrderService;

/**
 * 提现业务：扫描中金结算订单  状态同步
 */
@Component("scanZJClearOrderJob")
public class ScanZJClearOrderJob extends Job {

	@Autowired
	private CFCAOrderRepository cFCAOrderRepository;
	@Autowired
	private CFCAOrderService cFCAOrderService;
	@Autowired
	private TransactionService transactionService;
	
	
	@Override
	public Result run() {
		String var = "提现业务:扫描中金结算订单JOB: ";
		try{
			Logger.info(var+"开始处理");
			List<Integer>  clearStatus = new ArrayList<Integer>();
			clearStatus.add(CFCAOrder.ClearStatus.CLEAR_INIT);
			clearStatus.add(CFCAOrder.ClearStatus.CLEAR_DOING);
			clearStatus.add(CFCAOrder.ClearStatus.CLEAR_RECEIVE);
			List<CFCAOrder> cfcaOrderList = cFCAOrderRepository.findAllByStatusInAndType(clearStatus, CFCAOrder.Type.CLEAR);
			List<CFCAOrder> finishedOrderList = new ArrayList<CFCAOrder>();
			if(cfcaOrderList !=null && cfcaOrderList.size() > 0){
				for(CFCAOrder order: cfcaOrderList){
					if(order== null || Strings.empty(order.getTxSN())){
						continue ;
					}
					Tx1350Response tx1350Response = cFCAOrderService.invokeTx1350Request(order.getTxSN());
					if(tx1350Response!= null){
						if(CFCAOrder.ClearStatus.CLEAR_FINISH == tx1350Response.getStatus() &&
						    order.getTxSN().equals(tx1350Response.getSerialNumber())){
							//更新订单状态
							order.setStatus(CFCAOrder.ClearStatus.CLEAR_FINISH);
							finishedOrderList.add(order);
							//解冻
							transactionService.unfreeze(Transaction.Type.UNFREEZE, order.getUser(), order.getFee(), order.getUser().getId(), "解冻提现手续费");
							transactionService.unfreeze(Transaction.Type.UNFREEZE, order.getUser(), order.getAmount(), order.getUser().getId(), "解冻提现金额");
							//扣除平台手续费
							transactionService.toCropAccount(Transaction.Type.OUT, order.getUser(), UserAccount.Type.WITHDRAW_FEE, order.getFee(), order.getUser().getId(), "扣除提现手续费");
							//可用余额减少
							transactionService.toCropAccount(Transaction.Type.OUT, order.getUser(), UserAccount.Type.ZHONGJIN_FEE, order.getFee(), order.getUser().getId(), "扣除提现金额");
						}
					}
				}
			}
			//保存订单状态
			cFCAOrderRepository.save(finishedOrderList);
			Logger.info(var+"处理完成!");
		}catch(Exception e){
			throw  new ServiceException(e.getMessage(), e);
		}
		return new Result(true, false, "");
	}
}

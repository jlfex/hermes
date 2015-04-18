package com.jlfex.hermes.service.job;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.yltx.JlfexOrder;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.api.yltx.JlfexService;
import com.jlfex.hermes.service.order.jlfex.JlfexOrderService;
import com.jlfex.hermes.service.pojo.yltx.response.OrderResponseVo;
import com.jlfex.hermes.service.pojo.yltx.response.OrderVo;
import com.jlfex.hermes.service.userAccount.UserAccountService;

/**
 * 处理 支付状态=支付确认中 的订单
 */
@Component("scanJlfexWaitOrderJob")
public class ScanJlfexWaitOrderJob extends Job {

	
	@Autowired
	private JlfexService  jlfexService;
    @Autowired
    private JlfexOrderService jlfexOrderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private InvestService investService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
	private LoanRepository loanRepository;
	
	@Override
	public Result run() {
 		String var = "jlfex支付确认中订单扫描JOB：";
		try {
			List<String> payStatusList = new ArrayList<String>();
			payStatusList.add(HermesConstants.PAY_WAIT_CONFIRM);
			payStatusList.add(HermesConstants.NOTHING);
			List<JlfexOrder> orderList = jlfexOrderService.queryOrderByPayStatus(payStatusList);
			for(JlfexOrder order : orderList){
				try{
					String result =  jlfexService.queryOrderStatus(order.getOrderCode());
					if(Strings.notEmpty(result)){
						OrderResponseVo  responVo = JSON.parseObject(result, OrderResponseVo.class);
						List<OrderVo>   orderVoList = responVo.getContent();
						if(orderVoList==null){
							throw new Exception(var+ "根据orderCode="+order.getOrderCode()+" jlfex接口返回订单条数为空!");
						}
						if(orderVoList.size() != 1){
							throw new Exception(var+ "根据orderCode="+order.getOrderCode()+" jlfex接口返回订单条数:"+orderVoList.size());
						}
						OrderVo vo = orderVoList.get(0);
						Invest updateInvest = order.getInvest();
						User investUser = order.getInvest().getUser();
						String investStatus  = null;
						String orderStatus = vo.getOrderStatus();
						String payStatus = vo.getPayStatus();
						String orderDealStatus = JlfexOrder.Status.FIN_DEAL;
						if(HermesConstants.PAY_SUC.equals(vo.getPayStatus().trim())){
							//支付成功
							Loan loan = order.getInvest().getLoan();
							transactionService.cropAccountToJlfexPay(Transaction.Type.CHARGE,investUser , UserAccount.Type.JLFEX_FEE, order.getOrderAmount(),investUser.getId() , "JLfex代扣充值成功");
							transactionService.freeze(Transaction.Type.FREEZE, investUser.getId(), order.getOrderAmount(), loan.getId(), "投标冻结");
							Logger.info("资金流水记录成功  理财ID investId="+order.getInvest().getId());
							//保存操作日志
							investService.saveLoanLog(investUser, order.getOrderAmount(), loan, LoanLog.Type.INVEST, "投标成功");
							investService.saveUserLog(investUser);	
							investStatus = Invest.Status.FREEZE;
							payStatus = HermesConstants.PAY_SUC;
							//更新投标进度
							loan.setProceeds(loan.getProceeds().add(order.getOrderAmount()));
							loanRepository.save(loan);
						}else if(HermesConstants.PAY_FAIL.equals(vo.getPayStatus().trim())){
							//支付失败 撤单
							UserAccount cropAccount = userAccountService.findByUserIdAndType(HermesConstants.CROP_USER_ID, UserAccount.Type.ZHONGJIN_FEE);
							UserAccount investAccount = userAccountService.findByUserAndType(investUser, UserAccount.Type.CASH);
							transactionService.addCashAccountRecord(Transaction.Type.CHARGE, cropAccount,investAccount, order.getOrderAmount(), investUser.getId(), "JLfex代扣充值失败");
							investStatus = Invest.Status.FAIL;
							payStatus = HermesConstants.PAY_FAIL;
							orderDealStatus = JlfexOrder.Status.CANCEL;
							jlfexService.revokeOrder(order.getOrderCode());
							Logger.info(var+"撤单成功!");
							investService.saveLoanLog(investUser, order.getOrderAmount(), order.getInvest().getLoan(), LoanLog.Type.INVEST, "投标支付失败");
						}
						//更新订单状态
						order.setPayStatus(payStatus);
						order.setOrderStatus(orderStatus);
						order.setStatus(orderDealStatus);
						jlfexOrderService.saveOrder(order);
						//更新理财信息
						updateInvest.setStatus(investStatus);
						investService.save(updateInvest);
					}
				}catch(Exception e){
					Logger.error(var+",异常", e);
					continue;
				}
			}
		} catch (Exception e) {
			throw  new ServiceException(e.getMessage(), e);
		}
		return new Result(true, false, "");
	}

}

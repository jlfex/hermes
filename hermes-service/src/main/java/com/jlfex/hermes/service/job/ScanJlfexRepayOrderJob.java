package com.jlfex.hermes.service.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.yltx.JlfexOrder;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.InvestProfitService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.api.yltx.JlfexService;
import com.jlfex.hermes.service.loanRepay.LoanRepayService;
import com.jlfex.hermes.service.order.jlfex.JlfexOrderService;
import com.jlfex.hermes.service.pojo.yltx.response.OrderResponseVo;
import com.jlfex.hermes.service.pojo.yltx.response.OrderVo;

/**
 * 同步 已经回购订单  的订单状态
 */
@Component("scanJlfexRepayOrderJob")
public class ScanJlfexRepayOrderJob extends Job {

	
	@Autowired
	private JlfexService  jlfexService;
    @Autowired
    private JlfexOrderService jlfexOrderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private InvestService investService;
    @Autowired
    private InvestProfitService investProfitService;
    @Autowired
    private LoanRepayService  loanRepayService;
    @Autowired
    private InvestProfitRepository investProfitRepository;
    @Autowired
    private CreditInfoService creditInfoService;
    @Autowired
    private UserService userService;
	
	@Override
	public Result run() {
		String var = "jlfex订单回购状态同步JOB：";
		try {
			List<String> payStatusList = new ArrayList<String>();
			payStatusList.add(HermesConstants.PAY_SUC);
			List<JlfexOrder> orderList = jlfexOrderService.queryOrderByPayStatus(payStatusList);
			for(JlfexOrder order : orderList){
				try{
					String result =  jlfexService.queryOrderStatus(order.getOrderCode());
					if(result!=null){
						OrderResponseVo  responVo = JSON.parseObject(result, OrderResponseVo.class);
						List<OrderVo>   orderVoList = responVo.getContent();
						if(orderVoList==null || orderVoList.size() != 1){
							throw new Exception(var+ "根据orderCode="+order.getOrderCode()+" jlfex接口返回订单条数不唯一");
						}
						OrderVo vo = orderVoList.get(0);
						CrediteInfo crediteInfo = creditInfoService.findByCrediteCode(order.getAssetCode());
						Creditor creditor = crediteInfo.getCreditor();
						User  creditUser = userService.loadByAccount(creditor.getCreditorNo());
						//若订单状态为“已回款”+支付状态为“结算成功” 则进行还款业务操作
						if(HermesConstants.CLEARING_SUC.equals(vo.getPayStatus().trim()) && 
						   HermesConstants.ORDER_FINISH_REPAY.equals(vo.getOrderStatus().trim())){
							 //更新理财人收益信息
							List<LoanRepay>  loanRepayList =  loanRepayService.findByLoanAndStatus(order.getInvest().getLoan(), LoanRepay.RepayStatus.WAIT);
							for(LoanRepay loanRepay : loanRepayList){
								BigDecimal amount = BigDecimal.ZERO;		 // 总还款=总本金+总利息
								BigDecimal principal = BigDecimal.ZERO;		 // 总本金
								BigDecimal interest = BigDecimal.ZERO;		 // 总利息
								Loan loan = loanRepay.getLoan();
								List<InvestProfit> investProfitList = investProfitRepository.findByLoanRepay(loanRepay);
								for (InvestProfit investProfit : investProfitList){
									// 还本金
									transactionService.transact(Transaction.Type.OUT, creditUser, investProfit.getUser(), investProfit.getPrincipal(), investProfit.getId(), "jlfex正常还本金");
									principal = principal.add(investProfit.getPrincipal());
									// 还利息
									transactionService.transact(Transaction.Type.OUT, creditUser, investProfit.getUser(), investProfit.getInterest(), investProfit.getId(), "jlfex正常还利息");
									interest = interest.add(investProfit.getInterest());
									//更新理财收益表
									investProfit.setStatus(InvestProfit.Status.ALREADY);
									investProfitRepository.save(investProfit);
								}
							}
							//更新订单信息
						}
					}
				}catch(Exception e){
					Logger.error(var+",异常", e);
					continue;
				}
			}
			return  null;
		} catch (Exception e) {
			throw  new ServiceException(e.getMessage(), e);
		}

	}

}

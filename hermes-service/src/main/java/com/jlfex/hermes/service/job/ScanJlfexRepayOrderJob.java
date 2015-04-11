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
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.yltx.JlfexOrder;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.CreditRepayPlanService;
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
    @Autowired
    private CreditRepayPlanService creditRepayPlanService;
	
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
					if(Strings.notEmpty(result)){
						OrderResponseVo  responVo = JSON.parseObject(result, OrderResponseVo.class);
						List<OrderVo>   orderVoList = responVo.getContent();
						if(orderVoList==null || orderVoList.size() != 1){
							throw new Exception(var+ "根据orderCode="+order.getOrderCode()+" jlfex接口返回订单条数不唯一");
						}
						OrderVo vo = orderVoList.get(0);
						CrediteInfo crediteInfo = creditInfoService.findByCrediteCode(order.getAssetCode());
						Creditor creditor = crediteInfo.getCreditor();
						Invest invest = order.getInvest();
						User  creditUser = userService.loadByAccount(creditor.getCreditorNo());
						//若订单状态为“已回款”+支付状态为“结算成功” 则进行还款业务操作
						if(HermesConstants.CLEARING_SUC.equals(vo.getPayStatus().trim()) && 
						   HermesConstants.ORDER_FINISH_REPAY.equals(vo.getOrderStatus().trim())){
							//更新理财人收益信息
							List<InvestProfit> investProfitList = investProfitRepository.findByInvest(invest);
							for (InvestProfit investProfit : investProfitList){
								if(!investProfit.getStatus().equals(InvestProfit.Status.WAIT)){
									Logger.info("跳过还款处理：状态不对, 当前理财收益investProfitId="+investProfit.getId()+" 状态为="+investProfit.getStatus());
									continue ;
								}
								// 还本金
								transactionService.cropAccountToJlfexPay(Transaction.Type.CHARGE, creditUser, UserAccount.Type.JLFEX_FEE, investProfit.getPrincipal(), investProfit.getId(), "JLfex回购本金充值成功");
								transactionService.transact(Transaction.Type.OUT, creditUser, investProfit.getUser(), investProfit.getPrincipal(), investProfit.getId(), "jlfex正常还本金");
								// 还利息
								transactionService.cropAccountToJlfexPay(Transaction.Type.CHARGE, creditUser, UserAccount.Type.JLFEX_FEE, investProfit.getInterest(), investProfit.getId(), "JLfex回购利息充值成功");
								transactionService.transact(Transaction.Type.OUT, creditUser, investProfit.getUser(), investProfit.getInterest(), investProfit.getId(), "jlfex正常还利息");
								//更新理财收益表
								investProfit.setStatus(InvestProfit.Status.ALREADY);
								investProfitRepository.save(investProfit);
							}
							//更新订单信息
							order.setStatus(JlfexOrder.Status.FIN_DEAL);
							jlfexOrderService.saveOrder(order);
							//更新理财记录
							invest.setStatus(Invest.Status.COMPLETE);
							investService.save(invest);
						}
						//更新债权信息
						crediteInfo.setStatus(CrediteInfo.Status.REPAY_FIINISH);
						creditInfoService.save(crediteInfo);
						//更新债权还款计划
						List<CreditRepayPlan> creditPlanList = creditRepayPlanService.queryByCreditInfo(crediteInfo);
						for(CreditRepayPlan creditPlan: creditPlanList){
							creditPlan.setStatus(CreditRepayPlan.Status.ALREADY_PAY);
						}
						creditRepayPlanService.saveBatch(creditPlanList);
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

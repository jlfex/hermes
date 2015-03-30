package com.jlfex.hermes.service.financePlan;

import java.util.List;

import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.model.yltx.FinanceRepayPlan;

/**
 * 理财产品还款计划 业务
 * @author Administrator
 *
 */
public interface FinanceRepayPlanService {
	
	
	/**
	 * 保存 
	 * @param financeOrder
	 * @return
	 * @throws Exception
	 */
	public FinanceRepayPlan  save(FinanceRepayPlan financeRepayPlan) throws Exception  ;
	/**
	 * 根据理财产品编号  获取理财产品还款计划
	 * @param uniqId
	 * @return
	 * @throws Exception
	 */
	public List<FinanceRepayPlan>  queryByFinanceOrder(FinanceOrder financeOrder) throws Exception ;
	/**
	 * 批量保存
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<FinanceRepayPlan> saveList(List<FinanceRepayPlan> list) throws Exception;
	
	

}

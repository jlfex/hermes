package com.jlfex.hermes.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;




/**
 * 债权 还款明细 
 * @author Administrator
 *
 */
public interface CreditRepayPlanService {

    
	/**
	 * 保存
	 * @param product
	 * @return
	 */
	public List<CreditRepayPlan> saveBatch(List<CreditRepayPlan> list) throws Exception ;
	/**
	 * 还款明细列表 查询
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public Page<CreditRepayPlan> queryByCondition(int page, int size) throws Exception ;
	/**
	 * 获取债权信息下的还款明细
	 * @param creditInfo
	 * @return
	 * @throws Exception
	 */
	public List<CreditRepayPlan> queryByCreditInfo(CrediteInfo creditInfo) throws Exception;
	/**
	 * 计算剩余本金 剩余周期
	 * @param creditInfo
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Object> calculateRemainAmountAndPeriod(CrediteInfo creditInfo,List<CreditRepayPlan> planList) throws Exception;
	
	
	
}

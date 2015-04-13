package com.jlfex.hermes.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;




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
	public CreditRepayPlan save(CreditRepayPlan plan) throws Exception ;
    
	/**
	 * 批量保存
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
	/**
	 * 根据状态 和 债权信息 获取  债权还款明细
	 * @param creditInfo
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<CreditRepayPlan> queryByCreditInfoAndStatus(CrediteInfo creditInfo,String status) throws Exception;
	/**
	 * 根据 截止日期计算 发售金额
	 * @param creditInfo
	 * @param planList
	 * @param bidEndTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> calculateRemainAmount(CrediteInfo creditInfo,List<CreditRepayPlan> planList, String bidEndTime) throws Exception;
	/**
	 * 校验：债权转让 招标截止日期是否合法
	 * 规则：
	 *  1 存在已还款：   当天 < 招标截止日期 < 当期待还款 -2T
	 *  2 不存在已还款：当天 < 招标截止日期 < 招标截止日期   
	 * @param creditInfo
	 * @param planList
	 * @param bidEndTimeStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkBidEndTimeValid(CrediteInfo creditInfo,List<CreditRepayPlan> planList, String bidEndTimeStr) throws Exception;
	/**
	 * 根据债权信息 获取债权还款计划明细
	 * @param creditInfo
	 * @return
	 * @throws Exception
	 */
	public List<CreditRepayPlan> findByCreditInfoAscPeriod(CrediteInfo creditInfo) throws Exception;
	
	public List<CreditRepayPlan> findByCrediteInfo(CrediteInfo crediteInfo) throws Exception;

	
}

package com.jlfex.hermes.service.financePlan;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.model.yltx.FinanceRepayPlan;
import com.jlfex.hermes.repository.finance.FinanceRepayPlanRepository;


/**
 * 理财产品还款计划 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class FinanceRepayPlanServiceImpl implements  FinanceRepayPlanService {

	@Autowired
	private FinanceRepayPlanRepository financeRepayPlanRepository;

	/**
	 * 保存
	 */
	@Override
	public FinanceRepayPlan save(FinanceRepayPlan financeRepayPlan) throws Exception {
		return financeRepayPlanRepository.save(financeRepayPlan);
	}
    /**
     * 批量保存
     */
	@Override
	public List<FinanceRepayPlan> saveList(List<FinanceRepayPlan> list) throws Exception {
		return financeRepayPlanRepository.save(list);
	}
	/**
	 * 根据理财产品编号 获取理财产品还款计划
	 */
	@Override
	public List<FinanceRepayPlan> queryByFinanceOrder(FinanceOrder financeOrder) throws Exception {
		return financeRepayPlanRepository.findByFinanceOrder(financeOrder);
	}

	
	
	
	
	
	

}

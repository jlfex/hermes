package com.jlfex.hermes.repository.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.model.yltx.FinanceRepayPlan;

/**
 * 理财产品 还款计划
 * @author Administrator
 * 
 */
@Repository
public interface FinanceRepayPlanRepository extends JpaRepository<FinanceRepayPlan, String>, JpaSpecificationExecutor<FinanceRepayPlan> {

	/**
	 * 根据理财产品编号 获取理财产品还款计划
	 * @param uniqId
	 * @return
	 */
	public List<FinanceRepayPlan>  findByFinanceOrder(FinanceOrder financeOrder) ;
	
}

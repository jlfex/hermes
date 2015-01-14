package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.repository.CreditorRepayPlanRepository;
import com.jlfex.hermes.service.CreditRepayPlanService;
import com.jlfex.hermes.service.common.Pageables;


/**
 * 债权 信息 管理
 * @author Administrator
 *
 */
@Service
@Transactional

public class CreditRepayPlanServiceImpl  implements CreditRepayPlanService {
	@Autowired
	private CreditorRepayPlanRepository creditorRepayPlanRepository;

	@Override
	public List<CreditRepayPlan> saveBatch(List<CreditRepayPlan> list)throws Exception {
		return creditorRepayPlanRepository.save(list);
	}

	@Override
	public Page<CreditRepayPlan> queryByCondition(int page, int size)throws Exception {
		 Pageable pageable = Pageables.pageable(page, size);
		 return creditorRepayPlanRepository.findAll(null,pageable);
	}

	@Override
	public List<CreditRepayPlan> queryByCreditInfo(CrediteInfo creditInfo)throws Exception {
		List<CreditRepayPlan>  list =creditorRepayPlanRepository.findByCrediteInfo(creditInfo);
		Collections.sort(list, new Comparator<CreditRepayPlan>() {
            public int compare(CreditRepayPlan obj1, CreditRepayPlan obj2) {
            	return (String.valueOf(obj1.getPeriod())).compareTo(String.valueOf(obj2.getPeriod()));
            }
        }); 
		return  list;
	}
	@Override
	public Map<String ,Object> calculateRemainAmountAndPeriod(CrediteInfo creditInfo,List<CreditRepayPlan> planList) throws Exception { 
		Map  map = new HashMap();
		int allPeriod = (planList!=null)?planList.size():0;
	    BigDecimal remainAmount = BigDecimal.ZERO; //剩余本金
	    int  remainPeriod = 0;                     //剩余期数
	    List<CreditRepayPlan> expireList = creditorRepayPlanRepository.findByNowTimeAndCreditInfo(creditInfo);
	    if(expireList==null || expireList.size() == 0){
	    	remainPeriod = allPeriod;
	    	BigDecimal maxAmount = BigDecimal.ZERO;
	    	for(CreditRepayPlan plan : planList){
	    		if(plan!=null && (plan.getRepayPrincipal()!=null)){
	    			if(maxAmount.compareTo(plan.getRepayPrincipal()) != 1 ){
		    			maxAmount = plan.getRepayPrincipal();
		    		}
	    		}
	    	}
	    	remainAmount = remainAmount.add(maxAmount);
	    }else{
	    	int expireSize = expireList.size();
	    	remainPeriod = (allPeriod>expireSize)?(allPeriod-expireSize):0;
	    	BigDecimal maxAmount = BigDecimal.ZERO;
	    	for(CreditRepayPlan plan : expireList){
	    		if(plan!=null && (plan.getRemainPrincipal()!=null)){
	    			if(maxAmount.compareTo(plan.getRemainPrincipal()) != 1 ){
		    			maxAmount = plan.getRemainPrincipal();
		    		}
	    		}
	    	}
	    	remainAmount = remainAmount.add(maxAmount);
	    }
	    map.put("remainPeriod", remainPeriod);
	    map.put("remainAmount", remainAmount);
		return map;
	}

	
	


	
	
	
    
}

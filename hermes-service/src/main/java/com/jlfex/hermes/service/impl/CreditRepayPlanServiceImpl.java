package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.utils.Calendars;
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
            	return (""+obj1.getPeriod()).compareTo(""+obj2.getPeriod());
            }
        }); 
		return  list;
	}
	/**
	 * 转让价格=剩余本金+利息
	 * 剩余本金: 上一个还款日正常还款后剩余本金
	 * 利息: 当期应还利息*（当前日期-上一个还款日）/（当期计划还款日期-上一个还款日)
	 */
	@Override
	public Map<String ,Object> calculateRemainAmountAndPeriod(CrediteInfo creditInfo,List<CreditRepayPlan> planList) throws Exception { 
		Map<String,Object>  map = new HashMap<String,Object>();
		int allPeriod = (planList!=null)?planList.size():0;
		Collections.sort(planList, new Comparator<CreditRepayPlan>() {
	           public int compare(CreditRepayPlan obj1, CreditRepayPlan obj2) {
	            	return (""+obj1.getPeriod()).compareTo(""+obj2.getPeriod());
	           }
	    }); 
	    BigDecimal remainAmount = BigDecimal.ZERO; //剩余本金
	    int  remainPeriod = 0;                     //剩余期数
	    String current_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    Date   endNowDate = Calendars.add(Calendars.parseDate(current_date), "1d");
	    List<CreditRepayPlan> expireList = new ArrayList<CreditRepayPlan>(); 
	    for(CreditRepayPlan obj :planList){
	    	if(obj!=null){
	    		if(obj.getRepayPlanTime().before(endNowDate)){
	    			expireList.add(obj);
	    		}
	    	}
	    }
	    if(expireList.size() == 0){
	    	remainPeriod = allPeriod;
	    	CreditRepayPlan firstRepayPlan = planList.get(0);
	    	remainAmount = remainAmount.add(firstRepayPlan.getRemainPrincipal()).add(firstRepayPlan.getRepayPrincipal());
	    }else{
	    	int expireSize = expireList.size();
	    	remainPeriod = (allPeriod>expireSize)?(allPeriod-expireSize):0;
	    	BigDecimal maxAmount = BigDecimal.ZERO;
	    	for(CreditRepayPlan plan : expireList){
	    		if(plan!=null && (plan.getRemainPrincipal()!=null) && remainPeriod> 0){
	    			if(maxAmount.compareTo(plan.getRemainPrincipal()) != 1 ){
		    			maxAmount = plan.getRemainPrincipal();
		    		}
	    		}
	    	}
	    	remainAmount = remainAmount.add(maxAmount);
	    	//转让价格=剩余本金+利息
	    	BigDecimal remainInterest = BigDecimal.ZERO; //剩余本金
	    	if(remainPeriod > 0){
	    		CreditRepayPlan expirePayPlan = planList.get(expireSize-1);
	    		CreditRepayPlan waitPayPlan = planList.get(expireSize);
	    		Date  nowDate = new Date();
	    	    Date  lastExpireDate = expirePayPlan.getRepayPlanTime();
	    		Date  waitPayDate =   waitPayPlan.getRepayPlanTime();
	    		if(nowDate.after(lastExpireDate) && nowDate.before(waitPayDate)){
	    			int overDays =  Calendars.daysBetween(lastExpireDate, nowDate);
		    		int allDays =  Calendars.daysBetween(lastExpireDate, waitPayDate);
		    		BigDecimal over_day = new BigDecimal(""+overDays);
		    		BigDecimal all_day = new BigDecimal(""+allDays);
		    		BigDecimal percent = over_day.divide(all_day, 2, RoundingMode.UP);
		    		remainInterest =waitPayPlan.getRepayInterest().multiply(percent);
	    		}
	    	}
	    	remainAmount = remainAmount.add(remainInterest);
	    }
	    map.put("remainPeriod", remainPeriod); //剩余期数
	    map.put("remainAmount", remainAmount); //剩余金额
		return map;
	}

	@Override
	public List<CreditRepayPlan> queryByCreditInfoAndStatus(CrediteInfo creditInfo,String status) throws Exception {
		return  creditorRepayPlanRepository.findByCrediteInfoAndStatus(creditInfo, status);
	}
    
}

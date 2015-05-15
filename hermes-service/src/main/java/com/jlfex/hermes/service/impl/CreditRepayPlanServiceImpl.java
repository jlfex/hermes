package com.jlfex.hermes.service.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	public CreditRepayPlan save(CreditRepayPlan plan) throws Exception {
		return creditorRepayPlanRepository.saveAndFlush(plan);
	}
	
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
            	return obj1.getPeriod() > obj2.getPeriod()? 1:-1;
            }
        }); 
		return  list;
	}
	/**
	 * 转让价格=剩余本金+利息
	 */
	@Override
	public Map<String ,Object> calculateRemainAmountAndPeriod(CrediteInfo creditInfo,List<CreditRepayPlan> planList) throws Exception { 
		Map<String,Object>  map = new HashMap<String,Object>();
		int allPeriod = (planList!=null)?planList.size():0;
		int  remainDays = 0;                       //剩余天数
		int  remainPeriod = 0;                     //剩余期数
		BigDecimal remainAmount = BigDecimal.ZERO; //剩余本金
		if(planList == null || planList.size() == 0){
			map.put("remainPeriod", 0); //剩余期数
		    map.put("remainAmount", 0); //剩余金额
		    map.put("remainDays", 0);   //剩余期限 天
			return map;
		}
		Collections.sort(planList, new Comparator<CreditRepayPlan>() {
	           public int compare(CreditRepayPlan obj1, CreditRepayPlan obj2) {
	            	return obj1.getPeriod()> obj2.getPeriod()? 1:-1;
	           }
	    }); 
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
	    	//转让价格=剩余本金+利息
	    	BigDecimal remainInterest = BigDecimal.ZERO; //剩余本金
	    	if(remainPeriod > 0){
	    		CreditRepayPlan expirePayPlan = planList.get(expireSize-1);
	    		CreditRepayPlan waitPayPlan = planList.get(expireSize);
	    		remainAmount = expirePayPlan.getRemainPrincipal();
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
	    if(remainPeriod>0 ){
	    	if(creditInfo.getBidEndTime() != null){
	    		remainDays = Calendars.daysBetween(new Date(), creditInfo.getBidEndTime());
	    	}
	    }
	    map.put("remainPeriod", remainPeriod); //剩余期数 月
	    map.put("remainDays", remainDays);    //剩余期限 天
	    map.put("remainAmount", remainAmount); //剩余金额
		return map;
	}

	@Override
	public List<CreditRepayPlan> queryByCreditInfoAndStatus(CrediteInfo creditInfo,String status) throws Exception {
		return  creditorRepayPlanRepository.findByCrediteInfoAndStatus(creditInfo, status);
	}
    /**
     * 根据 截止日期计算 发售金额
     * 转让价格=剩余本金+利息
	 * 剩余本金: 上一个还款日正常还款后剩余本金
	 * 利息: 当期应还利息 *（招标截止日期-上一个还款日）/（当期计划还款日期-上一个还款日)
     */
	@Override
	public Map<String ,Object> calculateRemainAmount(CrediteInfo creditInfo,List<CreditRepayPlan> planList,String bidEndTimeStr) throws Exception { 
		Map<String,Object>  map = new HashMap<String,Object>();
		if(planList == null || planList.size() == 0){
		    map.put("remainAmount", 0); //剩余金额
			return map;
		}
		int allPeriod = (planList!=null)?planList.size():0;
		int  remainPeriod = 0;                     //剩余期数
		BigDecimal remainAmount = BigDecimal.ZERO; //剩余本金
		Collections.sort(planList, new Comparator<CreditRepayPlan>() {
	           public int compare(CreditRepayPlan obj1, CreditRepayPlan obj2) {
	            	return obj1.getPeriod()> obj2.getPeriod()? 1:-1;
	           }
	    }); 
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
	    	//转让价格=剩余本金+利息
	    	BigDecimal remainInterest = BigDecimal.ZERO; //剩余本金
	    	if(remainPeriod > 0){
	    		CreditRepayPlan expirePayPlan = planList.get(expireSize-1);
	    		CreditRepayPlan waitPayPlan = planList.get(expireSize);
	    		remainAmount = expirePayPlan.getRemainPrincipal();
	    		Date  bidEndTime = Calendars.parse("yyyy-MM-dd", bidEndTimeStr);
	    	    Date  lastExpireDate = expirePayPlan.getRepayPlanTime();
	    		Date  waitPayDate =   waitPayPlan.getRepayPlanTime();
	    		if(bidEndTime.after(lastExpireDate) && bidEndTime.before(waitPayDate)){
	    			int overDays =  Calendars.daysBetween(lastExpireDate, bidEndTime);
		    		int allDays =  Calendars.daysBetween(lastExpireDate, waitPayDate);
		    		BigDecimal over_day = new BigDecimal(""+overDays);
		    		BigDecimal all_day = new BigDecimal(""+allDays);
		    		BigDecimal percent = over_day.divide(all_day, 5, RoundingMode.HALF_EVEN);
		    		remainInterest =waitPayPlan.getRepayInterest().multiply(percent);
	    		}
	    	}
	    	remainAmount = remainAmount.add(remainInterest).setScale(2, RoundingMode.HALF_EVEN);
	    }
	    map.put("remainAmount", remainAmount); //剩余金额
		return map;
	}
	
    /**
     * 校验：债权转让 招标截止日期是否合法
	 * 规则：
	 *  1 存在已还款：   当天 < 招标截止日期 < 当期待还款 -2T
	 *  2 不存在已还款：当天 < 招标截止日期 < 招标截止日期   
     */
	@Override
	public Map<String, String> checkBidEndTimeValid(CrediteInfo creditInfo,List<CreditRepayPlan> planList,String bidEndTimeStr) throws Exception { 
		Map<String,String>  map = new HashMap<String,String>();
		String code = "", msg = "";
		if(planList == null || planList.size() == 0){
			return null;
		}
		int allPeriod = (planList!=null)?planList.size():0;
		int  remainPeriod = 0; //剩余期数
		Collections.sort(planList, new Comparator<CreditRepayPlan>() {
	           public int compare(CreditRepayPlan obj1, CreditRepayPlan obj2) {
	            	return obj1.getPeriod()> obj2.getPeriod()? 1:-1;
	           }
	    }); 
	    String current_date =  Calendars.format("yyyy-MM-dd", new Date());
	    Date   endNowDate = Calendars.add(Calendars.parseDate(current_date), "1d");
	    List<CreditRepayPlan> expireList = new ArrayList<CreditRepayPlan>(); 
	    for(CreditRepayPlan obj :planList){
	    	if(obj!=null){
	    		if(obj.getRepayPlanTime().before(endNowDate)){
	    			expireList.add(obj);
	    		}
	    	}
	    }
	    Date  bidEndTime = Calendars.parse("yyyy-MM-dd", bidEndTimeStr);
	    if(expireList.size() == 0){
	    	  //没有过期：招标结束日期要小于 债权到期日
	    	  if(bidEndTime.before(creditInfo.getDeadTime())){
	    		  code = "00";
	    	  }else{
	    		  code = "99";
	    		  msg = "当天("+current_date+")< 招标结束日期("+bidEndTimeStr+")<债权到期日("+Calendars.format("yyyy-MM-dd", bidEndTime)+")";
	    	  }
	    }else{
	    	// 有过期：当天 < 招标结束日期要< 当期待还款日期-2T
	    	int expireSize = expireList.size();
	    	remainPeriod = (allPeriod>expireSize)?(allPeriod-expireSize):0;
	    	if(remainPeriod > 0){
	    		CreditRepayPlan waitPayPlan = planList.get(expireSize);
	    		Date  waitPayDate_2 = null;
	    		Date  waitPayDate =  waitPayPlan.getRepayPlanTime();
	    		Calendar calendar = Calendar.getInstance();
	    		calendar.setTime(waitPayDate);
	    		calendar.add(Calendar.DATE, -2);
	    		waitPayDate_2 = Calendars.parse("yyyy-MM-dd", Calendars.format("yyyy-MM-dd", calendar.getTime()));
	    	    Date  nowDate = Calendars.parseDate(current_date);
	    		if(bidEndTime.after(nowDate) && bidEndTime.before(waitPayDate_2)){
	    			  code = "00";
	    		}else{
	    			 code = "99";
		    		 msg = "当天("+current_date+") < 招标结束日期 <当期待还款日期-2T["+Calendars.format("yyyy-MM-dd", waitPayDate_2)+"]";
	    		}
	    	}else{
	    		 code = "99";
	    		 msg = "无法转让,不存在有效的还款计划";
	    	}
	    }
	    map.put("code", code);
	    map.put("msg",  msg);
	    return map;
	}
    /**
     * 根据债权信息 获取债权还款计划明细
     */
	@Override
	public List<CreditRepayPlan> findByCreditInfoAscPeriod(CrediteInfo creditInfo) throws Exception{
		return creditorRepayPlanRepository.findByCreditInfoAscPeriod(creditInfo);
	}

	@Override
	public List<CreditRepayPlan> findByCrediteInfo(CrediteInfo crediteInfo) throws Exception {
		// TODO Auto-generated method stub
		return creditorRepayPlanRepository.findByCreditInfo(crediteInfo);
	}
}

package com.jlfex.hermes.console.credit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.service.CreditorService;

@Controller
@RequestMapping("/credit")
public class CreditController {

	private static String today;
	private final static String CACHE_CREDITOR_SEQUENCE = "com.jlfex.cache.creditorsequence";
	
	@Autowired
	private CreditorService creditorService;


	/**
	 * 债权人 列表
	 * @returno
	 */
	@RequestMapping("/index")
	public String index( Model model) {
		return "credit/index";
	}
	
	@RequestMapping("/goAdd")
	public String addCredit(Model model) {
		model.addAttribute("creditorNo", generateLoanNo());
		return "credit/creditAdd";
	}
	
	@RequestMapping("/list")
	public String loandata(String creditorName, String cellphone, String page,String size, Model model) {
		model.addAttribute("lists", creditorService.findCreditorList(creditorName, cellphone, page, size) );
		return "credit/data";
	}
	/**
	 * 债权人 新增
	 * @param creditor
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String addCredit(Creditor creditor, Model model) {
		try{
			if(creditor !=null){
			   creditorService.save(creditor);
			}
		}catch(Exception e){
			Logger.error("债权人 新增异常：",e);
		}
		return "credit/index";
	}
	/**
	 * 查询明细 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		Creditor creditor = new Creditor() ;
		try{
			creditor = creditorService.loadById(id);
		}catch(Exception e){
			Logger.error("债权人 新增异常：",e);
		}
		model.addAttribute("creditor",creditor);
		return "credit/creditAdd";
	}
	
   
	/**
	 * 生成债权人编号
	 * @return
	 */
	public synchronized String generateLoanNo() {
		String date = Calendars.format("yyyyMMdd");
		// 判断缓存序列是否存在 若不存在则初始化
		if (Caches.get(CACHE_CREDITOR_SEQUENCE) == null || today == null) {
			List<Creditor> creditorList;
			try {
				creditorList = creditorService.findAllByCredtorNo();
			} catch (Exception e) {
				creditorList = null;
			}
			String maxCreditNo = null;
			if (creditorList != null && creditorList.size() > 0) {
				maxCreditNo = creditorList.get(0).getCreditorNo();
			}
			if (maxCreditNo != null && maxCreditNo.length() == 12) {
				today = maxCreditNo.substring(0, 8);
				maxCreditNo = maxCreditNo.substring(8);
				Caches.set(CACHE_CREDITOR_SEQUENCE, Long.valueOf(maxCreditNo));
				Logger.info("set maxCreditNo sequence '{}' from database!", Long.valueOf(maxCreditNo));
			}
		}
		// 若未匹配则重置序列编号  判断日期是否与当前日期匹配
		if (!date.equals(today)) {
			today = date;
			Caches.set(CACHE_CREDITOR_SEQUENCE, 0);
			Logger.info("charge date to '{}', recount sequence!", date);
		}
		// 递增缓存数据
		Long seq = Caches.incr(CACHE_CREDITOR_SEQUENCE, 1);
		return String.format("ZQ%s%04d", today, seq);
	}
	


}

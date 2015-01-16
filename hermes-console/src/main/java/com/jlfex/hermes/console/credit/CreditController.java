package com.jlfex.hermes.console.credit;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.console.pojo.CreditInfoVo;
import com.jlfex.hermes.console.pojo.RepayPlanVo;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.CreditRepayPlanService;
import com.jlfex.hermes.service.CreditorService;

@Controller
@RequestMapping("/credit")
public class CreditController {

	private static String today;
	private final static String CACHE_CREDITOR_SEQUENCE = "com.jlfex.cache.creditorsequence";
	
	@Autowired
	private CreditorService creditorService;
	@Autowired
	private CreditInfoService creditInfoService;
	@Autowired
	private CreditRepayPlanService creditRepayPlanService;


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
		return "credit/add";
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
			   creditor.propTrim();
			   creditorService.save(creditor);
			   if(Caches.get(CACHE_CREDITOR_SEQUENCE) != null ){
				   Caches.set(CACHE_CREDITOR_SEQUENCE, null);
			   }
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
		return "credit/add";
	}
	
   
	/**
	 * 生成债权人编号
	 * @return
	 */
	public synchronized String generateLoanNo() {
		String date = Calendars.format("yyyyMMdd");
		// 判断缓存序列是否存在 若不存在则初始化
		if (Caches.get(CACHE_CREDITOR_SEQUENCE) == null || today == null) {
			Creditor creditor;
			try {
				creditor = creditorService.findMaxCredtorNo();
			} catch (Exception e) {
				Logger.error("生成债权人编号异常:", e);
				creditor = null;
			}
			String maxCreditNo = null;
			if (creditor != null) {
				maxCreditNo = creditor.getCreditorNo();
			}
			if (maxCreditNo != null && maxCreditNo.length() == 14) {
				today = maxCreditNo.substring(2, 10);
				maxCreditNo = maxCreditNo.substring(10);
				Caches.set(CACHE_CREDITOR_SEQUENCE, Long.valueOf(maxCreditNo));
				Logger.info("设置最大债权人编号："+Long.valueOf(maxCreditNo));
			}
		}
		// 若未匹配则重置序列编号  判断日期是否与当前日期匹配
		if (!date.equals(today)) {
			today = date;
			Caches.set(CACHE_CREDITOR_SEQUENCE, 0);
		}
		Long seq = Caches.incr(CACHE_CREDITOR_SEQUENCE, 1);// 递增缓存数据
		return String.format("ZQ%s%04d", today, seq);
	}
	/**
	 * 债权导入  列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/assign")
	public String assign(String page,String size, Model model ) {
		try {
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(null,page,size) ;
			model.addAttribute("infoList", obj);
		} catch (Exception e) {
			Logger.error("债权导入列表查询异常:", e);
		}
		return "credit/assign";
	}
	/**
	 * 债权导入 文件解析
	 * @param model
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public Map<String,String> creditImport(@RequestParam MultipartFile file, Model model) {
		 String msg = "", code="";
		 Map<String,String>  resultMap = new HashMap<String,String>();
		 String fileName = file.getOriginalFilename(); 
		 Map<String,Object> sheetMap= null;
		 try {
			 FileInputStream fileInputStream= (FileInputStream) file.getInputStream(); 
			 sheetMap = ExcelUtil.analysisExcel(fileName, fileInputStream);
			 List<CreditInfoVo>  creditList = (List<CreditInfoVo>) sheetMap.get("creditList");
			 List<RepayPlanVo>   repayList = (List<RepayPlanVo>) sheetMap.get("repayList");
			 //
			 List<CrediteInfo>   entityCreditList= new ArrayList<CrediteInfo>();
			 List<CreditRepayPlan>     entitLoanPayList = new ArrayList<CreditRepayPlan>();
			 for(CreditInfoVo vo : creditList){
				 CrediteInfo crediteInfo = new CrediteInfo();
				 entityCreditList.add(initCreditInfoEntity(vo, crediteInfo));
			 }
			 List<CrediteInfo> savedCreditInfolist = creditInfoService.saveBatch(entityCreditList);
			 if(savedCreditInfolist == null || savedCreditInfolist.size() == 0){
				 throw new Exception("债权信息保存失败");
			 }
			 CrediteInfo load_crediteInfo = savedCreditInfolist.get(0);
			 Creditor    load_creditor = savedCreditInfolist.get(0).getCreditor();
			 for(RepayPlanVo vo : repayList){
				 CreditRepayPlan repayPlan = new CreditRepayPlan();
				 entitLoanPayList.add(initCreditRepayPlanEntity(vo, repayPlan,load_creditor, load_crediteInfo));
			 }
			 List<CreditRepayPlan> creditRepayPlanList =  creditRepayPlanService.saveBatch(entitLoanPayList);
			 if(creditRepayPlanList == null || creditRepayPlanList.size() == 0){
				 Logger.info("还款计划表保存失败");
			 }
			 code = "00";
			 msg = "成功";
		} catch (Exception e) {
			 String var = "债权导入 文件解析异常";
			 Logger.error(var, e);
			 code = "99";
			 msg = e.getMessage();
		}
	    resultMap.put("code", code);
		resultMap.put("msg",  msg);
		return resultMap;
	}
	/**
	 * 债权信息 实体 初始化
	 * @param vo
	 * @param entity
	 * @return
	 */
	public CrediteInfo  initCreditInfoEntity(CreditInfoVo vo, CrediteInfo entity){
		if(vo!=null){
			entity.setCreditor(creditorService.findByCredtorNo(vo.getCreditorNo()));
			entity.setCrediteCode(vo.getCreditCode());
			entity.setCrediteType(vo.getCreditKind());
			entity.setBorrower(vo.getBorrower());
			entity.setCertType(vo.getCertType());
			entity.setCertificateNo(vo.getCertificateNo());
			entity.setWorkType(vo.getWorkType());
			entity.setProvince(vo.getProvince());
			entity.setCity(vo.getCity());
			entity.setAmount(vo.getAmount());
			entity.setRate(vo.getRate());
			entity.setPeriod(Integer.parseInt(Strings.empty(vo.getPeriod(), "0")));
			entity.setPurpose(vo.getPurpose());
			entity.setPayType(vo.getPayType());
			entity.setDeadTime(vo.getDeadTime());
			entity.setBusinessTime(vo.getBusinessTime());
			entity.setStatus(CrediteInfo.Status.WAIT_ASSIGN);
		}
	    return entity;
	}
	
	/**
	 * 债权还款计划明细 实体 初始化
	 * @param vo
	 * @param entity
	 * @return
	 */
	public CreditRepayPlan  initCreditRepayPlanEntity(RepayPlanVo vo, CreditRepayPlan entity,Creditor creditor, CrediteInfo creditInfo){
		if(vo!=null){
			entity.setCreditor(creditor);
			entity.setCrediteInfo(creditInfo); 
			entity.setPeriod(vo.getPeriod());
			entity.setRepayTime(vo.getRepayTime());
			entity.setRepayPrincipal(vo.getRepayPrincipal());
			entity.setRepayInterest(vo.getRepayInterest());
			entity.setRepayAllmount(vo.getRepayAllmount());
			entity.setRemainPrincipal(vo.getRemainPrincipal());
			entity.setStatus(CreditRepayPlan.Status.WAIT_PAY);
			entity.setRemark(vo.getRemark());
		}
	    return entity;
	}
	/**
	 * 查询 债权还款计划明细
	 * @param page
	 * @param size
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/repayPlanDetail/{id}")
	public String repayPlanDetail( @PathVariable("id") String id, Model model){
		try {
			CrediteInfo creditInfo = creditInfoService.findById(id);
			List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
			Map<String, Object> calculatedMap =  creditRepayPlanService.calculateRemainAmountAndPeriod(creditInfo, planList);
			model.addAttribute("creditInfo", creditInfo); //债权信息
			model.addAttribute("repayPlanDetailList",planList); //还款明细
			model.addAttribute("remainAmount", calculatedMap.get("remainAmount")); //剩余本金
			model.addAttribute("remainPeriod", calculatedMap.get("remainPeriod")); //剩余期数
		} catch (Exception e) {
			Logger.error("债权还款计划明细异常：", e);
		}
		return "credit/repayDetail";
	}
	/**
	 * 进行发售操作
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSell/{id}")
	public String goSellCredit( @PathVariable("id") String id, Model model){
		try {
			CrediteInfo creditInfo = creditInfoService.findById(id);
			List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
			Map<String, Object> calculatedMap =  creditRepayPlanService.calculateRemainAmountAndPeriod(creditInfo, planList);
			model.addAttribute("creditInfo", creditInfo);
			model.addAttribute("remainAmount", calculatedMap.get("remainAmount")); //剩余本金
		} catch (Exception e) {
			Logger.error("查询债权信息异常：id="+id, e);
		}
		return "credit/sell";
	}
	/**
	 * 发售列表
	 * @param creditInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/sellIndex")
	public String sellIndex(CrediteInfo creditInfo, String page,String size, Model model){
		try {
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(creditInfo,page,size) ;
			model.addAttribute("sellList", obj);
		} catch (Exception e) {
			Logger.error("债权导入列表查询异常:", e);
		}
		return "credit/sellList";
	}
	/**
	 * 发售 债权
	 * @param creditInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/sell")
	public String sellCredit(CrediteInfo creditInfo, Model model){
		try {
			if(creditInfo == null || Strings.empty(creditInfo.getId())){
				model.addAttribute("creditInfo", creditInfo);
				model.addAttribute("errMsg", "债权信息为空");
			}else{
				CrediteInfo entity = creditInfoService.findById(creditInfo.getId());
				if(!Strings.empty(creditInfo.getPurpose())){
					entity.setPurpose(creditInfo.getPurpose());
				}
				if(!Strings.empty(creditInfo.getBidEndTimeStr())){
					entity.setBidEndTimeStr(creditInfo.getBidEndTimeStr());
				}
				if(creditInfo.getPeriod() > 0){
					entity.setPeriod(creditInfo.getPeriod());
				}
				if(creditInfo.getAmount().compareTo(BigDecimal.ZERO) != 1){
					 throw new Exception("发售金额必须大于0");
				}else{
					entity.setAmount(creditInfo.getAmount());
				}
				if(!Strings.empty(creditInfo.getAssureType())){
					entity.setAssureType(creditInfo.getAssureType());
				}
				if(!Strings.empty(creditInfo.getAmountAim())){
					entity.setAmountAim(creditInfo.getAmountAim());
				}
				if(creditInfoService.sellCredit(entity)){
					Logger.info("发售债权人"+entity.getCreditor().getCreditorNo()+"，债权编号:"+entity.getCertificateNo()+",发售成功");
				}
			}
		} catch (Exception e) {
			Logger.error("发售债权异常:", e);
		}
		return "redirect:/credit/sellIndex";
	}
	
	
	
	
	
	
	
	

}

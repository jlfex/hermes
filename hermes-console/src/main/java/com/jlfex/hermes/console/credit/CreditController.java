package com.jlfex.hermes.console.credit;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.RequestUtils;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.console.pojo.CreditInfoVo;
import com.jlfex.hermes.console.pojo.RepayPlanVo;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.CreditRepayPlanService;
import com.jlfex.hermes.service.CreditorService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.UserService;

@Controller
@RequestMapping("/credit")
public class CreditController {

	@Autowired
	private CreditorService creditorService;
	@Autowired
	private CreditInfoService creditInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private CreditRepayPlanService creditRepayPlanService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private UserService  userService;
	
	

	public static final String FLAG_KIND_NINE = "99";
	public static final String FLAG_KIND_SUC = "00";
	public static final String FLAG_KIND_ONE = "01";
	public static final String VAR_GAP = "|";

	/**
	 * 债权人 列表
	 * 
	 * @returno
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "credit/index";
	}
    /**
    * 新增债权人1
    * @param model
    * @return
    */
	@RequestMapping("/goAdd")
	public String addCredit(Model model) {
		String uniqueueCode = "";
		try {
			uniqueueCode = creditorService.generateCreditorNo();
		} catch (Exception e) {
			Logger.error("生成债权人唯一编号异常：", e);
		}
		model.addAttribute("creditorNo", uniqueueCode);
		return "credit/add";
	}
    /**
     * 债权人列表
     * @param creditorName
     * @param cellphone
     * @param page
     * @param size
     * @param model
     * @return
     */
	@RequestMapping("/list")
	public String loandata(String creditorName, String cellphone, String page, String size, Model model) {
		model.addAttribute("lists", creditorService.findCreditorList(creditorName, cellphone, page, size));
		return "credit/data";
	}

	/**
	 * 债权人 新增2
	 * @param creditor
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String addCredit(Creditor creditor, Model model) {
		try {
			if (creditor != null) {
				creditor.propTrim();
				if (Strings.empty(creditor.getCreditorNo())) {
					return "credit/goAdd";
				}
				creditor.setOriginNo(creditor.getCreditorNo());
				creditorService.save(creditor);
			}
		} catch (Exception e) {
			Logger.error("债权人 新增异常：", e);
		}
		return "credit/index";
	}

	/**
	 * 查询明细
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		
		Creditor creditor = new Creditor();
		try {
			 creditor = creditorService.loadById(id);
			 List<CrediteInfo> lists = creditInfoService.findByCreditor(creditor);
			 if(lists!= null && lists.size() >0){
				 if(CrediteInfo.CreditKind.YLTX_API.equals(lists.get(0).getCreditKind())){
					model.addAttribute("creditorKind", CrediteInfo.CreditKind.YLTX_API);
				 }
			 }
		} catch (Exception e) {
			Logger.error("债权人 新增异常：", e);
		}
		model.addAttribute("creditor", creditor);
		return "credit/add";
	}



	/**
	 * 债权导入 列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/importLoan")
	public String assign(Model model) {
		return "credit/importLoan";
	}

	/**
	 * 债权导入 加载列表
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/loandata")
	public String loandata(CrediteInfo info,String page, Model model) {
		try {
			String size = "10";
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(info, page, size, null);
			model.addAttribute("infoList", obj);
		} catch (Exception e) {
			Logger.error("债权导入列表查询异常:", e);
			model.addAttribute("infoList", null);
		}
		return "credit/importLoandata";
	}

	/**
	 * 债权导入 文件解析
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/import")
	@ResponseBody
	public Map<String, String> creditImport(@RequestParam MultipartFile file, Model model) {
		String msg = "", code = "";
		int sheet1AllNum = 0, sheet1SucNum = 0;
		int sheet2AllNum = 0, sheet2SucNum = 0;
		Map<String, String> resultMap = new HashMap<String, String>();
		String fileName = file.getOriginalFilename();
		Map<String,String> sheet1Map = new  HashMap<String,String>();
		Map<String, Object> sheetMap = null;
		Logger.info(fileName+"：文件导入解析 开始");
		List<CreditInfoVo> invalidList = null;
		List<CrediteInfo> savedCreditInfolist = null;
		StringBuilder  repayPlanErrorMsg = new StringBuilder();
		try {
			// 1: 解析Excel 到 数据模型
			FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
			sheetMap = CreditExcelUtil.analysisExcel(fileName, fileInputStream);
			List<CreditInfoVo> creditList = (List<CreditInfoVo>) sheetMap.get("creditList"); //债权信息
			List<RepayPlanVo> repayList = (List<RepayPlanVo>) sheetMap.get("repayList");     //还款明细
			// 1.1 统计总数
			sheet1AllNum = creditList != null ? creditList.size() : 0;
			sheet2AllNum = repayList != null ? repayList.size() : 0;
			// 1.2: 业务规则校验
			Map<String, Object> creditResult = checkCreditValid(creditList,fileName); //校验结果
			if (FLAG_KIND_NINE.equals(creditResult.get("code"))) {
				throw new Exception("" + creditResult.get("msg"));
			}else{
				creditList.clear();
				List<CreditInfoVo> validList = (List<CreditInfoVo>) creditResult.get("validList");
				invalidList = (List<CreditInfoVo>) creditResult.get("invalidList");
				creditList.addAll(validList);
			}
			// 2:持久化:债权信息
			List<CrediteInfo> entityCreditList = new ArrayList<CrediteInfo>();
			for (CreditInfoVo vo : creditList) {
				CrediteInfo crediteInfo = new CrediteInfo();
				entityCreditList.add(initCreditInfoEntity(vo, crediteInfo));
			}
			try {
				//2.1统计 校验通过 数据
				savedCreditInfolist = creditInfoService.saveBatch(entityCreditList);
			} catch (Exception e) {
				Logger.error(fileName+"：债权信息保存失败",e);
			}
			if (savedCreditInfolist == null || savedCreditInfolist.size() == 0) {
				sheet1SucNum = 0;
				throw new Exception("债权信息保存失败");
			}else{
				sheet1SucNum = savedCreditInfolist.size();
				for (CrediteInfo obj : savedCreditInfolist) {
					if(CrediteInfo.Status.WAIT_ASSIGN.equals(obj.getStatus() ) ){
						sheet1Map.put(obj.getCreditor().getCreditorNo()+VAR_GAP+obj.getCrediteCode(), obj.getId()+VAR_GAP+obj.getPeriod());
					}
				}
			}
			//3.0 循环获取 CreditsheetMap
			List<RepayPlanVo> validRepayplanList = fetchRepayPlanRecord(sheet1Map, fileName, repayList, repayPlanErrorMsg);
			if(validRepayplanList != null && validRepayplanList.size() > 0){
				// 3.1:持久化:债权还款明细
				List<CreditRepayPlan> entitLoanPayList = new ArrayList<CreditRepayPlan>();
				for (RepayPlanVo vo : validRepayplanList) {
					CreditRepayPlan repayPlan = new CreditRepayPlan();
					entitLoanPayList.add(initCreditRepayPlanEntity(vo, repayPlan));
				}
				List<CreditRepayPlan> creditRepayPlanList = creditRepayPlanService.saveBatch(entitLoanPayList);
				//如果债权还款明细都过期  更新债权状态已过期
				for(CreditRepayPlan plan :creditRepayPlanList){
					 if(plan == null){
						 continue;
					 }
					 CrediteInfo obj = plan.getCrediteInfo();
					 List<CreditRepayPlan> list = creditRepayPlanService.queryByCreditInfoAndStatus(obj, CreditRepayPlan.Status.WAIT_PAY);
					 if(list == null || list.size() ==0){
						 obj.setStatus(CrediteInfo.Status.FAIL_ASSIGNING);
						 creditInfoService.save(obj);
					 }
				}
				if (creditRepayPlanList == null || creditRepayPlanList.size() == 0) {
					sheet2SucNum = 0;
					Logger.info(fileName+"：还款计划表保存失败");
				}else{
					sheet2SucNum = creditRepayPlanList.size();
				}
			}else{
				Logger.info(fileName+"：没有符合条件的 可保存的明细");
			}
			code = FLAG_KIND_SUC;
			msg = "成功";
			Logger.info(fileName+"：导入解析结束");
		} catch (Exception e) {
			String var = "债权导入 文件解析异常";
			Logger.error(var, e);
			code = FLAG_KIND_NINE;
			msg = e.getMessage();
			Logger.info(fileName+"：导入解析异常结束");
		}
		StringBuilder  checkErrorMsg = new StringBuilder();
		if(invalidList != null && invalidList.size() > 0){
			for(CreditInfoVo obj :invalidList){
				if(obj != null && CreditInfoVo.Status.INVALID.equals(obj.getStatus())){
					checkErrorMsg.append(obj.getRemark()).append("<br/>");
				}
			}
		}
		checkErrorMsg.append(repayPlanErrorMsg);
		resultMap.put("sheet1AllNum", String.valueOf(sheet1AllNum));
		resultMap.put("sheet1SucNum", String.valueOf(sheet1SucNum));
		resultMap.put("sheet1ErrNum", String.valueOf(sheet1AllNum - sheet1SucNum));
		resultMap.put("sheet2AllNum", String.valueOf(sheet2AllNum));
		resultMap.put("sheet2SucNum", String.valueOf(sheet2SucNum));
		resultMap.put("sheet2ErrNum", String.valueOf(sheet2AllNum - sheet2SucNum));
		resultMap.put("fileName", fileName);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		resultMap.put("checkErrorMsg", checkErrorMsg.toString());
		return resultMap;
	}
	//循环获取 CreditsheetMap
	public  List<RepayPlanVo> fetchRepayPlanRecord(Map<String,String> sheet1Map,String fileName,List<RepayPlanVo> repayList,StringBuilder errMsg) throws Exception{
		List<RepayPlanVo>  validRepayplanList = new ArrayList<RepayPlanVo>();
		for(Map.Entry<String, String> entry: sheet1Map.entrySet()) {
			 String  creditUniqueKey = entry.getKey();
			 String  creditUniqueVal = entry.getValue();
			 String  crditId = null;
			 int  period = 0;
			 if(!Strings.empty(creditUniqueVal)){
				 String[] array = creditUniqueVal.split("\\|");
				 crditId  = array[0];
				 period = Integer.parseInt(array[1]);
				 Logger.info(fileName+"：开始提取"+creditUniqueKey+"所属明细：根据债权期限："+period+",应该有"+period+"条还款明细");
			 }else{
				 Logger.warn(fileName+"：creditUniqueKey="+creditUniqueKey+",对应的值"+creditUniqueVal+"为空");
				 continue;
			 }
			 // 提取对应的 还款明细
			 List<RepayPlanVo>  belongRepayplanList = new ArrayList<RepayPlanVo>();
			 int i = 0;
			 for(RepayPlanVo vo : repayList){
				i++;
				if(vo != null){
					String originalKey = vo.getCreditorNo().trim()+VAR_GAP+vo.getCreditCode().trim();
					if(creditUniqueKey.equals(originalKey)){
						vo.setCreditId(crditId); //设置明细所属的 债权信息ID
						belongRepayplanList.add(vo);
					}else{
						Logger.info(fileName+"：第二个sheet:第"+i+"行，出现不连续的对应关系"+creditUniqueKey+",已找到"+(i-1)+"条明细,停止搜索");
						continue;
					}
				}
			 }
			 //期数一致 才保存
			 if(belongRepayplanList!= null && belongRepayplanList.size() == period ){
				 validRepayplanList.addAll(belongRepayplanList);
			 }else{
				 String[] array = creditUniqueKey.split("\\|");
				 String err = "还款明细期数错误：债权期限="+period+",还款明细期数="+belongRepayplanList.size();
				 if(array!=null && array.length == 2){
					 err = "还款明细期数错误：债权人编号："+array[0]+"债权编号"+array[1]+"的债权期限="+period+",还款明细期数="+belongRepayplanList.size()+"<br/>";
				 }
				 errMsg.append(err);
				//更新债权信息状态
				 CrediteInfo info = creditInfoService.findById(crditId);
				 info.setStatus(CrediteInfo.Status.IMP_FAIL);
				 info.setRemark(err);
				 creditInfoService.save(info);
			 }
		}
		return validRepayplanList;
	}

	/**
	 * 规则校验
	 * 
	 * @param creditList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkCreditValid(List<CreditInfoVo> creditList,String fileName) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> uniqMap = new HashMap<String, String>(); // 同一个债权人 债权编号不能重复
		List<CreditInfoVo> validList = new ArrayList<CreditInfoVo>();
		List<CreditInfoVo> invalidList = new ArrayList<CreditInfoVo>();
		if (creditList == null || creditList.size() == 0) {
			resultMap.put("code", FLAG_KIND_NINE);
			resultMap.put("msg", fileName+":债权信息列表：为空");
			return resultMap;
		}
		//业务逻辑校验
		int lineNum = 0 ;
		for (CreditInfoVo vo : creditList) {
			if(vo != null){
				lineNum ++;
				Map<String, Object> result = businessRuleCheck(vo, uniqMap, fileName,lineNum);
				if (FLAG_KIND_SUC.equals(result.get("flag"))) {
					vo.setRemark("数据正常");
					vo.setStatus(CreditInfoVo.Status.VALID);
					validList.add(vo);
				} else {
					vo.setStatus(CreditInfoVo.Status.INVALID);
					vo.setRemark("" + result.get("errMsg"));
					invalidList.add(vo); 
				}	
			}else{
				Logger.error("债权导入规则校验：为空");
			}
		}
		resultMap.put("validList", validList);
		resultMap.put("invalidList", invalidList);
		return resultMap;
	}
	/**
	 * 校验 同一个债权人 对应 债权编号是否重复
	 * @param creditList
	 * @return
	 */
	public  boolean checkCreditCodeUnq(List<CreditInfoVo> creditList){
		boolean flag = false;
	    if(creditList != null && creditList.size() > 0){
	    	int allNum = creditList.size();
	    	Map<String,String> crditCodeMap = new HashMap<String,String>();
	    	for(CreditInfoVo obj : creditList){
	    		if(obj != null && !Strings.empty(obj.getCreditCode())){
	    			crditCodeMap.put(obj.getCreditCode(), obj.getCreditCode());
	    		}
	    	}
	    	if(crditCodeMap.size() == allNum){
	    		flag = true;
	    	}
	    }
		return flag;
	}

	/**
	 * 业务规则校验
	 * 
	 * @param vo
	 * @param uniqCreditCodeMap
	 * @param creditorNo
	 * @return 00 成功 01：失败
	 */
	public Map<String, Object> businessRuleCheck(CreditInfoVo vo, Map<String, String> uniqMap, String fileName,int lineNum) throws Exception {
		boolean flag = true;
		String lineInfo= "债权信息第"+lineNum+"行:";
		String outInfo = "文件名="+fileName+","+lineInfo;
		StringBuilder errMsg = new StringBuilder();
		Date nowDate = new Date();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String creditorNo = vo.getCreditorNo(); // 债权人编号
		String creditCode = vo.getCreditCode(); // 债权编号
		String uniqkey = creditorNo+"_"+creditCode;
		if(uniqMap.containsKey(uniqkey)){
			resultMap.put("flag",   FLAG_KIND_ONE);
			resultMap.put("errMsg", lineInfo+"债权人编号:"+vo.getCreditorNo()+",债权编号:"+vo.getCreditCode()+"已重复");
			return resultMap;
		}else{
			uniqMap.put(uniqkey, uniqkey);
		}
	    //校验债权人编号是否存在
		Creditor creditor = creditorService.findByCredtorNo(creditorNo);
		if(creditor == null){
			resultMap.put("code", FLAG_KIND_NINE);
			resultMap.put("errMsg", lineInfo+"债权人编号系统不存在");
			return resultMap;
		}
		//校验 债权人+债权编号 是否存在
		boolean checkUniqLineFlag = true;
		try{
		   List<CrediteInfo> existsList = creditInfoService.findByCreditorNoAndCreditCode(creditorNo, creditCode);
		   if(existsList == null || existsList.size() == 0){
			   checkUniqLineFlag = false;
		   }
		}catch(Exception e){
			Logger.error(outInfo+"查询异常：债权人编号:"+creditorNo+",债权编号:"+creditCode, e);
		} 
		if(checkUniqLineFlag){
			resultMap.put("code", FLAG_KIND_NINE);
			resultMap.put("errMsg", lineInfo+"债权人编号:"+creditorNo+",债权编号:"+creditCode+", 系统已经存在;");
			return resultMap;
		}
		// 债权类型
		if (!CreditExcelUtil.CREDIT_KIND.contains(vo.getCreditKind())) { 
			flag = false;
			errMsg.append("债权类型不存在;");
		}
		if (CreditExcelUtil.IDENTITY_CARD.contains(vo.getCertType())){
			String identifyCode = vo.getCertificateNo();
			if (CreditExcelUtil.IDENTITY_CARD.equals(identifyCode)) {
				if (!CreditExcelUtil.checkIdentityCode(identifyCode)) {
					flag = false;
					errMsg.append("身份证格式错误");
				}
			}
		}
		if (!CreditExcelUtil.CREDIT_REPAY_WAY.contains(vo.getPayType())) { // 还款方式
			errMsg.append("还款方式错误");
			flag = false;
		}
		// 业务规则：放款日期 < 导入日期 < 债权到期日
		int i = 0;
		Date businessDate = null;
		Date deadTime = null ;
		try{
			 businessDate = Calendars.parse("yyyy-MM-dd", vo.getBusinessTime());i++;  
			 deadTime =  Calendars.parse("yyyy-MM-dd", vo.getDeadTime());
			 if (!(nowDate.after(businessDate) && nowDate.before(deadTime) && businessDate.before(deadTime))) {
				errMsg.append("日期有误：放款日期 < 导入日期 < 债权到期日 ;");
				flag = false;
			}
		}catch(Exception e){
			if(i <1){
				errMsg.append("放款日期,格式化异常,请输入格式(YYYY-MM-DD);");
			}else{
				errMsg.append("放款日期,格式化异常,请输入格式(YYYY-MM-DD);");
			}
			flag = false;
		}
		//校验借款金额 必须是正整数
		if(!CreditExcelUtil.checkNumber(vo.getAmount(),"+" ) ){
			errMsg.append("借款金额 必须是正整数：当前值: "+vo.getAmount()+";");
			flag = false;
		}
		//年利率 格式：10% 0--100
		String rateStr = vo.getRate();
		if(rateStr.contains(CreditExcelUtil.VAR_PERCENT) && 
		   CreditExcelUtil.checkNumber(rateStr.replace(CreditExcelUtil.VAR_PERCENT, ""), "0-100")){
		}else{
			errMsg.append("年利率 必须是百分比格式,且数值是0-100,当前值: "+rateStr+";");
			flag = false;
		}
		//借款期限
		String peroid = vo.getPeriod();
		if(!CreditExcelUtil.checkNumber(peroid, "0-100")){
			errMsg.append("借款期限 必须是正整数且数值是0-100，当前值= "+peroid+";");
			flag = false;
		}
		resultMap.put("flag", flag ? FLAG_KIND_SUC : FLAG_KIND_ONE);
		resultMap.put("errMsg", flag ? "" : lineInfo+errMsg.toString());
		return resultMap;
	}

	/**
	 * 债权信息 实体 初始化
	 * 
	 * @param vo
	 * @param entity
	 * @return
	 */
	public CrediteInfo initCreditInfoEntity(CreditInfoVo vo, CrediteInfo entity) throws Exception {
		if (vo != null) {
			entity.setCreditor(creditorService.findByCredtorNo(vo.getCreditorNo()));
			entity.setCrediteCode(vo.getCreditCode());
			entity.setCrediteType(vo.getCreditKind());
			entity.setBorrower(vo.getBorrower());
			entity.setCertType(vo.getCertType());
			entity.setCertificateNo(vo.getCertificateNo());
			entity.setWorkType(vo.getWorkType());
			entity.setProvince(vo.getProvince());
			entity.setCity(vo.getCity());
			entity.setPurpose(vo.getPurpose());
			entity.setPayType(vo.getPayType());
			entity.setRemark(vo.getRemark());
			entity.setCreditKind(CrediteInfo.CreditKind.EXECEL_IMP);
			if( CreditInfoVo.Status.VALID.equals(vo.getStatus()) ){
				entity.setRate(new BigDecimal(vo.getRate().trim().replace("%", "")).divide(new BigDecimal("100"), 2, RoundingMode.HALF_DOWN)) ;
				entity.setPeriod(Integer.parseInt(Strings.empty(vo.getPeriod(), "0")));
				entity.setDeadTime(Calendars.parse("yyyy-MM-dd", vo.getDeadTime()));
				entity.setBusinessTime(Calendars.parse("yyyy-MM-dd", vo.getBusinessTime()));
				entity.setAmount(new BigDecimal(vo.getAmount().trim()));
				entity.setStatus(CrediteInfo.Status.WAIT_ASSIGN);
			}else{
				entity.setRate(BigDecimal.ZERO) ;
				try{
				     entity.setAmount(new BigDecimal(vo.getAmount().trim()));
				}catch(Exception e){
					 entity.setAmount(BigDecimal.ZERO) ;
				}
				try{
				   entity.setPeriod(Integer.parseInt(Strings.empty(vo.getPeriod(), "0")));
				}catch(Exception e){
					entity.setPeriod(0);
				}
				entity.setDeadTime(Calendars.parse("yyyy-MM-dd", vo.getDeadTime()));
				entity.setBusinessTime(Calendars.parse("yyyy-MM-dd", vo.getBusinessTime()));
				try{
				  entity.setRate(new BigDecimal(vo.getRate().trim().replace("%", "")).divide(new BigDecimal("100"), 2, RoundingMode.HALF_DOWN)) ;
				}catch(Exception e){
					entity.setRate(BigDecimal.ZERO) ;
				}
				try{
				    entity.setPeriod(Integer.parseInt(Strings.empty(vo.getPeriod(), "0")));
				}catch(Exception e){
					entity.setPeriod(0);
				}
				try{
				   entity.setDeadTime(Calendars.parse("yyyy-MM-dd", vo.getDeadTime()));
				}catch(Exception e){}
				try{
				   entity.setBusinessTime(Calendars.parse("yyyy-MM-dd", vo.getBusinessTime()));
				}catch(Exception e){}
				entity.setStatus(CrediteInfo.Status.IMP_FAIL);
			}
		}
		return entity;
	}

	/**
	 * 债权还款计划明细 实体 初始化
	 * 
	 * @param vo
	 * @param entity
	 * @return
	 */
	public CreditRepayPlan initCreditRepayPlanEntity(RepayPlanVo vo, CreditRepayPlan entity) throws Exception{
		if (vo != null) {
			CrediteInfo creditInfo = creditInfoService.findById(vo.getCreditId());
			entity.setCreditor(creditInfo.getCreditor());
			entity.setCrediteInfo(creditInfo);
			entity.setPeriod(vo.getPeriod());
			entity.setRepayPlanTime(vo.getRepayTime());
			entity.setRepayPrincipal(vo.getRepayPrincipal());
			entity.setRepayInterest(vo.getRepayInterest());
			entity.setRepayAllmount(vo.getRepayAllmount());
			entity.setRemainPrincipal(vo.getRemainPrincipal());
			if(checkExpireDate(vo.getRepayTime())){
				entity.setStatus(CreditRepayPlan.Status.ALREADY_PAY); //设置为已还款
				entity.setRemark("导入时：还款时间小于当天24点,系统设置为已还款");
			}else{
				entity.setStatus(CreditRepayPlan.Status.WAIT_PAY);
				entity.setRemark(vo.getRemark());
			}
		}
		return entity;
	}
	/**
	 * 还款时间 必须小于当天24:00
	 * 否则 置为已还款 
	 * true : 已还款
	 * false: 未失效
	 */
	public boolean  checkExpireDate(Date repayPlanDate) {
		Date endOfToday = Calendars.parseEndDateTime(Calendars.format("yyyy-MM-dd", new Date()));
		return repayPlanDate.before(endOfToday);
	}
	

	/**
	 * 债权导入明细 查询
	 * 
	 * @param page
	 * @param size
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/repayPlanDetail/{id}")
	public String repayPlanDetail(@PathVariable("id") String id, Model model) {
		try {
			BigDecimal reMainCash = BigDecimal.ZERO;
			CrediteInfo creditInfo = creditInfoService.findById(id);
			List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
			List<LoanLog>  operateList = creditInfoService.queryCreditLogList(creditInfo);
			model.addAttribute("creditInfo", creditInfo);                          // 债权信息
			model.addAttribute("repayPlanDetailList", planList);                   // 还款明细
			model.addAttribute("operateList", operateList);                        // 债权标操作 明细
			User user = creditInfo.getCreditor().getUser();
			UserAccount userAccount = userInfoService.loadAccountByUserAndType(user, UserAccount.Type.CASH);
			if (userAccount != null) {
				if (userAccount.getBalance() != null) {
					reMainCash = userAccount.getBalance();
				}
			}
			model.addAttribute("reMainCash", reMainCash); // 账户可用余额
		} catch (Exception e) {
			Logger.error("债权还款计划明细异常：", e);
		}
		return "credit/importRepayDetail";
	}

	/**
	 * 进行发售操作
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSell/{id}")
	public String goSellCredit(@PathVariable("id") String id, Model model) {
		try {
			CrediteInfo creditInfo = creditInfoService.findById(id);
			List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
			Map<String, Object> calculatedMap = creditRepayPlanService.calculateRemainAmountAndPeriod(creditInfo, planList);
			model.addAttribute("creditInfo", creditInfo);
			model.addAttribute("remainPeriod", calculatedMap.get("remainPeriod")); // 剩余期数
		} catch (Exception e) {
			Logger.error("查询债权信息异常：id=" + id, e);
		}
		return "credit/sell";
	}

	/**
	 * 发售列表
	 * 
	 * @param creditInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/sellIndex")
	public String sellIndex(Model model) {
		return "credit/sellList";
	}
	/**
	 * 发售列表数据
	 * @param creditInfo
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/sellListTable")
	public String sellListTable(CrediteInfo creditInfo, String page, String size, Model model){
		try {
			size = "10";
			List<String> statuslist = new ArrayList<String>();
			statuslist.add(CrediteInfo.Status.WAIT_ASSIGN);
			statuslist.add(CrediteInfo.Status.BIDING);
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(creditInfo, page, size,statuslist);
			model.addAttribute("infoList", obj);
		} catch (Exception e) {
			Logger.error("债权导入列表查询异常:", e);
			model.addAttribute("infoList", null);
		}
		return "credit/sellListTable";
	}

	/**
	 * 发售 债权
	 * 
	 * @param creditInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/sell")
	public String sellCredit(CrediteInfo creditInfo, Model model) throws Exception {
			if (creditInfo == null || Strings.empty(creditInfo.getId())) {
				model.addAttribute("creditInfo", creditInfo);
				model.addAttribute("errMsg", "债权信息为空");
			}else{
				CrediteInfo entity = creditInfoService.findById(creditInfo.getId());
				if (!Strings.empty(creditInfo.getPurpose())) {
					entity.setPurpose(creditInfo.getPurpose());
				}
				if(creditInfo.getSellAmount().compareTo(BigDecimal.ZERO) != 1){
					 throw new ServiceException("发售金额必须大于0");
				}else{
					entity.setSellAmount(creditInfo.getSellAmount());
				}
				if (!Strings.empty(creditInfo.getAssureType())) {
					entity.setAssureType(creditInfo.getAssureType());
				}
				if (!Strings.empty(creditInfo.getAmountAim())) {
					entity.setAmountAim(creditInfo.getAmountAim());
				}
				if(creditInfo.getTermNum() <= 0){
					 throw new ServiceException("还款期数必须大于0");
				}else{
					entity.setTermNum(creditInfo.getTermNum());
				}
				String bidEndTimeStr = creditInfo.getBidEndTimeStr();
				if(Strings.empty(bidEndTimeStr)){
					 throw new ServiceException("招标截止时间不能为空");
				}else{
					entity.setBidEndTime(Calendars.parse("yyyy-MM-dd", bidEndTimeStr.trim()));
				}
				if(creditInfo.getDeadLine() <= 0){
					 throw new ServiceException("招标期限必须大于0");
				}else{
					entity.setDeadLine(creditInfo.getDeadLine());
				}
				//转让时间
				entity.setAssignTime(Calendars.parse("yyyy-MM-dd HH:mm:ss", Calendars.format("yyyy-MM-dd HH:mm:ss", new Date())));
				entity.setProductDesc(creditInfo.getProductDesc());
				if(creditInfoService.sellCredit(entity)){
					Logger.info("发售债权人"+entity.getCreditor().getCreditorNo()+"，债权编号:"+entity.getCertificateNo()+",发售成功");
				}
			}
		    return "redirect:/credit/sellIndex";
	}

	/**
	 * 债权导入 模板文件下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportData")
	public void fileDownLoad(HttpServletRequest request, HttpServletResponse response) {
		OutputStream ouputStream = null;
		ByteArrayOutputStream bytesarray = null;
		FileInputStream fileInput = null;
		try {
			String fileName = "外部债权导入模板.xlsx";
			RequestUtils.setDownload(request, response, "application/vnd.ms-excel", fileName);
			// 读取文件
			String url = request.getSession().getServletContext().getRealPath("resources/model/order-import-template.xlsx");
			fileInput = new FileInputStream(url);
			bytesarray = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = fileInput.read(bytes)) != -1) {
				bytesarray.write(bytes, 0, len);
			}
			ouputStream = response.getOutputStream();
			ouputStream.write(bytesarray.toByteArray());
		} catch (Exception e) {
			throw new ServiceException("债权导入模板文件下载异常");
		} finally {
			try {
				if (bytesarray != null) {
					bytesarray.flush();
					bytesarray.close();
				}
				if (ouputStream != null) {
					ouputStream.flush();
					ouputStream.close();
				}
				if (fileInput != null) {
					fileInput.close();
				}
			} catch (IOException e) {
				Logger.error("债权导入模板文件下载IO异常：",e);
			}
		}
	}

	/**
	 * 债权转让协议
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/assignProtocol/{id}")
	public String assignProtocol(@PathVariable("id") String id,Model model) throws Exception {
		if(!Strings.empty(id)){
			CrediteInfo creditInfo = creditInfoService.findById(id.trim());
			model.addAttribute("creditorIDCard", creditInfo.getCreditor().getCertificateNo());		//债权人证件号
			model.addAttribute("creditorName", creditInfo.getCreditor().getCreditorName());		    //债权人姓名
			model.addAttribute("principalAmount", creditInfo.getAmount().toString());  				//转让本金
			model.addAttribute("platformName", App.config("app.operation.name"));  					//平台名称
			model.addAttribute("companyAddr", App.config("app.company.address")); 				    //公司地址
			model.addAttribute("platformNetAddr", App.config("app.website"));						//平台网址
			model.addAttribute("rate", creditInfo.getRate());										//利率
			String unit = HermesConstants.UNIT_MONTH;
			if(CrediteInfo.CreditKind.YLTX_API.equals(creditInfo.getCreditKind())){
				unit = HermesConstants.UNIT_DAY;
			}
			model.addAttribute("period", creditInfo.getPeriod()+unit);								    //期限
			List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
			BigDecimal totalAmount = BigDecimal.ZERO;
			Date raiseDate = null;
			for(CreditRepayPlan obj: planList){
				totalAmount = totalAmount.add(obj.getRepayAllmount());
				if(obj.getPeriod() == 1){
					raiseDate = obj.getRepayPlanTime();
				}
			}
			model.addAttribute("repayPlanDetailList", planList );
			Calendar  calendar = Calendar.getInstance();
			calendar.setTime(raiseDate);
			String raiseDateStr = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONDAY)+1)+"月"+calendar.get(Calendar.DATE);
			model.addAttribute("raiseDate", raiseDateStr);  									    //起息日  第一期 还款时间 
			calendar.setTime(creditInfo.getDeadTime());
			String deadTimeDateStr = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONDAY)+1)+"月"+calendar.get(Calendar.DATE);
			model.addAttribute("deadTime", deadTimeDateStr);                               //债权到期日
			model.addAttribute("totalAmount", totalAmount);											//总金额合计
		}
		return "credit/assignProtocol";
	}

	/**
	 * 查看导入明细
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/viewImpDetail/{ids}")
	public String fileList(@PathVariable("ids") String ids, Model model) {
		if (Strings.empty(ids)) {
			Logger.error("查看导入明细 参数:ids为空");
		}
		String[] idArray = ids.split("_");
		try {
			List<CrediteInfo> lists = new ArrayList<CrediteInfo>();
			for (int i = 0; i < idArray.length; i++) {
				CrediteInfo creditInfo = creditInfoService.findById(idArray[i]);
				if (creditInfo != null) {
					lists.add(creditInfo);
				}
			}
			model.addAttribute("creditInfoList", lists);
		} catch (Exception e) {
			Logger.error("查看导入明细异常：id=" + ids, e);
		}
		return "credit/importLoan";
	}

	/**
	 * 已转让 列表 index
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/assigned")
	public String assigned(Model model) {
		return "credit/assigned";
	}

	/**
	 * 已转让列表 table加载
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/assignedTable")
	public String assignedTable(CrediteInfo creditInfo, String page, Model model) {
		String size = "10";
		try {
			List<String> statuslist = new ArrayList<String>();
			statuslist.add(CrediteInfo.Status.REPAYING);
			statuslist.add(CrediteInfo.Status.REPAY_FIINISH);
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(creditInfo, page, size,statuslist);
			model.addAttribute("assignedList", obj);
		} catch (Exception e) {
			throw new ServiceException(" 已转让列表 table加载异常");
		}
		return "credit/assignedTable";
	}
	
	/**
	 * 投标明细
	 * @param ids
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/bidDetail/{id}")
	public String bidDetail(@PathVariable("id") String id, Model model) throws Exception {
		if (Strings.empty(id)) {
			Logger.error("查看投标明细 参数:id为空");
		}
		List<LoanLog> loanLogList = new  ArrayList<LoanLog>();
		List<Loan> loanList = creditInfoService.queryLoanByCredit(id);
		for(Loan loan : loanList){
			if(loan!=null){ 
				List<String> typeList = new ArrayList<String>();
				typeList.add(LoanLog.Type.INVEST);
				List<LoanLog> loanLogRecordsList = loanService.loadLogByLoanIdAndTypeIn(loan, typeList );
				if(loanLogRecordsList != null && loanLogRecordsList.size() > 0){
					for(LoanLog loanLog : loanLogRecordsList){
						if(loanLog == null){
							continue;
						}
						User user = creditInfoService.queryUserByID(loanLog.getUser());
						loanLog.setUser(user.getAccount());
						loanLogList.add(loanLog);
					}
				}
			}
		}
		if(loanLogList ==null || loanLogList.size() == 0){
			loanLogList = null;
		}
		model.addAttribute("bidLogList", loanLogList);
		return "credit/assignedBidDetail";
	}
	/**
	 * 已转让债权-回款明细查看
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/repayDetail/{id}")
	public String  repayDetail(@PathVariable("id") String id, Model model){
		List<CreditRepayPlan> planList = null ;
		try {
			CrediteInfo creditInfo = creditInfoService.findById(id);
			planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
		} catch (Exception e) {
			Logger.info("已转让债权-回款明细查看异常 ,债权id="+id);
		}
		model.addAttribute("repayPlanDetailList", planList); // 还款明细
		return "credit/assignedRepayDetail";
	}

	/**
	 * 债权导入明细 查询
	 * 
	 * @param page
	 * @param size
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/sellDetail/{id}")
	public String sellDetail(@PathVariable("id") String id, Model model) {
		try {
			BigDecimal reMainCash = BigDecimal.ZERO;
			CrediteInfo creditInfo = creditInfoService.findById(id);
			List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
			Map<String, Object> calculatedMap = new HashMap<String, Object>();
			if(CrediteInfo.Status.IMP_FAIL.equals(creditInfo.getStatus()) ||
			   CrediteInfo.Status.WAIT_ASSIGN.equals(creditInfo.getStatus())){
				calculatedMap.put("remainAmount", "");
				calculatedMap.put("remainDays", "");
			}else{
				//calculatedMap = creditRepayPlanService.calculateRemainAmountAndPeriod(creditInfo, planList);
				calculatedMap.put("remainAmount", creditInfo.getSellAmount());
				calculatedMap.put("remainDays", creditInfo.getDeadLine());
			}
			List<LoanLog>  operateList = creditInfoService.queryCreditLogList(creditInfo);
			model.addAttribute("creditInfo", creditInfo);                           // 债权信息
			model.addAttribute("repayPlanDetailList", planList);                    // 还款明细
			model.addAttribute("operateList", operateList);                         // 债权标操作 明细
			model.addAttribute("remainAmount", calculatedMap.get("remainAmount") ); // 剩余本金
			model.addAttribute("remainDays",   calculatedMap.get("remainDays"));      // 剩余天数
			User user = creditInfo.getCreditor().getUser();
			UserAccount userAccount = userInfoService.loadAccountByUserAndType(user, UserAccount.Type.CASH);
			if (userAccount != null) {
				if (userAccount.getBalance() != null) {
					reMainCash = userAccount.getBalance();
				}
			}
			model.addAttribute("reMainCash", reMainCash); // 账户可用余额
		} catch (Exception e) {
			Logger.error("债权还款计划明细异常：", e);
		}
		return "credit/sellDetail";
	}
	
	/**
	 * 校验 招标截止日期  
	 * @param bidEndTime
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkEndBidTime")
	@ResponseBody
	public Map<String,String> checkEndBidTime(String bidEndTime, String id ) { 
		Map<String,String>  resultMap = new  HashMap<String,String>();
		String code = "", msg = "";
		if(Strings.empty(bidEndTime)){
			code =  "99";
			msg =  "招标截止时间为空";
		}else{
			CrediteInfo creditInfo= null;
			try {
				creditInfo = creditInfoService.findById(id);
				List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
				Map<String, String> checkResult = creditRepayPlanService.checkBidEndTimeValid(creditInfo, planList,bidEndTime);
				if(checkResult!=null && checkResult.size() > 0){
					code =  checkResult.get("code");
					msg = checkResult.get("msg");
				}else{
					code =  "99";
					msg =  "校验异常";
				}
			} catch (Exception e) {
				Logger.error("校验招标截止日期异常",e);
				code =  "99";
				msg =  "校验招标截止日期异常";
			}
		}
		resultMap.put("code", code);
		resultMap.put("msg",  msg);
		return resultMap;
	}
	
	@RequestMapping("/calcuRemainAmount")
	@ResponseBody
	public Map<String, String> calcuRemainAmount(String bidEndTime, String id ) { 
		Map<String,String>  resultMap = new  HashMap<String,String>();
		String code = "",msg = "", val="0" ;
		if(Strings.empty(bidEndTime)){
			code =  "99";
			msg =  "招标截止时间为空";
		}else{
			CrediteInfo creditInfo= null;
			try {
				creditInfo = creditInfoService.findById(id);
				List<CreditRepayPlan> planList = creditRepayPlanService.queryByCreditInfo(creditInfo);
				Map<String, Object> calculatedMap = creditRepayPlanService.calculateRemainAmount(creditInfo, planList,bidEndTime);
				if(calculatedMap!=null && calculatedMap.size() > 0){
					BigDecimal remainAmount  =  (BigDecimal) calculatedMap.get("remainAmount");
					val = remainAmount.toString();
				}
				code =  "00";
			} catch (Exception e) {
				Logger.error("计算发售金额异常",e);
				code =  "99";
				msg =  "计算发售金额异常";
			}
		}
		resultMap.put("code", code);
		resultMap.put("msg",  msg);
		resultMap.put("val",  val);
		return resultMap;
	}
}

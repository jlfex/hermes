package com.jlfex.hermes.console.credit;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.dict.Element;
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

	private static String today;
	private final static String CACHE_CREDITOR_SEQUENCE = "com.jlfex.cache.creditorsequence";

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

	/**
	 * 债权人 列表
	 * 
	 * @returno
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "credit/index";
	}

	@RequestMapping("/goAdd")
	public String addCredit(Model model) {
		String uniqueueCode = "";
		try {
			uniqueueCode = generateLoanNo();
		} catch (Exception e) {
			Logger.error("生成债权人唯一编号异常：", e);
		}
		model.addAttribute("creditorNo", uniqueueCode);
		return "credit/add";
	}

	@RequestMapping("/list")
	public String loandata(String creditorName, String cellphone, String page, String size, Model model) {
		model.addAttribute("lists", creditorService.findCreditorList(creditorName, cellphone, page, size));
		return "credit/data";
	}

	/**
	 * 债权人 新增
	 * 
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
		} catch (Exception e) {
			Logger.error("债权人 新增异常：", e);
		}
		model.addAttribute("creditor", creditor);
		return "credit/add";
	}

	/**
	 * 生成债权人编号
	 * 
	 * @return
	 */
	public synchronized String generateLoanNo() throws Exception {
		String date = Calendars.format("yyyyMMdd");
		Creditor creditor = null;
		List<Creditor> creditList = creditorService.findMaxCredtorNo();
		if (creditList != null && creditList.size() > 0) {
			creditor = creditList.get(0);
			if (creditor != null && !Strings.empty(creditor.getCreditorNo())) {
				String currMaxCreditNo = creditor.getCreditorNo();
				if (!Strings.empty(currMaxCreditNo) && currMaxCreditNo.length() == 14) {
					today = currMaxCreditNo.substring(2, 10);
					currMaxCreditNo = currMaxCreditNo.substring(10);
					Caches.set(CACHE_CREDITOR_SEQUENCE, Long.valueOf(currMaxCreditNo));
					Logger.info("数据库总：最大的债权人编号是：" + currMaxCreditNo);
				}
			}
		}
		// 判断缓存序列是否存在 若不存在则初始化
		if (Caches.get(CACHE_CREDITOR_SEQUENCE) == null) {
			Caches.set(CACHE_CREDITOR_SEQUENCE, 0);
		}
		// 若未匹配则重置序列编号 判断日期是否与当前日期匹配
		if (Strings.empty(today)) {
			today = date;
		} else {
			int num_nowDate = Integer.parseInt(date);
			int num_today = Integer.parseInt(Strings.empty(today, "0"));
			if (!date.equals(today)) {
				if (num_today < num_nowDate) {
					today = date;
					Caches.set(CACHE_CREDITOR_SEQUENCE, 0);
				}
			}
		}
		Long seq = Caches.incr(CACHE_CREDITOR_SEQUENCE, 1);// 递增缓存数据
		return String.format("ZQ%s%04d", today, seq);
	}

	/**
	 * 债权导入 列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/assign")
	public String assign(String page, String size, Model model) {
		return "credit/assign";
	}

	/**
	 * 债权导入 加载列表
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/loandata")
	public String loandata(String page, String size, Model model) {
		try {
			size = "10";
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(null, page, size, null);
			model.addAttribute("infoList", obj);
		} catch (Exception e) {
			Logger.error("债权导入列表查询异常:", e);
		}
		return "credit/loandata";
	}

	/**
	 * 债权导入 文件解析
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public Map<String, String> creditImport(@RequestParam MultipartFile file, Model model) {
		String msg = "", code = "";
		int sheet1AllNum = 0, sheet1SucNum = 0, sheet1ErrNum = 0;
		int sheet2AllNum = 0, sheet2SucNum = 0, sheet2ErrNum = 0;
		Map<String, String> resultMap = new HashMap<String, String>();
		String fileName = file.getOriginalFilename();
		StringBuilder creditInfoIds = new StringBuilder();
		Map<String, Object> sheetMap = null;
		try {
			// 1: 解析Excel 到 数据模型
			FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
			sheetMap = CreditExcelUtil.analysisExcel(fileName, fileInputStream);
			List<CreditInfoVo> creditList = (List<CreditInfoVo>) sheetMap.get("creditList");
			List<RepayPlanVo> repayList = (List<RepayPlanVo>) sheetMap.get("repayList");
			// 1.1 统计总数
			sheet1AllNum = creditList != null ? creditList.size() : 0;
			sheet2AllNum = repayList != null ? repayList.size() : 0;
			// 1.2: 业务规则校验
			Map<String, Object> creditResult = checkCreditValid(creditList);
			if (FLAG_KIND_NINE.equals(creditResult.get("code"))) {
				throw new Exception("" + creditResult.get("msg"));
			} else {
				creditList = new ArrayList<CreditInfoVo>();
				List<CreditInfoVo> validList = (List<CreditInfoVo>) creditResult.get("validList");
				List<CreditInfoVo> invalidList = (List<CreditInfoVo>) creditResult.get("invalidList");
				if (validList.size() == 0) {
					StringBuilder errorMsg = new StringBuilder();
					for (CreditInfoVo vo : invalidList) {
						errorMsg.append(vo.getRemark());
					}
					throw new Exception(errorMsg.toString());
				} else {
					creditList.addAll(validList);
				}
				// 1.2 统计 校验通过 数据
				sheet1SucNum = validList != null ? validList.size() : 0;
				sheet1ErrNum = invalidList != null ? invalidList.size() : 0;
			}
			// 1 checkRepayListValid(repayList);
			// 2:持久化:债权信息
			List<CrediteInfo> entityCreditList = new ArrayList<CrediteInfo>();
			List<CreditRepayPlan> entitLoanPayList = new ArrayList<CreditRepayPlan>();
			for (CreditInfoVo vo : creditList) {
				CrediteInfo crediteInfo = new CrediteInfo();
				entityCreditList.add(initCreditInfoEntity(vo, crediteInfo));
			}
			List<CrediteInfo> savedCreditInfolist = creditInfoService.saveBatch(entityCreditList);
			if (savedCreditInfolist == null || savedCreditInfolist.size() == 0) {
				throw new Exception("债权信息保存失败");
			} else {
				for (CrediteInfo obj : savedCreditInfolist) {
					creditInfoIds.append(obj.getId()).append("_");
				}
			}
			// 3:持久化:债权还款明细
			CrediteInfo load_crediteInfo = savedCreditInfolist.get(0);
			Creditor load_creditor = savedCreditInfolist.get(0).getCreditor();
			for (RepayPlanVo vo : repayList) {
				CreditRepayPlan repayPlan = new CreditRepayPlan();
				entitLoanPayList.add(initCreditRepayPlanEntity(vo, repayPlan, load_creditor, load_crediteInfo));
				sheet2SucNum++;
			}

			List<CreditRepayPlan> creditRepayPlanList = creditRepayPlanService.saveBatch(entitLoanPayList);
			if (creditRepayPlanList == null || creditRepayPlanList.size() == 0) {
				sheet2SucNum = 0;
				Logger.info("还款计划表保存失败");
			}
			sheet2ErrNum = sheet2AllNum - sheet2SucNum;
			code = FLAG_KIND_SUC;
			msg = "成功";
		} catch (Exception e) {
			String var = "债权导入 文件解析异常";
			Logger.error(var, e);
			code = FLAG_KIND_NINE;
			msg = e.getMessage();
		}
		resultMap.put("creditInfoIds", creditInfoIds.toString());
		resultMap.put("sheet1AllNum", String.valueOf(sheet1AllNum));
		resultMap.put("sheet1SucNum", String.valueOf(sheet1SucNum));
		resultMap.put("sheet1ErrNum", String.valueOf(sheet1ErrNum));
		resultMap.put("sheet2AllNum", String.valueOf(sheet2AllNum));
		resultMap.put("sheet2SucNum", String.valueOf(sheet2SucNum));
		resultMap.put("sheet2ErrNum", String.valueOf(sheet2ErrNum));
		resultMap.put("fileName", fileName);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**
	 * 规则校验
	 * 
	 * @param creditList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkCreditValid(List<CreditInfoVo> creditList) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> uniqCreditCodeMap = new HashMap<String, String>(); // 同一个债权人
																				// 债权编号不能重复
		List<CreditInfoVo> validList = new ArrayList<CreditInfoVo>();
		List<CreditInfoVo> invalidList = new ArrayList<CreditInfoVo>();
		if (creditList == null || creditList.size() == 0) {
			resultMap.put("code", FLAG_KIND_NINE);
			resultMap.put("msg", "解析债权信息列表：为空");
			return resultMap;
		}
		String creditorNo = creditList.get(0).getCreditorNo(); // 债权人编号
		Creditor creditor = creditorService.findByCredtorNo(creditorNo);
		if (creditor == null) {
			resultMap.put("code", FLAG_KIND_NINE);
			resultMap.put("msg", "债权人编号系统不存在");
			return resultMap;
		}
		for (CreditInfoVo vo : creditList) {
			if (vo != null && CreditInfoVo.Status.VALID.equals(vo.getStatus())) {
				Map<String, Object> result = businessRuleCheck(vo, uniqCreditCodeMap, creditorNo);
				if (FLAG_KIND_SUC.equals(result.get("flag"))) {
					vo.setRemark("");
					validList.add(vo);
				} else {
					vo.setStatus(CrediteInfo.Status.IMP_FAIL);
					vo.setRemark("" + result.get("errMsg"));
					invalidList.add(vo); // 业务规则校验不通过
				}
			} else {
				invalidList.add(vo); // 非空校验不通过
			}
		}
		resultMap.put("validList", validList);
		resultMap.put("invalidList", invalidList);
		return resultMap;
	}

	/**
	 * 业务规则校验
	 * 
	 * @param vo
	 * @param uniqCreditCodeMap
	 * @param creditorNo
	 * @return 00 成功 01：失败
	 */
	public Map<String, Object> businessRuleCheck(CreditInfoVo vo, Map<String, String> uniqCreditCodeMap, String creditorNo) throws Exception {
		boolean flag = true;
		StringBuilder errMsg = new StringBuilder();
		Date nowDate = new Date();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String creditCode = vo.getCreditorNo(); // 债权编号不能重复
		if (uniqCreditCodeMap == null) {
			uniqCreditCodeMap.put(creditCode, creditCode);
		} else {
			if (uniqCreditCodeMap.containsKey(creditCode)) {
				flag = false;
				errMsg.append("同一个债权人对应债权编号不能重复;");
			}
		}
		if (!creditorNo.equals(vo.getCreditorNo())) { // 债权人
			flag = false;
			errMsg.append("债权人编号错误;");
		}
		if (!CreditExcelUtil.CREDIT_KIND.contains(vo.getCreditKind())) { // 债权类型
			flag = false;
			errMsg.append("债权类型不存在;");
		}
		if (!CreditExcelUtil.CREDITOR_CERTIFICATE_KIND.contains(vo.getCertType())) {// 借款人证件类型
			flag = false;
			errMsg.append("借款人证件类型 系统不存在;");
		} else {
			String identifyCode = vo.getCertificateNo();
			if (CreditExcelUtil.IDENTITY_CARD.equals(identifyCode)) {
				if (!CreditExcelUtil.checkIdentityCode(identifyCode)) {
					flag = false;
					errMsg.append("身份证格式错误");
				}
			}
		}
		if (!CreditExcelUtil.CREDIT_REPAY_WAY.equals(vo.getPayType())) { // 还款方式
			errMsg.append("还款方式错误，只支持等额本息;");
			flag = false;
		}
		// 业务规则：放款日期 < 导入日期 < 债权到期日
		if (!(nowDate.after(vo.getBusinessTime()) && nowDate.before(vo.getDeadTime()) && vo.getBusinessTime().before(vo.getDeadTime()))) {
			errMsg.append("日期有误：放款日期 < 导入日期 < 债权到期日 ");
			flag = false;
		}
		resultMap.put("flag", flag ? FLAG_KIND_SUC : FLAG_KIND_ONE);
		resultMap.put("errMsg", flag ? "" : errMsg.toString());
		return resultMap;
	}

	/**
	 * 债权信息 实体 初始化
	 * 
	 * @param vo
	 * @param entity
	 * @return
	 */
	public CrediteInfo initCreditInfoEntity(CreditInfoVo vo, CrediteInfo entity) {
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
			entity.setAmount(vo.getAmount());
			entity.setRate(vo.getRate());
			entity.setPeriod(Integer.parseInt(Strings.empty(vo.getPeriod(), "0")));
			entity.setPurpose(vo.getPurpose());
			entity.setPayType(vo.getPayType());
			entity.setDeadTime(vo.getDeadTime());
			entity.setBusinessTime(vo.getBusinessTime());
			entity.setRemark(vo.getRemark());
			entity.setStatus(vo.getStatus());
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
	public CreditRepayPlan initCreditRepayPlanEntity(RepayPlanVo vo, CreditRepayPlan entity, Creditor creditor, CrediteInfo creditInfo) {
		if (vo != null) {
			entity.setCreditor(creditor);
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
	 * 查询 债权还款计划明细
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
			Map<String, Object> calculatedMap = creditRepayPlanService.calculateRemainAmountAndPeriod(creditInfo, planList);
			model.addAttribute("creditInfo", creditInfo); // 债权信息
			model.addAttribute("repayPlanDetailList", planList); // 还款明细
			model.addAttribute("remainAmount", calculatedMap.get("remainAmount")); // 剩余本金
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
		return "credit/repayDetail";
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
			model.addAttribute("remainAmount", calculatedMap.get("remainAmount")); // 剩余本金
			model.addAttribute("remainPeriod", calculatedMap.get("remainPeriod")); // 剩余本金
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
	public String sellIndex(CrediteInfo creditInfo, String page, String size, Model model) {
		try {
			size = "10";
			List<String> statuslist = new ArrayList<String>();
			statuslist.add(CrediteInfo.Status.WAIT_ASSIGN);
			statuslist.add(CrediteInfo.Status.BIDING);
			statuslist.add(CrediteInfo.Status.FAIL_ASSIGNING);
			Page<CrediteInfo> obj = creditInfoService.queryByCondition(creditInfo, page, size,statuslist);
			model.addAttribute("sellList", obj);
		} catch (Exception e) {
			Logger.error("债权导入列表查询异常:", e);
		}
		return "credit/sellList";
	}

	/**
	 * 发售 债权
	 * 
	 * @param creditInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/sell")
	public String sellCredit(CrediteInfo creditInfo, Model model) {
		try {
			if (creditInfo == null || Strings.empty(creditInfo.getId())) {
				model.addAttribute("creditInfo", creditInfo);
				model.addAttribute("errMsg", "债权信息为空");
			} else {
				CrediteInfo entity = creditInfoService.findById(creditInfo.getId());
				if (!Strings.empty(creditInfo.getPurpose())) {
					entity.setPurpose(creditInfo.getPurpose());
				}
				if (!Strings.empty(creditInfo.getBidEndTimeStr())) {
					entity.setBidEndTimeStr(creditInfo.getBidEndTimeStr());
				}
				if(creditInfo.getAmount().compareTo(BigDecimal.ZERO) != 1){
					 throw new ServiceException("发售金额必须大于0");
				}else{
					entity.setAmount(creditInfo.getAmount());
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
					entity.setBidEndTime(Calendars.parse("yyyy-MM-dd", bidEndTimeStr));
				}
				if(creditInfo.getDeadLine() <= 0){
					 throw new ServiceException("招标期限必须大于0");
				}else{
					entity.setDeadLine(creditInfo.getDeadLine());
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
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}

	/**
	 * 债权转让协议
	 * 
	 * @return
	 */
	@RequestMapping("/assignProtocol")
	public String assignProtocol() {
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
		return "credit/assign";
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
	public String assignedTable(String page, String size, Model model) {
		size = "10";
		CrediteInfo creditInfo = new CrediteInfo();
		creditInfo.setStatus(CrediteInfo.Status.REPAYING);
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
	 */
	@RequestMapping("/bidDetail/{id}")
	public String bidDetail(@PathVariable("id") String id, Model model) {
		if (Strings.empty(id)) {
			Logger.error("查看投标明细 参数:id为空");
		}
		List<LoanLog> loanLogList = new  ArrayList<LoanLog>();
		List<Loan> loanList = creditInfoService.queryLoanByCredit(id);
		for(Loan loan : loanList){
			if(loan!=null){ 
				// 债权标是 全额投标: 状态是满标
				LoanLog loanLog = loanService.loadLogByLoanIdAndType(loan.getId(), LoanLog.Type.FULL);
				if(loanLog != null){
					User user = creditInfoService.queryUserByID(loanLog.getUser());
					loanLog.setUser(user.getAccount());
					loanLogList.add(loanLog); 
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

}

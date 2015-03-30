package com.jlfex.hermes.service.api.yltx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.http.HttpClientUtil;
import com.jlfex.hermes.common.utils.Bean2Map;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.CollectionUtil;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.model.Sequence;
import com.jlfex.hermes.model.yltx.Asset;
import com.jlfex.hermes.model.yltx.AssetRepayPlan;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.model.yltx.FinanceRepayPlan;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.repository.apiconfig.ApiConfigRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.CreditRepayPlanService;
import com.jlfex.hermes.service.CreditorService;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.apiLog.ApiLogService;
import com.jlfex.hermes.service.asset.AssetService;
import com.jlfex.hermes.service.assetRepay.AssetRepayPlanService;
import com.jlfex.hermes.service.finance.FinanceOrderService;
import com.jlfex.hermes.service.financePlan.FinanceRepayPlanService;
import com.jlfex.hermes.service.pojo.yltx.request.OrderPayRequestVo;
import com.jlfex.hermes.service.pojo.yltx.response.AssetRepayPlanVo;
import com.jlfex.hermes.service.pojo.yltx.response.AssetVo;
import com.jlfex.hermes.service.pojo.yltx.response.FinanceOrderVo;
import com.jlfex.hermes.service.pojo.yltx.response.QueryFinanceOrderVo;
import com.jlfex.hermes.service.pojo.yltx.response.RepayPlan;
import com.jlfex.hermes.service.sequence.SequenceService;


/**
 * 易联天下 对接
 * @author Administrator
 * @since 1.0 20150310
 */
@Service
@Transactional
public class JlfexServiceImpl implements JlfexService {

	//开放平台流水号缓存标识
	private final static String CACHE_YLTX_SERIAL_SEQUENCE = "com.jlfex.cache.request.serial";
		
	@Autowired
	private  SequenceService  sequenceService;
	@Autowired
	private  ApiConfigRepository  apiConfigRepository;
	@Autowired
	private  DictionaryService  dictionaryService;
	@Autowired
	private  DictionaryTypeRepository dictionaryTypeRepository;
	@Autowired
	private  ApiLogService apiLogService;
	@Autowired
	private  ProductService productService;
	@Autowired
	private  RepayService repayService;
	@Autowired
	private  RateRepository rateRepository;
	@Autowired
	private  LoanService loanService;
	@Autowired
	private  FinanceOrderService financeOrderService;
	@Autowired
	private  CreditorService  creditorService;
	@Autowired
	private  CreditInfoService creditInfoService;
	@Autowired
	private  CreditRepayPlanService creditRepayPlanService;
	@Autowired
	private  AssetService  assetService;
	@Autowired
	private  AssetRepayPlanService assetRepayPlanService;
	@Autowired
	private  UserInfoService userInfoService;
	@Autowired 
	private  FinanceRepayPlanService financeRepayPlanService;
	//接口配置
	public static ApiConfig  apiConfig = null;
	
	
	@Override
	public String queryFinanceOrder(String financeProductStatus,String createDate, int pageSize, int pageNum) throws Exception {
		String result = null ;
		String var = "查询理财产品接口:";
		if(apiConfig==null){
			apiConfig = getApiConfig();
		}
		String serialNo = generateSerialNo(HermesConstants.CODE_FINANCE_FRODUCT_GET) ;
	    StringBuffer reqUrlBuffer = new StringBuffer();
	    reqUrlBuffer.append(apiConfig.getApiUrl().trim());
	    reqUrlBuffer.append(HttpClientUtil.buildGetCommonParam(HermesConstants.JL_FINANCE_FRODUCT_GET, serialNo));
	    if(Strings.notEmpty(createDate)){
	    	 reqUrlBuffer.append("createDate=").append("").append("&");
	    }
	    if(Strings.notEmpty(financeProductStatus)){
	    	 reqUrlBuffer.append("financeProductStatus=").append(financeProductStatus).append("&");
	    }
	    reqUrlBuffer.append("pageSize=").append(pageSize).append("&");
	    reqUrlBuffer.append("pageNum=").append(pageNum);
	    Logger.info(var+"请求地址:"+reqUrlBuffer.toString());
	    //保存请求日志
	  	Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod",HermesConstants.JL_FINANCE_FRODUCT_GET);
	  	recodeMap.put("requestMsg",reqUrlBuffer.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	try{
		    HttpClientUtil.initHttps(apiConfig.getClientStoreName(), apiConfig.getClientStorePwd(), apiConfig.getTruestStoreName(), apiConfig.getTruststorePwd());
		    result = HttpClientUtil.doGetHttps(reqUrlBuffer.toString().trim());
		    Logger.info(var+"响应结果"+result);
		    apiLog.setResponseMessage(result);
		    apiLog.setResponseTime(new Date());
		    apiLog.setDealFlag(ApiLog.DealResult.SUC);
	  	}catch(Exception e){
	  		Logger.error(var+"请求异常：", e);
	  		apiLog.setException(e.getMessage());
	  	}
	  	apiLogService.saveApiLog(apiLog);
	  	if(result.contains("status") && result.contains("memo")){
			Logger.info(var+" 接口返回结果="+result);
			result = null;
		}
		return result;
	}

	/**
	 * 下单并支付 
	 */
	@Override
	public Map<String,String> createOrderAndPay(OrderPayRequestVo vo) throws Exception {
		Map<String,String> resultMap = new  HashMap<String,String>();
		String var = "下单并支付接口:";
		String errMsg = null;
		if(vo == null){
			errMsg = "下单并支付接口： 无效的请求报文实体";
			Logger.info(errMsg);
			resultMap.put("code", "99");
			resultMap.put("msg", errMsg);
			return resultMap;
		}
		vo.setOrderSn(generateOrderSn());
		if(apiConfig==null){
			apiConfig = getApiConfig();
		}
		String serialNo = generateSerialNo(HermesConstants.CODE_ORDER_DO2PAY) ;
		Map<String,String>  commonMap = HttpClientUtil.buildPostCommonParam(HermesConstants.JL_ORDER_DO2PAY, serialNo);
		commonMap.putAll(Bean2Map.getValueMap(vo));
		//请求日志
		Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod",HermesConstants.JL_ORDER_DO2PAY);
	  	recodeMap.put("requestMsg",commonMap.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	String result = null;
	  	try{
		    HttpClientUtil.initHttps(apiConfig.getClientStoreName(), apiConfig.getClientStorePwd(), apiConfig.getTruestStoreName(), apiConfig.getTruststorePwd());
		    result = HttpClientUtil.doPostHttps(apiConfig.getApiUrl().trim(), commonMap);
		    Logger.info(var+"接口返回结果"+result);
		    apiLog.setResponseMessage(result);
		    apiLog.setResponseTime(new Date());
		    apiLog.setDealFlag(ApiLog.DealResult.SUC);
	  	}catch(Exception e){
	  		errMsg = e.getMessage();
	  		Logger.error(var+"请求异常：", e);
	  		apiLog.setException(e.getMessage());
	  	}
	  	apiLogService.saveApiLog(apiLog);
	  	if(result.contains("status") && result.contains("memo")){
			errMsg = result;
		}
	  	if(errMsg==null){
	  		resultMap.put("code", "00");
	  		resultMap.put("msg", "成功");
	  		resultMap.put("result", result);
	  	}else{
	  		resultMap.put("code", "99");
	  		resultMap.put("msg", errMsg);
	  	}
		return resultMap;
	}

	@Override
	public String revokeOrder(String orderCode) throws Exception {
		String var = "撤销订单接口:";
		Logger.info("订单: orderCode =" +orderCode+",开始进行撤单");
		if(Strings.empty(orderCode)){
			return null;
		}
		if(apiConfig==null){
			apiConfig = getApiConfig();
		}
		String serialNo = generateSerialNo(HermesConstants.CODE_ORDER_CANCEL) ;
		Map<String,String>  commonMap = HttpClientUtil.buildPostCommonParam(HermesConstants.JL_ORDER_CANCEL, serialNo);
		commonMap.put("orderCode", orderCode.trim());
		//请求日志
		Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod",HermesConstants.JL_ORDER_CANCEL);
	  	recodeMap.put("requestMsg",commonMap.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	String result = null;
	  	try{
		    HttpClientUtil.initHttps(apiConfig.getClientStoreName(), apiConfig.getClientStorePwd(), apiConfig.getTruestStoreName(), apiConfig.getTruststorePwd());
		    result = HttpClientUtil.doPostHttps(apiConfig.getApiUrl().trim(), commonMap);
		    Logger.info(var+"接口返回结果"+result);
		    apiLog.setResponseMessage(result);
		    apiLog.setResponseTime(new Date());
		    apiLog.setDealFlag(ApiLog.DealResult.SUC);
		    Logger.info("撤单成功: orderCode =" +orderCode);
	  	}catch(Exception e){
	  		Logger.error(var+"异常：", e);
	  		apiLog.setException(e.getMessage());
	  	}
	  	apiLogService.saveApiLog(apiLog);
	  	if(result.contains("status") && result.contains("memo")){
			 result = null;
		}
		return result;
	}

	@Override
	public String queryOrderStatus(String orderCodes) throws Exception {
		return null;
	}

	@Override
	public void queryProtocolFile(String fileId) throws Exception {
		
	}
	
	/**
	 * 请求流水号
	 * @param interfaceCode 接口方法编码
	 * @return
	 * @throws Exception
	 */
	public synchronized String generateSerialNo(String interfaceCode) throws Exception {
		int increamentVal = 1;
		Sequence sequence = sequenceService.findBySeqNameAndStatus(HermesConstants.SEQ_YLTX_REQUEST_SERIAL_NO, HermesConstants.VALID);
		if (sequence != null) {
			increamentVal = sequence.getIncrementVal();
			String currentVal = sequence.getCurrentVal();
			Caches.set(CACHE_YLTX_SERIAL_SEQUENCE, Long.valueOf(currentVal));
			Logger.info("当前系统请求流水号CACHE_YLTX_SERIAL_SEQUENCE=" + currentVal);	
		}else{
			Logger.error("序列serialNo没有初始化");
		}
		// 判断缓存序列是否存在 若不存在则初始化
		if (Caches.get(CACHE_YLTX_SERIAL_SEQUENCE) == null) {
			Caches.set(CACHE_YLTX_SERIAL_SEQUENCE, 0);
		}
		Long seq = Caches.incr(CACHE_YLTX_SERIAL_SEQUENCE, increamentVal);// 递增缓存数据
		sequence.setCurrentVal(seq.toString());
		sequenceService.saveSequnce(sequence);
		return String.format(interfaceCode+"%012d", seq);
	}
	
    /**
     * 订单流水号
     * @return
     * @throws Exception
     */
	public synchronized String generateOrderSn() throws Exception {
		int increamentVal = 1;
		Sequence sequence = sequenceService.findBySeqNameAndStatus(HermesConstants.SEQ_YLTX_REQUEST_ORDER_SN, HermesConstants.VALID);
		if (sequence != null) {
			increamentVal = sequence.getIncrementVal();
			String currentVal = sequence.getCurrentVal();
			Caches.set(CACHE_YLTX_SERIAL_SEQUENCE, Long.valueOf(currentVal));
			Logger.info("当前系统请求流水号CACHE_YLTX_SERIAL_SEQUENCE=" + currentVal);	
		}else{
			Logger.error("序列serialNo没有初始化");
		}
		// 判断缓存序列是否存在 若不存在则初始化
		if (Caches.get(CACHE_YLTX_SERIAL_SEQUENCE) == null) {
			Caches.set(CACHE_YLTX_SERIAL_SEQUENCE, 0);
		}
		Long seq = Caches.incr(CACHE_YLTX_SERIAL_SEQUENCE, increamentVal);// 递增缓存数据
		sequence.setCurrentVal(seq.toString());
		sequenceService.saveSequnce(sequence);
		return String.format(HermesConstants.PRE_HERMES+"%012d", seq);
	}
	
	/**
	 * 获取第三方平台配置信息
	 * @return
	 * @throws Exception
	 */
	public ApiConfig getApiConfig() throws Exception{
		ApiConfig  apiConfig = apiConfigRepository.findByPlatCodeAndStatus(HermesConstants.PLAT_JLFEX_CODE, HermesConstants.VALID);
		if(apiConfig == null){
			Map<String,String>  recodeMap = new HashMap<String,String>();
		  	recodeMap.put("interfaceMethod",HermesConstants.JL_FINANCE_FRODUCT_GET);
		    recordApiLog(apiConfig, recodeMap);
			throw new  Exception("平台接口配置没有初始化");
		}
		return  apiConfig;
	}
	
	/**
	 * 保存接口日志
	 * @param apiConfig
	 * @param map
	 */
	public ApiLog  recordApiLog(ApiConfig apiConfig, Map<String,String> map){
		ApiLog apiLog = new  ApiLog();
		apiLog.setApiConfig(apiConfig);
		apiLog.setCreator(HermesConstants.PLAT_MANAGER);
		apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
		apiLog.setRequestTime(new Date());
		apiLog.setDealFlag(ApiLog.DealResult.FAIL);
		if(!Strings.empty(map.get("interfaceMethod"))){
			apiLog.setInterfaceName(map.get("interfaceMethod"));
		}
		if(!Strings.empty(map.get("requestMsg"))){
			apiLog.setRequestMessage(map.get("requestMsg"));
		}
		if(!Strings.empty(map.get("serialNo"))){
			apiLog.setSerialNo(map.get("serialNo"));
		}
		try{
		    return apiLogService.saveApiLog(apiLog);
		}catch (Exception e) {
			Logger.error("接口日志对象保存异常：",e);
		    return null;
		}
	}

	/**
	 * 还款计划表 获取
	 */
	@Override
	public String  queryRepayPlan(String code, String type) throws Exception {
		String result = null ;
		String var = "查询还款计划表接口:";
		ApiConfig apiConfig = getApiConfig();
		String serialNo = generateSerialNo(HermesConstants.CODE_FINPRO_REPSCH) ;
	    StringBuffer reqUrlBuffer = new  StringBuffer();
	    reqUrlBuffer.append(apiConfig.getApiUrl().trim());
	    reqUrlBuffer.append(HttpClientUtil.buildGetCommonParam(HermesConstants.JL_FINPRO_REPSCH, serialNo));
	    reqUrlBuffer.append("code=").append(code.trim()).append("&");
	    reqUrlBuffer.append("type=").append(type.trim());
	    Logger.info(var+"请求地址:"+reqUrlBuffer.toString());
	    //保存请求日志
	  	Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod", HermesConstants.JL_FINPRO_REPSCH);
	  	recodeMap.put("requestMsg",reqUrlBuffer.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	try{
		    HttpClientUtil.initHttps(apiConfig.getClientStoreName(), apiConfig.getClientStorePwd(), apiConfig.getTruestStoreName(), apiConfig.getTruststorePwd());
		    result = HttpClientUtil.doGetHttps(reqUrlBuffer.toString().trim());
		    Logger.info(var+"响应结果"+result);
		    apiLog.setResponseMessage(result);
		    apiLog.setResponseTime(new Date());
		    apiLog.setDealFlag(ApiLog.DealResult.SUC);
	  	}catch(Exception e){
	  		Logger.error(var+"请求异常：", e);
	  		apiLog.setException(e.getMessage());
	  	}
	  	apiLogService.saveApiLog(apiLog);
	  	if(result.contains("status") && result.contains("memo")){
			Logger.info("还款计划获取失败：code="+code+",type="+type+", 接口返回结果="+result);
			result = null;
		}
	  	return result;
	}
	
	/**
	 * 易联债权标处理
	 * flag :  00 未处理  01 处理成功  02: 重复不处理
	 */
	@Override
	public String yltxCreditDeal(FinanceOrder obj) throws Exception {
		String  flag = "00";
		if(obj != null){
			FinanceOrder existOrder = financeOrderService.queryByUniqId(obj.getUniqId());
			//判断理财产品是否存在
			if(existOrder == null){
				obj.setDealStatus(FinanceOrder.DealStatus.WAIT_DEAL);
				//保存 易联理财产品信息
				existOrder = financeOrderService.save(obj);
				//保存理财产品还款计划
				String finaceRepayPlanResult = queryRepayPlan(existOrder.getUniqId().trim(),HermesConstants.TYPE_FINANCE_REPAY_PLAN);
				RepayPlan  finceRepayPlan = JSON.parseObject(finaceRepayPlanResult,RepayPlan.class);
			    if(finceRepayPlan!=null){
			    	List<AssetRepayPlanVo> financeRepayPlanVoLists = finceRepayPlan.getContent(); 
			    	if(financeRepayPlanVoLists!=null && financeRepayPlanVoLists.size() > 0){
			    		List<FinanceRepayPlan> finPlanList = transferFinanceRepayPlan(existOrder,financeRepayPlanVoLists);
			    		financeRepayPlanService.saveList(finPlanList);
					}
			    }
				//设置 资产所属理财产品
				Set<Asset>  assetSets =  obj.getAssetsList();
				Iterator<Asset>  iterators = assetSets.iterator();
				while(iterators.hasNext()){
					Asset assetItem = iterators.next();
					if(assetItem !=null){
						assetItem.setFinanceOrder(existOrder);
					}
				}
				List<Asset> savedAssetLists = assetService.save(CollectionUtil.switchSet2List(assetSets));
				// 解析还款计划
				List<AssetRepayPlan> assetEntityList = new ArrayList<AssetRepayPlan>();
				for(Asset entity :savedAssetLists){
					String assetRepayPlanResult = queryRepayPlan(entity.getCode().trim(),HermesConstants.TYPE_ASSET_REPAY_PLAN);
				    RepayPlan  repayPlan = JSON.parseObject(assetRepayPlanResult,RepayPlan.class);
				    if(repayPlan!=null){
				    	List<AssetRepayPlanVo> assetRepayPlanVoLists = repayPlan.getContent(); 
				    	if(assetRepayPlanVoLists!=null && assetRepayPlanVoLists.size() > 0){
				    		List<AssetRepayPlan> assetRepayPlan = transferAssetRepayPlan(entity,assetRepayPlanVoLists);
				    		assetEntityList.addAll(assetRepayPlan);
							//保存资产还款计划
							entity.setAssetRepayList(CollectionUtil.switchList2Set(assetRepayPlan));
						}
				    }
				}
				assetRepayPlanService.save(assetEntityList);
				//易联天下数据模型 转  hermes债权数据模型
				transCreditInfo(savedAssetLists, existOrder);
			}else{
				Logger.info("理财产品编号"+existOrder.getUniqId()+" 信息已经存在");
			}
			//若 没有处理成功继续 处理
			if(!FinanceOrder.DealStatus.DEAL_SUC.equals(existOrder.getDealStatus())){
				Logger.info("理财产品编号"+existOrder.getUniqId()+" 自动发售");
				autoBuildLoan(existOrder);
				flag = "01";
			}else{
				Logger.info("理财产品编号"+existOrder.getUniqId()+" 已经发售成功，不需重复发售");
				flag = "02";
			}
		}
		return flag;
	}
	

	/**
	 * 理财产品发售债权
	 */
	@Override
	public boolean autoBuildLoan(FinanceOrder financeOrder) throws Exception {
		Loan loan = buildLoan(financeOrder, queryRepayObj());
		Loan loanNew = loanService.save(loan);
		if(loanNew != null){
			financeOrder.setDealStatus(FinanceOrder.DealStatus.DEAL_SUC);
			financeOrderService.save(financeOrder);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 债权标 组装
	 * @param entity
	 * @param repay
	 * @return
	 * @throws Exception
	 */
	
	public Loan buildLoan(FinanceOrder order, Repay repay) throws Exception{
		Loan loan = new Loan();
		loan.setProduct(generateVirtualProduct(repay));
		loan.setUser(userInfoService.findByUserId(HermesConstants.PLAT_MANAGER_ID));
		loan.setRepay(repay);
		loan.setAmount(order.getLimit());
		loan.setPeriod(Integer.parseInt(order.getTimeLimit().replace("D", "").trim()));
		loan.setDeadline(Integer.parseInt(order.getTimeLimit().replace("D", "").trim()));
		loan.setRate(order.getInterestRate());
		loan.setPurpose(order.getFinanceProductName());
		loan.setDescription(order.getFinanceProductName());
		loan.setLoanKind(Loan.LoanKinds.YLTX_ASSIGN_LOAN);
		loan.setStatus(Loan.Status.BID);
		loan.setCreditInfoId(order.getId()); 
		loan.setManageFeeType("00"); //00: 百分比  01：具体金额
		loan.setProceeds(BigDecimal.ZERO);
		loan.setManageFee(BigDecimal.ZERO); 
		loan.setDescription(HermesConstants.YLTX_CREDIT);
		return loan ;
	}
	
	
	/**
	 * 创建 债权标  虚拟产品
	 * @param repay
	 * @return
	 */
	public Product  generateVirtualProduct(Repay repay) throws Exception{
		Boolean existsFlag = false;
		Product virtualProcut = new  Product();
		try{
			List<Product> lists = productService.findByStatusIn(Product.Status.YLTX_CREDIT_PROCD);
			if(lists!=null && lists.size()>0){
				virtualProcut = lists.get(0);
				existsFlag = true;
			}
		}catch(Exception e){ existsFlag = false; }
		if(!existsFlag){
			virtualProcut.setRepay(repay);
			virtualProcut.setName("易联标虚拟产品");
			virtualProcut.setCode("00");
			virtualProcut.setAmount("0");
			virtualProcut.setPeriod("0");
			virtualProcut.setRate("0");
			virtualProcut.setDeadline(0);
			virtualProcut.setManageFee(BigDecimal.ZERO);
			virtualProcut.setManageFeeType("");
			virtualProcut.setStatus(Product.Status.YLTX_CREDIT_PROCD);
			virtualProcut.setStartingAmt(new BigDecimal("0"));
			virtualProcut.setDescription("易联标虚拟产品配置初始化");
			Product savedProduct=  productService.save(virtualProcut);
			if(savedProduct !=null){
				Logger.info("易联标虚拟产品配置初始化成功");
				virtualProcut = savedProduct;
				// 设置产品对应的 费率
				initCreditRate(savedProduct, new BigDecimal(HermesConstants.YLTX_CREDIT_LEND_FEE), Rate.RateType.LOAN);
				initCreditRate(savedProduct, new BigDecimal(HermesConstants.YLTX_CREDIT_RISK_FEE), Rate.RateType.RISK);
			}
		}
		return virtualProcut;
	}
	/**
	 * 获取 偿还方式
	 * 默认 还本付息
	 * @param repayWay
	 * @return
	 * @throws Exception
	 */
	public Repay  queryRepayObj() throws Exception{
		List<Repay> repayList = repayService.findByNameAndStatusIn(HermesConstants.PAY_TYPE_HBFX, Repay.Status.VALID);
		if(repayList == null || repayList.size() == 0){
			Logger.info("根据 偿还方式="+HermesConstants.PAY_TYPE_HBFX+"状态="+Repay.Status.VALID+",获取对象为空");
			return null;
		}else{
			return repayList.get(0);
		}
	}
	
	/**
	 * 易联债权标： 费率初始化
	 * @param product
	 * @param rateVal
	 * @param type
	 * @throws Exception
	 */
	public void initCreditRate(Product product,BigDecimal rateVal, String type) throws Exception{
		Rate rate = new Rate();
		rate.setProduct(product);
		rate.setRate(rateVal);
		rate.setType(type);
		rate = rateRepository.save(rate);
		if(rate!=null){
		   Logger.info("易联标虚拟产品:"+product.getPeriod()+",费率类型="+type+"),初始化成功");
		}
	}
	
	
	/**
	 * 转换债权信息
	 * @param savedAssetLists
	 * @return
	 * @throws Exception
	 */
	@Override
	public void  transCreditInfo(List<Asset>  savedAssetLists,FinanceOrder financeOrder) throws Exception {
		if(savedAssetLists == null || savedAssetLists.size() == 0  ){
			Logger.info("保存债权人信息失败： 资产信息为空");
			return ;
		}
		for(Asset asset : savedAssetLists){
			//判断creditor 是否存在  根据债权人原始编号 判断唯一
			Creditor existsCreditor =  creditorService.findByOriginNo(asset.getMemberId());
			if(existsCreditor != null){
				Logger.info("资产对应的债权人编号="+asset.getMemberId()+", 已经存在,不进行保存。");
			}else{
				existsCreditor = saveCreditorFromAsset(asset);
			}
			//保存债权信息 债权人+债权编号 判断唯一
			CrediteInfo existsCreditInfo = creditInfoService.findByCreditorAndCrediteCode(existsCreditor, asset.getCode());
			if(existsCreditInfo != null ){
				Logger.info("债权信息已经存在：");
			}else{
				existsCreditInfo = saveCreditInfoFromAsset(financeOrder, existsCreditor, asset);
			}
			//保存债权还款计划
			List<AssetRepayPlan> planEntityList =  new ArrayList<AssetRepayPlan>();
			planEntityList.addAll(asset.getAssetRepayList());
			createCreditInfoPlan(existsCreditInfo, existsCreditor, planEntityList);
		}
		
	}
	/**
	 * 创建资产对应的债权人信息 	
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	public Creditor  saveCreditorFromAsset(Asset asset) throws Exception {
		Creditor  creditor = new Creditor();
		creditor.setCreditorNo(creditorService.generateCreditorNo());
		creditor.setOriginNo(asset.getMemberId());
		creditor.setCreditorName(asset.getTransferorName());
		creditor.setCertType(asset.getTransferorCertiType());
		creditor.setCertificateNo(asset.getTransferorCertNum());
		creditor.setBankAccount(asset.getTransferorBankAccount());
		creditor.setBankName(asset.getTransferorBankCard());
		creditor.setBankProvince(asset.getTransferorBankProvince());
		creditor.setBankCity(asset.getTransferorBankCity());
		creditor.setBankBrantch(asset.getTransferorBankAddress());
		creditor.setContacter(asset.getLinkman());
		creditor.setCellphone(asset.getTel());
		creditor.setMail(asset.getMail());
		creditor.setAssoureType(asset.getGuaranteeWay());
		creditor.setSource(asset.getSource());
		return   creditorService.save(creditor);
	}
	/**
	 * 生成 债权还款计划
	 * @param infoEntity
	 * @param creditor
	 * @param planEntityList
	 * @throws Exception
	 */
	public void  createCreditInfoPlan(CrediteInfo infoEntity, Creditor creditor, List<AssetRepayPlan> planEntityList) throws Exception {
		if(planEntityList == null || planEntityList.size() == 0 ){
			return ;
		}
		AssetRepayPlan  aPlan = planEntityList.get(0);
		CreditRepayPlan cPlan = new  CreditRepayPlan();
		cPlan.setCreditor(creditor);
		cPlan.setCrediteInfo(infoEntity);
		cPlan.setPeriod(aPlan.getPeriod());
		cPlan.setRepayPlanTime(aPlan.getRepaymentDate());
		cPlan.setRepayPrincipal(aPlan.getRepaymentPrincipal());
		cPlan.setRepayInterest(aPlan.getRepaymentInterest());
		cPlan.setRepayAllmount(aPlan.getRepaymentMoney());
		cPlan.setRemainPrincipal(aPlan.getResiduePrincipal());
		cPlan.setStatus(CreditRepayPlan.Status.WAIT_PAY);
		creditRepayPlanService.save(cPlan);
	}
	
	/**
	 * 债权信息 保存
	 * @param creidtor
	 * @param asset
	 * @throws Exception
	 */
	public CrediteInfo saveCreditInfoFromAsset(FinanceOrder financeOrder , Creditor creditor, Asset asset) throws Exception {
		if(creditor == null ){
			return null;
		}
		CrediteInfo  info = new  CrediteInfo();
		info.setCreditor(creditor);
		info.setCrediteCode(asset.getCode());
		info.setCrediteType(asset.getAssetDtlType()) ;
		info.setBorrower(asset.getBorrower());
		info.setCertType(asset.getBorrowerCertiType());
		info.setCertificateNo(asset.getBorrowerCardId());
		info.setWorkType(asset.getBorrowerIndustry());
		info.setProvince(asset.getBorrowerProvince());
		info.setCity(asset.getBorrowerCity());
		info.setAmount(asset.getConfiguredFinAmt());
		info.setSellAmount(asset.getConfiguredFinAmt());
		info.setRate(financeOrder.getInterestRate());
		info.setPeriod(Integer.parseInt(financeOrder.getTimeLimit().replace("D", "")));
		info.setPurpose(asset.getBorrowerPurpose());
		info.setPayType(HermesConstants.PAY_TYPE_HBFX); //asset.getRepaymentMode()
		info.setDeadTime(asset.getBuyBackDate());
		info.setBusinessTime(financeOrder.getRaiseStartTime());
		info.setManageFee(BigDecimal.ZERO);
		info.setStatus(CrediteInfo.Status.BIDING);
		info.setCreditKind(CrediteInfo.CreditKind.YLTX_API);
		info.setAssignTime(new Date());
		info.setDeadLine(calculateDeadLine(financeOrder)); 
		info.setBidEndTime(financeOrder.getRaiseEndTime()); //招标截止时间
		return  creditInfoService.save(info);
	}
	
	/**
	 * 计算招标期限
	 * @param asset
	 * @return
	 */
	public  int  calculateDeadLine(FinanceOrder financeOrder) {
		try {
			return  Calendars.daysBetween(new Date(), financeOrder.getRaiseEndTime());
		} catch (ParseException e) {
			Logger.error("计算招标期限异常:", e);
			return 0 ;
		}
	}
	
	/**
	 * 查询理财产品查询实体拼装
	 * @param financeOrder
	 * @param queryFunanceOrderVo
	 */
	public void buildFinanceOrder(List<FinanceOrder> financeOrderList, QueryFinanceOrderVo queryFunanceOrderVo)
	throws Exception{
		List<FinanceOrderVo> FinanceOrderList =  queryFunanceOrderVo.getContent();
		for(FinanceOrderVo vo: FinanceOrderList){
			//理财产品订单信息
			FinanceOrder  order = new FinanceOrder();
			if(vo!=null){
				vo.toString();
				//过滤状态： 只取 募资中 和 待付款
				if(!(HermesConstants.FINANCE_FUNDRAISING.equals(vo.getStatus()) ||
				   HermesConstants.FINANCE_WAIT_PAY.equals(vo.getStatus())) ){
					Logger.info("跳过处理：理财产品编号：id="+vo.getId()+", 状态="+vo.getStatus());
					continue ;
				}
				order.setFinanceProductName(vo.getFinanceProductName());
				order.setInterestRate(vo.getInterestRate().divide(new BigDecimal("100")));
				order.setTimeLimit(vo.getTimeLimit());
				order.setLimit(vo.getLimit());
				order.setLabelVolmoney(vo.getLabelVolmoney());
				order.setPurchaseAmount(vo.getPurchaseAmount());
				order.setAssetRating(vo.getAssetRating());
				order.setAssetGuarantee(vo.getAssetGuarantee());
				order.setAssetGuaranteeRate(vo.getAssetGuaranteeRate());
				order.setManagementRate(vo.getManagementRate());
				order.setDateOfValue(vo.getDateOfValue());
				order.setFundPurpose(vo.getFundPurpose());
				order.setInstitutionInstruction(vo.getInstitutionInstruction());
				order.setTaxExplain(vo.getTaxExplain());
				order.setRiskTip(vo.getRiskTip());
				order.setStatus(vo.getStatus());
				order.setUniqId(vo.getId());
				order.setRaiseStartTime(vo.getRaiseStartTime());
				order.setRaiseEndTime(vo.getRaiseEndTime());
				//资产信息
				if(vo.getAssetsList() !=null && vo.getAssetsList().size() > 0){
					Set<Asset>  assetSet = new  HashSet<Asset>();
					for(AssetVo voo : vo.getAssetsList()){
						if(voo!=null){
							Asset asset = new  Asset();
							voo.toTrim();
							asset.setCode(voo.getCode());
							asset.setProjectName(voo.getProjectName());
							asset.setRate(voo.getRate());
							asset.setTerm(voo.getTerm());
							asset.setRepaymentMode(voo.getRepaymentMode());
							asset.setAssetRating(voo.getAssetRating());
							asset.setIntroduction(voo.getIntroduction());
							asset.setPurposes(voo.getPurposes());
							asset.setAmt(voo.getAmt());
							asset.setConfiguredFinAmt(voo.getConfiguredFinAmt());
							asset.setLabelVolmoney(voo.getLabelVolmoney());
							asset.setBuyBackDate(voo.getBuyBackDate());
							asset.setMemberId(voo.getMemberId());
							asset.setTransferorName(voo.getTransferorName());
							asset.setTransferorCertiType(voo.getTransferorCertiType());
							asset.setTransferorCertNum(voo.getTransferorCertNum());
							asset.setTransferorBankCard(voo.getTransferorBankCard());
							asset.setTransferorBankProvince(voo.getTransferorBankProvince());
							asset.setTransferorBankCity(voo.getTransferorBankCity());
							asset.setTransferorBankAddress(voo.getTransferorBankAddress());
							asset.setTransferorBankAccount(voo.getTransferorBankAccount());
							asset.setLinkman(voo.getLinkman());
							asset.setTel(voo.getTel());
							asset.setMail(voo.getMail());
							asset.setGuaranteeWay(voo.getGuaranteeWay());
							asset.setSource(voo.getSource());
							asset.setBorrower(voo.getBorrower());
							asset.setBorrowerCertiType(voo.getBorrowerCertiType());
							asset.setBorrowerCardId(voo.getBorrowerCardId());
							asset.setBorrowerIndustry(voo.getBorrowerIndustry());
							asset.setBorrowerProvince(voo.getBorrowerProvince());
							asset.setBorrowerCity(voo.getBorrowerCity());
							asset.setBorrowerAmount(voo.getBorrowerAmount());
							asset.setBorrowerTerm(voo.getBorrowerTerm());
							asset.setBorrowerPurpose(voo.getBorrowerPurpose());
							asset.setAssetDtlType(voo.getAssetDtlType());
							asset.setAssetDtlCreditEndDate(voo.getAssetDtlCreditEndDate());
							asset.setAssetDtlLendingDay(voo.getAssetDtlLendingDay());
							asset.setAssetAttachmentIds(voo.getAssetAttachmentIds());
							assetSet.add(asset);
						}
					}
					order.setAssetsList(assetSet);
				}
			}
			financeOrderList.add(order);
		}
	}
	
	/**
	 * 资产还款计划 实体拼装
	 * @param entity
	 * @param assetRepayPlanVo
	 */
	public List<AssetRepayPlan> transferAssetRepayPlan(Asset asset , List<AssetRepayPlanVo> assetLists) throws Exception{
		List<AssetRepayPlan> planList = new ArrayList<AssetRepayPlan>();
		if(assetLists!= null && assetLists.size() > 0){
			for(AssetRepayPlanVo vo : assetLists){
				AssetRepayPlan plan = new AssetRepayPlan();
				plan.setAsset(asset);
				plan.setPeriod(vo.getPeriod());
				plan.setRepaymentDate(vo.getRepaymentDate());
				plan.setRepaymentPrincipal(vo.getRepaymentPrincipal());
				plan.setRepaymentInterest(vo.getRepaymentInterest());
				plan.setRepaymentMoney(vo.getRepaymentMoney());
				plan.setResiduePrincipal(vo.getResiduePrincipal());
				plan.setUniqId(vo.getUniqId());
				planList.add(plan);
			}
			
		}
		return planList ;
	}
	/**
	 * 理财产品还款计划 实体拼装
	 * @param entity
	 * @param assetRepayPlanVo
	 */
	public List<FinanceRepayPlan> transferFinanceRepayPlan(FinanceOrder financeOrder ,List<AssetRepayPlanVo> list) throws Exception{
		List<FinanceRepayPlan> planList = new ArrayList<FinanceRepayPlan>();
		if(list!=null && list.size() > 0){
			for(AssetRepayPlanVo vo : list){
				FinanceRepayPlan plan  = new FinanceRepayPlan();
				plan.setFinanceOrder(financeOrder);
				plan.setPeriod(vo.getPeriod());
				plan.setRepaymentDate(vo.getRepaymentDate());
				plan.setRepaymentPrincipal(vo.getRepaymentPrincipal());
				plan.setRepaymentInterest(vo.getRepaymentInterest());
				plan.setRepaymentMoney(vo.getRepaymentMoney());
				plan.setResiduePrincipal(vo.getResiduePrincipal());
				plan.setUniqId(vo.getUniqId());
				planList.add(plan);
			}
		}
		return planList ;
	}

}

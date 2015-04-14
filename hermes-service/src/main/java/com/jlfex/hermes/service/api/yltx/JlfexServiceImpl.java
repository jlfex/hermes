package com.jlfex.hermes.service.api.yltx;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
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
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.model.Sequence;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.yltx.Asset;
import com.jlfex.hermes.model.yltx.AssetRepayPlan;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.model.yltx.FinanceRepayPlan;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.repository.LoanRepayRepository;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.apiconfig.ApiConfigRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.CreditRepayPlanService;
import com.jlfex.hermes.service.CreditorService;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.apiLog.ApiLogService;
import com.jlfex.hermes.service.asset.AssetService;
import com.jlfex.hermes.service.assetRepay.AssetRepayPlanService;
import com.jlfex.hermes.service.finance.FinanceOrderService;
import com.jlfex.hermes.service.financePlan.FinanceRepayPlanService;
import com.jlfex.hermes.service.order.jlfex.JlfexOrderService;
import com.jlfex.hermes.service.pojo.yltx.request.OrderPayRequestVo;
import com.jlfex.hermes.service.pojo.yltx.response.AssetVo;
import com.jlfex.hermes.service.pojo.yltx.response.FinanceOrderVo;
import com.jlfex.hermes.service.pojo.yltx.response.PlanResponseVo;
import com.jlfex.hermes.service.pojo.yltx.response.QueryFinanceRspVo;
import com.jlfex.hermes.service.pojo.yltx.response.RepayPlanVo;
import com.jlfex.hermes.service.sequence.SequenceService;


/**
 * 易联天下 对接
 * @author Administrator
 */
@Service
@Transactional
public class JlfexServiceImpl implements JlfexService {

	//开放平台流水号缓存标识
	private  static final String CACHE_YLTX_SERIAL_SEQUENCE = "com.jlfex.cache.request.serial";
	private  static final String CACHE_YLTX_ORDERSN_SEQUENCE = "com.jlfex.cache.request.orderSn";
		
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
	@Autowired
	private  LoanRepayRepository loanRepayRepository;
	@Autowired
	private  UserService userService;
	@Autowired
	private  LoanRepository loanRepository;
	@Autowired
	private  InvestService investService;
	@Autowired
	private  TransactionService transactionService;
	@Autowired
	private  UserAccountRepository userAccountRepository;
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
	    	 reqUrlBuffer.append("createDate=").append(createDate).append("&");
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
	  	if(result!=null && result.contains("status") && result.contains("memo")){
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
		String var = "下单并支付接口:";
	  	String code  	 = null;
	  	String msg   	 = null;
	  	String result    = null;
	  	String exception = null;
	  	Map<String,String> resultMap = new  HashMap<String,String>();
		if(vo == null){
			resultMap.put("code", "99");
			resultMap.put("err_msg",   "下单并支付接口 请求报文实体为空");
			return resultMap;
		}
		if(apiConfig==null){
			apiConfig = getApiConfig();
		}
		vo.setOrderSn(generateOrderSn());
		String serialNo = generateSerialNo(HermesConstants.CODE_ORDER_DO2PAY);
		Map<String,String>  commonMap = HttpClientUtil.buildPostCommonParam(HermesConstants.JL_ORDER_DO2PAY, serialNo);
		commonMap.putAll(Bean2Map.getValueMap(vo));
		//请求日志
		Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod",HermesConstants.JL_ORDER_DO2PAY);
	  	recodeMap.put("requestMsg",commonMap.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	recodeMap.put("orderSn",serialNo );
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	try{
		    HttpClientUtil.initHttps(apiConfig.getClientStoreName(), apiConfig.getClientStorePwd(), apiConfig.getTruestStoreName(), apiConfig.getTruststorePwd());
		    result = HttpClientUtil.doPostHttps(apiConfig.getApiUrl().trim(), commonMap);
		    Logger.info(var+"接口返回结果"+result);
		    apiLog.setResponseMessage(result);
		    apiLog.setResponseTime(new Date());
		    apiLog.setDealFlag(ApiLog.DealResult.SUC);
	  	}catch(SocketTimeoutException se){
	  		Logger.error(var+"请求异常：", se);
	  		apiLog.setException(se.getMessage());
	  		exception ="接口数据读取超时";
	  	}catch(Exception e){
	  		Logger.error(var+"请求异常：", e);
	  		apiLog.setException(e.getMessage());
	  		exception = e.getMessage();
	  	}
	  	apiLogService.saveApiLog(apiLog);
	  	if(exception == null){
	  		//接口404
	  		code = "00";
	  		msg =  result;
	  		if(result.contains("status") && result.contains("memo")){
	  			code = "98";
		  		msg =  JSON.parseObject(result).getString("memo");
			}
	  	}else{
	  		code = "99";
	  		msg =  exception;
	  	}
	  	resultMap.put("code", code);
  		resultMap.put("msg",  msg);
		return resultMap;
	}

	/**
	 * 撤销订单接口
	 */
	@Override
	public String revokeOrder(String orderCode) throws Exception {
		String var = "撤销订单接口:";
		Logger.info("订单: orderCode =" +orderCode+",开始进行撤单");
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
	  		if(Strings.empty(orderCode)){
				throw new Exception("订单编号为空");
			}
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
   /**
    * 查询订单接口
    */
	@Override
	public String queryOrderStatus(String orderCodes) throws Exception {
		String var = "查询订单接口:";
		if(Strings.empty(orderCodes)){
			return null;
		}
		if(apiConfig==null){
			apiConfig = getApiConfig();
		}
		String serialNo = generateSerialNo(HermesConstants.CODE_ORDER_GET);
		StringBuffer reqUrlBuffer = new StringBuffer();
		reqUrlBuffer.append(apiConfig.getApiUrl().trim());
	    reqUrlBuffer.append(HttpClientUtil.buildGetCommonParam(HermesConstants.JL_ORDER_GET, serialNo));
	    if(Strings.notEmpty(orderCodes)){
	    	 reqUrlBuffer.append("orderCodes=").append(orderCodes);
	    }
	    Logger.info(var+"请求地址:"+reqUrlBuffer.toString());
	    //保存请求日志
	  	Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod",HermesConstants.JL_ORDER_GET);
	  	recodeMap.put("requestMsg",reqUrlBuffer.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	String result = null;
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
	  	if(result!=null && result.contains("status") && result.contains("memo")){
			Logger.info(var+" 接口返回结果="+result);
			result = null;
		}
		return result;
	}

	/**
	 * 查询文件协议
	 */
	@Override
	public ByteArrayOutputStream queryProtocolFile(String fileId) throws Exception {
		String var = "查询文件协议接口:";
		if(apiConfig==null){
			apiConfig = getApiConfig();
		}
		String serialNo = generateSerialNo(HermesConstants.CODE_FILE_GET);
		StringBuffer reqUrlBuffer = new StringBuffer();
		reqUrlBuffer.append(apiConfig.getApiUrl().trim());
	    reqUrlBuffer.append(HttpClientUtil.buildGetCommonParam(HermesConstants.JL_FILE_GET, serialNo));
	    if(Strings.notEmpty(fileId)){
	    	 reqUrlBuffer.append("fileId=").append(fileId);
	    }
	    Logger.info(var+"请求地址:"+reqUrlBuffer.toString());
	    //保存请求日志
	  	Map<String,String>  recodeMap = new HashMap<String,String>();
	  	recodeMap.put("interfaceMethod",HermesConstants.JL_FILE_GET);
	  	recodeMap.put("requestMsg",reqUrlBuffer.toString());
	  	recodeMap.put("serialNo", serialNo);
	  	ApiLog apiLog = recordApiLog(apiConfig, recodeMap);
	  	ByteArrayOutputStream inputSM = null;
	  	try{
		    HttpClientUtil.initHttps(apiConfig.getClientStoreName(), apiConfig.getClientStorePwd(), apiConfig.getTruestStoreName(), apiConfig.getTruststorePwd());
		    inputSM = HttpClientUtil.doFileGetHttps(reqUrlBuffer.toString().trim());
		    apiLog.setResponseMessage("");
		    apiLog.setResponseTime(new Date());
		    apiLog.setDealFlag(ApiLog.DealResult.SUC);
	  	}catch(Exception e){
	  		Logger.error(var+"请求异常：", e);
	  		apiLog.setException(e.getMessage());
	  	}
	  	apiLogService.saveApiLog(apiLog);
	  	 return inputSM;
	}
	
	/**
	 * 请求流水号
	 * @param interfaceCode 接口方法编码
	 * @return
	 * @throws Exception
	 */
	public synchronized String generateSerialNo(String interfaceCode) throws Exception {
		Sequence sequence = sequenceService.findBySeqNameAndStatus(HermesConstants.SEQ_YLTX_REQUEST_SERIAL_NO, HermesConstants.VALID);
		if (sequence != null) {
			Caches.set(CACHE_YLTX_SERIAL_SEQUENCE, Long.valueOf(sequence.getCurrentVal().trim()));
		}else{
			throw new Exception("序列serialNo没有初始化");
		}
		Long seq = Caches.incr(CACHE_YLTX_SERIAL_SEQUENCE, sequence.getIncrementVal());// 递增缓存数据
		sequence.setCurrentVal(seq.toString());
		sequenceService.saveSequnce(sequence);
		Logger.info("创建的serialNo="+String.format(interfaceCode+"%012d", seq));
		return String.format(interfaceCode+"%012d", seq);
	}
	
    /**
     * 订单流水号
     * @return
     * @throws Exception
     */
	public synchronized String generateOrderSn() throws Exception {
		Sequence sequence = sequenceService.findBySeqNameAndStatus(HermesConstants.SEQ_YLTX_REQUEST_ORDER_SN, HermesConstants.VALID);
		if (sequence != null) {
			Caches.set(CACHE_YLTX_ORDERSN_SEQUENCE, Long.valueOf(sequence.getCurrentVal()));
		}else{
			throw new Exception("订单流水号序列orderSn没有配置");
		}
		Long seq = Caches.incr(CACHE_YLTX_ORDERSN_SEQUENCE, sequence.getIncrementVal());// 递增缓存数据
		sequence.setCurrentVal(seq.toString());
		sequenceService.saveSequnce(sequence);
		Logger.info("创建的orderSn="+String.format(HermesConstants.PRE_HERMES+"%012d", seq));
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
			recodeMap.put("exception","平台接口配置没有初始化");
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
		apiLog.setDealFlag(ApiLog.DealResult.FAIL);//默认
		if(!Strings.empty(map.get("interfaceMethod"))){
			apiLog.setInterfaceName(map.get("interfaceMethod"));
		}
		if(!Strings.empty(map.get("requestMsg"))){
			apiLog.setRequestMessage(map.get("requestMsg"));
		}
		if(!Strings.empty(map.get("serialNo"))){
			apiLog.setSerialNo(map.get("serialNo"));
		}
		if(!Strings.empty(map.get("exception"))){
			apiLog.setException(map.get("exception"));
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
	 * 理财产品发售
	 * flag :  00 未处理  01 处理成功  02: 重复不处理
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public String sellCreditDeal(FinanceOrder obj) throws Exception {
		String  flag = "00";
		//判断理财是否募资截止
		if(obj.getRaiseEndTime().before(new Date())){
			throw  new Exception("理财产品："+obj.getUniqId()+"募资截止时间："+obj.getRaiseEndTime()+"< 当前系统时间："
		    +Calendars.format(HermesConstants.FORMAT_19)+",已经募资截止,不进行同步处理。");
		}
		if(obj != null){
			FinanceOrder existOrder = financeOrderService.queryByUniqId(obj.getUniqId());
			//判断理财产品是否存在
			if(existOrder == null){
				obj.setDealStatus(FinanceOrder.DealStatus.WAIT_DEAL);
				//保存 易联理财产品信息
				existOrder = financeOrderService.save(obj);
				//保存理财产品还款计划
				saveFinanceRepayPlan(existOrder);
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
				    PlanResponseVo  repayPlan = JSON.parseObject(assetRepayPlanResult,PlanResponseVo.class);
				    if(repayPlan!=null){
				    	List<RepayPlanVo> assetRepayPlanVoLists = repayPlan.getContent(); 
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
     * 保存理财产品 还款计划
     * @param existOrder
     * @param finaceRepayPlanResult
     * @throws Exception
     */
	public void saveFinanceRepayPlan(FinanceOrder existOrder) throws Exception {
		String finaceRepayPlanResult = queryRepayPlan(existOrder.getUniqId().trim(),HermesConstants.TYPE_FINANCE_REPAY_PLAN);
		PlanResponseVo  finceRepayPlan = JSON.parseObject(finaceRepayPlanResult,PlanResponseVo.class);
		if(finceRepayPlan!=null){
			List<RepayPlanVo> financeRepayPlanVoLists = finceRepayPlan.getContent(); 
			if(financeRepayPlanVoLists!=null && financeRepayPlanVoLists.size() > 0){
				List<FinanceRepayPlan> finPlanList = transferFinanceRepayPlan(existOrder,financeRepayPlanVoLists);
				financeRepayPlanService.saveList(finPlanList);
			}
		}
	}
	/**
	 * 理财产品发售债权
	 */
	@Override
	public boolean autoBuildLoan(FinanceOrder financeOrder) throws Exception {
		Loan loan = buildLoan(financeOrder, queryRepayObj());
		Loan loanNew = loanService.save(loan);
		//保存标 还款计划
		List<FinanceRepayPlan> financeRepayPlanList = financeRepayPlanService.queryByFinanceOrder(financeOrder);
		if(financeRepayPlanList==null || financeRepayPlanList.size()== 0){
			throw new Exception("理财产品id="+financeOrder.getUniqId()+",对应的还款计划表为空!");
		}
		List<LoanRepay> loanRepayList = new ArrayList<LoanRepay>();
		saveLoanRepay(loanNew, financeRepayPlanList, loanRepayList);
		if(loanNew != null){
			financeOrder.setDealStatus(FinanceOrder.DealStatus.DEAL_SUC);
			financeOrderService.save(financeOrder);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 保存 标还款计划明细
	 * @param loanNew
	 * @param financeRepayPlanList
	 * @param loanRepayList
	 */
	public void saveLoanRepay(Loan loanNew,List<FinanceRepayPlan> financeRepayPlanList,List<LoanRepay> loanRepayList)
	throws Exception{
		for(FinanceRepayPlan plan :financeRepayPlanList){
			LoanRepay loanRepay = new LoanRepay();
			loanRepay.setLoan(loanNew);
			loanRepay.setSequence(plan.getPeriod());
			loanRepay.setPlanDatetime(plan.getRepaymentDate());
			loanRepay.setAmount(plan.getRepaymentMoney());
			loanRepay.setPrincipal(plan.getRepaymentPrincipal());
			loanRepay.setInterest(plan.getRepaymentInterest());
			loanRepay.setOtherAmount(BigDecimal.ZERO); // 月缴管理费
			loanRepay.setOverdueDays(0);
			loanRepay.setOverdueInterest(BigDecimal.ZERO);
			loanRepay.setOverduePenalty(BigDecimal.ZERO);
			loanRepay.setStatus(LoanRepay.RepayStatus.WAIT);
			loanRepayList.add(loanRepay);
		}
		loanRepayRepository.save(loanRepayList);
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
		loan.setUser(userService.getUserByAccount(HermesConstants.PLAT_MANAGER));
		loan.setRepay(repay);
		loan.setAmount(order.getLimit());
		loan.setPeriod(Integer.parseInt(order.getTimeLimit().replace("D", "").trim()));
		loan.setDeadline(Calendars.format(HermesConstants.FORMAT_19, order.getRaiseEndTime()));
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
	public void buildFinanceOrder(List<FinanceOrder> financeOrderList, QueryFinanceRspVo queryFunanceOrderVo)
	throws Exception{
		List<FinanceOrderVo> FinanceOrderList =  queryFunanceOrderVo.getContent();
		for(FinanceOrderVo vo: FinanceOrderList){
			//理财产品订单信息
			FinanceOrder  order = new FinanceOrder();
			if(vo!=null){
				vo.toString();
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
							asset.setTransferorCertNum(voo.getTransferorCertiNum());
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
	public List<AssetRepayPlan> transferAssetRepayPlan(Asset asset , List<RepayPlanVo> assetLists) throws Exception{
		List<AssetRepayPlan> planList = new ArrayList<AssetRepayPlan>();
		if(assetLists!= null && assetLists.size() > 0){
			for(RepayPlanVo vo : assetLists){
				AssetRepayPlan plan = new AssetRepayPlan();
				plan.setAsset(asset);
				plan.setPeriod(vo.getPeriod());
				plan.setRepaymentDate(vo.getRepaymentDate());
				plan.setRepaymentPrincipal(vo.getRepaymentPrincipal());
				plan.setRepaymentInterest(vo.getRepaymentInterest());
				plan.setRepaymentMoney(vo.getRepaymentMoney());
				plan.setResiduePrincipal(vo.getResiduePrincipal());
				plan.setUniqId(vo.getId());
				plan.setOldInterest(vo.getRepaymentInterest());
				plan.setOldPrincipal(vo.getRepaymentPrincipal());
				plan.setOldMoney(vo.getRepaymentMoney());
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
	public List<FinanceRepayPlan> transferFinanceRepayPlan(FinanceOrder financeOrder ,List<RepayPlanVo> list) throws Exception{
		List<FinanceRepayPlan> planList = new ArrayList<FinanceRepayPlan>();
		if(list!=null && list.size() > 0){
			for(RepayPlanVo vo : list){
				FinanceRepayPlan plan  = new FinanceRepayPlan();
				plan.setFinanceOrder(financeOrder);
				plan.setPeriod(vo.getPeriod());
				plan.setRepaymentDate(vo.getRepaymentDate());
				plan.setRepaymentPrincipal(vo.getRepaymentPrincipal());
				plan.setRepaymentInterest(vo.getRepaymentInterest());
				plan.setRepaymentMoney(vo.getRepaymentMoney());
				plan.setResiduePrincipal(vo.getResiduePrincipal());
				plan.setUniqId(vo.getId());
				plan.setOldInterest(vo.getRepaymentInterest());
				plan.setOldPrincipal(vo.getRepaymentPrincipal());
				plan.setOldMoney(vo.getRepaymentMoney());
				planList.add(plan);
			}
		}
		return planList ;
	}
	
	/**
	 * 募资完成 更新理财产品
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean updateFinishedFinance(FinanceOrder synchObj) throws Exception {
		String var = "理财产品id="+synchObj.getUniqId();
		Logger.info(var+" 开始进行实际募资金额更新，理财产品起息日："+Calendars.format(HermesConstants.FORMAT_19, synchObj.getDateOfValue()));
		if(new Date().before(synchObj.getDateOfValue())){
			throw new Exception("理财产品没有起息,跳过处理：当前系统时间:"+Calendars.format(HermesConstants.FORMAT_19)+"< 理财产品起息日:"+Calendars.format(HermesConstants.FORMAT_19, synchObj.getDateOfValue()));
		}
		FinanceOrder existOrder = financeOrderService.queryByUniqId(synchObj.getUniqId());
		if(existOrder == null){
		   Logger.info("当前理财产品id="+synchObj.getUniqId()+", 状态="+synchObj.getStatus()+", 系统不存在，不需更新处理");
		}else{
			if(HermesConstants.FINANCE_FINISHED.equals(existOrder.getStatus())){
				Logger.info(var+",已经同步处理过不需要再次同步, 数据中状态是"+existOrder.getStatus());
				return  false;
			}
		}
		existOrder.setLabelVolmoney(synchObj.getLabelVolmoney());
		//更新实际募资金额
		existOrder = updateJlfexLoanAmount(synchObj, existOrder);
		Logger.info(var+" 实际募资金额更新为："+synchObj.getLabelVolmoney());
		//更新 理财产品还款计划
		updateFinancePlan(existOrder);
		//更新 子资产 还款计划 
		updateAssetAndPlan(existOrder,synchObj);
		//理财人冻结金额转账给债权人
		freeAndTransferInvest(existOrder);
		return  true;
	}

	/**
	 * 理财人冻结金额转账给债权人
	 * @param existOrder
	 * @throws Exception
	 */
	public void freeAndTransferInvest(FinanceOrder existOrder) throws Exception {
		List<Loan> loanList = loanRepository.findByCreditInfoAndLoanKind(existOrder.getId(), Loan.LoanKinds.YLTX_ASSIGN_LOAN);
		if(loanList!=null && loanList.size() == 1){
			Loan loan = loanList.get(0);
			Set<Asset>  assetSet =  existOrder.getAssetsList(); 
			if(assetSet==null || assetSet.size() != 1){
				throw new Exception("理财id="+existOrder.getUniqId()+",资产数不唯一");
			}
			Asset asset = CollectionUtil.switchSet2List(assetSet).get(0);
			User loanUser = creditInfoService.findByCrediteCode(asset.getCode()).getCreditor().getUser();
			List<Invest> investList = investService.findByLoanAndStatus(loan, Invest.Status.FREEZE);
			if(investList!= null && investList.size() > 0){
				for(Invest invest : investList){
					// 解冻
					transactionService.unfreeze(Transaction.Type.UNFREEZE, invest.getUser(), invest.getAmount(), invest.getId(), "jlfex放款解冻投标金额");
					// 转账
					transactionService.transact(Transaction.Type.OUT, invest.getUser(), loanUser, invest.getAmount(), invest.getId(), "jlfex放款理财人到借款人");
				}
			}
			//jlfex债权人现金账户重置0
			UserAccount sourceUserAccount = userAccountRepository.findByUserAndType(loanUser, UserAccount.Type.CASH);
			sourceUserAccount.setBalance(BigDecimal.ZERO);
			userAccountRepository.save(sourceUserAccount);
		}
	}
    /**
     * 更新理财产品 的子资产 及 子资产还款计划
     * @param existOrder
     * @throws Exception
     */
	public void updateAssetAndPlan(FinanceOrder existOrder, FinanceOrder synchObj) throws Exception {
		List<Asset>  assetList = CollectionUtil.switchSet2List(existOrder.getAssetsList());
		for(Asset asset_obj: assetList){
			Set<AssetRepayPlan> exists_Plans =  asset_obj.getAssetRepayList();
			if(exists_Plans!= null && exists_Plans.size() >0){
				List<AssetRepayPlan> existsPlan = CollectionUtil.switchSet2List(exists_Plans);  
				//更新子资产金额
				List<Asset>  _assetList = new ArrayList<Asset>();
				Iterator<Asset> iterator = synchObj.getAssetsList().iterator();
				while(iterator.hasNext()){
					Asset synchAsset = (Asset) iterator.next();
					if(asset_obj.getCode().equals(synchAsset.getCode())){
						asset_obj.setLabelVolmoney(synchAsset.getLabelVolmoney());
						_assetList.add(asset_obj);
					}
				}
				assetService.save(_assetList);
				Logger.info("子资产成交金额更新成功");
				String assetPlanJson = queryRepayPlan(asset_obj.getCode(),HermesConstants.TYPE_ASSET_REPAY_PLAN);
				if(assetPlanJson!=null){
					PlanResponseVo  assetRepayPlan = JSON.parseObject(assetPlanJson,PlanResponseVo.class);
					List<RepayPlanVo> repayPlanVoLists = assetRepayPlan.getContent(); 
					for(RepayPlanVo vo :repayPlanVoLists){
						String id = vo.getId();
						for(AssetRepayPlan plan :existsPlan){
							if(id.equals(plan.getUniqId())){
								plan.setRepaymentInterest(vo.getRepaymentInterest());
								plan.setRepaymentMoney(vo.getRepaymentMoney());
								plan.setRepaymentPrincipal(vo.getRepaymentPrincipal());
							}
						}
					}
					assetRepayPlanService.save(existsPlan);
				}else{
				    Logger.info("资产编号code="+asset_obj.getCode()+",对应的还款计划，还款计划为空");
				}
			}else{
				Logger.warn("子资产编号="+asset_obj.getCode()+",对应的还款计划数据库中不存在，无法进行更新");
			}
		}
	}
    /**
     * 更新理财产品还款计划
     * @param existOrder
     * @throws Exception
     */
	public void updateFinancePlan(FinanceOrder existOrder) throws Exception {
		String financePlanJson = queryRepayPlan(existOrder.getUniqId(),HermesConstants.TYPE_FINANCE_REPAY_PLAN);
		if(financePlanJson != null){
			 PlanResponseVo  finceRepayPlan = JSON.parseObject(financePlanJson,PlanResponseVo.class);
			 if(finceRepayPlan!=null){
				 List<RepayPlanVo> financeRepayPlanVoLists = finceRepayPlan.getContent(); 
					if(financeRepayPlanVoLists!=null && financeRepayPlanVoLists.size() > 0){
						List<FinanceRepayPlan> existsFinPlan = financeRepayPlanService.queryByFinanceOrder(existOrder);
						for(RepayPlanVo vo: financeRepayPlanVoLists){
							String uniqId = vo.getId().trim();
							for(FinanceRepayPlan entity : existsFinPlan){
								if(uniqId.equals(entity.getUniqId())){
									entity.setRepaymentInterest(vo.getRepaymentInterest());
									entity.setRepaymentPrincipal(vo.getRepaymentPrincipal());
									entity.setRepaymentMoney(vo.getRepaymentMoney());
								}
							}
						}
						financeRepayPlanService.saveList(existsFinPlan);
					}
			    }
		}else{
			Logger.info("理财产品还款计划获取为空");
		}
	}

	/**
	 * 更新理财产品时间募资金额，同时更新loan金额
	 * @param obj
	 * @param existOrder
	 */
	public FinanceOrder updateJlfexLoanAmount(FinanceOrder synchObj, FinanceOrder existOrder) {
		try {
			List<Loan> loanList = loanRepository.findByCreditInfoAndLoanKind(existOrder.getId(), Loan.LoanKinds.YLTX_ASSIGN_LOAN);
			if(loanList==null || loanList.size() != 1){
				Logger.error("jlfexLoan更新loan金额: 根据理财产品:"+existOrder.getUniqId()+",获取标数据不唯一或空");
				return null;
			}else{
				Loan loan = loanList.get(0);
				loan.setAmount(synchObj.getLabelVolmoney());
				loanService.save(loan);
				existOrder.setLabelVolmoney(synchObj.getLabelVolmoney());
				existOrder.setStatus(synchObj.getStatus());
				return financeOrderService.save(existOrder);
			}
		} catch (Exception e) {
			Logger.error("更新jlfexLoan发售金额异常：", e);
			return null;
		}
	}

}

package com.jlfex.hermes.service.cfca;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cfca.payment.api.system.TxMessenger;
import cfca.payment.api.tx.Tx1341Request;
import cfca.payment.api.tx.Tx134xResponse;
import cfca.payment.api.tx.Tx1350Request;
import cfca.payment.api.tx.Tx1350Response;
import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;
import cfca.util.GUID;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.constant.HermesEnum.P2ZJBank;
import com.jlfex.hermes.common.constant.HermesEnum.P2ZJIdType;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.apiconfig.ApiConfigRepository;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;
import com.jlfex.hermes.service.BankAccountService;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.apiLog.ApiLogService;

/**
 * 中金订单服务
 * @author jswu
 *
 */
@Service
@Transactional
public class CFCAOrderServiceImpl implements CFCAOrderService {

	@Autowired
	private CFCAOrderRepository cFCAOrderRepository;
	@Autowired
	private ApiLogService apiLogService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private ApiConfigRepository apiConfigRepository;
	//接口配置
	public static ApiConfig   zjApiCfg = null;
	
	/**
	 * 中金请求流水号：
	 * 代扣： IN
	 * 清算     OUT
	 */
	@Override
	public  String genSerialNo(String type) {
		if(HermesConstants.PRE_IN.equals(type) ||
		   HermesConstants.PRE_OUT.equals(type)){
			return String.format(type+"%s", GUID.getTxNo());
		}else{
			return  null;
		}
	}

	/**
	 * 记录调用中金日志
	 * 
	 * @param apiConfig
	 * @param map
	 * @return
	 */
	public ApiLog recordApiLog(Map<String, String> map) {
		ApiLog apiLog = new ApiLog();
		apiLog.setCreator(HermesConstants.PLAT_MANAGER);
		apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
		apiLog.setRequestTime(new Date());
		// 默认
		apiLog.setDealFlag(ApiLog.DealResult.FAIL);
		if (!Strings.empty(map.get("interfaceMethod"))) {
			apiLog.setInterfaceName(map.get("interfaceMethod"));
		}
		if (!Strings.empty(map.get("requestMsg"))) {
			apiLog.setRequestMessage(map.get("requestMsg"));
		}
		if(!Strings.empty(map.get("serialNo"))){
			apiLog.setSerialNo(map.get("serialNo"));
		}
		if (!Strings.empty(map.get("exception"))) {
			apiLog.setException(map.get("exception"));
		}
		try {
			return apiLogService.saveApiLog(apiLog);
		} catch (Exception e) {
			Logger.error("接口日志对象保存异常：", e);
			return null;
		}
	}

	/**
	 * 生成1361报文
	 */
	
	@Override
	@Transactional(readOnly=true)
	public Tx1361Request buildTx1361Request(User investUser, BigDecimal investAmount, BankAccount bankAccount, UserProperties userProperties, String serialNo) throws Exception {
		Tx1361Request tx1361Request = new Tx1361Request();
		tx1361Request.setInstitutionID(App.config(HermesConstants.CFCA_INSTITUTION_ID_CODE));
		tx1361Request.setTxSN(serialNo);
		tx1361Request.setOrderNo(App.config(HermesConstants.CFCA_MARKET_ORDER_NO_CODE));
		tx1361Request.setAmount(investAmount.multiply(new BigDecimal(100)).longValue());
		tx1361Request.setBankID(Dicts.name(bankAccount.getBank().getName(), "", P2ZJBank.class));
		tx1361Request.setAccountName(bankAccount.getName());
		tx1361Request.setAccountNumber(bankAccount.getAccount());
		tx1361Request.setBranchName(bankAccount.getDeposit());
		tx1361Request.setProvince(bankAccount.getCity().getName());
		tx1361Request.setAccountType(HermesConstants.ZJ_ACCOUNT_TYPE_11);
		tx1361Request.setCity(bankAccount.getCity().getName());
		tx1361Request.setPhoneNumber(investUser.getCellphone());
		tx1361Request.setEmail(investUser.getEmail());
		tx1361Request.setIdentificationNumber(userProperties.getIdNumber());
		tx1361Request.setIdentificationType(Dicts.name(userProperties.getIdType(), P2ZJIdType.class));
		return tx1361Request;
	}

	/**
	 * 生成中金订单
	 */
	@Override
	public CFCAOrder genCFCAOrder(Tx1361Response response, Invest invest, BigDecimal investAmount, String serialNo, String type) {
		CFCAOrder cfcaOrder = new CFCAOrder();
		cfcaOrder.setAmount(investAmount);
		cfcaOrder.setInvest(invest);
		cfcaOrder.setBankTxTime(response.getBankTxTime());
		cfcaOrder.setCode(response.getCode());
		cfcaOrder.setMessage(response.getMessage());
		cfcaOrder.setOrderNo(response.getOrderNo());
		cfcaOrder.setResponseCode(response.getResponseCode());
		cfcaOrder.setResponseMessage(response.getResponseMessage());
		cfcaOrder.setStatus(response.getStatus());
		cfcaOrder.setTxSN(serialNo);
		cfcaOrder.setUser(invest.getUser());
		cfcaOrder.setType(type);
		return cFCAOrderRepository.save(cfcaOrder);
	}

	@Override
	public CFCAOrder genCFCAOrder(Tx1361Response response, User user, BigDecimal investAmount, String serialNo, String type, BigDecimal fee) {
		CFCAOrder cfcaOrder = new CFCAOrder();
		cfcaOrder.setAmount(investAmount);
		cfcaOrder.setInvest(null);
		cfcaOrder.setFee(fee);
		cfcaOrder.setBankTxTime(response.getBankTxTime());
		cfcaOrder.setCode(response.getCode());
		cfcaOrder.setMessage(response.getMessage());
		cfcaOrder.setOrderNo(response.getOrderNo());
		cfcaOrder.setResponseCode(response.getResponseCode());
		cfcaOrder.setResponseMessage(response.getResponseMessage());
		cfcaOrder.setStatus(response.getStatus());
		cfcaOrder.setTxSN(serialNo);
		cfcaOrder.setUser(user);
		cfcaOrder.setType(type);
		return cFCAOrderRepository.save(cfcaOrder);
	}

	/**
	 * 1341-市场订单结算： 创建请求
	 */
	@Override
	public Tx1341Request buildTx1341Request(User investUser,BigDecimal withdrawAmount) {
		BankAccount  userbankAccount = bankAccountService.findOneByUserIdAndStatus(investUser.getId(), BankAccount.Status.ENABLED);
        Tx1341Request tx1341Request = new Tx1341Request();
        tx1341Request.setInstitutionID(App.config(HermesConstants.CFCA_INSTITUTION_ID_CODE));
        tx1341Request.setSerialNumber(String.format(HermesConstants.PRE_OUT+"%s", GUID.getTxNo()));
        tx1341Request.setOrderNo(App.config(HermesConstants.CFCA_MARKET_ORDER_NO_CODE));
        tx1341Request.setAmount(withdrawAmount.longValue());
        tx1341Request.setRemark(HermesConstants.CLEAR_NOTE);
        tx1341Request.setAccountType(HermesConstants.ZJ_ACCOUNT_TYPE_11);
        cfca.payment.api.vo.BankAccount cfcaBankAccount = new cfca.payment.api.vo.BankAccount();
        cfcaBankAccount.setBankID(Dicts.name(userbankAccount.getBank().getName(), "", P2ZJBank.class));
        cfcaBankAccount.setAccountName(userbankAccount.getName());
        cfcaBankAccount.setAccountNumber(userbankAccount.getAccount());
        cfcaBankAccount.setBranchName(userbankAccount.getDeposit());
        cfcaBankAccount.setProvince(userbankAccount.getCity().getName());
        cfcaBankAccount.setCity(userbankAccount.getCity().getName());
        tx1341Request.setBankAccount(cfcaBankAccount);
		return tx1341Request;
	}
	/**
	 * 1341-市场订单结算：调用接口
	 */
	@Override
	public Tx134xResponse invokeTx1341(Tx1341Request request) throws Exception{
		String var = "调用中金1341结算接口：";
		ApiLog apiLog = new ApiLog();
		Tx134xResponse response = null ;
		try {
			apiLog.setInterfaceName(HermesConstants.ZJ_INTERFACE_TX1341);
			apiLog.setCreator(HermesConstants.PLAT_MANAGER);
			apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
			apiLog.setSerialNo(request.getSerialNumber());
			request.process();
			String requestXml = new String(Base64.decodeBase64(request.getRequestMessage()),HermesConstants.CHARSET_UTF8);
			Logger.info(var+"请求报文：%s", requestXml);
			apiLog.setRequestMessage(requestXml);
			apiLog.setRequestTime(new Date());
			//发送请求
			TxMessenger txMessenger = new TxMessenger();
			String[] respMsg = txMessenger.send(request.getRequestMessage(), request.getRequestSignature());
			response = new Tx134xResponse(respMsg[0], respMsg[1]);
			Logger.info(var+"响应报文:%s ", response.getResponsePlainText());
			apiLog.setResponseTime(new Date());
			apiLog.setResponseMessage(response.getResponsePlainText());
			apiLog.setDealFlag(ApiLog.DealResult.SUC);
		} catch (Exception e) {
			Logger.error("调用中金接口异常：", e);
			apiLog.setResponseTime(new Date());
			apiLog.setException("接口异常："+e.getMessage());
			apiLog.setDealFlag(ApiLog.DealResult.FAIL);
		}
		if(zjApiCfg == null){
			zjApiCfg =  getApiConfig();
		}
		apiLog.setApiConfig(zjApiCfg);
		apiLogService.saveApiLog(apiLog);
	    return response;
	}
	
	/**
	 * 1341-市场订单结算：保存订单
	 */
	@Override
	public CFCAOrder genClearOrder(Tx134xResponse response, User investUser, BigDecimal investAmount,BigDecimal fee, String txSN, String type) {
		CFCAOrder cfcaOrder = new CFCAOrder();
		cfcaOrder.setAmount(investAmount);
		cfcaOrder.setFee(fee);
		cfcaOrder.setInvest(null);
		cfcaOrder.setCode(response.getCode());
		cfcaOrder.setMessage(response.getMessage());
		cfcaOrder.setOrderNo(App.config(HermesConstants.CFCA_MARKET_ORDER_NO_CODE));
		cfcaOrder.setTxSN(txSN);
		cfcaOrder.setStatus(CFCAOrder.ClearStatus.CLEAR_INIT);
		cfcaOrder.setUser(investUser);
		cfcaOrder.setType(type);
		return cFCAOrderRepository.save(cfcaOrder);
	}

	/**
	 * 1350-市场订单结算交易查询
	 */
	@Override
	public Tx1350Response invokeTx1350Request(String serialNumber) throws Exception {
		String var = "调用中金1350结算查询接口：";
		//保存 接口日志
		ApiLog apiLog = new ApiLog();
		apiLog.setInterfaceName(HermesConstants.ZJ_INTERFACE_TX1350);
		apiLog.setCreator(HermesConstants.PLAT_MANAGER);
		apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
		apiLog.setSerialNo(serialNumber);
		Tx1350Request tx1350Request = new Tx1350Request();
	    tx1350Request.setInstitutionID(App.config(HermesConstants.CFCA_INSTITUTION_ID_CODE));
	    tx1350Request.setSerialNumber(serialNumber);
	    Tx1350Response response = null;
		try {
			tx1350Request.process();
			String requestXml = new String(Base64.decodeBase64(tx1350Request.getRequestMessage()),HermesConstants.CHARSET_UTF8);
			Logger.info(var+"请求报文：%s", requestXml);
			apiLog.setRequestMessage(requestXml);
			apiLog.setRequestTime(new Date());
			TxMessenger txMessenger = new TxMessenger();
			//与支付平台进行通讯
			String[] result = txMessenger.send(tx1350Request.getRequestMessage(), tx1350Request.getRequestSignature());
			response = new Tx1350Response(result[0], result[1]);
			Logger.info(var+"响应报文:%s ", response.getResponsePlainText());
			apiLog.setResponseTime(new Date());
			apiLog.setResponseMessage(response.getResponsePlainText());
			apiLog.setDealFlag(ApiLog.DealResult.SUC);
		} catch (Exception e) {
			Logger.error(var, e);
			apiLog.setResponseTime(new Date());
			apiLog.setException(e.getMessage());
			apiLog.setDealFlag(ApiLog.DealResult.FAIL);
		}
		if(zjApiCfg==null){
			zjApiCfg = getApiConfig();
		}
		apiLog.setApiConfig(zjApiCfg);
		apiLogService.saveApiLog(apiLog);
		return response;
	}

	/**
	 * 获取第三方平台配置信息
	 * @return
	 * @throws Exception
	 */
	public ApiConfig getApiConfig() throws Exception{
		ApiConfig  apiConfig = apiConfigRepository.findByPlatCodeAndStatus(HermesConstants.PLAT_ZJ_CODE, HermesConstants.VALID);
		if(apiConfig == null){
			throw new  Exception("平台接口配置没有初始化");
		}
		return  apiConfig;
	}

}

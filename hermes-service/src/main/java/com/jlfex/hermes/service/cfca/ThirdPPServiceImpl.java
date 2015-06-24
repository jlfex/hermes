package com.jlfex.hermes.service.cfca;

import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.repository.apiconfig.ApiConfigRepository;
import com.jlfex.hermes.service.apiLog.ApiLogService;
import cfca.payment.api.system.TxMessenger;
import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;
import cfca.payment.api.tx.Tx1362Request;
import cfca.payment.api.tx.Tx1362Response;

/**
 * 中金支付接口实现
 * 
 * @author wujinsong
 *
 */
@Service
@Transactional
public class ThirdPPServiceImpl implements ThirdPPService {

	@Autowired
	private  ApiConfigRepository apiConfigRepository;
	@Autowired
	private  ApiLogService  apiLogService;
	private  static ApiConfig  zJApiCfg = null;
	
	@Override
	public Tx1361Response invokeTx1361(Tx1361Request request) throws Exception {
		ApiLog apiLog = new ApiLog();
		Tx1361Response response = null ;
		try {
			Logger.info("调用中金支付接口，请求消息："+"订单流水号："+request.getTxSN()+"金额："+request.getAmount()+"账户名称："+request.getAccountName());
			apiLog.setInterfaceName(HermesConstants.ZJ_INTERFACE_TX1361);
			apiLog.setCreator(HermesConstants.PLAT_MANAGER);
			apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
			apiLog.setSerialNo(request.getTxSN());
			request.process();
			String requestXml = new String(Base64.decodeBase64(request.getRequestMessage()),HermesConstants.CHARSET_UTF8);
			apiLog.setRequestMessage(requestXml);
			apiLog.setRequestTime(new Date());
			
			TxMessenger messenger = new TxMessenger();
			String[] respMsg = messenger.send(request.getRequestMessage(), request.getRequestSignature());
			response = new Tx1361Response(respMsg[0], respMsg[1]);
			Logger.info("中金支付返回信息："+ response.getResponsePlainText());
			apiLog.setResponseTime(new Date());
			apiLog.setResponseMessage(response.getResponsePlainText());
			apiLog.setDealFlag(ApiLog.DealResult.SUC);
		} catch (Exception e) {
			Logger.error("中金市场订单代扣1361接口异常：", e);
			apiLog.setResponseTime(new Date());
			apiLog.setException("1361接口异常："+e.getMessage());
			apiLog.setDealFlag(ApiLog.DealResult.FAIL);
		}
		if(zJApiCfg == null){
			zJApiCfg = getApiConfig();
		}
		apiLog.setApiConfig(zJApiCfg);
		apiLogService.saveApiLog(apiLog);
		return response; 
	}

	@Override
	public Tx1362Response invokeTx1362(Tx1362Request request) {
		try {
			Logger.info("中金市场订单1362接口,发起请求");
			request.process();

			TxMessenger messenger = new TxMessenger();
			String[] respMsg;
			respMsg = messenger.send(request.getRequestMessage(), request.getRequestSignature());
			Tx1362Response response = new Tx1362Response(respMsg[0], respMsg[1]);

			return response;
		} catch (Exception e) {
			Logger.error("中金市场订单代扣查询1362接口异常：", e);
			return null;
		}
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

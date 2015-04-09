package com.jlfex.hermes.service.cfca;

import java.util.Map;

import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.model.ApiLog;

/**
 * 中金订单服务类
 * 
 * @author jswu
 *
 */
public interface CFCAOrderService {
	public String genOrderTxSN();
	
	public ApiLog recordApiLog(Map<String, String> map);
}

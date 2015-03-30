package com.jlfex.hermes.service.apiLog;

import com.jlfex.hermes.model.ApiLog;

/**
 * 外围系统交互日志
 * @author Administrator
 *
 */
public interface ApiLogService {
	
	public ApiLog  saveApiLog(ApiLog apiLog) throws Exception  ;

}

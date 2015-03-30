package com.jlfex.hermes.service.apiLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.repository.apiLog.ApiLogRepository;


/**
 * 外围系统日志
 * @author Administrator
 *
 */
@Service
@Transactional
public class ApiLogServiceImpl implements  ApiLogService {

	@Autowired
	private ApiLogRepository apiLogRepository;
	
	/**
	 * 保存交互日志
	 */
	@Override
	public ApiLog saveApiLog(ApiLog apiLog) throws Exception {
		return apiLogRepository.save(apiLog);
	}
	
	

}

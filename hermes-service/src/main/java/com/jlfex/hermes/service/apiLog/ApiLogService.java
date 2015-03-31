package com.jlfex.hermes.service.apiLog;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.service.pojo.yltx.ApiLogVo;

/**
 * 外围系统交互日志
 * @author Administrator
 *
 */
public interface ApiLogService {
	
	public ApiLog  saveApiLog(ApiLog apiLog) throws Exception  ;
	/**
	 * 外围日志列表
	 */
	public Page<ApiLog>  queryByCondition(ApiLogVo apiLogVo,String page, String size) throws Exception ;

	public ApiLog  findOne(String id);

}

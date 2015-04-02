package com.jlfex.hermes.service.apiconfig;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.service.pojo.yltx.ApiConfigVo;

public interface ApiConfigService {

	public ApiConfig  queryByPlatCodeAndStatus(String platCode, String status) throws Exception;
	
	public Page<ApiConfig>  queryByCondition(ApiConfigVo apiConfigVo,String page, String size) throws Exception ;
	
	public ApiConfig  addOrUpdateApiConfig(ApiConfigVo apiConfigVo);
		
	public List<ApiConfig>  findByPlatCode(String platCode);
	
	public ApiConfig  findById(String id);
	
	public void  delApiConfig(String id);

}

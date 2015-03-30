package com.jlfex.hermes.service.apiconfig;

import com.jlfex.hermes.model.ApiConfig;

public interface ApiConfigService {

	public ApiConfig  queryByPlatCodeAndStatus(String platCode, String status) throws Exception;
	
}

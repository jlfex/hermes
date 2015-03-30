package com.jlfex.hermes.service.apiconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.repository.apiconfig.ApiConfigRepository;
import com.jlfex.hermes.service.CreditorService;


@Service
@Transactional
public class ApiconfigServiceImpl implements ApiConfigService {

	
	@Autowired
	private  ApiConfigRepository  apiConfigRepository;
	@Autowired
	private  CreditorService  creditorService;
	
	@Override
	public ApiConfig  queryByPlatCodeAndStatus(String platCode, String status) throws Exception {
		return apiConfigRepository.findByPlatCodeAndStatus(platCode, status);
	}

	
}

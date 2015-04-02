package com.jlfex.hermes.service.apiconfig;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.repository.apiconfig.ApiConfigRepository;
import com.jlfex.hermes.service.CreditorService;
import com.jlfex.hermes.service.pojo.yltx.ApiConfigVo;


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

	@Override
	public Page<ApiConfig> queryByCondition(final ApiConfigVo apiConfigVo, String page, String size) throws Exception {
		 Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC,  "createTime"));
		 return  apiConfigRepository.findAll(new Specification<ApiConfig>() {
			@Override
			public Predicate toPredicate(Root<ApiConfig> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(apiConfigVo.getPlatCode())) {
					list.add(cb.like(root.<String>get("platCode"), "%"+apiConfigVo.getPlatCode().trim()+"%"));
				}
				if (StringUtils.isNotEmpty(apiConfigVo.getClientStoreName())) {
					list.add(cb.like(root.<String>get("clientStoreName"), "%"+apiConfigVo.getClientStoreName().trim()+"%"));
				}
				if(StringUtils.isNotEmpty(apiConfigVo.getTruestStoreName())){
					list.add(cb.like(root.<String>get("truestStoreName"), "%"+apiConfigVo.getTruestStoreName().trim()+"%"));
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		},pageable);
	}

	@Override
	public ApiConfig addOrUpdateApiConfig(ApiConfigVo apiConfigVo) {
		ApiConfig apiConfig=null;
		if(StringUtils.isEmpty(apiConfigVo.getId())){
			apiConfig = new ApiConfig();
			apiConfig.setPlatCode(apiConfigVo.getPlatCode());
		}else{
			apiConfig = apiConfigRepository.findOne(apiConfigVo.getId());
			apiConfig.setPlatCode(apiConfig.getPlatCode());
		}		
		apiConfig.setClientStoreName(apiConfigVo.getClientStoreName());
		apiConfig.setClientStorePwd(apiConfigVo.getClientStorePwd());
		apiConfig.setTruestStoreName(apiConfigVo.getTruestStoreName());
		apiConfig.setTruststorePwd(apiConfigVo.getTruststorePwd());
		apiConfig.setApiUrl(apiConfigVo.getApiUrl());
		apiConfig.setStatus(apiConfigVo.getStatus());
		apiConfigRepository.save(apiConfig);
		return apiConfig;
	}

	@Override
	public List<ApiConfig> findByPlatCode(String platCode) {
		return apiConfigRepository.findByPlatCode(platCode);
	}

	@Override
	public ApiConfig findById(String id) {
		return apiConfigRepository.findOne(id);
	}

	@Override
	public void delApiConfig(String id) {
		apiConfigRepository.delete(id);
	}
	
}

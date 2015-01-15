package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.repository.CreditInfoRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.common.Pageables;


/**
 * 债权 信息 管理
 * @author Administrator
 *
 */
@Service
@Transactional
public class CreditInfoServiceImpl  implements CreditInfoService {

	@Autowired
	private CreditInfoRepository creditInfoRepository;
	

	@Override
	public List<CrediteInfo> findAll() {
	  return creditInfoRepository.findAll();
	}

	@Override
	public void save(CrediteInfo crediteInfo) throws Exception {
		creditInfoRepository.save(crediteInfo);
	}


	@Override
	public List<CrediteInfo>  saveBatch(List<CrediteInfo> list) throws Exception {
		return creditInfoRepository.save(list);
	}

	@Override
	public CrediteInfo findByCreditorAndCrediteCode(Creditor creditor ,String crediteCode)  {
		try{
			if(Strings.empty(crediteCode)){
				return null ;
			}
			return creditInfoRepository.findByCreditorAndCrediteCode(creditor, crediteCode.trim());
		}catch(Exception e){
			Logger.info("根据债权人+债权编号 获取债权信息异常", e);
			return  null;
		}
	}
	/**
	 * 债权 列表
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<CrediteInfo>  queryByCondition(String page, String size) throws Exception{
		 Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")));
		 return creditInfoRepository.findAll(null,pageable);
	}

	/**
	 * 根据id 获取债权信息
	 */
	@Override
	public CrediteInfo findById(String id) throws Exception {
		return creditInfoRepository.findOne(id);
	}
	
    
}

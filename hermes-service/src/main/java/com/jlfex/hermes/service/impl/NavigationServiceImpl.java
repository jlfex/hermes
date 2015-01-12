package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.service.NavigationService;

/**
 * 导航业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-18
 * @since 1.0
 */
@Service
@Transactional
public class NavigationServiceImpl implements NavigationService {

	private static final String CODE_DICTIONARY_NAVIGATION = "nav";
	
	/** 导航信息仓库 */
	@Autowired
	private NavigationRepository navigationRepository;
	
	/** 字典信息仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.NavigationService#loadById(java.lang.String)
	 */
	@Override
	public Navigation loadById(String id) {
		return navigationRepository.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.NavigationService#findRootByTypeCode(java.lang.String)
	 */
	@Override
	public List<Navigation> findRootByTypeCode(String typeCode) {
		if(Strings.empty(typeCode)){
			throw new ServiceException("导航类型码：typeCode为空");
		}
		Dictionary type = dictionaryRepository.findByTypeCodeAndCode(CODE_DICTIONARY_NAVIGATION, typeCode);
		List<Navigation> navigations = navigationRepository.findByRootAndType(type);
		return navigations;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.NavigationService#findByParent(com.jlfex.hermes.model.Navigation)
	 */
	@Override
	public List<Navigation> findByParent(Navigation parent) {
		if(parent == null){
			throw new ServiceException("导航：parent为空");
		}
		List<Navigation> navigations = navigationRepository.findByParent(parent);
		return navigations;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.NavigationService#findByParentId(java.lang.String)
	 */
	@Override
	public List<Navigation> findByParentId(String parentId) {
		if(Strings.empty(parentId)){
			throw new ServiceException("导航：parentId为空");
		}
		Navigation parent = navigationRepository.findOne(parentId);
		return findByParent(parent);
	}
}

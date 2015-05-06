package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.service.DictionaryService;

/**
 * 字典业务接口
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

	private static final String CACHE_PREFIX = "com.jlfex.hermes.cache.dictionary.";
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** 字典信息仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.DictionaryService#loadById(java.lang.String)
	 */
	@Override
	public Dictionary loadById(String id) {
		return dictionaryRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.DictionaryService#loadByTypeCodeAndCode(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public Dictionary loadByTypeCodeAndCode(String typeCode, String code) {
		return dictionaryRepository.findByTypeCodeAndCode(typeCode, code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.DictionaryService#findByTypeCode(java.lang.String
	 * )
	 */
	@Override
	public List<Dictionary> findByTypeCode(String typeCode) {
		return dictionaryRepository.findByTypeCodeAndStatus(typeCode, "00");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.DictionaryService#getByTypeCode(java.lang.String
	 * )
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getByTypeCode(String typeCode) {
		List<Dictionary> dictionaries = Caches.get(CACHE_PREFIX + typeCode, List.class);
		if (dictionaries == null) {
			dictionaries = findByTypeCode(typeCode);
			if (dictionaries != null)
				Caches.add(CACHE_PREFIX + typeCode, dictionaries);
		}
		return dictionaries;
	}

	@Override
	public String maxCodeByCode(String code) {
		return dictionaryRepository.maxCodeByCode(code);
	}

	@Override
	public List<Dictionary> findByType(String typeId) {
		return dictionaryRepository.findByType(typeId);
	}

	@Override
	public Page<Dictionary> queryByCondition(final Dictionary dictionary, final String id, String page, String size) throws Exception {
		 Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC,  "createTime"));
		 return  dictionaryRepository.findAll(new Specification<Dictionary>() {
			@Override
			public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(dictionary.getCode())) {
					list.add(cb.like(root.<String>get("code"), "%"+dictionary.getCode().trim()+"%"));
				}
				if (StringUtils.isNotEmpty(dictionary.getName())) {
					list.add(cb.like(root.<String>get("name"), "%"+dictionary.getName().trim()+"%"));
				}
				if (StringUtils.isNotEmpty(id)) {
					list.add(cb.equal(root.<String>get("type").<String>get("id"), id));
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		},pageable);
	}

	@Override
	public List<Dictionary> findByCode(String code) {
		return dictionaryRepository.findByCode(code);
	}

	@Override
	public Dictionary addOrUpdateDictionary(Dictionary dictionary,String dictId) {
		Dictionary dic = null;
		DictionaryType dicType = null;
		if(StringUtils.isNotEmpty(dictId)){
			dicType = dictionaryTypeRepository.findOne(dictId);
		}
		if(StringUtils.isEmpty(dictionary.getId())){
			dic = new Dictionary();
			dic.setStatus(dictionary.getStatus());
			dic.setType(dicType);
			dic.setOrder(nextOrder(dicType.getId()));
		}else{
			dic = dictionaryRepository.findOne(dictionary.getId());
		}
		dic.setName(dictionary.getName());
		dic.setCode(dictionary.getCode());
		dictionaryRepository.save(dic);
		return dictionary;
	}
	
	/**
	 * 获取下一个order排序的编码
	 * 
	 */
	public synchronized int nextOrder(String typeId) {
		try {
			Integer mdc = dictionaryRepository.maxOrderByTypeId(typeId);
			int temp = (mdc != null ? mdc : 0) + 1;
			logger.info("获取最大order成功：" + temp);
			return temp;
		} catch (Exception e) {
			logger.info("获取最大order失败");
		}
		return 0;
	}

	@Override
	public void delDictionary(String id) {
		dictionaryRepository.delete(id);
	}

	@Override
	public void switchDictionary(String id) {
		Dictionary dic = dictionaryRepository.findOne(id);
		if (dic.getStatus().equals("00")) {
			dic.setStatus("09");
		} else {
			dic.setStatus("00");
		}
		dictionaryRepository.save(dic);
	}


}

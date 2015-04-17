package com.jlfex.hermes.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.service.DictionaryService;

/**
 * 字典业务接口
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

	private static final String CACHE_PREFIX = "com.jlfex.hermes.cache.dictionary.";

	/** 字典信息仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;

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
	};

}

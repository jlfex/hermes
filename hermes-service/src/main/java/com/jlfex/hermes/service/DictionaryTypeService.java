package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.jlfex.hermes.model.DictionaryType;

public interface DictionaryTypeService {

	public Page<DictionaryType> queryByCondition(final DictionaryType dictionaryType,String page, String size) throws Exception;

	public List<DictionaryType> findByName(String name);
	
	public DictionaryType addOrUpdateDictionaryType(DictionaryType dictionaryType);
	
	public List<DictionaryType> findByCode(String code);
	
	public void  delDictionaryType(String id);
	
	public DictionaryType findById(String id);
}

package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.service.pojo.ParameterSetInfo;

/**
 * 
 * @author: lishunfeng
 * @time: 2015年1月6日 下午1:22:17
 */
public interface ParameterSetService {

	// 通过参数类/参数值查询借参数设置信息
	public Page<ParameterSetInfo> findByParameterTypeAndParameterValue(String parameterType, String parameterValue, String page, String size);

	// 新增参数设置
	public Dictionary addParameterSet(ParameterSetInfo parameterSetInfo);

	// 修改参数设置
	public Dictionary updateDictionary(ParameterSetInfo parameterSetInfo);

	// 根据id找到某条记录
	public Dictionary findOne(String id);

	public DictionaryType findOneById(String id);

	public DictionaryType findOneByName(String name);
	
	public List<DictionaryType> findAll();

	// 根据id改变字典状态
	public void switchDictionary(String id);

	public List<Dictionary> findByNameAndType(String name, String typeId);
	
	public List<DictionaryType> findByName(String name);
	
	public DictionaryType addDictionaryType(String name,String status,String typeCode);
	
	public DictionaryType updateDicType(String parameterType,String id,String typeCode);


}

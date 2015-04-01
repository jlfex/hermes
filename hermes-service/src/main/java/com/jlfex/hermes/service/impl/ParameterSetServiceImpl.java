package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.CommonRepository.Script;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.service.ParameterSetService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.pojo.ParameterSetInfo;
import com.jlfex.hermes.service.pojo.ResultVo;

/**
 * 参数设置业务的实现
 * 
 * @author lishunfeng
 */
@Service
@Transactional
public class ParameterSetServiceImpl implements ParameterSetService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;
	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;

	public Page<ParameterSetInfo> findByParameterTypeAndParameterValue(String parameterType, String parameterValue, String page, String size) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlSearchByParameterSet = commonRepository.readScriptFile(Script.searchByParameterSet);

		String sqlCountSearchByParameterSet = commonRepository.readScriptFile(Script.countSearchByParameterSet);
		String condition = getCondition(parameterType, parameterValue, params);

		sqlSearchByParameterSet = String.format(sqlSearchByParameterSet, condition);
		sqlCountSearchByParameterSet = String.format(sqlCountSearchByParameterSet, condition);

		// 初始化
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "5")));

		List<?> listCount = commonRepository.findByNativeSql(sqlCountSearchByParameterSet, params);
		Long total = Long.parseLong(String.valueOf(listCount.get(0)));
		List<?> list = commonRepository.findByNativeSql(sqlSearchByParameterSet, params, pageable.getOffset(), pageable.getPageSize());
		List<ParameterSetInfo> parameterSetInfos = new ArrayList<ParameterSetInfo>();
		for (int i = 0; i < list.size(); i++) {
			ParameterSetInfo parameterSetInfo = new ParameterSetInfo();
			Object[] object = (Object[]) list.get(i);
			parameterSetInfo.setId(String.valueOf(object[0]));
			parameterSetInfo.setParameterType(String.valueOf(object[1]));
			parameterSetInfo.setParameterValue(object[2]==null?null:String.valueOf(object[2]));
			parameterSetInfo.setStatus(String.valueOf(object[3]));
			parameterSetInfo.setDicId(String.valueOf(object[4]));
			parameterSetInfo.setTypeStatus(String.valueOf(object[5]));
			parameterSetInfos.add(parameterSetInfo);
		}
		// 返回结果
		Page<ParameterSetInfo> pageParameterSetInfo = new PageImpl<ParameterSetInfo>(parameterSetInfos, pageable, total);
		return pageParameterSetInfo;
	}

	private String getCondition(String parameterType, String parameterValue, Map<String, Object> params) {
		StringBuilder condition = new StringBuilder();

		if (!Strings.empty(parameterType)) {
			condition.append(" where h2.name = :parameterType");
			params.put("parameterType", parameterType);
		}
		if (!Strings.empty(parameterValue)) {
			condition.append(" where h1.name = :parameterValue");
			params.put("parameterValue", parameterValue);
		}
		return condition.toString();
	}

	/**
	 * 添加参数配置
	 */
	public Dictionary addParameterSet(ParameterSetInfo parameterSetInfo) {
		Dictionary dictionary = new Dictionary();
		DictionaryType dictionaryType = dictionaryTypeRepository.findOne(parameterSetInfo.getParameterType());
		dictionary.setType(dictionaryType);
		dictionary.setName(parameterSetInfo.getParameterValue());
		dictionary.setCode(nextDataCode(dictionaryType.getCode()));
		dictionary.setStatus(parameterSetInfo.getStatus());
		dictionary.setOrder(nextOrder(dictionaryType.getId()));
		dictionaryRepository.save(dictionary);
		return dictionary;
	}

	/**
	 * 添加参数类型
	 */
	public DictionaryType addDictionaryType(String name,String status) {
		DictionaryType dicType = new DictionaryType();
		dicType.setName(name);
		dicType.setStatus(status);
		dictionaryTypeRepository.save(dicType);
		return dicType;
	}

	/**
	 * 获取下一个字典编码
	 * 
	 */
	public synchronized String nextDataCode(String code) {
		try {
			String mdc = dictionaryRepository.maxCodeByCode(code);
			int temp = 0;
			if (StringUtils.isNotBlank(mdc) && mdc.length() > code.length()) {
				temp = NumberUtils.toInt(mdc.substring(code.length()));
			}
			String next = String.format("%s%04d", code, temp + 1);
			logger.info("获取字典编码成功：" + code + "." + next);
			return next;
		} catch (Exception e) {
			logger.info("获取字典编码失败：" + code);
		}
		return null;
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

	/**
	 * 修改启用停用状态
	 * 
	 */
	public void switchDictionary(String id) {
		Dictionary dic = dictionaryRepository.findOne(id);
		if (dic.getStatus().equals("00")) {
			dic.setStatus("09");
		} else {
			dic.setStatus("00");
		}
		dictionaryRepository.save(dic);
	}

	/**
	 * 修改参数配置并保存
	 * 
	 */
	public Dictionary updateDictionary(ParameterSetInfo parameterSetInfo) {
		Dictionary dictionary = dictionaryRepository.findOne(parameterSetInfo.getId());
		dictionary.setName(parameterSetInfo.getParameterValue());
		dictionaryRepository.save(dictionary);
		return dictionary;
	}
	/**
	 * 修改参数类型
	 * 
	 */
	public DictionaryType updateDicType(String parameterType,String id) {
		DictionaryType dicType = dictionaryTypeRepository.findOne(id);
		dicType.setName(parameterType);
		dictionaryTypeRepository.save(dicType);
		return dicType;
	}

	/**
	 * 根据id找到某条记录
	 * 
	 */
	public Dictionary findOne(String id) {
		return dictionaryRepository.findOne(id);
	}

	public DictionaryType findOneById(String id) {
		return dictionaryTypeRepository.findOne(id);
	}

	public DictionaryType findOneByName(String name) {
		return dictionaryTypeRepository.findOneByName(name);
	}

	/**
	 * 根据id修改记录
	 * 
	 */
	public ResultVo update(String id) {
		Dictionary dic = dictionaryRepository.findOne(id);
		if (dic != null) {
			return new ResultVo(0, dic);
		} else {
			return new ResultVo(1, "");
		}
	}

	@Override
	public List<Dictionary> findByNameAndType(String name, String typeId) {
		return dictionaryRepository.findByNameAndType(name, typeId);
	}

	@Override
	public List<DictionaryType> findByName(String name) {
		return dictionaryTypeRepository.findByName(name);
	}

}

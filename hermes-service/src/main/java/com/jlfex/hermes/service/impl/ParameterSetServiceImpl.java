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
import com.jlfex.hermes.model.HermesConstants;
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
			parameterSetInfo.setParameterValue(String.valueOf(object[2]));
			parameterSetInfo.setStatus(String.valueOf(object[3]));
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
	public ResultVo addParameterSet(ParameterSetInfo parameterSetInfo) {
		try {
			Dictionary dictionary = new Dictionary();
			DictionaryType dictionaryType = dictionaryTypeRepository.findOne(parameterSetInfo.getParameterType());
			dictionary.setType(dictionaryType);
			if (findByName(parameterSetInfo.getParameterValue())) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "参数值已经存在");
			} else if (parameterSetInfo.getParameterValue().equals("")) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "参数值不能为空");
			}
			dictionary.setName(parameterSetInfo.getParameterValue());
			dictionary.setCode(nextDataCode(dictionaryType.getCode()));
			dictionary.setStatus(parameterSetInfo.getStatus());
			dictionary.setOrder(nextOrder(dictionaryType.getId()));
			dictionaryRepository.save(dictionary);
			logger.info("添加字典成功：" + dictionary.getType() + "." + dictionary.getCode() + "=" + dictionary.getName());
			return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "操作成功！");
		} catch (Exception e) {
			logger.info("添加字典失败：" + parameterSetInfo.getParameterType() + "-" + parameterSetInfo.getParameterValue());
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, e.getMessage());
		}
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
	 * 根据name判断记录是否存在
	 */

	public Boolean findByName(String name) {
		Dictionary dic = dictionaryRepository.findByName(name);
		if (dic != null) {
			return true;
		} else {
			return false;
		}
	};

	/**
	 * 修改启用停用状态
	 * 
	 */
	public ResultVo switchDictionary(String id) {
		Dictionary dic = dictionaryRepository.findOne(id);

		if (dic != null) {
			if (dic.getStatus().equals("00")) {
				dic.setStatus("09");
			} else {
				dic.setStatus("00");
			}
			dictionaryRepository.save(dic);
			return new ResultVo(0, "修改成功");
		} else {
			return new ResultVo(1, "修改失败");
		}

	}

	/**
	 * 修改参数配置并保存
	 * 
	 */
	public ResultVo updateDictionary(String id, ParameterSetInfo parameterSetInfo) {
		try {
			Dictionary dictionary = dictionaryRepository.findOne(id);
			DictionaryType dicType = dictionaryTypeRepository.findOne(parameterSetInfo.getParameterType());
			// 判断参数值是否已经存在以及不能为空
			if (dictionary.getName().equals(parameterSetInfo.getParameterValue())) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "参数值已经存在");
			} else if (!StringUtils.isNotBlank(parameterSetInfo.getParameterValue())) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "参数值不能为空");
			}
			String oldName = dictionary.getName();
			dictionary.setName(parameterSetInfo.getParameterValue());
			dictionary.setType(dicType);
			dictionary.setOrder(1);//
			dictionaryRepository.save(dictionary);
			logger.info("修改参数配置成功：" + dictionary.getType() + "." + dictionary.getCode() + ":" + oldName + "->" + dictionary.getName());
			return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "操作成功！");
		} catch (Exception e) {
			logger.info("修改参数配置失败：" + parameterSetInfo.getParameterValue());
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, e.getMessage());
		}
	}

	/**
	 * 根据id找到某条记录
	 * 
	 */
	public Dictionary findOne(String id) {
		return dictionaryRepository.findOne(id);
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

}

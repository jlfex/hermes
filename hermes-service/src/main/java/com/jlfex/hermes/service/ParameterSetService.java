package com.jlfex.hermes.service;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.service.pojo.ParameterSetInfo;
import com.jlfex.hermes.service.pojo.ResultVo;

/**
 * 
 * @author: lishunfeng
 * @time: 2015年1月6日 下午1:22:17
 */
public interface ParameterSetService {

	/**
	 * 通过参数类/参数值查询借参数设置信息
	 * 
	 */
	public Page<ParameterSetInfo> findByParameterTypeAndParameterValue(String parameterType, String parameterValue, String page, String size);

	/**
	 * 新增参数设置
	 * 
	 */
	public ResultVo addParameterSet(ParameterSetInfo parameterSetInfo);

	/**
	 * 修改参数设置
	 * 
	 */
	public ResultVo updateDictionary(String id, ParameterSetInfo parameterSetInfo);

	public ResultVo update(String id);

	/**
	 * 根据id找到某条记录
	 * 
	 */
	public Dictionary findOne(String id);

	/**
	 * 根据id改变字典状态
	 * 
	 */
	public ResultVo switchDictionary(String id);

}

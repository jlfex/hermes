package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Properties;

/**
 * 系统属性业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-26
 * @since 1.0
 */
public interface PropertiesService {

	/**
	 * 通过编号查询记录
	 * 
	 * @param id
	 * @return
	 */
	public Properties findById(String id);

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<Properties> findAll();

	/**
	 * 通过名称和代码分页查询记录<br>
	 * 查询条件都可能为空
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Properties> findByNameAndCode(String name, String code, Integer page, Integer size);

	/**
	 * 保存
	 * 
	 * @param properties
	 * @return
	 */
	public Properties save(Properties properties);

	/**
	 * 保存
	 * 
	 * @param id
	 * @param name
	 * @param code
	 * @param value
	 * @return
	 */
	public Properties save(String id, String name, String code, String value);

	/**
	 * 通过Code查询记录
	 * 
	 * @param id
	 * @return
	 */
	public Properties findByCode(String code);
}

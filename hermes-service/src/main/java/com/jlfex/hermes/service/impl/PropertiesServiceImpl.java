package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.common.Query;
import com.jlfex.hermes.service.web.PropertiesFilter;

/**
 * 系统属性业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-26
 * @since 1.0
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

	/** 系统属性仓库 */
	@Autowired
	private PropertiesRepository propertiesRepository;

	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.PropertiesService#findById(java.lang.String)
	 */
	@Override
	public Properties findById(String id) {
		Assert.notEmpty(id, "properties id is empty.", "exception.properties");
		return propertiesRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.PropertiesService#findAll()
	 */
	@Override
	public List<Properties> findAll() {
		return propertiesRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.PropertiesService#findByNameAndCode(java.lang
	 * .String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<Properties> findByNameAndCode(String name, String code, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		Query query = new Query("from Properties where 1 = 1");

		// 查询数据
		query.and("name like :name", "name", "%" + name + "%", !Strings.empty(name));
		query.and("code like :code", "code", "%" + code + "%", !Strings.empty(code));
		query.order("code asc");
		Long total = commonRepository.count(query.getCount(), query.getParams());
		List<Properties> properties = commonRepository.pageByJpql(query.getJpql(), query.getParams(), pageable.getOffset(), pageable.getPageSize(), Properties.class);

		// 返回结果
		Page<Properties> pageProp = new PageImpl<Properties>(properties, pageable, total);
		return pageProp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.PropertiesService#save(com.jlfex.hermes.model
	 * .Properties)
	 */
	@Override
	public Properties save(Properties properties) {
		// 断言
		Assert.notNull(properties, "properties is null.");

		// 验证代码
		// 若是修改数据判断代码是否变更
		// 若是添加数据判断代码是否唯一
		if (Strings.empty(properties.getId())) {
			Properties byCode = propertiesRepository.findByCode(properties.getCode());
			Assert.isNull(byCode, "properties code is duplicated.", "exception.properties.code.duplicate");
		} else {
			Properties byId = propertiesRepository.findOne(properties.getId());
			Assert.notNull(byId, "properties id is not correct. id is " + properties.getId(), "exception.properties");
			Assert.equals(byId.getCode(), properties.getCode(), "cannot edit code " + byId.getCode() + " to " + properties.getCode(), "exception.properties.code.edit");
		}

		// 清除缓存并保存数据返回
		PropertiesFilter.clear();
		return propertiesRepository.save(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.PropertiesService#save(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Properties save(String id, String name, String code, String value) {
		// 断言
		Assert.notEmpty(name, "properties name is empty.", "exception.properties.name.empty");
		Assert.notEmpty(code, "properties code is empty.", "exception.properties.code.empty");
		Assert.notEmpty(value, "properties value is empty.", "exception.properties.value.empty");

		// 设置属性
		Properties properties = Strings.empty(id) ? new Properties() : propertiesRepository.findOne(id);
		properties.setName(name);
		properties.setCode(code);
		properties.setValue(value);

		// 保存并返回
		return save(properties);
	}

	@Override
	public Properties findByCode(String code) {
		return propertiesRepository.findByCode(code);
	}
}

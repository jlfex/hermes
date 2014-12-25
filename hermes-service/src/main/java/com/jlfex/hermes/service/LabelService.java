package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Label;

/**
 * 标签业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
public interface LabelService {

	/**
	 * 通过名称查询标签
	 * 
	 * @param names
	 * @return
	 */
	public List<Label> findByNames(String... names);
}

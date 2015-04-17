package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Label;

/**
 * 标签业务接口
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

package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Label;

/**
 * 标签仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Repository
public interface LabelRepository extends JpaRepository<Label, String> {

	/**
	 * 通过名称加载标签
	 * 
	 * @param name
	 * @return
	 */
	public Label findByName(String name);
}

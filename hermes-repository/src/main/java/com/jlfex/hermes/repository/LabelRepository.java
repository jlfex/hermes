package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Label;

/**
 * 标签仓库
 */
@Repository
public interface LabelRepository extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {

	/**
	 * 通过名称加载标签
	 * 
	 * @param name
	 * @return
	 */
	public Label findByName(String name);
}

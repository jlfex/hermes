package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlfex.hermes.model.Text;

/**
 * 文本信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-31
 * @since 1.0
 */
public interface TextRepository extends JpaRepository<Text, String> {

	/**
	 * 通过关系和类型查询文本
	 * 
	 * @param reference
	 * @param type
	 * @return
	 */
	public Text findByReferenceAndType(String reference, String type);
}

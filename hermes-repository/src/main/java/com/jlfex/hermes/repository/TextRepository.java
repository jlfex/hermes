package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Text;

/**
 * 文本信息仓库
 */
@Repository
public interface TextRepository extends JpaRepository<Text, String>, JpaSpecificationExecutor<Text> {

	/**
	 * 通过关系和类型查询文本
	 * 
	 * @param reference
	 * @param type
	 * @return
	 */
	public Text findByReferenceAndType(String reference, String type);
}

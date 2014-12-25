package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Dictionary;

/**
 * 字典信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-18
 * @since 1.0
 */
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, String> {

	/**
	 * 通过类型代码以及字典代码查询字典
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public Dictionary findByTypeCodeAndCode(String typeCode, String code);
	
	/**
	 * 通过类型代码查询字典
	 * 
	 * @param typeCode
	 * @return
	 */
	@Query("from Dictionary d where d.type.code = ?1 order by d.order")
	public List<Dictionary> findByTypeCode(String typeCode);
}

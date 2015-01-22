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

	public Dictionary findByTypeCodeAndCode(String typeCode, String code);

	@Override
	public Dictionary findOne(String id);

	public Dictionary findByName(String name);

	@Query("from Dictionary d where d.type.code = ?1 and d.status = ?2 order by d.order")
	public List<Dictionary> findByTypeCodeAndStatus(String typeCode, String status);

	@Query("SELECT MAX(t.order) FROM Dictionary t WHERE t.type.id=?1")
	public Integer maxOrderByTypeId(String typeId);

	@Query("SELECT MAX(t.code) FROM Dictionary t WHERE t.code=?1")
	public String maxCodeByCode(String code);

	@Query("from Dictionary d where d.name = ?1 and d.type.id = ?2")
	public List<Dictionary> findByNameAndType(String name, String typeId);

}

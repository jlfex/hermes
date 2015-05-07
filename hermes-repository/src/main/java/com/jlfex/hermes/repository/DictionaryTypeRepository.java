package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.DictionaryType;

/**
 * 字典类型信息仓库
 */
@Repository
public interface DictionaryTypeRepository extends JpaRepository<DictionaryType, String> , JpaSpecificationExecutor<DictionaryType>{
	/**
	 * 通过类型名称查询字典
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public DictionaryType findByCode(String code);
	
	public DictionaryType findOneByName(String name);	
	
	@Query("from DictionaryType d where d.name = ?1")
	public List<DictionaryType> findByName(String name);
	
	@Query("from DictionaryType d where d.code = ?1")
	public List<DictionaryType> findAllByCode(String code);

}

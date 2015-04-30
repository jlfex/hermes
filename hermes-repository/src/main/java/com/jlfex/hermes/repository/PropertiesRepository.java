package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Properties;

/**
 * 系统属性仓库
 */
@Repository
public interface PropertiesRepository extends JpaRepository<Properties, String>, JpaSpecificationExecutor<Properties> {

	/**
	 * 通过代码查询系统属性
	 * 
	 * @param code
	 * @return
	 */
	public Properties findByCode(String code);
	
	@Query("from Properties p where p.code = ?1")
	public List<Properties> findAllByCode(String code);

	@Query("from Properties p where p.name = ?1")
	public List<Properties> findAllByName(String name);
}

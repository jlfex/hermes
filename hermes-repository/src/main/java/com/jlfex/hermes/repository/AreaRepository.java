package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Area;

/**
 * 地区信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-03
 * @since 1.0
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, String>, JpaSpecificationExecutor<Area> {

	/**
	 * 通过父级查询数据
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Area> findByParentIdOrderByCodeDesc(String parentId);
	
	/**
	 * 查询根级数据
	 * 
	 * @return
	 */
	@Query("from Area a where a.parentId is null order by a.code")
	public List<Area> findRoots();
}

package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Navigation;

/**
 * 导航信息仓库
 */
@Repository
public interface NavigationRepository extends JpaRepository<Navigation, String>, JpaSpecificationExecutor<Navigation> {

	/**
	 * 通过类型查询顶级导航列表
	 * 
	 * @param type
	 * @return
	 */
	@Query("from Navigation n where n.type = ?1 and n.parent is null order by n.order")
	public List<Navigation> findByRootAndType(Dictionary type);
	
	/**
	 * 通过上级查询导航列表
	 * 
	 * @param parent
	 * @return
	 */
	@Query("from Navigation n where n.parent = ?1 order by n.order")
	public List<Navigation> findByParent(Navigation parent);
}

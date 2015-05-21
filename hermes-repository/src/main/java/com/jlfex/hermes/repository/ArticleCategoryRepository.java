package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.ArticleCategory;

/**
 * 文章分类信息仓库
 */
@Repository
public interface ArticleCategoryRepository extends PagingAndSortingRepository<ArticleCategory, String>, JpaSpecificationExecutor<ArticleCategory> {
	ArticleCategory findOneByNameAndLevel(String name, String level);

	List<ArticleCategory> findByLevelNotNull();

	ArticleCategory findOneByName(String name);

	@Query("SELECT a FROM ArticleCategory a WHERE a.level = ?1 order by a.createTime asc")
	List<ArticleCategory> findByLevel(String level);

	List<ArticleCategory> findByParent(ArticleCategory parent);

	ArticleCategory findByCode(String code);

	List<ArticleCategory> findByParentId(String parentId);

	int countByNameAndParentId(String inputName, String parentId);
	
	ArticleCategory findOneByNameAndParentId(String inputName, String parentId);

}

package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.ArticleCategory;

/**
 * 文章分类信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-30
 * @since 1.0
 */
@Repository
public interface ArticleCategoryRepository extends PagingAndSortingRepository<ArticleCategory, String>, JpaSpecificationExecutor<ArticleCategory> {
	ArticleCategory findOneByNameAndLevel(String name, String level);

	List<ArticleCategory> findByLevelNotNull();
}

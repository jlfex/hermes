package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategory;

/**
 * 文章信息仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-30
 * @since 1.0
 */
@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, String>, JpaSpecificationExecutor<Article> {
	List<Article> findByCategory(ArticleCategory articleCategory);

	@Query("select a from Article a where a.code= ?1 order by a.updateTime desc")
	List<Article> findByCode(String code);
}

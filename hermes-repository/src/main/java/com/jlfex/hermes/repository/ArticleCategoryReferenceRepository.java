package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.ArticleCategoryReference;

/**
 * 文章分类关系仓库
 */
@Repository
public interface ArticleCategoryReferenceRepository extends JpaRepository<ArticleCategoryReference, String> , JpaSpecificationExecutor<ArticleCategoryReference>{

	/**
	 * 通过分类代码以及文章状态查询关系
	 * 
	 * @param categoryCode
	 * @param articleStatus
	 * @return
	 */
	public List<ArticleCategoryReference> findByCategoryCodeAndArticleStatusIn(String categoryCode, List<String> articleStatus);
	
	/**
	 * 通过分类代码以及文章状态分页查询关系
	 * 
	 * @param categoryCode
	 * @param articleStatus
	 * @param pageable
	 * @return
	 */
	public Page<ArticleCategoryReference> findByCategoryCodeAndArticleStatusIn(String categoryCode, List<String> articleStatus, Pageable pageable);
}

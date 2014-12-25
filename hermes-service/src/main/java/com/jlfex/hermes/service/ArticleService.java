package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategoryReference;

/**
 * 文章业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-18
 * @since 1.0
 */
public interface ArticleService {

	/**
	 * 通过编号加载带有文本的文章
	 * 
	 * @param id
	 * @return
	 */
	public Article loadByIdWithText(String id);
	
	/**
	 * 通过分类代码及状态查询文章
	 * 
	 * @param categoryCode
	 * @param status
	 * @return
	 */
	public List<Article> findByCategoryCodeAndStatus(String categoryCode, String... status);
	
	/**
	 * 查询首页公告
	 * 
	 * @return
	 */
	public List<Article> findHomeNotices();
	
	/**
	 * 查询公告
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<ArticleCategoryReference> findNotices(Integer page, Integer size);
}

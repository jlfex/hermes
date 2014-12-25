package com.jlfex.hermes.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 文章分类关系信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-11
 * @since 1.0
 */
@Entity
@Table(name = "hm_article_category_reference")
public class ArticleCategoryReference extends Model {
	
	private static final long serialVersionUID = 1656697197127517621L;

	/** 文章 */
	@ManyToOne
	@JoinColumn(name = "article")
	private Article article;
	
	/** 分类 */
	@ManyToOne
	@JoinColumn(name = "category")
	private ArticleCategory category;
	
	/**
	 * 读取文章
	 * 
	 * @return
	 * @see #article
	 */
	public Article getArticle() {
		return article;
	}
	
	/**
	 * 设置文章
	 * 
	 * @param article
	 * @see #article
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
	
	/**
	 * 读取分类
	 * 
	 * @return
	 * @see #category
	 */
	public ArticleCategory getCategory() {
		return category;
	}
	
	/**
	 * 设置分类
	 * 
	 * @param category
	 * @see #category
	 */
	public void setCategory(ArticleCategory category) {
		this.category = category;
	}
}

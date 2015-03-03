package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategoryReference;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.repository.ArticleCategoryReferenceRepository;
import com.jlfex.hermes.repository.ArticleRepository;
import com.jlfex.hermes.repository.TextRepository;
import com.jlfex.hermes.service.ArticleService;
import com.jlfex.hermes.service.common.Pageables;

/**
 * 文章业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-30
 * @since 1.0
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	private static final String CATEGORY_NOTICE = "notice";

	/** 文章信息仓库 */
	@Autowired
	private ArticleRepository articleRepository;

	/** 文章分类关系仓库 */
	@Autowired
	private ArticleCategoryReferenceRepository articleCategoryReferenceRepository;

	/** 文本信息仓库 */
	@Autowired
	private TextRepository textRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.ArticleService#loadByIdWithText(java.lang.String
	 * )
	 */
	@Override
	@Transactional(readOnly = true)
	public Article loadByIdWithText(String id) {
		// 查询数据
		Article article = articleRepository.findOne(id);
		Text text = textRepository.findByReferenceAndType(id, Text.Type.ARTICLE);

		// 设置文本
		// article.setText((text == null) ? null : text.getText());

		// 返回结果
		return article;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.ArticleService#findByCategoryCodeAndStatus(java
	 * .lang.String, java.lang.String[])
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Article> findByCategoryCodeAndStatus(String categoryCode, String... status) {
		List<ArticleCategoryReference> articleCategoryReferences = articleCategoryReferenceRepository.findByCategoryCodeAndArticleStatusIn(categoryCode, Arrays.asList(status));
		List<Article> articles = new ArrayList<Article>(articleCategoryReferences.size());
		for (ArticleCategoryReference acr : articleCategoryReferences) {
			articles.add(acr.getArticle());
		}
		return articles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.ArticleService#findHomeNotices()
	 */
	@Override
	public List<Article> findHomeNotices() {
		return findByCategoryCodeAndStatus(CATEGORY_NOTICE, Article.Status.TOP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.ArticleService#findNotices(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Page<ArticleCategoryReference> findNotices(Integer page, Integer size) {
		Pageable pageable = Pageables.pageable(page, size, Direction.DESC, "article.datetime");
		return articleCategoryReferenceRepository.findByCategoryCodeAndArticleStatusIn(CATEGORY_NOTICE, Arrays.asList(Article.Status.TOP, Article.Status.ENABLED), pageable);
	}

	@Override
	public Page<Article> find(final String categoryId, Pageable page) {
		// TODO Auto-generated method stub
		return articleRepository.findAll(new Specification<Article>() {
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.where(cb.equal(root.get("category").get("id"), categoryId));
				query.orderBy(cb.desc(root.get("order")));
				return query.getRestriction();
			}
		}, page);
	}
}

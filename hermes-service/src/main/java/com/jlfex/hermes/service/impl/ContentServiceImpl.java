package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.pojo.ContentCategory;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private ArticleCategoryRepository articleCategoryDao;

	@Override
	public void addCategory(ContentCategory category) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setName(category.getInputName());
		articleCategory.setStatus("00");
		String level1 = category.getCategoryLevelOne();
		String level2 = category.getCategoryLevelTwo();
		if (level1.equals("选择分类") && level2.equals("选择分类")) {
			articleCategory.setLevel("一级");
		}
		if (!level1.equals("选择分类") && level2.equals("选择分类")) {
			ArticleCategory parent = findCategoryByNameAndLevel(level1, "一级");
			articleCategory.setLevel("二级");
			articleCategory.setParent(parent);
		}
		if (!level1.equals("选择分类") && !level2.equals("选择分类")) {
			ArticleCategory parent = findCategoryByNameAndLevel(level2, "二级");
			articleCategory.setLevel("三级");
			articleCategory.setParent(parent);
		}
		articleCategoryDao.save(articleCategory);
	}

	@Override
	public ArticleCategory findCategoryByNameAndLevel(String name, String level) {
		return articleCategoryDao.findOneByNameAndLevel(name, level);
	}

	@Override
	public List<ArticleCategory> findByLevelNotNull() {
		return articleCategoryDao.findByLevelNotNull();
	}

}

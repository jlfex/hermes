package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.service.pojo.ContentCategory;

public interface ContentService {

	public void addCategory(ContentCategory category);

	public ArticleCategory findCategoryByNameAndLevel(String name, String level);

	public List<ArticleCategory> findByLevelNotNull();
}

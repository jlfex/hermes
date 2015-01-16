package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.service.pojo.ContentCategory;
import com.jlfex.hermes.service.pojo.ResultVo;

public interface ContentService {

	public void addCategory(ContentCategory category);

	public ArticleCategory findCategoryByNameAndLevel(String name, String level);

	public List<ArticleCategory> findByLevelNotNull();

	public ResultVo deleteCategory(String id);

	public ResultVo insertCategory(ContentCategory contentCategory);

	public ResultVo updateCategory(ContentCategory contentCategory);

	// 根据level查找所有一级分类
	public List<ArticleCategory> findCategoryByLevel(String level);

	// 根据parent查找所有子分类
	public List<ArticleCategory> findCategoryByParent(ArticleCategory parent);

	// 根据父级ID查询子分类
	public List<ArticleCategory> findByParentId(String parentId);

	// 根据ID查询某条分类
	public ArticleCategory findOne(String id);

}

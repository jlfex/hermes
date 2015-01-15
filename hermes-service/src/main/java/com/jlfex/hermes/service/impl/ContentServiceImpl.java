package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.model.HermesConstants;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.pojo.ContentCategory;
import com.jlfex.hermes.service.pojo.ResultVo;

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

	/**
	 * 删除分类
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ResultVo deleteCategory(String id) {
		ArticleCategory articleCategory = articleCategoryDao.findOne(id);
		List<ArticleCategory> articleCategorys = articleCategory.getChildren();
		if (articleCategorys.size() > 0) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "请先删除子分类再做分类删除");
		} else {
			articleCategoryDao.delete(articleCategory);
		}
		return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "删除成功");
	}

	/**
	 * 处理新增分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ResultVo insertCategory(ContentCategory category) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setName(category.getInputName());
		articleCategory.setStatus("00");
		String level1 = category.getCategoryLevelOne();
		String level2 = category.getCategoryLevelTwo();
		if (!level1.equals("") && level2.equals("") && category.getInputName().equals("")) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "您还未添加任何分类");
		} else if (!level1.equals("") && level2.equals("")) { // 当一级分类为不空，二级分类为空
			ArticleCategory parent = articleCategoryDao.findOne(level1);
			int count = articleCategoryDao.countByNameAndParentId(category.getInputName(), level1);
			if (count > 0) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "二级分类已存在，请重新添加");
			}
			articleCategory.setLevel("二级");
			articleCategory.setParent(parent);
		} else if (!level1.equals("") && !level2.equals("")) {
			ArticleCategory parent = articleCategoryDao.findOne(level2);
			int count = articleCategoryDao.countByNameAndParentId(category.getInputName(), level2);
			if (count > 0) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "三级分类已存在，请重新添加");
			}
			articleCategory.setLevel("三级");
			articleCategory.setParent(parent);
		}
		articleCategoryDao.save(articleCategory);
		return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "添加分类成功");
	}

	/**
	 * 处理编辑分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ResultVo updateCategory(ContentCategory category) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setName(category.getInputName());
		String level1 = category.getCategoryLevelOne();
		String level2 = category.getCategoryLevelTwo();
		if (!level1.equals("") && level2.equals("") && category.getInputName().equals("")) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "您还未添加任何分类");
		} else if (!level1.equals("") && level2.equals("")) { // 当一级分类为不空，二级分类为空
			int count = articleCategoryDao.countByNameAndParentId(category.getInputName(), level1);
			if (count > 0) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "二级分类已存在，请重新添加");
			}
		} else if (!level1.equals("") && !level2.equals("")) {
			int count = articleCategoryDao.countByNameAndParentId(category.getInputName(), level2);
			if (count > 0) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "三级分类已存在，请重新添加");
			}
		}
		articleCategoryDao.save(articleCategory);
		return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "添加分类成功");
	}

	/**
	 * 根据类型查找分类
	 * 
	 * @author lishunfeng
	 */
	@Override
	public List<ArticleCategory> findCategoryByLevel(String level) {
		return articleCategoryDao.findByLevel(level);
	}

	/**
	 * 根据parent查找所有子分类
	 * 
	 * @author lishunfeng
	 */
	@Override
	public List<ArticleCategory> findCategoryByParent(ArticleCategory parent) {
		return articleCategoryDao.findByParent(parent);
	}

	@Override
	public List<ArticleCategory> findByParentId(String parentId) {
		return articleCategoryDao.findByParentId(parentId);
	}

	@Override
	public ArticleCategory findOne(String id) {
		return articleCategoryDao.findOne(id);
	}
}

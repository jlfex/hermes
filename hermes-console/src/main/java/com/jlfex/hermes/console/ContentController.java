package com.jlfex.hermes.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.pojo.ContentCategory;
import com.jlfex.hermes.service.pojo.ResultVo;

/**
 * @author admin 内容管理控制器
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	/**
	 * 分类管理结果页面
	 * 
	 */
	@RequestMapping("/categoryIndex")
	public String categoryIndex(Model model) {
		model.addAttribute("categories", contentService.findByLevelNotNull());
		return "/content/categoryIndex";
	}

	/**
	 * 删除分类
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/deleteCategory")
	@ResponseBody
	public ResultVo deleteCategory(@RequestParam(value = "id", required = true) String id, Model model) {
		return contentService.deleteCategory(id);
	}

	/**
	 * 跳转至编辑分类页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/editCategory")
	public String editCategory(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("category", contentService.findOne(id));
		model.addAttribute("categoryForLevel1", contentService.findCategoryByLevel("一级"));
		return "/content/editCategory";
	}

	/**
	 * 处理编辑分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/updateCategory")
	@ResponseBody
	public ResultVo updateCategory(ContentCategory contentCategory) {
		return contentService.updateCategory(contentCategory);
	}

	/**
	 * 跳转至新增分类页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/addCategory")
	public String addCategory(Model model) {
		return "/content/addCategory";
	}

	/**
	 * 处理新增分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/insertCategory")
	@ResponseBody
	public ResultVo insertCategory(ContentCategory contentCategory) {
		return contentService.insertCategory(contentCategory);
	}

	/**
	 * 根据类型查找分类
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/findCategoryByLevel")
	@ResponseBody
	public List<ArticleCategory> findCategoryByLevel(@RequestParam("level") String level) {
		return contentService.findCategoryByLevel(level);
	}

	/**
	 * 根据父类查找子类
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/findCategoryByParent")
	@ResponseBody
	public List<ArticleCategory> findCategoryByParent(@RequestParam("level") String level, @RequestParam("parentCode") String parentCode, @RequestParam(required = true) String parentId) {
		return contentService.findByParentId(parentId);
	}

	@RequestMapping("/saveAddedCategory")
	public String saveAddedCategory(ContentCategory category, Model model) {
		try {
			contentService.addCategory(category);
		} catch (Exception e) {

		}
		return "redirect:/content/categoryIndex";
	}

	@RequestMapping("/publish")
	public String publish(Model model) {

		return "/content/publish";
	}

	@RequestMapping("/friendLink")
	public String friendLink(Model model) {

		return "/content/friendLink";
	}

	@RequestMapping("/management")
	public String contentManagement(Model model) {

		return "/content/management";
	}

	@RequestMapping("/banner")
	public String banner(Model model) {

		return "/content/banner";
	}

	@RequestMapping("/addBanner")
	public String addBanner(Model model) {

		return "/content/addBanner";
	}

}

package com.jlfex.hermes.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.pojo.ContentCategory;

/**
 * @author admin 内容管理控制器
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/categoryIndex")
	public String categoryIndex(Model model) {
		model.addAttribute("categories", contentService.findByLevelNotNull());
		return "/content/categoryIndex";
	}

	@RequestMapping("/addCategory")
	public String addCategory(Model model) {

		return "/content/addCategory";
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

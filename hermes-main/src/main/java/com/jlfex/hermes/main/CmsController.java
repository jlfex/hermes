package com.jlfex.hermes.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.service.ArticleService;

@Controller
public class CmsController {
	private static String helpCenterCode = "help-center";

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;

	@RequestMapping("help-center")
	public String helpCenter(Model model) {
		model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
		return "cms/template";
	}

	@RequestMapping("help-center/{id}")
	public String helpCenterArticle(@PathVariable String code, Model model) {
		model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
		return "cms/template";
	}
}

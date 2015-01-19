package com.jlfex.hermes.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.service.ArticleService;

@Controller
public class CmsController {
	private static String helpCenterCode = "help_center";

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;

	@RequestMapping("help-center")
	public String helpCenter(Model model) {
		ArticleCategory ac = articleCategoryRepository.findByCode(helpCenterCode);
		String code = ac.getChildren().get(0).getChildren().get(0).getCode();
		return "redirect:/help-center/" + code;
	}

	@RequestMapping("help-center/{ccode}")
	public String helpCenterArticleCategory(@PathVariable String ccode, Model model) {
		List<Article> data = articleService.findByCategoryCodeAndStatus(ccode, Article.Status.ENABLED);
		if (data.size() == 1) {
			return "redirect:/help-center/" + ccode + "/" + data.get(0).getId();
		} else {
			model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
			model.addAttribute("sel", articleCategoryRepository.findByCode(ccode));
			model.addAttribute("aeli", data);
			return "cms/template_li";
		}
	}

	@RequestMapping("help-center/{ccode}/{aid}")
	public String helpCenterArticle(@PathVariable String aid, Model model) {
		model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
		model.addAttribute("ae", articleService.loadByIdWithText(aid));
		return "cms/template_ae";
	}
}

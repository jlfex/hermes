package com.jlfex.hermes.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		String cid = ac.getChildren().get(0).getChildren().get(0).getId();
		return "redirect:/help-center/" + cid;
	}

	@RequestMapping("help-center/{cid}")
	public String helpCenterArticleCategory(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @PathVariable String cid, Model model) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "order", "updateTime"));
		Page<Article> dataBox = articleService.find(cid, pageable);
		if (dataBox.getContent().size() == 1) {
			return "redirect:/help-center/" + cid + "/" + dataBox.getContent().get(0).getId();
		} else {
			model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
			model.addAttribute("sel", articleCategoryRepository.findOne(cid));
			model.addAttribute("aeli", dataBox);
			return "cms/template_li";
		}
	}

	@RequestMapping("help-center/{cid}/{aid}")
	public String helpCenterArticle(@PathVariable String cid, @PathVariable String aid, Model model) {
		model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
		model.addAttribute("ae", articleService.loadByIdWithText(aid));
		model.addAttribute("sel", articleCategoryRepository.findOne(cid));
		return "cms/template_ae";
	}
}

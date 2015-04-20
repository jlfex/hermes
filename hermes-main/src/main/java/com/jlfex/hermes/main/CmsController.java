package com.jlfex.hermes.main;

import java.util.List;
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
	private static String companyIntroduce = "company_introduction";
	private static String OTHER_KIND_LEVEL = "other_two";
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;

	@RequestMapping("help-center")
	public String helpCenter(Model model) {
		ArticleCategory ac = articleCategoryRepository.findByCode(helpCenterCode);
		List<ArticleCategory> secondLeveList = ac.getChildren();
		ArticleCategory defaultMenu = null;
		for (ArticleCategory obj : secondLeveList) {
			if (obj != null && companyIntroduce.equals(obj.getCode())) {
				defaultMenu = obj;
			}
		}
		String cid = defaultMenu.getChildren().get(0).getId();
		return "redirect:/help-center/" + cid;
	}

	@RequestMapping("help-center/{cid}")
	public String helpCenterArticleCategory(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @PathVariable String cid, Model model) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "order", "updateTime"));
		Page<Article> dataBox = articleService.find(cid, pageable);
		if (dataBox.getContent().size() == 1) {
			return "redirect:/help-center/" + cid + "/" + dataBox.getContent().get(0).getId();
		} else {
			List<ArticleCategory> articleCategorys = articleCategoryRepository.findByLevel("二级");
			for (ArticleCategory a : articleCategorys) {
				if (OTHER_KIND_LEVEL.equals(a.getCode())) {
					articleCategorys.remove(a);
					break;
				}
			}
			model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
			model.addAttribute("second", articleCategorys);
			model.addAttribute("sel", articleCategoryRepository.findOne(cid));
			model.addAttribute("aeli", dataBox);
			return "cms/template_li";
		}
	}

	@RequestMapping("help-center/{cid}/{aid}")
	public String helpCenterArticle(@PathVariable String cid, @PathVariable String aid, Model model) {
		List<ArticleCategory> articleCategorys = articleCategoryRepository.findByLevel("二级");
		for (ArticleCategory a : articleCategorys) {
			if (OTHER_KIND_LEVEL.equals(a.getCode())) {
				articleCategorys.remove(a);
				break;
			}
		}
		model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
		model.addAttribute("second", articleCategorys);
		model.addAttribute("ae", articleService.loadByIdWithText(aid));
		model.addAttribute("sel", articleCategoryRepository.findOne(cid));
		return "cms/template_ae";
	}

	@RequestMapping("notice/{cid}")
	public String noticeArticleCategory(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @PathVariable String cid, Model model) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.DESC, "order", "updateTime"));
		Page<Article> dataBox = articleService.find(cid, pageable);
		if (dataBox.getContent().size() == 1) {
			return "redirect:/notice/" + cid + "/" + dataBox.getContent().get(0).getId();
		} else {
			model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
			model.addAttribute("sel", articleCategoryRepository.findOne(cid));
			model.addAttribute("aeli", dataBox);
			return "cms/notice_li";
		}
	}

	@RequestMapping("notice/{cid}/{aid}")
	public String noticeArticle(@PathVariable String cid, @PathVariable String aid, Model model) {
		model.addAttribute("nav", articleCategoryRepository.findByCode(helpCenterCode));
		model.addAttribute("ae", articleService.loadByIdWithText(aid));
		model.addAttribute("sel", articleCategoryRepository.findOne(cid));
		return "cms/notice_ae";
	}

}

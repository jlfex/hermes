package com.jlfex.hermes.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.TmpNoticeRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.service.ArticleService;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.web.PropertiesFilter;

/**
 * 索引控制器
 */
@Controller
public class IndexController {

	/** 文章业务接口 */
	@Autowired
	private ArticleService articleService;

	/** 借款业务接口 */
	@Autowired
	private LoanService loanService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private TmpNoticeRepository tmpNoticeRepository;

	@Autowired
	private RoleResourceRepository roleResourceRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;

	@Autowired
	private NavigationRepository navigationRepository;

	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;
	/**
	 * 索引
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("nav", HomeNav.HOME);
		List<String> loanKindList = new ArrayList<String>();
		loanKindList.add(Loan.LoanKinds.NORML_LOAN);
		model.addAttribute("loans", loanService.findForIndex(loanKindList));
		loanKindList.clear();
		loanKindList.add(Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN);
		loanKindList.add(Loan.LoanKinds.YLTX_ASSIGN_LOAN);
		model.addAttribute("assignLoans", loanService.findForIndex(loanKindList));
		model.addAttribute("bannerPicture", contentService.findOneByCode(HermesConstants.INDEX_BANNER));
		model.addAttribute("investPicture", contentService.findOneByCode(HermesConstants.INDEX_INVEST));
		model.addAttribute("loanPicture", contentService.findOneByCode(HermesConstants.INDEX_LOAN));
		ArticleCategory articleCategory = articleCategoryRepository.findByCode(HermesConstants.PUBLIC_NOTICE);
		model.addAttribute("articleCategoryId", articleCategory == null ? "" : articleCategory.getId());
		List<Article> articleList = contentService.findArticleByCode(HermesConstants.NOTICE_CODE);
		List<Article> newlist = new ArrayList<>();
		if (articleList.size() > 5) {
			for (int i = 0; i < 5; i++) {
				newlist.add(articleList.get(i));
			}
			model.addAttribute("notices", newlist);
		} else {
			model.addAttribute("notices", articleList);
		}

		model.addAttribute("token", App.current().getToken());
		return "index";
	}

	@RequestMapping("/n/{id}")
	public String tmpNotices(@PathVariable String id, Model model) {
		model.addAttribute("notice", tmpNoticeRepository.findOne(id));
		return "/system/tmpnotice";
	}

	/**
	 * 公告列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/notices")
	public String notices(Integer page, Integer size, Model model) {
		model.addAttribute("notices", articleService.findNotices(page, size));
		return "/system/notices";
	}

	/**
	 * 公告内容
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	// @RequestMapping("/notice/{id}")
	// public String notice(@PathVariable("id") String id, Model model) {
	// model.addAttribute("article", articleService.loadByIdWithText(id));
	// return "/system/notice";
	// }

	/**
	 * 清空缓存
	 * 
	 * @return
	 */
	@RequestMapping("/tool/clear")
	@ResponseBody
	public String clear() {
		PropertiesFilter.clear();
		Caches.clear();
		Logger.info("缓存已经清理完毕");
		return "ok, clear all cache.";
	}

	/**
	 * 首页导航
	 *
	 */
	public static final class HomeNav {

		@Element("首页")
		public static final String HOME = "home";

		@Element("我要理财")
		public static final String INVEST = "invest";

		@Element("我要借款")
		public static final String LOAN = "loan";

		@Element("账户中心")
		public static final String ACCOUNT = "account";

		@Element("使用帮助")
		public static final String HELP = "help";
	}
}

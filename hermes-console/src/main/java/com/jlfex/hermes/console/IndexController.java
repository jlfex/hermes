package com.jlfex.hermes.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.constant.HermesEnum.NavigationEnum;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.console.pojo.Tree;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.NavigationService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.pojo.LoanStatusCount;
import com.jlfex.hermes.service.web.PropertiesFilter;

/**
 * 索引控制器
 */
@Controller
public class IndexController {

	private static final String CODE_NAVIGATION_CONSOLE = "console";

	/** 导航业务接口 */
	@Autowired
	private NavigationService navigationService;

	/** 借款业务接口 */
	@Autowired
	private LoanService loanService;

	@Autowired
	private NavigationRepository navigationRepository;

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private RoleResourceRepository roleResourceRepository;

	/**
	 * 索引
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("navs", Tree.fromNavigation(navigationService.findRootByTypeCode(CODE_NAVIGATION_CONSOLE)));
		model.addAttribute("today", Calendars.format("yyyy年MM月dd日 E"));
		model.addAttribute("username", App.message("index.username", App.user().getName()));
		return "index";
	}

	/**
	 * 菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/menu")
	@ResponseBody
	public List<Tree> menu(String id) {
		List<String> roleResourceList = PropertiesFilter.roleResourceList;
		List<Navigation> navigations = navigationService.findByParentId(id);
		List<Navigation> subMenu = new ArrayList<Navigation>();

		Navigation navigation = navigationRepository.findOne(id);
		if (navigation.getCode().equals(NavigationEnum.back_search.name()) || navigation.getCode().equals(NavigationEnum.back_interface_mng.name())) {
			for (Navigation nav : navigations) {
				if (roleResourceList.contains(nav.getCode())) {
					subMenu.add(nav);
				}
			}

		} else {
			subMenu.addAll(navigations);
		}

		return Tree.fromNavigation(subMenu);
	}

	/**
	 * 首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/home")
	public String home(Model model) {
		Map<String, LoanStatusCount> map = LoanStatusCount.getHomeCount(loanService.countByStatus());
		model.addAttribute("audit", map.get(LoanStatusCount.HomeStatus.AUDIT));
		model.addAttribute("out", map.get(LoanStatusCount.HomeStatus.LOAN_OUT));
		model.addAttribute("demand", map.get(LoanStatusCount.HomeStatus.DEMAND));
		return "home";
	}
}

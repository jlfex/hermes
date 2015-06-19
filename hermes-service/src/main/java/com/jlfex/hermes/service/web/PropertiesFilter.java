package com.jlfex.hermes.service.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.WebApp;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.FriendLink;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.service.FriendLinkService;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.TextService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.role.RoleResourceService;

/**
 * 系统属性过滤器
 */
@Component
public class PropertiesFilter implements Filter {
	private static final String companyIntroductionCode = "company_introduction";
	private static final String SITE_SERVICE_TEL = "site.service.tel";
	private static final String SITE_SERVICE_TIME = "site.service.time";
	private static final String App_OPERATION_NICKNAME = "app.operation.nickname";
	private static final String App_COPYRIGHT = "app.copyright";
	private static final String App_LOGO = "app.logo";
	private static String appLogoBase64;
	private static ArticleCategory articleCategory;
	private static List<ArticleCategory> articleCategoryList;
	private static List<FriendLink> friendLinkList;
	public static List<String> roleResourceList;
	public static List<String> backRoleResourceList;
	public static Map<String, List<String>> backRoleResourceMap = new HashMap<String, List<String>>();

	/** 系统属性业务接口 */
	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private TextService textService;

	@Autowired
	private FriendLinkService friendLinkService;

	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;

	@Autowired
	private PropertiesRepository propertiesRepository;

	@Autowired
	private RoleResourceService roleResourceService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private DictionaryRepository dictionaryRepository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		App.config(propertiesService.loadFromDatabase());
		if (App.config("app.logo") != null) {
			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put("app.logo.data", textService.loadById(App.config("app.logo")).getText());
			App.config(tmp);
		}
		Logger.info("properties filter is working...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.isResource(req) && Strings.empty(App.config(HermesConstants.KEY_DATABASE))) {
			Logger.info("properties from database has changed! rebuild cache now.");
			App.config(propertiesService.loadFromDatabase());
			Logger.info("properties rebuild completed.");
		}
		if (articleCategoryList == null) {
			if (articleCategory == null) {
				articleCategory = articleCategoryRepository.findByCode(companyIntroductionCode);
			}
			articleCategoryList = articleCategoryRepository.findByParent(articleCategory);
		}
		if (friendLinkList == null) {
			friendLinkList = friendLinkService.findTop10();
		}
		if (roleResourceList == null) {
			Dictionary dictionary = dictionaryRepository.findByCodeAndStatus(HermesConstants.DIC_FTONT_NAV,Dictionary.Status.VALID);
			roleResourceList = roleResourceService.getSoftModelRoleResource(dictionary);
		}

		try {
			AppUser curUser = App.current().getUser();
			if (curUser != null) {
				User user = userInfoService.findByUserId(curUser.getId());
				if (user.getAccount().equals(HermesConstants.PLAT_MANAGER)) {
					if (backRoleResourceMap.get(HermesConstants.PLAT_MANAGER) == null) {
						backRoleResourceList = roleResourceService.getBackRoleResource();
						backRoleResourceMap.put(HermesConstants.PLAT_MANAGER, backRoleResourceList);
					} else {
						backRoleResourceList = backRoleResourceMap.get(HermesConstants.PLAT_MANAGER);
					}
				} else {
					List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
					List<Role> roles = new ArrayList<Role>();
					for (UserRole userRole : userRoles) {
						roles.add(userRole.getRole());
					}

					if (roles != null && roles.size() == 1) {
						Role role = roles.get(0);
						if (backRoleResourceMap.get(role.getCode()) == null) {
							backRoleResourceList = roleResourceService.getBackRoleResource();
						} else {
							backRoleResourceList = backRoleResourceMap.get(role.getCode());
							backRoleResourceMap.put(role.getCode(), backRoleResourceList);
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.error("获取用户出现异常:" + e.getMessage());
		}

		req.setAttribute("friendlinkData", friendLinkList);
		req.setAttribute("companyIntroductions", articleCategoryList);
		req.setAttribute("roleResourceList", roleResourceList);
		req.setAttribute("backRoleResourceList", backRoleResourceList);
		req.setAttribute("siteServiceTel", App.config(SITE_SERVICE_TEL));
		req.setAttribute("siteServiceTime", App.config(SITE_SERVICE_TIME));
		req.setAttribute("appCopyright", App.config(App_COPYRIGHT));
		req.setAttribute("appOperationNickname", App.config(App_OPERATION_NICKNAME));
		if (appLogoBase64 == null) {
			Text text = textService.loadById(App.config(App_LOGO).trim());
			if (text != null) {
				appLogoBase64 = text.getText();
			} else {
				appLogoBase64 = "";
			}
		}
		req.setAttribute("appLogo", appLogoBase64);
		chain.doFilter(req, resp);
		HttpServletRequest request = HttpServletRequest.class.cast(req);
		Logger.info("请求信息：RemoteAddr=%s,RemotePort=%s,RequestURL=%s", request.getRemoteAddr(), request.getRemotePort(), request.getRequestURL());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed properties filter!");
	}

	/**
	 * 清除数据库加载的属性缓存
	 */
	public static void clear() {
		Map<String, String> values = new HashMap<String, String>();
		values.put(HermesConstants.KEY_DATABASE, "");
		App.config(values);
		Logger.info("properties 缓存已经重新加载.");
	}
}

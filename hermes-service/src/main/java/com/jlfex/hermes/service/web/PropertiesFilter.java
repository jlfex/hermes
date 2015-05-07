package com.jlfex.hermes.service.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.WebApp;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.model.FriendLink;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.service.FriendLinkService;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.TextService;
import com.jlfex.hermes.service.role.RoleResourceService;

/**
 * 系统属性过滤器
 */
@Component
public class PropertiesFilter implements Filter {
	private static final String companyIntroductionCode = "company_introduction";
	public static final String SITE_SERVICE_TEL = "site.service.tel";
	public static final String SITE_SERVICE_TIME = "site.service.time";
	public static final String App_OPERATION_NICKNAME = "app.operation.nickname";
	public static final String App_COPYRIGHT = "app.copyright";
	public static final String App_LOGO = "app.logo";
	public static ArticleCategory articleCategory;
	public static List<ArticleCategory> articleCategoryList;
	public static List<FriendLink> friendLinkList;
	public static List<String> roleResourceList;

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
			roleResourceList = roleResourceService.getFrontIndexRoleResource();
		}
		req.setAttribute("friendlinkData", friendLinkList);
		req.setAttribute("companyIntroductions", articleCategoryList);
		req.setAttribute("roleResourceList", roleResourceList);
		chain.doFilter(req, resp);
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

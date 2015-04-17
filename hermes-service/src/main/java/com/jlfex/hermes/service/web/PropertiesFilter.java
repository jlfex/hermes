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
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.WebApp;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.service.FriendLinkService;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.TextService;
import com.jlfex.hermes.service.impl.PropertiesServiceImpl;

/**
 * 系统属性过滤器
 */
@Component
public class PropertiesFilter implements Filter {
	private static String companyIntroductionCode = "company_introduction";

	/** 系统属性业务接口 */
	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private TextService textService;

	@Autowired
	private FriendLinkService friendLinkService;

	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;

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
		if (!WebApp.isResource(req) && Strings.empty(App.config(PropertiesServiceImpl.KEY_DATABASE))) {
			Logger.info("properties from database has changed! rebuild cache now.");
			App.config(propertiesService.loadFromDatabase());
			Logger.info("properties rebuild completed.");
		}
		ArticleCategory articleCategory = articleCategoryRepository.findByCode(companyIntroductionCode);
		List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByParent(articleCategory);
		req.setAttribute("friendlinkData", friendLinkService.findTop10());
		req.setAttribute("companyIntroductions", articleCategoryList);
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
		values.put(PropertiesServiceImpl.KEY_DATABASE, "");
		App.config(values);
		Logger.info("properties clear.");
	}
}

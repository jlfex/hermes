package com.jlfex.hermes.common.support.spring;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.web.WebApp;
import com.jlfex.hermes.common.web.filter.WebAppFilter;

/**
 * 采用 Spring 容器的网络应用过滤器
 */
public class SpringWebAppFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("initializing spring web application...");
		SpringWebApp.initialize(config.getServletContext());
		Logger.info("spring web application filter is working...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.isResource(req)) {
			req.setAttribute(WebAppFilter.PARAM_APP, SpringWebApp.create(HttpServletRequest.class.cast(req), HttpServletResponse.class.cast(resp)));
			req.setAttribute(WebAppFilter.PARAM_USER, App.user());
		}
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed spring web application filter!");
	}
}

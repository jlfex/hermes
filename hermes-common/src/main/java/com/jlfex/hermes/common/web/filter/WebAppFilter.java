package com.jlfex.hermes.common.web.filter;

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

/**
 * 网络应用过滤器
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-08
 * @since 1.0
 */
public class WebAppFilter implements Filter {

	public static final String PARAM_APP 	= "app";
	public static final String PARAM_USER	= "cuser";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("initializing web application...");
		WebApp.initialize(config.getServletContext());
		Logger.info("web application filter is working...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.isResource(req)) {
			req.setAttribute(PARAM_APP, WebApp.create(HttpServletRequest.class.cast(req), HttpServletResponse.class.cast(resp)));
			req.setAttribute(PARAM_USER, App.user());
		}
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed web application filter!");
	}
}

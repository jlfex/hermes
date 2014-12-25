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
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.service.PropertiesService;

/**
 * 系统属性过滤器
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-26
 * @since 1.0
 */
@Component
public class PropertiesFilter implements Filter {

	private static final String KEY_DATABASE = "com.jlfex.properties.database";
	
	/** 系统属性业务接口 */
	@Autowired
	private PropertiesService propertiesService;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		App.config(loadFromDatabase());
		Logger.info("properties filter is working...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.isResource(req) && Strings.empty(App.config(KEY_DATABASE))) {
			Logger.info("properties from database has changed! rebuild cache now.");
			App.config(loadFromDatabase());
			Logger.info("properties rebuild completed.");
		}
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed properties filter!");
	}
	
	/**
	 * 从数据库加载
	 * 
	 * @return
	 */
	protected Map<String, String> loadFromDatabase() {
		// 初始化
		List<Properties> properties = propertiesService.findAll();
		Map<String, String> values = new HashMap<String, String>();
		
		// 遍历读取数据并设置
		for (Properties prop: properties) values.put(prop.getCode(), prop.getValue());
		
		// 返回结果
		values.put(KEY_DATABASE, "true");
		return values;
	}
	
	/**
	 * 清除数据库加载的属性缓存
	 */
	public static void clear() {
		Map<String, String> values = new HashMap<String, String>();
		values.put(KEY_DATABASE, "");
		App.config(values);
		Logger.info("properties clear.");
	}
}

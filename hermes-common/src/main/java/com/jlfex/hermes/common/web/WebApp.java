package com.jlfex.hermes.common.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 网络应用信息
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-07
 * @since 1.0
 */
public class WebApp extends App {

	private static final String SESSION_USER				= "com.jlfex.session.USER";
	private static final String SESSION_FLASH				= "com.jlfex.session.FLASH";
	private static final String CACHE_TOKEN					= "com.jlfex.cache.TOKEN.";
	private static final String CONFIG_PATH_RESOURCE		= App.config("app.path.resource");
	private static final String CONFIG_PATH_IMAGE			= App.config("app.path.image");
	private static final String CONFIG_PATH_JAVASCRIPT		= App.config("app.path.javascript");
	private static final String CONFIG_PATH_STYLESHEET		= App.config("app.path.stylesheet");
	private static final String CONFIG_THEME				= App.config("app.theme");
	private static final String CONFIG_THEME_PATH			= App.config("app.theme.path");
	private static final String DEFAULT_PATH_RESOURCE		= "/resources";
	private static final String DEFAULT_PATH_IMAGE			= "/resources/images";
	private static final String DEFAULT_PATH_JAVASCRIPT		= "/resources/javascripts";
	private static final String DEFAULT_PATH_STYLESHEET		= "/resources/stylesheets";
	
	private static String appPath;
	private static String tempPath;
	private static String resourcePath;
	private static String imagePath;
	private static String javascriptPath;
	private static String stylesheetPath;
	private static String themePath;
	
	protected HttpServletRequest request;
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public WebApp(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		Flash.prepare(request);
		Logger.debug("request uri: " + request.getRequestURI());
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.App#getUser()
	 */
	@Override
	public AppUser getUser() {
		return AppUser.class.cast(request.getSession().getAttribute(SESSION_USER));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.App#setUser(com.jlfex.hermes.common.AppUser)
	 */
	@Override
	public void setUser(AppUser user) {
		if (user == null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(SESSION_USER);
			}
			return;
		}
		request.getSession().setAttribute(SESSION_USER, user);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.App#getToken()
	 */
	@Override
	public String getToken() {
		if (Caches.get(CACHE_TOKEN + getUser()) == null) Caches.add(CACHE_TOKEN + getUser(), Strings.uuid());
		return Caches.get(CACHE_TOKEN + getUser(), String.class);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.App#updateToken()
	 */
	@Override
	public String updateToken() {
		Caches.set(CACHE_TOKEN + getUser(), Strings.uuid());
		return Caches.get(CACHE_TOKEN + getUser(), String.class);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.App#getLocale()
	 */
	public Locale getLocale() {
		return request.getLocale();
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.App#getMessage(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String getMessage(String key, Object... args) {
		throw new ServiceException("The method is not implement!");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPath();
	}
	
	/**
	 * 读取应用路径
	 * 
	 * @return
	 */
	public String getPath() {
		return appPath;
	}
	
	/**
	 * 读取临时路径
	 * 
	 * @return
	 */
	public String getTempPath() {
		return tempPath;
	}
	
	/**
	 * 读取资源路径
	 * 
	 * @return
	 */
	public String getResource() {
		return resourcePath;
	}
	
	/**
	 * 读取图片路径
	 * 
	 * @return
	 */
	public String getImg() {
		return imagePath;
	}
	
	/**
	 * 读取脚本路径
	 * 
	 * @return
	 */
	public String getJs() {
		return javascriptPath;
	}
	
	/**
	 * 读取样式路径
	 * 
	 * @return
	 */
	public String getCss() {
		return stylesheetPath;
	}
	
	/**
	 * 读取主题地址
	 * 
	 * @return
	 */
	public String getTheme() {
		return themePath;
	}
	
	/**
	 * 读取完整应用路径
	 * 
	 * @return
	 */
	public String getFullPath() {
		String url = request.getRequestURL().toString();
		Integer pos = url.indexOf(appPath);
		return url.substring(0, pos + appPath.length());
	}
	
	/**
	 * 读取闪存
	 * 
	 * @return
	 */
	public Flash getFlash() {
		return Flash.class.cast(request.getSession().getAttribute(SESSION_FLASH));
	}
	
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void initialize(ServletContext context) {
		appPath = context.getContextPath();
		tempPath = String.valueOf(context.getAttribute("javax.servlet.context.tempdir"));
		resourcePath = Strings.empty(CONFIG_PATH_RESOURCE, appPath + DEFAULT_PATH_RESOURCE);
		imagePath = Strings.empty(CONFIG_PATH_IMAGE, appPath + DEFAULT_PATH_IMAGE);
		javascriptPath = Strings.empty(CONFIG_PATH_JAVASCRIPT, appPath + DEFAULT_PATH_JAVASCRIPT);
		stylesheetPath = Strings.empty(CONFIG_PATH_STYLESHEET, appPath + DEFAULT_PATH_STYLESHEET);
		themePath = String.format("%s/%s/%s", appPath, CONFIG_THEME_PATH, CONFIG_THEME);
		
		Logger.info("initialize app path: %s", appPath);
		Logger.info("initialize temp path: %s", tempPath);
		Logger.info("initialize resource path: %s", resourcePath);
		Logger.info("initialize image path: %s", imagePath);
		Logger.info("initialize javascript path: %s", javascriptPath);
		Logger.info("initialize stylesheet path: %s", stylesheetPath);
		Logger.info("initialize theme path: %s", themePath);
	}
	
	/**
	 * 创建
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static App create(HttpServletRequest request, HttpServletResponse response) {
		set(new WebApp(request, response));
		return current();
	}
	
	/**
	 * 是否为资源
	 * 
	 * @param req
	 * @return
	 */
	public static boolean isResource(ServletRequest req) {
		HttpServletRequest request = HttpServletRequest.class.cast(req);
		return (request.getRequestURI().startsWith(resourcePath) || request.getRequestURI().startsWith(themePath));
	}
	
	/**
	 * 读取应用路径
	 * 
	 * @return
	 */
	public static String getAppPath() {
		return appPath;
	}
	
	/**
	 * 闪存作用域
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-07
	 * @since 1.0
	 */
	public static final class Flash {
		
		private Map<String, Object> data = new HashMap<String, Object>();
		private Map<String, Object> out = new HashMap<String, Object>();
		
		/**
		 * 设置
		 * 
		 * @param key
		 * @param value
		 */
		public void put(String key, Object value) {
			data.put(key, value);
			out.put(key, value);
		}
		
		/**
		 * 读取
		 * 
		 * @param key
		 * @return
		 */
		public Object get(String key) {
			return data.get(key);
		}
		
		/**
		 * 成功消息
		 * 
		 * @param message
		 * @param args
		 */
		public void success(String message, Object... args) {
			put("success", String.format(message, args));
		}
		
		/**
		 * 错误消息
		 * 
		 * @param message
		 * @param args
		 */
		public void error(String message, Object... args) {
			put("error", String.format(message, args));
		}
		
		/**
		 * 切换
		 */
		private void next() {
			data.clear();
			data = new HashMap<String, Object>(out);
			out.clear();
		}
		
		/**
		 * 准备
		 * 
		 * @param request
		 */
		private static void prepare(HttpServletRequest request) {
			Flash flash = Flash.class.cast(request.getSession().getAttribute(SESSION_FLASH));
			if (flash != null) flash.next();
			else request.getSession().setAttribute(SESSION_FLASH, new Flash());
		}
	}
}

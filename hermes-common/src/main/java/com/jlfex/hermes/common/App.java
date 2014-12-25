package com.jlfex.hermes.common;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.jlfex.hermes.common.exception.NotSignInException;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 应用信息
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-05
 * @since 1.0
 */
public abstract class App {

	private static final String PATH_CONFIG	= "/application.conf";
	
	private static ThreadLocal<App> current = new ThreadLocal<App>();
	private static Properties properties;
	
	/**
	 * 读取用户
	 * 
	 * @return
	 */
	public abstract AppUser getUser();
	
	/**
	 * 设置用户
	 * 
	 * @param user
	 */
	public abstract void setUser(AppUser user);
	
	/**
	 * 读取令牌
	 * 
	 * @return
	 */
	public abstract String getToken();
	
	/**
	 * 更新令牌
	 * 
	 * @return
	 */
	public abstract String updateToken();
	
	/**
	 * 读取位置
	 * 
	 * @return
	 */
	public abstract Locale getLocale();
	
	/**
	 * 读取国际化消息
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public abstract String getMessage(String key, Object... args);
	
	/**
	 * 读取当前应用信息
	 * 
	 * @return
	 */
	public static App current() {
		return current.get();
	}
	
	/**
	 * 读取当前应用信息
	 * 
	 * @param requiredType
	 * @return
	 */
	public static <T extends App> T current(Class<T> requiredType) {
		return requiredType.cast(current());
	}
	
	/**
	 * 设置当前应用信息
	 * 
	 * @param value
	 * @return
	 */
	public static <T extends App> T set(T value) {
		current.set(value);
		return value;
	}
	
	/**
	 * 当前用户
	 * 
	 * @return
	 */
	public static AppUser user() {
		return current().getUser();
	}
	
	/**
	 * 检查用户
	 */
	public static void checkUser() {
		if (user() == null) {
			throw new NotSignInException();
		}
	}
	
	/**
	 * 国际化消息
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String message(String key, Object... args) {
		return current().getMessage(key, args);
	}
	
	/**
	 * 配置信息
	 * 
	 * @param key
	 * @return
	 */
	public static String config(String key) {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(App.class.getResourceAsStream(PATH_CONFIG));
			} catch (IOException e) {
				throw new ServiceException();
			}
		}
		return properties.getProperty(key);
	}
	
	/**
	 * 配置信息<br>
	 * 若配置为空则返回默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String config(String key, String defaultValue) {
		return Strings.empty(config(key), defaultValue);
	}
	
	/**
	 * 设置配置信息
	 * 
	 * @param values
	 */
	public static void config(Map<String, String> values) {
		if (properties == null) config("0");
		properties.putAll(values);
	}
}

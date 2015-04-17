package com.jlfex.hermes.common;

import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Objects;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 断言<br>
 * 断言失败抛出业务异常
 */
public abstract class Assert {

	/**
	 * 对象不为空
	 * 
	 * @param obj
	 * @param message
	 * @param key
	 */
	public static void notNull(Object obj, String message, String key) {
		if (obj == null) throw new ServiceException(message, key);
	}
	
	/**
	 * 对象不为空
	 * 
	 * @param obj
	 * @param message
	 */
	public static void notNull(Object obj, String message) {
		notNull(obj, message, null);
	}
	
	/**
	 * 对象为空
	 * 
	 * @param obj
	 * @param message
	 * @param key
	 */
	public static void isNull(Object obj, String message, String key) {
		if (obj != null) throw new ServiceException(message, key);
	}
	
	/**
	 * 对象为空
	 * 
	 * @param obj
	 * @param message
	 */
	public static void isNull(Object obj, String message) {
		isNull(obj, message, null);
	}
	
	/**
	 * 对象相等
	 * 
	 * @param one
	 * @param another
	 * @param message
	 * @param key
	 */
	public static void equals(Object one, Object another, String message, String key) {
		if (!Objects.equals(one, another)) throw new ServiceException(message, key);
	}
	
	/**
	 * 对象相等
	 * 
	 * @param one
	 * @param another
	 * @param message
	 */
	public static void equals(Object one, Object another, String message) {
		equals(one, another, message, null);
	}
	
	/**
	 * 字符串不为空
	 * 
	 * @param str
	 * @param message
	 * @param key
	 */
	public static void notEmpty(String str, String message, String key) {
		if (Strings.empty(str)) throw new ServiceException(message, key);
	}
	
	/**
	 * 字符串不为空
	 * 
	 * @param str
	 * @param message
	 */
	public static void notEmpty(String str, String message) {
		notEmpty(str, message, null);
	}
}

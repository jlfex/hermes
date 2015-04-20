package com.jlfex.hermes.common.cache;
import java.io.Serializable;
import java.util.Map;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 缓存工具
 */
public abstract class Caches {

	private static final String CONFIG_CACHE_TYPE		= App.config("app.cache.type", "ehcache");
	private static final String CONFIG_CACHE_DURATION	= App.config("app.cache.duration", "7d");
	private static final String EHCACHE					= "ehcache";
	private static final String MEMCACHED				= "memcached";
	
	/** 缓存实现 */
	private static Cache cache;
	
	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public static void add(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("add %s - %s into cache for %d seconds.", key, Strings.toString(value), expiration);
		getCache().add(key, value, expiration);
	}
	
	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void add(String key, Object value, String duration) {
		add(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void add(String key, Object value) {
		add(key, value, CONFIG_CACHE_DURATION);
	}
	
	/**
	 * 安全添加数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public static boolean safeAdd(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("add %s - %s into cache for %d seconds.", key, Strings.toString(value), expiration);
		if (getCache().safeAdd(key, value, expiration)) return true;
		Logger.warn("add %s - %s into cache failure!", key, Strings.toString(value));
		return false;
	}
	
	/**
	 * 安全添加数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 * @return
	 */
	public static boolean safeAdd(String key, Object value, String duration) {
		return safeAdd(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 安全添加数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean safeAdd(String key, Object value) {
		return safeAdd(key, value, CONFIG_CACHE_DURATION);
	}
	
	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public static void set(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("set %s - %s int cache for %d seconds.", key, Strings.toString(value), expiration);
		getCache().set(key, value, expiration);
	}
	
	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void set(String key, Object value, String duration) {
		set(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value) {
		set(key, value, CONFIG_CACHE_DURATION);
	}
	
	/**
	 * 安全设置数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public static boolean safeSet(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("set %s - %s into cache for %d seconds.", key, value, expiration);
		if (getCache().safeSet(key, value, expiration)) return true;
		Logger.warn("set %s - %s into cache failure!", key, value);
		return false;
	}

	/**
	 * 安全设置数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 * @return
	 */
	public static boolean safeSet(String key, Object value, String duration) {
		return safeSet(key, value, Calendars.parseDuration(duration));
	}

	/**
	 * 安全设置数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean safeSet(String key, Object value) {
		return safeSet(key, value, CONFIG_CACHE_DURATION);
	}
	
	/**
	 * 替换数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public static void replace(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("replace %s - %s into cache for %d seconds.", key, value, expiration);
		getCache().replace(key, value, expiration);
	}

	/**
	 * 替换数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void replace(String key, Object value, String duration) {
		replace(key, value, Calendars.parseDuration(duration));
	}

	/**
	 * 替换数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void replace(String key, Object value) {
		replace(key, value, CONFIG_CACHE_DURATION);
	}

	/**
	 * 安全替换数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public static boolean safeReplace(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("replace %s - %s into cache for %d seconds.", key, value, expiration);
		if (getCache().safeReplace(key, value, expiration)) return true;
		Logger.warn("replace %s - %s into cache failure!", key, value);
		return false;
	}

	/**
	 * 安全替换数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 * @return
	 */
	public static boolean safeReplace(String key, Object value, String duration) {
		return safeReplace(key, value, Calendars.parseDuration(duration));
	}

	/**
	 * 安全替换数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean safeReplace(String key, Object value) {
		return safeReplace(key, value, CONFIG_CACHE_DURATION);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return getCache().get(key);
	}

	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param requiredType
	 * @return
	 */
	public static <T> T get(String key, Class<T> requiredType) {
		return requiredType.cast(get(key));
	}

	/**
	 * 读取数据
	 * 
	 * @param keys
	 * @return
	 */
	public static Map<String, Object> get(String... keys) {
		return getCache().get(keys);
	}

	/**
	 * 递增
	 * 
	 * @param key
	 * @param by
	 * @return
	 */
	public static long incr(String key, int by) {
		return getCache().incr(key, by);
	}

	/**
	 * 递减
	 * 
	 * @param key
	 * @param by
	 * @return
	 */
	public static long decr(String key, int by) {
		return getCache().decr(key, by);
	}

	/**
	 * 移除数据
	 * 
	 * @param key
	 * @return
	 */
	public static void delete(String key) {
		Logger.info("remove %s from cache.", key);
		getCache().delete(key);
	}

	/**
	 * 安全移除数据
	 * 
	 * @param key
	 * @return
	 */
	public static boolean safeDelete(String key) {
		Logger.info("remove %s from cache.", key);
		if (getCache().safeDelete(key)) return true;
		Logger.warn("remove %s from cache failure!", key);
		return false;
	}

	/**
	 * 清空缓存数据
	 */
	public static void clear() {
		Logger.info("clear all cache.");
		getCache().clear();
	}
	
	/**
	 * 检查对象是否序列化
	 * 
	 * @param value
	 */
	protected static void checkSerializable(Object value) {
		if (value != null && !(value instanceof Serializable)) {
			throw new ServiceException("Object " + value + " is not serialized.");
		}
	}
	
	/**
	 * 读取并初始化缓存
	 * 
	 * @return
	 */
	protected static Cache getCache() {
		if (cache == null) {
			if (EHCACHE.equalsIgnoreCase(CONFIG_CACHE_TYPE)) {
				cache = EhCacheImpl.getInstance();
				Logger.info("ehcache is ready...");
			} else if (MEMCACHED.equalsIgnoreCase(CONFIG_CACHE_TYPE)) {
				cache = MemcachedImpl.getInstance();
				Logger.info("memcached is ready...");
			}
		}
		return cache;
	}
}

package com.jlfex.hermes.common.cache;
import java.util.HashMap;
import java.util.Map;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache 实现
 */
public class EhCacheImpl implements Cache {

	private static final String DEFAULT_NAME = App.config("app.cache.name", "appDefault");
	
	private static EhCacheImpl singleton;
	
	private CacheManager cacheManager;
	private net.sf.ehcache.Cache cache;
	
	/**
	 * 构造函数
	 */
	protected EhCacheImpl() {
		synchronized (DEFAULT_NAME) {
			cacheManager = CacheManager.create();
			if (!cacheManager.cacheExists(DEFAULT_NAME)) cacheManager.addCache(DEFAULT_NAME);
			cache = cacheManager.getCache(DEFAULT_NAME);
			Logger.info("initialize ehcache '%s' completed.", DEFAULT_NAME);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#add(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void add(String key, Object value, int expiration) {
		if (cache.get(key) != null) return;
		set(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeAdd(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeAdd(String key, Object value, int expiration) {
		try {
			add(key, value, expiration);
			return true;
		} catch (Exception e) {
			Logger.warn(e.getMessage());
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#set(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void set(String key, Object value, int expiration) {
		Element element = new Element(key, value);
		element.setTimeToLive(expiration);
		cache.put(element);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeSet(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeSet(String key, Object value, int expiration) {
		try {
			set(key, value, expiration);
			return true;
		} catch (Exception e) {
			Logger.warn(e.getMessage());
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#replace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void replace(String key, Object value, int expiration) {
		if (cache.get(key) == null) return;
		set(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeReplace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeReplace(String key, Object value, int expiration) {
		try {
			replace(key, value, expiration);
			return true;
		} catch (Exception e) {
			Logger.warn(e.getMessage());
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		Element element = cache.get(key);
		return (element == null) ? null : element.getObjectValue();
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#get(java.lang.String[])
	 */
	@Override
	public Map<String, Object> get(String... keys) {
		Map<String, Object> map = new HashMap<String, Object>(keys.length);
		for (String key: keys) {
			map.put(key, get(key));
		}
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#incr(java.lang.String, int)
	 */
	@Override
	public long incr(String key, int by) {
		Element element = cache.get(key);
		if (element == null) return -1;
		long value = ((Number) element.getObjectValue()).longValue() + by;
		set(key, value, element.getTimeToLive());
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#decr(java.lang.String, int)
	 */
	@Override
	public long decr(String key, int by) {
		Element element = cache.get(key);
		if (element == null) return -1;
		long value = ((Number) element.getObjectValue()).longValue() - by;
		set(key, value, element.getTimeToLive());
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#delete(java.lang.String)
	 */
	@Override
	public void delete(String key) {
		cache.remove(key);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeDelete(java.lang.String)
	 */
	@Override
	public boolean safeDelete(String key) {
		try {
			delete(key);
			return true;
		} catch (Exception e) {
			Logger.warn(e.getMessage());
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#clear()
	 */
	@Override
	public void clear() {
		cache.removeAll();
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#stop()
	 */
	@Override
	public void stop() {
		cacheManager.shutdown();
	}
	
	/**
	 * 读取实例
	 * 
	 * @return
	 */
	public static EhCacheImpl getInstance() {
		if (singleton == null) singleton = new EhCacheImpl();
		return singleton;
	}
}

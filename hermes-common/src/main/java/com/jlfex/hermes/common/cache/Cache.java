package com.jlfex.hermes.common.cache;

import java.util.Map;

/**
 * 缓存接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-05
 * @since 1.0
 */
public interface Cache {

	/**
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public void add(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public boolean safeAdd(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public void set(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public boolean safeSet(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public void replace(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public boolean safeReplace(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * @param keys
	 * @return
	 */
	public Map<String, Object> get(String... keys);
	
	/**
	 * @param key
	 * @param by
	 * @return
	 */
	public long incr(String key, int by);
	
	/**
	 * @param key
	 * @param by
	 * @return
	 */
	public long decr(String key, int by);
	
	/**
	 * @param key
	 */
	public void delete(String key);
	
	/**
	 * @param key
	 * @return
	 */
	public boolean safeDelete(String key);
	
	/**
	 * 
	 */
	public void clear();
	
	/**
	 * 
	 */
	public void stop();
}

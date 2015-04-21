package com.jlfex.hermes.common.cache;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * Memcached 实现
 */
public class MemcachedImpl implements Cache {

	private static MemcachedImpl singleton;
	//TODO 配置提取
	private MemCachedClient client;
	private String[] servers;
	private Integer[] weights;
	
	protected MemcachedImpl() {
		// 初始化
		client = new MemCachedClient();
		servers = getServers();
		weights = getWeights();
		SockIOPool pool = SockIOPool.getInstance();
		
		// 设置属性
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setMaxIdle(Integer.parseInt(App.config("app.cache.max.idle", "1800000")));
		pool.setMaxConn(Integer.parseInt(App.config("app.cache.max.conn", "250")));
		pool.setMinConn(Integer.parseInt(App.config("app.cache.min.conn", "5")));
		pool.setInitConn(Integer.parseInt(App.config("app.cache.init.conn", "5")));
		pool.setMaintSleep(Integer.parseInt(App.config("app.cache.maint.sleep", "30000")));
		pool.setMaxBusyTime(Integer.parseInt(App.config("app.cache.max.busy.time", "60000")));
		pool.initialize();
		Logger.info("initialized memcached servers: %s", Arrays.asList(servers));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#add(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void add(String key, Object value, int expiration) {
		safeAdd(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeAdd(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeAdd(String key, Object value, int expiration) {
		return client.add(key, value, new Date(expiration * 1000));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#set(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void set(String key, Object value, int expiration) {
		safeSet(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeSet(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeSet(String key, Object value, int expiration) {
		return client.set(key, value, new Date(expiration * 1000));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#replace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void replace(String key, Object value, int expiration) {
		safeReplace(key, value, expiration);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeReplace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean safeReplace(String key, Object value, int expiration) {
		return client.replace(key, value, new Date(expiration * 1000));
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return client.get(key);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#get(java.lang.String[])
	 */
	@Override
	public Map<String, Object> get(String... keys) {
		return client.getMulti(keys);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#incr(java.lang.String, int)
	 */
	@Override
	public long incr(String key, int by) {
		return client.incr(key, by);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#decr(java.lang.String, int)
	 */
	@Override
	public long decr(String key, int by) {
		return client.decr(key, by);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#delete(java.lang.String)
	 */
	@Override
	public void delete(String key) {
		safeDelete(key);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#safeDelete(java.lang.String)
	 */
	@Override
	public boolean safeDelete(String key) {
		return client.delete(key);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#clear()
	 */
	public void clear() {
		client.flushAll();
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.common.cache.Cache#stop()
	 */
	@Override
	public void stop() {
		Logger.warn("can not stop client!");
	}
	
	/**
	 * 读取服务器
	 * 
	 * @return
	 */
	protected String[] getServers() {
		ArrayList<String> servers = new ArrayList<String>();
		if (!Strings.empty(App.config("app.cache.server"))) {
			servers.add(App.config("app.cache.server"));
		} else if (!Strings.empty(App.config("app.cache.1.server"))) {
			int i = 1;
			while (!Strings.empty(App.config("app.cache." + i + ".server"))) {
				servers.add(App.config("app.cache." + i + ".server"));
				i++;
			}
		}
		return servers.toArray(new String[] {});
	}
	
	/**
	 * 读取权重
	 * 
	 * @return
	 */
	protected Integer[] getWeights() {
		ArrayList<Integer> weights = new ArrayList<Integer>();
		if (!Strings.empty(App.config("app.cache.weight"))) {
			weights.add(Integer.parseInt(App.config("app.cache.weight")));
		} else if (!Strings.empty(App.config("app.cache.1.weight"))) {
			int i = 1;
			while (!Strings.empty(App.config("app.cache." + i + ".weight"))) {
				weights.add(Integer.parseInt(App.config("app.cache." + i + ".weight")));
				i++;
			}
		}
		return weights.toArray(new Integer[] {});
	}
	
	/**
	 * 读取实例
	 * 
	 * @return
	 */
	public static MemcachedImpl getInstance() {
		if (singleton == null) singleton = new MemcachedImpl();
		return singleton;
	}
}

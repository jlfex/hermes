package com.jlfex.hermes.common;

import org.junit.Assert;
import org.junit.Test;

import com.jlfex.hermes.common.cache.Caches;

/**
 * @author ultrafrog
 * @version 1.0, 2013-11-07
 * @since 1.0
 */
public class CachesTest {

	/**
	 * 
	 */
	@Test
	public void testCachesAdd() {
		Caches.add("hello", "world");
		Assert.assertEquals("world", Caches.get("hello"));
		Caches.add("hello", "nihao");
		Assert.assertNotEquals("nihao", Caches.get("hello"));
		Assert.assertEquals("world", Caches.get("hello"));
	}
}

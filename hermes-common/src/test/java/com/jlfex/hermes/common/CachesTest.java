package com.jlfex.hermes.common;
import org.junit.Assert;
import org.junit.Test;
import com.jlfex.hermes.common.cache.Caches;


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

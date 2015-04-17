package com.jlfex.hermes.service.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.jlfex.hermes.common.App;

/**
 * 分页工具
 */
public abstract class Pageables {

	private static final String DEFAULT_PAGE_SIZE	= "10";
	
	/**
	 * 分页信息
	 * 
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	public static Pageable pageable(Integer page, Integer size, Sort sort) {
		page = (page == null || page.compareTo(0) < 0) ? 0 : page;
		size = (size == null) ? Integer.parseInt(App.config("app.page.size", DEFAULT_PAGE_SIZE)) : size;
		return new PageRequest(page, size, sort);
	}
	
	/**
	 * 分页信息
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public static Pageable pageable(Integer page, Integer size) {
		return pageable(page, size, null);
	}
	
	/**
	 * 分页信息
	 * 
	 * @param page
	 * @param size
	 * @param direction
	 * @param properties
	 * @return
	 */
	public static Pageable pageable(Integer page, Integer size, Direction direction, String... properties) {
		return pageable(page, size, new Sort(direction, properties));
	}
}

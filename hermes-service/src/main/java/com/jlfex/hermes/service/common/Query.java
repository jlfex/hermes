package com.jlfex.hermes.service.common;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 查询
 */
public class Query implements Serializable {

	private static final long serialVersionUID = 5700285192296483145L;
	
	private static final String PREFIX_COUNT 	= "select count(*) ";
	private static final String LOGIC_AND		= " and ";
	private static final String LOGIC_OR		= " or ";
	
	private static final String DATE_BEGIN		= " 00:00:00";
	private static final String DATE_END		= " 23:59:59";
	private static final String DATE_NAME_BEGIN	= "beginDate";
	private static final String DATE_NAME_END	= "endDate";

	/** 脚本 */
	private StringBuffer jpql = new StringBuffer();
	
	/** 顺序 */
	private String order = null;
	
	/** 参数 */
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/**
	 * 构造函数
	 */
	public Query() {}
	
	/**
	 * 构造函数
	 * 
	 * @param jqpl
	 */
	public Query(String jpql) {
		this.jpql.append(jpql);
	}
	
	/**
	 * 并且
	 * 
	 * @param jpql
	 * @param params
	 * @param condition
	 */
	public void and(String jpql, Map<String, Object> params, boolean condition) {
		if (condition) {
			this.jpql.append(LOGIC_AND).append(jpql);
			this.params.putAll(params);
		}
	}
	
	/**
	 * 并且
	 * 
	 * @param jpql
	 * @param obj
	 * @param condition
	 */
	public void and(String jpql, String name, Object obj, boolean condition) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(name, obj);
		and(jpql, params, condition);
	}
	
	/**
	 * @param jpql
	 * @param params
	 * @param condition
	 */
	public void or(String jpql, Map<String, Object> params, boolean condition) {
		if (condition) {
			this.jpql.append(LOGIC_OR).append(jpql);
			params.putAll(params);
		}
	}
	
	/**
	 * 或
	 * 
	 * @param jpql
	 * @param name
	 * @param obj
	 * @param condition
	 */
	public void or(String jpql, String name, Object obj, boolean condition) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(name, obj);
		or(jpql, params, condition);
	}
	
	/**
	 * 顺序
	 * 
	 * @param order
	 */
	public void order(String order) {
		this.order = order;
	}
	
	/**
	 * 读取脚本
	 * 
	 * @return
	 */
	public String getJpql() {
		return jpql.toString() + (Strings.empty(order) ? "" : " order by " + order);
	}
	
	/**
	 * 读取统计脚本
	 * 
	 * @return
	 */
	public String getCount() {
		return PREFIX_COUNT + jpql.toString();
	}
	
	/**
	 * 读取参数
	 * 
	 * @return
	 */
	public Map<String, Object> getParams() {
		return params;
	}
	
	/**
	 * 日期范围
	 * 
	 * @param beginName
	 * @param beginDate
	 * @param endName
	 * @param endDate
	 * @return
	 */
	public static Map<String, Object> dateBetween(String beginName, String beginDate, String endName, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put(beginName, Calendars.parseDateTime(beginDate + DATE_BEGIN));
			map.put(endName, Calendars.parseDateTime(endDate + DATE_END));
		} catch (ParseException e) {
			throw new ServiceException("cannot parse datetime.", e);
		}
		return map;
	}
	
	/**
	 * 日期范围
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Map<String, Object> dateBetween(String beginDate, String endDate) {
		return dateBetween(DATE_NAME_BEGIN, beginDate, DATE_NAME_END, endDate);
	}
}

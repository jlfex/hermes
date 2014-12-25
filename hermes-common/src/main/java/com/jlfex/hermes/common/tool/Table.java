package com.jlfex.hermes.common.tool;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.jlfex.hermes.common.utils.Strings;

/**
 * 库表信息
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-11
 * @since 1.0
 */
public class Table implements Serializable {

	private static final long serialVersionUID = -1179181673248151394L;
	private static final String PREFIX = "hm_";

	/** 名称 */
	private String name;
	
	/** 代码 */
	private String code;
	
	/** 字段列表 */
	private List<Column> columns = new LinkedList<Column>();

	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取字段列表
	 * 
	 * @return
	 * @see #columns
	 */
	public List<Column> getColumns() {
		return columns;
	}
	
	/**
	 * @param prefix
	 * @return
	 */
	public String getClassName() {
		String className = "";
		for (String name: (code.startsWith(PREFIX) ? code.substring(PREFIX.length()) : code).split("_")) {
			className += Strings.toUpperCaseInitial(name);
		}
		return className;
	}
}

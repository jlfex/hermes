package com.jlfex.hermes.common.tool;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jlfex.hermes.common.utils.Strings;

/**
 * 字段信息
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-11
 * @since 1.0
 */
public class Column implements Serializable {

	private static final long serialVersionUID = 8179332736406522283L;
	
	private static Map<String, String> types;
	private static List<String> hides;

	/** 名称 */
	private String name;
	
	/** 代码 */
	private String code;
	
	/** 数据类型 */
	private String dataType;
	
	/** 长度 */
	private Integer length;
	
	/** 精度 */
	private Integer precision;
	
	/** 是否强制 */
	private Boolean mandatory;

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
	 * 读取数据类型
	 * 
	 * @return
	 * @see #dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 设置数据类型
	 * 
	 * @param dataType
	 * @see #dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 读取长度
	 * 
	 * @return
	 * @see #length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * 设置长度
	 * 
	 * @param length
	 * @see #length
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * 读取精度
	 * 
	 * @return
	 * @see #precision
	 */
	public Integer getPrecision() {
		return precision;
	}

	/**
	 * 设置精度
	 * 
	 * @param precision
	 * @see #precision
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	/**
	 * 读取是否强制
	 * 
	 * @return
	 * @see #mandatory
	 */
	public Boolean getMandatory() {
		return mandatory;
	}

	/**
	 * 设置是否强制
	 * 
	 * @param mandatory
	 * @see #mandatory
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * @return
	 */
	public String getType() {
		int pos = dataType.indexOf("(");
		return types().get((pos > 0) ? dataType.substring(0, pos) : dataType);
	}
	
	/**
	 * @return
	 */
	public String getFieldName() {
		String fieldName = "";
		String[] fieldNames = code.split("_");
		for (int i = 0; i < fieldNames.length; i++) fieldName += (i == 0) ? fieldNames[i] : Strings.toUpperCaseInitial(fieldNames[i]);
		return fieldName;
	}
	
	/**
	 * @return
	 */
	public String getMethodName() {
		return Strings.toUpperCaseInitial(getFieldName());
	}
	
	/**
	 * @return
	 */
	public Boolean getShow() {
		return !hides().contains(code);
	}
	
	/**
	 * @return
	 */
	public static Map<String, String> types() {
		if (types == null) {
			types = new HashMap<String, String>();
			types.put("varchar", "String");
			types.put("tinyint", "Integer");
			types.put("smallint", "Integer");
			types.put("int", "Integer");
			types.put("bigint", "Long");
			types.put("decimal", "BigDecimal");
			types.put("datetime", "Date");
		}
		return types;
	}
	
	/**
	 * @return
	 */
	public static List<String> hides() {
		if (hides == null) {
			hides = new LinkedList<String>();
			hides.add("id");
			hides.add("id");
			hides.add("creator");
			hides.add("create_time");
			hides.add("updater");
			hides.add("update_time");
		}
		return hides;
	}
}

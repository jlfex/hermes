package com.jlfex.hermes.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象工具
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-05
 * @since 1.0
 */
public abstract class Objects {

	/**
	 * 比较两个对象是否相等
	 * 
	 * @param one
	 * @param another
	 * @return
	 */
	public static boolean equals(Object one, Object another) {
		return ((one == another) || ((one != null) && one.equals(another)));
	}

	/**
	 * 转换成列表
	 * 
	 * @param values
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> toList(T... values) {
		List<T> list = new ArrayList<T>(values.length);
		for (T val : values) {
			list.add(val);
		}
		return list;
	}

	/**
	 * 强制转换
	 * 
	 * @param obj
	 * @param requiredType
	 * @return
	 */
	public static <T> T cast(Object obj, Class<T> requiredType) {
		return requiredType.cast(obj);
	}
}

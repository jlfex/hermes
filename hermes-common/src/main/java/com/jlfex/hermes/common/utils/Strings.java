package com.jlfex.hermes.common.utils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.jlfex.hermes.common.utils.Cryptos.HashType;

/**
 * 字符串工具
 */
public abstract class Strings {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean empty(String str) {
		return (str == null || str.length() == 0);
	}
	
	public static boolean notEmpty(String str) {
		return !(str == null || str.length() == 0);
	}
	
	/**
	 * 判断字符串是否为空<br>
	 * 若字符串为空则返回默认字符串
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String empty(String str, String defaultValue) {
		return empty(str) ? defaultValue : str;
	}
	
	/**
	 * 判断字符串是否为空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean blank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 判断字符串是否为空白<br>
	 * 若为空白则返回默认值
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String blank(String str, String defaultValue) {
		return (blank(str)) ? defaultValue : str;
	}
	
	/**
	 * 比较两个字符串是否相等
	 * 
	 * @param str
	 * @param another
	 * @return
	 */
	public static boolean equals(String str, String another) {
		return Objects.equals(str, another);
	}
	
	/**
	 * 大写首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCaseInitial(String str) {
		if (!empty(str)) {
			char[] chars = str.toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			return new String(chars);
		}
		return str;
	}
	
	/**
	 * 转换成列表
	 * 
	 * @param strs
	 * @return
	 */
	public static List<String> toList(String... strs) {
		return Objects.toList(strs);
	}
	
	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static List<String> split(String str, String regex) {
		return toList(str.split(regex));
	}
	
	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param regex
	 * @param limit
	 * @return
	 */
	public static List<String> split(String str, String regex, int limit) {
		return toList(str.split(regex, limit));
	}
	
	/**
	 * 合并成字符串
	 * 
	 * @param separator
	 * @param strs
	 * @return
	 */
	public static String join(String separator, String... strs) {
		StringBuilder str = new StringBuilder();
		for (String s: strs) {
			str.append(separator).append(String.valueOf(s));
		}
		return str.substring(separator.length());
	}
	
	/**
	 * 合并成字符串
	 * 
	 * @param separator
	 * @param strs
	 * @return
	 */
	public static String join(String separator, Object... objs) {
		String[] strs = new String[objs.length];
		for (int i = 0; i < objs.length; i++) {
			strs[i] = String.valueOf(objs[i]);
		}
		return join(separator, strs);
	}
	
	/**
	 * 截断字符串
	 * 
	 * @param str
	 * @param len
	 * @param suffix
	 * @return
	 */
	public static String truncate(String str, int len, String suffix) {
		if (!empty(str) && str.length() > len) {
			str = str.substring(0, len);
			str = str + empty(suffix, "");
		}
		return str;
	}
	
	/**
	 * 随机字符串
	 * 
	 * @param len
	 * @param set
	 * @param sets
	 * @return
	 */
	public static String random(int len, String set, String... sets) {
		// 初始化
		Random random = new Random();
		StringBuilder strset = new StringBuilder(set).append(join("", sets));
		StringBuilder builder = new StringBuilder(len);
		
		// 生成随机字符串
		for (int i = 0; i < len; i++) {
			builder.append(strset.charAt(random.nextInt(strset.length())));
		}
		
		// 返回结果
		return builder.toString();
	}
	
	/**
	 * 转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj instanceof List) {
			return String.format("%d rows list", ((List<?>) obj).size());
		} else if (obj instanceof Map) {
			return String.format("%d rows map", ((Map<?, ?>) obj).size());
		} else {
			return String.valueOf(obj);
		}
	}
	
	/**
	 * UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return Cryptos.hashAndHex(str, HashType.MD5);
	}
	
	/**
	 * 字符串集合
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-11-05
	 * @since 1.0
	 */
	public static final class StringSet {
		
		/** 数字 */
		public static final String NUMERIC			= "1234567890";

		/** 小写字母 */
		public static final String LOWER_ALPHABET 	= "abcdefghijklmnopqrstuvwxyz";

		/** 大写字母 */
		public static final String UPPER_ALPHABET 	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		/** 符号 */
		public static final String SYMBOL			= "!@#$%^&*_+-=|:;?";
	}
}

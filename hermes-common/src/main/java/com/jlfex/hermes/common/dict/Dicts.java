package com.jlfex.hermes.common.dict;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 字典工具
 */
public abstract class Dicts {

	private static final String PREFIX = "DICT.";
	
	/**
	 * 读取字典元素
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, String> elements(Class<?> type) {
		// 判断相关类型是否已经缓存
		// 若尚未缓存则进行缓存处理
		if (Caches.get(PREFIX + type.getName()) == null) {
			// 初始化
			Logger.info("cache dictionary %s start...", type.getName());
			Map<Object, String> elems = new LinkedHashMap<Object, String>();
			Field[] fields = type.getDeclaredFields();
			
			// 遍历处理类型字段
			for (Field f: fields) {
				// 仅处理注解为元素的字段
				Element elem = f.getAnnotation(Element.class);
				if (elem != null) {
					try {
						Object key = f.get(type);
						String value = elem.value();
						elems.put(key, value);
						Logger.info("cache dictionary field '%s: %s' from %s.", key, value, type.getName());
					} catch (IllegalArgumentException e) {
						Logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						Logger.error(e.getMessage());
					}
				}
			}
			
			// 缓存数据
			Caches.add(PREFIX + type.getName(), elems);
			Logger.info("cache dictionary %s completed!", type.getName());
		}
		
		// 返回结果
		return Caches.get(PREFIX + type.getName(), Map.class);
	}
	
	/**
	 * 读取字典元素
	 * 
	 * @param type
	 * @param excludes
	 * @return
	 */
	public static Map<Object, String> elements(Class<?> type, Object... excludes) {
		Map<Object, String> copy = new LinkedHashMap<Object, String>(elements(type));
		for (Object obj: excludes) copy.remove(obj);
		return copy;
	}
	
	/**
	 * 读取字典元素名称
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public static String name(Object key, Class<?> type) {
		return elements(type).get(key);
	}
	
	/**
	 * 读取字典元素名称<br>
	 * 若字典元素名称为空则返回默认字符串
	 * 
	 * @param key
	 * @param defaultValue
	 * @param type
	 * @return
	 */
	public static String name(Object key, String defaultValue, Class<?> type) {
		return Strings.empty(name(key, type), defaultValue);
	}
}

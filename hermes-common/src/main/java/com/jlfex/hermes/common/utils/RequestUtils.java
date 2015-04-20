package com.jlfex.hermes.common.utils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

public class RequestUtils {

	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if (CollectionUtil.isEmpty(params)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object value = entry.getValue();

			if (value instanceof String[]) {
				for (String val : (String[]) value) {
					queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(val).append("&");
				}
			} else {
				queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue()).append("&");
			}
		}
		if (queryStringBuilder.length() > 0) {
			queryStringBuilder.deleteCharAt(queryStringBuilder.length() - 1);
		}
		return queryStringBuilder.toString();
	}

	public static String encodeParameterHtmlWithPrefix(Map<String, Object> params, String prefix) {
		if (CollectionUtil.isEmpty(params)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			Object value = entry.getValue();

			if (value instanceof String[]) {
				for (String val : (String[]) value) {
					queryStringBuilder.append("<input type=\"hidden\" value=\"" + val + "\" name=\"" + prefix + entry.getKey() + "\" />");
				}
			} else {
				if (value != null) {
					queryStringBuilder.append("<input type=\"hidden\" value=\"" + value + "\" name=\"" + prefix + entry.getKey() + "\" />");
				}
			}
		}
		return queryStringBuilder.toString();
	}

	/**
	 * 将数字元素用 "," 连接起来
	 * 
	 * @author xj
	 */
	public static String multiJoinStr(String[] arr) {
		if (arr == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(",");
		}
		return sb.toString();
	}

	/**
	 * 回显参数设置
	 * <p>
	 * 将VO里面的查询参数设置到model 支持 String，String[](调用multiJoinStr(String[] arr))
	 * 
	 * @author xj
	 */
	public static void backView(Model model, Object obj) {
		Class<?> cls = obj.getClass();
		Object value = null;
		for (Field field : cls.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				value = field.get(obj);
				if (value != null) {
					if (value instanceof String[]) {
						model.addAttribute(field.getName(), multiJoinStr((String[]) value));
					} else {
						model.addAttribute(field.getName(), value);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}
	}

	/**
	 * 随机生成一个4位数的验证码
	 * 
	 * @return
	 */
	public static String creaetAuthCode4() {
		return creaetAuthCode(4);
	}

	public static String creaetAuthCode(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(getRandomString(random.nextInt(randString.length())));
		}
		return sb.toString();
	}

	private static final String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串

	/**
	 * 获取随机的字符
	 */
	private static String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}

	public static String getStringValue(Object obj) {
		return obj == null ? null : (String) obj;
	}

	public static Integer getIntegerValue(Object obj) {
		try {
			return Integer.parseInt((String) obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static Long getLongValue(Object obj) {
		try {
			return Long.parseLong((String) obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateValue(Object obj) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse((String) obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> getListValue(Object obj) {
		if (obj != null) {
			if (obj instanceof String) {
				return Arrays.asList(new String[] { (String) obj });
			}
			if (obj instanceof String[]) {
				return Arrays.asList((String[]) obj);
			}
		}
		return null;
	}

	/**
	 * 设置下载
	 */
	public static void setDownload(HttpServletRequest request, HttpServletResponse response, String contentType, String fileName) throws UnsupportedEncodingException {
		setResponse(request, response, contentType, fileName, "attachment");
	}

	/**
	 * 设置预览
	 */
	public static void setPreview(HttpServletRequest request, HttpServletResponse response, String contentType, String fileName) throws UnsupportedEncodingException {
		setResponse(request, response, contentType, fileName, "inline");
	}

	private static void setResponse(HttpServletRequest request, HttpServletResponse response, String contentType, String fileName, String mode) throws UnsupportedEncodingException {
		response.setContentType(contentType);
		// 设置中文文件名称转码，兼容各主流浏览器
		fileName = encodeChineseDownloadFileName(request, fileName);
		response.setHeader("Content-Disposition", mode + "; filename=\"" + fileName + "\";target=_blank");
	}

	public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) throws UnsupportedEncodingException {
		String filename = null;
		String agent = request.getHeader("USER-AGENT");
		if (null != agent) {
			if (-1 != agent.indexOf("Firefox")) {// Firefox
				filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
			} else if (-1 != agent.indexOf("Chrome")) {// Chrome
				filename = new String(pFileName.getBytes(), "ISO8859-1");
			} else {// IE7+
				filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
				filename = StringUtils.replace(filename, "+", "%20");// 替换空格
			}
		} else {
			filename = pFileName;
		}
		return filename;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Object convertMap(Class<?> type, Map<String, Object> map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
}
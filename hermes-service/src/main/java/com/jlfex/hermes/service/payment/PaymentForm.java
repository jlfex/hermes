package com.jlfex.hermes.service.payment;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 支付表单
 */
public class PaymentForm {

	/** 支付地址 */
	private String url;
	
	/** 参数 */
	private Map<String, String> values;

	/**
	 * 构造函数
	 */
	public PaymentForm() {
		values = new HashMap<String, String>();
	}
	
	/**
	 * 构造函数
	 * 
	 * @param url
	 */
	public PaymentForm(String url) {
		this();
		this.url = url;
	}
	
	/**
	 * 读取支付地址
	 * 
	 * @return
	 * @see #url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置支付地址
	 * 
	 * @param url
	 * @see #url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 添加参数
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, String value) {
		values.put(key, value);
	}
	
	/**
	 * 读取参数
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return values.get(key);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// 初始化
		StringBuilder form = new StringBuilder();
		
		// 生成表单
		form.append("<form method=\"post\" action=\"").append(url).append("\">");
		for (Entry<String, String> entry: values.entrySet()) {
			form.append("<input name=\"").append(entry.getKey()).append("\" value=\"").append(entry.getValue()).append("\" type=\"hidden\">");
		}
		form.append("</form>");
		
		// 返回结果
		return form.toString();
	}
}

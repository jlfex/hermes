package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Element;

/**
 * 文本信息模型
 * @since 1.0
 */
@Entity
@Table(name = "hm_text")
public class Text extends Model {

	private static final long serialVersionUID = -4361402566171659117L;

	/** 关系 */
	@Column(name = "reference")
	private String reference;

	/** 内容 */
	@Lob
	@Column(name = "text")
	private String text;

	/** 类型 */
	@Column(name = "type")
	private String type;

	/**
	 * 读取关系
	 * 
	 * @return
	 * @see #reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 设置关系
	 * 
	 * @param reference
	 * @see #reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 读取内容
	 * 
	 * @return
	 * @see #text
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置内容
	 * 
	 * @param text
	 * @see #text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-12-18
	 * @since 1.0
	 */
	public static final class Type {

		@Element("文章")
		public static final String ARTICLE = "00";

		@Element("产品")
		public static final String PRODUCT = "01";

		@Element("通知模板")
		public static final String NOTIFYMODEL = "10";

		@Element("小图片")
		public static final String SMALL_PIC = "11";
	}
}

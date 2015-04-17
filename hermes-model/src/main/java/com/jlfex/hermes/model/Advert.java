package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 广告信息模型
 */
@Entity
@Table(name = "hm_advert")
public class Advert extends Model {

	private static final long serialVersionUID = 8376867048760588650L;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 说明 */
	@Column(name = "description")
	private String description;

	/** 图片 */
	@Column(name = "image")
	private String image;

	/** 链接 */
	@Column(name = "link")
	private String link;

	/** 顺序 */
	@Column(name = "order_")
	private Integer order;

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
	 * 读取说明
	 * 
	 * @return
	 * @see #description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置说明
	 * 
	 * @param description
	 * @see #description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 读取图片
	 * 
	 * @return
	 * @see #image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 设置图片
	 * 
	 * @param image
	 * @see #image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 读取链接
	 * 
	 * @return
	 * @see #link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置链接
	 * 
	 * @param link
	 * @see #link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 读取顺序
	 * 
	 * @return
	 * @see #order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置顺序
	 * 
	 * @param order
	 * @see #order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
}

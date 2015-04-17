package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 图片管理模型
 */
@Entity
@Table(name = "hm_image_manage")
public class ImageManage extends Model {
	private static final long serialVersionUID = 8426583187024867914L;

	/** 图片名称 */
	@Column(name = "name")
	private String name;

	/** 图片链接 */
	@Column(name = "link")
	private String link;

	/** 图片 */
	@Column(name = "image")
	@Lob
	private String image;

	/** 顺序 */
	@Column(name = "order_")
	private Integer order;

	/** 状态 */
	@Column(name = "status")
	private String status;
	/** 类型 */
	@Column(name = "type")
	private String type;
	/** 代码 */
	@Column(name = "code")
	private String code;

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
	 * 读取图片
	 * 
	 * @return
	 * @see #image
	 */
	@Lob
	public String getImage() {
		return image;
	}

	/**
	 * 设置图片
	 * 
	 * @param imgStr
	 * @see #image
	 */
	public void setImage(String imgStr) {
		this.image = imgStr;
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

	/**
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

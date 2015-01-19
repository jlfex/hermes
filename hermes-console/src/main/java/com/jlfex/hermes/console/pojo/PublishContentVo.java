package com.jlfex.hermes.console.pojo;

import java.io.Serializable;

public class PublishContentVo implements Serializable {
	private static final long serialVersionUID = 7830641634590682351L;

	private String articleTitle;// 文章标题
	private String author;// 发布人
	private String categoryId;// 所属分类
	private Integer order;// 排序
	private String keywords;// 关键字
	private String description;// 文章描述
	private byte[] content;// 文章内容

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}

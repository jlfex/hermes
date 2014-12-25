package com.jlfex.hermes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jlfex.hermes.common.dict.Element;

/**
 * 文章信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-11
 * @since 1.0
 */
@Entity
@Table(name = "hm_article")
public class Article extends Model {
	
	private static final long serialVersionUID = -8821546967087076070L;

	/** 标题 */
	@Column(name = "title")
	private String title;
	
	/** 作者 */
	@Column(name = "author")
	private String author;
	
	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;
	
	/** 摘要 */
	@Column(name = "summary")
	private String summary;
	
	/** 标志 */
	@Column(name = "mark")
	private String mark;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 内容 */
	@Transient
	private String text;
	
	/**
	 * 读取标题
	 * 
	 * @return
	 * @see #title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 设置标题
	 * 
	 * @param title
	 * @see #title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 读取作者
	 * 
	 * @return
	 * @see #author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * 设置作者
	 * 
	 * @param author
	 * @see #author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 读取日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}
	
	/**
	 * 设置日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 读取摘要
	 * 
	 * @return
	 * @see #summary
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * 设置摘要
	 * 
	 * @param summary
	 * @see #summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * 读取标志
	 * 
	 * @return
	 * @see #mark
	 */
	public String getMark() {
		return mark;
	}
	
	/**
	 * 设置标志
	 * 
	 * @param mark
	 * @see #mark
	 */
	public void setMark(String mark) {
		this.mark = mark;
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
	
	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * 状态
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-12-30
	 * @since 1.0
	 */
	public static final class Status {
		
		@Element("正常")
		public static final String ENABLED	= "00";
		
		@Element("置顶")
		public static final String TOP		= "10";
		
		@Element("失效")
		public static final String DISABLED	= "99";
	}
}

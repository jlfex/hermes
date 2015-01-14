package com.jlfex.hermes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Element;

/**
 * 临时公告模型
 * 
 * @author admin
 *
 */
@Entity
@Table(name = "hm_tmp_notice")
public class TmpNotice extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 标题 */
	@Column(name = "title")
	private String title;

	/**
	 * 公告内容
	 */
	@Column(name = "content")
	private String content;
	/**
	 * 开始时间
	 */
	@Column(name = "start_date")
	private Date startDate;
	/**
	 * 结束时间
	 */
	@Column(name = "end_date")
	private Date endDate;
	/**
	 * 是否有效
	 */
	@Column(name = "status")
	private String status;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 状态
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-12-30
	 * @since 1.0
	 */
	public static final class Status {

		@Element("有效")
		public static final String ENABLED = "00";

		@Element("失效")
		public static final String DISABLED = "99";
	}
}

package com.jlfex.hermes.service.pojo;

import java.io.Serializable;
import java.util.Date;

public class TmpNoticeVo implements Serializable {
	private static final long serialVersionUID = 7830641634590682351L;

	private String id;
	private String title;
	private String content;
	private Date startDate;
	private Date endDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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

}

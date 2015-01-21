package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

public class FriendLinkVo implements Serializable {
	private static final long serialVersionUID = 7830641634590682351L;

	private String id;
	private String linkName;
	private String link;
	private Integer order;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

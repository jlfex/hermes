package com.jlfex.hermes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Calendars;

/**
 * 模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-08
 * @since 1.0
 */
@MappedSuperclass
public class Model extends Identity {

	private static final long serialVersionUID = -1648244977807270840L;

	/** 创建用户 */
	@Column(name = "creator")
	private String creator;

	/** 创建时间 */
	@Column(name = "create_time")
	private Date createTime;

	/** 更新用户 */
	@Column(name = "updater")
	private String updater;

	/** 更新时间 */
	@Column(name = "update_time")
	private Date updateTime;

	/** 版本 */
	@Column(name = "version")
	private Long version = 0L;

	/**
	 * 读取创建用户
	 * 
	 * @return
	 * @see #creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 设置创建用户
	 * 
	 * @param creator
	 * @see #creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 读取创建时间
	 * 
	 * @return
	 * @see #createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createTime
	 * @see #createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取更新用户
	 * 
	 * @return
	 * @see #updater
	 */
	public String getUpdater() {
		return updater;
	}

	/**
	 * 设置更新用户
	 * 
	 * @param updater
	 * @see #updater
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	/**
	 * 读取更新时间
	 * 
	 * @return
	 * @see #updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param updateTime
	 * @see #updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 读取格式化后的创建时间
	 * 
	 * @return
	 */
	public String getFormattedCreateTime() {
		return Calendars.dateTime(createTime);
	}

	/**
	 * 读取格式化后的更新时间
	 * 
	 * @return
	 */
	public String getFormattedUpdateTime() {
		return Calendars.dateTime(updateTime);
	}

	/**
	 * 持久化前回调
	 */
	@PrePersist
	protected void onPrePersist() {
		creator = getCurrentUserId();
		createTime = new Date();
		updater = creator;
		updateTime = createTime;
	}

	/**
	 * 持久化后调用
	 */
	@PostPersist
	protected void onPostPersist() {
		Logger.info("user '%s' insert data '%s - %s'.", getCreator(), this.getClass().getSimpleName(), getId());
	}

	/**
	 * 更新前回调
	 */
	@PreUpdate
	protected void onPreUpdate() {
		updater = getCurrentUserId();
		updateTime = new Date();
	}

	/**
	 * 更新后回调
	 */
	@PostUpdate
	protected void onPostUpdate() {
		Logger.info("user '%s' update data '%s - %s'.", getUpdater(), this.getClass().getSimpleName(), getId());
	}

	/**
	 * 读取当前用户编号
	 * 
	 * @return
	 */
	public static String getCurrentUserId() {
		try {
			return App.user().getId();
		} catch (Exception e) {
			Logger.warn("can not get current user id for reason: " + e.getMessage());
			return null;
		}
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}

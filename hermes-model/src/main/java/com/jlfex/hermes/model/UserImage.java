package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Element;

/**
 * 用户图片信息模型
 *
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_user_image")
public class UserImage extends Model {

	private static final long serialVersionUID = -5532725982276575674L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	/** 标签 */
	@ManyToOne
	@JoinColumn(name = "label")
	private Label label;

	/** 图片 */
	@Column(name = "image")
	@Lob
	private String image;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/** 类型 */
	@Column(name = "type")
	private String type;

	/** 备注 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 读取用户
	 * 
	 * @return
	 * @see #user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param user
	 * @see #user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 读取标签
	 * 
	 * @return
	 * @see #label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * 设置关系
	 * 
	 * @param label
	 * @see #label
	 */
	public void setLabel(Label label) {
		this.label = label;
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
	 * 读取备注
	 * 
	 * @return
	 * @see #remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 * 
	 * @param remark
	 * @see #remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 状态
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-12
	 * @since 1.0
	 */
	public static final class Status {

		@Element("正常")
		public static final String ENABLED = "00";

		@Element("失效")
		public static final String DISABLED = "99";
	}

	/**
	 * 类型
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-12
	 * @since 1.0
	 */
	public static final class Type {

		@Element("认证")
		public static final String AUTH = "00";

		@Element("头像")
		public static final String AVATAR = "10";

		@Element("大头像")
		public static final String AVATAR_LG = "11";
	}
}

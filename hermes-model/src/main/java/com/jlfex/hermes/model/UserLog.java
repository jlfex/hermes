package com.jlfex.hermes.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户日志信息模型
 */
@Entity
@Table(name = "hm_user_log")
public class UserLog extends Model {
	
	private static final long serialVersionUID = -7345981190255634019L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;
	
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
	
	
	public String getTypeName() {
		return Dicts.name(type, type, LogType.class);
	}

	/**
	 * 用户操作日志类型
	 */
	public static final class LogType {
		@Element("登录")
		public static final String LOGIN = "00";

		@Element("修改密码")
		public static final String MODIFY = "01";

		@Element("找回密码")
		public static final String RETRIEVE = "02";

		@Element("注销")
		public static final String LOGOUT = "09";

		@Element("充值")
		public static final String RECHARGE = "50";

		@Element("提现")
		public static final String WITHDRAW = "51";
	}
}

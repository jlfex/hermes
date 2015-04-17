package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 用户学历信息模型
 * @since 1.0
 */
@Entity
@Table(name = "hm_user_education")
public class UserEducation extends Model {
	
	private static final long serialVersionUID = -3719737047502613783L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	/** 毕业学校 */
	@Column(name = "school")
	private String school;
	
	/** 毕业年份 */
	@Column(name = "year")
	private String year;
	
	/** 学历 */
	@Column(name = "degree")
	private String degree;
	
	/** 学历编号 */
	@Column(name = "degree_number")
	private String degreeNumber;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	
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
	 * 读取毕业学校
	 * 
	 * @return
	 * @see #school
	 */
	public String getSchool() {
		return school;
	}
	
	/**
	 * 设置毕业学校
	 * 
	 * @param school
	 * @see #school
	 */
	public void setSchool(String school) {
		this.school = school;
	}
	
	/**
	 * 读取毕业年份
	 * 
	 * @return
	 * @see #year
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * 设置毕业年份
	 * 
	 * @param year
	 * @see #year
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * 读取学历
	 * 
	 * @return
	 * @see #degree
	 */
	public String getDegree() {
		return degree;
	}
	
	/**
	 * 设置学历
	 * 
	 * @param degree
	 * @see #degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	/**
	 * 读取学历编号
	 * 
	 * @return
	 * @see #degreeNumber
	 */
	public String getDegreeNumber() {
		return degreeNumber;
	}
	
	/**
	 * 设置学历编号
	 * 
	 * @param degreeNumber
	 * @see #degreeNumber
	 */
	public void setDegreeNumber(String degreeNumber) {
		this.degreeNumber = degreeNumber;
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
	 * @return description:获取学历名称
	 */
	public String getDegreeName() {
		return Dicts.name(degree, degree, Education.class);
	}

	/**
	 * @return description:获取类型名称
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * 学历
	 */
	public static final class Education {
		@Element("初中及以下")
		public static final String JUNIORHIGH = "00";

		@Element("高中")
		public static final String SENIOR = "01";

		@Element("专科")
		public static final String JUNIORCOLLEGE = "02";

		@Element("本科")
		public static final String BACHELOR = "03";

		@Element("硕士")
		public static final String MASTER = "04";

		@Element("博士")
		public static final String DOCTOR = "05";
	}

	/**
	 * 类型
	 */
	public static final class Type {
		@Element("一般")
		public static final String GENERAL = "00";

		@Element("最高学历")
		public static final String HIGHEST = "99";
	}

}

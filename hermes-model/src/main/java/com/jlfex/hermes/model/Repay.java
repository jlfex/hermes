package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 还款方式信息模型
 */
@Entity
@Table(name = "hm_repay",uniqueConstraints={@UniqueConstraint(columnNames = {"code" })})
public class Repay extends Model {
	
	private static final long serialVersionUID = 7181867659726922827L;

	/** 名称 */
	@Column(name = "name")
	private String name;
	
	/** 代码 */
	@Column(name = "code")
	private String code;
	
	/** 实现类 */
	@Column(name = "clazz")
	private String clazz;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	
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
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 读取实现类
	 * 
	 * @return
	 * @see #class
	 */
	public String getClazz() {
		return clazz;
	}
	
	/**
	 * 设置实现类
	 * 
	 * @param class
	 * @see #class
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
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
	 * @return description:获取状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 状态
	 */
	public static final class Status {
		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}
}

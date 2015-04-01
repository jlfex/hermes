package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

public class ParameterSetInfo implements Serializable {
	private static final long serialVersionUID = 2738596796196493682L;
	/** 编号 */
	private String id;
	/** 字典编号*/
	private String dicId;
	/** 参数类 */
	private String parameterType;
	/** 参数值 */
	private String parameterValue;
	/** 参数状态 */
	private String status;
	/** 参数类型状态 */
	private String typeStatus;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

}

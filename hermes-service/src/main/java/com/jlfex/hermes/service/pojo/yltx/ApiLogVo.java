package com.jlfex.hermes.service.pojo.yltx;

import java.io.Serializable;

public class ApiLogVo implements Serializable{
	
	private static final long serialVersionUID = 7830641634590682351L;

	private String id;
	private String serialNo;              //请求流水号	
	private String interfaceName;         //接口名称
	private String beginDate; // 开始时间 
	private String endDate; // 结束时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}

package com.jlfex.hermes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Element;

@Entity
@Table(name = "hm_api_log")
public class ApiLog   extends Model{

	/**
	 * 外围系统交互日志
	 */
	private static final long serialVersionUID = 496445057439598051L;
	
	@ManyToOne
	@JoinColumn(name = "apiConfig_id")
	private ApiConfig  apiConfig ;        //外围系统日志
	@Column(name = "serial_no")
	private String serialNo;              //请求流水号
	@Column(name = "interface_name")
	private String interfaceName;         //接口名称
	@Column(name = "request_time")
	private Date   requestTime;           //请求时间
	@Column(name = "request_message", length=2000)
	private String requestMessage;        //请求报文
	@Column(name = "response_time")
	private Date   responseTime;          //响应时间
	@Lob
	@Column(name = "response_message")
	private String responseMessage;       //响应报文
	@Column(name = "deal_flag")
	private String dealFlag;              //处理标识
	private String exception;             //异常信息
	
	public ApiConfig getApiConfig() {
		return apiConfig;
	}
	public void setApiConfig(ApiConfig apiConfig) {
		this.apiConfig = apiConfig;
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
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getDealFlag() {
		return dealFlag;
	}
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	/**
	 * 处理状态
	 * @author Administrator
	 *
	 */
	public static final class DealResult {
		@Element("成功")
		public static final String SUC 			= "00";
		@Element("失败")
		public static final String FAIL 	    = "01";

	}
	
	
}

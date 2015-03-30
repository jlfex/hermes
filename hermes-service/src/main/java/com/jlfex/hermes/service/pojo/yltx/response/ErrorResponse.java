package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;

/***
 * 异常响应接口
 * @author Administrator
 *
 */
public class ErrorResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2942447091247360534L;
	
	private String  status;
	private String  memo;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
	

}

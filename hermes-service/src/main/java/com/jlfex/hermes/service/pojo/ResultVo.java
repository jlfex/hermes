package com.jlfex.hermes.service.pojo;

import java.io.Serializable;

/**
 * 返回的请求包装
 * 
 * 
 */
public class ResultVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;// 编码(0表示成功, 1表示业务错误,-1表示系统错误)
	private Object attachment;

	public ResultVo(int code, Object attachment) {
		this.code = code;
		this.attachment = attachment;
	}

	public ResultVo(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getAttachment() {
		return attachment;
	}

	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}
}

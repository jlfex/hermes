package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;
import java.util.List;

/**
 * 订单查询结果
 * @author Administrator
 *
 */
public class OrderResponseVo implements Serializable {

	
	private static final long serialVersionUID = 8227382557418394877L;
	
	
	private List<OrderVo> content;  //返回内容 


	public List<OrderVo> getContent() {
		return content;
	}


	public void setContent(List<OrderVo> content) {
		this.content = content;
	}

	

	
}

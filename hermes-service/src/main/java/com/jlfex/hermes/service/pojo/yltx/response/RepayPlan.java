package com.jlfex.hermes.service.pojo.yltx.response;

import java.io.Serializable;
import java.util.List;

/**
 * 还款计划
 * @author Administrator
 *
 */
public class RepayPlan implements Serializable {

	private static final long serialVersionUID = 98294601687213662L;
	
	
	private List<AssetRepayPlanVo> content;  //返回内容 

	public List<AssetRepayPlanVo> getContent() {
		return content;
	}

	public void setContent(List<AssetRepayPlanVo> content) {
		this.content = content;
	}

	
}

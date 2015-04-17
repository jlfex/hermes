package com.jlfex.hermes.service.pojo.yltx.response;
import java.io.Serializable;
import java.util.List;

public class QueryFinanceRspVo implements  Serializable{

	/**
	 * 查询理财产品订单接口  响应实体
	 */
	private static final long serialVersionUID = -6697557441579588233L;

	private String pageSize;   			   //每次抓取条数
	private String pageNo;     			   //第几页
	private String totalNum;   			   //总记录数
	private List<FinanceOrderVo> content;  //返回内容
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public List<FinanceOrderVo> getContent() {
		return content;
	}
	public void setContent(List<FinanceOrderVo> content) {
		this.content = content;
	}
	
	
	
}

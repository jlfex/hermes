package com.jlfex.hermes.service.pojo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Loan;

/**
 * 借款状态统计
 */
public class LoanStatusCount {

	/** 状态 */
	private String status;
	
	/** 合计 */
	private Long count;

	/** 构造函数 */
	public LoanStatusCount() {
		count = 0L;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param status
	 */
	public LoanStatusCount(String status) {
		this();
		this.status = status;
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
	 * 读取合计
	 * 
	 * @return
	 * @see #count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * 设置合计
	 * 
	 * @param count
	 * @see #count
	 */
	public void setCount(Long count) {
		this.count = count;
	}
	
	/**
	 * 读取首页状态名称
	 * 
	 * @return
	 */
	public String getHomeStatusName() {
		return Dicts.name(status, HomeStatus.class);
	}
	
	/**
	 * 读取首页统计
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, LoanStatusCount> getHomeCount(List<LoanStatusCount> data) {
		// 初始化
		Map<String, LoanStatusCount> map = new HashMap<String, LoanStatusCount>();
		map.put(HomeStatus.AUDIT, new LoanStatusCount(HomeStatus.AUDIT));
		map.put(HomeStatus.LOAN_OUT, new LoanStatusCount(HomeStatus.LOAN_OUT));
		map.put(HomeStatus.DEMAND, new LoanStatusCount(HomeStatus.DEMAND));
		
		// 遍历数据进行统计
		for (LoanStatusCount lsc: data) {
			// 根据状态读取统计对象
			LoanStatusCount count = null;
			if (Strings.equals(lsc.getStatus(), Loan.Status.AUDIT_FIRST)) {
				count = map.get(HomeStatus.AUDIT);
			} else if (Strings.equals(lsc.getStatus(), Loan.Status.AUDIT_FINAL)) {
				count = map.get(HomeStatus.AUDIT);
			} else if (Strings.equals(lsc.getStatus(), Loan.Status.FULL)) {
				count = map.get(HomeStatus.LOAN_OUT);
			} else if (Strings.equals(lsc.getStatus(), Loan.Status.REPAYING)) {
				count = map.get(HomeStatus.DEMAND);
			}
			
			// 累加数据
			if (count != null) count.setCount(count.getCount() + lsc.getCount());
		}
		
		// 返回结果
		return map;
	}
	
	/**
	 * 首页状态
	 */
	public static final class HomeStatus {
		
		@Element("审核")
		public static final String AUDIT	= "00";
		
		@Element("满标放款")
		public static final String LOAN_OUT	= "01";
		
		@Element("催款")
		public static final String DEMAND	= "02";
	}
}

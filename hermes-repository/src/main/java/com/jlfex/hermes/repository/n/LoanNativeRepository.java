package com.jlfex.hermes.repository.n;

import java.math.BigDecimal;

/**
 * 借款信息仓库扩展接口
 */
public interface LoanNativeRepository {	
	/**
	 * 变更借款状态
	 * @param id 借款ID
	 * @param fromStatus 变更前状态
	 * @param toStatus	变更后状态
	 * @return
	 */
	int updateStatus(String id,String fromStatus,String toStatus);	
	/**
	 * 变更借款状态
	 * @param id 借款ID
	 * @param fromStatus 变更前状态
	 * @param toStatus	变更后状态
	 * @return
	 */
	int updateStatusAndAmount(String id,String fromStatus,String toStatus,BigDecimal amount);	
	/**
	 * 变更借款已筹金额
	 * @param id
	 * @param amount
	 * @return
	 */
	public int updateProceeds(String id,BigDecimal amount);
	
}

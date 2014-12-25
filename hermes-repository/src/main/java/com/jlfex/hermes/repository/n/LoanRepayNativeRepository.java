package com.jlfex.hermes.repository.n;

import com.jlfex.hermes.model.LoanRepay;

/**
 * 还款计划仓库扩展接口
 * 
 * 
 * @author Ray
 * @version 1.0, 2013-12-30
 * @since 1.0
 */
public interface LoanRepayNativeRepository {

	/**
	 * 
	 * @param loanRepay
	 * @param fromStatus
	 * @return int 成功执行行数，小于等于0执行失败
	 */
	int updateStatus(LoanRepay loanRepay,String formStatus);
	
	/**
	 * 变更借款还款状态
	 * @param id 还款ID
	 * @param fromStatus 变更前状态
	 * @param toStatus	变更后状态
	 * @return
	 */
	int updateStatus(String id,String fromStatus,String toStatus);
	
	/**
	 *  逾期计算更新借款还款表
	 * @param loanRepay
	 * @param formmStatus
	 * @return
	 */
	int overdueCalc(LoanRepay loanRepay,String formStatus);
	
}

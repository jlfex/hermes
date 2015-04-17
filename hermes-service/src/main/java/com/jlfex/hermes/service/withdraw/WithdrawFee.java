package com.jlfex.hermes.service.withdraw;

import java.math.BigDecimal;

/**
 * 提现手续费接口
 */
public interface WithdrawFee {

	/**
	 * 初始化
	 * 
	 * @param config
	 */
	public void init(String config);
	
	/**
	 * 计算手续费
	 * 
	 * @param amount
	 * @param config
	 * @return
	 */
	public BigDecimal calcFee(BigDecimal amount, String config);
}

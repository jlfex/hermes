package com.jlfex.hermes.service;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Withdraw;

/**
 * 提现业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-21
 * @since 1.0
 */
public interface WithdrawService {

	/**
	 * 通过编号加载提现信息
	 * 
	 * @param id
	 * @return
	 */
	public Withdraw loadById(String id);
	
	/**
	 * 通过姓名/日期范围/状态查询提现信息
	 * 
	 * @param name
	 * @param beginDate
	 * @param endDate
	 * @param status
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Withdraw> findByNameAndDateBetweenAndStatus(String name, String beginDate, String endDate, String status, Integer page, Integer size);
	
	/**
	 * 处理
	 * 
	 * @param id
	 * @param status
	 * @param remark
	 * @return
	 */
	public Withdraw deal(String id, String status, String remark);
}

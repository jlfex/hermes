package com.jlfex.hermes.service;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.service.pojo.LoanLogVo;

/**
 * 投标日志接口
 */
public interface LoanLogService {
	/**
	 * 分页查询投标日志
	 * @param loanLogVo 查询条件
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public Page<LoanLog> queryByCondition(LoanLogVo loanLogVo, String page, String size) throws Exception;

	
	/**
	 * 根据id获取投标日志
	 * @param id
	 * @return
	 */
	public LoanLog findOne(String id);
}

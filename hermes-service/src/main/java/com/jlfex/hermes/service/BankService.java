package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Bank;

/**
 * 银行信息接口
 * 
 */
public interface BankService {
	/**
	 * 通过所有的银行信息
	 */
	public List<Bank> findAll();

}

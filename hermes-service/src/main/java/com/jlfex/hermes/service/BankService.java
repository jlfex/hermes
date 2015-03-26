package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.hermes.BranchBank;

/**
 * 银行信息接口
 * 
 */
public interface BankService {
	/**
	 * 通过所有的银行信息
	 */
	public List<Bank> findAll();
	/**
	 * 根据银行名称及城市查询支行信息
	 */
	public List<BranchBank> findByBranchBankAndCity(String bankName, String cityName);
	
	public Bank findOne(String id);
	

}

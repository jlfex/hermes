package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.hermes.BranchBank;
import com.jlfex.hermes.repository.BankRepository;
import com.jlfex.hermes.repository.bank.BranchBankRepository;
import com.jlfex.hermes.service.BankService;

@Service
@Transactional
public class BankServiceImpl implements BankService {
	/** 银行信息接口 */
	@Autowired
	private BankRepository bankRepository;
	/** 支行信息接口 */
	@Autowired
	private BranchBankRepository branchBankRepository;

	@Override
	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	@Override
	public List<BranchBank> findByBranchBankAndCity(String bankName,String cityName) {
		return branchBankRepository.findByBranchBankAndCity(bankName, cityName);
	}

	@Override
	public Bank findOne(String id) {
		return bankRepository.findOne(id);
	}

}

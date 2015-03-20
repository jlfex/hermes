package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.repository.BankRepository;
import com.jlfex.hermes.service.BankService;

@Service
@Transactional
public class BankServiceImpl implements BankService {
	/** 银行信息接口 */
	@Autowired
	private BankRepository bankRepository;

	@Override
	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

}

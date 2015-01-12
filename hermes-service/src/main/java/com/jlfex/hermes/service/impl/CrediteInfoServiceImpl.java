package com.jlfex.hermes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.repository.CreditorInfoRepository;
import com.jlfex.hermes.service.CreditorInfoService;

@Service
@Transactional
public class CrediteInfoServiceImpl implements CreditorInfoService {
	@Autowired
	private CreditorInfoRepository creditorInfoRepository;

	public CrediteInfo findById(String id) {
		return creditorInfoRepository.findById(id);
	}

}

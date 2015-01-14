package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.CrediteInfo;

/**
 * 债权信息 仓库
 * 
 * @author Administrator
 * 
 */
@Repository
public interface CreditorInfoRepository extends JpaRepository<CrediteInfo, String> {

	public CrediteInfo findById(String id);

}

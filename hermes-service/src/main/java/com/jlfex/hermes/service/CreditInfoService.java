package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;



/**
 * 债权 信息
 * @author Administrator
 *
 */
public interface CreditInfoService {

    /**
     * 列表查询
     * @return
     */
	public List<CrediteInfo> findAll();

	/**
	 * 保存
	 * @param product
	 * @return
	 */
	public void save(CrediteInfo crediteInfo) throws Exception ;
	
	/**
	 * 保存
	 * @param product
	 * @return
	 */
	public List<CrediteInfo> saveBatch(List<CrediteInfo> list) throws Exception ;
	/**
	 * 根据：债权人 + 债权编号 获取债权信息
	 */
	public CrediteInfo findByCreditorAndCrediteCode(Creditor creditor ,String crediteCode) ;
	/**
	 * 债权 列表
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public Page<CrediteInfo>  queryByCondition(String page, String size) throws Exception ;
	/**
	 * 根据：id 查询一条债权信息
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public CrediteInfo  findById(String id) throws Exception ;
	
}

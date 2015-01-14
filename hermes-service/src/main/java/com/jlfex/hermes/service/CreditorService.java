package com.jlfex.hermes.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.jlfex.hermes.model.Creditor;



/**
 * 债权人 信息
 * @author Administrator
 *
 */
public interface CreditorService {

    /**
     * 列表查询
     * @return
     */
	public List<Creditor> findAll();

	/**
	 * id查询
	 * @param id
	 * @return
	 */
	public Creditor loadById(String id);
	/**
	 * 列表
	 * @param creditorName
	 * @param cellphone
	 * @param page
	 * @param size
	 * @return
	 */
	public  Page<Creditor> findCreditorList(final String creditorName, final String cellphone, String page, String size);
	/**
	 * 保存
	 * @param product
	 * @return
	 */
	public void save(Creditor creditor) throws Exception ;
	
	/**
	 * 获取 最大 债权人编号
	 * @return
	 * @throws Exception
	 */
	public Creditor findMaxCredtorNo() throws Exception ;
	
	/**
	 * 获取 最大 债权人编号
	 * @return
	 * @throws Exception
	 */
	public Creditor findByCredtorNo(String creditorNo)   ;
	
	
}

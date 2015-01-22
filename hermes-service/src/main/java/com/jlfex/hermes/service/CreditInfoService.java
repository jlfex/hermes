package com.jlfex.hermes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.model.User;



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
	public Page<CrediteInfo>  queryByCondition(CrediteInfo creditInfo,String page, String size, final List<String> statusList) throws Exception ;
	/**
	 * 根据：id 查询一条债权信息
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public CrediteInfo  findById(String id) throws Exception ;
	/**
	 * 发售债权
	 * @param crediteInfo
	 * @return
	 */
	public boolean  sellCredit(CrediteInfo crediteInfo) throws Exception;

    /**
     * 债权标 组装
     * @param entity
     * @param repay
     * @return
     * @throws Exception
     */
	public Loan buildLoan(CrediteInfo entity, Repay repay) throws Exception;
    /**
     * 债权表获取 偿还方式
     * @param repayWay
     * @return
     * @throws Exception
     */
	public Repay queryRepayObj(String repayWay) throws Exception;
    /**
     * 创建 债权标  虚拟产品
     * @param repay
     * @return
     * @throws Exception
     */
	public Product generateVirtualProduct(Repay repay) throws Exception;
    /**
     * 外部债权表： 费率初始化
     * @param product
     * @param rateVal
     * @param type
     * @throws Exception
     */
	public void initCreditRate(Product product, BigDecimal rateVal, String type) throws Exception;
    /**
     * 根据债权id 查找 loan信息
     * @param creditInfoId
     * @return
     */
	public List<Loan> queryLoanByCredit(String creditInfoId);
   /**
    *  根据id 获取用户信息
    * @param userId
    * @return
    */
	public User queryUserByID(String userId);
	/**
	 * 根据债权  获取操作日志
	 * @param creditId
	 * @return
	 */
	public List<LoanLog> queryCreditLogList(CrediteInfo creditInfo) throws Exception;
    /**
     * 债权标：自动流标后， 更新 债权 状态
     * @param loan
     * @return
     */
	public boolean afterBidAwayUpdateCredt(Loan loan) ;
}

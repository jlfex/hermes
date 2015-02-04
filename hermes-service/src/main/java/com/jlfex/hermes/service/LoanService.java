package com.jlfex.hermes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanAudit;
import com.jlfex.hermes.model.LoanAuth;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.service.pojo.LoanAuditInfo;
import com.jlfex.hermes.service.pojo.LoanInfo;
import com.jlfex.hermes.service.pojo.LoanStatusCount;
import com.jlfex.hermes.service.pojo.LoanUserBasic;
import com.jlfex.hermes.service.pojo.LoanUserInfo;

/**
 * 借款业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-11
 * @since 1.0
 */
public interface LoanService {

	/**
	 * 查询所有借款
	 * 
	 * @return
	 */
	public List<Loan> findAll();

	/**
	 * 通过状态统计
	 * 
	 * @param status
	 * @return
	 */
	public List<LoanStatusCount> countByStatus(String... status);

	/**
	 * 通过状态统计
	 * 
	 * @return
	 */
	public List<LoanStatusCount> countByStatus();

	/**
	 * 通过编号查询借款
	 * 
	 * @param id
	 * @return
	 */
	public Loan loadById(String id);

	/**
	 * 通过状态查询借款
	 * 
	 * @param limit
	 * @param status
	 * @return
	 */
	public List<Loan> findByStatus(Integer limit, String... status);
	
	/**
	 * 通过 类型 and 状态查询借款
	 * 
	 * @param limit
	 * @param status
	 * @return
	 */
	public List<Loan> findByKindAndStatus(Integer limit,String loanKind, String... status);

	/**
	 * 为首页查询借款
	 * 
	 * @return
	 */
	public List<LoanInfo> findForIndex(String loanKind);

	/**
	 * 通过多个条件查询借款
	 * 
	 * @param loanNo
	 * @param userName
	 * @param minAmount
	 * @param maxAmount
	 * @return
	 */
	public List<Loan> findByLoanNoAndUserNameAndAmountRange(String loanNo, String userName, BigDecimal minAmount, BigDecimal maxAmount);

	/**
	 * 审核借款
	 * 
	 * @param id
	 * @param remark
	 * @param amount
	 * @param isPass
	 * @return
	 */
	public Loan audit(String id, String remark, BigDecimal amount, boolean isPass);

	/**
	 * 借款放款
	 * 
	 * @param id
	 * @param remark
	 * @param isPass
	 * @return
	 */
	public Loan loanOut(String id, String remark, boolean isPass) throws Exception ;

	/**
	 * 借款催收
	 * 
	 * @param id
	 * @param date
	 * @param type
	 * @param remark
	 * @return
	 */
	public Loan demand(String id, String date, String type, String remark);

	/**
	 * 通过借款编号查询日志
	 * 
	 * @param loanId
	 * @return
	 */
	public List<LoanLog> findLogByLoanId(String loanId);

	/**
	 * 通过借款编号和类型查询日志
	 * 
	 * @param type
	 * @return
	 */
	public List<LoanLog> findLogByLoanIdAndType(String... type);

	/**
	 * 通过借款ID和类型查找单个借款日志对象
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public LoanLog loadLogByLoanIdAndType(String loanId, String type);

	/**
	 * 保存借款对象
	 * 
	 * @param loan
	 * @return
	 */
	public Loan save(Loan loan);

	/**
	 * 通过用户查找借款
	 * 
	 * @return
	 */
	public List<LoanInfo> findByUser(User user);

	/**
	 * 获取还款计划
	 * 
	 * @param loan
	 * @return
	 */
	public List<LoanRepay> getRepayPlan(Loan loan);

	/**
	 * 根据借款状态获取借款列表
	 * 
	 * @param status
	 * @return
	 */
	public List<Loan> findByStatus(String status);

	/**
	 * 通过用户查找前台借款用户信息
	 * 
	 * @param user
	 * @return
	 */
	public LoanUserInfo loadLoanUserInfoByUserId(String userId);

	/**
	 * 通过用户查找后台借款用户基本信息
	 * 
	 * @param user
	 * @return
	 */
	public LoanUserBasic loadLoanUserBasicByUserId(String userId);

	/**
	 * 借款审核
	 * 
	 * @param loan
	 *            借款对象
	 * @param isPass
	 *            通过/驳回
	 * @param type
	 *            初审/终审
	 * @param amount
	 *            变更金额
	 * @param remark
	 *            审核备注
	 * @return
	 */
	public Loan audit(Loan loan, Boolean isPass, String type, BigDecimal amount, String remark);

	/**
	 * 初审
	 * 
	 * @param loan
	 * @param isPass
	 * @param amount
	 * @param remark
	 * @return
	 */
	public Loan firstAudit(Loan loan, Boolean isPass, BigDecimal amount, String remark);

	/**
	 * 终审
	 * 
	 * @param loan
	 * @param isPass
	 * @param amount
	 * @param remark
	 * @param labels
	 * @return
	 */
	public Loan finalAudit(Loan loan, Boolean isPass, BigDecimal amount, String remark, List<String> labelList);

	/**
	 * 后台审核查询，用于初审终审查询等 通过借款编号/金额范围/昵称/状态查询借款信息
	 * 
	 * @param loanNo
	 * @param account
	 * @param startAmount
	 * @param endAmount
	 * @param page
	 * @param size
	 * @param status
	 * @return
	 */
	public Page<Loan> findByLoanNoAndAccountAndAmountBetweenAndStatus(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, Integer page, Integer size, String... status);

	/**
	 * 通过用户查找用户借款记录
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<LoanInfo> loadLoanRecordByUser(String userId, Integer page, Integer size);

	/**
	 * 后台审核查询，用于满标放款询等 通过借款编号/金额范围/昵称/状态查询借款信息
	 * 
	 * @param loanNo
	 * @param account
	 * @param startAmount
	 * @param endAmount
	 * @param page
	 * @param size
	 * @param status
	 * @return
	 */
	public Page<LoanAuditInfo> findByLoanNoAndNickAndAmountBetweenAndStatus(String loanNo, String account, BigDecimal startAmount, BigDecimal endAmount, String status, String page, String size);

	/**
	 * 后台审核查询，用于借款信息查询等 通过借款编号/手机号码查询借款信息
	 * 
	 * @param loanNo
	 * @param account
	 * @param startAmount
	 * @param endAmount
	 * @param page
	 * @param size
	 * @param status
	 * @return
	 */
	public Page<LoanAuditInfo> findByLoanNoAndCellphone(String loanNo, String cellphone, String page, String size);

	/**
	 * 根据借款信息查询该所有借款审核纪录
	 * 
	 * @return
	 */
	public List<LoanAudit> findLoanAuditByLoan(Loan loan);

	/**
	 * 根据借款信息查询该所有借款认证纪录
	 * 
	 * @return
	 */
	public List<LoanAuth> findLoanAuthByLoan(Loan loan);

	/**
	 *  变更借款表的状态
	 * @param id
	 * @param fromStatus
	 * @param toStatus
	 * @return
	 */
	public boolean updateStatus(String id, String fromStatus, String toStatus);
	/**
	 * 获取还款计划表id
	 * @param creditRepayPlanId
	 * @return
	 */
    public String queryLoanRepayId(String creditRepayPlanId) throws Exception;
    /**
     * 更新 债权还款计划明细状态
     * @param repayid
     * @param status
     * @return
     */
    public boolean  updateCreditRepayPlanStatus(String repayid, String  status) ;
    /**
     * 根据：债权人id 获取账户信息
     * @param creditorId
     * @return
     */
    public UserAccount queryUserAccountByCreditorId(String creditorId) ;
  
    /**
     * 债权账户虚拟线下充值
     * @param amount
     * @param accountId
     * @return
     */
	public UserAccount accountCharge(String amount, String accountId);
    
}

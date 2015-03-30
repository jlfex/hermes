package com.jlfex.hermes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.service.pojo.InvestInfo;
import com.jlfex.hermes.service.pojo.LoanInfo;
import com.jlfex.hermes.service.pojo.yltx.response.OrderPayResponseVo;

/**
 * 理财业务接口
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
 */
public interface InvestService {

	/**
	 * 保存理财对象
	 * 
	 * @param invest
	 * @return
	 */
	public Invest save(Invest invest);

	/**
	 * 通过编号查询理财
	 * 
	 * @param id
	 * @return
	 */
	public Invest loadById(String id);


	/**
	 * 通过查询条件查询借款相关信息
	 * 
	 * @param purpose
	 * @param raterange
	 * @param periodrange
	 * @param repayname
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<LoanInfo> findByJointSql(String purpose, String raterange, String periodrange, String repayname, String page, String size, String orderByField, String orderByDirection,String loanKind);

	/**
	 * 通过借款编号查询理财
	 * 
	 * @param loan
	 * @return
	 */
	public List<Invest> findByLoan(Loan loan);

	/**
	 * 投标操作
	 * 
	 * @param loanId
	 * @param investUser
	 * @param investAmount
	 * @param otherRepay
	 * @return
	 */
	public Map<String,String> bid(String loanId, User investUser, BigDecimal investAmount, String otherRepay) throws Exception;

	/**
	 * 根据用户和状态取记录数
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public Long loadCountByUserAndStatus(User user, String... status);


	/**
	 * 通过用户查找理财
	 * 
	 * @return
	 */
	public List<InvestInfo> findByUser(User user,String loanKind);

	/**
	 * 加载用户投标记录（分页）
	 * 
	 * @param userId
	 * @return
	 */
	public Page<InvestInfo> loadInvestRecordByUser(String userId, Integer page, Integer size);

	/**
	 * 自动任务，处理自动流标失败的接口
	 * @param loanId
	 * @return
	 */
	public boolean processAutoBidFailure(Loan loan);
	
	/**
	 * 我要理财 首页列表展示
	 * @param page
	 * @param size
	 * @param loanKind
	 * @return
	 */
	public Page<LoanInfo> investIndexLoanList(String page, String size,  String loanKind);
   /**
    * 自己的借款标自己不能投资
    * @param loanId
    * @param investUser
    * @return
    */
	public boolean bidAuthentication(String loanId, User investUser);

	/**
	 * 易联标 规则： 理财产品募资开始时间之后，募资截止日期的中午12点之前发起
	 * @param loan
	 * @return
	 */
	public boolean checkValid(Loan loan);
	/**
	 * 易联标： 下单支付
	 * @param loanId
	 * @param investUser
	 * @param investAmount
	 * @return
	 * @throws Exception
	 */
	public OrderPayResponseVo createJlfexOrder (String loanId, User investUser, BigDecimal investAmount) throws Exception;
    /**
     * 易联标：投标
     * @param loanId
     * @param investUser
     * @param investAmount
     * @param responseVo
     * @return
     * @throws Exception
     */
	public boolean jlfexBid(String loanId, User investUser,BigDecimal investAmount,OrderPayResponseVo responseVo) throws Exception;


}

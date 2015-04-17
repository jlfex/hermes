package com.jlfex.hermes.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.service.pojo.InvestProfitInfo;

/**
 * 理财收益业务接口
 */
public interface InvestProfitService {

	/**
	 * 保存理财收益对象
	 * @param investProfit
	 * @return
	 */
	public InvestProfit save(InvestProfit investProfit);

	/**
	 * 通过编号查询理财收益
	 * @param id
	 * @return
	 */
	public InvestProfit loadById(String id);
	
	/**
	 * 通过用户id和状态统计所有收益和
	 * @param user
	 * @param status
	 * @return
	 */
	public BigDecimal loadSumAllProfitByUserAndInStatus(User user,String... status);
	
	/**
	 * 通过用户id和状态统计所有利息和
	 * @param user
	 * @param status
	 * @return
	 */
	public BigDecimal loadInterestSumByUserAndInStatus(User user,String... status);
	
	/**
	 * 通过用户id和状态统计所有罚息和
	 * @param user
	 * @param status
	 * @return
	 */
	public BigDecimal loadOverdueInterestSumByUserAndInStatus(User user,String... status);
	

	// /**
	// * 通过传入理财id得到理财明细页面的数据
	// * @param investId
	// * @param page
	// * @param size
	// * @return
	// */
	//public Page<Map<String, Object>> findByJointSql(String investId, String page, String size);
	
	/**
	 * 此方法用于获取理财记录及最新一期待收
	 * @param invest
	 * @return
	 */
	public Page<InvestProfitInfo> getInvestProfitRecords(Invest invest,Integer page, Integer size);
	/**
	 * 债权标    统计所有收益和
	 * @param user
	 * @param loanKind
	 * @param profitState
	 * @return
	 */
	public InvestProfit sumAllProfitByAssignLoan(User user, List<String> loanKinds ,String... profitState);

	/**
	 * 根据理财  获取理财收益信息
	 * @param invest
	 * @return
	 * @throws Exception
	 */
	public List<InvestProfit> queryByInvest(Invest invest) throws Exception;

	

}

package com.jlfex.hermes.service.cfca;

import java.math.BigDecimal;
import java.util.Map;

import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;

import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.cfca.CFCAOrder;

/**
 * 中金订单服务类
 * 
 * @author jswu
 *
 */
public interface CFCAOrderService {
	public String genOrderTxSN();

	public ApiLog recordApiLog(Map<String, String> map);

	/**
	 * 
	 * @param investUser
	 *            投资人
	 * @param investAmount
	 *            投资金额
	 * @param bankAccount
	 *            银行账户
	 * @param userProperties
	 *            用户属性
	 * @param txSn
	 *            订单流水
	 * @return
	 */
	public Tx1361Request buildTx1361Request(User investUser, BigDecimal investAmount, BankAccount bankAccount, UserProperties userProperties, String txSn);

	/**
	 * 生成中金订单
	 * 
	 * @param response
	 *            调用1361接口报文
	 * @param invest
	 *            投资信息
	 * @param investAmount
	 *            投资金额
	 * @param txSN
	 *            订单流水号
	 * @param type
	 *            类型：01，投标，00充值
	 */
	public CFCAOrder genCFCAOrder(Tx1361Response response, Invest invest, BigDecimal investAmount, String txSN, String type);

	/**
	 * 生成中金定单
	 * 
	 * @param response
	 *            调用1361接口报文
	 * @param user
	 *            投资人
	 * @param investAmount
	 *            投资金额
	 * @param txSN
	 *            定单流水号
	 * @param type
	 *            类型：01，投标，00充值
	 */
	public CFCAOrder genCFCAOrder(Tx1361Response response, User user, BigDecimal investAmount, String txSN, String type);
}

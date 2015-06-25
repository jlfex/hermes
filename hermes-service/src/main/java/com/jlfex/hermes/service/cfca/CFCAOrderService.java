package com.jlfex.hermes.service.cfca;

import java.math.BigDecimal;
import java.util.Map;
import cfca.payment.api.tx.Tx1341Request;
import cfca.payment.api.tx.Tx134xResponse;
import cfca.payment.api.tx.Tx1350Response;
import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.cfca.CFCAOrder;

/**
 * 中金订单服务接口
 * 
 * @author jswu
 *
 */
public interface CFCAOrderService {

	/**
	 * 生成请求流水 
	 * @param type
	 * @return
	 */
	public String genSerialNo(String type);
    /**
     * 记录日志
     * @param map
     * @return
     */
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
	public Tx1361Request buildTx1361Request(User investUser, BigDecimal investAmount, BankAccount bankAccount, UserProperties userProperties,String serialNo) throws Exception;

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
	public CFCAOrder genCFCAOrder(Tx1361Response response, User user, BigDecimal investAmount, String txSN, String type, BigDecimal fee);
	
	/**
	 * 1341结算： 创建请求
	 * @param investUser     提现人
	 * @param withdrawAmount 提现金额
	 * @param fee            提现手续费
	 * @return
	 */
	public Tx1341Request buildTx1341Request(User investUser, BigDecimal withdrawAmount);
    /**
     * 1341结算：调用结算接口
     * @param request
     * @return
     */
	public Tx134xResponse invokeTx1341(Tx1341Request request) throws Exception;
    /**
     * 1341结算：订单保存
     * @param response
     * @param invest
     * @param investAmount
     * @param fee
     * @param txSN
     * @param type
     * @return
     */
	public CFCAOrder genClearOrder(Tx134xResponse response, User investUser, BigDecimal investAmount, BigDecimal fee, String txSN, String type);

	/**
	 * 1350结算：结果查询
	 * @param institutionID
	 * @param serialNumber
	 * @return
	 * @throws Exception
	 */
	public Tx1350Response invokeTx1350Request(String serialNumber) throws Exception;
}

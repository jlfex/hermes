package com.jlfex.hermes.service.cfca;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cfca.payment.api.tx.Tx1361Request;
import cfca.payment.api.tx.Tx1361Response;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.constant.HermesEnum.P2ZJBank;
import com.jlfex.hermes.common.constant.HermesEnum.P2ZJIdType;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.apiLog.ApiLogService;

/**
 * 中金订单服务
 * @author jswu
 *
 */
@Service
@Transactional
public class CFCAOrderServiceImpl implements CFCAOrderService {
	/** 当前日期 */
	private static String today;

	private final static String CACHE_TXSN_SEQUENCE = "com.jlfex.cache.txsnsequence";
	@Autowired
	private CFCAOrderRepository cFCAOrderRepository;
	@Autowired
	private ApiLogService apiLogService;
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 生成下一个订单流水
	 */
	@Override
	public synchronized String genOrderTxSN() {
		String maxTxSN = null;
		String date = Calendars.format("yyyyMMdd");
		List<CFCAOrder> cfcaOrders = cFCAOrderRepository.findAllOrderByTxSN();
		if (cfcaOrders != null && cfcaOrders.size() > 0) {
			maxTxSN = cfcaOrders.get(0).getTxSN();
			Logger.info("当前数据库中最大编号：txSN=%s", Long.valueOf(maxTxSN));
			if (!Strings.empty(maxTxSN) && maxTxSN.length() == 13) {
				today = maxTxSN.substring(0, 8);
				maxTxSN = maxTxSN.substring(8);
				Caches.set(CACHE_TXSN_SEQUENCE, Long.valueOf(maxTxSN));
			}
		}
		if (maxTxSN == null) {
			today = date;
			Caches.set(CACHE_TXSN_SEQUENCE, 0);
		} else {
			Date dbDate = null, nowDate = null;
			try {
				dbDate = Calendars.parse("yyyyMMdd", today);
				nowDate = Calendars.parse("yyyyMMdd", date);
				if (dbDate.before(nowDate)) {
					today = date;
					Caches.set(CACHE_TXSN_SEQUENCE, 0);
				}
			} catch (ParseException e) {
				Logger.error("生成txSN时：格式化时间异常：dbDate=" + dbDate + ",nowDate=" + date);
				today = date;
			}
		}
		// 递增缓存数据
		Long seq = Caches.incr(CACHE_TXSN_SEQUENCE, 1);
		Logger.info("新创建的中金订单流水号：txSN=%s", String.format("%s%05d", today, seq));
		return String.format("%s%05d", today, seq);
	}

	/**
	 * 记录调用中金日志
	 * 
	 * @param apiConfig
	 * @param map
	 * @return
	 */
	public ApiLog recordApiLog(Map<String, String> map) {
		ApiLog apiLog = new ApiLog();
		apiLog.setCreator(HermesConstants.PLAT_MANAGER);
		apiLog.setUpdater(HermesConstants.PLAT_MANAGER);
		apiLog.setRequestTime(new Date());
		// 默认
		apiLog.setDealFlag(ApiLog.DealResult.FAIL);
		if (!Strings.empty(map.get("interfaceMethod"))) {
			apiLog.setInterfaceName(map.get("interfaceMethod"));
		}
		if (!Strings.empty(map.get("requestMsg"))) {
			apiLog.setRequestMessage(map.get("requestMsg"));
		}
		if (!Strings.empty(map.get("exception"))) {
			apiLog.setException(map.get("exception"));
		}
		try {
			return apiLogService.saveApiLog(apiLog);
		} catch (Exception e) {
			Logger.error("接口日志对象保存异常：", e);
			return null;
		}
	}

	/**
	 * 生成1361报文
	 */
	@Override
	public Tx1361Request buildTx1361Request(User investUser, BigDecimal investAmount, BankAccount bankAccount, UserProperties userProperties, String txSn) {
		Tx1361Request tx1361Request = new Tx1361Request();
		tx1361Request.setInstitutionID(HermesConstants.CFCA_INSTITUTION_ID);
		tx1361Request.setTxSN(txSn);
		tx1361Request.setOrderNo(HermesConstants.CFCA_MARKET_ORDER_NO);
		tx1361Request.setAmount(investAmount.multiply(new BigDecimal(100)).longValue());
		tx1361Request.setBankID(Dicts.name(bankAccount.getBank().getName(), "", P2ZJBank.class));
		tx1361Request.setAccountName(bankAccount.getName());
		tx1361Request.setAccountNumber(bankAccount.getAccount());
		tx1361Request.setBranchName(bankAccount.getDeposit());
		tx1361Request.setProvince(bankAccount.getCity().getName());
		tx1361Request.setAccountType(11);
		tx1361Request.setCity(bankAccount.getCity().getName());
		tx1361Request.setPhoneNumber(investUser.getCellphone());
		tx1361Request.setEmail(investUser.getEmail());
		tx1361Request.setIdentificationNumber(userProperties.getIdNumber());
		tx1361Request.setIdentificationType(Dicts.name(userProperties.getIdType(), P2ZJIdType.class));

		return tx1361Request;
	}

	/**
	 * 生成中金订单
	 */
	@Override
	public CFCAOrder genCFCAOrder(Tx1361Response response, Invest invest, BigDecimal investAmount, String txSN, String type) {
		CFCAOrder cfcaOrder = new CFCAOrder();
		cfcaOrder.setAmount(investAmount);
		cfcaOrder.setInvest(invest);
		cfcaOrder.setBankTxTime(response.getBankTxTime());
		cfcaOrder.setCode(response.getCode());
		cfcaOrder.setMessage(response.getMessage());
		cfcaOrder.setOrderNo(response.getOrderNo());
		cfcaOrder.setResponseCode(response.getResponseCode());
		cfcaOrder.setResponseMessage(response.getResponseMessage());
		cfcaOrder.setStatus(response.getStatus());
		cfcaOrder.setTxSN(txSN);
		cfcaOrder.setUser(invest.getUser());
		cfcaOrder.setType(type);
		return cFCAOrderRepository.save(cfcaOrder);
	}

	@Override
	public CFCAOrder genCFCAOrder(Tx1361Response response, User user, BigDecimal investAmount, String txSN, String type, BigDecimal fee) {
		CFCAOrder cfcaOrder = new CFCAOrder();
		cfcaOrder.setAmount(investAmount);
		cfcaOrder.setInvest(null);
		cfcaOrder.setFee(fee);
		cfcaOrder.setBankTxTime(response.getBankTxTime());
		cfcaOrder.setCode(response.getCode());
		cfcaOrder.setMessage(response.getMessage());
		cfcaOrder.setOrderNo(response.getOrderNo());
		cfcaOrder.setResponseCode(response.getResponseCode());
		cfcaOrder.setResponseMessage(response.getResponseMessage());
		cfcaOrder.setStatus(response.getStatus());
		cfcaOrder.setTxSN(txSN);
		cfcaOrder.setUser(user);
		cfcaOrder.setType(type);
		return cFCAOrderRepository.save(cfcaOrder);
	}
}

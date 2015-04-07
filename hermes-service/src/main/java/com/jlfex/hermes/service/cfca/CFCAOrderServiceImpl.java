package com.jlfex.hermes.service.cfca;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.repository.cfca.CFCAOrderRepository;

/**
 * 
 * @author jswu
 *
 */
@Service
@Transactional
public class CFCAOrderServiceImpl implements CFCAOrderService{
	/** 当前日期 */
	private static String today;

	private final static String CACHE_TXSN_SEQUENCE = "com.jlfex.cache.txsnsequence";
	@Autowired
	private CFCAOrderRepository cFCAOrderRepository;
	
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
}

package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.support.spring.SpringWebApp;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.RequestParam;
import com.jlfex.hermes.common.web.WebApp;
import com.jlfex.hermes.model.Payment;
import com.jlfex.hermes.model.PaymentChannel;
import com.jlfex.hermes.model.PaymentChannelAttribute;
import com.jlfex.hermes.model.PaymentLog;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.repository.PaymentChannelAttributeRepository;
import com.jlfex.hermes.repository.PaymentChannelRepository;
import com.jlfex.hermes.repository.PaymentLogRepository;
import com.jlfex.hermes.repository.PaymentRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.PaymentService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.payment.PaymentChannelImpl;

/**
 * 支付业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-08
 * @since 1.0
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private static final String CACHE_SEQ_PAYMENT	= "com.jlfex.hermes.cache.seq.payment";
	
	private static final String PROP_CHARGE_RATE	= "fee.charge.rate";
	
	private static final String URL_RETURN 			= "account/payment/return";
	private static final String URL_NOTIFY			= "account/payment/notify";
	
	private static String staticDate = null;
	
	/** 实例管理器 */
	@PersistenceContext
	private EntityManager em;
	
	/** 支付信息仓库 */
	@Autowired
	private PaymentRepository paymentRepository;
	
	/** 支付日志信息仓库 */
	@Autowired
	private PaymentLogRepository paymentLogRepository;
	
	/** 支付信息仓库 */
	@Autowired
	private PaymentChannelRepository paymentChannelRepository;
	
	/** 支付渠道参数信息仓库 */
	@Autowired
	private PaymentChannelAttributeRepository paymentChannelAttributeRepository;
	
	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;
	
	/** 交易业务接口 */
	@Autowired
	private TransactionService transactionService;
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#findChannelByGroupAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, List<PaymentChannel>> findChannelByGroupAll() {
		// 初始化
		Map<String, List<PaymentChannel>> map = new LinkedHashMap<String, List<PaymentChannel>>();
		Map<Object, String> types = Dicts.elements(PaymentChannel.Type.class);
		
		// 根据类型分别查询支付方式
		for (Entry<Object, String> entry: types.entrySet()) {
			List<PaymentChannel> payments = findEnabledChannelByType(String.class.cast(entry.getKey()));
			if (payments != null && payments.size() > 0) { map.put(entry.getValue(), payments); }
		}
		
		// 返回结果
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#findChannelByTypeAndStatus(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PaymentChannel> findChannelByTypeAndStatus(String type, String status) {
		Assert.notEmpty(type, "type is empty.");
		Assert.notEmpty(status, "status is empty.");
		return paymentChannelRepository.findByTypeAndStatus(type, status);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#findEnabledChannelByType(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PaymentChannel> findEnabledChannelByType(String type) {
		return findChannelByTypeAndStatus(type, PaymentChannel.Status.ENABLED);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#findAttributeMapByChannel(com.jlfex.hermes.model.PaymentChannel)
	 */
	@Override
	public Map<String, String> findAttributeMapByChannel(PaymentChannel paymentChannel) {
		// 初始化并查询数据
		Map<String, String> attrs = new HashMap<String, String>();
		List<PaymentChannelAttribute> pcattrs = paymentChannelAttributeRepository.findByChannel(paymentChannel);
		
		// 处理结果
		for (PaymentChannelAttribute pca: pcattrs) attrs.put(pca.getCode(), pca.getValue());
		
		// 返回结果
		return attrs;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#calcChargeFee(java.lang.Double)
	 */
	@Override
	@Transactional(readOnly = true)
	public BigDecimal calcChargeFee(Double amount) {
		// 初始化
		BigDecimal rate = BigDecimal.valueOf(Double.valueOf(App.config(PROP_CHARGE_RATE)));
		BigDecimal amt = Numbers.currency(amount);
		BigDecimal fee = amt.multiply(rate);
		
		// 返回结果
		return fee;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#save(com.jlfex.hermes.model.Payment)
	 */
	@Override
	public Payment save(Payment payment) {
		Assert.notNull(payment, "payment is null.");
		return paymentRepository.save(payment);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#save(java.lang.String, java.lang.Double)
	 */
	@Override
	public Payment save(String channel, Double amount) {
		// 验证参数
		Assert.notEmpty(channel, "channel is empty.");
		Assert.notNull(amount, "amount is null.");
		
		// 初始化
		Payment payment = new Payment();
		BigDecimal fee = calcChargeFee(amount);
		BigDecimal amt = Numbers.currency(amount);
		User user = userRepository.findOne(App.user().getId());
		PaymentChannel paymentChannel = paymentChannelRepository.findOne(channel);
		
		// 设置数据
		payment.setUser(user);
		payment.setChannel(paymentChannel);
		payment.setDatetime(new Date());
		payment.setSequence(getSequence());
		payment.setAmount(amt);
		payment.setFee(fee);
		payment.setStatus(Payment.Status.WAIT);
		
		// 保存并返回结果
		return save(payment);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#callback(java.lang.String, com.jlfex.hermes.common.web.RequestParam, java.lang.String)
	 */
	@Override
	public Result<Payment> callback(String id, RequestParam param, String type) {
		// 查询数据并初始化
		PaymentChannel channel = paymentChannelRepository.findOne(id);
		PaymentChannelImpl impl = getPaymentChannelImpl(channel);
		Map<String, String> attrs = findAttributeMapByChannel(channel);
		Result<Payment> result = null;
		
		// 判断操作类型并处理
		if (Strings.equals(TYPE_RETURN, type)) {
			result =  impl.doReturn(param, attrs);
		} else if (Strings.equals(TYPE_NOTIFY, type)) {
			result = impl.doNotify(param, attrs);
		} else {
			throw new ServiceException("type '" + type + "' is not defined.");
		}
		
		// 处理支付记录
		// 仅当状态为等待或已付款未确认时进行处理
		Payment payment = result.getData();
		payment = (!Strings.empty(payment.getId())) ? paymentRepository.findOne(payment.getId()) : paymentRepository.findBySequence(payment.getSequence());
		if (Strings.equals(Payment.Status.WAIT, payment.getStatus()) || Strings.equals(Payment.Status.PAIED, payment.getStatus())) {
			payment.setStatus(result.getData().getStatus());
			paymentRepository.save(payment);
			
			// 仅当处理成功时进行转账处理
			if (Strings.equals(Payment.Status.SUCCESS, result.getData().getStatus())) {
				transactionService.fromCropAccount(Transaction.Type.CHARGE, payment.getUser(), UserAccount.Type.PAYMENT, payment.getAmount(), payment.getId(), App.message("transaction.charge"));
				transactionService.betweenCropAccount(Transaction.Type.OUT, UserAccount.Type.PAYMENT, UserAccount.Type.PAYMENT_FEE, payment.getFee(), payment.getId(), App.message("transaction.charge.fee"));
			}
		}
		
		// 记录支付回调日志
		PaymentLog log = new PaymentLog();
		log.setPayment(payment);
		log.setDatetime(new Date());
		log.setMethod(type);
		log.setStatus(result.getData().getStatus());
		log.setRaw(JSON.toJSONString(param.get()));
		paymentLogRepository.save(log);
		
		// 返回结果
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#getPaymentForm(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPaymentForm(String id, String remoteIp) {
		// 初始化并查询数据
		Payment payment = paymentRepository.findOne(id);
		PaymentChannelImpl impl = getPaymentChannelImpl(payment.getChannel());
		Map<String, String> attrs = findAttributeMapByChannel(payment.getChannel());
		String returnUrl = String.format("%s/%s/%s", App.current(WebApp.class).getFullPath(), URL_RETURN, payment.getChannel().getId());
		String notifyUrl = String.format("%s/%s/%s", App.current(WebApp.class).getFullPath(), URL_NOTIFY, payment.getChannel().getId());
		
		// 将实例游离态化
		em.clear();
		
		// 生成表单并返回
		return impl.generateForm(payment, attrs, returnUrl, notifyUrl, remoteIp).toString();
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#getPaymentChannelImpl(com.jlfex.hermes.model.PaymentChannel)
	 */
	@Override
	public PaymentChannelImpl getPaymentChannelImpl(PaymentChannel channel) {
		// 若存在代码则通过容器获取实例
		if (!Strings.empty(channel.getCode())) {
			try {
				return SpringWebApp.getBean(channel.getCode(), PaymentChannelImpl.class);
			} catch (Exception e) {
				Logger.debug("cannot get payment channel instance by code: %s", channel.getCode());
			}
		}
		
		// 若无法通过容器获取实例则通过全类名获取实例
		try {
			return PaymentChannelImpl.class.cast(Class.forName(channel.getClazz()).newInstance());
		} catch (InstantiationException e) {
			throw new ServiceException("cannot get payment channel instance.", e);
		} catch (IllegalAccessException e) {
			throw new ServiceException("cannot get payment channel instance.", e);
		} catch (ClassNotFoundException e) {
			throw new ServiceException("cannot get payment channel instance.", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.PaymentService#getSequence()
	 */
	@Override
	public synchronized Long getSequence() {
		// 初始化
		String today = Calendars.format("yyyyMMdd");
		
		// 判断序号是否存在
		// 若不存在则初始化
		if (Caches.get(CACHE_SEQ_PAYMENT) == null || staticDate == null) {
			Page<Payment> payments = paymentRepository.findAll(Pageables.pageable(0, 1, Direction.DESC, "sequence"));
			Long maxSequence = (payments.getNumberOfElements() >= 1) ? payments.getContent().get(0).getSequence() : Long.valueOf(String.format("%s%04d", today, 0));
			staticDate = String.valueOf(maxSequence / 10000);
			Caches.set(CACHE_SEQ_PAYMENT, maxSequence);
		}
		
		// 判断日期是否与当前日期匹配
		// 若未匹配则重置序号
		if (!Strings.equals(today, staticDate)) {
			staticDate = today;
			Caches.set(CACHE_SEQ_PAYMENT, Long.valueOf(String.format("%s%04d", today, 0)));
		}
		
		// 返回数据
		return Caches.incr(CACHE_SEQ_PAYMENT, 1);
	}
}

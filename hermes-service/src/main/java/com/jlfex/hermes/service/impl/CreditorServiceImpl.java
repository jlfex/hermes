package com.jlfex.hermes.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.User.Status;
import com.jlfex.hermes.model.User.Type;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserAccount.Minus;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserProperties.Auth;
import com.jlfex.hermes.model.UserProperties.Mortgagor;
import com.jlfex.hermes.repository.CreditorRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.CreditorService;
import com.jlfex.hermes.service.common.Pageables;

/**
 * 债权人 信息 管理
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class CreditorServiceImpl implements CreditorService {

	private static String today;
	private final static String CACHE_CREDITOR_SEQUENCE = "com.jlfex.cache.creditorsequence";
	
	@Autowired
	private CreditorRepository creditorRepository;
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	@Override
	public List<Creditor> findAll() {
		return creditorRepository.findAll();
	}

	public Page<Creditor> findCreditorList(final String creditorName, final String cellphone, String page, String size) {
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")));
		Page<Creditor> pageCreditorInfo = creditorRepository.findAll(new Specification<Creditor>() {
			@Override
			public Predicate toPredicate(Root<Creditor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> p = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(creditorName)) {
					p.add(cb.equal(root.get("creditorName"), creditorName));
				}
				if (StringUtils.isNotEmpty(cellphone)) {
					p.add(cb.equal(root.get("cellphone"), cellphone));
				}
				return cb.and(p.toArray(new Predicate[p.size()]));
			}
		}, pageable);
		return pageCreditorInfo;
	}

	@Override
	public Creditor loadById(String id) {
		return creditorRepository.findAllById(id);
	}

	@Override
	public Creditor  save(Creditor creditor) throws Exception {
		if (creditor != null && Strings.empty(creditor.getId())) {
			creditor.setUser(buildAccount(creditor));
		}
		return creditorRepository.save(creditor);
	}

	/**
	 * 创建 债权人 账户信息
	 * 
	 * @return
	 */
	public User buildAccount(Creditor creditor) throws Exception {
		User obj = new User();
		obj.setAccount(creditor.getCreditorNo());
		obj.setType(Type.CREDIT);
		obj.setStatus(Status.ENABLED);
		obj.setSignPassword("");
		obj.setRealName(creditor.getCreditorName());
		obj.setCellphone(creditor.getCellphone());
		User user = userRepository.save(obj);

		// 用户属性信息
		UserProperties userProperties = new UserProperties();
		userProperties.setUser(user);
		userProperties.setRealName(user.getRealName());
		userProperties.setAuthCellphone(Auth.WAIT);
		userProperties.setAuthEmail(Auth.WAIT);
		userProperties.setAuthName(Auth.WAIT);
		userProperties.setIsMortgagor(Mortgagor.ALL);
		userPropertiesRepository.save(userProperties);

		// 创建用户的现金账户
		UserAccount cashAccount = new UserAccount();
		cashAccount.setUser(user);
		cashAccount.setMinus(Minus.INMINUS);
		cashAccount.setStatus(com.jlfex.hermes.model.UserAccount.Status.VALID);
		cashAccount.setType(com.jlfex.hermes.model.UserAccount.Type.CASH);
		cashAccount.setBalance(BigDecimal.ZERO);
		// 创建用户的冻结账户
		UserAccount freezeAccount = new UserAccount();
		freezeAccount.setUser(user);
		freezeAccount.setMinus(Minus.INMINUS);
		freezeAccount.setStatus(com.jlfex.hermes.model.UserAccount.Status.VALID);
		freezeAccount.setType(com.jlfex.hermes.model.UserAccount.Type.FREEZE);
		freezeAccount.setBalance(BigDecimal.ZERO);
		List<UserAccount> accountList = new ArrayList<UserAccount>();
		accountList.add(cashAccount);
		accountList.add(freezeAccount);
		userAccountRepository.save(accountList);
		Logger.info("债权人:" + user.getId() + " 现金账户、冻结账户 创建成功");
		return user;

	}

	/**
	 * 债权人 最大编号
	 */
	@Override
	public List<Creditor> findMaxCredtorNo() throws Exception {
		return creditorRepository.findMaxCredtorNo();
	}

	/**
	 * 根据 债权人编号 获取债权人信息
	 */
	@Override
	public Creditor findByCredtorNo(String creditorNo) {
		try {
			return creditorRepository.findByCredtorNo(creditorNo);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 生成债权人编号
	 * @return
	 */
	public synchronized String generateCreditorNo() throws Exception {
		String date = Calendars.format("yyyyMMdd");
		Creditor creditor = null;
		List<Creditor> creditList = findMaxCredtorNo();
		if (creditList != null && creditList.size() > 0) {
			creditor = creditList.get(0);
			if (creditor != null && !Strings.empty(creditor.getCreditorNo())) {
				String currMaxCreditNo = creditor.getCreditorNo();
				if (!Strings.empty(currMaxCreditNo) && currMaxCreditNo.length() == 14 ) {
					today = currMaxCreditNo.substring(2, 10);
					if(HermesConstants.PRE_ZQ.equals(currMaxCreditNo.substring(0, 2))){
						currMaxCreditNo = currMaxCreditNo.substring(10);
						Caches.set(CACHE_CREDITOR_SEQUENCE, Long.valueOf(currMaxCreditNo));
						Logger.info("数据库总：最大的债权人编号是：" + currMaxCreditNo);
					}
				}
			}
		}
		// 判断缓存序列是否存在 若不存在则初始化
		if (Caches.get(CACHE_CREDITOR_SEQUENCE) == null) {
			Caches.set(CACHE_CREDITOR_SEQUENCE, 0);
		}
		// 若未匹配则重置序列编号 判断日期是否与当前日期匹配
		if (Strings.empty(today)) {
			today = date;
		} else {
			int num_nowDate = Integer.parseInt(date);
			int num_today = Integer.parseInt(Strings.empty(today, "0"));
			if (!date.equals(today)) {
				if (num_today < num_nowDate) {
					today = date;
					Caches.set(CACHE_CREDITOR_SEQUENCE, 0);
				}
			}
		}
		Long seq = Caches.incr(CACHE_CREDITOR_SEQUENCE, 1);// 递增缓存数据
		return String.format("ZQ%s%04d", today, seq);
	}
	
	/**
	 * 根据债权人原始编号获取债权人信息
	 */
	@Override
	public Creditor findByOriginNo(String creditorNo) {
		try {
			return creditorRepository.findByOriginNo(creditorNo);
		} catch (Exception e) {
			return null;
		}
	}

}

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
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "5")));
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
	public void save(Creditor creditor) throws Exception {
		if (creditor != null && Strings.empty(creditor.getId())) {
			creditor.setUser(buildAccount(creditor));
		}
		creditorRepository.save(creditor);
	}

	/**
	 * 创建 债权人 账户信息
	 * 
	 * @return
	 */
	public User buildAccount(Creditor creditor) throws Exception {
		User obj = new User();
		obj.setAccount(creditor.getCreditorName());
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

}

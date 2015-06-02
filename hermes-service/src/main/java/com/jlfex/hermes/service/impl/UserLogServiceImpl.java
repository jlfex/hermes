package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.repository.UserLogRepository;
import com.jlfex.hermes.service.UserLogService;
import com.jlfex.hermes.service.pojo.UserLogVo;

@Service
@Transactional
public class UserLogServiceImpl implements UserLogService {
	/** 用户日志仓库 */
	@Autowired
	private UserLogRepository userLogRepository;

	@Override
	public void saveUserLog(UserLog userLog) {
		userLogRepository.save(userLog);
	}

	/**
	 * 获取用户操作日志 用户
	 */
	@Override
	public List<UserLog> queryUserLogByuser(User user) {
		return userLogRepository.findByUser(user);
	}

	@Override
	public Page<UserLog> queryByCondition(final UserLogVo userLogVo, String page, String size) throws Exception {
		Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC, "createTime"));
		return userLogRepository.findAll(new Specification<UserLog>() {
			@Override
			public Predicate toPredicate(Root<UserLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(userLogVo.getEmail())) {
					list.add(cb.like(root.<User> get("user").<String> get("email"), "%" + userLogVo.getEmail().trim() + "%"));
				}
				
				if (StringUtils.isNotEmpty(userLogVo.getType())) {
					list.add(cb.equal(root.<String> get("type"), userLogVo.getType().trim()));
				}
				
				if (StringUtils.isNotEmpty(userLogVo.getBeginDate()) && StringUtils.isNotEmpty(userLogVo.getEndDate())) {
					Date beginDate = null, endDate = null;
					try {
						beginDate = Calendars.parse("yyyy-MM-dd HH:mm:ss", userLogVo.getBeginDate());
						endDate = Calendars.parse("yyyy-MM-dd HH:mm:ss", userLogVo.getEndDate());
					} catch (Exception e) {
						Logger.error("日志列表查询：格式化导入开始时间[" + beginDate + "]，结束时间[" + endDate + "],异常，忽略时间查询条件");
					}
					list.add(cb.greaterThanOrEqualTo(root.<Date> get("datetime"), beginDate));
					list.add(cb.lessThanOrEqualTo(root.<Date> get("datetime"), endDate));
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		}, pageable);
	}

	@Override
	public UserLog findOne(String id) {
		return userLogRepository.findOne(id);
	}
}

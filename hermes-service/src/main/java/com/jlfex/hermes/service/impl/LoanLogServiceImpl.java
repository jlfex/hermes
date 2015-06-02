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
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.service.LoanLogService;
import com.jlfex.hermes.service.pojo.LoanLogVo;

@Service
@Transactional
public class LoanLogServiceImpl implements LoanLogService {
	@Autowired
	private LoanLogRepository loanLogRepository;

	@Override
	public Page<LoanLog> queryByCondition(final LoanLogVo loanLogVo, String page, String size) throws Exception {
		Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC, "createTime"));
		return loanLogRepository.findAll(new Specification<LoanLog>() {
			@Override
			public Predicate toPredicate(Root<LoanLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(loanLogVo.getLoanNo())) {
					list.add(cb.like(root.<Loan> get("loan").<String> get("loanNo"), "%" + loanLogVo.getLoanNo().trim() + "%"));
				}
				
				if (StringUtils.isNotEmpty(loanLogVo.getType())) {
					list.add(cb.equal(root.<String> get("type"), loanLogVo.getType().trim()));
				}
				
				if (StringUtils.isNotEmpty(loanLogVo.getBeginDate()) && StringUtils.isNotEmpty(loanLogVo.getEndDate())) {
					Date beginDate = null, endDate = null;
					try {
						beginDate = Calendars.parse("yyyy-MM-dd HH:mm:ss", loanLogVo.getBeginDate());
						endDate = Calendars.parse("yyyy-MM-dd HH:mm:ss", loanLogVo.getEndDate());
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
	public LoanLog findOne(String id) {
		return loanLogRepository.findOne(id);
	}
}

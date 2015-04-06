package com.jlfex.hermes.service.apiLog;

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
import com.jlfex.hermes.model.ApiLog;
import com.jlfex.hermes.repository.apiLog.ApiLogRepository;
import com.jlfex.hermes.service.pojo.yltx.ApiLogVo;


/**
 * 外围系统日志
 * @author Administrator
 *
 */
@Service
@Transactional
public class ApiLogServiceImpl implements  ApiLogService {

	@Autowired
	private ApiLogRepository apiLogRepository;
	
	/**
	 * 保存交互日志
	 */
	@Override
	public ApiLog saveApiLog(ApiLog apiLog) throws Exception {
		return apiLogRepository.save(apiLog);
	}
	/**
	 * 外围日志列表
	 * @param page
	 * @param size
	 */
	@Override
	public Page<ApiLog> queryByCondition(final ApiLogVo apiLogVo, String page, String size) throws Exception {
		 Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC,  "createTime"));
		 return  apiLogRepository.findAll(new Specification<ApiLog>() {
			@Override
			public Predicate toPredicate(Root<ApiLog> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(apiLogVo.getSerialNo())) {
					list.add(cb.like(root.<String>get("serialNo"), "%"+apiLogVo.getSerialNo().trim()+"%"));
				}
				if (StringUtils.isNotEmpty(apiLogVo.getInterfaceName())) {
					list.add(cb.like(root.<String>get("interfaceName"), "%"+apiLogVo.getInterfaceName().trim()+"%"));
				}
				if (StringUtils.isNotEmpty(apiLogVo.getStatus())) {
					list.add(cb.equal(root.<String>get("dealFlag"), apiLogVo.getStatus().trim()));
				}
				if(StringUtils.isNotEmpty(apiLogVo.getBeginDate()) && StringUtils.isNotEmpty(apiLogVo.getEndDate())){
					Date beginDate = null, endDate = null;
					try{
							beginDate = Calendars.parse("yyyy-MM-dd HH:mm:ss", apiLogVo.getBeginDate());
							endDate = Calendars.parse("yyyy-MM-dd HH:mm:ss", apiLogVo.getEndDate());
					}catch(Exception e){
						Logger.error("日志列表查询：格式化导入开始时间["+beginDate+"]，结束时间["+endDate+"],异常，忽略时间查询条件");
					}
					list.add(cb.greaterThanOrEqualTo(root.<Date> get("requestTime"), beginDate));
					list.add(cb.lessThanOrEqualTo(root.<Date> get("requestTime"), endDate));
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		},pageable);
	}
	@Override
	public ApiLog findOne(String id) {
		return apiLogRepository.findOne(id);
	}
	
}

package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
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
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.service.DictionaryTypeService;

/**
 * 字典类型接口实现
 */
@Service
@Transactional
public class DictionaryTypeServiceImpl implements DictionaryTypeService {
	/** 字典信息仓库 */
	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;

	@Override
	public Page<DictionaryType> queryByCondition(final DictionaryType dictionaryType, String page, String size) throws Exception {
		 Pageable pageable = new PageRequest(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "10")), new Sort(Direction.DESC,  "createTime"));
		 return  dictionaryTypeRepository.findAll(new Specification<DictionaryType>() {
			@Override
			public Predicate toPredicate(Root<DictionaryType> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(dictionaryType.getCode())) {
					list.add(cb.like(root.<String>get("code"), "%"+dictionaryType.getCode().trim()+"%"));
				}
				if (StringUtils.isNotEmpty(dictionaryType.getName())) {
					list.add(cb.like(root.<String>get("name"), "%"+dictionaryType.getName().trim()+"%"));
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		},pageable);
	}

	@Override
	public List<DictionaryType> findByName(String name) {
		return dictionaryTypeRepository.findByName(name);
	}

	@Override
	public DictionaryType addOrUpdateDictionaryType(DictionaryType dictionaryType) {
		DictionaryType dicType = null;
		if(StringUtils.isEmpty(dictionaryType.getId())){
			dicType = new DictionaryType();
		}else{
			dicType = dictionaryTypeRepository.findOne(dictionaryType.getId());
		}
		dicType.setName(dictionaryType.getName());
		dicType.setStatus(dictionaryType.getStatus());
		dicType.setCode(dictionaryType.getCode());
		dictionaryTypeRepository.save(dicType);
		return dictionaryType;
	}

	@Override
	public List<DictionaryType> findByCode(String code) {
		return dictionaryTypeRepository.findAllByCode(code);
	}

	@Override
	public void delDictionaryType(String id) {
		dictionaryTypeRepository.delete(id);
	}

	@Override
	public DictionaryType findById(String id) {
		return dictionaryTypeRepository.findOne(id);
	}

}

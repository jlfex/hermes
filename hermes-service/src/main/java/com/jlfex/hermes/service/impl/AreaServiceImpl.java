package com.jlfex.hermes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.repository.AreaRepository;
import com.jlfex.hermes.service.AreaService;

/**
 * 地区业务实现
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-03
 * @since 1.0
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	private static final String CACHE_NAME		= "com.jlfex.hermes.cache.area";
	private static final String CACHE_PREFIX	= "com.jlfex.hermes.cache.area.";
	
	/** 地区信息仓库 */
	@Autowired
	private AreaRepository areaRepository;
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.AreaService#loadById(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Area loadById(String id) {
		return areaRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.AreaService#loadByParentId(java.lang.String)
	 */
	@Override
	public List<Area> loadByParentId(String parentId) {
		return areaRepository.findByParentIdOrderByCodeDesc(parentId);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.AreaService#getById(java.lang.String)
	 */
	@Override
	public Area getById(String id) {
		Area area = Caches.get(CACHE_PREFIX + id, Area.class);
		if (area == null) {
			area = areaRepository.findOne(id);
			if (area != null) Caches.add(CACHE_PREFIX + id, area);
		}
		return area;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.AreaService#getAllChildren(com.jlfex.hermes.model.Area)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAllChildren(Area area) {
		if (area == null) {
			List<Area> areas = Caches.get(CACHE_NAME, List.class);
			if (areas == null) {
				areas = areaRepository.findRoots();
				for (Area a: areas) getAllChildren(a);
				Caches.add(CACHE_NAME, areas);
			}
			return areas;
		} else {
			List<Area> areas = areaRepository.findByParentIdOrderByCodeDesc(area.getId());
			for (Area a: areas) getAllChildren(a);
			area.getChildren().addAll(areas);
			return areas;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.AreaService#getAddress(java.lang.String, java.lang.String[])
	 */
	@Override
	public String getAddress(String address, String... areas) {
		// 初始化
		StringBuilder sb = new StringBuilder();
		String prev = null;
		
		// 遍历地址
		for (String id: areas) {
			Area area = getById(id);
			if (area != null && !Strings.equals(area.getName(), prev)) {
				prev = area.getName();
				sb.append(prev);
			}
		}
		
		// 返回结果
		return sb.append(address).toString();
	}

	@Override
	public List<Area> findByParentIsNull() {
		// TODO Auto-generated method stub
		return areaRepository.findRoots();
	}
}

package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Area;

/**
 * 地区业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2014-01-03
 * @since 1.0
 */
public interface AreaService {
	
	/**
	 * 通过编号加载地区
	 * 
	 * @param id
	 * @return
	 */
	public Area loadById(String id);

	/**
	 * 通过父级加载地区
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Area> loadByParentId(String parentId);
	
	/**
	 * 通过编号读取地区<br>
	 * 加载缓存数据
	 * 
	 * @param id
	 * @return
	 */
	public Area getById(String id);
	
	/**
	 * 读取所有下级地区<br>
	 * 优先从缓存中加载
	 * 
	 * @param area
	 * @return
	 */
	public List<Area> getAllChildren(Area area);
	
	/**
	 * 读取地址
	 * 
	 * @param address
	 * @param areas
	 * @return
	 */
	public String getAddress(String address, String... areas);
}

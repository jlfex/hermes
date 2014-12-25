package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Navigation;

/**
 * 导航业务接口
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-18
 * @since 1.0
 */
public interface NavigationService {

	public static final String PATH_PREFIX = "@";
	
	/**
	 * 通过编号加载数据
	 * 
	 * @param id
	 * @return
	 */
	public Navigation loadById(String id);
	
	/**
	 * 通过类型代码查询顶级导航列表
	 * 
	 * @param typeCode
	 * @return
	 */
	public List<Navigation> findRootByTypeCode(String typeCode);
	
	/**
	 * 通过上级查询导航列表
	 * 
	 * @param parent
	 * @return
	 */
	public List<Navigation> findByParent(Navigation parent);
	
	/**
	 * 通过上级编号查询导航列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Navigation> findByParentId(String parentId);
}

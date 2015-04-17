package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.Advert;

/**
 * 广告业务接口
 */
public interface AdvertService {

	/**
	 * 通过代码查询广告
	 * 
	 * @param code
	 * @return
	 */
	List<Advert> findByCode(String code);
}

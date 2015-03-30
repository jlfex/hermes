package com.jlfex.hermes.service.userProperties;

import com.jlfex.hermes.model.UserProperties;

/**
 * 用户属性  业务
 * @author Administrator
 *
 */
public interface UserPropertiesService {
	
	public UserProperties queryByUser(String userId) throws Exception  ;

}

package com.jlfex.hermes.service.userProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.repository.UserPropertiesRepository;


/**
 * 用户属性 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserPropertiesServiceImpl implements  UserPropertiesService {

	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/**
	 * 根据用户查询 属性 
	 */
	@Override
	public UserProperties queryByUser(String userId) throws Exception {
		return userPropertiesRepository.findByUserId(userId);
	}
	
	
	
	

}

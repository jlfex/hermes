package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserProperties;

/**
 * 用户属性仓库
 */
@Repository
public interface UserPropertiesRepository extends JpaRepository<UserProperties, String>, JpaSpecificationExecutor<UserProperties> {
	
	/**
	 * 通过用户查询用户属性
	 * 
	 * @param user
	 * @return
	 */
	public UserProperties findByUser(User user);
	
	/**
	 * 通过用户编号查询用户属性
	 * 
	 * @param userId
	 * @return
	 */
	public UserProperties findByUserId(String userId);

	/**
	 * 通过手机认证状态以及用户查询用户属性
	 * 
	 * @param authPhone
	 * @param user
	 * @return
	 */
	public List<UserProperties> findByAuthCellphoneAndUserIn(String authPhone, List<User> user);

	/**
	 * 通过证件号码及类型并实名认证状态查询用户属性
	 * 
	 * @param idNumber
	 * @param idType
	 * @param authName
	 * @return
	 */
	public UserProperties findByIdNumberAndIdTypeAndAuthName(String idNumber, String idType, String authName);
}

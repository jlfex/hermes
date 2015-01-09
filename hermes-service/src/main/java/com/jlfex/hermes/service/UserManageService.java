package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserContacter;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.service.pojo.UserBasic;
import com.jlfex.hermes.service.pojo.UserInfo;

/**
 * 客户管理
 * 
 * 
 * @author Aether
 * @version 1.0, 2014-1-21
 * @since 1.0
 */
public interface UserManageService {
	/**
	 * 根据条件查询用户
	 * 
	 * @param userInfo
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<UserInfo> findByCondition(UserInfo userInfo, Integer page, Integer size);

	/**
	 * 根据用户查找账户信息
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<BankAccount> findBankByUser(String userId, Integer page, Integer size);

	/**
	 * 根据用户查询房产信息
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public List<UserHouse> findHouseByUser(String userId);

	/**
	 * 根据用户查找工作信息
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<UserJob> findJobByUser(String userId, Integer page, Integer size);

	/**
	 * 根据用户查找车辆信息
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<UserCar> findCarByUser(String userId, Integer page, Integer size);

	/**
	 * 根据用户查找联系人信息
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<UserContacter> findContacterByUser(String userId, Integer page, Integer size);

	/**
	 * 根据用户查找基本信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserBasic loadBasicByUser(String userId);

	/**
	 * 根据认证类型、用户查找图片
	 * 
	 * @param userId
	 * @param label
	 * @param type
	 * @return
	 */
	public List<UserImage> loadImageByUserAndLabelAndType(String userId, String label, String type);

	/**
	 * 冻结用户
	 * 
	 * @param userId
	 * @return
	 */
	public Result freezeUser(String userId);

	/**
	 * 解冻用户
	 * 
	 * @param userId
	 * @return
	 */
	public Result unfreezeUser(String userId);

	/**
	 * 注销用户
	 * 
	 * @param userId
	 * @return
	 */
	public Result logOffUser(String userId);

	/**
	 * 根据用户id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public User findById(String userId);
	/**
	 * 
	 * 通过用户编号和用户类型
	 * 
	 */
	public UserAccount findByUserIdAndType(String userId, String type);
}

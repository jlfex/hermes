package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserContacter;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.service.pojo.UserBasic;


/**
 * 用户个人信息接口
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
public interface UserInfoService {

	public User findByUserId(String userId);
	/**
	 * 显示用户的账户信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserAccount> findAccountByUserId(String userId);

	/**
	 * 查找用户的头像信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserImage findImageByUserIdAndType(String userId, String type);

	/**
	 * 显示用户的基本信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserBasic findUserInfoByUserId(String userId);

	/**
	 * 显示用户的工作信息
	 * 
	 * @param user
	 * @return
	 */
	public List<UserJob> findJobByUserId(User user);

	/**
	 * 显示用户的房产信息
	 * 
	 * @param user
	 * @return
	 */
	public List<UserHouse> findHouseByUserId(User user);

	/**
	 * 显示用户的车辆信息
	 * 
	 * @param user
	 * @return
	 */
	public List<UserCar> findCarByUserId(User user);

	/**
	 * 显示用户的联系人信息
	 * 
	 * @param user
	 * @return
	 */
	public List<UserContacter> findContacterByUserId(User user);

	/**
	 * 保存用户基本信息
	 * 
	 * @param userBasic
	 */
	public void saveUserBasicInfo(UserBasic userBasic, User user);

	/**
	 * 保存用户工作信息
	 * 
	 * @param userJob
	 */
	public void saveJobInfo(UserJob userJob);

	/**
	 * 保存用户房产信息
	 * 
	 * @param userHouse
	 */
	public void saveHouseInfo(UserHouse userHouse);

	/**
	 * 保存用户车辆信息
	 * 
	 * @param userCar
	 */
	public void saveCarInfo(UserCar userCar);

	/**
	 * 保存用户联系人信息
	 * 
	 * @param userContacter
	 */
	public void saveContacterInfo(UserContacter userContacter);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param orginalPwd
	 * @param newPwd
	 */
	public Result resetPassword(String userId, String orginalPwd, String newPwd);

	/**
	 * 用户属性信息
	 * 
	 * @param user
	 * @return
	 */
	public UserProperties getProByUser(User user);

	/**
	 * 保存用户图像信息
	 * 
	 * @param user
	 * @param imgStr
	 * @param type
	 * @param label
	 */
	public void saveImage(User user, String imgStr, String type, String label);

	/**
	 * 用户label
	 */
	public String[] loadLabel();

	/**
	 * 通过用户编号和类型加载用户帐号
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public UserAccount loadByUserIdAndType(String userId, String type);
	
	/**
	 * 通过用户编号加载用户属性
	 * 
	 * @param userId
	 * @return
	 */
	public UserProperties loadPropertiesByUserId(String userId);
	
	/**
	 * 通过用户及类型加载用户账户
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	public UserAccount loadAccountByUserAndType(User user, String type);

	/**
	 * 通过用户及类型加载图片信息
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	public List<UserImage> loadImagesByUserAndType(User user, String type);

	/**
	 * 根据id加载工作信息
	 * 
	 * @param jobId
	 * @return
	 */
	public UserJob loanUserJobByJobId(String jobId);

	/**
	 * 根据id加载联系人信息
	 * 
	 * @param id
	 * @return
	 */
	public UserContacter loanUserContacterById(String id);

	/**
	 * 根据id加载房产信息
	 * 
	 * @param id
	 * @return
	 */
	public UserHouse loadUserHouseById(String id);

	/**
	 * 根据id加载车辆信息
	 * 
	 * @param id
	 * @return
	 */
	public UserCar loadUserCarById(String id);
}

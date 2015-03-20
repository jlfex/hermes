package com.jlfex.hermes.service;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.UserProperties;

/**
 * 认证中心接口
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
public interface AuthService {

	/**
	 * 查找用户信息，获取用户认证情况
	 * 
	 * @param userId
	 * @return
	 */
	public UserProperties findByUserId(String userId);

	/**
	 * 发送手机验证码
	 * 
	 * @param phone
	 */
	public Result sendAuthCodeByPhone(String userId, String phone);

	/**
	 * 手机认证
	 * 
	 * @param phone
	 * @param validCode
	 */
	public Result authPhone(String userId, String phone, String validCode);

	/**
	 * 实名认证
	 * 
	 * @param userId
	 * @param realName
	 * @param idType
	 * @param idNumber
	 */
	public Result authIdentify(String userId, String realName, String idType, String idNumber);

	/**
	 * 是否需要认证
	 * 
	 * @param code
	 * @return
	 */
	public boolean isAuth(String code);

	/**
	 * 绑定银行卡
	 * 
	 * @param bankId
	 * @param cityId
	 * @param deposit
	 * @param account
	 */
	public Result bindBank(String userId, String bankId, String cityId, String deposit, String account, String isdefault);

	/**
	 * 更换银行卡
	 * 
	 * @param bankId
	 * @param cityId
	 * @param deposit
	 * @param account
	 */
	public Result editBankCard(String id, String bankId, String cityId, String deposit, String account, String isdefault);

}

package com.jlfex.hermes.service;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.UserProperties;

/**
 * 认证中心接口
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
	public Result<String> sendAuthCodeByPhone(String userId, String phone);

	/**
	 * 手机认证
	 * 
	 * @param phone
	 * @param validCode
	 */
	public Result<String> authPhone(String userId, String phone, String validCode);

	/**
	 * 实名认证
	 * 
	 * @param userId
	 * @param realName
	 * @param idType
	 * @param idNumber
	 */
	public Result<String> authIdentify(String userId, String realName, String idType, String idNumber);

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
	public Result<String> bindBank(String userId, String bankId, String cityId, String deposit, String account, String isdefault,String realName);
	/**
	 * 新增银行卡
	 * 
	 */
	public Result<String> bindBank(String userId, String bankId, String cityId, String deposit, String account,String realName);

	/**
	 * 更换银行卡
	 * 
	 * @param bankId
	 * @param cityId
	 * @param deposit
	 * @param account
	 */
	public Result<String> editBankCard(String id, String bankId, String cityId, String deposit, String account, String isdefault);

}

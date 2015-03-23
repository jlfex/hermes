package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.sms.SMSSender;
import com.jlfex.hermes.common.support.freemarker.StringTemplateLoader;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.utils.Strings.StringSet;
import com.jlfex.hermes.model.Area;
import com.jlfex.hermes.model.Bank;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAuth;
import com.jlfex.hermes.model.UserAuth.Status;
import com.jlfex.hermes.model.UserAuth.Type;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserProperties.Auth;
import com.jlfex.hermes.repository.AreaRepository;
import com.jlfex.hermes.repository.BankAccountRepository;
import com.jlfex.hermes.repository.BankRepository;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.repository.TextRepository;
import com.jlfex.hermes.repository.UserAuthRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.AuthService;

/**
 * 认证实现
 * 
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	/** 用户属性仓库 */
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;

	/** 属性仓库 */
	@Autowired
	private PropertiesRepository propertiesRepository;

	/** 用户认证仓库 */
	@Autowired
	private UserAuthRepository userAuthRepository;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private TextRepository textRepository;
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private BankAccountRepository bankAccountRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.AuthService#findByUserId(java.lang.String)
	 */
	@Override
	public UserProperties findByUserId(String userId) {
		User user = userRepository.findOne(userId);
		UserProperties userProperties = userPropertiesRepository.findByUser(user);
		return userProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.AuthService#sendAuthCodeByPhone(java.lang.String
	 * ,java.lang.String)
	 */
	@Override
	public Result sendAuthCodeByPhone(String userId, String phone) {
		try {
			Result result = new Result();
			UserAuth userAuth = new UserAuth();
			// check whether the phone is used
			List<User> users = userRepository.findByCellphone(phone);
			List<UserProperties> userPros = new ArrayList<UserProperties>();
			if (users != null && users.size() > 0) {
				userPros = userPropertiesRepository.findByAuthCellphoneAndUserIn(Auth.PASS, users);
			}
			if (userPros != null && userPros.size() > 0) {
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				result.addMessage(App.message("result.failure.phone.occupy", null));
			} else {
				User user = userRepository.findOne(userId);
				user.setCellphone(phone);

				Date curDate = new Date();
				userAuth.setUser(user);
				String validateCode = Strings.random(6, StringSet.NUMERIC);
				userAuth.setCode(validateCode);
				String smsPro = App.config("auth.sms.expire");
				Date expire = Calendars.add(curDate, smsPro);
				userAuth.setStatus(Status.WAITVERIFY);
				userAuth.setType(Type.SMS);
				userAuth.setExpire(expire);

				userRepository.save(user);
				userAuthRepository.save(userAuth);

				// send verification code of sms
				//sendSms(phone, validateCode);
				result.addMessage(App.message("result.success.phone", null));
				result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Result result = new Result();
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(App.message("失败", null));
			return result;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.AuthService#authPhone(java.lang.String,java.
	 * lang.String,java.lang.String)
	 */
	@Override
	public Result authPhone(String userId, String phone, String validCode) {
		Result result = new Result();
		User user = userRepository.findOne(userId);
		if (user.getCellphone().equals(phone)) {
			UserAuth userAuth = userAuthRepository.findByUserAndCodeOrderByCreateTimeDesc(user, validCode);
			if (userAuth != null) {
				Date expire = userAuth.getExpire();
				Date currentDate = new Date();
				if (currentDate.before(expire)) {
					UserProperties userPro = userPropertiesRepository.findByUser(user);
					userPro.setAuthCellphone(Auth.PASS);
					userPropertiesRepository.save(userPro);
					result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
					userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.VERIFY);
					user.setStatus(com.jlfex.hermes.model.User.Status.CERTIFIED);
				} else {
					userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.OVERDUE);
					result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
					result.addMessage(App.message("result.failure.phone.overdue", null));
				}
				userAuthRepository.save(userAuth);
				userRepository.save(user);
			} else {
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				result.addMessage(App.message("result.failure.phone.error", null));
			}
		} else {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(App.message("result.failure.phone.unmatch", null));
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.AuthService#authIdentify(java.lang.String,java
	 * .lang.String,java.lang.String)
	 */
	@Override
	public Result authIdentify(String userId, String realName, String idType, String idNumber) {
		Result result = new Result();
		User user = userRepository.findOne(userId);
		UserProperties userPro_c = userPropertiesRepository.findByIdNumberAndIdTypeAndAuthName(idNumber, idType, Auth.PASS);
		if (userPro_c != null) {
			if (!userPro_c.getUser().getId().equals(userId)) { // 证件被占用
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				result.addMessage(App.message("result.failure.id.occupy", null));
			}
		} else {
			String res = idInterface(realName, idType, idNumber);
			UserProperties userPro_u = userPropertiesRepository.findByUser(user);
			userPro_u.setRealName(realName);
			userPro_u.setIdType(idType);
			userPro_u.setIdNumber(idNumber);
			// success
			if (res == "3") {
				userPro_u.setAuthName(Auth.PASS);
				result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
			} else {
				userPro_u.setAuthName(Auth.FAILURE);
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				result.addMessage(App.message("result.failure.id", null));
			}
			userPropertiesRepository.save(userPro_u);
		}
		return result;
	}

	/**
	 * 绑定银行卡
	 */
	public Result bindBank(String userId, String bankId, String cityId, String deposit, String account, String isdefault) {
		Result result = new Result();
		User user = userRepository.findOne(userId);
		Area city = areaRepository.findOne(cityId);
		Bank bank = bankRepository.findOne(bankId);
		UserProperties userProperties = userPropertiesRepository.findByUserId(userId);
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUser(user);// 持卡人信息
		bankAccount.setBank(bank);// 银行名称
		bankAccount.setCity(city);// 开户所在地
		bankAccount.setDeposit(deposit);// 开户行
		bankAccount.setAccount(account);// 银行账号
		bankAccount.setName(userProperties.getRealName());
		List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(userId);

		if (bankAccounts.size() == 0) {
			bankAccount.setIsDefault(true);// 用户第一次绑定银行卡的时候设为默认
		} else {
			if (isdefault != null) {
				List<BankAccount> bankAccountList = bankAccountRepository.findByUserId(userId);
				for (BankAccount bankinfo : bankAccountList) {
					bankinfo.setIsDefault(false);
				}
				bankAccount.setIsDefault(true);
			} else {
				bankAccount.setIsDefault(false);
			}
		}
		bankAccountRepository.save(bankAccount);
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		return result;
	}

	/**
	 * 更改银行卡
	 */
	public Result editBankCard(String id, String bankId, String cityId, String deposit, String account, String isdefault) {
		Result result = new Result();
		Area city = areaRepository.findOne(cityId);
		Bank bank = bankRepository.findOne(bankId);
		BankAccount bankAccount = bankAccountRepository.findOne(id);
		bankAccount.setBank(bank);// 银行名称
		bankAccount.setCity(city);// 开户所在地
		bankAccount.setDeposit(deposit);// 开户行
		bankAccount.setAccount(account);// 银行账号
		List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(bankAccount.getUser().getId());

		if (bankAccounts.size() == 0) {
			bankAccount.setIsDefault(true);// 用户第一次绑定银行卡的时候设为默认
		} else {
			if (isdefault != null) {
				List<BankAccount> bankAccountList = bankAccountRepository.findByUserId(bankAccount.getUser().getId());
				for (BankAccount bankinfo : bankAccountList) {
					bankinfo.setIsDefault(false);
				}
				bankAccount.setIsDefault(true);
			} else {
				bankAccount.setIsDefault(false);
			}
		}
		bankAccountRepository.save(bankAccount);
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.AuthService#isAuth(java.lang.String)
	 */
	@Override
	public boolean isAuth(String code) {
		Properties properties = propertiesRepository.findByCode(code);
		if (properties != null) {
			// code:auth.cellphone.switch(The phone authentication)
			// auth.realname.switch(real name the authentication)
			// value：1.Required 0.Not Required
			if (properties.getValue().equals("1")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 发送短信接口
	 * 
	 * @param phone
	 * @param validateCode
	 */
	public void sendSms(String phone, String validateCode) {
		Text text = textRepository.findOne("afa431f4-9a65-11e3-85fa-6cae8b21aeaa");

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("validateCode", validateCode);
		String html = StringTemplateLoader.process(text.getText(), root);
		SMSSender.sendMessage(html, phone);
	}

	/**
	 * 证件验证接口
	 */
	private String idInterface(String realName, String idType, String idNumber) {
		String param = realName + "," + idNumber;
		// return Identity.verify(param);
		return "3";
	}

}

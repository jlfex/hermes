package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PostPersist;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.utils.Strings.StringSet;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.User.Status;
import com.jlfex.hermes.model.User.Type;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserAccount.Minus;
import com.jlfex.hermes.model.UserAuth;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.model.UserLog.LogType;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserProperties.Auth;
import com.jlfex.hermes.model.UserProperties.Mortgagor;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserAuthRepository;
import com.jlfex.hermes.repository.UserImageRepository;
import com.jlfex.hermes.repository.UserLogRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.security.PasswordEncoder;

/**
 * 处理用户注册、登录、登出功能
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Service
@Transactional
public class UserServiceImpl extends PasswordEncoder implements UserService {

	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;

	/** 用户账户仓库 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/** 用户属性仓库 */
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 用户认证仓库 */
	@Autowired
	private UserAuthRepository userAuthRepository;

	/** 用户图片信息仓库 */
	@Autowired
	private UserImageRepository userImageRepository;

	/** 用户日志仓库 */
	@Autowired
	private UserLogRepository userLogRepository;

	/** 系统属性仓库 */
	@Autowired
	private PropertiesRepository propertiesRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#loadById(java.lang.String)
	 */
	@Override
	public User loadById(String id) {
		return userRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#loadByEmail(java.lang.String)
	 */
	@Override
	public User loadByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#loadByAccount(java.lang.String)
	 */
	@Override
	public User loadByAccount(String account) {
		return userRepository.findByAccount(account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#findByInput(java.lang.String)
	 */
	@Override
	public List<User> findByInput(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#resetPassword(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public User resetPassword(String userId, String original, String reset) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#checkEmail(java.lang.String)
	 */
	@Override
	public boolean checkEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return false;
		} else if (user.getStatus().equals(Status.INACTIVATE)) {
			return false;
		} else {
			return true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#checkPhone(java.lang.String)
	 */
	@Override
	public boolean checkPhone(String phone) {
		Assert.notEmpty(phone, "cellphone is empty");
		String smsPro = App.config("auth.cellphone.switch");
		// value：1.需要认证 0.不需要认证
		if (smsPro.equals("1")) {
			List<User> users = userRepository.findByCellphone(phone);
			if (users.size() > 0) {
				List<UserProperties> userPros = userPropertiesRepository.findByAuthCellphoneAndUserIn(Auth.PASS, users);
				if (userPros.size() > 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}

		} else {
			// 手机不需要认证，则不检查手机是否被占用
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#signUp(User)
	 */
	@Override
	@PostPersist
	public void signUp(User user) throws Exception {
		Date createDate = new Date();
		user.setType(Type.CLIENT);
		user.setStatus(Status.INACTIVATE);
		String pwd = encode(user.getSignPassword());
		user.setSignPassword(pwd);// 密码加密
		userRepository.save(user);
		Logger.info("用户成功 ID=" + user.getId());
		saveUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#signSupplement(UserBasic,
	 * HttpServletRequest)
	 */
	// @Override
	// public String signSupplement(UserBasic userBasic, HttpServletRequest req)
	// {
	// Date curDate=new Date();
	// User user = userRepository.findOne(userBasic.getId());
	// Logger.info("完善注册信息~~~~~~~~" + user.getId());
	// UserProperties userPro =userPropertiesRepository.findByUser(user);
	// userPro.setRealName(userBasic.getRealName());
	// userPro.setUpdateTime(curDate);
	//
	// user.setAccount(userBasic.getAccount());
	// user.setCellphone(userBasic.getCellphone());
	// user.setUpdateTime(curDate);
	// userPropertiesRepository.save(userPro);
	// return sendActiveMail(user, req);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#sendActiveMail(User,HttpServletRequest
	 * )
	 */
	@Override
	public Map<String, Object> getActiveMailModel(User user, HttpServletRequest req) {
		String validateCode = generateCode(user);
		Map<String, Object> root = new HashMap<String, Object>();
		String ipPath = getPath(req);
		StringBuffer sb = new StringBuffer("\"http://" + ipPath + "/userIndex/activeMail?uuId=");
		sb.append(user.getId()).append("&validateCode=").append(validateCode).append("\"");
		root.put("active_url", sb.toString());
		root.put("userName", user.getAccount());
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#activeMail(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Result<?> activeMail(String userId, String validateCode) {
		Result<String> result = new Result<String>();
		User user = userRepository.findOne(userId);
		// 用户是否存在
		if (user != null) {
			// 用户是否激活
			if (user.getStatus().equals(Status.INACTIVATE)) {
				UserAuth userAuth = userAuthRepository.findByUserAndCodeOrderByCreateTimeDesc(user, validateCode);
				if (userAuth != null) {
					Date expire = userAuth.getExpire();
					Date currentDate = new Date();
					// 验证码是否过期
					if (currentDate.before(expire)) {
						user.setStatus(Status.ENABLED);
						user.setUpdateTime(currentDate);
						userRepository.save(user);
						UserProperties userPro = userPropertiesRepository.findByUser(user);
						userPro.setAuthEmail(Auth.PASS);
						userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.VERIFY);
						result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
						result.addMessage(App.message("message.email.active.success", user.getEmail()));
					} else {
						userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.OVERDUE);
						result.setData(user.getEmail());
						result.setType(com.jlfex.hermes.common.Result.Type.WARNING);

					}
					userAuth.setUpdateTime(currentDate);
					userAuthRepository.save(userAuth);
				}
			} else {
				result.addMessage(App.message("message.email.hasactive.success", user.getEmail()));
				result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
			}
		} else {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(App.message("message.email.active.fialure"));

		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#signIn(User)
	 */
	@Override
	public Result<?> signIn(User signUser) {
		Result<String> result = new Result<String>();
		User user = userRepository.findByEmail(signUser.getEmail());
		// 账户不存在
		if (user == null) {
			result.addMessage(App.message("result.failure.email"));
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
		} else {
			boolean matchRes = matches(signUser.getSignPassword(), user.getSignPassword());
			if (matchRes) {
				if (user.getStatus().equals(Status.INACTIVATE)) {
					result.setType(com.jlfex.hermes.common.Result.Type.WARNING);
					result.addMessage(App.message("result.warning.email"));
				} else {
					AppUser appUser = new AppUser();
					appUser.setId(user.getId());
					appUser.setAccount(user.getEmail());
					UserProperties userPro = userPropertiesRepository.findByUser(user);
					if (userPro != null) {
						appUser.setName(user.getAccount());
					} else {
						appUser.setName(App.message("anonymous"));
					}
					App.current().setUser(appUser);
					result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
					// 用户日志记录
					saveUserLog(user, LogType.LOGIN);
				}
			} else {
				// 账户和密码不匹配
				result.addMessage(App.message("result.failure.sign.in"));
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#isExistentEmail(java.lang.String)
	 */
	@Override
	public boolean isExistentEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return false;
		}
		return true;
	}

	/*
	 * 重置密码邮件 组装 数据模型 (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#sendResetPwdEmail(java.lang.String
	 * ,HttpServletRequest)
	 */
	@Override
	public Map<String, Object> getResetPwdEmailModel(String email, HttpServletRequest request) throws Exception {
		User user = userRepository.findByEmail(email);
		String validateCode = generateCode(user);
		Map<String, Object> root = new HashMap<String, Object>();
		String ipPath = getPath(request);
		StringBuffer sb = new StringBuffer("\"http://" + ipPath + "/userIndex/handleRetrive?uuId=");
		sb.append(user.getId()).append("&validateCode=").append(validateCode).append("\"");
		root.put("active_url", sb.toString());
		root.put("userName", user.getAccount());
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#handleRetrieveMail(java.lang.String
	 * ,java.lang.String)
	 */
	@Override
	public Result<?> handleRetrieveMail(String userId, String validCode) {
		User user = userRepository.findOne(userId);
		Result<String> result = new Result<String>();
		if (user != null) {
			UserAuth userAuth = userAuthRepository.findByUserAndCodeOrderByCreateTimeDesc(user, validCode);
			if (userAuth != null) {
				Date expire = userAuth.getExpire();
				Date currentDate = new Date();
				// 验证码是否过期
				if (currentDate.before(expire)) {
					result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
					userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.VERIFY);
				} else {
					userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.OVERDUE);
					result.setType(com.jlfex.hermes.common.Result.Type.WARNING);
				}
				userAuth.setUpdateTime(currentDate);
				userAuthRepository.save(userAuth);
			} else {
				result.setType(com.jlfex.hermes.common.Result.Type.WARNING);
			}
		} else {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(App.message("message.email.active.fialure "));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#saveUser(User)
	 */
	public void saveUser(User user) throws Exception {
		Date createDate = new Date();
		// 用户属性信息
		UserProperties userProperties = new UserProperties();
		userProperties.setUser(user);
		userProperties.setRealName(user.getRealName());
		userProperties.setAuthCellphone(Auth.WAIT);
		userProperties.setAuthEmail(Auth.WAIT);
		userProperties.setAuthName(Auth.WAIT);
		userProperties.setIsMortgagor(Mortgagor.ALL);
		userPropertiesRepository.save(userProperties);

		// 创建用户的现金账户
		UserAccount cashAccount = new UserAccount();
		cashAccount.setUser(user);
		cashAccount.setMinus(Minus.INMINUS);
		cashAccount.setStatus(com.jlfex.hermes.model.UserAccount.Status.VALID);
		cashAccount.setType(com.jlfex.hermes.model.UserAccount.Type.CASH);
		cashAccount.setBalance(BigDecimal.ZERO);

		// 创建用户的冻结账户
		UserAccount freezeAccount = new UserAccount();
		freezeAccount.setUser(user);
		freezeAccount.setMinus(Minus.INMINUS);
		freezeAccount.setStatus(com.jlfex.hermes.model.UserAccount.Status.VALID);
		freezeAccount.setType(com.jlfex.hermes.model.UserAccount.Type.FREEZE);
		freezeAccount.setBalance(BigDecimal.ZERO);

		List<UserAccount> accountList = new ArrayList<UserAccount>();
		accountList.add(cashAccount);
		accountList.add(freezeAccount);
		userAccountRepository.save(accountList);
		Logger.info("用户的 属性信息、 现金账户、冻结账户 保存成功");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#signOut()
	 */
	@Override
	public void signOut() {
		AppUser appUser = App.current().getUser();
		User user = userRepository.findOne(appUser.getId());
		saveUserLog(user, LogType.LOGOUT);
		App.current().setUser(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#getAvatar(com.jlfex.hermes.model
	 * .User, java.lang.String)
	 */
	@Override
	public String getAvatar(User user, String type) {
		UserImage userImage = userImageRepository.findByUserAndType(user, type);
		if (userImage == null)
			return null;
		return userImage.getImage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#retrievePwd(java.lang.String,java
	 * .lang.String)
	 */
	@Override
	public void retrievePwd(String userId, String newPwd) {
		Assert.notEmpty(userId, "user id is empty.");
		User user = userRepository.findOne(userId);
		String pwd = encode(newPwd);
		user.setSignPassword(pwd);
		user.setUpdateTime(new Date());
		userRepository.save(user);
		saveUserLog(user, LogType.RETRIEVE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.UserService#generateCode(User)
	 */
	private String generateCode(User user) {
		Date createDate = new Date();
		// 生成激活码
		String validateCode = Strings.random(10, StringSet.NUMERIC);
		UserAuth userAuth = new UserAuth();
		userAuth.setUser(user);
		userAuth.setCode(validateCode);
		// 邮件失效期限 2d
		String mailPro = App.config("auth.mail.expire");
		Date expiere = Calendars.add(createDate, mailPro);
		userAuth.setExpire(expiere);
		userAuth.setType(com.jlfex.hermes.model.UserAuth.Type.EMAIL);
		userAuth.setStatus(com.jlfex.hermes.model.UserAuth.Status.WAITVERIFY);
		userAuthRepository.save(userAuth);
		Logger.info("生成邮件激活授权码:" + userAuth.getCode());
		return validateCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#saveUserLog(User,java.lang.String)
	 */
	private void saveUserLog(User user, String type) {
		Date curDate = new Date();
		UserLog userLog = new UserLog();
		userLog.setDatetime(curDate);
		userLog.setType(type);
		userLog.setUser(user);
		userLogRepository.save(userLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#getUserByAccount(java.lang.String)
	 */
	@Override
	public User getUserByAccount(String account) {
		return userRepository.findByAccount(account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserService#loadPropertiesByUserId(java.lang
	 * .String)
	 */
	@Override
	public UserProperties loadPropertiesByUserId(String userId) {
		return userPropertiesRepository.findByUserId(userId);
	}

	private String getPath(HttpServletRequest req) {
		String ip = req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
		return ip;
	}
}

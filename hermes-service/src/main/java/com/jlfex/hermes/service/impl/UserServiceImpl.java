package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PostPersist;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.constant.HermesConstants;
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
import com.jlfex.hermes.service.UserLogService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.privilege.user.UserRoleVo;
import com.jlfex.hermes.service.security.PasswordEncoder;

/**
 * 处理用户注册、登录、登出功能
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

	@Autowired
	private UserLogService userLogService;

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

	/**
	 * 校验手机号码是否可用 true 可用 false 不可用
	 */
	@Override
	public boolean checkPhone(String phone) {
		List<User> users = userRepository.findByCellphone(phone);
		if (users != null && users.size() > 0) {
			// 校验手机 是否已经通过认证
			List<UserProperties> userPros = userPropertiesRepository.findByAuthCellphoneAndUserIn(Auth.PASS, users);
			if (userPros.size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
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
		root.put("platform_site", App.config("app.website"));
		root.put("customer_email", App.config("app.customer.service.email"));
		root.put("service_tel", App.config("site.service.tel"));
		root.put("platName", App.config("app.operation.name"));
		root.put("expireHour", calcuEmailExpireHour());
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
		try {
			if (user == null) {
				result.addMessage(App.message("result.failure.email"));
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			} else {
				boolean matchRes = matches(signUser.getSignPassword(), user.getSignPassword());
				if (matchRes) {
					if (Status.INACTIVATE.equals(user.getStatus())) {
						result.setType(com.jlfex.hermes.common.Result.Type.WARNING);
						result.addMessage(App.message("result.warning.email"));
					} else if (Status.FROZEN.equals(user.getStatus())) {
						result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
						result.addMessage(App.message("账号已被冻结"));
					} else if (Status.DISABLED.equals(user.getStatus())) {
						result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
						result.addMessage(App.message("账号已被注销"));
					} else if (Status.ENABLED.equals(user.getStatus())) {
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
						if (!userPro.getAuthCellphone().equals(Auth.PASS)) {
							result.setType(com.jlfex.hermes.common.Result.Type.CELLPHNOE_NOTAUTH);// 判断手机是否认证
						} else if (!userPro.getAuthName().equals(Auth.PASS)) {
							result.setType(com.jlfex.hermes.common.Result.Type.NAME_NOTAUTH);// 判断实名是否认证
						} else if (!StringUtils.isEmpty(userPro.getAuthBankcard()) && !userPro.getAuthBankcard().equals(Auth.PASS)) {
							result.setType(com.jlfex.hermes.common.Result.Type.BANKCARD_NOTAUTH);
						} else if (StringUtils.isEmpty(userPro.getAuthBankcard())) {
							result.setType(com.jlfex.hermes.common.Result.Type.BANKCARD_NOTAUTH);// 判断银行卡是否认证
						} else {
							result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
						}
					}
				} else {
					// 账户和密码不匹配
					result.addMessage(App.message("result.failure.sign.in"));
					result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				}
				// 用户日志记录
				userLogService.saveUserLog(user, LogType.LOGIN);
			}
		} catch (Exception e) {
			result.addMessage("修改密码错误");
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
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
		root.put("platform_site", App.config("app.website"));
		root.put("customer_email", App.config("app.customer.service.email"));
		root.put("service_tel", App.config("site.service.tel"));
		root.put("platName", App.config("app.operation.name"));
		root.put("expireHour", calcuEmailExpireHour());
		return root;
	}

	/**
	 * 计算邮件失效时间
	 * 
	 * @return
	 */
	public String calcuEmailExpireHour() {
		String expireHoure = App.config("auth.mail.expire");
		if (App.config("auth.mail.expire").contains(HermesConstants.NICK_DAY)) {
			try {
				expireHoure = "" + (Integer.parseInt(expireHoure.replace(HermesConstants.NICK_DAY, "").trim()) * 24);
			} catch (Exception e) {
				Logger.error("计算邮件激活时间异常：请检查配置 是否正常 auth.mail.expire=" + App.config("auth.mail.expire"), e);
			}
		}
		return expireHoure;
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
		// 用户属性信息
		UserProperties userProperties = new UserProperties();
		userProperties.setUser(user);
		userProperties.setRealName(user.getRealName());
		userProperties.setAuthCellphone(Auth.WAIT);
		userProperties.setAuthEmail(Auth.WAIT);
		userProperties.setAuthName(Auth.WAIT);
		userProperties.setAuthBankcard(Auth.WAIT);
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
		try {
			User user = userRepository.findOne(appUser.getId());
			userLogService.saveUserLog(user, LogType.LOGOUT);
			App.current().setUser(null);
		} catch (Exception e) {
			Logger.error("注销用户：" + appUser.getAccount() + "失败");
		}
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
		try {
			Assert.notEmpty(userId, "user id is empty.");
			User user = userRepository.findOne(userId);
			String pwd = encode(newPwd);
			user.setSignPassword(pwd);
			userRepository.save(user);
			userLogService.saveUserLog(user, LogType.RETRIEVE);
		} catch (Exception e) {
			Logger.error("找回密码失败");
		}
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
	
	@Override
	public List<User> getUserByCreator(String creator) {
		return userRepository.findByCreator(creator);
	}
	/**
	 * 权限：添加后台管理人员
	 * @param userRoleVo
	 * @return
	 */
	@Override
	public Map<String,String>  saveConsoleManager(UserRoleVo userRoleVo){
		Map<String,String>  resultMap = new  HashMap<String,String>();
		String code="99", msg="保存失败"; 
		if(userRoleVo==null ){
			msg = "保存失败，信息为空";
		}else if(Strings.empty(userRoleVo.getId())){
			//保存
			User user = new User();
			user.setAccount(userRoleVo.getUserName().trim());
			user.setType(Type.NORMAL_ADMIN);
			user.setStatus(Status.ENABLED);
			user.setSignPassword(encode(userRoleVo.getUserPwd()));// 密码加密
			user.setRemark(userRoleVo.getRemark());
			if(userRepository.save(user) !=null){
				code = "00";
			}
		}else if(Strings.notEmpty(userRoleVo.getId())){
			//编辑
			User user = loadById(userRoleVo.getId().trim());
			user.setRemark(userRoleVo.getRemark());
			if(Strings.notEmpty(userRoleVo.getOriginalPwd())){
				//修改密码
				String encryptOldPwd = encode(userRoleVo.getOriginalPwd());
				if(encryptOldPwd.equals(user.getSignPassword())){
					user.setSignPassword(encode(userRoleVo.getUserPwd()));// 密码加密
					userRepository.save(user);
					code = "00";
				}else{
					msg = "保存失败，原始密码有误!";
				}
			}else{
				//不修改密码
				userRepository.save(user);
				code = "00";
			}
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
	    return resultMap;
	}
	/**
	 * 后台用户删除
	 */
	@Override
	public void delUser(String userId) {
		 userRepository.delete(userId);
	}
	/**
	 *  后台用户更新
	 */
	@Override
	public User updateUser(User user) {
		 return userRepository.save(user);
	}
	/**
	 * 验证输入密码是否和原始密码相同
	 * @param id
	 * @param inputPwd
	 * @return
	 */
	@Override
	public boolean  checkCorrectOfUserPwd(String id,String inputPwd) {
		User user = userRepository.findOne(id);
		if(user !=null){
			String originalPwd = user.getSignPassword();
			if(Strings.notEmpty(inputPwd) && originalPwd.equals(encode(inputPwd.trim()))){
				return true;
			}
		}
		return false;
	}
}

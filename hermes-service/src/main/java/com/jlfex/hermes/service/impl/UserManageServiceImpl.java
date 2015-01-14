package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.BankAccount.Status;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserAddress;
import com.jlfex.hermes.model.UserAddress.Type;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserContacter;
import com.jlfex.hermes.model.UserEducation;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.repository.BankAccountRepository;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.CommonRepository.Script;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserAddressRepository;
import com.jlfex.hermes.repository.UserCarRepository;
import com.jlfex.hermes.repository.UserContacterRepository;
import com.jlfex.hermes.repository.UserEducationRepository;
import com.jlfex.hermes.repository.UserHouseRepository;
import com.jlfex.hermes.repository.UserImageRepository;
import com.jlfex.hermes.repository.UserJobRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.UserManageService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.pojo.UserBasic;
import com.jlfex.hermes.service.pojo.UserInfo;

@Service
@Transactional
public class UserManageServiceImpl implements UserManageService {
	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;

	/** 用户仓库 */
	@Autowired
	private UserRepository userRepository;

	/** 用户属性仓库 */
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 银行账户仓库 */
	@Autowired
	private BankAccountRepository bankAccountRepository;

	/** 房产信息仓库 */
	@Autowired
	private UserHouseRepository userHouseRepository;

	/** 车辆信息仓库 */
	@Autowired
	private UserCarRepository userCarRepository;

	/** 联系人信息仓库 */
	@Autowired
	private UserContacterRepository userContacterRepository;

	/** 工作信息仓库 */
	@Autowired
	private UserJobRepository userJobRepository;

	/** 用户地址 */
	@Autowired
	private UserAddressRepository userAddressRepository;

	/** 用户学历 */
	@Autowired
	private UserEducationRepository userEducationRepository;

	/** 账户信息 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/** 用户图片 */
	@Autowired
	private UserImageRepository userImageRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#findByCondition(java.lang.
	 * Integer, java.lang.Integer)
	 */
	@Override
	public Page<UserInfo> findByCondition(UserInfo userInfo, Integer page, Integer size) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		// 查询条件
		String condition = getCondition(userInfo, params);

		// 统计条数
		String countUser = commonRepository.readScriptFile(Script.countSearchUser);
		countUser = String.format(countUser, condition);
		List<?> count = commonRepository.findByNativeSql(countUser, params);
		Long total = Long.parseLong(String.valueOf(count.get(0)));

		// 查询用户
		String sqlSearchUser = commonRepository.readScriptFile(Script.searchUser);
		sqlSearchUser = String.format(sqlSearchUser, condition);
		List<?> users = commonRepository.findByNativeSql(sqlSearchUser, params, pageable.getOffset(), pageable.getPageSize());
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		for (int i = 0; i < users.size(); i++) {
			Object[] object = (Object[]) users.get(i);
			UserInfo userinfo = new UserInfo();
			List<UserAccount> accts = userAccountRepository.findByUserId(String.valueOf(object[0]));
			BigDecimal allBalance = BigDecimal.ZERO;// 总金额
			userinfo.setFree(Numbers.toCurrency(BigDecimal.ZERO));
			userinfo.setFreeze(Numbers.toCurrency(BigDecimal.ZERO));
			for (UserAccount acct : accts) {
				allBalance = allBalance.add(acct.getBalance());
				if (acct.getType().equals(com.jlfex.hermes.model.UserAccount.Type.CASH)) {
					userinfo.setFree(Numbers.toCurrency(acct.getBalance()));
				} else {
					userinfo.setFreeze(Numbers.toCurrency(acct.getBalance()));
				}
			}
			userinfo.setId(String.valueOf(object[0]));
			userinfo.setAccount(String.valueOf(object[1] == null ? App.message("anonymous", null) : object[1]));
			userinfo.setCellphone(String.valueOf(object[2] == null ? "" : object[2]));
			userinfo.setRealName(String.valueOf(object[3] == null ? "" : object[3]));
			userinfo.setTotal(Numbers.toCurrency(allBalance));
			userInfos.add(userinfo);
		}
		// 返回结果
		Page<UserInfo> pageUser = new PageImpl<UserInfo>(userInfos, pageable, total);
		return pageUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#findBankByUser(java.lang.String
	 * ,java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<BankAccount> findBankByUser(String userId, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		List<BankAccount> bankAccts = bankAccountRepository.findByUserIdAndStatus(userId, Status.ENABLED);
		Long total = Long.valueOf(bankAccts.size());
		Page<BankAccount> pageBank = new PageImpl<BankAccount>(bankAccts, pageable, total);
		return pageBank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#findHouseByUser(java.lang.
	 * String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<UserHouse> findHouseByUser(String userId) {
		// 初始化
		// Pageable pageable = Pageables.pageable(page, size);
		List<UserHouse> houses = userHouseRepository.findByUserId(userId);
		// Long total = Long.valueOf(houses.size());
		// Page<UserHouse> pageHouse = new PageImpl<UserHouse>(houses, pageable,
		// total);
		return houses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#findJobByUser(java.lang.String
	 * , java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<UserJob> findJobByUser(String userId, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		List<UserJob> jobs = userJobRepository.findByUserId(userId);
		Long total = Long.valueOf(jobs.size());
		Page<UserJob> pageJob = new PageImpl<UserJob>(jobs, pageable, total);
		return pageJob;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#findCarByUser(java.lang.String
	 * , java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<UserCar> findCarByUser(String userId, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		List<UserCar> cars = userCarRepository.findByUserId(userId);
		Long total = Long.valueOf(cars.size());
		Page<UserCar> pageCar = new PageImpl<UserCar>(cars, pageable, total);
		return pageCar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#findContacterByUser(java.lang
	 * .String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<UserContacter> findContacterByUser(String userId, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		List<UserContacter> contacters = userContacterRepository.findByUserId(userId);
		Long total = Long.valueOf(contacters.size());
		Page<UserContacter> pageContacter = new PageImpl<UserContacter>(contacters, pageable, total);
		return pageContacter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#loadBasicByUser(java.lang.
	 * String)
	 */
	@Override
	public UserBasic loadBasicByUser(String userId) {
		User user = userRepository.findOne(userId);
		UserProperties userProperties = userPropertiesRepository.findByUserId(userId);
		UserBasic userBasic = new UserBasic();
		BeanUtils.copyProperties(userProperties, userBasic);
		userBasic.setEmail(user.getEmail());
		userBasic.setStatus(user.getStatus());
		if (user.getAccount() != null) {
			userBasic.setAccount(user.getAccount());
		} else {
			userBasic.setAccount(App.message("anonymous", null));
		}
		userBasic.setCellphone(user.getCellphone());
		UserAddress userAdd = userAddressRepository.findByUserIdAndType(userId, Type.COMMON);
		if (userAdd != null) {
			userBasic.setProvince(userAdd.getProvince());
			userBasic.setCity(userAdd.getCity());
			userBasic.setCounty(userAdd.getCounty());
			userBasic.setAddress(userAdd.getAddress());
		}
		UserEducation userEdu = userEducationRepository.findByUserIdAndType(userId, com.jlfex.hermes.model.UserEducation.Type.HIGHEST);
		if (userEdu != null) {
			userBasic.setSchool(userEdu.getSchool());
			userBasic.setYear(userEdu.getYear());
			userBasic.setDegree(userEdu.getDegree());
		}
		return userBasic;
	}

	private String getCondition(UserInfo userInfo, Map<String, Object> params) {
		StringBuilder condition = new StringBuilder();
		if (userInfo.getType().equals("loan")) {
			condition.append("and hp.is_mortgagor='01'");
		}
		if (!Strings.empty(userInfo.getAccount())) {
			condition.append("and hu.account = :account");
			params.put("account", userInfo.getAccount());
		}
		if (!Strings.empty(userInfo.getCellphone())) {
			condition.append("and hu.cellphone = :cellphone");
			params.put("cellphone", userInfo.getCellphone());
		}
		if (!Strings.empty(userInfo.getRealName())) {
			condition.append("and hp.real_name = :realName");
			params.put("realName", userInfo.getRealName());
		}
		condition.append(" order by hu.id");
		return condition.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.UserManageService#loadImageByUserAndLabelAndType
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserImage> loadImageByUserAndLabelAndType(String userId, String label, String type) {
		return userImageRepository.findByUserIdAndTypeAndLabelIdAndStatus(userId, type, label, com.jlfex.hermes.model.UserImage.Status.ENABLED);
	}

	/**
	 * 账号冻结
	 */
	@Override
	public Result freezeUser(String userId) {
		Result<String> result = new Result<String>();
		if (Strings.empty(userId)) {
			result.setType(Result.Type.FAILURE);
			Logger.error("账户冻结:userId为空");
			return result;
		}
		User user = userRepository.findOne(userId);
		user.setStatus(com.jlfex.hermes.model.User.Status.FROZEN);
		userRepository.save(user);
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		return result;
	}

	/**
	 * 账号 解冻
	 */
	@Override
	public Result unfreezeUser(String userId) {
		Result<String> result = new Result<String>();
		if (Strings.empty(userId)) {
			result.setType(Result.Type.FAILURE);
			Logger.error("账户解冻:userId为空");
			return result;
		}
		User user = userRepository.findOne(userId);
		user.setStatus(com.jlfex.hermes.model.User.Status.ENABLED);
		userRepository.save(user);
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		return result;
	}

	/**
	 * 注销 账号
	 */
	@Override
	public Result logOffUser(String userId) {
		Result result = new Result();
		if (Strings.empty(userId)) {
			Logger.warn("注销用户: 用户id为空!");
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage("注销用户: 用户id为空!");
			return result;
		}
		User user = userRepository.findOne(userId);
		List<UserAccount> accounts = userAccountRepository.findByUser(user);
		for (UserAccount acc : accounts) {
			if (acc.getBalance().compareTo(BigDecimal.ZERO) == 1) {
				result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
				result.addMessage("账户余额大于0,不能注销");
				return result;
			}
		}
		user.setStatus(com.jlfex.hermes.model.User.Status.DISABLED);
		userRepository.save(user);
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		result.addMessage("成功注销");
		return result;
	}

	@Override
	public User findById(String userId) {
		return userRepository.findOne(userId);
	}

	/**
	 * 
	 * 通过用户编号和用户类型
	 * 
	 */
	@Override
	public UserAccount findByUserIdAndType(String userId, String type) {
		return userAccountRepository.findByUserIdAndType(userId, type);
	}
}

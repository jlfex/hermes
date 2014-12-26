package com.jlfex.hermes.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.model.Label;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserAddress;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserContacter;
import com.jlfex.hermes.model.UserEducation;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserAddress.Status;
import com.jlfex.hermes.model.UserAddress.Type;
import com.jlfex.hermes.repository.LabelRepository;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.repository.UserAccountRepository;
import com.jlfex.hermes.repository.UserAddressRepository;
import com.jlfex.hermes.repository.UserCarRepository;
import com.jlfex.hermes.repository.UserContacterRepository;
import com.jlfex.hermes.repository.UserEducationRepository;
import com.jlfex.hermes.repository.UserHouseRepository;
import com.jlfex.hermes.repository.UserImageRepository;
import com.jlfex.hermes.repository.UserJobRepository;
import com.jlfex.hermes.repository.UserLogRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.pojo.UserBasic;
import com.jlfex.hermes.service.security.PasswordEncoder;

/**
 * 用户个人信息实现
 * 
 * @author Aether
 * @version 1.0, 2013-12-25
 * @since 1.0
 */
@Service
@Transactional
public class UserInfoServiceImpl extends PasswordEncoder implements UserInfoService {

	/** 用户账户仓库 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/** 用户信息仓库 */
	@Autowired
	private UserRepository userRepository;

	/** 用户属性仓库 */
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;

	/** 属性仓库 */
	@Autowired
	private PropertiesRepository propertiesRepository;

	/** 用户日志仓库 */
	@Autowired
	private UserLogRepository userLogRepository;

	/** 用户工作信息仓库 */
	@Autowired
	private UserJobRepository userJobRepository;

	/** 用户房产信息仓库 */
	@Autowired
	private UserHouseRepository userHouseRepository;

	/** 用户车辆信息仓库 */
	@Autowired
	private UserCarRepository userCarRepository;

	/** 用户联系人信息仓库 */
	@Autowired
	private UserContacterRepository userContacterRepository;

	/** 用户图片 */
	@Autowired
	private UserImageRepository userImageRepository;

	/** 用户地址 */
	@Autowired
	private UserAddressRepository userAddressRepository;

	/** 用户学历 */
	@Autowired
	private UserEducationRepository userEducationRepository;

	/** 标签 */
	@Autowired
	private LabelRepository labelRepository;
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#findByUserId(java.lang.String)
	 */
	@Override
	public User findByUserId(String userId) {
		return userRepository.findOne(userId);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#findAccountByUserId(java.lang.String)
	 */
	@Override
	public List<UserAccount> findAccountByUserId(String userId) {
		Assert.notEmpty(userId, "user id is empty.");
		return userAccountRepository.findByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#findImageByUserId(java.lang.String)
	 */
	@Override
	public UserImage findImageByUserIdAndType(String userId, String type) {
		return userImageRepository.findByUserIdAndTypeAndStatus(userId, type, UserImage.Status.ENABLED);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#findUserInfoByUserId(java.lang.String)
	 */
	@Override
	public UserBasic findUserInfoByUserId(String userId) {
		User user = userRepository.findOne(userId);
		UserProperties userProperties = userPropertiesRepository.findByUserId(userId);
		UserBasic userBasic=new UserBasic();
		BeanUtils.copyProperties(userProperties, userBasic);
		if (user.getAccount() != null) {
			userBasic.setAccount(user.getAccount());// TODO
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

	@Override
	public List<UserJob> findJobByUserId(User user) {
		return userJobRepository.findByUser(user);
	}

	@Override
	public List<UserHouse> findHouseByUserId(User user) {
		return userHouseRepository.findByUser(user);
	}

	@Override
	public List<UserCar> findCarByUserId(User user) {
		return userCarRepository.findByUser(user);
	}

	@Override
	public List<UserContacter> findContacterByUserId(User user) {
		return userContacterRepository.findByUser(user);
	}

	@Override
	public void saveUserBasicInfo(UserBasic userBasic, User user) {
		Date curDate = new Date();
		UserProperties userPro = userPropertiesRepository.findByUser(user);
		userBasic.setId(userPro.getId());
		BeanUtils.copyProperties(userBasic, userPro);
		userPro.setUpdateTime(curDate);

		UserAddress userAdd = userAddressRepository.findByUserIdAndType(user.getId(), Type.COMMON);
		if (userAdd == null) {
			userAdd = new UserAddress();
			userAdd.setUser(user);
			userAdd.setType(Type.COMMON);
		}
		userAdd.setProvince(userBasic.getProvince());
		userAdd.setCity(userBasic.getCity());
		userAdd.setCounty(userBasic.getCounty());
		userAdd.setAddress(userBasic.getAddress());
		userAdd.setStatus(Status.VALID);
		userAdd.setZip("");
		userAdd.setCreateTime(curDate);
		userAdd.setUpdateTime(curDate);

		UserEducation userEdu = userEducationRepository.findByUserIdAndType(user.getId(), com.jlfex.hermes.model.UserEducation.Type.HIGHEST);
		if (userEdu == null) {
			userEdu = new UserEducation();
			userEdu.setUser(user);
			userEdu.setType(com.jlfex.hermes.model.UserEducation.Type.HIGHEST);
		}
		userEdu.setSchool(userBasic.getSchool());
		userEdu.setYear(userBasic.getYear());
		userEdu.setDegree(userBasic.getDegree());
		userEdu.setStatus("00");
		userEdu.setCreateTime(curDate);
		userEdu.setUpdateTime(curDate);

		userPropertiesRepository.save(userPro);
		userEducationRepository.save(userEdu);
		userAddressRepository.save(userAdd);

	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#saveJobInfo(UserJob)
	 */
	@Override
	public void saveJobInfo(UserJob userJob) {
		Date curDate = new Date();
			userJob.setStatus(com.jlfex.hermes.model.UserJob.Status.VALID);
			userJob.setCreateTime(curDate);
		userJob.setUpdateTime(curDate);
		userJobRepository.save(userJob);

	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#saveHouseInfo(UserHouse)
	 */
	@Override
	public void saveHouseInfo(UserHouse userHouse) {
		Date curDate = new Date();
			userHouse.setStatus(com.jlfex.hermes.model.UserHouse.Status.VALID);
			userHouse.setCreateTime(curDate);
		userHouse.setUpdateTime(curDate);
		userHouseRepository.save(userHouse);

	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#saveCarInfo(jUserCar)
	 */
	@Override
	public void saveCarInfo(UserCar userCar) {
		Date curDate = new Date();
		userCar.setStatus(com.jlfex.hermes.model.UserCar.Status.VALID);
		userCar.setCreateTime(curDate);
		userCar.setUpdateTime(curDate);
		userCarRepository.save(userCar);

	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#resetPassword(java.lang.String,java.lang.String,java.lang.String)
	 */
	@Override
	public void saveContacterInfo(UserContacter userContacter) {
		userContacter.setStatus(com.jlfex.hermes.model.UserContacter.Status.VALID);
		Date curDate = new Date();
		userContacter.setCreateTime(curDate);
		userContacter.setUpdateTime(curDate);
		userContacterRepository.save(userContacter);

	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#resetPassword(java.lang.String,java.lang.String,java.lang.String)
	 */
	@Override
	public Result resetPassword(String userId, String orginalPwd, String newPwd) {
		Result result = new Result();
		Assert.notEmpty(userId, "user id is empty.");
		User user = userRepository.findOne(userId);
		boolean match = matches(orginalPwd, user.getSignPassword());
		if (match) {
			String pwd = encode(newPwd);
			user.setSignPassword(pwd);
			user.setUpdateTime(new Date());
			userRepository.save(user);
			result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
			result.setData("修改成功");
		} else {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(App.message("result.failure.password", null));
			result.setData("原始密码不正确,修改失败");
		}
		return result;
	}

	@Override
	public UserProperties getProByUser(User user) {
		UserProperties userPro = userPropertiesRepository.findByUser(user);
		return userPro;
	}

	@Override
	public void saveImage(User user, String imgStr, String type, String labelStr) {
		UserImage userImage = new UserImage();
		Date curDate = new Date();
		if (type.equals(UserImage.Type.AVATAR) || type.equals(UserImage.Type.AVATAR_LG)) {
			List<UserImage> userImages = userImageRepository.findByUserAndTypeAndStatus(user, type, UserImage.Status.ENABLED);
			// 用户的大小头像信息只能各存在一条
			if (userImages.size() > 0) {
				userImage = userImages.get(0);
				userImage.setImage(imgStr);
				userImage.setUpdateTime(curDate);
			} else {
				userImage.setUser(user);
				userImage.setType(type);
				userImage.setImage(imgStr);
				userImage.setStatus(UserImage.Status.ENABLED);
				userImage.setCreateTime(curDate);
				userImage.setUpdateTime(curDate);
			}
		} else {
			Label label = labelRepository.findOne(labelStr);
			userImage.setUser(user);
			userImage.setType(type);
			userImage.setLabel(label);
			userImage.setImage(imgStr);
			userImage.setStatus(UserImage.Status.ENABLED);
			userImage.setCreateTime(curDate);
			userImage.setUpdateTime(curDate);
		}

		userImageRepository.save(userImage);
	}

	@Override
	public String[] loadLabel() {
		String labels = App.config("user.auth.labels");
		String[] labelArr = labels.split(",");
		return labelArr;
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loadByUserIdAndType(java.lang.String, java.lang.String)
	 */
	@Override
	public UserAccount loadByUserIdAndType(String userId, String type) {
		return userAccountRepository.findByUserIdAndType(userId, type);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loadPropertiesByUserId(java.lang.String)
	 */
	@Override
	public UserProperties loadPropertiesByUserId(String userId) {
		return userPropertiesRepository.findByUserId(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loadAccountByUserAndType(com.jlfex.hermes.model.User, java.lang.String)
	 */
	@Override
	public UserAccount loadAccountByUserAndType(User user, String type) {
		return userAccountRepository.findByUserAndType(user, type);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loadImagesByUserAndType(com.jlfex.hermes.model.User, java.lang.String)
	 */
	@Override
	public List<UserImage> loadImagesByUserAndType(User user, String type) {
		return userImageRepository.findByUserAndTypeAndStatus(user, type, com.jlfex.hermes.model.UserImage.Status.ENABLED);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loanUserJobByJobId(java.lang.String)
	 */
	@Override
	public UserJob loanUserJobByJobId(String jobId) {
		return userJobRepository.findOne(jobId);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loanUserContacterById(java.lang.String)
	 */
	@Override
	public UserContacter loanUserContacterById(String id) {
		return userContacterRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loadUserCarById(java.lang.String)
	 */
	@Override
	public UserHouse loadUserHouseById(String id) {
		return userHouseRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.UserInfoService#loadUserCarById(java.lang.String)
	 */
	@Override
	public UserCar loadUserCarById(String id) {
		return userCarRepository.findOne(id);
	}
}

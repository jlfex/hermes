package com.jlfex.hermes.main;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Files;
import com.jlfex.hermes.common.utils.Images;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Label;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserCar;
import com.jlfex.hermes.model.UserContacter;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserJob;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.UserAccount.Type;
import com.jlfex.hermes.model.UserEducation.Education;
import com.jlfex.hermes.model.UserHouse.Mortgage;
import com.jlfex.hermes.model.UserJob.Enterprise;
import com.jlfex.hermes.model.UserJob.Scale;
import com.jlfex.hermes.model.UserProperties.Gender;
import com.jlfex.hermes.model.UserProperties.IdType;
import com.jlfex.hermes.model.UserProperties.Married;
import com.jlfex.hermes.service.AreaService;
import com.jlfex.hermes.service.LabelService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.pojo.UserBasic;

/**
 * 账户中心
 */
@Controller
@RequestMapping("/account")
public class AccountPersonalController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private LabelService labelService;
	/**
	 * 个人信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	public String getUserInfo(Model model) {
		AppUser curUser = App.current().getUser();
		App.checkUser();
		User user = userInfoService.findByUserId(curUser.getId());

		List<UserAccount> userAccounts = userInfoService.findAccountByUserId(curUser.getId());
		BigDecimal allBalance = BigDecimal.ZERO;// 总金额
		BigDecimal cashBalance = BigDecimal.ZERO;// 现金金额
		BigDecimal freezeBalance = BigDecimal.ZERO;// 现金金额
		for (UserAccount userAcc : userAccounts) {
			if (userAcc.getType().equals(Type.CASH)) {
				cashBalance = userAcc.getBalance();// 现金金额
			} else {
				freezeBalance = userAcc.getBalance();// 冻结金额

			}
			allBalance = allBalance.add(userAcc.getBalance());
		}
		model.addAttribute("cashBalance", cashBalance);// 现金金额
		model.addAttribute("freezeBalance", freezeBalance);// 冻结金额
		model.addAttribute("allBalance", allBalance);
		model.addAttribute("email", curUser.getAccount());
		// model.addAttribute("email", user.getEmail());
		// 用户头像信息
		UserImage avatar = new UserImage();
		avatar = userInfoService.findImageByUserIdAndType(curUser.getId(), com.jlfex.hermes.model.UserImage.Type.AVATAR_LG);
		model.addAttribute("avatar", avatar);

		// 用户属性信息
		UserProperties userPro = userInfoService.getProByUser(user);
		model.addAttribute("userPro", userPro);

		return "account/personalnew";
	}

	@RequestMapping("getUserBasic")
	public String getUserBasic(Model model) {
		App.checkUser();
		String area = JSON.toJSONString(areaService.getAllChildren(null));
		model.addAttribute("area",area);
		AppUser curUser = App.current().getUser();
		// 用户属性信息
		UserBasic userBasic = userInfoService.findUserInfoByUserId(curUser.getId());
		if (userBasic.getProvince() != null && userBasic.getCity() != null && userBasic.getCounty() != null) {
			String addressPerson = areaService.getAddress(userBasic.getAddress(), userBasic.getProvince(), userBasic.getCity(), userBasic.getCounty());
			model.addAttribute("addressPerson", addressPerson);
		}
		model.addAttribute("userBasic", userBasic);
		// 证件类型
		Map<Object, String> idTypeMap = Dicts.elements(IdType.class);
		model.addAttribute("idTypeMap", idTypeMap);
		// 性别
		Map<Object, String> genterMap = Dicts.elements(Gender.class);
		model.addAttribute("genterMap", genterMap);
		// 婚姻状况
		Map<Object, String> marriedMap = Dicts.elements(Married.class);
		model.addAttribute("marriedMap", marriedMap);
		// 学历
		Map<Object, String> eduMap = Dicts.elements(Education.class);
		model.addAttribute("eduMap", eduMap);
		return "account/basic";
	}

	@RequestMapping("loadEditBasic")
	public String loadEditBasic(Model model) {

		return "account/basic-eidt";
	}
	/**
	 * 保存基本信息
	 * 
	 * @param userBasic
	 * @return
	 */
	@RequestMapping("saveBasic")
	@ResponseBody
	public Result<String> saveBasic(UserBasic userBasic) {
		App.checkUser();
		Result<String> result = new Result<String>();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		userInfoService.saveUserBasicInfo(userBasic, user);
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		return result;
	}

	/**
	 * 获取工作信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserJob")
	public String getUserJob(Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		List<UserJob> jobs = userInfoService.findJobByUserId(user);
		model.addAttribute("jobs", jobs);

		Map<Object, String> enterpriseMap = Dicts.elements(Enterprise.class);
		model.addAttribute("enterpriseMap", enterpriseMap);

		Map<Object, String> scaleMap = Dicts.elements(Scale.class);
		model.addAttribute("scaleMap", scaleMap);

		Map<Object, String> typeMap = Dicts.elements(com.jlfex.hermes.model.UserJob.Type.class);
		model.addAttribute("typeMap", typeMap);
		return "account/job";
	}

	/**
	 * 加载新建、编辑工作信息界面
	 * 
	 * @param jobid
	 * @param model
	 * @return
	 */
	@RequestMapping("loadJobDetail/{jobid}")
	public String loadJobDetail(@PathVariable("jobid") String jobid, Model model) {
		App.checkUser();
		if (jobid != null && jobid != "") {
			UserJob job = userInfoService.loanUserJobByJobId(jobid);
			model.addAttribute("job", job);
		} else {
			UserJob job = new UserJob();
			model.addAttribute("job", job);
		}
		Map<Object, String> enterpriseMap = Dicts.elements(Enterprise.class);
		model.addAttribute("enterpriseMap", enterpriseMap);
		Map<Object, String> scaleMap = Dicts.elements(Scale.class);
		model.addAttribute("scaleMap", scaleMap);
		Map<Object, String> typeMap = Dicts.elements(com.jlfex.hermes.model.UserJob.Type.class);
		model.addAttribute("typeMap", typeMap);
		return "account/job_detail";
	}

	/**
	 * 保存工作信息
	 * 
	 * @param userJob
	 * @param model
	 * @return
	 */
	@RequestMapping("saveJob")
	public String saveJob(UserJob userJob, Model model) {
		App.checkUser();
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		userJob.setUser(user);
		if(userJob == null || Strings.empty(userJob.getId())){
			userInfoService.saveJobInfo(userJob);
		}else{
			UserJob entity = userInfoService.loanUserJobByJobId(userJob.getId());
			entity.setType(userJob.getType());
			entity.setName(userJob.getName());
			entity.setProperties(userJob.getProperties());
			entity.setScale(userJob.getScale());
			entity.setAddress(userJob.getAddress());
			entity.setPhone(userJob.getPhone());
			entity.setPosition(userJob.getPosition());
			entity.setAnnualSalary(userJob.getAnnualSalary());
			entity.setLicense(userJob.getLicense());
			entity.setRegisteredCapital(userJob.getRegisteredCapital());
			userInfoService.saveJobInfo(entity);
		}
		List<UserJob> jobs = userInfoService.findJobByUserId(user);
		model.addAttribute("jobs", jobs);
		Map<Object, String> enterpriseMap = Dicts.elements(Enterprise.class);
		model.addAttribute("enterpriseMap", enterpriseMap);
		Map<Object, String> scaleMap = Dicts.elements(Scale.class);
		model.addAttribute("scaleMap", scaleMap);
		Map<Object, String> typeMap = Dicts.elements(com.jlfex.hermes.model.UserJob.Type.class);
		model.addAttribute("typeMap", typeMap);
		return "account/job";
	}

	/**
	 * 获取房产信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserHouse")
	public String getUserHouse(Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		List<UserHouse> houses = userInfoService.findHouseByUserId(user);

		for (UserHouse house : houses) {
			int i = 0;
			String[] areaStr = new String[3];
			if (house.getProvince() != null) {
				areaStr[i] = house.getProvince();
				i++;
			}
			if (house.getProvince() != null) {
				areaStr[i] = house.getCity();
				i++;
			}
			if (house.getProvince() != null) {
				areaStr[i] = house.getCounty();
			}
			String areas = areaService.getAddress(house.getAddress(), areaStr);
			house.setAddressDetail(areas);
		}
		model.addAttribute("houses", houses);
		Map<Object, String> mortgageMap = Dicts.elements(Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);

		String area = JSON.toJSONString(areaService.getAllChildren(null));
		model.addAttribute("area", area);

		return "account/house";
	}

	@RequestMapping("loadHouseDetail/{houseId}")
	public String loadHouseDetail(@PathVariable("houseId") String houseId, Model model) {
		String area = JSON.toJSONString(areaService.getAllChildren(null));
		model.addAttribute("area", area);
		if (houseId != null && houseId != "" && !houseId.equals("null")) {
			UserHouse house = userInfoService.loadUserHouseById(houseId);
			model.addAttribute("house", house);
		} else {
			UserHouse house = new UserHouse();
			model.addAttribute("house", house);
		}
		Map<Object, String> mortgageMap = Dicts.elements(Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);
		return "account/house_detail";

	}

	@RequestMapping("saveHouse")
	public String saveHouse(UserHouse userHouse, Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		userHouse.setUser(user);
		userInfoService.saveHouseInfo(userHouse);
		List<UserHouse> houses = userInfoService.findHouseByUserId(user);
		for (UserHouse house : houses) {
			int i = 0;
			String[] areaStr = new String[3];
			if (house.getProvince() != null) {
				areaStr[i] = house.getProvince();
				i++;
			}
			if (house.getProvince() != null) {
				areaStr[i] = house.getCity();
				i++;
			}
			if (house.getProvince() != null) {
				areaStr[i] = house.getCounty();
			}
			String areas = areaService.getAddress(house.getAddress(), areaStr);
			house.setAddressDetail(areas);
		}
		model.addAttribute("houses", houses);
		Map<Object, String> mortgageMap = Dicts.elements(Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);

		return "account/house";
	}

	/**
	 * 获取车辆信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserCar")
	public String getUserCar(Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		List<UserCar> cars = userInfoService.findCarByUserId(user);
		model.addAttribute("cars", cars);
		Map<Object, String> mortgageMap = Dicts.elements(Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);
		return "account/car";
	}

	@RequestMapping("loanCarDetail/{carId}")
	public String loanCarDetail(@PathVariable("carId") String carId, Model model) {
		if (carId != null && carId != "") {
			UserCar car = userInfoService.loadUserCarById(carId);
			model.addAttribute("car", car);
		} else {
			UserCar car = new UserCar();
			model.addAttribute("car", car);
		}
		Map<Object, String> mortgageMap = Dicts.elements(Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);
		return "account/car_detail";
	}

	@RequestMapping("saveCar")
	public String saveCar(UserCar userCar, Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		userCar.setUser(user);
		userInfoService.saveCarInfo(userCar);
		List<UserCar> cars = userInfoService.findCarByUserId(user);
		model.addAttribute("cars", cars);
		Map<Object, String> mortgageMap = Dicts.elements(Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);
		return "account/car";
	}
	/**
	 * 获取联系人信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("getUserContacter")
	public String getUserContacter(Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		List<UserContacter> contacters = userInfoService.findContacterByUserId(user);
		model.addAttribute("contacters", contacters);
		return "account/contacter";
	}

	@RequestMapping("loadContacterDetail/{contacterId}")
	public String loadContacterDetail(@PathVariable("contacterId") String contacterId, Model model) {
		if (contacterId != null && contacterId != "") {
			UserContacter contacter = userInfoService.loanUserContacterById(contacterId);
			model.addAttribute("contacter", contacter);
		} else {
			UserContacter contacter = new UserContacter();
			model.addAttribute("contacter", contacter);
		}

		Map<Object, String> enterpriseMap = Dicts.elements(Enterprise.class);
		model.addAttribute("enterpriseMap", enterpriseMap);
		return "account/contacter_detail";

	}

	@RequestMapping("saveContacter")
	public String saveContacter(UserContacter userContacter, Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		userContacter.setUser(user);
		userInfoService.saveContacterInfo(userContacter);
		List<UserContacter> contacters = userInfoService.findContacterByUserId(user);
		model.addAttribute("contacters", contacters);
		return "account/contacter";
	}
	@RequestMapping("getUserPicture")
	public String getUserPicture(Model model) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		String[] labelNames = userInfoService.loadLabel();
		List<Label> labels = labelService.findByNames(labelNames);
		model.addAttribute("labels", labels);

		List<UserImage> images = userInfoService.loadImagesByUserAndType(user, com.jlfex.hermes.model.UserImage.Type.AUTH);
		model.addAttribute("images", images);
		return "account/picture";
	}
	/**
	 * 修改密码界面
	 * 
	 * @return
	 */
	@RequestMapping("showModify")
	public String showModify() {
		return "account/modifyPwd";
	}

	/**
	 * 修改密码
	 * 
	 * @param orginalPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping("resetPassword")
	@ResponseBody
	public Result<String> resetPassword(String orginalPwd, String confirm) {
		AppUser curUser = App.current().getUser();
		return userInfoService.resetPassword(curUser.getId(), orginalPwd, confirm);
	}

	/**
	 * 上传图片信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadImage")
	@ResponseBody()
	public void uploadImage(MultipartHttpServletRequest request) {
		try {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		String label = request.getParameter("label");
		MultipartFile file = request.getFile("file");
			String imgStr = Images.toBase64(Files.getMimeType(file.getOriginalFilename()), file.getBytes());
			userInfoService.saveImage(user, imgStr, com.jlfex.hermes.model.UserImage.Type.AUTH, label);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@RequestMapping("changeAvatar")
	public String changeAvatar() {
		return "account/avatar";
	}

	/**
	 * 修改头像
	 * 
	 * @param request
	 */
	@RequestMapping("saveAvatar")
	@ResponseBody()
	public void saveAvatar(MultipartHttpServletRequest request) {
		AppUser curUser = App.current().getUser();
		User user = userInfoService.findByUserId(curUser.getId());
		String startX = request.getParameter("startX");
		String startY = request.getParameter("startY");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String imgWidth = request.getParameter("imgWidth");
		String imgHeight = request.getParameter("imgHeight");
		// 获得文件：
		MultipartFile file = request.getFile("file");
		// 图片裁剪并转为Base64
		Map<String, String> map = Images.crop(file, 
				Integer.valueOf(Strings.empty(startX, "0")), 
				Integer.valueOf(Strings.empty(startY, "0")), 
				Integer.valueOf(Strings.empty(width, "0")), 
				Integer.valueOf(Strings.empty(height,"0")), 
				Integer.valueOf(Strings.empty(imgWidth,"0")), 
				Integer.valueOf(Strings.empty(imgHeight,"0")));
		String avatar = map.get("avatar");
		String avatar_lg = map.get("avatar_lg");
		userInfoService.saveImage(user, avatar, com.jlfex.hermes.model.UserImage.Type.AVATAR, "");
		userInfoService.saveImage(user, avatar_lg, com.jlfex.hermes.model.UserImage.Type.AVATAR_LG, "");
	}
	
	/**
	 * 删除  工作信息
	 * @param jobid
	 * @param model
	 * @return
	 */
	@RequestMapping("/delJobDetail/{jobid}")
	@ResponseBody
	public String delJobDetail(@PathVariable("jobid") String jobid, Model model) {
		String flag = "99";
		Logger.info("进行工作信息删除操作：jobid="+jobid);
		App.checkUser();
		if (!Strings.empty(jobid)){
			try{
			   userInfoService.delUserJobByJobId(jobid);
			   flag = "00";
			}catch(Exception e){
				Logger.error("删除工作信息异常：", e);
			}
		}
		return flag;
	}
	/**
	 * 删除 房屋信息
	 * @param jobid
	 * @param model
	 * @return
	 */
	@RequestMapping("/delHouseDetail/{houseId}")
	@ResponseBody
	public String delHourseDetail(@PathVariable("houseId") String houseId, Model model) {
		String flag = "99";
		Logger.info("进行房屋信息删除操作：houseId="+houseId);
		App.checkUser();
		if (!Strings.empty(houseId)){
			try{
			   userInfoService.delUserHouseById(houseId);
			   flag = "00";
			}catch(Exception e){
				Logger.error("删除房屋信息异常：", e);
			}
		}
		return flag;
	}
	
	/**
	 * 删除 联系人信息
	 * @param jobid
	 * @param model
	 * @return
	 */
	@RequestMapping("/delContacterDetail/{contacterId}")
	@ResponseBody
	public String delContacterDetail(@PathVariable("contacterId") String contacterId, Model model) {
		String flag = "99";
		Logger.info("进行联系人信息删除操作：contacterId="+contacterId);
		App.checkUser();
		if (!Strings.empty(contacterId)){
			try{
			   userInfoService.delUserContacterById(contacterId);
			   flag = "00";
			}catch(Exception e){
				Logger.error("删除联系人信息异常：", e);
			}
		}
		return flag;
	}
	/**
	 * 删除车辆信息
	 * @param carId
	 * @param model
	 * @return
	 */
	@RequestMapping("/delCarDetail/{carId}")
	@ResponseBody
	public String delCarDetail(@PathVariable("carId") String carId, Model model) {
		String flag = "99";
		Logger.info("进行车辆信息删除操作：carId="+carId);
		App.checkUser();
		if (!Strings.empty(carId)){
			try{
			   userInfoService.delUserCarById(carId);
			   flag = "00";
			}catch(Exception e){
				Logger.error("删除车辆信息异常：", e);
			}
		}
		return flag;
	}
}

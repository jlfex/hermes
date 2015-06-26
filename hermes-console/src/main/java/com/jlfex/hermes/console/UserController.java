package com.jlfex.hermes.console;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.Label;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserHouse;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.User.Status;
import com.jlfex.hermes.model.UserEducation.Education;
import com.jlfex.hermes.model.UserHouse.Mortgage;
import com.jlfex.hermes.model.UserImage.Type;
import com.jlfex.hermes.model.UserJob.Enterprise;
import com.jlfex.hermes.model.UserJob.Scale;
import com.jlfex.hermes.model.UserProperties.Gender;
import com.jlfex.hermes.model.UserProperties.IdType;
import com.jlfex.hermes.model.UserProperties.Married;
import com.jlfex.hermes.service.AreaService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LabelService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.UserManageService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.pojo.UserBasic;
import com.jlfex.hermes.service.pojo.UserInfo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserManageService userManageService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private InvestService investService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private  UserService  userService;

	/**
	 * 客户管理首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "user/user-index";
	}
	
	/**
	 * 理财用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadFinanceUser")
	public String loanFinanceUser(Model model){
		model.addAttribute("type", "finance");
		return "user/user-list";
	}
	
	/**
	 * 借款用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadLoanUser")
	public String loanLoanUser(Model model){
		model.addAttribute("type", "loan");
		return "user/user-list";
	}

	@RequestMapping("/table")
	public String table(UserInfo userInfo, Integer page, Model model) {
		String _url = "/user/loadLoanDetail/";
		String flag = "loan";
		if (userInfo.getType().equals("finance")) {
			_url = "/user/loadFinanceDetail/";
			flag = "finance";
		} 
		model.addAttribute("_url", _url);
		model.addAttribute("flag", flag);
		model.addAttribute("users", userManageService.findByCondition(userInfo, page, null));
		return "user/table";

	}
	
	/**
	 * 理财人详细页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadFinanceDetail/{id}")
	public String loadFinanceDetail(@PathVariable("id") String id, Model model) {
		model.addAttribute("id", id);
		User user = userManageService.findById(id);
		model.addAttribute("user", user);
		return "user/user-finance";
	}

	/**
	 * 借款人详细页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadLoanDetail/{id}")
	public String loadLoanDetail(@PathVariable("id") String id, Model model) {
		model.addAttribute("id", id);
		User user = userManageService.findById(id);
		model.addAttribute("user", user);
		return "user/user-loan";
	}

	/**
	 * 基本信息
	 * 
	 * @param id
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadBasic/{type}/{id}")
	public String loadBasic(@PathVariable("id") String id, @PathVariable("type") String type, Model model) {
		UserBasic userBasic = userManageService.loadBasicByUser(id);
		model.addAttribute("userBasic", userBasic);
		model.addAttribute("type", type);
		if (userBasic.getProvince() != null && userBasic.getCity() != null && userBasic.getCounty() != null) {
			String addressPerson = areaService.getAddress(userBasic.getAddress(), userBasic.getProvince(), userBasic.getCity(), userBasic.getCounty());
			model.addAttribute("addressPerson", addressPerson);
		}

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

		// 状态
		Map<Object, String> statusMap = Dicts.elements(Status.class);
		model.addAttribute("statusMap", statusMap);
		return "user/user-basic";
	}

	/**
	 * 账户信息
	 * 
	 * @param id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadAccount/{id}")
	public String loadAccount(@PathVariable("id") String id, Integer page, Model model) {
		model.addAttribute("accounts", userManageService.findBankByUser(id, page, null));
		return "user/user-account";
	}

	/**
	 * 投标记录
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadInvestRecord/{id}")
	public String loadInvestRecord(@PathVariable("id") String id, Integer page, Model model) {
		model.addAttribute("invests", investService.loadInvestRecordByUser(id, page, null));
		return "user/invest-record";
	}

	/**
	 * 工作信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadJob/{id}")
	public String loadJob(@PathVariable("id") String id, Model model) {
		Map<Object, String> enterpriseMap = Dicts.elements(Enterprise.class);
		model.addAttribute("enterpriseMap", enterpriseMap);

		Map<Object, String> scaleMap = Dicts.elements(Scale.class);
		model.addAttribute("scaleMap", scaleMap);

		Map<Object, String> typeMap = Dicts.elements(com.jlfex.hermes.model.UserJob.Type.class);
		model.addAttribute("typeMap", typeMap);
		model.addAttribute("jobs", userManageService.findJobByUser(id, 0, null));
		return "user/user-job";
	}

	/**
	 * 房产信息
	 * 
	 * @param id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadHouse/{id}")
	public String loadHouse(@PathVariable("id") String id, Integer page, Model model) {
		List<UserHouse> houses = userManageService.findHouseByUser(id);
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
		return "user/user-house";
	}

	/**
	 * 车辆信息
	 * 
	 * @param id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadCar/{id}")
	public String loadCar(@PathVariable("id") String id, Integer page, Model model) {
		model.addAttribute("cars", userManageService.findCarByUser(id, page, null));
		Map<Object, String> mortgageMap = Dicts.elements(com.jlfex.hermes.model.UserCar.Mortgage.class);
		model.addAttribute("mortgageMap", mortgageMap);
		return "user/user-car";
	}

	/**
	 * 联系人信息
	 * 
	 * @param id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadContacter/{id}")
	public String loadContacter(@PathVariable("id") String id, Integer page, Model model) {
		model.addAttribute("contacters", userManageService.findContacterByUser(id, page, null));
		return "user/user-contacter";
	}

	/**
	 * 认证资料
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadAuth/{id}")
	public String loadAuth(@PathVariable("id") String id, Model model) {
		String labelStr = App.config("user.auth.labels");
		String[] labelNames = labelStr.split(",");
		List<Label> labels = labelService.findByNames(labelNames);
		model.addAttribute("labels", labels);
		model.addAttribute("id", id);
		return "user/user-auth";
	}

	/**
	 * 图片
	 * 
	 * @param id
	 * @param labelId
	 * @param model
	 * @return
	 */
	@RequestMapping("loadPicture/{id}/{labelId}")
	public String loadPicture(@PathVariable("id") String id, @PathVariable("labelId") String labelId, Model model) {
		List<UserImage> images = userManageService.loadImageByUserAndLabelAndType(id, labelId, Type.AUTH);
		model.addAttribute("images", images);
		model.addAttribute("labelId", labelId);
		return "user/picture";
	}

	/**
	 * 借款记录
	 * 
	 * @param id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadLoanRecord/{id}")
	public String loadLoanRecord(@PathVariable("id") String id, Integer page, Model model) {
		model.addAttribute("loans", loanService.loadLoanRecordByUser(id, page, null));
		return "user/loan-record";
	}
	/**
	 * 冻结用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/freezeUser/{id}")
	@ResponseBody
	public Result<String> freezeUser(@PathVariable("id")String id){
	return userManageService.freezeUser(id);
	}

	/**
	 * 解冻用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/unfreezeUser/{id}")
	@ResponseBody
	public Result<String> unfreezeUser(@PathVariable("id")String id){
	return userManageService.unfreezeUser(id);
	}

	/**
	 * 注销用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/logOffUser/{id}")
	@ResponseBody
	public Result<String> logOffUser(@PathVariable("id")String id){
	return userManageService.logOffUser(id);
	}
	
	/**
	 * 查看昵称是否被占用
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping("checkAccount")
	@ResponseBody
	public JSONObject checkAccount(String account) {
		User user = userService.getUserByAccount(account);
		JSONObject jsonObj = new JSONObject();
		if (user != null && User.Status.ENABLED.equals(user.getStatus())) {
			jsonObj.put("account", false);
		} else {
			jsonObj.put("account", true);
		}

		return jsonObj;
	}
	
	/**
	 * 查看原始密码是否正确
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping("checkOriginalPwd")
	@ResponseBody
	public JSONObject checkOriginalPwd(String id, String pwd) {
		boolean correctFlag = userService.checkCorrectOfUserPwd(id,pwd);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("correctFlag", correctFlag);
		return jsonObj;
	}
}

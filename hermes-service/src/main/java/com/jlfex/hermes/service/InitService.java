package com.jlfex.hermes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.DictionaryTypeRepository;
import com.jlfex.hermes.repository.NavigationRepository;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.UserPropertiesRepository;
import com.jlfex.hermes.repository.UserRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.repository.role.RoleRepository;
import com.jlfex.hermes.service.userProperties.UserPropertiesService;

@Service
@Transactional
public class InitService {
	@Autowired
	private NavigationService navigationService;
	@Autowired
	private NavigationRepository navigationRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPropertiesService userPropertiesService;
	@Autowired
	private UserPropertiesRepository userPropertiesRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleResourceRepository roleResourceRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private DictionaryTypeRepository dictionaryTypeRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;

	public void initData() {
		// initRole();
		// initUserRole();
		// initDictionary();
		// initNavigation();
		// initBackNavigation();
		initRoleResouce();
	}

	/**
	 * 初始化角色
	 */
	private void initRole() {
		for (RoleMode roleMode : RoleMode.values()) {
			Role role = new Role();
			role.setCode(roleMode.name());
			role.setName(roleMode.getModel());
			roleRepository.save(role);
		}
	}

	/**
	 * 初始化用户角色
	 */
	private void initUserRole() {
		User user = userRepository.findOne("e74428d8-7fb4-11e3-ae10-6cae8b21aeaa");
		Role role = roleRepository.findAll().get(0);
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRoleRepository.save(userRole);
	}

	private void initDictionary() {
		DictionaryType dictionaryType = dictionaryTypeRepository.findOne("ec445644-7fb6-11e3-ae10-6cae8b21aeaa");

		Dictionary dictionary = new Dictionary();
		dictionary.setType(dictionaryType);
		dictionary.setName("前端导航");
		dictionary.setCode("ftont_nav");
		dictionary.setOrder(0);
		dictionary.setStatus("00");

		dictionaryRepository.save(dictionary);

	}

	private void initBackNavigation() {
		Dictionary dictionary = dictionaryRepository.findByName("控制台");

		Navigation accountCenterNavigationResult = navigationRepository.findOneByCode("back_home");
		Navigation accountCenterNavigationResult1 = navigationRepository.findOneByCode("back_user_info");

		// 信息管理
		Navigation infoMngNavigation = new Navigation();
		infoMngNavigation.setCode("back_inveter_mgr");
		infoMngNavigation.setName("理财人管理");
		infoMngNavigation.setPath("back_inveter_mgr");
		infoMngNavigation.setOrder(new Integer(0));
		infoMngNavigation.setTarget("_menu");
		infoMngNavigation.setType(dictionary);
		infoMngNavigation.setParent(accountCenterNavigationResult1);

		navigationRepository.save(infoMngNavigation);

		Navigation infoMngNavigation1 = new Navigation();
		infoMngNavigation.setCode("back_loaner_mgr");
		infoMngNavigation.setName("借款人管理");
		infoMngNavigation.setPath("back_loaner_mgr");
		infoMngNavigation.setOrder(new Integer(0));
		infoMngNavigation.setTarget("_menu");
		infoMngNavigation.setType(dictionary);
		infoMngNavigation.setParent(accountCenterNavigationResult1);

		navigationRepository.save(infoMngNavigation1);

		for (BackIndexEnum navigationEnum : BackIndexEnum.values()) {
			Navigation navigation = new Navigation();
			navigation.setCode(navigationEnum.name());
			navigation.setName(navigationEnum.getModel());
			navigation.setPath(navigationEnum.name());
			navigation.setOrder(new Integer(0));
			navigation.setTarget("_menu");
			navigation.setType(dictionary);
			navigation.setParent(accountCenterNavigationResult);
			navigationRepository.save(navigation);
		}
	}

	private void initNavigation() {
		Dictionary dictionary = dictionaryRepository.findByName("前端导航");
		// 账户中心
		Navigation accountCenterNavigation = new Navigation();
		accountCenterNavigation.setCode("front_account_center");
		accountCenterNavigation.setName("账户中心");
		accountCenterNavigation.setPath("front_account_center");
		accountCenterNavigation.setOrder(new Integer(0));
		accountCenterNavigation.setTarget("main");
		accountCenterNavigation.setType(dictionary);

		Navigation accountCenterNavigationResult = navigationRepository.findOne("e13ce47e-8e8f-4183-97e9-c8815829086c");// ;navigationRepository.save(accountCenterNavigation);

		// 信息管理
		Navigation infoMngNavigation = new Navigation();
		infoMngNavigation.setCode("front_info_mng");
		infoMngNavigation.setName("信息管理");
		infoMngNavigation.setPath("front_info_mng");
		infoMngNavigation.setOrder(new Integer(0));
		infoMngNavigation.setTarget("main");
		infoMngNavigation.setType(dictionary);
		infoMngNavigation.setParent(accountCenterNavigationResult);

		Navigation infoMngNavigationResult = navigationRepository.save(infoMngNavigation);

		// 理财信息
		Navigation investNavigation = new Navigation();
		investNavigation.setCode("front_invest_info");
		investNavigation.setName("理财信息");
		investNavigation.setPath("front_invest_info");
		investNavigation.setOrder(new Integer(0));
		investNavigation.setTarget("main");
		investNavigation.setType(dictionary);
		investNavigation.setParent(accountCenterNavigationResult);

		Navigation investNavigationResult = navigationRepository.save(investNavigation);

		// 资金管理
		Navigation fundNavigation = new Navigation();
		fundNavigation.setCode("front_fund_mng");
		fundNavigation.setName("资金管理");
		fundNavigation.setPath("front_fund_mng");
		fundNavigation.setOrder(new Integer(0));
		fundNavigation.setTarget("main");
		fundNavigation.setType(dictionary);
		fundNavigation.setParent(accountCenterNavigationResult);

		Navigation fundNavigationResult = navigationRepository.save(fundNavigation);

		// 个人信息
		Navigation personalNavigation = new Navigation();
		personalNavigation.setCode("front_personal_info");
		personalNavigation.setName("个人信息");
		personalNavigation.setPath("${app}/account/getUserInfo");
		personalNavigation.setOrder(new Integer(0));
		personalNavigation.setTarget("main");
		personalNavigation.setType(dictionary);
		personalNavigation.setParent(infoMngNavigationResult);

		Navigation personalNavigationResult = navigationRepository.save(personalNavigation);

		// 认证中心
		Navigation authNavigation = new Navigation();
		authNavigation.setCode("front_account_authCenter");
		authNavigation.setName("认证中心");
		authNavigation.setPath("${app}/account/approve");
		authNavigation.setOrder(new Integer(0));
		authNavigation.setTarget("main");
		authNavigation.setType(dictionary);
		authNavigation.setParent(infoMngNavigationResult);

		Navigation authNavigationResult = navigationRepository.save(authNavigation);

		// 银行卡管理
		Navigation bankCardMgrNavigation = new Navigation();
		bankCardMgrNavigation.setCode("front_account_bankCardMgr");
		bankCardMgrNavigation.setName("银行卡管理");
		bankCardMgrNavigation.setPath("${app}/account/bankCardManage");
		bankCardMgrNavigation.setOrder(new Integer(0));
		bankCardMgrNavigation.setTarget("main");
		bankCardMgrNavigation.setType(dictionary);
		bankCardMgrNavigation.setParent(infoMngNavigationResult);

		Navigation bankCardMgrNavigationResult = navigationRepository.save(bankCardMgrNavigation);

		// 个人信息
		for (PersonalInfoEnum navigationEnum : PersonalInfoEnum.values()) {
			Navigation navigation = new Navigation();
			navigation.setCode(navigationEnum.name());
			navigation.setName(navigationEnum.getModel());
			navigation.setPath(navigationEnum.name());
			navigation.setOrder(new Integer(0));
			navigation.setTarget("main");
			navigation.setType(dictionary);
			navigation.setParent(personalNavigationResult);
			navigationRepository.save(navigation);
		}

		// 理财信息
		String[] lcPath = new String[] { "${app}/loan/myloan", "${app}/invest/myinvest", "${app}/invest/myCredit" };
		int i = 0;
		for (LCEnum navigationEnum : LCEnum.values()) {
			Navigation navigation = new Navigation();
			navigation.setCode(navigationEnum.name());
			navigation.setName(navigationEnum.getModel());
			navigation.setPath(lcPath[i]);
			navigation.setOrder(new Integer(0));
			navigation.setTarget("main");
			navigation.setType(dictionary);
			navigation.setParent(investNavigationResult);
			navigationRepository.save(navigation);
			i++;
		}

		String[] zjPath = new String[] { "${app}/account/detail", "${app}/account/charge", "${app}/account/withdraw" };
		int j = 0;
		for (ZJEnum navigationEnum : ZJEnum.values()) {
			Navigation navigation = new Navigation();
			navigation.setCode(navigationEnum.name());
			navigation.setName(navigationEnum.getModel());
			navigation.setPath(zjPath[j]);
			navigation.setOrder(new Integer(0));
			navigation.setTarget("main");
			navigation.setType(dictionary);
			navigation.setParent(fundNavigationResult);
			navigationRepository.save(navigation);
			j++;
		}
	}

	private void initRoleResouce() {
		initInvestModel();
		initLoanModel();
		initAllModel();

	}

	// 借款业务运营方
	private void initLoanModel() {
		DictionaryType dictionaryType = dictionaryTypeRepository.findOne("ec445644-7fb6-11e3-ae10-6cae8b21aeaa");
		Role role = roleRepository.findOne("d09fc28a-e27a-4e85-a3ff-864d0687c469");

		List<RoleResource> resources = new ArrayList<RoleResource>();

		String[] roles = new String[] { "front_account_center", "front_info_mng", "front_invest_info", "front_fund_mng", "front_personal_info", "back_home", "back_search", "back_user_info", "back_interface_mng" };

		for (String string : roles) {
			Navigation navigation = navigationRepository.findOneByCode(string);
			RoleResource roleResource = new RoleResource();
			roleResource.setResource(navigation.getId());
			roleResource.setRole(role);
			roleResource.setStatus(HermesConstants.VALID);
			roleResource.setType(dictionaryType.getId());

			resources.add(roleResource);
		}

		roleResourceRepository.save(resources);
	}

	// 理财业务运营方
	private void initInvestModel() {
		DictionaryType dictionaryType = dictionaryTypeRepository.findOne("ec445644-7fb6-11e3-ae10-6cae8b21aeaa");
		Role role = roleRepository.findOne("035c99bb-e72f-4490-a14f-4424045b96db");

		List<RoleResource> resources = new ArrayList<RoleResource>();

		String[] roles = new String[] { "front_account_center", "front_info_mng", "front_invest_info", "front_fund_mng", "front_personal_info", "back_home", "back_search", "back_user_info", "back_interface_mng" };

		for (String string : roles) {
			Navigation navigation = navigationRepository.findOneByCode(string);
			RoleResource roleResource = new RoleResource();
			roleResource.setResource(navigation.getId());
			roleResource.setRole(role);
			roleResource.setStatus(HermesConstants.VALID);
			roleResource.setType(dictionaryType.getId());

			resources.add(roleResource);
		}

		roleResourceRepository.save(resources);

	}

	// P2P业务运营方
	private void initAllModel() {
		DictionaryType dictionaryType = dictionaryTypeRepository.findOne("ec445644-7fb6-11e3-ae10-6cae8b21aeaa");
		Role role = roleRepository.findOne("7ec53c8a-ff0c-4653-9be6-052b03c7aec2");

		List<RoleResource> resources = new ArrayList<RoleResource>();

		String[] roles = new String[] { "front_account_center", "front_info_mng", "front_invest_info", "front_fund_mng", "front_personal_info", "back_home", "back_search", "back_user_info", "back_interface_mng" };

		for (String string : roles) {
			Navigation navigation = navigationRepository.findOneByCode(string);
			RoleResource roleResource = new RoleResource();
			roleResource.setResource(navigation.getId());
			roleResource.setRole(role);
			roleResource.setStatus(HermesConstants.VALID);
			roleResource.setType(dictionaryType.getId());

			resources.add(roleResource);
		}

		roleResourceRepository.save(resources);
	}

	public enum RoleMode {
		loanModel("借款业务运营方"), investModel("理财业务运营方"), allModel("P2P业务运营方");
		String model;

		private RoleMode(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}

	/**
	 * 账户中心
	 * 
	 * @author jswu
	 *
	 */
	public enum NavigationEnum {
		front_info_mng("信息管理"), front_invest_info("理财信息"), front_fund_mng("资金管理");
		String model;

		private NavigationEnum(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}

	/**
	 * 信息管理
	 * 
	 * @author jswu
	 *
	 */
	public enum InfoMngEnum {
		front_personal_info("个人信息"), front_account_authCenter("认证中心"), front_account_bankCardMgr("银行卡管理");
		String model;

		private InfoMngEnum(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}

	/**
	 * 个人信息
	 * 
	 * @author jswu
	 *
	 */
	public enum PersonalInfoEnum {
		front_info_base("基本信息"), front_info_work("工作信息"), front_info_house("房产信息"), front_info_car("车辆信息"), front_info_contacter("联系人信息"), front_info_imag("图片信息");
		String model;

		private PersonalInfoEnum(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}

	/**
	 * 理财信息
	 * 
	 * @author jswu
	 *
	 */
	public enum LCEnum {
		front_myloan("我的借款"), fron_myinvest("我的理财"), fron_myCredit("我的债权");
		String model;

		private LCEnum(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}

	/**
	 * 资金管理
	 * 
	 * @author jswu
	 *
	 */
	public enum ZJEnum {
		front_fundDetail("资金明细"), front_charg("账户充值"), front_withdraw("账户提现");
		String model;

		private ZJEnum(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}

	/**
	 * 后台首页
	 * 
	 * @author jswu
	 *
	 */
	public enum BackIndexEnum {
		back_home_audit("审核"), back_home_full("满标放款"), back_home_debt("催款");
		String model;

		private BackIndexEnum(String model) {
			this.model = model;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}
	}
}

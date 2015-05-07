package com.jlfex.hermes.common.constant;

import com.jlfex.hermes.common.dict.Element;

/**
 * 枚举类
 * 
 * @author wujinsong
 *
 */
public class HermesEnum {
	public enum Tx1361Status {
		/**
		 * 处理中
		 */
		IN_PROCESSING(20),
		/**
		 * 代扣成功
		 */
		WITHHOLDING_SUCC(30),
		/**
		 * 代扣失败
		 */
		WITHHOLDING_FAIL(40);

		private Tx1361Status(Integer status) {
			this.status = status;
		}

		private Integer status;

		public Integer getStatus() {
			return status;
		}
	}

	/**
	 * 第三方支付机构
	 * 
	 * @author jswu
	 *
	 */
	public enum PPOrg {
		/**
		 * 中金
		 */
		ZJ
	}

	/**
	 * 平台证件类型和中金证件类型映射
	 * 
	 * @author jswu
	 *
	 */
	public static final class P2ZJIdType {
		@Element("0")
		public static final String ID = "00";
		@Element("2")
		public static final String CCB = "01";
	}

	/**
	 * 平台银行和中金银行映射
	 * 
	 * @author jswu
	 *
	 */
	public static final class P2ZJBank {
		@Element("105")
		public static final String CCB = "中国建设银行";
		@Element("103")
		public static final String ABC = "中国农业银行";
		@Element("308")
		public static final String CMB = "招商银行";
		@Element("102")
		public static final String ICBC = "中国工商银行";
		@Element("104")
		public static final String BOC = "中国银行";
		@Element("301")
		public static final String BCM = "交通银行";
		@Element("401")
		public static final String BOS = "上海银行";
		@Element("310")
		public static final String SPDB = "浦发银行";
		@Element("309")
		public static final String CIB = "兴业银行";
		@Element("305")
		public static final String CMBC = "中国民生银行";
		@Element("303")
		public static final String CEB = "中国光大银行";
		@Element("302")
		public static final String ECITIC = "中信银行";
		@Element("100")
		public static final String PSBC = "中国邮政储蓄银行";
		@Element("307")
		public static final String PAB = "平安银行";
		@Element("403")
		public static final String BOB = "北京银行";
		@Element("306")
		public static final String CGB = "广发银行";
	}

	/**
	 * 角色模型
	 * 
	 * @author jswu
	 *
	 */
	public enum RoleModel {
		/**
		 * 信贷端
		 */
		LoanModel,
		/**
		 * 理财端
		 */
		InvestModel,
		/**
		 * 信贷+理财
		 */
		AllModel
	}

	/**
	 * 导航枚举
	 * 
	 * @author jswu
	 *
	 */
	public enum NavigationEnum {
		front_home("首页"), front_invest("我要理财"), front_loan("我要借款"),front_help("使用帮助"), front_account_center("账户中心"), front_info_mng("信息管理"), front_invest_info("理财信息"), front_fund_mng("资金管理"), front_personal_info("个人信息"), front_info_base("基本信息"), front_info_work("工作信息"), front_info_house("房产信息"), front_info_car(
				"车辆信息"), front_info_contacter("联系人信息"), front_info_imag("图片信息"), front_account_authCenter("认证中心"), front_account_bankCardMgr("银行卡管理"), front_myloan("我的借款"), fron_myinvest("我的理财"), fron_myCredit("我的债权"), front_fundDetail("资金明细"), front_charg("账户充值"), front_withdraw(
				"账户提现"), back_home("首页"), back_search("信息查询"), back_user_info("客户信息"), back_interface_mng("接口管理"), back_home_audit("审核"), back_home_full("满标放款"), back_home_debt("催款"), back_loan_info("借款信息"), back_inveter_mgr("理财人管理"), back_loaner_mgr("借款人管理"), back_product_mgr(
				"产品管理"), back_credit_mgr("债权管理"), back_fund_mgr("资金管理"), back_content_mgr("内容管理"), back_system_mgr("系统管理"), back_api_log_mgr("API日志"), back_interface_mgr("API配置");
		private NavigationEnum(String name) {
			this.name = name;
		}

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}

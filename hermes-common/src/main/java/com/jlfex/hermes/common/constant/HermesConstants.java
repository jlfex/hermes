package com.jlfex.hermes.common.constant;

/**
 * hermes平台常量
 * 
 * @author Administrator
 *
 */
public final class HermesConstants {

	/**
	 * 平台管理
	 */
	public static final String PLAT_MANAGER = "admin";
	public static final String PRE_HERMES = "HERMES"; // 下单并支付接口 订单流水号前缀
	public static final String CROP_USER_ID = "crop"; 
	
	public static final String COMPANY_NAME = "app.company.name";       //公司名称
	public static final String COMPANY_ADDRESS = "app.company.address"; //公司地址
	public static final String COMPANY_PNAME = "app.operation.name";    //平台名称 
	

	public static final int RESULT_VO_CODE_SUCCESS = 0;
	public static final int RESULT_VO_CODE_BIZ_ERROR = 1;
	public static final String INDEX_BANNER = "banner";
	public static final String INDEX_INVEST = "invest";
	public static final String INDEX_LOGIN = "login";
	public static final String INDEX_LOAN = "loan";
	public static final String INDEX_REGISTER = "register";
	public static final String NOTICE_CODE = "01";
	public static final String BANNER = "首页banner广告";
	public static final String INVEST = "首页—我要理财";
	public static final String LOAN = "首页—我要借款";
	public static final String LOGIN = "登录界面";
	public static final String REGISTER = "注册界面";

	public static final String CODE_PRODUCT_MANAGE_FEE_PERCENT  = "month.manager.fee.percent";		//产品月管理费百分比编码
	public static final String CODE_PRODUCT_MANAGE_FEE_CONSTANT = "month.manager.fee.constant";     //产品月管理费定值编码
	public static final String CODE_PRODUCT_MANAGE_FEE_SWITCH   = "month.manager.fee.switch";		//产品月管理费开关切换编码
	public static final String PRODUCT_OVERDU_INTEREST_FEE_CODE = "overdue_interest_fee"; 			//产品逾期还款：罚息费率梯度编码
	public static final String PRODUCT_OVERDU_PENALTY_FEE_CODE  = "overdue_penalty_fee";			//产品逾期还款: 违约金费率梯度编码
	
	public static final String PROP_WITHDRAW_FEE_NAME	 = "fee.withdraw.name";
	public static final String PROP_WITHDRAW_FEE_CONFIG	 = "fee.withdraw.config";					//提现手续费区间配置
	public static final String PROP_WITHDRAW_FEE_RATE 	 = "fee.withdraw.rate";                		//提现手续费比例编码
	public static final String WITHDRAW_FEE_SWITCH 		 = "fee.withdraw.switch";					//提现手续费类型开关编码
	public static final String WITHDRAW_FEE_MAX_AMOUNT 	 = "fee.charge.max.amount";                 //提现手续费比例方式 单笔最大金额
	
	/**
	 * 公共开关标识 
	 * 0:开 ;
	 * 1:关
	 */
	public static final String SWITCH_FLAG_ZERO = "0";   // 开
	public static final String SWITCH_FLAG_ONE =  "1";   // 关
	
	
	/**
	 * HTTPS 连接常量
	 */
	public static final int    HTTPS_PORT = 443;
	public static final String SCHEME_HTTPS = "https";
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String KEY_STORE_TYPE_JKS = "jks";
	public static final String KEY_STORE_TYPE_P12 = "PKCS12";
	public static final String CONTENT_TYPE_PDF = "application/pdf";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String MESSAGE_FORMAT = "json";
	public static final String MESSAGE_VERSION = "1.0";

	/**
	 * 时间格式化常量
	 */
	public static final String FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_10 = "yyyy-MM-dd";
	public static final String NICK_DAY = "d";  // 单位：天
	public static final String UNIT_MONTH = "月";
	public static final String UNIT_DAY   = "天";

	/**
	 * 状态 0: 有效 1：无效
	 */
	public static final String VALID = "0";
	public static final String INVALID = "1";

	/**
	 * 00 :成功 99 ：异常
	 */
	public static final String CODE_00 = "00";
	public static final String CODE_99 = "99";


	/**
	 * 接入第三方平台编码
	 */
	public static final String YLXT = "易联天下";
	public static final String PLAT_JLFEX_CODE = "yltx";
	public static final String DICTIONARY_TYPE_OPEN_PLAT_CODE = "open.jlfex.interface";
	public static final String PRE_ZQ = "ZQ"; // execell导入债权 债权人新增 序列 前缀

	/**
	 * 开发平台接口
	 */

	public static final int    JL_PAGE_SIZE = 30; 								 // 页面结果集 大小
	public static final int    JL_PAGE_NUM = 1; 								 // 当前页
	public static final String JL_FINANCE_FRODUCT_GET = "jl.financefroduct.get"; // 查询理财产品接口
	public static final String JL_ORDER_GET = "jl.order.get";					 // 查询订单接口
	public static final String JL_FILE_GET = "jl.file.get";						 // 查询文件协议
	public static final String JL_ORDER_DO2PAY = "jl.order.do2pay"; 			 // 下单并支付接口
	public static final String JL_FINPRO_REPSCH = "jl.finpro.repsch.get"; 		 // 查询还款计划接口
	public static final String JL_ORDER_CANCEL = "jl.order.cancel"; 			 // 撤销订单接口

	public static final String CODE_FINANCE_FRODUCT_GET = "9900001"; 			 // 查询理财产品接口
	public static final String CODE_ORDER_GET = "9900002"; 						 // 查询订单接口
	public static final String CODE_FILE_GET = "9900003"; 						 // 查询文件协议
	public static final String CODE_ORDER_DO2PAY = "1000005"; 					 // 下单并支付接口
	public static final String CODE_FINPRO_REPSCH = "9900004"; 					 // 查询还款计划接口
	public static final String CODE_ORDER_CANCEL = "1000003"; 					 // 撤销订单接口

	public static final String TYPE_FINANCE_REPAY_PLAN = "1"; 					 // 理财产品还款计划
	public static final String TYPE_ASSET_REPAY_PLAN = "2"; 					 // 资产还款计划
	public static final String TYPE_ORDER_REPAY_PLAN = "3";						 // 订单还款计划

	/**
	 *jlfex 订单状态
	 */
	public static final String ORDER_WAIT_PAY = "待付款";
	public static final String ORDER_TRAD_FAIL = "交易失败";
	public static final String ORDER_CANCEL_ORDER = "已撤单";
	public static final String ORDER_FINISH_PAY = "已付款";
	public static final String ORDER_WAIT_REPAY = "等待回款";
	public static final String ORDER_ASSIGNING = "转让中";
	public static final String ORDER_FINISH_REPAY = "已回款";
	public static final String ORDER_OVERDUE = "已逾期";
	public static final String ORDER_INSTEAD_PAY = "已代偿";
	public static final String ORDER_PAYING = "付款中";

	/**
	 * jlfex 支付状态
	 */
	public static final String PAY_SUC = "支付成功";
	public static final String PAY_FAIL = "支付失败";
	public static final String PAY_WAIT_CONFIRM = "支付确认中";
	public static final String CLEARING_SUC = "结算成功";
	public static final String CLEARING_FAIL = "结算失败";
	public static final String CLEARING_DOING = "结算正在处理中";
	public static final String WAIT_CLEARING = "未结算";
	public static final String REFUND_SUC = "退款成功";
	public static final String REFUND_FAIL = "退款失败";
	public static final String NOTHING = "无";

	/**
	 * 理财产品状态
	 */
	public static final String FINANCE_FUNDRAISING = "募资中";
	public static final String FINANCE_WAIT_PAY = "待付款";
	public static final String FINANCE_FINISHED = "募资完成";
	/**
	 * 易联债权标 默认属性
	 */
	public static final String YLTX_CREDIT_LEND_FEE = "0.00000000"; // 债权标：借款手续费率
	public static final String YLTX_CREDIT_RISK_FEE = "0.00000000"; // 债权标：风险金费率

	/**
	 * 还款方式
	 */
	public static final String PAY_TYPE_DEBX = "等额本息";
	public static final String PAY_TYPE_HBFX = "一次性还本付息";

	/**
	 * 易联天下债权标
	 */
	public static final String YLTX_CREDIT = "外部债权标";
	public static final String PROVINCE_SUFFIX = "市";

	/**
	 * 中金接口
	 */
	public static final String ZJ_INTERFACE_TX1361 = "1361-市场订单代扣";
	public static final String ZJ_INTERFACE_TX1341 = "1341-市场订单结算";
	public static final String ZJ_INTERFACE_TX1350 = "1350-市场订单结算查询";
	/**
	 * 中金支付代表成功返回的代码
	 */
	public static final String CFCA_SUCCESS_CODE = "2000";

	/**
	 * 中金市场订单号编码
	 */
	public static final String CFCA_MARKET_ORDER_NO_CODE = "cfca.market.order.no";

	/**
	 * 中金机构号编码
	 */
	public static final String CFCA_INSTITUTION_ID_CODE = "cfca.institution.id";
	//账户类型
	public static final int ZJ_ACCOUNT_TYPE_11 = 11; //个人账户
	public static final int ZJ_ACCOUNT_TYPE_12 = 12; //企业账户
	public static final int ZJ_ACCOUNT_TYPE_20 = 20; //支付平台内部账户
	
	//中金代扣请求流水号前缀
	public static final String  PRE_IN = "IN";
	//中金清算请求流水号前缀
	public static final String  PRE_OUT = "OUT";
	public static final String  CLEAR_NOTE = "市场订单结算";
	
	/**
	 * properties 标识
	 */
	public static final String KEY_DATABASE = "com.jlfex.properties.database";
	/**
	 * 字典项: 平台类编码
	 */
	public static final String PLAT_TYPE = "plat_type";
	/**
	 * 第三方 中金支付 编码
	 */
	public static final String PLAT_ZJ_CODE= "zjpay";

	/**
	 * 字典类型的导航类型
	 */
	public static final String DIC_NAV = "nav";
	
	/**
	 * 后台菜单code
	 */
	public static final String DIC_CONSOLE = "console";
	/**
	 * 等额本息
	 */
	public static final String REPAY_PRINCIPAL = "Principal";
	
	/**
	 * 文章类型：公告
	 */
	public static final String PUBLIC_NOTICE = "public_notice";
	
	/**
	 * 百分号 %
	 */
	public static final String SUFFIX_PERCENT = "%";
	
	/**
	 * 默认每页页大小
	 */
	public static final String DEFAULT_PAGE_SIZE = "10";
	
	/**
	 * crop的email
	 */
	public static final String CROP_EMAIL = "平台";

	/**
	 * 表示对象不存在
	 */
	public static final String OBJECT_NOT_EXIST = "-1";
	
	/**
	 * Navigation根节点
	 */
	public static final String ROOT = "ROOT";
}

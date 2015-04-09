package com.jlfex.hermes.common.constant;


/**
 * hermes平台常量
 * @author Administrator
 *
 */
public final class HermesConstants {
	
	/**
	 * 平台管理
	 */
	public static final String 	 PLAT_MANAGER = "admin";
	public static final String   PRE_HERMES = "HERMES"; // 下单并支付接口 订单流水号前缀
	public static final String   CROP_USER_ID = "crop";
    /**
     * HTTPS 连接常量
     */
	public static final int   	 HTTPS_PORT         = 443;
	public static final String	 SCHEME_HTTPS       = "https";
	public static final String 	 CHARSET_UTF8       = "UTF-8";
	public static final String   KEY_STORE_TYPE_JKS = "jks";
	public static final String   KEY_STORE_TYPE_P12 = "PKCS12";
	public static final String   CONTENT_TYPE_PDF   = "application/pdf";
	public static final String   CONTENT_TYPE_JSON  = "application/json";
	public static final String   MESSAGE_FORMAT     = "json";
	public static final String   MESSAGE_VERSION    = "1.0";

	/**
	 * 时间格式化常量
	 */
	public static final String   FORMAT_19  = "yyyy-MM-dd HH:mm:ss";
	public static final String   FORMAT_10  = "yyyy-MM-dd";
	
	/**
	 * 状态 0: 有效   1：无效
	 */
	public static final String  VALID= "0"; 
	public static final String  INVALID = "1";
	
	/**
	 * 00 :成功    99 ：异常
	 */
	public static final String  CODE_00= "00"; 
	public static final String  CODE_99 = "99";
	
	/**
	 * 序列名称
	 */
	//易联天下：请求流水号
	public static final String SEQ_YLTX_REQUEST_SERIAL_NO = "serialNo";
	public static final String SEQ_YLTX_REQUEST_ORDER_SN  = "orderSn";
	
	/**
	 * 接入第三方平台编码
	 */
	public static final String  YLXT = "易联天下";
	public static final String  PLAT_JLFEX_CODE = "yltx";
	public static final String  DICTIONARY_TYPE_OPEN_PLAT_CODE = "open.jlfex.interface";
	public static final String  PRE_ZQ = "ZQ";  //execell导入债权  债权人新增 序列 前缀
	
	/**
	 * 开发平台接口
	 */
	
	public  static final int    JL_PAGE_SIZE = 30;                                  //页面结果集 大小
	public  static final int    JL_PAGE_NUM = 1;                                    //当前页
	public  static final String JL_FINANCE_FRODUCT_GET = "jl.financefroduct.get";  //查询理财产品接口
	public  static final String JL_ORDER_GET = "jl.order.get";					   //查询订单接口
	public  static final String JL_FILE_GET = "jl.file.get";                       //查询文件协议
	public  static final String JL_ORDER_DO2PAY = "jl.order.do2pay";               //下单并支付接口
	public  static final String JL_FINPRO_REPSCH = "jl.finpro.repsch.get";         //查询还款计划接口
	public  static final String JL_ORDER_CANCEL = "jl.order.cancel";         	   //撤销订单接口
	
	public  static final String CODE_FINANCE_FRODUCT_GET = "9900001";  			   //查询理财产品接口
	public  static final String CODE_ORDER_GET = "9900002";					       //查询订单接口
	public  static final String CODE_FILE_GET = "9900003";                         //查询文件协议
	public  static final String CODE_ORDER_DO2PAY = "1000005";                     //下单并支付接口
	public  static final String CODE_FINPRO_REPSCH = "9900004";                    //查询还款计划接口
	public  static final String CODE_ORDER_CANCEL = "1000003";                     //撤销订单接口
	
	public  static final String TYPE_FINANCE_REPAY_PLAN = "1"; 	                   //理财产品还款计划
	public  static final String TYPE_ASSET_REPAY_PLAN = "2";                       //资产还款计划
	public  static final String TYPE_ORDER_REPAY_PLAN = "3";                       //订单还款计划
	
	//中金接口
	/**
	 * 单笔代收
	 */
	public static final String ZJ_INTERFACE_TX1361 = "Tx1361";
	
	/**
	 * 订单状态
	 */
	public  static final String ORDER_WAIT_PAY 		 	 = "待付款";
	public  static final String ORDER_TRAD_FAIL 		 = "交易失败";
	public  static final String ORDER_CANCEL_ORDER 	     = "已撤单";
	public  static final String ORDER_FINISH_PAY		 = "已付款";
	public  static final String ORDER_WAIT_REPAY		 = "等待回款";
	public  static final String ORDER_ASSIGNING 		 = "转让中";
	public  static final String ORDER_FINISH_REPAY	     = "已回款";
	public  static final String ORDER_OVERDUE			 = "已逾期";
	public  static final String ORDER_INSTEAD_PAY	 	 = "已代偿";
	public  static final String ORDER_PAYING			 = "付款中";
	
	/**
	 * 支付状态
	 */
	public  static final String PAY_SUC   		= "支付成功";
	public  static final String PAY_FAIL 		= "支付失败";
	public  static final String PAY_WAIT_CONFIRM= "支付确认中";
	public  static final String CLEARING_SUC  	= "结算成功";
	public  static final String CLEARING_FAIL 	= "结算失败";
	public  static final String CLEARING_DOING	= "结算正在处理中";
	public  static final String WAIT_CLEARING 	= "未结算";
	public  static final String REFUND_SUC 		= "退款成功";
	public  static final String REFUND_FAIL	    = "退款失败";
	public  static final String NOTHING			= "无";
	
	
	/**
	 * 理财产品状态
	 */
	public  static  final String  FINANCE_FUNDRAISING = "募资中";
	public  static  final String  FINANCE_WAIT_PAY    = "待付款";
	public  static  final String  FINANCE_FINISHED = "募资完成";
	/**
	 * 易联债权标 默认属性
	 */
	public static final String YLTX_CREDIT_LEND_FEE = "0.00000000";  //债权标：借款手续费率
	public static final String YLTX_CREDIT_RISK_FEE = "0.00000000";  //债权标：风险金费率   
	
	/**
	 * 还款方式
	 */
	public static final String PAY_TYPE_DEBX = "等额本息"; 
	public static final String PAY_TYPE_HBFX= "一次性还本付息"; 
	
	/**
	 * 易联天下债权标
	 */
	public static final String  YLTX_CREDIT = "外部债权标";
	public static final String  PROVINCE_SUFFIX = "市";
	
	/**
	 * 中金支付代表成功返回的代码
	 */
	public static final String  CFCA_SUCCESS_CODE = "2000";
	
	/**
	 * 中金市场订单号
	 */
	public static final String CFCA_MARKET_ORDER_NO = "H-100000-000001";
}

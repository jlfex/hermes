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
}

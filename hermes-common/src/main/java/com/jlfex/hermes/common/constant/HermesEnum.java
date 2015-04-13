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
		@Element("中国建设银行")
		public static final String CCB = "105";
		@Element("中国农业银行")
		public static final String ABC = "103";
		@Element("招商银行")
		public static final String CMB = "308";
		@Element("102")
		public static final String ICBC = "中国工商银行";
		@Element("中国银行")
		public static final String BOC = "104";
		@Element("交通银行")
		public static final String BCM = "301";
		@Element("上海银行")
		public static final String BOS = "401";
		@Element("浦发银行")
		public static final String SPDB = "310";
		@Element("兴业银行")
		public static final String CIB = "309";
		@Element("中国民生银行")
		public static final String CMBC = "305";
		@Element("中国光大银行")
		public static final String CEB = "303";
		@Element("中信银行")
		public static final String ECITIC = "302";
		@Element("中国邮政储蓄银行")
		public static final String PSBC = "100";
		@Element("平安银行")
		public static final String PAB = "307";
		@Element("北京银行")
		public static final String BOB = "403";
		@Element("广发银行")
		public static final String CGB = "306";
	}
}

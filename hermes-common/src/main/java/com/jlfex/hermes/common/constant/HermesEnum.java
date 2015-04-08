package com.jlfex.hermes.common.constant;

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
	 * @author jswu
	 *
	 */
	public enum PPOrg {
		/**
		 * 中金
		 */
		ZJ
	}
}

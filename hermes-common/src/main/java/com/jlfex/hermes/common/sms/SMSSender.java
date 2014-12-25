package com.jlfex.hermes.common.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;

public class SMSSender {
	private static String errorMessage = "";

	/**
	 * 
	 * @param para
	 * @return 0成功 -1失败
	 */
	public static int sendMessage(String content, String tel) {
		if (Strings.empty(tel) || tel.length() != 11) {
			errorMessage = "手机号码格式错误,tel=" + tel;
			Logger.error(errorMessage);
			return -1;
		}
		Logger.info("发送短信，手机号码：" + tel + " 短信内容\"" + content + "\"");
		return send(tel, content);
		// return 0;
	}


	/**
	 * 
	 * @param tel
	 *            :手机号码
	 * @param m
	 *            ：短信内容
	 * @return 0成功 -1失败
	 */
	private static int send(String tel, String m) {
		if (Strings.empty(tel) || Strings.empty(m)) {
			errorMessage = "请检查参数的正常确性,tel=" + tel + ",m=" + m;
			Logger.error(errorMessage);
			return -1;
		}
		final SMSClient sm = new SMSClient();
		String username = "shzhengda2";
		String password = "zd0716";
		sm.initialize(username, password, "sms.imoso.com.cn", 8088);
		long msgId = System.currentTimeMillis() + new Random().nextInt();
		int rs;
		try {
			rs = sm.send(msgId, tel, m);
		} catch (Exception e1) {
			rs = -1;
			errorMessage = "短信发送失败,tel=" + tel + ",m=" + m + ",系统错误信息：" + e1.getMessage();
			Logger.error(errorMessage);
			// throw new RuntimeException(errorMessage);
			e1.printStackTrace();
		}
		sm.close();
		return rs;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", "[测试中文乱码]");
		sendMessage("tel_bind", "15102168937");
		// map.put("0", "123456");
		// for(int i=0;i<5;i++){
		// }
		// sendMessage("18629176032", "")

		// ==================================
	}
}

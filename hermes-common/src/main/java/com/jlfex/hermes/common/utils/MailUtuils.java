package com.jlfex.hermes.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailUtuils {
	/**
	 * 判断邮件是否有效
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidMail(String email) {
		boolean isExist = false;
		Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
		Matcher m = p.matcher(email);
		if(m.matches()) {
			isExist = true;
		}
		return isExist;
	}
}

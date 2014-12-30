package com.jlfex.hermes.common.mail;

/**
 * 邮件接口
 * @author Administrator
 * @since 2014年12月30日12:45:47
 * @version V1.0
 */
public interface EmailService {
	
	/**
	 * 发送邮件方法
	 * @param to
	 * @param subject
	 * @param htmlText
	 * @throws Exception
	 */
	void sendEmail(String to, String subject, String htmlText) throws  Exception;

}

package com.jlfex.hermes.common.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件发送的实现类
 * 
 * @author Administrator
 * @since 2014年12月30日12:48:44
 * @version 1.0
 * 
 */
public class EmailServiceImpl implements EmailService {

	private JavaMailSender javaMailSender;
	private String systemEmail;

	@Override
	public void sendEmail(String to, String subject, String htmlText) throws Exception {
		try {

			// 使用javaMailSender创建一个MimeMessage msg，对应了将要发送的邮件
			MimeMessage msg = javaMailSender.createMimeMessage();
			// 通过MimeMessage创建一个MimeMessageHelper msgHelper
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg);
			// 使用MimeMessageHelper设置邮件的发送地址、收件地址、主题以及内容
			msgHelper.setFrom(systemEmail);
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(htmlText, true);
			// 发送邮件
			javaMailSender.send(msg);
		} catch (Exception e) {
			throw new Exception("Failed to send email.", e);
		}

	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

}

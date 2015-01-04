package com.jlfex.hermes.common;

import static org.junit.Assert.*;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.jlfex.hermes.common.mail.EmailService;

/**
 * greenMail邮件单元测试
 * @author Administrator
 * @since 2014年12月30日14:32:40
 * @version 1.0
 */
public class MailTest {

	/**
	 * @param args
	 */
	private GreenMail greenMail;
    private ApplicationContext applicationContext;
    @Before
    public void setUp() throws Exception {
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("jinlumaster@sina.cn", "Wise_123");
        greenMail.start();
    }
    
    @Test
    public void testSendEmail() throws Exception {
//        applicationContext = new ClassPathXmlApplicationContext("mail.xml");
//        EmailService accountEmailService = (EmailService) applicationContext.getBean("emailService");
//        String subject = "hermes邮件系统";
//        String htmlText = "<h3> 测试邮件，请勿回复003 </h3>";
//        accountEmailService.sendEmail("yanlei@jlfex.com", subject, htmlText);
//        greenMail.waitForIncomingEmail(2000, 1);
//        Message[] msgs = greenMail.getReceivedMessages();
//        assertEquals(0, msgs.length);
    }
    
    @After
    public void tearDown() throws Exception {
        greenMail.stop();
    }
 


    
	
}

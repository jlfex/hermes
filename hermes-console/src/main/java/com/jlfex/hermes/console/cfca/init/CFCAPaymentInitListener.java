package com.jlfex.hermes.console.cfca.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cfca.payment.api.system.PaymentEnvironment;

import com.jlfex.hermes.common.Logger;

public class CFCAPaymentInitListener implements ServletContextListener {

	private static final String PAYMENT_CONFIG_PATH = "payment";

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		Logger.info("开始初始化中金支付环境");
		try {
			// 初始化支付环境
			String path = Class.class.getClass().getResource("/").getPath() + PAYMENT_CONFIG_PATH;
			PaymentEnvironment.initialize(path);
		} catch (Exception e) {
			Logger.error("初始化中金支付环境异常:", e);
		}
		Logger.info("初始化中金支付环境完毕");
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}

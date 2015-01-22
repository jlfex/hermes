package com.jlfex.hermes.console.tomcat;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HermesConsoleStart {
	private static Logger logger = LoggerFactory.getLogger(HermesConsoleStart.class);

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8090);
		tomcat.getConnector().setURIEncoding("UTF-8");
		String path = HermesConsoleStart.class.getResource("/").getPath();
		tomcat.addWebapp("", path.substring(0, path.indexOf("target")) + "src/main/webapp");
		tomcat.start();
		logger.info("Started tomcat");
		tomcat.getServer().await();
	}
}
package com.jlfex.hermes.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlfex.hermes.common.support.spring.SpringWebApp;

@Controller
@RequestMapping("/system")
public class SystemController {

	/**
	 * 修改数据库配置
	 */
	@RequestMapping("udpateDbConfig")
	public String udpateDbConfig() {
		Properties properties = new Properties();
		try {
			properties.load(SystemController.class.getResourceAsStream("/application.conf"));
			OutputStream outputStream = new FileOutputStream(new File(SystemController.class.getResource(
					"/application.conf").toURI()));
			properties.setProperty("db.driver", "com.mysql.jdbc.Driver");
			properties.setProperty("db.url",
					"jdbc:mysql://127.0.0.1:3306/hermes?useUnicode=true&characterEncoding=utf-8");
			properties.setProperty("db.username", "hermes_dev");
			properties.setProperty("db.password", "123456");
			properties.setProperty("jpa.database", "MYSQL");
			properties.store(outputStream, "System Base Infomation(Database and Administrator)");
			outputStream.flush();
			outputStream.close();
			SpringWebApp.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/configdb";
	}
}

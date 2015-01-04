package com.jlfex.hermes.service.web;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.CommonRepository.Script;
import com.jlfex.hermes.service.common.Query;

public class ForUpdateListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		checkAndUpdate(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
	}
	
	private void checkAndUpdate(WebApplicationContext ctx){
		Logger.info("start check for update");
		CommonRepository commonRepository=ctx.getBean(CommonRepository.class);
		Query query=new Query("from User");
		Long userCount=commonRepository.count(query.getCount(), new HashMap<String, Object>());
		if(userCount==0){
			Logger.info("first launch.need init db");
			commonRepository.executeNative(Script.initData);
			Logger.info("init db done.");
			return;
		}
		Logger.info("no need for update");
	}

}

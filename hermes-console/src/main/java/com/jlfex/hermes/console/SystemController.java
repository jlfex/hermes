package com.jlfex.hermes.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.service.PropertiesService;

/**
 * 系统控制器
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-29
 * @since 1.0
 */
@Controller
@RequestMapping("/system")
public class SystemController {

	/** 系统属性业务接口 */
	@Autowired
	private PropertiesService propertiesService;

	/**
	 * 系统属性
	 * 
	 * @return
	 */
	@RequestMapping("/properties")
	public String properties() {
		return "system/properties";
	}

	/**
	 * 查询系统属性
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @return
	 */
	@RequestMapping("/properties/search")
	@ResponseBody
	public Page<Properties> searchProperties(String name, String code, Integer page) {
		return propertiesService.findByNameAndCode(name, code, page, null);
	}

	/**
	 * 保存系统属性
	 * 
	 * @param id
	 * @param name
	 * @param code
	 * @param value
	 * @return
	 */
	@RequestMapping("/properties/save")
	@ResponseBody
	public Properties saveProperties(String id, String name, String code, String value) {
		return propertiesService.save(id, name, code, value);
	}

	/**
	 * 读取系统属性
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/properties/load")
	@ResponseBody
	public Properties loadProperties(String id) {
		return propertiesService.findById(id);
	}
}

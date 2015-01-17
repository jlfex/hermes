package com.jlfex.hermes.console;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.utils.Files;
import com.jlfex.hermes.common.utils.Images;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.TextService;

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
	@Autowired
	private TextService textService;

	/**
	 * 系统属性
	 * 
	 * @return
	 */
	@RequestMapping("/properties")
	public String properties(Model model) {
		Properties properties = propertiesService.findByCode("app.logo");
		model.addAttribute("logo", textService.loadById(properties.getValue()).getText());
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
	public Result saveProperties(MultipartHttpServletRequest request) {

		MultipartFile file = request.getFile("logo");
		Result<String> result = new Result<String>();
		try {
			propertiesService.saveConfigurableProperties(
					Images.toBase64(Files.getMimeType(file.getOriginalFilename()), file.getBytes()),
					request.getParameter("companyName"), request.getParameter("nickname"),
					request.getParameter("operationName"), request.getParameter("operationAddress"),
					request.getParameter("operationContact"), request.getParameter("website"),
					request.getParameter("copyright"), request.getParameter("icp"), request.getParameter("serviceTel"),
					request.getParameter("serviceEmail"));
		} catch (IOException e) {
			result.setType(com.jlfex.hermes.common.Result.Type.FAILURE);
			result.addMessage(e.getMessage());
		}
		result.setType(com.jlfex.hermes.common.Result.Type.SUCCESS);
		result.addMessage("保存成功");
		return result;
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

	/**
	 * 读取系统logo属性
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/properties/logo")
	@ResponseBody
	public Text loadLogo() {
		Properties properties = propertiesService.findByCode("app.logo");
		return textService.loadById(properties.getValue());
	}
}

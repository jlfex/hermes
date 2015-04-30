package com.jlfex.hermes.console.properties;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.PropertiesService;

/**
 * 属性控制器
 * @author lishunfeng
 */
@Controller
@RequestMapping("/properties")
public class PropertiesController {

	private static final String DICTIONARY_ID = "197b5cd8-7f63-41ce-94ba-235a0280bb25";

	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("status", Dicts.elements(Properties.Status.class).entrySet());
		model.addAttribute("dicts", dictionaryService.findByType(DICTIONARY_ID));
		return "properties/propertiesIndex";
	}
	
	/**
	 * 属性结果列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/propertiesData")
	public String apiConfigdata(Properties properties,String typeId, String page, Model model) {
		try {
			String size = "10";
			Page<Properties> obj = propertiesService.queryByCondition(properties, typeId, page, size);
			model.addAttribute("propertiesList", obj);
		} catch (Exception e) {
			Logger.error("属性列表查询异常:", e);
			model.addAttribute("propertiesList", null);
		}
		return "properties/propertiesData";
	}
	/**
	 * 新增属性配置
	 */
	@RequestMapping("/addProperties")
	public String addProperties(Model model) {
		model.addAttribute("dicts", dictionaryService.findByType(DICTIONARY_ID));
		return "/properties/addProperties";
	}
	
	/**
	 * 处理新增属性配置业务
	 * 
	 */
	@RequestMapping("/handleAddProperties")
	public String handleAddApiConfig(Properties properties, String typeId,RedirectAttributes attr, Model model) {
		try {
			List<Properties> propertiesList = propertiesService.findAllByCode(properties.getCode());
			List<Properties> propList = propertiesService.findAllByName(properties.getName());
			if (propertiesList.size() > 0) {
				attr.addFlashAttribute("msg", "参数编码已存在，不能重复添加，请重新输入");
				return "redirect:/properties/addProperties";
			}else if(propList.size() > 0){
				attr.addFlashAttribute("msg", "参数名称已存在，不能重复添加，请重新输入");
				return "redirect:/properties/addProperties";				
			}else {
				propertiesService.addOrUpdateProperties(properties,typeId);
				attr.addFlashAttribute("msg", "新增参数配置成功");
				return "redirect:/properties/index";
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "新增参数配置失败");
			Logger.error("新增参数配置：", e);
			return "redirect:/properties/addProperties";
		}
	}

	/**
	 * 修改属性配置
	 */
	@RequestMapping("/updateProperties")
	public String updateProperties(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("properties", propertiesService.findById(id));
		model.addAttribute("dicts", dictionaryService.findByType(DICTIONARY_ID));
		return "/properties/updateProperties";
	}

	/**
	 * 修改属性配置业务
	 * 
	 */
	@RequestMapping("/handleUpdateProperties")
	public String handleUpdateProperties(Properties properties,String typeId, RedirectAttributes attr, Model model) {
		try {
			propertiesService.addOrUpdateProperties(properties,typeId);
			attr.addFlashAttribute("msg", "修改参数配置成功");
			return "redirect:/properties/index";		
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "修改参数配置失败");
			Logger.error("修改参数配置：", e);
			return "redirect:/properties/updateProperties";
		}
	}

	/**
	 * 删除参数配置
	 * 
	 */
	@RequestMapping("/delProperties")
	public String delProperties(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		try {
			propertiesService.delProperties(id);
			attr.addFlashAttribute("msg", "删除参数配置成功");
			return "redirect:/properties/index";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "删除参数配置失败");
			Logger.error("删除参数配置失败：", e);
			return "redirect:/properties/index";
		}
	}

}

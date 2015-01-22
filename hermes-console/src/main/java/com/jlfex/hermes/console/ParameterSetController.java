package com.jlfex.hermes.console;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.service.ParameterSetService;
import com.jlfex.hermes.service.pojo.ParameterSetInfo;

/**
 * 
 * @author: lishunfeng
 * @time: 2015年1月6日 下午1:22:17
 */
@Controller
@RequestMapping("/parameter")
public class ParameterSetController {
	@Autowired
	private ParameterSetService parameterSetService;

	/**
	 * 参数设置首页
	 * 
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("names", Dicts.elements(DictionaryType.Name.class).entrySet());
		return "/parameterset/parameterIndex";
	}

	/**
	 * 参数设置查询结果页面
	 * 
	 */
	@RequestMapping("/parameterdata")
	public String loandata(String parameterType, String parameterValue, String page, String size, Model model) {
		model.addAttribute("parameterSet", parameterSetService.findByParameterTypeAndParameterValue(parameterType, parameterValue, page, size));
		return "/parameterset/parameterdata";
	}

	/**
	 * 新增分类页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/addParameter")
	public String addDictionary(Model model) {
		return "/parameterset/addParameter";
	}

	/**
	 * 处理新增参数配置逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/handerAddDictionary")
	public String handerAddDictionary(ParameterSetInfo psVo, RedirectAttributes attr, Model model) {
		try {
			DictionaryType dictionaryType = parameterSetService.findOneById(psVo.getParameterType());
			List<Dictionary> dicList = parameterSetService.findByNameAndType(psVo.getParameterValue(), dictionaryType.getId());
			if (dicList.size() > 0) {
				attr.addFlashAttribute("msg", "参数值已经存在");
				return "redirect:/parameter/addParameter";
			} else if (psVo.getParameterValue().equals("")) {
				attr.addFlashAttribute("msg", "参数值不能为空");
				return "redirect:/parameter/addParameter";
			} else {
				parameterSetService.addParameterSet(psVo);
				attr.addFlashAttribute("msg", "新增参数配置成功");
				return "redirect:/parameter/index";
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "新增参数配置失败");
			Logger.error("新增参数配置失败：", e);
			return "redirect:/parameter/addParameter";
		}
	}

	/**
	 * 编辑参数配置页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/editParameter")
	public String editParameter(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("parameter", parameterSetService.findOne(id));
		return "/parameterset/editParameter";
	}

	/**
	 * 处理逻辑参数配置逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/handerEditParameter")
	public String handerEditParameter(ParameterSetInfo psVo, RedirectAttributes attr, Model model) {
		try {
			DictionaryType dictionaryType = parameterSetService.findOneByName(psVo.getParameterType());
			List<Dictionary> dicList = parameterSetService.findByNameAndType(psVo.getParameterValue(), dictionaryType.getId());
			if (dicList.size() > 0) {
				attr.addFlashAttribute("msg", "参数值已经存在");
				return "redirect:/parameter/editParameter";
			} else if (psVo.getParameterValue().equals("")) {
				attr.addFlashAttribute("msg", "参数值不能为空");
				return "redirect:/parameter/editParameter";
			}
			parameterSetService.updateDictionary(psVo);
			attr.addFlashAttribute("msg", "参数配置修改成功");
			return "redirect:/parameter/index";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "参数配置修改失败");
			Logger.error("参数配置修改失败：", e);
			return "redirect:/parameter/editParameter";
		}
	}

	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	public String switchDictionary(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		Dictionary dictionary = parameterSetService.findOne(id);
		try {
			parameterSetService.switchDictionary(id);
			if (("00").equals(dictionary.getStatus())) {
				attr.addFlashAttribute("msg", "禁用成功");
			} else {
				attr.addFlashAttribute("msg", "启用成功");
			}
		} catch (Exception e) {
			if (("00").equals(dictionary.getStatus())) {
				attr.addFlashAttribute("msg", "禁用失败");
			} else {
				attr.addFlashAttribute("msg", "启用失败");
			}
		}
		return "redirect:/parameter/index";
	}
}
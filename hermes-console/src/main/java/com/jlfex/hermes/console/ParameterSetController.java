package com.jlfex.hermes.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.service.ParameterSetService;
import com.jlfex.hermes.service.pojo.ParameterSetInfo;
import com.jlfex.hermes.service.pojo.ResultVo;

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
	 * 参数设置查询页面
	 * 
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("names", Dicts.elements(DictionaryType.Name.class).entrySet());
		return "parameterset/index";
	}

	/**
	 * 参数设置查询结果页面
	 * 
	 */
	@RequestMapping("/parameterdata")
	public String loandata(String parameterType, String parameterValue, String page, String size, Model model) {
		model.addAttribute("parameterSet", parameterSetService.findByParameterTypeAndParameterValue(parameterType, parameterValue, page, size));
		return "parameterset/parameterdata";
	}

	/**
	 * 新增参数设置
	 */
	@RequestMapping(value = "/addDictionary", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo addDictionary(ParameterSetInfo parameterSetInfo) {
		return parameterSetService.addParameterSet(parameterSetInfo);
	}

	/**
	 * 修改参数设置
	 */
	@RequestMapping(value = "/updateDictionary", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo updateDictionary(@RequestParam(value = "id", required = true) String id, ParameterSetInfo parameterSetInfo) {
		return parameterSetService.updateDictionary(id, parameterSetInfo);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo updateDictionary(@RequestParam(value = "id", required = true) String id, Model model) {
		return parameterSetService.update(id);
	}

	/**
	 * 修改启用禁用状态
	 */
	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo switchDictionary(@RequestParam(value = "id", required = true) String id, Model model, ParameterSetInfo parameterSetInfo) {
		return parameterSetService.switchDictionary(id);
	}
}

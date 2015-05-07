package com.jlfex.hermes.console.dictionary;

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
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.service.DictionaryTypeService;

@Controller
@RequestMapping("/dictionaryType")
public class DictionaryTypeController {
	@Autowired
	private DictionaryTypeService dictionaryTypeService;

	/**
	 * 字典配置首页
	 * 
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("names", Dicts.elements(DictionaryType.Name.class).entrySet());
		return "/dictionarytype/dictionaryTypeIndex";
	}

	/**
	 * 字典配置结果页面
	 * 
	 */
	@RequestMapping("/dictionaryTypeData")
	public String dictionaryTypeData(DictionaryType dictionaryType,String page, Model model) {
		try {
			String size = "10";
			Page<DictionaryType> obj = dictionaryTypeService.queryByCondition(dictionaryType, page, size);
			model.addAttribute("dictList", obj);
		} catch (Exception e) {
			Logger.error("字典列表查询异常:", e);
			model.addAttribute("dictList", null);
		}
		return "dictionarytype/dictionaryTypeData";
	}
	
	/**
	 * 新增字典类型
	 * 
	 */
	@RequestMapping("/addDictionaryType")
	public String addParameterType(Model model) {
		return "/dictionarytype/addDictionaryType";
	}

	/**
	 * 处理新增字典类型业务
	 * 
	 */
	@RequestMapping("/handerAddDictionaryType")
	public String handerAddDictionaryType(DictionaryType dictionaryType,RedirectAttributes attr, Model model) {
		try {
			List<DictionaryType> dicList = dictionaryTypeService.findByCode(dictionaryType.getCode());
			for(DictionaryType dicType:dicList){
				if(dictionaryType.getCode().equals(dicType.getCode())){
					attr.addFlashAttribute("msg", "类型编码已存在，请重新输入");
					return "redirect:/dictionaryType/addDictionaryType";
				}
			}
			dictionaryTypeService.addOrUpdateDictionaryType(dictionaryType);
			attr.addFlashAttribute("msg", "添加字典成功");
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "添加字典失败");
			Logger.error("添加字典失败：", e);
			return "redirect:/dictionaryType/addDictionaryType";
		}
		return "redirect:/dictionaryType/index";			
	}

	/**
	 * 修改字典配置
	 */
	@RequestMapping("/updateDictionaryType")
	public String updateDictionaryType(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("dictionaryType", dictionaryTypeService.findById(id));
		return "/dictionarytype/updateDictionaryType";
	}

	/**
	 * 修改字典配置业务
	 * 
	 */
	@RequestMapping("/handleUpdateDictionaryType")
	public String handleUpdateDictionaryType(DictionaryType dictionaryType,RedirectAttributes attr, Model model) {
		try {
			DictionaryType dicType = dictionaryTypeService.findById(dictionaryType.getId());
			List<DictionaryType> dicTypes = dictionaryTypeService.findByCode(dictionaryType.getCode());
			if(dictionaryType.getCode().equals(dicType.getCode())){
				dictionaryTypeService.addOrUpdateDictionaryType(dictionaryType);
				attr.addFlashAttribute("msg", "修改字典成功");
				return "redirect:/dictionaryType/index";		
			}else if(dicTypes.size() > 0){
				attr.addFlashAttribute("msg", "类型编码已存在，请重新输入");
				return "redirect:/dictionaryType/updateDictionaryType?id="+dictionaryType.getId();		
			}else{
				dictionaryTypeService.addOrUpdateDictionaryType(dictionaryType);
				attr.addFlashAttribute("msg", "修改字典成功");
				return "redirect:/dictionaryType/index";		
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "修改字典失败");
			Logger.error("修改字典：", e);
			return "redirect:/dictionaryType/updateDictionaryType";
		}
	}

	/**
	 * 删除字典配置
	 * 
	 */
	@RequestMapping("/delDictionaryType")
	public String delDictionaryType(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		try {
			dictionaryTypeService.delDictionaryType(id);
			attr.addFlashAttribute("msg", "删除字典成功");
			return "redirect:/dictionaryType/index";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "请先删除对应的字典项");
			Logger.error("删除字典失败：", e);
			return "redirect:/dictionaryType/index";
		}
	}

}

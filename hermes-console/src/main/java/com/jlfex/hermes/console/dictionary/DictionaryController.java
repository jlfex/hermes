package com.jlfex.hermes.console.dictionary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.DictionaryType;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.DictionaryTypeService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private DictionaryTypeService dictionaryTypeService;

	/**
	 * 字典项配置首页
	 * 
	 */
	@RequestMapping("/index")
	public String index(@RequestParam(value = "id", required = true) String id,Model model) {
		model.addAttribute("dictId", id);
		return "/dictionary/dictionaryIndex";
	}

	/**
	 * 字典详情结果列表
	 * 
	 */
	@RequestMapping("/dictionaryData")
	public String dictionaryData(@RequestParam(value = "id", required = true) String id,Dictionary dictionary,String page, Model model) {
		try {
			String size = "10";
			Page<Dictionary> obj = dictionaryService.queryByCondition(dictionary,id,page, size);
			model.addAttribute("dictList", obj);
		} catch (Exception e) {
			Logger.error("字典列表查询异常:", e);
			model.addAttribute("dictList", null);
		}
		return "dictionary/dictionaryData";
	}

	/**
	 * 新增字典项
	 * 
	 */
	@RequestMapping("/addDictionary")
	public String addDictionary(@RequestParam(value = "id", required = true) String id,Model model) {
		model.addAttribute("dictionaryType", dictionaryTypeService.findById(id));
		return "/dictionary/addDictionary";
	}

	/**
	 * 处理新增字典项业务
	 * 
	 */
	@RequestMapping("/handerAddDictionary")
	public String handerAddDictionary(Dictionary dictionary,String typeId,RedirectAttributes attr, Model model) {
		DictionaryType dicType = dictionaryTypeService.findById(typeId);
		try {
			List<Dictionary> dicList = dictionaryService.findByCode(dictionary.getCode());
			if(dicList.size() > 0){
				attr.addFlashAttribute("msg", "字典项编码已存在，请重新输入");
				return "redirect:/dictionary/addDictionary?id="+dicType.getId();				
			}else{
				dictionaryService.addOrUpdateDictionary(dictionary,typeId);
				attr.addFlashAttribute("msg", "添加字典项成功");			
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "添加字典项失败");
			Logger.error("添加字典项失败：", e);
			return "redirect:/dictionary/addDictionary?id="+dicType.getId();
		}
		return "redirect:/dictionary/index?id="+dicType.getId();			
	}

	/**
	 * 修改字典项页面
	 */
	@RequestMapping("/updateDictionary")
	public String updateDictionary(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("dictionary", dictionaryService.loadById(id));
		model.addAttribute("dictionaryType", dictionaryService.loadById(id).getType());
		return "/dictionary/updateDictionary";
	}

	/**
	 * 修改字典项业务
	 * 
	 */
	@RequestMapping("/handleUpdateDictionary")
	public String handleUpdateDictionary(Dictionary dictionary,RedirectAttributes attr, Model model) {
		Dictionary dict = dictionaryService.loadById(dictionary.getId());
		try {
			List<Dictionary> dicTypes = dictionaryService.findByCode(dictionary.getCode());
			if(dictionary.getCode().equals(dict.getCode())){
				dictionaryService.addOrUpdateDictionary(dictionary,null);
				attr.addFlashAttribute("msg", "修改字典项成功");
				return "redirect:/dictionary/index?id="+dict.getType().getId();		
			}else if(dicTypes.size() > 0){
				attr.addFlashAttribute("msg", "字典项编码已存在，请重新输入");
				return "redirect:/dictionary/updateDictionary?id="+dict.getId();		
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "修改字典项失败");
			Logger.error("修改字典项：", e);
			return "redirect:/dictionary/updateDictionary?id="+dict.getId();
		}
		return null;
	}

	/**
	 * 删除字典项
	 * 
	 */
	@RequestMapping("/delDictionary")
	public String delDictionary(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		String typeId = dictionaryService.loadById(id).getType().getId();
		try {
			dictionaryService.delDictionary(id);
			attr.addFlashAttribute("msg", "删除字典项成功");
			return "redirect:/dictionary/index?id="+typeId;
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "删除字典项失败");
			Logger.error("删除字典失败：", e);
			return "redirect:/dictionary/index?id="+typeId;
		}
	}

	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	public String switchDictionary(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		Dictionary dictionary = dictionaryService.loadById(id);
		try {
			dictionaryService.switchDictionary(id);
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
		return "redirect:/dictionary/index?id="+dictionary.getType().getId();
	}
}

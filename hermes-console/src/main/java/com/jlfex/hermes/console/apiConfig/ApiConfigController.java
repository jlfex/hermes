package com.jlfex.hermes.console.apiConfig;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.service.apiconfig.ApiConfigService;
import com.jlfex.hermes.service.pojo.yltx.ApiConfigVo;

/**
 * 接口控制器
 * @author lishunfeng
 */
@Controller
@RequestMapping("/apiConfig")
public class ApiConfigController {
	@Autowired
	private ApiConfigService apiConfigService;

	@RequestMapping("/index")
	public String index(Model model) {
		return "apiConfig/apiConfigIndex";
	}
	/**
	 * 接口结果列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/apiConfigData")
	public String apiConfigdata(ApiConfigVo apiConfigVo,String page, Model model) {
		try {
			String size = "10";
			Page<ApiConfig> obj = apiConfigService.queryByCondition(apiConfigVo, page, size);
			model.addAttribute("apiConfigList", obj);
		} catch (Exception e) {
			Logger.error("接口列表查询异常:", e);
			model.addAttribute("apiConfigList", null);
		}
		return "apiConfig/apiConfigData";
	}
	/**
	 * 新增接口配置
	 */
	@RequestMapping("/addApiConfig")
	public String addApiConfig(Model model) {
		return "/apiConfig/addApiConfig";
	}
	/**
	 * 新增接口业务
	 * 
	 */
	@RequestMapping("/handleAddApiConfig")
	public String handleAddApiConfig(ApiConfigVo apiConfigVo, RedirectAttributes attr, Model model) {
		try {
			List<ApiConfig> apiConfigList = apiConfigService.findByPlatCode(apiConfigVo.getPlatCode());
			if (apiConfigList.size() > 0) {
				attr.addFlashAttribute("msg", "平台名称已经存在,请重新添加");
				return "redirect:/apiConfig/addApiConfig";
			}else {
				apiConfigService.addOrUpdateApiConfig(apiConfigVo);
				attr.addFlashAttribute("msg", "新增接口配置成功");
				return "redirect:/apiConfig/index";
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "添加接口配置失败");
			Logger.error("添加接口配置：", e);
			return "redirect:/apiConfig/addApiConfig";
		}
	}

	/**
	 * 修改接口配置
	 */
	@RequestMapping("/updateApiConfig")
	public String updateApiConfig(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("apiConfig", apiConfigService.findById(id));
		return "/apiConfig/updateApiConfig";
	}

	/**
	 * 修改接口业务
	 * 
	 */
	@RequestMapping("/handleUpdateApiConfig")
	public String handleUpdateApiConfig(ApiConfigVo apiConfigVo, RedirectAttributes attr, Model model) {
		try {
			apiConfigService.addOrUpdateApiConfig(apiConfigVo);
			attr.addFlashAttribute("msg", "修改接口配置成功");
			return "redirect:/apiConfig/index";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "修改接口配置失败");
			Logger.error("修改接口配置：", e);
			return "redirect:/apiConfig/updateApiConfig";
		}
	}
	/**
	 * 删除接口
	 * 
	 */
	@RequestMapping("/delApiConfig")
	public String delApiConfig(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		try {
			ApiConfig apiConfig = apiConfigService.findById(id);
			if(apiConfig!=null && (
			  HermesConstants.PLAT_JLFEX_CODE.equals(apiConfig.getPlatCode())||
			  HermesConstants.PLAT_ZJ_CODE.equals(apiConfig.getPlatCode())) ){
				attr.addFlashAttribute("msg", "初始化数据不允许删除");
			}else{
				apiConfigService.delApiConfig(id);
				attr.addFlashAttribute("msg", "删除接口成功");
			}
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "删除接口失败");
			Logger.error("删除接口失败：", e);
		}
		return "redirect:/apiConfig/index";
	}

}

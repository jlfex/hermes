package com.jlfex.hermes.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.pojo.ContentCategory;
import com.jlfex.hermes.service.pojo.FriendLinkVo;
import com.jlfex.hermes.service.pojo.PublishContentVo;
import com.jlfex.hermes.service.pojo.ResultVo;
import com.jlfex.hermes.service.pojo.TmpNoticeVo;

/**
 * @author admin 内容管理控制器
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 分类管理结果页面
	 * 
	 */
	@RequestMapping("/categoryIndex")
	public String categoryIndex(Model model) {
		model.addAttribute("categories", contentService.findByLevelNotNull());
		return "/content/categoryIndex";
	}

	/**
	 * 删除分类
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/deleteCategory")
	@ResponseBody
	public ResultVo deleteCategory(@RequestParam(value = "id", required = true) String id, Model model) {
		return contentService.deleteCategory(id);
	}

	/**
	 * 跳转至编辑分类页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/editCategory")
	public String editCategory(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("category", contentService.findOne(id));
		model.addAttribute("categoryForLevel1", contentService.findCategoryByLevel("一级"));
		return "/content/editCategory";
	}

	/**
	 * 处理编辑分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/updateCategory")
	@ResponseBody
	public ResultVo updateCategory(ContentCategory contentCategory) {
		return contentService.updateCategory(contentCategory);
	}

	/**
	 * 跳转至新增分类页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/addCategory")
	public String addCategory(Model model) {
		return "/content/addCategory";
	}

	/**
	 * 处理新增分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/insertCategory")
	@ResponseBody
	public ResultVo insertCategory(ContentCategory contentCategory) {
		return contentService.insertCategory(contentCategory);
	}

	/**
	 * 根据类型查找分类
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/findCategoryByLevel")
	@ResponseBody
	public List<ArticleCategory> findCategoryByLevel(@RequestParam("level") String level) {
		return contentService.findCategoryByLevel(level);
	}

	/**
	 * 根据父类查找子类
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/findCategoryByParent")
	@ResponseBody
	public List<ArticleCategory> findCategoryByParent(@RequestParam("level") String level, @RequestParam("parentCode") String parentCode, @RequestParam(required = true) String parentId) {
		return contentService.findByParentId(parentId);
	}

	@RequestMapping("/saveAddedCategory")
	public String saveAddedCategory(ContentCategory category, Model model) {
		try {
			contentService.addCategory(category);
		} catch (Exception e) {

		}
		return "redirect:/content/categoryIndex";
	}

	/**
	 * 内容管理首页
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/contentIndex")
	public String index(String levelOne, String levelTwo, Model model) {
		model.addAttribute("categoryForLevel1", contentService.findCategoryByLevel("一级"));
		model.addAttribute("categoryForLevel2", contentService.findByParentId(levelOne));
		model.addAttribute("categoryForLevel3", contentService.findByParentId(levelTwo));
		return "/content/contentIndex";
	}

	/**
	 * 内容管理查询数据结果页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/contentData")
	public String productData(String levelOne, String levelTwo, String levelThree, String inputName, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
		model.addAttribute("contentData", contentService.find(levelOne, levelTwo, levelThree, inputName, page, size));
		return "/content/contentData";
	}

	/**
	 * 发布内容页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/publish")
	public String publish(String levelOne, String levelTwo, Model model) {
		model.addAttribute("categoryForLevel1", contentService.findCategoryByLevel("一级"));
		model.addAttribute("categoryForLevel2", contentService.findByParentId(levelOne));
		model.addAttribute("categoryForLevel3", contentService.findByParentId(levelTwo));
		return "/content/addPublish";
	}

	/**
	 * 点击发布页面提交按钮处理逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/addPublish")
	public String addPublish(PublishContentVo pcVo, RedirectAttributes attr, Model model) {
		try {
			contentService.addPublish(pcVo);
			attr.addFlashAttribute("msg", "发布内容成功");
			return "redirect:/content/publish";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "发布内容失败");
			return "redirect:/content/addPublish";
		}
	}

	/**
	 * 提交并继续发布处理逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/addPublishAgain")
	public String addPublishAgain(PublishContentVo pcVo, RedirectAttributes attr, Model model) {
		try {
			contentService.addPublish(pcVo);
			attr.addFlashAttribute("msg", "发布内容成功");
			return "redirect:/content/addPublish";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "发布内容失败");
			return "redirect:/content/addPublish";
		}
	}

	/**
	 * 编辑内容页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/editContent")
	public String editContent(@RequestParam(value = "id", required = true) String id, String levelOne, String levelTwo, Model model) {
		model.addAttribute("article", contentService.findOneById(id));
		model.addAttribute("categoryForLevel1", contentService.findCategoryByLevel("一级"));
		model.addAttribute("categoryForLevel2", contentService.findByParentId(levelOne));
		model.addAttribute("categoryForLevel3", contentService.findByParentId(levelTwo));
		return "/content/editContent";
	}

	/**
	 * 点击编辑内容页面保存按钮处理逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/handerEditContent")
	public String handerEditContent(PublishContentVo pcVo, RedirectAttributes attr, Model model) {
		try {
			contentService.updateContent(pcVo);
			attr.addFlashAttribute("msg", "内容修改成功");
			return "redirect:/content/contentIndex";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "内容修改失败");
			return "redirect:/content/editContent";
		}
	}

	/**
	 * 删除内容
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/deleteContent")
	public String deleteContent(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		try {
			contentService.deleteContent(id);
			attr.addFlashAttribute("msg", "删除内容成功");
			return "redirect:/content/contentIndex";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "删除内容失败");
			return "redirect:/content/contentIndex";
		}
	}

	/**
	 * 批量删除内容
	 * 
	 * @author lishunfeng
	 */

	@RequestMapping("/batchDeleteContent")
	public String batchDeleteContent(@RequestParam(value = "ids", required = true) String ids, RedirectAttributes attr, Model model) {
		try {
			contentService.batchDeleteContent(ids);
			attr.addFlashAttribute("msg", "批量删除成功");
			return "redirect:/content/contentIndex";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "批量删除失败");
			return "redirect:/content/contentIndex";
		}
	}

	/**
	 * 友情链接结果页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/friendLink")
	public String friendLinkData(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
		model.addAttribute("friendLinks", contentService.findAll(page, size));
		return "/content/friendLinkIndex";
	}

	/**
	 * 新增友情链接
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/addFriendLink")
	public String addFriendLink(Model model) {
		return "/content/addFriendLink";
	}

	/**
	 * 点击添加链接页面保存按钮处理逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/handerAddFriendLink")
	public String handerAddFriendLink(FriendLinkVo flVo, RedirectAttributes attr, Model model) {
		try {
			contentService.addFriendLink(flVo);
			attr.addFlashAttribute("msg", "添加链接成功");
			return "redirect:/content/friendLink";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "添加链接失败");
			return "redirect:/content/addFriendLink";
		}
	}

	/**
	 * 编辑友情链接页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/editFriendLink")
	public String editFriendLink(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("friendLink", contentService.findOneBy(id));
		return "/content/editFriendLink";
	}

	/**
	 * 点击编辑友情链接页面保存按钮处理逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/handerEditFriendLink")
	public String handerEditFriendLink(FriendLinkVo flVo, RedirectAttributes attr, Model model) {
		try {
			contentService.updateFriendLink(flVo);
			attr.addFlashAttribute("msg", "友情链接修改成功");
			return "redirect:/content/friendLink";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "友情链接修改失败");
			return "redirect:/content/editFriendLink";
		}
	}

	/**
	 * 删除友情链接
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/deleteFriendLink")
	public String deleteFriendLink(@RequestParam(value = "id", required = true) String id, RedirectAttributes attr, Model model) {
		try {
			contentService.deleteFriendLink(id);
			attr.addFlashAttribute("msg", "删除友情链接成功");
			return "redirect:/content/friendLink";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "删除友情链接失败");
			return "redirect:/content/friendLink";
		}
	}

	/**
	 * 临时公告结果页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/tmpNotice")
	public String tmpNoticeData(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
		model.addAttribute("tmpNotices", contentService.findAllTmpNotices(page, size));
		return "/content/tmpNoticeIndex";
	}

	/**
	 * 编辑临时公告页面
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/editTmpNotice")
	public String editTmpNotice(@RequestParam(value = "id", required = true) String id, Model model) {
		model.addAttribute("tmpNotice", contentService.findOneByTmpNoticeId(id));
		return "/content/editTmpNotice";
	}

	/**
	 * 编辑临时公告处理逻辑
	 * 
	 * @author lishunfeng
	 */
	@RequestMapping("/handerEditTmpNotice")
	public String handerEditTmpNotice(TmpNoticeVo tnVo, RedirectAttributes attr, Model model) {
		try {
			contentService.updateTmpNotice(tnVo);
			attr.addFlashAttribute("msg", "临时公告修改成功");
			return "redirect:/content/tmpNotice";
		} catch (Exception e) {
			attr.addFlashAttribute("msg", "临时公告修改失败");
			return "redirect:/content/editTmpNotice";
		}
	}

	@RequestMapping("/banner")
	public String banner(Model model) {

		return "/content/banner";
	}

	@RequestMapping("/addBanner")
	public String addBanner(Model model) {

		return "/content/addBanner";
	}

}

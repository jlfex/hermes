package com.jlfex.hermes.console.product;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.pojo.SimpleProduct;

@Controller
@RequestMapping("/product")
public class ProductManageController {
	@Autowired
	private ProductService productService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private RepayService repayService;

	/**
	 * 产品管理首页
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("purpose", dictionaryService.findByTypeCode("product.purpose"));
		return "product/index";
	}

	/**
	 * 产品查询数据结果页面
	 */
	@RequestMapping("/data")
	public String productData(String code, String name, String purpose, String status, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
		model.addAttribute("data", productService.find(code, name, purpose, status, page, size));
		return "product/data";
	}

	/**
	 * 跳转新增产品信息页面
	 */
	@RequestMapping("/add")
	public String save(Product product, Model model) {
		model.addAttribute("title", "新增产品");
		model.addAttribute("guarantee", dictionaryService.findByTypeCode("product.guarantee"));
		model.addAttribute("purpose", dictionaryService.findByTypeCode("loan_purpose"));
		model.addAttribute("repay", repayService.findAll());
		model.addAttribute("deadline", dictionaryService.findByTypeCode("product.deadline"));
		model.addAttribute("productCode", productService.generateProductCode());
		return "product/add";
	}

	/**
	 * 新增产品处理（保存到数据库）
	 */
	@RequestMapping("/doadd")
	public String doadd(SimpleProduct product, RedirectAttributes attr, String id) {
		try {
			Product p = null;
			String msg = null;
			Product cp = productService.loadByCode(product.getCode());
			if (StringUtils.isNotEmpty(id)) {
				p = productService.loadById(id);
				if (cp != null && !cp.getId().equals(p.getId())) {
					attr.addFlashAttribute("msg", "该产品已存在，无法继续添加");
					return "redirect:/product/add";
				}
				msg = "修改产品成功";
			} else {
				if (cp != null) {
					attr.addFlashAttribute("msg", "该产品已存在，无法继续添加");
					return "redirect:/product/add";
				}
				msg = "添加产品成功";
				p = new Product();
				p.setStatus(Product.Status.VALID);
				// 产品管理费
				p.setManageFee(new BigDecimal(HermesConstants.PRODUCT_MANAGE_FEE));
				p.setManageFeeType(HermesConstants.PRODUCT_MANAGE_FEE_TYPE_ZERO_ZERO);
			}
			productService.editProduct(p, product);

			attr.addFlashAttribute("msg", msg);
			return "redirect:/product/index";
		} catch (Exception e) {
			Logger.error("新增产品异常", e);
			if (StringUtils.isNotEmpty(id)) {
				attr.addFlashAttribute("msg", "修改产品失败");
				return "redirect:/product/detail/" + id;
			} else {
				attr.addFlashAttribute("msg", "添加产品失败");
				return "redirect:/product/add";
			}
		}
	}

	/**
	 * 产品详情
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		model.addAttribute("title", "产品详情");
		model.addAttribute("guarantee", dictionaryService.findByTypeCode("product.guarantee"));
		model.addAttribute("purpose", dictionaryService.findByTypeCode("loan_purpose"));
		model.addAttribute("repay", repayService.findAll());
		model.addAttribute("prodtl", productService.loadById(id));
		model.addAttribute("deadline", dictionaryService.findByTypeCode("product.deadline"));
		return "product/add";
	}
}

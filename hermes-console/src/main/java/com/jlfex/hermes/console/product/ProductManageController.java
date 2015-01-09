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

import com.jlfex.hermes.console.pojo.SimpleProduct;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.RepayService;

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
	public String productData(String code, String name, String purpose, String status, @RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
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
		model.addAttribute("purpose", dictionaryService.findByTypeCode("product.purpose"));
		model.addAttribute("repay", repayService.findAll());
		model.addAttribute("deadline", dictionaryService.findByTypeCode("product.deadline"));
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
			}
			p.setCode(product.getCode());
			p.setName(product.getName());
			p.setAmount(product.getAmount());
			p.setPeriod(product.getPeriod());
			p.setRate(product.getRate());
			p.setDeadline(new Integer(product.getDeadline()));
			if (StringUtils.isNotEmpty(product.getGuaranteeId())) {
				p.setGuarantee(dictionaryService.loadById(product.getGuaranteeId()));
			}
			p.setRepay(repayService.loadById(product.getRepayId()));
			p.setPurpose(dictionaryService.loadById(product.getPurposeId()));
			p.setStartingAmt(new BigDecimal(product.getStartingAmt()));
			p.setDescription(product.getDescription());
			p.setPeriodType(product.getPeriodType());
			productService.save(p);

			// 为产品添加 “借款手续费费率”，“风险金费率”
			Properties ploan = propertiesService.findByCode("product.rate.loan");
			Properties prisk = propertiesService.findByCode("product.rate.risk");
			Rate r1 = new Rate();
			r1.setType(Rate.RateType.LOAN);
			r1.setProduct(p);
			r1.setRate(new BigDecimal(ploan.getValue()));

			Rate r2 = new Rate();
			r2.setType(Rate.RateType.RISK);
			r2.setProduct(p);
			r2.setRate(new BigDecimal(prisk.getValue()));

			rateRepository.save(r1);
			rateRepository.save(r2);

			attr.addFlashAttribute("msg", msg);
			return "redirect:/product/index";
		} catch (Exception e) {
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
		model.addAttribute("purpose", dictionaryService.findByTypeCode("product.purpose"));
		model.addAttribute("repay", repayService.findAll());
		model.addAttribute("prodtl", productService.loadById(id));
		model.addAttribute("deadline", dictionaryService.findByTypeCode("product.deadline"));
		return "product/add";
	}
}

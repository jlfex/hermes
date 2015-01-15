package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.ProductRepository;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.repository.RepayRepository;
import com.jlfex.hermes.service.DictionaryService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.pojo.ProductInfo;
import com.jlfex.hermes.service.pojo.SimpleProduct;

/**
 * 
 * 产品业务实现
 * 
 * @author Ray
 * @version 1.0, 2013-12-23
 * @since 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final String CACHE_PRODUCT = "com.jlfex.hermes.cache.products";

	/** 产品仓库 */
	@Autowired
	private ProductRepository productRepository;

	/** 还款方式仓库 */
	@Autowired
	private RepayRepository repayRepository;

	/** 字典仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private RepayService repayService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.ProductService#findAll()
	 */
	@Override
	public List<ProductInfo> findAll() {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		Iterable<Product> productList = productRepository.findAll();
		List<Repay> repayList = repayRepository.findAll();
		List<Dictionary> loanUseList = dictionaryRepository.findByTypeCode("loan_purpose");
		ProductInfo productInfo = null;
		for (Product product : productList) {
			productInfo = new ProductInfo();
			productInfo.setId(product.getId());
			productInfo.setName(product.getName());

			String minAmount = product.getAmount().substring(0, product.getAmount().indexOf(","));
			String maxAmount = product.getAmount().substring(product.getAmount().indexOf(",") + 1);
			productInfo.setMinAmount(minAmount);
			productInfo.setMaxAmount(maxAmount);
			productInfo.setAmount(String.valueOf((Long.valueOf(maxAmount) - Long.valueOf(minAmount))));

			String minPeriod = product.getPeriod().substring(0, product.getPeriod().indexOf(","));
			String maxPeriod = product.getPeriod().substring(product.getPeriod().indexOf(",") + 1);
			productInfo.setMinPeriod(minPeriod);
			productInfo.setMaxPeriod(maxPeriod);
			productInfo.setPeriod(String.valueOf((Double.valueOf(maxPeriod) - Double.valueOf(minPeriod))));

			String minRate = product.getRate().substring(0, product.getRate().indexOf(","));
			String maxRate = product.getRate().substring(product.getRate().indexOf(",") + 1);
			productInfo.setMinRate(minRate);
			productInfo.setMaxRate(maxRate);
			productInfo.setRate(String.valueOf((Double.valueOf(maxPeriod) - Double.valueOf(minRate))));

			productInfo.setLoanUse(loanUseList);
			productInfo.setRepayMethod(repayList);
			productInfo.setDescription(product.getDescription());
			productInfoList.add(productInfo);
		}
		return productInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.ProductService#loadById(java.lang.String)
	 */
	@Override
	public Product loadById(String id) {
		return productRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.ProductService#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> getAll() {
		List<ProductInfo> productInfos = Caches.get(CACHE_PRODUCT, List.class);
		if (productInfos == null) {
			productInfos = findAll();
			if (productInfos != null)
				Caches.add(CACHE_PRODUCT, productInfos);
		}
		return productInfos;
	}

	@Override
	public Page<Product> find(final String code, final String name, final String purpose, final String status, final int page, final int size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		Page<Product> productList = productRepository.findAll(new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> p = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(code)) {
					p.add(cb.equal(root.get("code"), code));
				}
				if (StringUtils.isNotEmpty(name)) {
					p.add(cb.equal(root.get("name"), name));
				}
				if (StringUtils.isNotEmpty(purpose)) {
					p.add(cb.equal(root.get("purpose").get("code"), purpose));
				}
				if (StringUtils.isNotEmpty(status)) {
					p.add(cb.equal(root.get("status"), status));
				}
				return cb.and(p.toArray(new Predicate[p.size()]));
			}
		}, pageable);
		return productList;
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product loadByCode(final String code) {
		return productRepository.findOne(new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return cb.equal(root.get("code"), code);
			}
		});
	}

	@Override
	public Product editProduct(Product p, SimpleProduct product) {
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
		p = save(p);

		// 为产品添加 “借款手续费费率”，“风险金费率”
		Properties ploan = propertiesService.findByCode("product.rate.loan");
		Properties prisk = propertiesService.findByCode("product.rate.risk");

		// 获取借款利率对象
		Rate rateLoan = rateRepository.findByProductAndType(p, Rate.RateType.LOAN);
		if (rateLoan == null) {
			Rate r1 = new Rate();
			r1.setType(Rate.RateType.LOAN);
			r1.setProduct(p);
			r1.setRate(new BigDecimal(ploan.getValue()));
			rateRepository.save(r1);
		} else {
			rateLoan.setRate(new BigDecimal(ploan.getValue()));
			rateRepository.save(rateLoan);
		}
		// 获取风险金 利率对象
		Rate rateRisk = rateRepository.findByProductAndType(p, Rate.RateType.RISK);
		if (rateRisk == null) {
			Rate r2 = new Rate();
			r2.setType(Rate.RateType.RISK);
			r2.setProduct(p);
			r2.setRate(new BigDecimal(prisk.getValue()));
			rateRepository.save(r2);
		} else {
			rateRisk.setRate(new BigDecimal(prisk.getValue()));
			rateRepository.save(rateRisk);
		}
		return p;
	}

}

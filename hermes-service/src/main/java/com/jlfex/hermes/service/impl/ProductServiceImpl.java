package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.AppUser;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.HermesConstants;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.ProductOverdue;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.ProductOverdueRepository;
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
@Transactional
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
	@Autowired
	private ProductOverdueRepository productOverdueRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.ProductService#findAll()
	 */
	@Override
	public List<ProductInfo> findAll() {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		List validState = new ArrayList();
		validState.add(Product.Status.VALID);
		List<Product>  sortProductList = new ArrayList<Product>();
		Iterable<Product> productList = productRepository.findByStatusIn(validState);
		Iterator iterator = productList.iterator();
		while(iterator.hasNext()){
			sortProductList.add((Product)iterator.next());
		}
		Collections.sort(sortProductList, new Comparator<Product>() {
            public int compare(Product obj1, Product obj2) {
            	return obj1.getCreateTime().before(obj2.getCreateTime())? 1:-1;
            }
        }); 
		ProductInfo productInfo = null;
		List<Repay> repayList = repayRepository.findAll();
		List<Dictionary> loanUseList = dictionaryRepository.findByTypeCodeAndStatus("loan_purpose", "00");
		int displaySequence = 1; //20150211需求：固定显示五个产品
		int productSize = 0;
		if(sortProductList!=null && sortProductList.size() > 0){
			productSize = sortProductList.size();
		}
		for(Product product : sortProductList){
			if(displaySequence >5){
				break;
			}
			displaySequence ++;
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

	/**
	 * 根据 状态 获取产品信息
	 */
	@Override
	public List<Product> findByStatusIn(String... status) throws Exception {
		return productRepository.findByStatusIn(Arrays.asList(status));
	}

	@Override
	public Product editProduct(Product obj, SimpleProduct product) throws Exception {
		obj.setCode(product.getCode());
		obj.setName(product.getName());
		obj.setAmount(product.getAmount());
		obj.setPeriod(product.getPeriod());
		obj.setRate(product.getRate());
		obj.setDeadline(new Integer(product.getDeadline()));
		if (StringUtils.isNotEmpty(product.getGuaranteeId())) {
			obj.setGuarantee(dictionaryService.loadById(product.getGuaranteeId()));
		}
		obj.setRepay(repayService.loadById(product.getRepayId()));
		obj.setPurpose(dictionaryService.loadById(product.getPurposeId()));
		obj.setStartingAmt(new BigDecimal(product.getStartingAmt()));
		obj.setDescription(product.getDescription());
		obj.setPeriodType(product.getPeriodType());
		Product entity = save(obj);
		setProductLoanRate(entity);
		setProductRiskRate(entity);
		setProductOverGrad(entity);
		return entity;
	}
	
	/**
	 * 新增产品产品  设置: 借款手续费费率
	 * @param product
	 */
	public void  setProductLoanRate(Product product) throws Exception{
		Properties ploan = propertiesService.findByCode("product.rate.loan");
		if(ploan == null){
			throw new Exception("新增产品： 借款手续费费率系统属性表 product.rate.loan 没有配置.");
		}
		Rate rateLoan = rateRepository.findByProductAndType(product, Rate.RateType.LOAN);
		if(rateLoan == null){
			Rate rate = new Rate();
			rate.setType(Rate.RateType.LOAN);
			rate.setProduct(product);
			rate.setRate(new BigDecimal(ploan.getValue()));
			rateRepository.save(rate);
		}else{
			rateLoan.setRate(new BigDecimal(ploan.getValue()));
			rateRepository.save(rateLoan);
		}
	}
	/**
	 * 新增产品产品  设置: 风险金费率
	 * @param product
	 */
	public void  setProductRiskRate(Product product) throws Exception{
		Properties prisk = propertiesService.findByCode("product.rate.risk");
		if(prisk == null){
			throw new Exception("新增产品： 风险金费率系统属性表 product.rate.risk 没有配置.");
		}
		Rate rateRisk = rateRepository.findByProductAndType(product, Rate.RateType.RISK);
		if (rateRisk == null) {
			Rate rate = new Rate();
			rate.setType(Rate.RateType.RISK);
			rate.setProduct(product);
			rate.setRate(new BigDecimal(prisk.getValue().trim()));
			rateRepository.save(rate);
		} else {
			rateRisk.setRate(new BigDecimal(prisk.getValue().trim()));
			rateRepository.save(rateRisk);
		}
	}
	/**
	 * 新增产品产品  设置: 罚息梯度 
	 * 当前:罚息设置三个梯度
	 * 逾期：1-30 天  梯度1  31--60 梯度2   超过60天 ：梯度0
	 * @param product
	 */
	public void setProductOverGrad(Product product) throws Exception{
		List<ProductOverdue> list = new ArrayList<ProductOverdue>();
		ProductOverdue  obj = null ;
		for(int i=0; i<3; i++){
			obj = new ProductOverdue();
			obj.setCreator(App.current().getUser().getId());
			obj.setProduct(product);
			obj.setRank(i);
			if(i == 0){
				obj.setInterest(new BigDecimal(HermesConstants.PRODUCT_OVERDU_INTEREST_FEE0));
				obj.setPenalty(new BigDecimal(HermesConstants.PRODUCT_OVERDU_PENALTY_FEE0));
			}else if(i == 1){
				obj.setInterest(new BigDecimal(HermesConstants.PRODUCT_OVERDU_INTEREST_FEE1));
				obj.setPenalty(new BigDecimal(HermesConstants.PRODUCT_OVERDU_PENALTY_FEE1));
			}else{
				obj.setInterest(new BigDecimal(HermesConstants.PRODUCT_OVERDU_INTEREST_FEE2));
				obj.setPenalty(new BigDecimal(HermesConstants.PRODUCT_OVERDU_PENALTY_FEE2));
			}
			list.add(obj);
		}
		productOverdueRepository.save(list);
	}

	@Override
	public Long countProductNum() {

		return productRepository.countProductNum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.ProductService#generateProductCode()
	 */
	@Override
	public String generateProductCode() {
		Long count = countProductNum() + 1;
		return StringUtils.leftPad(count.toString(), 6, "0");
	}
}

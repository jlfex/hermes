package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.model.Dictionary;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.repository.DictionaryRepository;
import com.jlfex.hermes.repository.ProductRepository;
import com.jlfex.hermes.repository.RepayRepository;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.pojo.ProductInfo;

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
	
	private static final String CACHE_PRODUCT	= "com.jlfex.hermes.cache.products";
	
	/** 产品仓库 */
	@Autowired
	private ProductRepository productRepository;
	
	/** 还款方式仓库 */
	@Autowired
	private RepayRepository repayRepository;
	
	/** 字典仓库 */
	@Autowired
	private DictionaryRepository dictionaryRepository;

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.ProductService#findAll()
	 */
	@Override
	public List<ProductInfo> findAll() {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		List<Product> productList = productRepository.findAll();
		List<Repay> repayList = repayRepository.findAll();
		List<Dictionary>  loanUseList = dictionaryRepository.findByTypeCode("loan_purpose");
		ProductInfo productInfo =null;
		for(Product product : productList){
			productInfo = new ProductInfo();
			productInfo.setId(product.getId());
			productInfo.setName(product.getName());
			
			String minAmount = product.getAmount().substring(0,product.getAmount().indexOf(","));
			String maxAmount = product.getAmount().substring(product.getAmount().indexOf(",")+1);
			productInfo.setMinAmount(minAmount);
			productInfo.setMaxAmount(maxAmount);
			productInfo.setAmount(String.valueOf((Long.valueOf(maxAmount)-Long.valueOf(minAmount))));
			
			String minPeriod = product.getPeriod().substring(0,product.getPeriod().indexOf(","));
			String maxPeriod = product.getPeriod().substring(product.getPeriod().indexOf(",")+1);
			productInfo.setMinPeriod(minPeriod);
			productInfo.setMaxPeriod(maxPeriod);
			productInfo.setPeriod(String.valueOf((Double.valueOf(maxPeriod)-Double.valueOf(minPeriod))));
			
			String minRate = product.getRate().substring(0,product.getRate().indexOf(","));
			String maxRate = product.getRate().substring(product.getRate().indexOf(",")+1);
			productInfo.setMinRate(minRate);
			productInfo.setMaxRate(maxRate);
			productInfo.setRate(String.valueOf((Double.valueOf(maxPeriod)-Double.valueOf(minRate))));
			
			productInfo.setLoanUse(loanUseList);
			productInfo.setRepayMethod(repayList);
			productInfo.setDescription(product.getDescription());
			productInfoList.add(productInfo);
		}
		return productInfoList;
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.ProductService#loadById(java.lang.String)
	 */
	@Override
	public Product loadById(String id) {
		return productRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.ProductService#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfo> getAll() {
		List<ProductInfo> productInfos = Caches.get(CACHE_PRODUCT, List.class);
		if (productInfos == null) {
			productInfos = findAll();
			if (productInfos != null) Caches.add(CACHE_PRODUCT, productInfos);
		}
		return productInfos;
	}
}

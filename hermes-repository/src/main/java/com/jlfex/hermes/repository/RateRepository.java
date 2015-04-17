package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Rate;

public interface RateRepository  extends JpaRepository<Rate, String>, JpaSpecificationExecutor<Rate>{
	
	/**
	 * 通过产品和类型查找利率对象
	 * @param product
	 * @param type
	 * @return 返回单个利率对象
	 */
	public Rate findByProductAndType(Product product,String type);
}

package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.ProductOverdue;

/**
 * 
 * 产品逾期信息仓库
 * 
 * @author Ray
 * @version 1.0, 2014-1-2
 * @since 1.0
 */
@Repository
public interface ProductOverdueRepository extends JpaRepository<ProductOverdue, String> {
	
	/**
	 * 通过产品获取逾期列表
	 * @param product
	 * @return
	 */
	public List<ProductOverdue> findByProduct(Product product);

}

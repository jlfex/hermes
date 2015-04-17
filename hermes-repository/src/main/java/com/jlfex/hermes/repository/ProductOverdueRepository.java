package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.ProductOverdue;

/**
 * 产品逾期信息仓库
 */
@Repository
public interface ProductOverdueRepository extends JpaRepository<ProductOverdue, String>, JpaSpecificationExecutor<ProductOverdue> {
	
	/**
	 * 通过产品获取逾期列表
	 * @param product
	 * @return
	 */
	public List<ProductOverdue> findByProduct(Product product);

}

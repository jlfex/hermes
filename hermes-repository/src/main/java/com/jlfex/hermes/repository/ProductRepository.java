package com.jlfex.hermes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.Product;

/**
 * 
 * 产品信息仓库
 * 
 * @author Ray
 * @version 1.0, 2013-12-23
 * @since 1.0
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String>, JpaSpecificationExecutor<Product> {
	/**
	 * 根据状态返回产品信息
	 * 
	 * @return
	 */
	public List<Product> findByStatusIn(List<String> status);

	@Query("select count(o.id) from Product o ")
	public Long countProductNum();
}

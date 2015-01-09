package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

}

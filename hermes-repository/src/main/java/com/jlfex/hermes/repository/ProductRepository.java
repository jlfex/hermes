package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface ProductRepository extends JpaRepository<Product, String> {

}

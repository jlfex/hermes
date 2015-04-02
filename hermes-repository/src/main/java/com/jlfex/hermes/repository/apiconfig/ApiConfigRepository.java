package com.jlfex.hermes.repository.apiconfig;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.ApiConfig;

/**
 * api 接口 配置
 * @author Administrator
 * 
 */
@Repository
public interface ApiConfigRepository extends JpaRepository<ApiConfig, String>, JpaSpecificationExecutor<ApiConfig> {

	public ApiConfig findByPlatCodeAndStatus(String platCode,String status);

	public List<ApiConfig> findByPlatCode(String platCode);


}

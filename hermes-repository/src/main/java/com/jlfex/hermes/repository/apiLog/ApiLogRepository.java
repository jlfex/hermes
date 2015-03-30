package com.jlfex.hermes.repository.apiLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.ApiLog;

/**
 * api 接口日志  
 * @author Administrator
 * 
 */
@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, String>, JpaSpecificationExecutor<ApiLog> {

}

package com.jlfex.hermes.repository.param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.param.CommonParam;

/**
 * 系统公共参数配置
 * @author Administrator
 *
 */
@Repository
public interface CommonParamRepository extends JpaRepository<CommonParam, String>,JpaSpecificationExecutor<CommonParam> {
	
}
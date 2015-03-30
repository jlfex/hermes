package com.jlfex.hermes.repository.assetRepay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.yltx.AssetRepayPlan;

/**
 * 理财产品 
 * @author Administrator
 * 
 */
@Repository
public interface AssetRepayPlanRepository extends JpaRepository<AssetRepayPlan, String>, JpaSpecificationExecutor<AssetRepayPlan> {

}

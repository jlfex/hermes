package com.jlfex.hermes.service.assetRepay;

import java.util.List;

import com.jlfex.hermes.model.yltx.AssetRepayPlan;

/**
 * 资产还款计划 业务
 * @author Administrator
 *
 */
public interface AssetRepayPlanService {
	
	public List<AssetRepayPlan>  save(List<AssetRepayPlan> assetRepayPlanlists) throws Exception  ;

}

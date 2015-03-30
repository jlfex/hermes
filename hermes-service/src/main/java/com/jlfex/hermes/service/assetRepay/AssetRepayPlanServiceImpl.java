package com.jlfex.hermes.service.assetRepay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.yltx.AssetRepayPlan;
import com.jlfex.hermes.repository.assetRepay.AssetRepayPlanRepository;


/**
 * 理财产品 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class AssetRepayPlanServiceImpl implements  AssetRepayPlanService {

	@Autowired
	private AssetRepayPlanRepository assetRepayPlanRepository;

	/**
	 * 保存
	 */
	@Override
	public List<AssetRepayPlan> save(List<AssetRepayPlan> assetRepayPlanLists) throws Exception {
		return assetRepayPlanRepository.save(assetRepayPlanLists);
	}

	
	
	

}

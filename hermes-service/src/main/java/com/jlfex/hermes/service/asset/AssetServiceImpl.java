package com.jlfex.hermes.service.asset;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.yltx.Asset;
import com.jlfex.hermes.repository.asset.AssetRepository;


/**
 * 理财产品 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class AssetServiceImpl implements  AssetService {

	@Autowired
	private AssetRepository assetRepository;

	/**
	 * 保存
	 */
	@Override
	public List<Asset> save(List<Asset> asset) throws Exception {
		return assetRepository.save(asset);
	}
	
	
	
	

}

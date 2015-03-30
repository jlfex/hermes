package com.jlfex.hermes.service.asset;

import java.util.List;

import com.jlfex.hermes.model.yltx.Asset;

/**
 * 资产 业务
 * @author Administrator
 *
 */
public interface AssetService {
	
	public List<Asset>  save(List<Asset> asset) throws Exception  ;

}

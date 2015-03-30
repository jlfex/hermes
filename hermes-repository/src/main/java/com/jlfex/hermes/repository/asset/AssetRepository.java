package com.jlfex.hermes.repository.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.yltx.Asset;

/**
 * 理财产品 
 * @author Administrator
 * 
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, String>, JpaSpecificationExecutor<Asset> {

}

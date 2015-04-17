package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.ImageManage;
/**
 * 图片管理仓库
 */
@Repository
public interface ImageManageRepository extends PagingAndSortingRepository<ImageManage, String>, JpaSpecificationExecutor<ImageManage> {
	ImageManage findOneByCode(String code);

	List<ImageManage> findOneByType(String type);
}

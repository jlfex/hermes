package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.TmpNotice;

/**
 * 公告信息仓库
 */
@Repository
public interface TmpNoticeRepository extends PagingAndSortingRepository<TmpNotice, String>, JpaSpecificationExecutor<TmpNotice> {
	
	public TmpNotice findByStatus(String status);
	
}

package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jlfex.hermes.model.TmpNotice;

public interface TmpNoticeRepository extends PagingAndSortingRepository<TmpNotice, String>, JpaSpecificationExecutor<TmpNotice> {
	public TmpNotice findByStatus(String status);
}

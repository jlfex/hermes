package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jlfex.hermes.model.WebAppearanceAssist;

public interface WebAppearanceAssistRepository extends PagingAndSortingRepository<WebAppearanceAssist, String>, JpaSpecificationExecutor<WebAppearanceAssist> {

}

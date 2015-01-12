package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jlfex.hermes.model.FriendLink;

public interface FriendLinkRepository extends PagingAndSortingRepository<FriendLink, String>, JpaSpecificationExecutor<FriendLink> {

}

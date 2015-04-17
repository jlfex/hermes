package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.FriendLink;
/**
 * 友情链接管理仓库
 */
@Repository
public interface FriendLinkRepository extends PagingAndSortingRepository<FriendLink, String>, JpaSpecificationExecutor<FriendLink> {

}

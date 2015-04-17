package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.FriendLink;

/**
 * 友情链接
 */
public interface FriendLinkService {
	List<FriendLink> findTop10();
}

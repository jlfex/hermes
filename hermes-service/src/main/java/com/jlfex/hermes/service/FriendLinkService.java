package com.jlfex.hermes.service;

import java.util.List;

import com.jlfex.hermes.model.FriendLink;

/**
 * 友情链接
 * 
 * @author xx
 * @version 1.0, 2015-1-19
 * @since 1.0
 */
public interface FriendLinkService {
	List<FriendLink> findTop10();
}

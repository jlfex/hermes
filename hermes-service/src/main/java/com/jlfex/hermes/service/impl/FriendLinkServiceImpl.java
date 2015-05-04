package com.jlfex.hermes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.FriendLink;
import com.jlfex.hermes.repository.FriendLinkRepository;
import com.jlfex.hermes.service.FriendLinkService;

/**
 * 友情链接业务实现
 * @version 1.0, 2015-01-19
 * @since 1.0
 */
@Service
@Transactional
public class FriendLinkServiceImpl implements FriendLinkService {

	@Autowired
	private FriendLinkRepository friendLinkRepository;

	@Override
	public List<FriendLink> findTop10() {
		return friendLinkRepository.findAll(new PageRequest(0, 10, new Sort(Direction.ASC, "order", "order_"))).getContent();
	}

}

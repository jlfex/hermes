package com.jlfex.hermes.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.model.FriendLink;
import com.jlfex.hermes.model.TmpNotice;
import com.jlfex.hermes.service.pojo.ContentCategory;
import com.jlfex.hermes.service.pojo.FriendLinkVo;
import com.jlfex.hermes.service.pojo.PublishContentVo;
import com.jlfex.hermes.service.pojo.ResultVo;
import com.jlfex.hermes.service.pojo.TmpNoticeVo;

public interface ContentService {

	public ArticleCategory findCategoryByNameAndLevel(String name, String level);

	public List<ArticleCategory> findByLevelNotNull();

	public ResultVo deleteCategory(String id);

	public ResultVo insertCategory(ContentCategory contentCategory);

	public ResultVo updateCategory(ContentCategory contentCategory);

	// 根据level查找所有一级分类
	public List<ArticleCategory> findCategoryByLevel(String level);

	// 根据parent查找所有子分类
	public List<ArticleCategory> findCategoryByParent(ArticleCategory parent);

	// 根据父级ID查询子分类
	public List<ArticleCategory> findByParentId(String parentId);

	// 根据ID查询某条分类
	public ArticleCategory findOne(String id);

	Page<Article> find(String levelOne, String levelTwo, String levelThree, String inputName, int page, int size);

	public Article addPublish(PublishContentVo pcVo);

	public Page<FriendLink> findAll(int page, int size);

	public Article findOneById(String id);

	public FriendLink findOneBy(String id);

	Article updateContent(PublishContentVo pcVo);

	void deleteContent(String id);

	public FriendLink addFriendLink(FriendLinkVo flVo);

	FriendLink updateFriendLink(FriendLinkVo flVo);

	TmpNotice updateTmpNotice(TmpNoticeVo tnVo);

	void deleteFriendLink(String id);

	public void batchDeleteContent(String ids);

	Page<TmpNotice> findAllTmpNotices(int page, int size);

	TmpNotice findOneByTmpNoticeId(String id);

}

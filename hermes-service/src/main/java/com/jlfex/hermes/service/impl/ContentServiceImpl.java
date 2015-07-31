package com.jlfex.hermes.service.impl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Article;
import com.jlfex.hermes.model.ArticleCategory;
import com.jlfex.hermes.model.FriendLink;
import com.jlfex.hermes.model.ImageManage;
import com.jlfex.hermes.model.TmpNotice;
import com.jlfex.hermes.repository.ArticleCategoryRepository;
import com.jlfex.hermes.repository.ArticleRepository;
import com.jlfex.hermes.repository.FriendLinkRepository;
import com.jlfex.hermes.repository.ImageManageRepository;
import com.jlfex.hermes.repository.TmpNoticeRepository;
import com.jlfex.hermes.service.ContentService;
import com.jlfex.hermes.service.pojo.ContentCategory;
import com.jlfex.hermes.service.pojo.FriendLinkVo;
import com.jlfex.hermes.service.pojo.PublishContentVo;
import com.jlfex.hermes.service.pojo.ResultVo;
import com.jlfex.hermes.service.pojo.TmpNoticeVo;

/**
 * 内容发布
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	private static final String IMG_FLAG = "<img";
	private static final String SRC_FLAG = "src";
	private static final String RIGHT_BRACE = ">";
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private FriendLinkRepository friendLinkRepository;
	@Autowired
	private TmpNoticeRepository tmpNoticeRepository;
	@Autowired
	private ImageManageRepository imageManageRepository;

	@Override
	public ArticleCategory findCategoryByNameAndLevel(String name, String level) {

		return articleCategoryRepository.findOneByNameAndLevel(name, level);
	}

	@Override
	public Page<ArticleCategory> findByLevelNotNull(int page, int size) {
		return articleCategoryRepository.findAll(new PageRequest(page, size));
	}

	/**
	 * 删除分类
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ResultVo deleteCategory(String id) {
		ArticleCategory articleCategory = articleCategoryRepository.findOne(id);
		List<Article> articleList = articleRepository.findByCategory(articleCategory);
		List<ArticleCategory> articleCategorys = articleCategory.getChildren();
		if (articleCategorys.size() > 0) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "请先删除子分类再做分类删除");
		} else if (articleList.size() > 0) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "此分类已有关联的文章，不能删除");
		} else {
			articleCategoryRepository.delete(articleCategory);
		}
		return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "删除成功");
	}

	/**
	 * 处理新增分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ResultVo insertCategory(ContentCategory category) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setName(category.getInputName());
		articleCategory.setCode(category.getCode());
		articleCategory.setStatus("00");
		String level1 = category.getCategoryLevelOne();
		String level2 = category.getCategoryLevelTwo();
		if (!StringUtils.isEmpty(level1) && StringUtils.isEmpty(level2) && StringUtils.isEmpty(category.getInputName())) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "您还未添加任何分类");
		} else if (!StringUtils.isEmpty(level1) && StringUtils.isEmpty(level2)) { // 当一级分类为不空，二级分类为空
			ArticleCategory parent = articleCategoryRepository.findOne(level1);
			int count = articleCategoryRepository.countByNameAndParentId(category.getInputName(), level1);
			if (count > 0) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "二级分类已存在，请重新添加");
			}
			articleCategory.setLevel("二级");
			articleCategory.setParent(parent);
		} else if (!StringUtils.isEmpty(level1) && !StringUtils.isEmpty(level2)) {
			ArticleCategory parent = articleCategoryRepository.findOne(level2);
			int count = articleCategoryRepository.countByNameAndParentId(category.getInputName(), level2);
			if (count > 0) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "三级分类已存在，请重新添加");
			}
			articleCategory.setLevel("三级");
			articleCategory.setParent(parent);
		}
		articleCategoryRepository.save(articleCategory);
		return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "添加分类成功");
	}

	/**
	 * 处理编辑分类逻辑
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ResultVo updateCategory(ContentCategory category) {
		ArticleCategory articleCategory = articleCategoryRepository.findOne(category.getId());
		articleCategory.setName(category.getInputName());
		articleCategory.setCode(category.getCode());
		String level1 = category.getCategoryLevelOne();
		String level2 = category.getCategoryLevelTwo();
		if (!StringUtils.isEmpty(level1) && StringUtils.isEmpty(level2) && StringUtils.isEmpty(category.getInputName())) {
			return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "您还未添加任何分类");
		} else if (!StringUtils.isEmpty(level1) && StringUtils.isEmpty(level2)) { // 当一级分类为不空，二级分类为空
			ArticleCategory articleCategory2 = articleCategoryRepository.findOneByNameAndParentId(category.getInputName(), level1);
			if (articleCategory2 != null && !category.getId().equals(articleCategory2.getId())) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "二级分类已存在，请重新添加");
			}
		} else if (!StringUtils.isEmpty(level1) && !StringUtils.isEmpty(level2)) {
			
			ArticleCategory articleCategory2 = articleCategoryRepository.findOneByNameAndParentId(category.getInputName(), level2);
			if (articleCategory2 != null && !category.getId().equals(articleCategory2.getId())) {
				return new ResultVo(HermesConstants.RESULT_VO_CODE_BIZ_ERROR, "三级分类已存在，请重新添加");
			}
		}
		articleCategoryRepository.save(articleCategory);
		return new ResultVo(HermesConstants.RESULT_VO_CODE_SUCCESS, "添加分类成功");
	}

	/**
	 * 根据类型查找分类
	 * 
	 * @author lishunfeng
	 */
	@Override
	public List<ArticleCategory> findCategoryByLevel(String level) {
		return articleCategoryRepository.findByLevel(level);
	}

	/**
	 * 根据parent查找所有子分类
	 * 
	 * @author lishunfeng
	 */
	@Override
	public List<ArticleCategory> findCategoryByParent(ArticleCategory parent) {
		return articleCategoryRepository.findByParent(parent);
	}

	@Override
	public List<ArticleCategory> findByParentId(String parentId) {
		return articleCategoryRepository.findByParentId(parentId);
	}

	/**
	 * 根据id找到某条分类记录
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ArticleCategory findOne(String id) {
		return articleCategoryRepository.findOne(id);
	}

	/**
	 * 根据id找到某条内容
	 * 
	 * @author lishunfeng
	 */

	@Override
	public Article findOneById(String id) {
		return articleRepository.findOne(id);
	}

	/**
	 * 根据id找到某条友情链接
	 * 
	 * @author lishunfeng
	 */

	@Override
	public FriendLink findOneBy(String id) {
		return friendLinkRepository.findOne(id);
	}

	/**
	 * 内容管理查询结果
	 * 
	 * @author lishunfeng
	 */
	@Override
	public Page<Article> find(final String levelOne, final String levelTwo, final String levelThree, final String inputName, int page, int size) {
		// 初始化
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "order"));
		orders.add(new Order(Direction.DESC, "updateTime"));
		Pageable pageable = new PageRequest(page, size, new Sort(orders));
		Page<Article> articleList = articleRepository.findAll(new Specification<Article>() {
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> p = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(levelOne) && StringUtils.isEmpty(levelTwo)) {
					p.add(cb.equal(root.<ArticleCategory> get("category").<String> get("id"), levelOne));
				}
				if (StringUtils.isNotEmpty(levelTwo) && StringUtils.isEmpty(levelThree)) {
					p.add(cb.equal(root.<ArticleCategory> get("category").<String> get("id"), levelTwo));
				}
				if (StringUtils.isNotEmpty(levelThree)) {
					p.add(cb.equal(root.<ArticleCategory> get("category").<String> get("id"), levelThree));
				}
				if (StringUtils.isNotEmpty(inputName)) {
					p.add(cb.like(root.<String> get("articleTitle"), "%" + inputName + "%"));
				}
				query.where(cb.and(p.toArray(new Predicate[p.size()])));
				return query.getRestriction();
			}
		}, pageable);
		return articleList;
	}

	/**
	 * 新增发布内容
	 * 
	 * @author lishunfeng
	 * @throws Exception
	 */
	@Override
	public Article addPublish(PublishContentVo pcVo, int maxWidth) throws Exception {
		Article article = new Article();
		article.setArticleTitle(pcVo.getArticleTitle());
		article.setAuthor(HermesConstants.PLAT_MANAGER);
		StringBuffer contentBuffer = new StringBuffer(pcVo.getContent());
		// 判断图文内容中是否含有图片
		if (pcVo.getContent().contains(IMG_FLAG)) {
			String[] strGaps = pcVo.getContent().split(IMG_FLAG);
			for (int i = 0; i < strGaps.length; i++) {
				try {
					String element = strGaps[i];
					if (element.contains(SRC_FLAG)) {
						int beginIndex = element.indexOf(SRC_FLAG);
						int endIndex = element.indexOf(RIGHT_BRACE);
						String base64Source = element.substring(beginIndex, endIndex);
						base64Source = base64Source.substring(base64Source.indexOf(",") + 1, base64Source.lastIndexOf("\""));
						String resizeBase64Code = compressImgToBase64(base64Source, maxWidth);
						if (resizeBase64Code != null) {
							// 替换压缩后的编码
							int begin = contentBuffer.indexOf(base64Source);
							int end = begin + base64Source.length();
							contentBuffer.replace(begin, end, resizeBase64Code);
						}
					}
				} catch (Exception e) {
					Logger.error("发布内容--图文信息中图片压缩异常，跳过压缩操作：", e);
					continue;
				}
			}
			article.setContent(contentBuffer.toString().getBytes("UTF-8"));
		} else {
			article.setContent(pcVo.getContent().getBytes("UTF-8"));
		}
		article.setKeywords(pcVo.getKeywords());
		article.setDescription(pcVo.getDescription());
		ArticleCategory articleCategory = null;
		if (StringUtils.isNotEmpty(pcVo.getLevelOne()) && StringUtils.isEmpty(pcVo.getLevelTwo())) {
			articleCategory = articleCategoryRepository.findOne(pcVo.getLevelOne());
		} else if (StringUtils.isNotEmpty(pcVo.getLevelTwo()) && StringUtils.isEmpty(pcVo.getLevelThree())) {
			articleCategory = articleCategoryRepository.findOne(pcVo.getLevelTwo());
		} else if (StringUtils.isNotEmpty(pcVo.getLevelThree())) {
			articleCategory = articleCategoryRepository.findOne(pcVo.getLevelThree());
		}
		if (articleCategory.getId().equals("1f37572b-b006-11e4-b1e0-3adca39d28f0")) {
			article.setCode("01");
		}
		article.setCategory(articleCategory);
		article.setOrder(pcVo.getOrder());
		article.setStatus("10");
		articleRepository.save(article);
		return article;
	}

	/**
	 * 编辑发布内容
	 * 
	 * @author lishunfeng
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public Article updateContent(PublishContentVo pcVo, int maxWidth) throws Exception {
		Article article = articleRepository.findOne(pcVo.getId());
		article.setArticleTitle(pcVo.getArticleTitle());
		article.setAuthor(HermesConstants.PLAT_MANAGER);
		StringBuffer contentBuffer = new StringBuffer(pcVo.getContent());
		// 判断图文内容中是否含有图片
		if (pcVo.getContent().contains(IMG_FLAG)) {
			String[] strGaps = pcVo.getContent().split(IMG_FLAG);
			for (int i = 0; i < strGaps.length; i++) {
				try {
					String element = strGaps[i];
					if (element.contains(SRC_FLAG)) {
						int beginIndex = element.indexOf(SRC_FLAG);
						int endIndex = element.indexOf(RIGHT_BRACE);
						String base64Source = element.substring(beginIndex, endIndex);
						base64Source = base64Source.substring(base64Source.indexOf(",") + 1, base64Source.lastIndexOf("\""));
						String resizeBase64Code = compressImgToBase64(base64Source, maxWidth);
						if (resizeBase64Code != null) {
							// 替换压缩后的编码
							int begin = contentBuffer.indexOf(base64Source);
							int end = begin + base64Source.length();
							contentBuffer.replace(begin, end, resizeBase64Code);
						}
					}
				} catch (Exception e) {
					Logger.error("发布内容--图文信息中图片压缩异常，跳过压缩操作：", e);
					continue;
				}
			}
			article.setContent(contentBuffer.toString().getBytes("UTF-8"));
		} else {
			article.setContent(pcVo.getContent().getBytes("UTF-8"));
		}
		article.setKeywords(pcVo.getKeywords());
		article.setDescription(pcVo.getDescription());
		ArticleCategory articleCategory = null;
		if (StringUtils.isNotEmpty(pcVo.getLevelOne()) && StringUtils.isEmpty(pcVo.getLevelTwo())) {
			articleCategory = articleCategoryRepository.findOne(pcVo.getLevelOne());
		} else if (StringUtils.isNotEmpty(pcVo.getLevelTwo()) && StringUtils.isEmpty(pcVo.getLevelThree())) {
			articleCategory = articleCategoryRepository.findOne(pcVo.getLevelTwo());
		} else if (StringUtils.isNotEmpty(pcVo.getLevelThree())) {
			articleCategory = articleCategoryRepository.findOne(pcVo.getLevelThree());
		}
		article.setCategory(articleCategory);
		article.setOrder(pcVo.getOrder());
		article.setStatus("10");
		articleRepository.save(article);
		return article;
	}

	/**
	 * 删除内容
	 * 
	 * @author lishunfeng
	 */
	@Override
	public void deleteContent(String id) {
		Article article = articleRepository.findOne(id);
		articleRepository.delete(article);
	}

	/**
	 * 批量删除内容
	 * 
	 * @author lishunfeng
	 */
	public void batchDeleteContent(String ids) {
		String[] idss = ids.split(",");
		for (int i = 0; i < idss.length; i++) {
			this.deleteContent(idss[i]);
		}
	}

	/**
	 * 查询友情链接
	 * 
	 * @author lishunfeng
	 */
	@Override
	public Page<FriendLink> findAll(int page, int size) {
		return friendLinkRepository.findAll(new PageRequest(page, size));
	}

	/**
	 * 新增友情链接
	 * 
	 * @author lishunfeng
	 */

	@Override
	public FriendLink addFriendLink(FriendLinkVo flVo) {
		FriendLink friendLink = new FriendLink();
		friendLink.setName(flVo.getLinkName());
		friendLink.setLink(flVo.getLink());
		friendLink.setOrder(flVo.getOrder());
		friendLink.setType(flVo.getType());
		friendLink.setStatus("10");
		friendLinkRepository.save(friendLink);
		return friendLink;
	}

	/**
	 * 编辑友情链接
	 * 
	 * @author lishunfeng
	 */
	@Override
	public FriendLink updateFriendLink(FriendLinkVo flVo) {
		FriendLink friendLink = friendLinkRepository.findOne(flVo.getId());
		friendLink.setName(flVo.getLinkName());
		friendLink.setLink(flVo.getLink());
		friendLink.setOrder(flVo.getOrder());
		friendLink.setType(flVo.getType());
		friendLink.setStatus("10");
		friendLinkRepository.save(friendLink);
		return friendLink;
	}

	/**
	 * 删除友情链接
	 * 
	 * @author lishunfeng
	 */
	@Override
	public void deleteFriendLink(String id) {
		FriendLink friendLink = friendLinkRepository.findOne(id);
		friendLinkRepository.delete(friendLink);
	}

	/**
	 * 查询临时公告
	 * 
	 * @author lishunfeng
	 */
	@Override
	public Page<TmpNotice> findAllTmpNotices(int page, int size) {
		return tmpNoticeRepository.findAll(new PageRequest(page, size));
	}

	/**
	 * 新增临时公告
	 * 
	 * @author lishunfeng
	 */

	@Override
	public TmpNotice addTmpNotice(TmpNotice tmpNotice) {
		return tmpNoticeRepository.save(tmpNotice);
	}

	/**
	 * 根据id找到某条临时公告
	 * 
	 * @author lishunfeng
	 */

	@Override
	public TmpNotice findOneByTmpNoticeId(String id) {
		return tmpNoticeRepository.findOne(id);
	}

	/**
	 * 编辑临时公告
	 * 
	 * @author lishunfeng
	 */

	@Override
	public TmpNotice updateTmpNotice(TmpNoticeVo tnVo) {
		TmpNotice tmpNotice = tmpNoticeRepository.findOne(tnVo.getId());
		tmpNotice.setTitle(tnVo.getTitle());
		tmpNotice.setContent(tnVo.getContent());
		tmpNotice.setStartDate(tnVo.getStartDate());
		tmpNotice.setEndDate(tnVo.getEndDate());
		tmpNoticeRepository.save(tmpNotice);
		return tmpNotice;
	}

	/**
	 * 删除临时公告
	 * 
	 * @author lishunfeng
	 */

	@Override
	public void deleteTmpNotice(String id) {
		TmpNotice tmpNotice = tmpNoticeRepository.findOne(id);
		tmpNoticeRepository.delete(tmpNotice);
	}

	/**
	 * 查询图片管理
	 * 
	 * @author lishunfeng
	 */
	@Override
	public Page<ImageManage> findAllImageManage(int page, int size) {
		return imageManageRepository.findAll(new PageRequest(page, size));
	}

	/**
	 * 新增图片管理
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ImageManage addImageManage(String type, String name, String link, int order, String imgStr) {
		List<ImageManage> imageList = imageManageRepository.findOneByType(type);
		ImageManage imageManage = null;
		if (imageList.size() > 0) {
			imageManage = imageList.get(0);
		} else {
			imageManage = new ImageManage();
			if (type.equals(HermesConstants.BANNER)) {
				imageManage.setCode("banner");
			} else if (type.equals(HermesConstants.INVEST)) {
				imageManage.setCode("invest");
			} else if (type.equals(HermesConstants.LOAN)) {
				imageManage.setCode("loan");
			} else if (type.equals(HermesConstants.LOGIN)) {
				imageManage.setCode("login");
			} else if (type.equals(HermesConstants.REGISTER)) {
				imageManage.setCode("register");
			}
		}
		imageManage.setImage(imgStr);
		imageManage.setType(type);
		imageManage.setName(name);
		imageManage.setLink(link);
		imageManage.setOrder(order);
		return imageManageRepository.save(imageManage);
	}

	/**
	 * 根据id找到某条图片管理记录
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ImageManage findOneImageManage(String id) {
		return imageManageRepository.findOne(id);
	}

	/**
	 * 根据code找到某条图片管理记录
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ImageManage findOneByCode(String code) {
		return imageManageRepository.findOneByCode(code);
	}

	/**
	 * 编辑图片管理
	 * 
	 * @author lishunfeng
	 */
	@Override
	public ImageManage updateImageManage(String id, String type, String name, String link, int order, String imgStr) {
		ImageManage imageManage = imageManageRepository.findOne(id);
		// 如果imgStr为空的话，不用再去设值
		if (StringUtils.isNotEmpty(imgStr)) {
			imageManage.setImage(imgStr);
		}
		imageManage.setName(name);
		imageManage.setLink(link);
		imageManage.setOrder(order);
		imageManage.setType(type);
		imageManageRepository.save(imageManage);
		return imageManage;
	}

	/**
	 * 删除图片管理
	 * 
	 * @author lishunfeng
	 */
	@Override
	public void deleteImageManage(String id) {
		ImageManage imageManage = imageManageRepository.findOne(id);
		imageManageRepository.delete(imageManage);
	}

	@Override
	public List<Article> findArticleByCode(String code) {
		return articleRepository.findByCode(code);
	}

	/**
	 * 图文信息中超过 预设宽度的图片 压缩
	 * 
	 * @param originBase64
	 * @param maxWidth
	 * @return
	 * @throws Exception
	 */
	public String compressImgToBase64(String originBase64, int maxWidth) throws Exception {
		if (Strings.empty(originBase64)) {
			return null;
		}
		// Base64解码
		byte[] originBinary = Base64.decodeBase64(originBase64);
		for (int i = 0; i < originBinary.length; ++i) {
			if (originBinary[i] < 0) {// 调整异常数据
				originBinary[i] += 256;
			}
		}
		BufferedImage source = ImageIO.read(new ByteArrayInputStream(originBinary));
		int ogriWidth = source.getWidth();
		if (ogriWidth > maxWidth) {
			source = Scalr.resize(source, maxWidth); // 只设置宽度
		} else {
			return null;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(source, "jpeg", os);
		os.flush();
		byte[] newBytes = os.toByteArray();
		os.close();
		return  Base64.encodeBase64String(newBytes);
	}
}

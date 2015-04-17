package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserImage;

/**
 * 用户图片信息仓库
 */
@Repository
public interface UserImageRepository extends JpaRepository<UserImage, String>, JpaSpecificationExecutor<UserImage> {

	/**
	 * 根据用户/类型/状态查询用户图片
	 * 
	 * @param user
	 * @param type
	 * @param status
	 * @return
	 */
	public List<UserImage> findByUserAndTypeAndStatus(User user, String type, String status);
	
	/**
	 * 通过用户/类型/类型加载用户图片
	 * 
	 * @param userId
	 * @param type
	 * @param status
	 * @return
	 */
	public UserImage findByUserIdAndTypeAndStatus(String userId, String type, String status);
	
	/**
	 * 通过用户和类型查询用户图片
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	public UserImage findByUserAndType(User user, String type);

	/**
	 * 通过用户、类型、标签、状态加载用户图片
	 * 
	 * @param userId
	 * @param type
	 * @param labelId
	 * @param status
	 * @return
	 */
	public List<UserImage> findByUserIdAndTypeAndLabelIdAndStatus(String userId, String type, String labelId, String status);
}

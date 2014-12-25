package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlfex.hermes.model.UserEducation;

/**
 * 用户教育仓库
 * 
 * 
 * @author Aether
 * @version 1.0, 2014-1-2
 * @since 1.0
 */
public interface UserEducationRepository extends JpaRepository<UserEducation, String> {

	/**
	 * 根据用户编号和类型查找学历信息
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	UserEducation findByUserIdAndType(String userId, String type);

}

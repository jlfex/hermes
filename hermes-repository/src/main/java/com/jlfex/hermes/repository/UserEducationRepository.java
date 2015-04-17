package com.jlfex.hermes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jlfex.hermes.model.UserEducation;

/**
 * 用户教育仓库
 */
public interface UserEducationRepository extends JpaRepository<UserEducation, String>, JpaSpecificationExecutor<UserEducation> {

	/**
	 * 根据用户编号和类型查找学历信息
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	UserEducation findByUserIdAndType(String userId, String type);

}

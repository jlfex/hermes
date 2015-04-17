package com.jlfex.hermes.service.apiconfig;

import java.util.List;
import org.springframework.data.domain.Page;
import com.jlfex.hermes.model.ApiConfig;
import com.jlfex.hermes.service.pojo.yltx.ApiConfigVo;

/**
 * 接口配置
 * @author Administrator
 *
 */
public interface ApiConfigService {

	/**
	 * 根据平台编码 和 状态 获取配置
	 * @param platCode
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ApiConfig  queryByPlatCodeAndStatus(String platCode, String status) throws Exception;
	/**
	 * 分页查询
	 * @param apiConfigVo
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public Page<ApiConfig>  queryByCondition(ApiConfigVo apiConfigVo,String page, String size) throws Exception ;
	/**
	 * 新增或保存接口配置
	 * @param apiConfigVo
	 * @return
	 */
	public ApiConfig  addOrUpdateApiConfig(ApiConfigVo apiConfigVo);
    /**
     * 根据平台编码获取配置
     * @param platCode
     * @return
     */
	public List<ApiConfig>  findByPlatCode(String platCode);
	/**
	 * 根据ID获取 配置信息
	 * @param id
	 * @return
	 */
	public ApiConfig  findById(String id);
	/**
	 * 更加ID删除配置
	 * @param id
	 */
	public void  delApiConfig(String id);

}

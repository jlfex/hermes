package com.jlfex.hermes.service;
import java.util.List;
import com.jlfex.hermes.model.Dictionary;

/**
 * 字典业务接口
 */
public interface DictionaryService {

	/**
	 * 通过编号加载字典
	 * 
	 * @param id
	 * @return
	 */
	public Dictionary loadById(String id);

	/**
	 * 通过类型代码和字典代码加载字典
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public Dictionary loadByTypeCodeAndCode(String typeCode, String code);

	/**
	 * 通过类型代码查询字典
	 * 
	 * @param typeCode
	 * @return
	 */
	public List<Dictionary> findByTypeCode(String typeCode);

	/**
	 * 通过类型代码读取字典<br>
	 * 从缓存中读取数据
	 * 
	 * @param typeCode
	 * @return
	 */
	public List<Dictionary> getByTypeCode(String typeCode);

	public String maxCodeByCode(String code);
}

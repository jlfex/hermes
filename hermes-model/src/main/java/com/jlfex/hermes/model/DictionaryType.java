package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 字典类型信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-12
 * @since 1.0
 */
@Entity
@Table(name = "hm_dictionary_type")
public class DictionaryType extends Model {

	private static final long serialVersionUID = -6577472604550820331L;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 状态
	 * 
	 * @author Aether
	 * @version 1.0, 2013-11-13
	 * @since 1.0
	 */
	public static final class Status {

		@Element("有效")
		public static final String VALID = "00";

		@Element("无效")
		public static final String INVALID = "99";
	}

	/**
	 * 读取字典名称
	 * 
	 * @return
	 */
	public String getNames() {
		return Dicts.name(name, name, Name.class);
	}

	/**
	 * 字典名称
	 */
	public static final class Name {

		@Element("借款用途")
		public static final String LOAN_PURPOSE = "loan_purpose";

		@Element("证件类型")
		public static final String ID_TYPE = "id_type";

		@Element("导航类型")
		public static final String NAV = "nav";

		@Element("还款方式")
		public static final String REPAY_MODE = "repay_mode";

		@Element("费用类型")
		public static final String COST_TYPE = "cost_type";

		@Element("产品担保方式")
		public static final String PRODUCT_GUARANTEE = "product.guarantee";

		@Element("产品用途")
		public static final String PRODUCT_PURPOSE = "product.purpose";

		@Element("产品招标期限")
		public static final String PRODUCT_DEADLINE = "product.deadline";

	}

}

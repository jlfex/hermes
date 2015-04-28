package com.jlfex.hermes.model.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Model;

/**
 * 系统通用参数配置
 * @author Administrator
 *
 */
@Entity
@Table(name = "hm_common_param", uniqueConstraints={@UniqueConstraint(columnNames = {"code" })} )
public class CommonParam  extends Model{

	private static final long serialVersionUID = 8929055496000562912L;
	
	//键
	@Column(name = "code",nullable=false, length = 40)
	private  String  code ;
	//值
	@Column(name = "val",nullable=false, length = 30)
	private  String  val;
	//状态
	@Column(name = "status",nullable=false, length = 1)
	private  String  status;
	//备注
	@Column(name = "remark", length = 200)
	private  String  remark;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 读取状态名称
	 * 
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}
	
	/**
	 * 状态
	 *
	 */
	public static final class Status {
		@Element("有效")
		public static final String INVALID = "0";
		@Element("无效")
		public static final String EFFECTIVE = "1";

	}
	
}

package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.ApiLog.DealResult;


@Entity
@Table(name = "hm_api_config", uniqueConstraints={@UniqueConstraint(columnNames = {"plat_code" })} )
public class ApiConfig  extends Model{

	/**
	 * 接口参数 统一配置
	 */
	private static final long serialVersionUID = -2860055762227324208L;
	
	//平台名称
	@Column(name = "plat_code",nullable=false, length = 40)
	private  String  platCode ;
	//本地证书名称
	@Column(name = "client_store_name",nullable=false, length = 30)
	private  String  clientStoreName;
	//本地证书保护密码
	@Column(name = "client_store_pwd",nullable=false, length = 20)
	private  String  clientStorePwd;
	//服务端证书名称
	@Column(name = "trust_store_name",nullable=false, length = 30)
	private  String  truestStoreName;
	//服务端信任证书库保护密码
	@Column(name = "trust_store_pwd",nullable=false, length = 20)
	private  String  truststorePwd;
	//服务端Url
	@Column(name = "api_url",nullable=false, length = 100)
	private  String  apiUrl;
	
	//扩展字段
	@Column(name = "ext",length = 60)
	private  String  ext;
	//接口状态
	@Column(name = "status",nullable=false, length = 2)
	private  String  status;
	//备注
	@Column(name = "remark", length = 200)
	private  String  remark;
	
	
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public String getClientStoreName() {
		return clientStoreName;
	}
	public void setClientStoreName(String clientStoreName) {
		this.clientStoreName = clientStoreName;
	}
	public String getClientStorePwd() {
		return clientStorePwd;
	}
	public void setClientStorePwd(String clientStorePwd) {
		this.clientStorePwd = clientStorePwd;
	}
	public String getTruestStoreName() {
		return truestStoreName;
	}
	public void setTruestStoreName(String truestStoreName) {
		this.truestStoreName = truestStoreName;
	}
	public String getTruststorePwd() {
		return truststorePwd;
	}
	public void setTruststorePwd(String truststorePwd) {
		this.truststorePwd = truststorePwd;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
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
	 * 接口状态
	 *
	 */
	public static final class Status {
		@Element("有效")
		public static final String INVALID = "0";
		@Element("无效")
		public static final String EFFECTIVE = "1";

	}
	
}

package com.jlfex.hermes.service.pojo.yltx;

import java.io.Serializable;

public class ApiConfigVo implements Serializable{
	private static final long serialVersionUID = 7830641634590682351L;

	private  String  id;
	private  String  platCode;              //平台名称	
	private  String  clientStoreName;         //本地证书名称
	private  String  truestStoreName;         //服务端证书名称
	private  String  clientStorePwd;
	private  String  truststorePwd;
	private  String  apiUrl;
	private  String  status;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getTruestStoreName() {
		return truestStoreName;
	}
	public void setTruestStoreName(String truestStoreName) {
		this.truestStoreName = truestStoreName;
	}
	public String getClientStorePwd() {
		return clientStorePwd;
	}
	public void setClientStorePwd(String clientStorePwd) {
		this.clientStorePwd = clientStorePwd;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

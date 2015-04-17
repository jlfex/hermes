package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 外部：债权人信息 模型
 * @author Administrator
 *
 */
@Entity
@Table(name = "hm_creditor")
public class Creditor  extends Model {

	
	private static final long serialVersionUID = 1095216291266727515L; 

	//债权人编号
	@Column(name = "creditor_no")
	private String creditorNo;
	//债权人名称
	@Column(name = "creditor_name")
	private String creditorName;
	//外围系统编号
	@Column(name = "origin_no")
	private String originNo;
	//债权人证件类型
	@Column(name = "cert_type")
	private String  certType;
	//债权人证件号码
	@Column(name = "certificate_no")
	private String certificateNo;
	//银行账号
	@Column(name = "bank_account")
	private String bankAccount;
	//银行名称
	@Column(name = "bank_name")
	private String bankName;
	//银行 所属 省
	@Column(name = "bank_prov")
	private String bankProvince;
	//银行 所属 市
	@Column(name = "bank_city")
	private String bankCity;
	//开户行
	@Column(name = "bank_brantch")
	private String bankBrantch;	
	//联系人
	@Column(name = "contacter")
	private String contacter ;
	//手机号码
	@Column(name = "cellphone")
	private String cellphone ;
	//邮箱
	@Column(name = "mail")
	private String mail ;
	//担保方式
	@Column(name = "assoureType")
	private String assoureType ;
	//现金账户
	@OneToOne
	@JoinColumn(name = "user")
	private User user;
	//状态
	@Column(name = "status")
	private String status ;
	//债权 来源
	@Column(name = "source")
	private String source ;
	//简介
	@Column(name = "remark")
	private String remark ;
	public String getCreditorNo() {
		return creditorNo;
	}
	public void setCreditorNo(String creditorNo) {
		this.creditorNo = creditorNo;
	}
	
	public String getOriginNo() {
		return originNo;
	}
	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getBankBrantch() {
		return bankBrantch;
	}
	public void setBankBrantch(String bankBrantch) {
		this.bankBrantch = bankBrantch;
	}
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAssoureType() {
		return assoureType;
	}
	public void setAssoureType(String assoureType) {
		this.assoureType = assoureType;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static final class Status {
		@Element("有效")
		public static final String VALID 			= "00";
		@Element("无效")
		public static final String INVALID 			= "01";
	}
	
	public  void propTrim(){
		if(!Strings.empty(creditorNo)){
			creditorNo.trim();
		}
		if(!Strings.empty(creditorName)){
			creditorName.trim();
		}
		if(!Strings.empty(bankAccount)){
			bankAccount.trim();
		}
		if(!Strings.empty(bankName)){
			bankName.trim();
		}
		if(!Strings.empty(contacter)){
			contacter.trim();
		}
		if(!Strings.empty(creditorName)){
			creditorName.trim();
		}
		if(!Strings.empty(cellphone)){
			cellphone.trim();
		}
		if(!Strings.empty(creditorName)){
			creditorName.trim();
		}
	}
	
}

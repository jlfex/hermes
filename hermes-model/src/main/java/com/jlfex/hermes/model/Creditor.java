package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	@Column(name = "aceditor_no")
	private String creditorNo;
	//债权人名称
	@Column(name = "aceditor_name")
	private String creditorName;
	//债权人证件类型
	@Column(name = "type")
	private String  type;
	//债权人证件号码
	@Column(name = "certificate_no")
	private String certificateNo;
	//联系人
	@Column(name = "contacter")
	private String contacter ;
	//手机号码
	@Column(name = "cellphone")
	private String cellphone ;
	//邮箱
	@Column(name = "mail")
	private String mail ;
	//简介
	@Column(name = "remark")
	private String remark ;
	//来源
	@Column(name = "source")
	private String source ;
	//担保方式
	@Column(name = "assoureType")
	private String assoureType ;
	//现金账户
	@Column(name = "cash_account_id")
	private String cashAccountId ;
	//冻结账户
	@Column(name = "freeze_account_id")
	private String freezeAccountId ;	

	//现金账户
	@OneToOne
	@JoinColumn(name = "cash_account")
	private UserAccount cashAccount;
	
	//冻结账户
	@OneToOne
	@JoinColumn(name = "freeze_account")
	private UserAccount freezeAccount;
	
	
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) { 
		this.certificateNo = certificateNo;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAssoureType() {
		return assoureType;
	}
	public void setAssoureType(String assoureType) {
		this.assoureType = assoureType;
	}
	public String getCreditorNo() {
		return creditorNo;
	}
	public void setCreditorNo(String creditorNo) {
		this.creditorNo = creditorNo;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getCashAccountId() {
		return cashAccountId;
	}
	public void setCashAccountId(String cashAccountId) {
		this.cashAccountId = cashAccountId;
	}
	public String getFreezeAccountId() {
		return freezeAccountId;
	}
	public void setFreezeAccountId(String freezeAccountId) {
		this.freezeAccountId = freezeAccountId;
	}
	public UserAccount getCashAccount() {
		return cashAccount;
	}
	public void setCashAccount(UserAccount cashAccount) {
		this.cashAccount = cashAccount;
	}
	public UserAccount getFreezeAccount() {
		return freezeAccount;
	}
	public void setFreezeAccount(UserAccount freezeAccount) {
		this.freezeAccount = freezeAccount;
	}
	
	
	
	
	
	
	
	
}

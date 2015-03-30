package com.jlfex.hermes.model.yltx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.jlfex.hermes.model.Model;


@Entity
@Table(name = "hm_asset")
public class Asset extends Model{

	/**
	 * 资产模型
	 */
	private static final long serialVersionUID = -6309890297706625947L;

	@ManyToOne
	@JoinColumn(name = "finance_order_id")
	private  FinanceOrder  financeOrder;		//理财产品
	private  String  code ;						//资产编号
	@Column(name =   "project_name")
	private  String  projectName;				//资产名称
	private  BigDecimal  rate;					//融资利率
	private  String  term;						//融资期限
	@Column(name =   "repayment_mode")
	private  String  repaymentMode;				//偿还方式
	@Column(name =   "asset_rating")
	private  String  assetRating;				//资产评级
	private  String  introduction;				//项目介绍
	private  String  purposes;					//融资用途
	private  BigDecimal  amt;					//融资金额
	@Column(name =   "configured_finAmt")
	private  BigDecimal  configuredFinAmt;		//配置额度
	@Column(name =   "label_vol_money")
	private  BigDecimal  labelVolmoney;			//成交金额
	@Column(name =   "buy_back_date")
	private  Date    buyBackDate;				//回购日期
	@Column(name =   "member_id")
	private  String  memberId;					//客户编号
	@Column(name =   "transferor_name")
	private  String  transferorName;			//出让人
	@Column(name =   "transferor_certiType")
	private  String  transferorCertiType;		//出让人证件类型
	@Column(name =   "transferor_certNum")
	private  String  transferorCertNum;			//出让人证件号码
	@Column(name =   "transferor_bankCard")
	private  String  transferorBankCard;		//出让人银行名称
	@Column(name =   "transferor_bank_province")
	private  String  transferorBankProvince;	//银行所在省份
	@Column(name =   "transferor_bank_city")
	private  String  transferorBankCity;		//银行所在城市
	@Column(name =   "transferor_bank_address")
	private  String  transferorBankAddress;		//开户支行
	@Column(name =   "transferor_bank_account")
	private  String  transferorBankAccount;		//银行帐号
	private  String  linkman;					//联系人
	private  String  tel;						//电话
	private  String  mail;						//邮箱
	@Column(name =   "guarantee_way")
	private  String  guaranteeWay;				//资产保障方式
	private  String  source;					//来源
	private  String  borrower;					//借款人
	@Column(name =   "borrower_certiType")
	private  String  borrowerCertiType;			//借款人证件类型
	@Column(name =   "borrower_cardId")
	private  String  borrowerCardId;			//借款人证件号码
	@Column(name =   "borrower_industry")
	private  String  borrowerIndustry;			//行业
	@Column(name =   "borrower_province")
	private  String  borrowerProvince;			//所在省份
	@Column(name =   "borrower_city")
	private  String  borrowerCity;				//所在城市
	@Column(name =   "borrower_amount")
	private  BigDecimal  borrowerAmount;		//借款金额
	@Column(name =   "borrower_term")
	private  String  borrowerTerm;				//借款期限
	@Column(name =   "borrower_purpose")
	private  String  borrowerPurpose;			//借款用途
	@Column(name =   "assetDtl_type")
	private  String  assetDtlType;				//原始债权类型
	@Column(name =   "assetDtl_credit_endDate")
	private  Date     assetDtlCreditEndDate;	//原始债权到期日
	@Column(name =   "assetDtlL_endingDay")
	private  Date     assetDtlLendingDay;		//原始债权放款日
	@Column(name =   "asset_attachmentids")
	private  String  assetAttachmentIds;		//资产附件编号
	
	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY)
	private Set<AssetRepayPlan> assetRepayList = new  HashSet<AssetRepayPlan>() ; //资产还款计划表
	
	
	public FinanceOrder getFinanceOrder() {
		return financeOrder;
	}
	public void setFinanceOrder(FinanceOrder financeOrder) {
		this.financeOrder = financeOrder;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	public String getAssetRating() {
		return assetRating;
	}
	public void setAssetRating(String assetRating) {
		this.assetRating = assetRating;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPurposes() {
		return purposes;
	}
	public void setPurposes(String purposes) {
		this.purposes = purposes;
	}
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getTransferorName() {
		return transferorName;
	}
	public void setTransferorName(String transferorName) {
		this.transferorName = transferorName;
	}
	public String getTransferorCertiType() {
		return transferorCertiType;
	}
	public void setTransferorCertiType(String transferorCertiType) {
		this.transferorCertiType = transferorCertiType;
	}
	public String getTransferorCertNum() {
		return transferorCertNum;
	}
	public void setTransferorCertNum(String transferorCertNum) {
		this.transferorCertNum = transferorCertNum;
	}
	public String getTransferorBankCard() {
		return transferorBankCard;
	}
	public void setTransferorBankCard(String transferorBankCard) {
		this.transferorBankCard = transferorBankCard;
	}
	public String getTransferorBankProvince() {
		return transferorBankProvince;
	}
	public void setTransferorBankProvince(String transferorBankProvince) {
		this.transferorBankProvince = transferorBankProvince;
	}
	public String getTransferorBankCity() {
		return transferorBankCity;
	}
	public void setTransferorBankCity(String transferorBankCity) {
		this.transferorBankCity = transferorBankCity;
	}
	public String getTransferorBankAddress() {
		return transferorBankAddress;
	}
	public void setTransferorBankAddress(String transferorBankAddress) {
		this.transferorBankAddress = transferorBankAddress;
	}
	public String getTransferorBankAccount() {
		return transferorBankAccount;
	}
	public void setTransferorBankAccount(String transferorBankAccount) {
		this.transferorBankAccount = transferorBankAccount;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getGuaranteeWay() {
		return guaranteeWay;
	}
	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getBorrowerCertiType() {
		return borrowerCertiType;
	}
	public void setBorrowerCertiType(String borrowerCertiType) {
		this.borrowerCertiType = borrowerCertiType;
	}
	public String getBorrowerCardId() {
		return borrowerCardId;
	}
	public void setBorrowerCardId(String borrowerCardId) {
		this.borrowerCardId = borrowerCardId;
	}
	public String getBorrowerIndustry() {
		return borrowerIndustry;
	}
	public void setBorrowerIndustry(String borrowerIndustry) {
		this.borrowerIndustry = borrowerIndustry;
	}
	public String getBorrowerProvince() {
		return borrowerProvince;
	}
	public void setBorrowerProvince(String borrowerProvince) {
		this.borrowerProvince = borrowerProvince;
	}
	public String getBorrowerCity() {
		return borrowerCity;
	}
	public void setBorrowerCity(String borrowerCity) {
		this.borrowerCity = borrowerCity;
	}
	
	public String getBorrowerTerm() {
		return borrowerTerm;
	}
	public void setBorrowerTerm(String borrowerTerm) {
		this.borrowerTerm = borrowerTerm;
	}
	public String getBorrowerPurpose() {
		return borrowerPurpose;
	}
	public void setBorrowerPurpose(String borrowerPurpose) {
		this.borrowerPurpose = borrowerPurpose;
	}
	public String getAssetDtlType() {
		return assetDtlType;
	}
	public void setAssetDtlType(String assetDtlType) {
		this.assetDtlType = assetDtlType;
	}
	
	public String getAssetAttachmentIds() {
		return assetAttachmentIds;
	}
	public void setAssetAttachmentIds(String assetAttachmentIds) {
		this.assetAttachmentIds = assetAttachmentIds;
	}
	public Set<AssetRepayPlan> getAssetRepayList() {
		return assetRepayList;
	}
	public void setAssetRepayList(Set<AssetRepayPlan> assetRepayList) {
		this.assetRepayList = assetRepayList;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public BigDecimal getConfiguredFinAmt() {
		return configuredFinAmt;
	}
	public void setConfiguredFinAmt(BigDecimal configuredFinAmt) {
		this.configuredFinAmt = configuredFinAmt;
	}
	public BigDecimal getLabelVolmoney() {
		return labelVolmoney;
	}
	public void setLabelVolmoney(BigDecimal labelVolmoney) {
		this.labelVolmoney = labelVolmoney;
	}
	public Date getBuyBackDate() {
		return buyBackDate;
	}
	public void setBuyBackDate(Date buyBackDate) {
		this.buyBackDate = buyBackDate;
	}
	public BigDecimal getBorrowerAmount() {
		return borrowerAmount;
	}
	public void setBorrowerAmount(BigDecimal borrowerAmount) {
		this.borrowerAmount = borrowerAmount;
	}
	public Date getAssetDtlCreditEndDate() {
		return assetDtlCreditEndDate;
	}
	public void setAssetDtlCreditEndDate(Date assetDtlCreditEndDate) {
		this.assetDtlCreditEndDate = assetDtlCreditEndDate;
	}
	public Date getAssetDtlLendingDay() {
		return assetDtlLendingDay;
	}
	public void setAssetDtlLendingDay(Date assetDtlLendingDay) {
		this.assetDtlLendingDay = assetDtlLendingDay;
	}
	
	
	
	
}

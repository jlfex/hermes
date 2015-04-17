package com.jlfex.hermes.service.pojo.yltx.response;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.jlfex.hermes.common.utils.Strings;

public class AssetVo  implements Serializable {

	/**
	 * 资产信息实体
	 */
	private static final long serialVersionUID = -8129311891407036789L;

	private  String  code ;						//资产编号
	private  String  projectName;				//资产名称
	private  BigDecimal  rate;					//融资利率
	private  String  term;						//融资期限
	private  String  repaymentMode;				//偿还方式
	private  String  assetRating;				//资产评级
	private  String  introduction;				//项目介绍
	private  String  purposes;					//融资用途
	private  BigDecimal  amt;					//融资金额
	private  BigDecimal  configuredFinAmt;		//配置额度
	private  BigDecimal  labelVolmoney;			//成交金额
	private  Date  buyBackDate;				    //回购日期
	private  String  memberId;					//客户编号
	private  String  transferorName;			//出让人
	private  String  transferorCertiType;		//出让人证件类型
	private  String  transferorCertiNum;		//出让人证件号码
	private  String  transferorBankCard;		//出让人银行名称
	private  String  transferorBankProvince;	//银行所在省份
	private  String  transferorBankCity;		//银行所在城市
	private  String  transferorBankAddress;		//开户支行
	private  String  transferorBankAccount;		//银行帐号
	private  String  linkman;					//联系人
	private  String  tel;						//电话
	private  String  mail;						//邮箱
	private  String  guaranteeWay;				//资产保障方式
	private  String  source;					//来源
	private  String  borrower;					//借款人
	private  String  borrowerCertiType;			//借款人证件类型
	private  String  borrowerCardId;			//借款人证件号码
	private  String  borrowerIndustry;			//行业
	private  String  borrowerProvince;			//所在省份
	private  String  borrowerCity;				//所在城市
	private  BigDecimal  borrowerAmount;		//借款金额
	private  String  borrowerTerm;				//借款期限
	private  String  borrowerPurpose;			//借款用途
	private  String  assetDtlType;				//原始债权类型
	private  Date  assetDtlCreditEndDate;		//原始债权到期日
	private  Date  assetDtlLendingDay;		    //原始债权放款日
	private  List<RepayPlanVo> creditorsRepaymentList; //原始债权还款计划表
	private  String  assetAttachmentIds;		//资产附件编号
	
	
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
	
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
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
	public String getTransferorCertiNum() {
		return transferorCertiNum;
	}
	public void setTransferorCertiNum(String transferorCertiNum) {
		this.transferorCertiNum = transferorCertiNum;
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
	
	public List<RepayPlanVo> getCreditorsRepaymentList() {
		return creditorsRepaymentList;
	}
	public void setCreditorsRepaymentList(
			List<RepayPlanVo> creditorsRepaymentList) {
		this.creditorsRepaymentList = creditorsRepaymentList;
	}
	public String getAssetAttachmentIds() {
		return assetAttachmentIds;
	}
	public void setAssetAttachmentIds(String assetAttachmentIds) {
		this.assetAttachmentIds = assetAttachmentIds;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
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
	public BigDecimal getAmt() {
		return amt;
	}
	
	public void toTrim(){
		if(Strings.notEmpty(code)){
			this.code = code.trim();
		}
		if(Strings.notEmpty(projectName)){
			this.projectName = projectName.trim();
		}
		if(Strings.notEmpty(term)){
			this.term = term.trim();
		}
		if(Strings.notEmpty(repaymentMode)){
			this.repaymentMode = repaymentMode.trim();
		}
		if(Strings.notEmpty(assetRating)){
			this.assetRating = assetRating.trim();
		}
		if(Strings.notEmpty(introduction)){
			this.introduction = introduction.trim();
		}
		if(Strings.notEmpty(purposes)){
			this.purposes = purposes.trim();
		}
		if(Strings.notEmpty(memberId)){
			this.memberId = memberId.trim();
		}
		if(Strings.notEmpty(transferorName)){
			this.transferorName = transferorName.trim();
		}
		if(Strings.notEmpty(transferorCertiType)){
			this.transferorCertiType = transferorCertiType.trim();
		}
		if(Strings.notEmpty(transferorCertiNum)){
			this.transferorCertiNum = transferorCertiNum.trim();
		}
		if(Strings.notEmpty(transferorBankCard)){
			this.transferorBankCard = transferorBankCard.trim();
		}
		if(Strings.notEmpty(transferorBankProvince)){
			this.transferorBankProvince = transferorBankProvince.trim();
		}
		if(Strings.notEmpty(transferorBankCity)){
			this.transferorBankCity = transferorBankCity.trim();
		}
		if(Strings.notEmpty(transferorBankAddress)){
			this.transferorBankAddress = transferorBankAddress.trim();
		}
		if(Strings.notEmpty(transferorBankAccount)){
			this.transferorBankAccount = transferorBankAccount.trim();
		}
		if(Strings.notEmpty(linkman)){
			this.linkman = linkman.trim();
		}
		if(Strings.notEmpty(tel)){
			this.tel = tel.trim();
		}
		if(Strings.notEmpty(mail)){
			this.mail = mail.trim();
		}
		if(Strings.notEmpty(guaranteeWay)){
			this.guaranteeWay = guaranteeWay.trim();
		}
		if(Strings.notEmpty(source)){
			this.source = source.trim();
		}
		if(Strings.notEmpty(borrower)){
			this.borrower = borrower.trim();
		}
		if(Strings.notEmpty(borrowerCertiType)){
			this.borrowerCertiType = borrowerCertiType.trim();
		}
		if(Strings.notEmpty(borrowerCardId)){
			this.borrowerCardId = borrowerCardId.trim();
		}
		if(Strings.notEmpty(borrowerIndustry)){
			this.borrowerIndustry = borrowerIndustry.trim();
		}
		if(Strings.notEmpty(borrowerProvince)){
			this.borrowerProvince = borrowerProvince.trim();
		}
		if(Strings.notEmpty(borrowerCity)){
			this.borrowerCity = borrowerCity.trim();
		}
		if(Strings.notEmpty(borrowerTerm)){
			this.borrowerTerm = borrowerTerm.trim();
		}
		if(Strings.notEmpty(borrowerPurpose)){
			this.borrowerPurpose = borrowerPurpose.trim();
		}
		if(Strings.notEmpty(assetDtlType)){
			this.assetDtlType = assetDtlType.trim();
		}
		if(Strings.notEmpty(assetAttachmentIds)){
			this.assetAttachmentIds = assetAttachmentIds.trim();
		}
	}
	
	
	
}

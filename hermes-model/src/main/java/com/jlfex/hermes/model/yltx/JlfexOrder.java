package com.jlfex.hermes.model.yltx;



import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Model;


@Entity
@Table(name = "hm_order_jlfex")
public class JlfexOrder extends Model{

	/**
	 *  JLFEX  订单  实体
	 */
	private static final long serialVersionUID = -7945558419475963945L;
	
	
	@ManyToOne
	@JoinColumn(name = "finance_order_id")
	private  FinanceOrder  financeOrder;		    //理财产品
	@OneToOne
	@JoinColumn(name = "invest_id")
	private  Invest   invest;		                //理财信息
	@JoinColumn(name = "order_amount")
	private  BigDecimal  orderAmount;		        //订单金额
	@Column(name =   "order_code")
	private  String   orderCode;					//订单编号
	@Column(name =   "guarantee_pdf_id")
	private  String   guaranteePdfId;				//担保承诺函编号
	@Column(name =   "loan_pdf_id")
	private  String   loanPdfId;					//债权转让及回购协议编号
	@Column(name =   "payment_pdf_id")
	private  String   paymentPdfId;				    //支付协议编号
	@Column(name =   "order_status")
	private  String   orderStatus;				    //订单状态
	@Column(name =   "pay_status")
	private  String   payStatus;					//支付状态
	private  String   status;					    //处理状态
	@Column(name =   "asset_code")
	private  String   assetCode;				    //资产编号
	
	
	
	
	public FinanceOrder getFinanceOrder() {
		return financeOrder;
	}
	public void setFinanceOrder(FinanceOrder financeOrder) {
		this.financeOrder = financeOrder;
	}
	public Invest getInvest() {
		return invest;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public void setInvest(Invest invest) {
		this.invest = invest;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getGuaranteePdfId() {
		return guaranteePdfId;
	}
	public void setGuaranteePdfId(String guaranteePdfId) {
		this.guaranteePdfId = guaranteePdfId;
	}
	public String getLoanPdfId() {
		return loanPdfId;
	}
	public void setLoanPdfId(String loanPdfId) {
		this.loanPdfId = loanPdfId;
	}
	public String getPaymentPdfId() {
		return paymentPdfId;
	}
	public void setPaymentPdfId(String paymentPdfId) {
		this.paymentPdfId = paymentPdfId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	/**
	 * 处理状态
	 * @author Administrator
	 *
	 */
	public static final class Status {
		@Element("待同步")
		public static final String WAIT_DEAL   = "00";
		@Element("已同步")
		public static final String FIN_DEAL    = "01";
		@Element("已撤单")
		public static final String CANCEL      = "02";
	}
	
	
	
	
}

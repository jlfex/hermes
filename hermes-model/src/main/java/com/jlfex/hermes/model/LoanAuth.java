package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;

/**
 * 借款审核信息模型
 */
@Entity
@Table(name = "hm_loan_auth")
public class LoanAuth extends Model {

	private static final long serialVersionUID = 7499225026460863829L;

	/** 借款 */
	@ManyToOne
	@JoinColumn(name = "loan")
	private Loan loan;
	
	/** 标签 */
	@ManyToOne
	@JoinColumn(name = "label")
	private Label label;
	
	/** 状态 */
	@Column(name = "status")
	private String status;

	/**
	 * 读取借款
	 * 
	 * @return
	 * @see #loan
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * 设置借款
	 * 
	 * @param loan
	 * @see #loan
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	/**
	 * 读取标签
	 * 
	 * @return
	 * @see #label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * 设置标签
	 * 
	 * @param label
	 * @see #label
	 */
	public void setLabel(Label label) {
		this.label = label;
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
	 */
	public static final class Status {
		
		@Element("未通过")
		public static final String NOT_PASS	= "00";
		
		@Element("通过")
		public static final String PASS		= "01";
	}
}

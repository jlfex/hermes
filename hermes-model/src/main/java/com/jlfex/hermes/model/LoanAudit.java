package com.jlfex.hermes.model;

import java.util.Date;

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
@Table(name = "hm_loan_audit")
public class LoanAudit extends Model {
	
	private static final long serialVersionUID = 6837844941000452215L;

	/** 借款 */
	@ManyToOne
	@JoinColumn(name = "loan")
	private Loan loan;
	
	/** 审核人 */
	@Column(name = "auditor")
	private String auditor;
	
	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;
	
	/** 备注 */
	@Column(name = "remark")
	private String remark;
	
	/** 状态 */
	@Column(name = "status")
	private String status;
	
	/** 类型 */
	@Column(name = "type")
	private String type;
	
	
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
	 * 读取审核人
	 * 
	 * @return
	 * @see #auditor
	 */
	public String getAuditor() {
		return auditor;
	}
	
	/**
	 * 设置审核人
	 * 
	 * @param auditor
	 * @see #auditor
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	/**
	 * 读取日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}
	
	/**
	 * 设置日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 读取备注
	 * 
	 * @return
	 * @see #remark
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * 设置备注
	 * 
	 * @param remark
	 * @see #remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return description:取得类型名称
	 */
	public String getTypeName() {
		return Dicts.name(type, type, Type.class);
	}

	/**
	 * @return description:取得状态名称
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	/**
	 * 审核类型
	 */
	public static final class Type {
		@Element("初审")
		public static final String FIRST_AUDIT = "00";

		@Element("终审")
		public static final String FINALL_AUDIT = "01";
	}

	/**
	 * 审核结果
	 */
	public static final class Status {
		@Element("通过")
		public static final String PASS = "00";

		@Element("驳回")
		public static final String REJECT = "01";
	}
}

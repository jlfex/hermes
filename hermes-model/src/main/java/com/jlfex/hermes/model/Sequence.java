package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 序列
 * @author Administrator
 *
 */
@Entity
@Table(name = "hm_sequence", uniqueConstraints={@UniqueConstraint(columnNames = {"seq_name" })} )
public class Sequence extends Model {

	
	private static final long serialVersionUID = 2454921842046468440L;
	
	//序列名称
	@Column(name = "seq_name",nullable=false, length = 40)
	private  String  seqName ;
	// 当前值
	@Column(name = "current_val",nullable=false, length = 12)
	private  String currentVal;
	// 步长(增幅)
	@Column(name = "increment_val",nullable=false, length = 4)
	private  int    incrementVal;
	//状态：00: 有效   10无效
	@Column(name = "status",nullable=false, length = 2)
	private  String  status ;
	
	
	
	public String getSeqName() {
		return seqName;
	}
	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	public String getCurrentVal() {
		return currentVal;
	}
	public void setCurrentVal(String currentVal) {
		this.currentVal = currentVal;
	}
	public int getIncrementVal() {
		return incrementVal;
	}
	public void setIncrementVal(int incrementVal) {
		this.incrementVal = incrementVal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}

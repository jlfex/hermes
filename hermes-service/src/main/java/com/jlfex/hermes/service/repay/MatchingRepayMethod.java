package com.jlfex.hermes.service.repay;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanRepay;

/**
 * 
 * 等额本息实现
 * 
 * @author Ray
 * @version 1.0, 2013-12-23
 * @since 1.0
 */
public class MatchingRepayMethod implements RepayMethod {

	/**
	 * 生成还款计划
	 */
	@Override
	public List<LoanRepay> generatePlan(Loan loan, Map<String, String> params) {
		// 创建一个长度为最大期数List
		List<LoanRepay> loanRepayList = new ArrayList<LoanRepay>(loan.getPeriod());
		Date[] repayDatePlan = null;
		// 如果参数不等于空，并且获取到放款日期，就生成计划还款日
		if (params != null) {
			if (params.get("lendingDate") != null) {
				repayDatePlan = generateRepayDate(params.get("lendingDate"), loan.getPeriod());
			}
		}
		BigDecimal[] surplusPrincipal = new BigDecimal[loan.getPeriod()];
		LoanRepay loanRepay = null;
		BigDecimal monthRate = loan.getRate().divide(new BigDecimal(12), 8, RoundingMode.HALF_DOWN);// 计算月利率
		BigDecimal managefee = calManagemefee(loan); // 计算月缴管理费
		BigDecimal monthRepayPrincipalIinterest = calAverageCapitalPlusInterest(loan, monthRate);// 月还本息
		for (int i = 0; i < loan.getPeriod(); i++) {
			BigDecimal principalInterest = BigDecimal.ZERO; // 月还本息
			BigDecimal interest = BigDecimal.ZERO; // 月还利息
			BigDecimal principal = BigDecimal.ZERO; // 月还本金
			if (i == 0) {
				// 第一期还款计划生成
				principalInterest = monthRepayPrincipalIinterest.setScale(2, RoundingMode.HALF_UP); // 月还本息
				interest = (loan.getAmount().multiply(monthRate)).setScale(2, RoundingMode.HALF_UP); // 月还利息
				principal = (principalInterest.subtract(interest)).setScale(2, RoundingMode.HALF_UP); // 月还本金
				surplusPrincipal[i] = (loan.getAmount().subtract(principal)).setScale(2, RoundingMode.HALF_UP); // 剩余本金
			} else if (i == loan.getPeriod() - 1) {
				// 最后一期还款计划生成
				interest = (surplusPrincipal[i - 1].multiply(monthRate)).setScale(2, RoundingMode.HALF_UP); // 月还利息
				principal = surplusPrincipal[i - 1].setScale(2, RoundingMode.HALF_UP); // 月还本金
				surplusPrincipal[i] = (surplusPrincipal[i - 1].subtract(principal)).setScale(2, RoundingMode.HALF_UP);// 剩余本金
				principalInterest = interest.add(principal); // 月还本息

			} else {
				principalInterest = monthRepayPrincipalIinterest.setScale(2, RoundingMode.HALF_UP); // 月还本息
				interest = (surplusPrincipal[i - 1].multiply(monthRate)).setScale(2, RoundingMode.HALF_UP); // 月还利息
				principal = (principalInterest.subtract(interest)).setScale(2, RoundingMode.HALF_UP); // 月还本金
				surplusPrincipal[i] = (surplusPrincipal[i - 1].subtract(principal)).setScale(2, RoundingMode.HALF_UP);// 剩余本金
			}
			loanRepay = new LoanRepay();
			loanRepay.setLoan(loan);
			loanRepay.setSequence(i + 1); // 期数
			loanRepay.setOtherAmount(managefee); // 月缴管理费
			loanRepay.setAmount(principalInterest); // 本息
			loanRepay.setInterest(interest); // 利息
			loanRepay.setPrincipal(principal); // 本金
			loanRepay.setOverdueDays(0);
			loanRepay.setOverdueInterest(BigDecimal.ZERO);
			loanRepay.setOverduePenalty(BigDecimal.ZERO);
			loanRepay.setStatus(LoanRepay.RepayStatus.WAIT);
			if (repayDatePlan != null && repayDatePlan.length == loan.getPeriod()) {
				loanRepay.setPlanDatetime(repayDatePlan[i]); // 计划还款日
			}
			loanRepayList.add(loanRepay); // 加入list
		}
		return loanRepayList;
	}

	/**
	 * 计算预期收益
	 */
	@Override
	public BigDecimal getProceeds(Loan loan, Map<String, String> params, BigDecimal amount) {
		List<LoanRepay> loanRepayList = generatePlan(loan, null);
		BigDecimal scale = amount.divide(loan.getAmount(), 8, RoundingMode.HALF_DOWN);
		BigDecimal totalProceeds = BigDecimal.ZERO;
		for (LoanRepay loanRepay : loanRepayList) {
			totalProceeds = loanRepay.getAmount().add(totalProceeds);
		}
		return totalProceeds.multiply(scale).setScale(2, RoundingMode.HALF_UP);
	}
	/**
	 * 计算总计还款金额
	 */
	@Override
	public BigDecimal getAllPT(Loan loan) {
		List<LoanRepay> loanRepayList = generatePlan(loan, null);
		BigDecimal totalProceeds = BigDecimal.ZERO;
		for (LoanRepay loanRepay : loanRepayList) {
			totalProceeds = loanRepay.getAmount().add(totalProceeds);
		}
		return totalProceeds;
	}

	@Override
	public void getTiqian(Loan loan, Map<String, String> params) {
		// TODO Auto-generated method stub

	}

	/**
	 * 等额本息法：每月还本息= [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]
	 */
	private BigDecimal calAverageCapitalPlusInterest(Loan loan, BigDecimal monthRate) {
		BigDecimal tmp1 = BigDecimal.ONE.add(monthRate).pow(loan.getPeriod()); // （1+月利率）^还款月数
		BigDecimal tmp2 = loan.getAmount().multiply(monthRate); // 贷款本金×月利率
		BigDecimal tmp3 = tmp1.multiply(tmp2); // [贷款本金×月利率×（1+月利率）^还款月数]
		BigDecimal tmp4 = (BigDecimal.ONE.add(monthRate)).pow(loan.getPeriod()).subtract(BigDecimal.ONE); // （1+月利率）^还款月数－1
		return tmp3.divide(tmp4, 8, RoundingMode.HALF_DOWN);
	}

	/**
	 * 计算月缴管理费
	 */
	private BigDecimal calManagemefee(Loan loan) {
		BigDecimal managefee = BigDecimal.ZERO;
		// 借款类型00为百分比，借款金额X百分比值 01为固定值
		if ("00".equals(loan.getManageFeeType())) {
			managefee = (loan.getAmount().multiply(loan.getManageFee())).setScale(2, RoundingMode.HALF_UP);
		} else if ("01".equals(loan.getManageFeeType())) {
			managefee = loan.getManageFee();
		}
		return managefee;
	}

	/**
	 * 计算还款日期列表
	 * 
	 * @param lendingDate 放款日期
	 * @param curNum 期数
	 * @return
	 */
	private Date[] generateRepayDate(String lendingDate, int curNum) {
		Date[] repayDatePlan = new Date[curNum];
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date lend = null;
		try {
			lend = format.parse(lendingDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lend);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 算出几号
		calendar.add(Calendar.MONTH, 1);// 下月
		if (day >= 1 && day <= 15) {
			// 1日-15日,下月1号还款
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			// 16日-31日,下月16号还款
			calendar.set(Calendar.DAY_OF_MONTH, 16);
		}
		for (int i = 0; i < repayDatePlan.length; i++) {
			// dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			repayDatePlan[i] = calendar.getTime();
			calendar.add(Calendar.MONTH, 1);// 下月
		}
		return repayDatePlan;
	}

	/**
	 * 计算逾期罚息(逾期罚息=剩余未还期数*每期应还本息*天数*比例)
	 * 
	 * @param overdueDay 逾期天数
	 * @param term 剩余未还期数
	 * @param monthRepayPrincipalIinterest 每期应还本息
	 * @param rate 逾期罚息费率
	 * @return
	 */
	@Override
	public BigDecimal getOverdueInterest(int overdueDay, int term, BigDecimal monthRepayPrincipalIinterest, BigDecimal rate) {
		return (new BigDecimal(term).multiply(monthRepayPrincipalIinterest).multiply(new BigDecimal(overdueDay)).multiply(rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 计算逾期违约金（逾期违约金：=剩余未还期数*每期应还管理费*逾期天数*逾期罚息费率） 
	 * * @param overdueDay 逾期天数
	 * @param term 剩余未还期数
	 * @param manageFee 每期应还管理费
	 * @param rate 罚息比例
	 */
	@Override
	public BigDecimal getOverduePenalty(int overdueDay, int term, BigDecimal manageFee, BigDecimal rate) {
		return new BigDecimal(term).multiply(manageFee).multiply(new BigDecimal(overdueDay)).multiply(rate);
	}

	public static void main(String[] args) {
//		Loan loan = new Loan();
//		loan.setAmount(new BigDecimal(10000));
//		loan.setManageFeeType("00");
//		loan.setManageFee(new BigDecimal(0.005));
//		loan.setPeriod(6);
//		loan.setRate(new BigDecimal(0.0666));
//		Map<String, String> m = new HashMap<String, String>();
//		m.put("lendingDate", "2013-9-3");
//		List<LoanRepay> list = new MatchingRepayMethod().generatePlan(loan, m);
//		for (int i = 0; i < list.size(); i++) {
//			LoanRepay l = list.get(i);
//			System.out.println(l.getSequence() + "   " + l.getPlanDatetime() + "   " + l.getAmount() + "   " + l.getPrincipal() + "   " + l.getInterest() + "   " + l.getOtherAmount() + "   ");
//		}
//
//		MathContext v2 = new MathContext(2, RoundingMode.HALF_DOWN);
//		BigDecimal a2 = new BigDecimal("0.875", v2);
//		System.out.println(a2);

		// BigDecimal a=new MatchingRepayMethod().getProceeds(loan, null, new
		// BigDecimal(3000));
		// System.out.println(a);

		// Date[] d=new MatchingRepay().generateRepayDate("2013-11-4", 12);
		//
		// for(int j=0;j<d.length;j++){
		//
		// System.out.println(d[j]);
		// }

	}

}

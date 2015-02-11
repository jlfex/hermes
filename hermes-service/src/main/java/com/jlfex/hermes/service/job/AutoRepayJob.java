package com.jlfex.hermes.service.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.LoanLog.Type;
import com.jlfex.hermes.model.LoanRepay.RepayStatus;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.service.RepayService;

/**
 * @author 自动还款，假如金额不足改还款状态为逾期
 */
@Component("autoRepayJob")
public class AutoRepayJob extends Job {

	@Autowired
	private RepayService repayService;
	@Autowired
	private LoanLogRepository loanLogRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.job.Job#run()
	 */
	@Override
	public Result run() {
		String var = "自动还款JOB....";
		try {
			long beginTime = System.currentTimeMillis();
			Logger.info(var+"开始....");
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date yesterday = calendar.getTime();
			Date beginDateTime = Calendars.parseBeginDateTime(Calendars.date(yesterday));
			Date endDateTime = Calendars.parseEndDateTime(Calendars.date(yesterday));
			List<LoanRepay> loanRepayList = repayService.findByPlanDatetimeBetweenAndStatus(beginDateTime, endDateTime, LoanRepay.RepayStatus.WAIT);
			int successCount = 0;
			StringBuilder successRemark = new StringBuilder();
			int failureCount = 0;
			StringBuilder failureRemark = new StringBuilder();
			for(int i = 0; i < loanRepayList.size(); i++) {
				LoanRepay loanRepay = loanRepayList.get(i);
				try {
					Loan loan = loanRepay.getLoan();
					if(loan!= null && Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(loan.getLoanKind())){
						continue; 
					}
					//自动还款 判断之前是否有逾期
					List<LoanRepay> overdueLoanRepayList = overdueCheck(loan); 
					//优先还逾期
					if(overdueLoanRepayList != null && overdueLoanRepayList.size() > 0){
						for(LoanRepay overdueObj :overdueLoanRepayList){
							boolean flag = repayService.autoRepayment(overdueObj.getId());
							if(!flag){
								throw new Exception("自动还款JOB--优先处理逾期：逾期还款:"+(flag?"成功":"失败")+",loanRepayId = "+overdueObj.getId()+",还款时间="+overdueObj.getPlanDatetime());
							}
						}
					}
					boolean success = repayService.autoRepayment(loanRepay.getId());
					if (success){
						LoanLog loanLog = new LoanLog();
						loanLog.setUser(loanRepay.getLoan().getUser().getId());
						loanLog.setLoan(loanRepay.getLoan());
						loanLog.setDatetime(new Date());
						loanLog.setType(Type.REPAY);
						loanLog.setAmount(loanRepay.getAmount());
						loanLog.setRemark("自动还款成功");
						loanLogRepository.save(loanLog);
						successRemark.append("编号:" + loanRepay.getLoan().getLoanNo() + ",还款成功\r\n");
						successCount++;
					}
				} catch (ServiceException ex) {
					repayService.updateStatus(loanRepay.getId(), loanRepay.getStatus(), RepayStatus.OVERDUE);
					LoanLog loanLog = new LoanLog();
					loanLog.setUser(loanRepay.getLoan().getUser().getId());
					loanLog.setLoan(loanRepay.getLoan());
					loanLog.setDatetime(new Date());
					loanLog.setType(Type.REPAY);
					loanLog.setAmount(loanRepay.getAmount());
					loanLog.setRemark("自动还款失败，错误原因code:" + ex.getCode());
					loanLogRepository.save(loanLog);
					failureCount++;
					failureRemark.append("编号:" + loanRepay.getLoan().getLoanNo() + ",还款失败\r\n");
					Logger.error("自动还款异常:", ex);
				}
			}
			Logger.info(var+"完成. 耗时%s millisecond.", (System.currentTimeMillis() - beginTime));
			StringBuilder remarks = new StringBuilder();
			String remark = String.format("%s,共跑 %s笔借款,成功还款%s笔,失败还款%s笔", Calendars.date(), loanRepayList.size(), successCount, failureCount);
			remarks.append(remark);
			Logger.info(remark);
			if (successRemark.length() > 0) {
				remarks.append(successRemark.toString());
			}
			if (failureRemark.length() > 0) {
				remarks.append(failureRemark.toString());
			}
			return new Result(true, true, remarks.toString());
		} catch (Exception e) {
			ServiceException exception = new ServiceException(e.getMessage(), e);
			throw exception;
		}

	}
	/**
	 * 还款本期 之前：如果存在逾期的  优先还款.
	 * @param loan
	 * @return
	 */
	public List<LoanRepay>  overdueCheck(Loan loan){
		try{
			List<LoanRepay> loanRepayList =  repayService.findByLoanAndStatus(loan, LoanRepay.RepayStatus.OVERDUE);
			if(loanRepayList == null || loanRepayList.size() == 0){
				return null;
			}
			Collections.sort(loanRepayList, new Comparator<LoanRepay>() {
		           public int compare(LoanRepay obj1, LoanRepay obj2) {
		            	return (""+obj1.getSequence()).compareTo(""+obj2.getSequence());
		           }
		    }); 
			return loanRepayList;
		}catch(Exception e){
			Logger.error("标还款明细中状态为：逾期，期数最小的记录异常:", e);
		}
		return null;
	}
}

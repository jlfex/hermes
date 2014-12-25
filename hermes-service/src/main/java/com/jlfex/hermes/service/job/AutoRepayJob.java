package com.jlfex.hermes.service.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.model.LoanLog.Type;
import com.jlfex.hermes.model.LoanRepay.RepayStatus;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.service.RepayService;

/**
 * @author 陈琪 自动还款，假如金额不足改还款状态为逾期
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
		try {
			long beginTime = System.currentTimeMillis();
			Logger.info("batch autoRepayJob start.");
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
			for (int i = 0; i < loanRepayList.size(); i++) {
				LoanRepay loanRepay = loanRepayList.get(i);
				try {
					boolean success = repayService.autoRepayment(loanRepay.getId());
					if (success) {
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
					Logger.error(ex.getMessage(), ex);
				}
			}
			Logger.info("batch autoRepayJob finished. take %s millisecond.", (System.currentTimeMillis() - beginTime));
			StringBuilder remarks = new StringBuilder();
			String remark = String.format("%s,共跑 %s笔借款,成功还款%s笔,失败还款%s笔", Calendars.date(), loanRepayList.size(), successCount, failureCount);
			remarks.append(remark);
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
}

package com.jlfex.hermes.service.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.LoanLog.Type;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LoanService;

/**
 * @author 陈琪 自动流标，更新借款状态为自动流标
 */
@Component("autoBidFailureJob")
public class AutoBidFailureJob extends Job {

	// 正在招标中的Cache的list，其中list里的每个loan对象的BusinessDate属性都 放置截至日期，精确到秒
	private static final String CACHE_LOAN_BID_LIST_NAME = "com.jlfex.hermes.cache.loan.bid.list";

	@Autowired
	private LoanService loanService;

	@Autowired
	private InvestService investService;
	/** 借款信息仓库扩展 */
	@Autowired
	private LoanLogRepository loanLogRepository;

	@SuppressWarnings("unchecked")
	@Override
	public Result run() {
		try {
			long beginTime = System.currentTimeMillis();
			Logger.info("batch autoBidFailureJob start.");
			if (Caches.get(CACHE_LOAN_BID_LIST_NAME) == null) {
				List<Loan> loans = loanService.findByStatus(Loan.Status.BID);
				List<Loan> loanStartInvest = new ArrayList<Loan>();
				for (int i = 0; i < loans.size(); i++) {
					String loanId = loans.get(i).getId();
					LoanLog LoanLogStartInvest = loanService.loadLogByLoanIdAndType(loanId, LoanLog.Type.START_INVEST);
					if (LoanLogStartInvest != null) {
						Loan loan = loans.get(i);
						String duration = String.valueOf(loan.getPeriod() + "d");
						//设置投标的截至时间
						loan.setBusinessDate(Calendars.add(LoanLogStartInvest.getDatetime(), duration));
						loanStartInvest.add(loan);
					}
				}
				//由于每天有可能有新的终审通过，开始招标的借款，故设置有效期为1天 
				Caches.set(CACHE_LOAN_BID_LIST_NAME, loanStartInvest, "1d");
			}
			//是否有更新的数据
			boolean isUpdateData = false;
			StringBuilder remarks = new StringBuilder();
			Date now = new Date();
			List<Loan> loansNew = Caches.get(CACHE_LOAN_BID_LIST_NAME, List.class);
			for (int i = 0; i < loansNew.size(); i++) {
				Loan loan = loansNew.get(i);
				if (loan.getBusinessDate() != null && loan.getBusinessDate().before(now)) {
					isUpdateData = true;
					boolean success = investService.processAutoBidFailure(loan);
					if (success) {
						LoanLog loanLog = new LoanLog();
						loanLog.setUser(loan.getUser().getId());
						loanLog.setLoan(loan);
						loanLog.setDatetime(new Date());
						loanLog.setType(Type.AUTO_FAILURE);
						loanLog.setAmount(loan.getAmount());
						loanLog.setRemark("自动流标成功");
						loanLogRepository.save(loanLog);
						remarks.append("编号为" + loan.getLoanNo() + "的借款于" + Calendars.date() + "自动流标。\r\n");
					
					} else {
						remarks.append("编号为" + loan.getLoanNo() + "的借款于" + Calendars.date() + "自动流标失败。\r\n");
						Logger.error("更新借款状态失败，借款编号%s", loan.getLoanNo());
					}
				} else {
					continue;
				}
			}
			Logger.info("batch autoBidFailureJob finished. take %s millisecond.", (System.currentTimeMillis() - beginTime));
			if (isUpdateData) {
				Caches.delete(CACHE_LOAN_BID_LIST_NAME);
				return new Result(true, true, remarks.toString());
			} else {
				return new Result(true, false, "");
			}

		} catch (Exception e) {
			ServiceException exception = new ServiceException(e.getMessage(), e);
			throw exception;
		}

	}
}

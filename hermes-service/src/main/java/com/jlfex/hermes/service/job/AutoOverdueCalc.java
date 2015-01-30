package com.jlfex.hermes.service.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.model.LoanRepay;
import com.jlfex.hermes.repository.LoanRepayRepository;
import com.jlfex.hermes.service.RepayService;

/**
 * @author 自动逾期计算，更新loan_repay的状态为逾期的各个逾期费用的计算
 */
@Component("autoOverdueCalcJob")
public class AutoOverdueCalc extends Job {

	@Autowired
	private RepayService repayService;
	@Autowired
	private LoanRepayRepository loanRepayRepository;

	@Override
	public Result run() {
		String var ="自动逾期计算JOB";
		try {
			long beginTime = System.currentTimeMillis();
			Logger.info(var+"开始.....");
			List<LoanRepay> loanRepayList = repayService.findByStatus(LoanRepay.RepayStatus.OVERDUE);
			boolean isUpdateData = false;
			StringBuilder remarks = new StringBuilder();
			for (int i = 0; i < loanRepayList.size(); i++) {
				LoanRepay loanRepay = loanRepayList.get(i);
				try {
					isUpdateData = true;
					boolean success = repayService.overdueCalc(loanRepay);
					if (success) {
						LoanRepay loanRepayNew = loanRepayRepository.findOne(loanRepay.getId());
						// 计算逾期等级
						Integer rank = (loanRepayNew.getOverdueDays() - 1) / 30 + 1;
						remarks.append("借款编号," + loanRepayNew.getLoan().getLoanNo() + "逾期" + loanRepayNew.getOverdueDays() + "天,逾期状态M" + rank + "天,罚息" + loanRepayNew.getOverdueInterest() + "元,逾期管理费"
								+ loanRepayNew.getOverduePenalty() + "元 \r\n");
					} else {
						remarks.append("借款编号," + loanRepay.getLoan().getLoanNo() + "逾期计算失败。\r\n");
						Logger.error("更新逾期相关金额失败，借款编号%s", loanRepay.getLoan().getLoanNo());
					}

				} catch (ServiceException ex) {
					Logger.error(ex.getMessage(), ex);
				}
			}
			Logger.info(var+"完成. 耗时  %s millisecond.", (System.currentTimeMillis() - beginTime));
			if (isUpdateData) {
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

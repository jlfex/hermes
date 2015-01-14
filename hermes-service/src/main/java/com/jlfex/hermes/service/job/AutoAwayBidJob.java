package com.jlfex.hermes.service.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.Loan.LoanKinds;
import com.jlfex.hermes.model.LoanAudit;
import com.jlfex.hermes.model.LoanAudit.Status;
import com.jlfex.hermes.model.LoanAudit.Type;
import com.jlfex.hermes.model.LoanLog;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.repository.LoanLogRepository;
import com.jlfex.hermes.service.CreditorInfoService;
import com.jlfex.hermes.service.InvestService;
import com.jlfex.hermes.service.LoanAuditService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.ProductService;

@Component("autoAwayBidJob")
public class AutoAwayBidJob extends Job {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	// 正在招标中的Cache的list，其中list里的每个loan对象的BusinessDate属性都 放置截至日期，精确到秒
	private static final String CACHE_LOAN_BID_LIST_NAME = "com.jlfex.hermes.cache.loan.bid.list";

	@Autowired
	private LoanService loanService;
	@Autowired
	private LoanAuditService loanAuditService;
	@Autowired
	private ProductService productService;
	@Autowired
	private InvestService investService;
	@Autowired
	private LoanLogRepository loanLogRepository;
	@Autowired
	private CreditorInfoService creditorInfoService;

	@SuppressWarnings("unchecked")
	@Override
	public Result run() {

		try {
			long beginTime = System.currentTimeMillis();
			logger.info("batch autoAwayBidJob start.");
			if (Caches.get(CACHE_LOAN_BID_LIST_NAME) == null) {
				List<Loan> loans = loanService.findByStatus(Loan.Status.BID);// 查找状态为"招标中"的借款记录
				List<Loan> loanStartInvest = new ArrayList<Loan>();
				for (int i = 0; i < loans.size(); i++) {
					Loan loan = loans.get(i);
					LoanAudit loanAudit = loanAuditService.findByLoanAndTypeAndStatus(loan, Type.FINALL_AUDIT, Status.PASS);// 根据loan，审核类型，审核结果查找借款审核记录
					Product product = loan.getProduct();// 根据loan获取产品信息
					CrediteInfo crediteInfo = creditorInfoService.findById(loan.getCreditorId());// 根据loan中的creditorId获取债权信息
					// 判断普通标还是外部债权标，然后往loan中添加业务日期，也就是招标截至日期
					if (loan.getLoanKind().equals(LoanKinds.NORML_LOAN) && loanAudit != null && product != null) {
						Date businessDate = DateUtils.addDays(loanAudit.getDatetime(), product.getDeadline());
						loan.setBusinessDate(businessDate);
					} else if (loan.getLoanKind().equals(LoanKinds.OUTSIDE_ASSIGN_LOAN) && crediteInfo != null) {
						loan.setBusinessDate(crediteInfo.getBidEndTime());
					}
					loanStartInvest.add(loan);
				}
				// 由于每天有可能有新的终审通过，开始招标的借款，故设置有效期为1天
				Caches.set(CACHE_LOAN_BID_LIST_NAME, loanStartInvest, "1d");
			}
			// 检查是否有更新的数据
			boolean isUpdateData = false;
			StringBuilder remarks = new StringBuilder();
			Date now = new Date();
			// 读取缓存中的借款信息
			List<Loan> loansNew = Caches.get(CACHE_LOAN_BID_LIST_NAME, List.class);
			for (int i = 0; i < loansNew.size(); i++) {
				Loan loan = loansNew.get(i);
				if (loan.getBusinessDate() != null && loan.getBusinessDate().before(now)) { // 招标截至日起不为空且在当前时间之前
					isUpdateData = true;
					boolean success = investService.processAutoBidFailure(loan);
					if (success) {
						LoanLog loanLog = new LoanLog();
						loanLog.setUser(loan.getUser().getId());
						loanLog.setLoan(loan);
						loanLog.setDatetime(new Date());
						loanLog.setType(com.jlfex.hermes.model.LoanLog.Type.AUTO_FAILURE);
						loanLog.setAmount(loan.getAmount());
						loanLog.setRemark("自动流标成功");
						loanLogRepository.save(loanLog);
						remarks.append("编号为" + loan.getLoanNo() + "的借款于" + Calendars.date() + "自动流标。\r\n");

					} else {
						remarks.append("编号为" + loan.getLoanNo() + "的借款于" + Calendars.date() + "自动流标失败。\r\n");
						logger.error("更新借款状态失败，借款编号%s", loan.getLoanNo());
					}
				} else {
					continue;
				}
			}
			logger.info("batch autoAwayBidJob finished. take %s millisecond.", (System.currentTimeMillis() - beginTime));
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

	/**
	 * 
	 * @description: 平台内债权标的招标结束日期
	 * @author: lishunfeng
	 */
	public Date getBidEndDate(Date dateTime, Integer deadline) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(dateTime);
		logger.info("终审通过的时间是：" + date);

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, deadline);// deadline为增加的天数
		dateTime = ca.getTime();
		// String backTime = format.format(dateTime);
		logger.info("平台内债权标的招标结束日期：" + dateTime);
		return dateTime;
	}

}

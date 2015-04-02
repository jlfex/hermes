package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.InvestProfit;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.LoanOverdue;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.InvestProfitRepository;
import com.jlfex.hermes.repository.LoanOverdueRepository;
import com.jlfex.hermes.service.InvestProfitService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.pojo.InvestProfitInfo;
import com.jlfex.hermes.service.repay.RepayMethod;

/**
 * 
 * 理财收益业务实现
 * 
 * @author chenqi
 * @version 1.0, 2013-12-24
 * @since 1.0
 */
@Service
@Transactional
public class InvestProfitServiceImpl implements InvestProfitService {

	@Autowired
	private InvestProfitRepository investProfitRepository;
	@Autowired
	private CommonRepository commonRepository;
	/** 借款逾期信息仓库 */
	@Autowired
	private LoanOverdueRepository loanOverdueRepository;
	@Autowired
	private RepayService repayService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestProfitService#save(com.jlfex.hermes
	 * .model.InvestProfit)
	 */
	@Override
	public InvestProfit save(InvestProfit investProfit) {

		// 保存数据并返回
		return investProfitRepository.save(investProfit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.InvestProfitService#LoadById(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public InvestProfit loadById(String id) {
		return investProfitRepository.findOne(id);
	}

	@Override
	public BigDecimal loadSumAllProfitByUserAndInStatus(User user, String... status) {

		return investProfitRepository.loadSumAllProfitByUserAndInStatus(user, Arrays.asList(status));
	}

	@Override
	public BigDecimal loadInterestSumByUserAndInStatus(User user, String... status) {
		return investProfitRepository.loadInterestSumByUserAndInStatus(user, Arrays.asList(status));
	}

	@Override
	public BigDecimal loadOverdueInterestSumByUserAndInStatus(User user, String... status) {
		return investProfitRepository.loadOverdueInterestSumByUserAndInStatus(user, Arrays.asList(status));
	}



	/**
	 * 此方法用于获取已收记录及最新一期待收
	 * 
	 * @param loan
	 * @return
	 */
	public List<InvestProfitInfo> getInvestProfitRecords(Invest invest) {
		List<InvestProfit> investProfitList = investProfitRepository.findByInvest(invest);
		List<InvestProfit> investProfitRecords = new ArrayList<InvestProfit>();

		int count = investProfitList.size();
		if (count > 0) {
			InvestProfit unRepay = null;
			investProfitRecords.add(unRepay);
			for (InvestProfit investProfit : investProfitList) {
				// 假如正常还款或者逾期还款，
				if (InvestProfit.Status.ALREADY.equals(investProfit.getStatus()) || InvestProfit.Status.ADVANCE.equals(investProfit.getStatus())
						|| InvestProfit.Status.OVERDUE.equals(investProfit.getStatus())) {
					investProfitRecords.add(investProfit);
					count--;
				}
			}
			// 判断是否存在还未还款的 ，等于0表示全都已收款(包括已收款和已收逾期款和已收垫付款)了
			if (count > 0) {
				// 获取当期未还
				unRepay = investProfitList.get(count - 1);
				Date now = new Date();
				// 如果计划时间小于等于当前时间，为正常还款
				if (unRepay.getLoanRepay().getPlanDatetime().getTime() <= now.getTime()) {
					// 计算逾期天数
					int overdueDay = RepayServiceImpl.getOverdueDays(unRepay.getLoanRepay().getPlanDatetime(), now);
					// 计算逾期等级
					Integer rank = (overdueDay - 1) / 30 + 1;
					LoanOverdue loanOverdue = loanOverdueRepository.findByLoanAndRank(unRepay.getLoanRepay().getLoan(), rank);
					// 根据逾期等级获取费率，如获取为空，则表示坏账，获取为非空，采用获取费率计算
					if (loanOverdue == null) {
						loanOverdue = loanOverdueRepository.findByLoanAndRank(unRepay.getLoanRepay().getLoan(), 0);
					}
					RepayMethod repayMethod = repayService.getRepayMethod(unRepay.getLoanRepay().getLoan().getRepay().getId());
					// 计算借款人逾期违约金
					// BigDecimal overduePenalty = repayMethod.getOverduePenalty(overdueDay,loan.getPeriod() -
					// unRepay.getSequence() + 1, loan.getManageFee(),loanOverdue.getPenalty());
					// 计算逾期罚息
					int term = unRepay.getLoanRepay().getLoan().getPeriod() - unRepay.getLoanRepay().getSequence() + 1;
					BigDecimal loanAmount = unRepay.getLoanRepay().getLoan().getAmount();
					BigDecimal loanOverdueInterest = BigDecimal.ZERO;
					if(loanOverdue != null){
						loanOverdueInterest = loanOverdue.getInterest();
					}
					BigDecimal overdueInterest = repayMethod.getOverdueInterest(overdueDay, term, loanAmount , loanOverdueInterest);
					if(Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN.equals(unRepay.getLoanRepay().getLoan().getLoanKind())){
						//债权表 暂不计算逾期 后期改造
						overdueDay = 0;
						overdueInterest = new BigDecimal(String.valueOf(0));
					}
					unRepay.getLoanRepay().setOverdueDays(overdueDay);
					unRepay.setOverdueInterest(overdueInterest);
				}
				investProfitRecords.set(0, unRepay);
			} else {
				investProfitRecords.remove(0);
			}
		}
		List<InvestProfitInfo> investProfitInfoRecords = new ArrayList<InvestProfitInfo>();
		for (InvestProfit investProfit : investProfitRecords) {
			InvestProfitInfo investProfitInfo = new InvestProfitInfo();
			investProfitInfo.setSequence(Strings.toString(investProfit.getLoanRepay().getSequence()));
			investProfitInfo.setPlanDatetime(investProfit.getLoanRepay().getPlanDatetime());
			investProfitInfo.setPeriod(Strings.toString(investProfit.getLoanRepay().getLoan().getPeriod()));
			investProfitInfo.setAmount(Numbers.toCurrency(investProfit.getAmount().doubleValue()));
			investProfitInfo.setInterest(Numbers.toCurrency(investProfit.getInterest().doubleValue()));
			investProfitInfo.setPrincipal(Numbers.toCurrency(investProfit.getPrincipal().doubleValue()));
			investProfitInfo.setOverdueInterest(Numbers.toCurrency(investProfit.getOverdueInterest().doubleValue()));
			investProfitInfo.setOverdueDays(Strings.toString(investProfit.getLoanRepay().getOverdueDays()));
			investProfitInfo.setStatus(investProfit.getStatus());
			investProfitInfoRecords.add(investProfitInfo);
		}
		return investProfitInfoRecords;
	}
	
	@Override
	public InvestProfit sumAllProfitByAssignLoan(User user, List<String> loanKinds ,String... profitState) {
		return investProfitRepository.sumAllProfitByAssignLoan( Arrays.asList(profitState),  loanKinds, user);
	}
	
	@Override
	public List<InvestProfit> queryByInvest(Invest invest) throws Exception{
		return  investProfitRepository.findByInvest(invest);
	}
	

}

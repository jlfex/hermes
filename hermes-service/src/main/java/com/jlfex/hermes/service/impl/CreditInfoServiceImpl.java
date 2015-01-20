package com.jlfex.hermes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.model.Rate;
import com.jlfex.hermes.model.Repay;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.repository.CreditInfoRepository;
import com.jlfex.hermes.repository.CreditorRepayPlanRepository;
import com.jlfex.hermes.repository.RateRepository;
import com.jlfex.hermes.service.CreditInfoService;
import com.jlfex.hermes.service.LoanService;
import com.jlfex.hermes.service.ProductService;
import com.jlfex.hermes.service.RepayService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.common.Pageables;


/**
 * 债权 信息 管理
 * @author Administrator
 *
 */
@Service
@Transactional
public class CreditInfoServiceImpl  implements CreditInfoService {

	@Autowired
	private CreditInfoRepository creditInfoRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ProductService productService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private CreditorRepayPlanRepository creditorRepayPlanRepository;
	
	private static final String INIT_CREDIT_LEND_FEE = "0.02000000";  //债权标：借款手续费率
	private static final String INIT_CREDIT_RISK_FEE = "0.03000000";  //债权标：风险金费率   
	private static final String INIT_CREDIT_REPAY_WAY = "等额本息";  
	
	
	@Override
	public List<CrediteInfo> findAll() {
	  return creditInfoRepository.findAll();
	}

	@Override
	public void save(CrediteInfo crediteInfo) throws Exception {
		creditInfoRepository.save(crediteInfo);
	}


	@Override
	public List<CrediteInfo>  saveBatch(List<CrediteInfo> list) throws Exception {
		return creditInfoRepository.save(list);
	}

	@Override
	public CrediteInfo findByCreditorAndCrediteCode(Creditor creditor ,String crediteCode)  {
		try{
			if(Strings.empty(crediteCode)){
				return null ;
			}
			return creditInfoRepository.findByCreditorAndCrediteCode(creditor, crediteCode.trim());
		}catch(Exception e){
			Logger.info("根据债权人+债权编号 获取债权信息异常", e);
			return  null;
		}
	}
	/**
	 * 债权 列表
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<CrediteInfo>  queryByCondition(CrediteInfo creditInfo,String page, String size) throws Exception{
		 Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, "15")));
		 final  String crediteCode = creditInfo!=null?creditInfo.getCrediteCode():"";
		 final  String status = creditInfo!=null?creditInfo.getStatus():"";
		 return  creditInfoRepository.findAll(new Specification<CrediteInfo>() {
			@Override
			public Predicate toPredicate(Root<CrediteInfo> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				List<Predicate> p = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(crediteCode)) {
					p.add(cb.equal(root.get("crediteCode"), crediteCode));
				}
				if(StringUtils.isNotEmpty(status)){
					p.add(cb.equal(root.get("status"), status));
				}
				return cb.and(p.toArray(new Predicate[p.size()]));
			}
		},pageable);
	}

	/**
	 * 根据id 获取债权信息
	 */
	@Override
	public CrediteInfo findById(String id) throws Exception {
		return creditInfoRepository.findOne(id);
	}
    /**
     * 发售 债权
     */
	@Override
	public boolean sellCredit(CrediteInfo entity) throws Exception {
		List<CreditRepayPlan> updateRepayList = new ArrayList<CreditRepayPlan>();
		Repay repay = queryRepayObj(INIT_CREDIT_REPAY_WAY);
		Loan loan = buildLoan(entity, repay);
		creditInfoRepository.save(entity);
		Loan loanNew = loanService.save(loan);
		if(loanNew != null){
			entity.setStatus(CrediteInfo.Status.ASSIGNING);
			creditInfoRepository.save(entity);
			// 更新债权明细 状态
			List<CreditRepayPlan> repayList = creditorRepayPlanRepository.findByCrediteInfo(entity);
			for(CreditRepayPlan item : repayList){
				item.setStatus(CreditRepayPlan.Status.WAIT_BID);
				updateRepayList.add(item);
			}
			creditorRepayPlanRepository.save(updateRepayList);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 创建 债权标  虚拟产品
	 * @param repay
	 * @return
	 */
	public Product  generateVirtualProduct(Repay repay) throws Exception{
		
		Boolean existsFlag = false;
		Product virtualProcut = new  Product();
		try{
			List<Product> lists = productService.findByStatusIn(Product.Status.VIRTUAL_CREDIT_PROCD);
			if(lists!=null && lists.size()>0){
				virtualProcut = lists.get(0);
				existsFlag = true;
			}
		}catch(Exception e){ existsFlag = false; }
		if(!existsFlag){
			virtualProcut.setRepay(repay);
			virtualProcut.setName("债权标虚拟产品");
			virtualProcut.setCode("00");
			virtualProcut.setAmount("0");
			virtualProcut.setPeriod("0");
			virtualProcut.setRate("0");
			virtualProcut.setDeadline(0);
			virtualProcut.setManageFee(BigDecimal.ZERO);
			virtualProcut.setManageFeeType("");
			virtualProcut.setStatus(Product.Status.VIRTUAL_CREDIT_PROCD);
			virtualProcut.setDescription("债权标虚拟产品配置初始化");
			Product savedProduct=  productService.save(virtualProcut);
			if(savedProduct !=null){
				Logger.info("债权标虚拟产品配置初始化成功");
				virtualProcut = savedProduct;
				// 设置产品对应的 费率
				initCreditRate(savedProduct, new BigDecimal(INIT_CREDIT_LEND_FEE), Rate.RateType.LOAN);
				initCreditRate(savedProduct, new BigDecimal(INIT_CREDIT_RISK_FEE), Rate.RateType.RISK);
			}
		}
		return virtualProcut;
	}
	/**
	 * 债权表获取 偿还方式
	 * 默认 等额本息
	 * @param repayWay
	 * @return
	 * @throws Exception
	 */
	public Repay  queryRepayObj(String repayWay) throws Exception{
		List<Repay> repayList = repayService.findByNameAndStatusIn(repayWay, Repay.Status.VALID);
		if(repayList == null || repayList.size() == 0){
			Logger.warn("根据 偿还方式="+repayWay+"状态="+Repay.Status.VALID+",获取对象为空");
			return null;
		}else{
			return repayList.get(0);
		}
	}
	/**
	 * 债权标 组装
	 * @param entity
	 * @param repay
	 * @return
	 * @throws Exception
	 */
	public Loan buildLoan( CrediteInfo entity, Repay repay) throws Exception{
		String loanDesc = "外部债权标";
		Loan loan = new Loan();
		loan.setProduct(generateVirtualProduct(repay));
		loan.setRepay(repay);
		loan.setAmount(entity.getAmount());
		loan.setPeriod(entity.getPeriod());
		loan.setRate(entity.getRate());
		loan.setPurpose(entity.getPurpose());
		loan.setDescription(entity.getRemark());
		loan.setLoanKind(Loan.LoanKinds.OUTSIDE_ASSIGN_LOAN);
		loan.setStatus(Loan.Status.BID);
		loan.setUser(entity.getCreditor().getUser());
		loan.setDeadline(entity.getDeadLine()); //招标期限
		loan.setCreditInfoId(entity.getId()); 
		loan.setManageFeeType("00"); //00: 百分比  01：具体金额
		loan.setProceeds(BigDecimal.ZERO);
		loan.setManageFee(BigDecimal.ZERO); 
		loan.setDescription(loanDesc);
		return loan ;
	}
	
	/**
	 * 外部债权表： 费率初始化
	 * @param product
	 * @param rateVal
	 * @param type
	 * @throws Exception
	 */
	public void initCreditRate(Product product,BigDecimal rateVal, String type) throws Exception{
		Rate rate = new Rate();
		rate.setProduct(product);
		rate.setRate(rateVal);
		rate.setType(type);
		rate = rateRepository.save(rate);
		if(rate!=null){
		   Logger.info("外部债权标虚拟产品:"+product.getPeriod()+",费率类型="+type+"),初始化成功");
		}
	}
	
    
}

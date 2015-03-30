package com.jlfex.hermes.service.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.repository.finance.FinanceOrderRepository;


/**
 * 理财产品 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class FinanceOrderServiceImpl implements  FinanceOrderService {

	@Autowired
	private FinanceOrderRepository financeOrderRepository;

	/**
	 * 保存
	 */
	@Override
	public FinanceOrder save(FinanceOrder financeOrder) throws Exception {
		return financeOrderRepository.save(financeOrder);
	}
   /**
   * 根据理财产品编号  获取理财产品
   */
	@Override
	public FinanceOrder queryByUniqId(String uniqId) throws Exception {
		return financeOrderRepository.findByUniqId(uniqId);
	}
	/**
	 * 根据理财产品ID 获取理财产品信息
	 */
	@Override
	public FinanceOrder queryById(String id) throws Exception {
		return financeOrderRepository.findById(id);
	}
		
	
	
	
	
	

}

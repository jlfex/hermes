package com.jlfex.hermes.service.order.jlfex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.yltx.JlfexOrder;
import com.jlfex.hermes.repository.order.jlfex.JlfexOrderRepository;


/**
 * JLFEX 订单 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class JlfexOrderServiceImpl implements  JlfexOrderService {

	@Autowired
	private JlfexOrderRepository jlfexOrderRepository;
	
	/**
	 * 保存订单 
	 */
	@Override
	public JlfexOrder saveOrder(JlfexOrder jlfexOrder) throws Exception {
		return jlfexOrderRepository.save(jlfexOrder);
	}
    /**
     * 根据支付状态查询订单
     */
	@Override
	public List<JlfexOrder> queryOrderByPayStatus(List<String> satatusList) throws Exception {
		return  jlfexOrderRepository.findByPayStatusIn(satatusList) ;
	}
	@Override
	public JlfexOrder findByInvest(String investId) throws Exception {
		return jlfexOrderRepository.findByInvest(investId);
	}
	
	

}

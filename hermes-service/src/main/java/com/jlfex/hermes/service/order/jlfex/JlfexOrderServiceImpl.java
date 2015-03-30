package com.jlfex.hermes.service.order.jlfex;

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
	
	

}

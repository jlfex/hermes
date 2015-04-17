package com.jlfex.hermes.service.order.jlfex;
import java.util.List;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.yltx.JlfexOrder;

/**
 * JLFEX 订单 业务
 * @author Administrator
 *
 */
public interface JlfexOrderService {
	/**
	 * 保存订单
	 * @param jlfexOrder
	 * @return
	 * @throws Exception
	 */
	public JlfexOrder  saveOrder(JlfexOrder jlfexOrder) throws Exception  ;
	/**
	 * 根据支付状态
	 * @param satatusList
	 * @return
	 * @throws Exception
	 */
	public List<JlfexOrder>  queryOrderByPayStatus(List<String> satatusList) throws Exception  ;

	/**
	 * 根据理财信息ID 查询
	 * @param investId
	 * @return
	 * @throws Exception
	 */
	public JlfexOrder  findByInvest(String investId);
	/**
	 * 根据理财人 + 支付状态  获取订单
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<JlfexOrder>  queryByInvestUserAndPayStatus(User user, String payStatus) throws Exception  ;


}

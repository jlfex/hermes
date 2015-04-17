package com.jlfex.hermes.service.api.yltx;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import com.jlfex.hermes.model.yltx.Asset;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.service.pojo.yltx.request.OrderPayRequestVo;
import com.jlfex.hermes.service.pojo.yltx.response.QueryFinanceRspVo;

/**
 * 易联天下 对接接口
 * @author Administrator
 * @since 20150311
 */
public interface JlfexService {
	
	/**
	 * 查询理财产品
	 * @param financeProductStatus 理财产品状态 最大10位
	 * @param createDate           日期 yyyy-MM-dd
	 * @param pageSize             每次抓取条数
	 * @param pageNum              第几页
	 * @return
	 * @throws Exception
	 */
	public  String   queryFinanceOrder(String financeProductStatus,String createDate, int pageSize, int pageNum) throws Exception;
	
	/**
	 * 下单并支付
	 * @return
	 * @throws Exception
	 */	
	public  Map<String,String>   createOrderAndPay(OrderPayRequestVo vo) throws  Exception;
	
    /**
     * 撤销订单
     * @param orderCode  编号最长13位
     * @return
     * @throws Exception
     */
	public  String   revokeOrder(String orderCode)  throws  Exception;
	
	/**
	 * * 订单查询
	 * @param orderCodes  可多个以”,”分隔。最多支持100个编号，单个编号最长13位
	 * @return
	 * @throws Exception
	 */
	public  String   queryOrderStatus(String orderCodes) throws Exception;
	
	/**
	 *  客户协议查询
	 * @param fileId  最大长度19
	 * @throws Exception
	 */
	public ByteArrayOutputStream queryProtocolFile(String fileId) throws Exception ;
	
	/**
	 * 还款计划查询接口
	 * @param code  资产编号
	 * @param type  1、理财产品；2、资产；3、订单
	 * @throws Exception
	 */
	public  String  queryRepayPlan(String code, String type) throws Exception ;
	
	/**
	 * 理财产品发售标
	 * @param financeOrder
	 * @return
	 * @throws Exception
	 */
	public  boolean autoBuildLoan(FinanceOrder  financeOrder) throws Exception;
	
	/**
	 * 创建理财产品
	 * @param financeOrderList
	 * @param queryFunanceOrderVo
	 * @throws Exception
	 */
	public void buildFinanceOrder(List<FinanceOrder> financeOrderList, QueryFinanceRspVo queryFunanceOrderVo) throws Exception;
	

	/**
	 * 创建 债权信息
	 * @param savedAssetLists
	 * @param financeOrder
	 * @throws Exception
	 */
	public void transCreditInfo(List<Asset> savedAssetLists, FinanceOrder financeOrder) throws Exception;

	/**
	 * 理财产品发售
	 * @param financeOrderList
	 * @return
	 * @throws Exception
	 */
	public String sellCreditDeal( FinanceOrder financeOrder) throws Exception;
	/**
	 * 募资完成 更新理财产品
	 * @param financeOrderList
	 * @return
	 * @throws Exception
	 */
	public boolean updateFinishedFinance(FinanceOrder obj) throws Exception;
	

}

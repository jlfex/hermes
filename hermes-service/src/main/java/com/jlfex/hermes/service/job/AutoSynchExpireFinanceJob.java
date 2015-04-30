package com.jlfex.hermes.service.job;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.service.api.yltx.JlfexService;
import com.jlfex.hermes.service.finance.FinanceOrderService;
import com.jlfex.hermes.service.pojo.yltx.response.QueryFinanceRspVo;

/**
 * 自动同步 募资截止理财产品JOB
 */
@Component("autoSynchExpireFinanceJob")
public class AutoSynchExpireFinanceJob extends Job {

	
	@Autowired
	private  FinanceOrderService  financeOrderService;
	@Autowired
	private  JlfexService  jlfexService;
	
	@Override
	public Result run() {
		String var = "自动同步募资截止理财产品JOB：";
		try {
			Logger.info(var+Calendars.format(HermesConstants.FORMAT_19)+"开始....");
			try{
				List<FinanceOrder> financeLists = getExpireFinanceList();
				if(financeLists ==null || financeLists.size() == 0){
					Logger.info(var+"hermes侧：当天"+Calendars.format(HermesConstants.FORMAT_10)+",没有起息的理财产品,不进行同步操作");
					return new Result(true, false, var+"处理结束");
				}
				List<FinanceOrder> waitDealList = new  ArrayList<FinanceOrder>();
				synchWaitDealList(var, financeLists, waitDealList);
				// 更新理财产品
				if(waitDealList.size() > 0){
					for(FinanceOrder order: waitDealList){
						boolean result = false;
						try{
							result = jlfexService.updateFinishedFinance(order);
							Logger.info(var+"理财产品id="+order.getUniqId()+",实际募资金额、理财产品还款计划、子资产还款计划 更新"+ (result?"成功":"失败") );
						} catch (Exception ee) {
							Logger.error(var+"异常",ee);
							continue;
						}
					}
				}else{
					Logger.info(var+"没有待处理的理财产品");
				}
			}catch(Exception e){
				Logger.error(var+"异常：", e);
				return  new Result(true, false, var+"异常"+e.getMessage());
			}
			return new Result(true, false, var+"处理结束");
		} catch (Exception e) {
			throw  new ServiceException(e.getMessage(), e);
		}

	}
	/**
	 * 同步最新的理财产品
	 * @param var
	 * @param financeLists
	 * @param waitDealList
	 */
	public void synchWaitDealList(String var, List<FinanceOrder> financeLists, List<FinanceOrder> waitDealList) {
		for(FinanceOrder obj: financeLists){
			try{
				String  uniqId = obj.getUniqId();
				String  queryDate = Calendars.format(HermesConstants.FORMAT_10 ,obj.getCreateTime());
				//同步理财产品
				String jsonOrder = jlfexService.queryFinanceOrder(null, queryDate, HermesConstants.JL_PAGE_SIZE, HermesConstants.JL_PAGE_NUM);
				QueryFinanceRspVo queryFunanceOrderVo = JSON.parseObject(jsonOrder,QueryFinanceRspVo.class);
				if(queryFunanceOrderVo != null ){
					List<FinanceOrder> financeOrderList = new  ArrayList<FinanceOrder>();
					jlfexService.buildFinanceOrder(financeOrderList, queryFunanceOrderVo);
					for(FinanceOrder synchObj: financeOrderList){
						if(synchObj.getUniqId().equals(uniqId)){
							waitDealList.add(synchObj);
						}
					}
				}
			}catch (Exception ee) {
				Logger.error(var+"异常", ee);
				continue;
			}
		}
	}
    /**
     * 获取系统 起息日 的理财产品 
     * @return
     * @throws ParseException
     */
	public List<FinanceOrder> getExpireFinanceList() throws ParseException {
		Date expireDate = Calendars.parseDate(Calendars.format(HermesConstants.FORMAT_10, new Date()));
		List<String> statusList = new ArrayList<String>();
		statusList.add(HermesConstants.FINANCE_FUNDRAISING );
		statusList.add(HermesConstants.FINANCE_WAIT_PAY );
		//获取起息的理财产品
		List<FinanceOrder> financeLists =  financeOrderService.queryByDateOfValueAndStatusIn(expireDate, statusList);
		return financeLists;
	}

}

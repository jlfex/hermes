package com.jlfex.hermes.service.job;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.jlfex.hermes.service.pojo.yltx.response.QueryFinanceRspVo;

/**
 * 自动同步  T-1天  理财产品JOB
 */
@Component("autoSynchFinanceJob")
public class AutoSynchFinanceJob extends Job {

	
	@Autowired
	private  JlfexService  jlfexService;

	
	@Override
	public Result run() {
		String var = "自动同步T-1天理财产品JOB：";
		try {
			Logger.info(var+Calendars.format(HermesConstants.FORMAT_19)+"开始....");
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			String  queryDate = Calendars.format(HermesConstants.FORMAT_10 ,calendar.getTime());
			QueryFinanceRspVo queryFunanceOrderVo = null;
			try{
				String jsonOrder = jlfexService.queryFinanceOrder(null, queryDate, HermesConstants.JL_PAGE_SIZE, HermesConstants.JL_PAGE_NUM);
				queryFunanceOrderVo = JSON.parseObject(jsonOrder,QueryFinanceRspVo.class);
				if(queryFunanceOrderVo == null){
					return new Result(true, false, "自动同步理财产品JOB返回为空，请查看接口日志");
				}
				//数据处理
				List<FinanceOrder> financeOrderList = new  ArrayList<FinanceOrder>();
				jlfexService.buildFinanceOrder(financeOrderList, queryFunanceOrderVo);
				for(FinanceOrder obj: financeOrderList){
					try{
						String  resultVal = null;
						int assetSize = obj.getAssetsList().size();
						if(assetSize>1){
							Logger.info("业务规则：JOB只同步含一个资产的理财产品，理财产品id="+obj.getUniqId()+",含有子资产数="+assetSize);
							continue ;
						}
						String  financeStatus = obj.getStatus().trim();
						if( HermesConstants.FINANCE_FINISHED.equals(financeStatus)){
							// 更新理财产品
							boolean result = jlfexService.updateFinishedFinance(obj);
							if(result){
							   Logger.info("理财产品id="+obj.getUniqId()+",实际募资金额、理财产品还款计划、子资产还款计划 更新完成");	
							}
						}else if(HermesConstants.FINANCE_FUNDRAISING .equals(financeStatus) || 
								 HermesConstants.FINANCE_WAIT_PAY.equals(financeStatus)){
						    // 发售理财产品
							String  resultCode = jlfexService.sellCreditDeal(obj);
							if("00".equals(resultCode)){
								resultVal = "发售失败";
							}else if("01".equals(resultCode)){
								resultVal = "发售成功";
							}else if("02".equals(resultCode)){
								resultVal = "不需重复发售";
							}else{
								resultVal = "发售失败";
							}
							Logger.info("理财产品id="+obj.getUniqId()+"处理结果： "+resultVal);
						}else{
							Logger.info("当前理财产品id="+obj.getUniqId()+",状态="+obj.getStatus()+", 跳过处理");
						}
					}catch(Exception e){
						Logger.error(var+" 理财产品id="+obj.getUniqId()+"处理异常：",e);
						continue;
					}
				}
			}catch(Exception e){
				Logger.error("自动同步理财产品JOB 异常：", e);
				return  new Result(true, false, "自动同步理财产品JOB 异常"+e.getMessage());
			}
			return new Result(true, false, "自动同步 理财产品JOB处理结束");
		} catch (Exception e) {
			throw  new ServiceException(e.getMessage(), e);
		}

	}

}

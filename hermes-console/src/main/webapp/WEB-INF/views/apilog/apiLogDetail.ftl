<div class="panel panel-primary">
        <div class="panel-heading">查看日志明细</div>
    	<div class="panel-body">
              <div role="tabpanel" class="tab-pane active" id="basicInfo">        
                <div class="row">
                    <div class="col-xs-4">
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">平台编码：</span>${(apiLog.apiConfig.platCode)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">请求流水号：</span>${(apiLog.serialNo)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">接口名称：</span>${(apiLog.interfaceName)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">请求时间：</span>${(apiLog.requestTime)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">请求报文：</span>${(apiLog.requestMessage)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">响应时间：</span>${(apiLog.responseTime)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">响应报文：</span>${(apiLog.responseMessage)!''}</div>                        
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">处理标识：</span>${(apiLog.dealResultName)!''}</div>                        
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">异常信息：</span>${(apiLog.exception)!''}</div>                                                
                    </div>
               </div>             
             </div> 
        </div>
  </div>
<div class="panel panel-primary">
        <div class="panel-heading">查看用户日志明细</div>
    	<div class="panel-body">
              <div role="tabpanel" class="tab-pane active" id="basicInfo">        
                <div class="row">
                    <div class="col-xs-4">
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">账户：</span>${(userLog.user.account)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">邮件：</span>${(userLog.user.email)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">类型：</span>${(userLog.typeName)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">备注：</span>${(userLog.remark)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">创建时间：</span>${(userLog.datetime)!''}</div>
                    </div>
               </div>             
             </div> 
        </div>
  </div>
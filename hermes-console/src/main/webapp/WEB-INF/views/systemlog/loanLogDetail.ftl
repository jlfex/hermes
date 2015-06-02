<div class="panel panel-primary">
        <div class="panel-heading">查看投标日志明细</div>
    	<div class="panel-body">
              <div role="tabpanel" class="tab-pane active" id="basicInfo">        
                <div class="row">
                    <div class="col-xs-4">
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">标编号：</span>${(loanLog.loan.loanNo)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">用户账户：</span>${(loanLog.loan.user.email)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">类型：</span>${(loanLog.typeName)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">创建时间：</span>${(loanLog.datetime)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">金额：</span>${(loanLog.amount)!''}</div>
                        <div class="spanblock" style="padding-top:5px;padding-bottom:5px;"><span class="wd_100" style="font-weight:bold;">备注：</span>${(loanLog.remark)!''}</div>
                    </div>
               </div>             
             </div> 
        </div>
  </div>
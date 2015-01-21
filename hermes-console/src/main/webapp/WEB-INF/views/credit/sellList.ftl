 <div class="panel panel-primary">
        <div class="panel-heading">债权发售列表</div>
 </div>
  <div class="panel-body">
            <form id="creditSellForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="account">债权人名称</label>
                        <input id="creditorName" name="creditorName" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="cellphone">债权编号</label>
                        <input id="crediteCode" name="crediteCode" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="realname">借款类型</label>
                        <select id="status" name="status" class="form-control">
                            <option value="">学生贷</option>
                            <option value="00">房贷</option>
                            <option value="10">车贷</option>
                        </select>
                    </div>
                    <div class="col-xs-1 hm-col form-group">
                        <label for="beginDate">导入日期</label>
                        <input readonly="" id="beginDate" name="beginDate"  class="form-control" type="text">
                    </div>
                    <div class="col-xs-1 hm-col form-group">
                        <label for="endDate">&nbsp;</label>
                        <input readonly="" id="endDate" name="endDate"  class="form-control" type="text">
                    </div>

                    <div class="col-xs-1 hm-col form-group">
                       
                    </div>
                    <div class="col-xs-1 hm-col form-group">
                       <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                        <input id="page" name="page" value="0" type="hidden">
                    </div>
            
                </div>
 
            </form>
        </div>

        <div id="data" style="display:block">
            <table id="sellListTable" class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th class="align-center">债权人编号</th>
                        <th class="align-center">债权编号</th>
                        <th class="align-center">债权类型</th>
                        <th class="align-center">借款金额</th>
                        <th class="align-center">年利率</th>
                        <th class="align-center">期限</th>
                        <th class="align-center">借款用途</th>
                        <th class="align-center">还款方式</th>
                        <th class="align-center">债权到期日</th>
                        <th class="align-center">放款日期</th>
                        <th class="align-center">导入日期</th>
                        <th class="align-center">操作人</th>
                        <th class="align-center">状态</th>
                        <th class="align-center">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <#if sellList??>
                        <#if sellList.numberOfElements == 0>
					<tr>
						<td colspan="14" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list (sellList.content)?sort_by("createTime")?reverse as l>
							<tr>
	                        <td class="align-center">${(l.creditor.creditorNo)!''}</td> 
	                        <td class="align-center">${(l.crediteCode)!''}</td> 
	                        <td class="align-center">${(l.crediteType)!''}</td> 
	                        <td class="align-center">${(l.amount)!''}</td> 
	                        <td class="align-center">${(l.rate!'')?string.percent}</td> 
	                        <td class="align-center">${(l.period)!''}个月</td>
	                        <td class="align-center">${(l.purpose)!''}</td>
	                        <td class="align-center">${(l.payType)!''}</td> 
	                        <td class="align-center">${(l.deadTime)?string('yyyy-MM-dd')}</td>
	                        <td class="align-center">${(l.businessTime)?string('yyyy-MM-dd')}</td>
	                        <td class="align-center">${(l.createTime)!''}</td> 
	                        <td class="align-center">${(l.currentUserName!'')}</td> 
	                        <td class="align-center">${(l.statusName)!''}</td> 
	                        <td class="align-center">
	                            <a href="#" id="creditDetailView" data-url="${app}/credit/repayPlanDetail/${l.id}" >查看</a>
	                            <#if l.status == '00'>
	                            <a href="#" data-url="${app}/credit/goSell/${l.id}" >发售</a>
	                            </#if>
	                        </td>    
	                    </tr>
						</#list>
					</#if>
                    <#else>
                      <tr>
						<td colspan="14" class="align-center"><@messages key="common.table.empty" /></td>
					  </tr>
                    </#if>
                </tbody>
            </table>
              <#if sellList??>
            <div class="pull-right">
                <ul class="pagination" data-number="${sellList.number}" data-total-pages="${sellList.totalPages}"></ul>
            </div>
            </#if>
        </div>
    </div>
    
<div id="data"></div> 
    
   
<script type="text/javascript" charset="utf-8">
<!--
   jQuery(function($) {
		$('#table a').link();
		$('.pagination').pagination({
			handler: function(elem) {
				$('#page').val(elem.data().page);
				$('#searchForm').trigger('submit');
			}
		});
		
	$("#beginDate").datepicker();  
	$("#endDate").datepicker();
	});
   
   
   $("#sellListTable a").on("click",function(){
		$.link.html(null, {
			url: $(this).attr("data-url"),
			target: 'main'
		});
   }); 
   
   $("#searchBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/credit/sellIndex',
			data: $("#creditSellForm").serialize(),
			target: 'main'
		});
   }); 
   
   
  
//-->
</script>
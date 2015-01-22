
<div class="panel panel-primary">
        <div class="panel-heading">已转让债权导入</div>  </div>
    	   <div class="panel-body">
              <form id="searchForm" method="post" action="#">
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
                         <label for="cellphone">借款类型</label>
                        <input id="crediteType" name="crediteType" value="" class="form-control" type="text">
                    </div>
                </div>
                <div class="row">
                        <div class="col-xs-2 hm-col form-group">
                            <label for="beginDate">导入日期</label>
                            <input readonly="" id="beginDate" name="beginDate" class="form-control" type="text">
                        </div>
                        <div class="col-xs-2 hm-col form-group">
                            <label for="endDate">&nbsp;</label>
                               <input readonly="" id="endDate" name="endDate"  class="form-control" type="text">
                        </div>
                        <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                        <input id="page" name="page" value="0" type="hidden">
                    </div>
                </div>
            </form>
        </div>

    </div>
    
   <div id="data"></div> 
   <div id="dataDetail"></div> 

<script type="text/javascript" charset="utf-8">
<!--
var labelId;
jQuery(function($) {
	$("#beginDate").datepicker();  
	$("#endDate").datepicker();
	
	$.page.withdraw({
		search: '${app}/credit/assignedTable'
	});
	
	
});
//-->
</script>
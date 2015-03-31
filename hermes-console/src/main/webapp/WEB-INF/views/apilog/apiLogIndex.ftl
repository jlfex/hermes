<div class="panel panel-primary">
        <div class="panel-heading">外围日志管理</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="serialNo">请求流水号</label>
                        <input id="serialNo" name="serialNo" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="interfaceName">接口名称</label>
                        <input id="interfaceName" name="interfaceName" value="" class="form-control" type="text">
                    </div>    
                    <div class="col-xs-2 hm-col form-group">
                         <label for="beginDate">请求时间</label>
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
<div id="data">
</div>   
<script type="text/javascript" charset="utf-8">
jQuery(function($) {
    $("#beginDate").datetimepicker({
          timeFormat: "HH:mm:ss",
          dateFormat: "yy-mm-dd"
    });
      
	$("#endDate").datetimepicker({
	      timeFormat: "HH:mm:ss",
          dateFormat: "yy-mm-dd"
	});
	//点击查询按钮
	$.page.withdraw({
    	search: '${app}/apiLog/apiLogdata'
	});
});
</script>


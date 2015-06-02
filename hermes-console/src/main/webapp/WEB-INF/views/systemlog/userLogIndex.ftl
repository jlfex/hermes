<div class="panel panel-primary">
        <div class="panel-heading">用户日志管理</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="email">邮件</label>
                        <input id="email" name="email" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="type">类型</label>
						<select id="type" name="type" class="form-control">
							<option value="">全部</option>
							<#list type as s>
								<option value="${s.key}">${s.value}</option>
							</#list>
						</select>
                    </div>                   
                 </div>
                 <div class="row">  
                    <div class="col-xs-2 hm-col form-group">
                         <label for="beginDate">时间</label>
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
	$.page.userLogMng({
    	search: '${app}/userLog/userLogData'
	});
});
</script>


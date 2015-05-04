 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

<div class="panel panel-primary">
        <div class="panel-heading">接口配置管理</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="platCode">平台编码</label>
                        <input id="platCode" name="platCode" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="clientStoreName">本地证书</label>
                        <input id="clientStoreName" name="clientStoreName" value="" class="form-control" type="text">
                    </div>    
                    <div class="col-xs-2 hm-col form-group">
                        <label for="truestStoreName">服务端证书</label>
                        <input id="truestStoreName" name="truestStoreName" value="" class="form-control" type="text">
                    </div>                       
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                    </div>  
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="addBtn" type="button" class="btn btn-primary btn-block">新增</button>
                        <input id="page" name="page" value="0" type="hidden">
                    </div>    		                     		
                </div>               
            </form>
        </div>
<div id="data"></div>   
<script type="text/javascript" charset="utf-8">
jQuery(function($) {
	//点击查询按钮
	$.page.withdraw({
    	search: '${app}/apiConfig/apiConfigData'
	});
		//点击添加按钮
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/apiConfig/addApiConfig',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});
	
});
</script>


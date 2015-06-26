 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

<div class="panel panel-primary">
        <div class="panel-heading">用户管理</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row hm-row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="platCode">用户名称</label>
                        <input id="userName" name="userName" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                    </div> 
                    <#if backRoleResourceList?seq_contains("back_privi_user_add")>
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="addBtn" type="button" class="btn btn-primary btn-block">新增</button>
                        <input id="page" name="page" value="0" type="hidden">
                    </div>   
                    </#if> 		                     		
                </div>               
            </form>
        </div>
<div id="data"></div>   
<script type="text/javascript" charset="utf-8">
jQuery(function($) {
	//点击查询按钮
	$.page.withdraw({
    	search: '${app}/privilege/userMgrData'
	});
    //点击添加按钮
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/privilege/goAddUser',
			target: 'main'
		});
	});
	
});
</script>


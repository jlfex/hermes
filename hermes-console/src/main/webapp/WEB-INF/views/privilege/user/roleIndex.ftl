 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="panel panel-primary">
        <div class="panel-heading">角色授权</div> </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="platCode">角色名称</label>
                        <input id="roleName" name="roleName" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                    </div>
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="backBtn" type="button" class="btn btn-default btn-block backBtn">返回</button>
                    </div>
                </div>               
            </form>
        </div>
<div id="data"></div>   
<script type="text/javascript" charset="utf-8">
jQuery(function($) {
	//点击查询按钮
	var userId = '${userId!''}';
	$.page.withdraw({
    	search: '${app}/privilege/userRoleData?userId='+userId,
	});
	
	//返回
	$(".backBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/privilege/userIndex',
			target: 'main'
		});
    });	
	
});
</script>


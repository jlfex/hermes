<#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="panel panel-primary">
	<div class="panel-heading">参数设置</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="parameterType">参数类型</label>
					<input id="parameterType" name="parameterType" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="parameterValue">参数值</label>
					<input id="parameterValue" name="parameterValue" type="text" class="form-control">
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="addTypeBtn" type="button" class="btn btn-primary btn-block">添加参数类型</button>
				</div>	
			   	<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="addBtn" type="button" class="btn btn-primary btn-block">添加参数值</button>
					<input id="page" name="page" type="hidden" value="0">
				</div>				   										
			</div>
		</form>
	</div>
</div>
<div id="data"></div>
<script type="text/javascript">
<!--
jQuery(function($) {
	//点击查询按钮
	$.page.withdraw({
		search: '${app}/parameter/parameterdata'
	});	

    //点击添加参数类按钮
	$("#addTypeBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/addParameterType',
			target: 'main'
		});
	});
	
    //点击添加参数值按钮
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/parameter/addParameter',
			target: 'main'
		});
	});
});
//-->
</script>
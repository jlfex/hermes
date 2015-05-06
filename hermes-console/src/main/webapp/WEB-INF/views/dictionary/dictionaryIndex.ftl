<#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="panel panel-primary">
	<div class="panel-heading">字典项配置</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
		    <input type="hidden" value="${(dictId)!}" name="id" id="dictId"/>
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="parameterType">字典项编码</label>
					<input id="typeCode" name="code" type="text" class="form-control">
				</div>			
				<div class="col-xs-2 hm-col form-group">
					<label for="parameterType">字典项名称</label>
					<input id="parameterType" name="name" type="text" class="form-control">
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="addBtn" type="button" class="btn btn-primary btn-block">新增</button>
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
		search: '${app}/dictionary/dictionaryData'
	});	

    //点击添加参数类按钮
	$("#addBtn").on("click",function(){
	    var dictId = $("#dictId").val();
		$.link.html(null, {
			url: '${app}/dictionary/addDictionary?id='+dictId,
			target: 'main'
		});
	});
});
//-->
</script>
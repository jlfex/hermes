 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

<div class="panel panel-primary">
        <div class="panel-heading">通用参数配置</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="code">参数编码</label>
                        <input id="code" name="code" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="name">参数名称</label>
                        <input id="name" name="name" value="" class="form-control" type="text">
                    </div>  
                    <div class="col-xs-2 hm-col form-group">
                        <label for="status">参数状态</label>
						<select id="status" name="status" class="form-control">
							<option value="">全部</option>
							<#list status as s>
							<option value="${s.key}">${s.value}</option>
							</#list>
						</select>
                    </div>                                         
                    <div class="col-xs-2 hm-col form-group">
                        <label for="type">参数类型</label>
						<select id="type" name="typeId" class="form-control">
							<option value="">全部</option>
							<#list dicts as d>
							<option value="${d.id}">${d.name}</option>
							</#list>
						</select>
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
                     <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="clearMemory" type="button" class="btn btn-primary btn-block">清缓存</button>
                    </div>    	  		                     		
                </div>               
            </form>
        </div>
<div id="data"></div>   
<script type="text/javascript" charset="utf-8">
jQuery(function($) {
	//点击查询按钮
	$.page.withdraw({
    	search: '${app}/properties/propertiesData'
	});
		//点击添加按钮
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/properties/addProperties',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});
	
	//clearMemory
	$("#clearMemory").on("click",function(){
		$.link.html(null, {
			url: '${app}/properties/clearProperties',
			target: 'main'
		});
	});
});
</script>


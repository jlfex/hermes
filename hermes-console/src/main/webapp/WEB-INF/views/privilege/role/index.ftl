<div class="panel panel-primary">
	<div class="panel-heading">角色管理</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="code">代码</label>
					<input id="code" name="code" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="name">名称</label>
					<input id="name" name="name" type="text" class="form-control">
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
					<input id="page" name="page" type="hidden" value="0">
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="addBtn" type="button" class="btn btn-primary btn-block">新增</button>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="data"></div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$.page.withdraw({
		search: '${app}/privilege/list'
	});
	
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/privilege/addOrEdit/-1',
			target: 'main'
		});
	});				
});
//-->
</script>

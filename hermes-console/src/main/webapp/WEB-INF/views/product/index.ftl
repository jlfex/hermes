<#if msg??>
	<div class="alert alert-success alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="panel panel-primary">
	<div class="panel-heading">产品管理</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="code"><@messages key="model.product.code" /></label>
					<input id="code" name="code" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="name"><@messages key="model.product.name" /></label>
					<input id="name" name="name" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="purpose"><@messages key="model.product.purpose" /></label>
					<select name="purpose" class="form-control">
						<option value="">请选择</option>
						<#list purpose as l>
							<option value="${l.id}">${l.name}</option>
						</#list>
					</select>	
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="status"><@messages key="model.product.status" /></label>
					<select name="status" class="form-control">
						<option value="">请选择</option>
						<option value="00">启用</option>
						<option value="99">禁用</option>
					</select>
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.search" /></button>
				</div>
				<#if backRoleResourceList?seq_contains("back_product_mgr_add")>
					<div class="col-xs-1 hm-col form-group">
						<label>&nbsp;</label>
						<button id="addBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.add" /></button>
						<input id="page" name="page" type="hidden" value="0">
					</div>
				</#if>
			</div>
		</form>
	</div>
</div>

<div id="data"></div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$.page.withdraw({
		search: '${app}/product/data'
	});
	
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/product/add',
			target: 'main'
		});
	});
});
//-->
</script>
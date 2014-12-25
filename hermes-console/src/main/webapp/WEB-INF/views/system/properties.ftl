<div class="content-tab">
	<div id="tabForms" class="tab-form">
		<ul>
			<li data-name="search" data-toggle="search"><a href="javascript:;"><i class="fa fa-search"></i></a></li>
			<li data-name="data" data-toggle="data"><a href="javascript:;"><i class="fa fa-plus"></i></a></li>
		</ul>
	</div>
	
	<table id="table" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th data-column="name"><@messages key="model.properties.name" /></th>
				<th data-column="code"><@messages key="model.properties.code" /></th>
				<th data-column="value"><@messages key="model.properties.value" /></th>
				<th data-toggle="edit"><@messages key="common.op.edit" /></th>
				<th data-toggle="remove"><@messages key="common.op.remove" /></th>
			</tr>
		</thead>
	</table>
</div>

<!-- search form -->
<div id="search" class="tab-content">
	<form method="post" action="${app}/system/properties/search">
	<div class="row">
		<div class="col-xs-2">
			<div class="form-group">
				<label for="searchName"><@messages key="model.properties.name" /></label>
				<input id="searchName" name="name" type="text" placeholder="<@messages key="form.hint.properties.search.name" />" class="form-control">
			</div>
		</div>
		<div class="col-xs-2">
			<div class="form-group">
				<label for="searchCode"><@messages key="model.properties.code" /></label>
				<input id="searchCode" name="code" type="text" placeholder="<@messages key="form.hint.properties.search.code" />" class="form-control">
			</div>
		</div>
		<div class="col-xs-2">
			<div class="form-group">
				<label class="label-block">&nbsp;</label>
				<button id="searchBtn" type="button" class="btn btn-success"><@messages key="common.op.search" /></button>
				<input id="page" name="page" type="hidden">
			</div>
		</div>
	</div>
	</form>
</div>
<!-- /search form -->

<!-- data form -->
<div id="data" class="tab-content">
	<form method="post" action="${app}/system/properties/save">
	<div class="row">
		<div class="col-xs-2">
			<div class="form-group">
				<label for="name"><@messages key="model.properties.name" /></label>
				<input id="name" name="name" type="text" placeholder="<@messages key="form.hint.properties.form.name" />" class="form-control">
			</div>
		</div>
		<div class="col-xs-2">
			<div class="form-group">
				<label for="code"><@messages key="model.properties.code" /></label>
				<input id="code" name="code" type="text" placeholder="<@messages key="form.hint.properties.form.code" />" class="form-control">
			</div>
		</div>
		<div class="col-xs-6">
			<div class="form-group">
				<label for="value"><@messages key="model.properties.value" /></label>
				<input id="value" name="value" type="text" placeholder="<@messages key="form.hint.properties.form.value" />" class="form-control">
			</div>
		</div>
		<div class="col-xs-2">
			<div class="form-group">
				<label class="label-block">&nbsp;</label>
				<button id="saveBtn" type="button" class="btn btn-success"><@messages key="common.op.save" /></button>
				<input id="id" name="id" type="hidden">
			</div>
		</div>
	</div>
	</form>
</div>
<!-- /data form -->

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 初始化
	$('#tabForms').form();
	
	// 查询事件绑定
	$('#searchBtn').on('click', function() {
		$.hermes.json({
			url: $('#search > form').prop('action'),
			data: $('#search > form').serialize(),
			callback: function(data) { $('#table').table('page', data); $('#tabForms').form('close', 'search'); }
		});
	}).trigger('click');
	
	// 保存事件绑定
	$('#saveBtn').on('click', function() {
		$.hermes.json({
			url: $('#data > form').prop('action'),
			data: $('#data > form').serialize(),
			callback: function(data) { $('#searchBtn').trigger('click'); },
			alert: function(alert) { $('#data .alert').remove(); $('#data').prepend(alert); }
		});
	});
	
	console.log($.hermes.table.settings($('#table')));
});
//-->
</script>

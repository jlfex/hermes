<div class="panel panel-primary">
	<div class="panel-heading"><@messages key="withdraw.caption" /></div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="name"><@messages key="withdraw.label.name" /></label>
					<input id="name" name="name" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="beginDate"><@messages key="withdraw.label.date" /></label>
					<input id="beginDate" name="beginDate" type="text" value="${today}" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="endDate">&nbsp;</label>
					<input id="endDate" name="endDate" type="text" value="${today}" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="status"><@messages key="withdraw.label.status" /></label>
					<select id="status" name="status" class="form-control">
						<option value=""><@messages key="common.all" /></option>
						<#list status as s>
						<option value="${s.key}">${s.value}</option>
						</#list>
					</select>
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.search" /></button>
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
	$.page.withdraw({
		search: '${app}/withdraw/table',
		today: '${today}'
	});
});
//-->
</script>

<div class="panel panel-primary">
	<div class="panel-heading"><@messages key="withdraw.caption" /></div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="email">账户</label>
					<input id="email" name="email" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="beginDate">充值时间</label>
					<input id="beginDate" name="beginDate" type="text" value="${today}" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="endDate">&nbsp;</label>
					<input id="endDate" name="endDate" type="text" value="${today}" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="remark">充值结果</label>
					<input id="remark" name="remark" type="text" class="form-control">
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
	$.page.rechargeDetailMng({
		search: '${app}/transaction/table',
		today: '${today}'
	});
});
//-->
</script>

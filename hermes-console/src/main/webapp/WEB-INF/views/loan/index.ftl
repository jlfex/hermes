

<div class="panel panel-primary">
	<div class="panel-heading">借款信息</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="loanNo"><@messages key="model.loan.loanNo" /></label>
					<input id="loanNo" name="loanNo" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="account">手机号码</label>
					<input id="cellphone" name="cellphone" type="text" class="form-control">
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
		search: '${app}/loan/loandata'
	});
				
});
//-->
</script>




<!-- user info -->
<div class="block">
	<div class="body-xs">
		<div class="row u-row">
			<div class="col-xs-6 u-col line-right">
				<div class="body-sm clearfix">
					<div class="pull-left" style="padding-right:20px;">
						<p><img alt="" src="${avatar!''}" class="avatar-lg"></p>
					</div>
					<div class="pull-left">
						<p class="name">${user.email}</p>
						<p><a href="#"><@messages key="account.info.user.reset.password" /></a></p>
						<ul class="auth clearfix">
							<li class="email" data-auth="${prop.authEmail}"><i class="fa fa-envelope"></i></li>
							<li class="cellphone" data-auth="${prop.authCellphone}"><i class="fa fa-mobile"></i></li>
							<li class="name" data-auth="${prop.authName}"><i class="fa fa-user"></i></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-xs-6 u-col">
				<div class="body">
					<p class="account">
						<@messages key="account.info.user.cash" />&nbsp;<span class="currency currency-lg">${cash.balance?string('#,##0.00')}</span>&nbsp;<@messages key="common.unit.cny" /><#t>
						&nbsp;&nbsp;<#t>
						<@messages key="account.info.user.frozen" />&nbsp;<span class="currency">${frozen.balance?string('#,##0.00')}</span>&nbsp;<@messages key="common.unit.cny" /><#t>
					</p>
					<p>
						<!--<button type="button" class="btn btn-primary" data-menu="charge"><@messages key="common.op.charge" /></button>-->
						<button type="button" class="btn btn-danger" data-menu="withdraw"><@messages key="common.op.withdraw" /></button>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /user info -->

<!-- transactions -->
<div class="block">
	<div class="body-sm">
		<h4><@messages key="account.fund.detail.caption" /></h4>
		<form id="searchForm" method="post" action="#" class="form-horizontal">
			<div class="form-group">
				<label class="col-xs-2 u-col control-label"><@messages key="account.fund.detail.date" /></label>
				<div class="col-xs-2 u-col">
					<input id="beginDate" name="beginDate" type="text" value="${beginDate}" class="form-control">
				</div>
				<div class="col-xs-2 u-col">
					<input id="endDate" name="endDate" type="text" value="${endDate}" class="form-control">
				</div>
				<div class="col-xs-2 u-col">
					<button id="searchBtn" type="button" class="btn btn-primary"><@messages key="common.op.search" /></button>
					<input id="page" name="page" type="hidden" value="0">
				</div>
			</div>
		</form>
		<div id="data"></div>
	</div>
</div>
<!-- /transactions -->

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 处理货币样式
	$('.currency').formatNumber();
	
	// 绑定表单提交事件
	$('#searchForm').on('submit', function() {
		$.ajax('${app}/account/detail/table', {
			data: $(this).serialize(),
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				$('#data').fadeOut('fast', function() {
					$(this).html(data).fadeIn('fast');
				});
			}
		});
		return false;
	});
	
	// 绑定查询按钮事件
	$('#searchBtn').on('click', function() {
		$('#page').val(0);
		$('#searchForm').trigger('submit');
	}).trigger('submit');
	
	// 日历控件处理
	$('#beginDate').prop('readonly', true).datepicker({
		numberOfMonths: 2,
		onClose: function(date) {
			$('#endDate').datepicker('option', 'minDate', date);
		}
	}).datepicker('option', 'maxDate', '${endDate}');
	$('#endDate').prop('readonly', true).datepicker({
		numberOfMonths: 2,
		onClose: function(date) {
			$('#beginDate').datepicker('option', 'maxDate', date);
		}
	}).datepicker('option', 'maxDate', '${endDate}');
	
	// 充值提现按钮事件
	$('[data-menu]').on('click', function() {
		$('#accountMenu').find('.' + $(this).data().menu).trigger('click');
	});
	
	// 处理用户认证
	$('[data-auth]').each(function() {
		if ($(this).data().auth == '${pass}') $(this).addClass('active');
	});
});
//-->
</script>

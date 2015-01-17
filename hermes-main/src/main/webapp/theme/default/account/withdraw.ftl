<meta charset="utf-8">
<div class="block">
	<h3 class="clearfix">
		<span><@messages key="account.fund.withdraw.caption" /></span>
		<span id="balance" class="sub pull-right"><@messages key="account.fund.withdraw.balance" />${account.balance?string("#,##0.00")} <@messages key="common.unit.cny" /></span>
	</h3>
	<div class="body">
		<form id="withdrawForm" method="post" action="#" class="form-horizontal">
			<div class="form-group">
				<label class="col-xs-2 u-col control-label"><@messages key="account.fund.withdraw.account" /></label>
				<div class="col-xs-10 u-col">
					<#list bankAccounts as ba>
					<div class="radio">
						<label>
							<input name="bankAccountId" type="radio" value="${ba.id}">
							${ba.bank.name}&nbsp;<@messages key="account.fund.withdraw.account.last" />${ba.accountLast}
						</label>
					</div>
					</#list>
					<p id="addBankAccount" class="form-control-static"><a href="#"><i class="fa fa-plus"></i>&nbsp;<@messages key="account.fund.withdraw.account.add" /></a></p>
				</div>
			</div>
			<div class="form-group">
				<label for="amount" class="col-xs-2 u-col control-label"><@messages key="account.fund.withdraw.amount" /></label>
				<div class="col-xs-3 u-col">
					<input id="amount" name="amount" type="number" placeholder="<@messages key="account.fund.withdraw.amount.hint" />" class="form-control">
				</div>
				<div class="col-xs-1 u-col">
					<p class="form-control-static"><@messages key="common.unit.cny" /></p>
				</div>
			</div>
			<div class="form-group">
				<label for="fee" class="col-xs-2 u-col control-label"><@messages key="account.fund.withdraw.fee" /></label>
				<div class="col-xs-3 u-col">
					<p class="form-control-static"><span id="fee">0.00&nbsp;<@messages key="common.unit.cny" /></span> <a id="info" href="#"><i class="fa fa-info-circle"></i></a></p>
				</div>
			</div>
			<div class="form-group">
				<label for="sum" class="col-xs-2 u-col control-label"><@messages key="account.fund.withdraw.sum" /></label>
				<div class="col-xs-3 u-col">
					<p id="sum" class="form-control-static">0.00&nbsp;<@messages key="common.unit.cny" /></p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-2 col-xs-offset-2 u-col">
					<button id="addWithdrawBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.withdraw" /></button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 默认选中首选项
	$('#addBankAccount').parent().find(':radio:first').prop('checked', true);

	// 格式化数字
	$('#balance, #fee, #sum').formatNumber();
	
	// 缁戝畾娣诲姞閾惰甯愬彿鍔熻兘
	$('#addBankAccount').on('click', function() {
		var _elem = $(this);
		$.ajax('${app}/account/bank-account/form', {
			type: 'post',
			dataType: 'html',
			timeout: 10000,
			success: function(data, textStatus, xhr) {
				_elem.hide();
				$(data).hide().appendTo(_elem.parent()).show('fast');
			}
		});
	});
	
	// 璁＄畻鎻愮幇璐圭敤
	$('#amount').on('blur', function() {
		// 鍒濆鍖�
		var _elem = $(this),
			_fee = $('#fee'),
			_sum = $('#sum');
		
		// 鍒ゆ柇杈撳叆鏄惁鏈夋晥
		if (_elem.val() === '') return;
		
		// 鍔犺浇鍥炬爣
		_fee.html('<i class="fa fa-spinner fa-spin"></i>');
		_sum.html('<i class="fa fa-spinner fa-spin"></i>');
		
		// 寮傛鍔犺浇
		$.ajax('${app}/account/withdraw/calc', {
			data: { amount: _elem.val() },
			type: 'post',
			dataType: 'json',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				if (data.typeName === 'success') {
					_fee.html(data.messages[0] + '&nbsp;<@messages key="common.unit.cny" />').formatNumber();
					_sum.html(data.messages[1] + '&nbsp;<@messages key="common.unit.cny" />').formatNumber();
				} else {
					_fee.html('<span class="failure"><i class="fa fa-info-circle"></i> ' + data.firstMessage + '</span>');
					_sum.html('<span class="failure"><i class="fa fa-info-circle"></i> ' + data.firstMessage + '</span>');
				}
			}
		});
	});
	
	// 添加提现记录
	$('#addWithdrawBtn').on('click', function() {
		$.ajax('${app}/account/withdraw/add', {
			data: $('#withdrawForm').serialize(),
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				$('#main').fadeOut('fast', function() {
					$(this).html(data).fadeIn('fast');
				});
			}
		});
	});
	
	// 费用提示处理
	$('#info').popover({
		html: true,
		placement: 'right',
		trigger: 'hover', 
		content: '<span style="white-space:nowrap;"><@config key="fee.withdraw.desc" /></span>'
	});
});
//-->
</script>

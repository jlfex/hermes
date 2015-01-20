<meta charset="utf-8">
<div class="panel panel-default">
	<div class="panel-body">
		<form id="dataForm" method="post" action="#" class="form-horizontal">
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.proposer" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${prop.realName!}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.datetime" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.formatDatetime}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.amount" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.formatAmount}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.fee" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.formatFee}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.bank.name" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.bankAccount.bank.name}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.bank.city" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.bankAccount.city.name}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.bank.deposit" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.bankAccount.deposit}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.bank.account" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.bankAccount.account}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.bank.name" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.bankAccount.name!''}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"><@messages key="withdraw.label.status" /></label>
				<div class="col-xs-10">
					<p class="form-control-static">${withdraw.statusName}</p>
				</div>
			</div>
			<div class="form-group">
				<label for="remark" class="col-xs-2 control-label"><@messages key="withdraw.label.remark" /></label>
				<div class="col-xs-5">
					<textarea id="remark" name="remark" rows="2" class="form-control">${withdraw.remark!''}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">&nbsp;</label>
				<div class="col-xs-10">
					<button type="button" data-status="${success}" class="btn btn-success"><@messages key="common.op.success" /></button>
					<button type="button" data-status="${failure}" class="btn btn-danger"><@messages key="common.op.failure" /></button>
					<button type="button" class="btn btn-default"><@messages key="common.op.cancel" /></button>
					<input name="id" type="hidden" value="${withdraw.id}">
					<input id="detailStatus" name="status" type="hidden" value="${withdraw.status}">
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	// 初始化
	var _form_search = $('#searchForm'),
		_form_data = $('#dataForm'),
		_area_remark = $('#remark'),
		_hide_status = $('#detailStatus');
	
	// 根据状态进行处理
	if (!(_hide_status.val() === '${wait}')) {
		_area_remark.after($('<p />').addClass('form-control-static').text(_area_remark.val())).remove();
		_form_data.find('button.btn-success, button.btn-danger').remove();
	}
	
	// 绑定按钮事件
	_form_data.find('button').on('click', function() {
		if ($(this).hasClass('btn-default')) {
			_form_search.trigger('submit');
		} else if (_area_remark.val() === '') {
			_area_remark.next().remove();
			_area_remark.after($('<p />').addClass('help-block help-block-remark').html('<i class="fa fa-times-circle"></i> <@messages key="withdraw.label.remark.required" />')).parent().parent().addClass('has-error');
		} else {
			$(this).html('<i class="fa fa-spinner fa-spin"></i> <@messages key="common.op.doing" />').parent().find('button').prop('disabled', true);
			_hide_status.val($(this).data().status);
			$.link.html(null, {
				url: '${app}/withdraw/deal',
				target: 'data',
				data: _form_data.serialize()
			});
		}
	});
});
//-->
</script>

<div id="bankAccountForm" class="panel panel-default embed">
	<div class="panel-body">
		<div class="form-group">
			<label for="name" class="col-xs-2 u-col control-label"><@messages key="model.bank.account.name" /></label>
			<div class="col-xs-3 u-col">
				<p id="fee" class="form-control-static">${properties.realName!''}</p>
			</div>
		</div>
		<div class="form-group">
			<label for="bankId" class="col-xs-2 u-col control-label"><@messages key="model.bank.account.bank" /></label>
			<div class="col-xs-4 u-col">
				<select id="bankId" name="bankId" class="form-control">
					<#list banks as b>
					<option value="${b.id}">${b.name}</option>
					</#list>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="cityId" class="col-xs-2 u-col control-label"><@messages key="model.bank.account.city" /></label>
			<div class="col-xs-3 u-col">
				<select id="provinceId" name="provinceId" class="form-control"></select>
			</div>
			<div class="col-xs-3 u-col">
				<select id="cityId" name="cityId" class="form-control"></select>
			</div>
		</div>
		<div class="form-group">
			<label for="deposit" class="col-xs-2 u-col control-label"><@messages key="model.bank.account.deposit" /></label>
			<div class="col-xs-6 u-col">
				<input id="deposit" name="deposit" type="text" class="form-control">
			</div>
		</div>
		<div class="form-group">
			<label for="account" class="col-xs-2 u-col control-label"><@messages key="model.bank.account.account" /></label>
			<div class="col-xs-6 u-col">
				<input id="account" name="account" type="text" size="19" class="form-control">
			</div>
		</div>
		<div class="form-group">
			<div class="col-xs-2 col-xs-offset-2 u-col">
				<button id="addBankAccountBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.add" /></button>
			</div>
			<div class="col-xs-2 u-col">
				<button id="cancelBankAccountBtn" type="button" class="btn btn-default btn-block"><@messages key="common.op.cancel" /></button>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" charset="utf-8">
	<!--
	jQuery(function($) {
		// ��ȡ��ť�¼�
		$('#cancelBankAccountBtn').on('click', function() {
			$('#bankAccountForm').hide('fast', function() {
				$(this).remove();
				$('#addBankAccount').show();
			});
		});
		
		// ����Ӱ�ť�¼�
		$('#addBankAccountBtn').on('click', function() {
			$.ajax('${app}/account/bank-account/save', {
				data: { bankId: $('#bankId').val(), cityId: $('#cityId').val(), deposit: $('#deposit').val(), account: $('#account').val() },
				type: 'post',
				dataType: 'json',
				timeout: 5000,
				success: function(data, textStatus, xhr) {
					$('#addBankAccount').before('<div class="radio"><label><input name="bankAccountId" type="radio" value="' + data.id + '">' + data.bank.name + '&nbsp;<@messages key="account.fund.withdraw.account.last" />' + data.accountLast + '</label></div>')
					$('#addBankAccount').parent().find(':radio:last').prop('checked', true);
					$('#cancelBankAccountBtn').trigger('click');
				}
			});
		});
		
		// ������Ϣ����
		$.area({ data: ${area}, bind: [$('#provinceId'), $('#cityId')] });
		
		// ����������ʾ
		$('#account').on('keypress', function(event) {
			if (event.charCode < 48 || event.charCode > 57) return false;
			if ($(this).val().length === 19) return false;
		}).on('keyup', function() {
			var _hint = $(this).parent().find('.account-hint').empty();
			if (_hint.size() <= 0) _hint = $('<div />').addClass('account-hint').html('&nbsp;').appendTo($(this).parent());
			for (var _i = 0; _i <= $(this).val().length; _i++) {
				if (_i > 0 && _i % 4 === 0) _hint.append('&nbsp;');
				_hint.append($(this).val()[_i]);
			}
			_hint.fadeIn('fast');
			if (_hint.html() === '') _hint.html('&nbsp;');
		}).on('blur', function() {
			$(this).parent().find('.account-hint').fadeOut('fast');
		});
	});
	//-->
	</script>
</div>

<div class="block block-auth" data-auth="${userProp.authEmail}">
	<div class="body clearfix">
		<div class="icon pull-left"><i class="fa fa-envelope"></i></div>
		<div class="text pull-left">
			<h5><@messages key="account.info.auth.mail" /></h5>
			<p><span><i class="fa fa-check-circle"></i></span> <@messages key="account.info.auth.passed" /> ${email}</p>
		</div>
		<div class="check pull-right"><i class="fa fa-check"></i></div>
	</div>
</div>
<!--
<#if phoneSwitch>
<div class="block block-auth authphone" data-auth="${userProp.authCellphone}">
	<div class="body clearfix">
		<div class="icon pull-left"><i class="fa fa-mobile"></i></div>
		<div class="text pull-left">
			<h5><@messages key="account.info.auth.cellphone" /></h5>
			<#if userProp.authCellphone == '10'>
			<p><span><i class="fa fa-check-circle"></i></span> <@messages key="account.info.auth.passed" /></p>
			<#else>
			<p class="non"><i class="fa fa-times-circle"></i> <@messages key="account.info.auth.nonpass" /></p>
			</#if>
		</div>
		<#if userProp.authCellphone == '10'>
		<div class="check pull-right"><i class="fa fa-check"></i></div>
		<#else>
		<div class="auth-btn pull-right"><button id="authCellphoneBtn" type="button" class="btn btn-info"><@messages key="account.info.auth.cellphone.btn" /></button></div>
		<div id="authPhoneOk" class="check pull-right hidden"><i class="fa fa-check"></i></div>
		</#if>
	</div>
</div>
</#if>
-->
<#if idSwitch>
<div class="block block-auth identity" data-auth="${userProp.authName}">
	<div class="body clearfix">
		<div class="icon pull-left"><i class="fa fa-user"></i></div>
		<div class="text pull-left">
			<h5><@messages key="account.info.auth.name" /></h5>
			<#if userProp.authName == '10'>
			<p><span><i class="fa fa-check-circle"></i></span> <@messages key="account.info.auth.passed" /></p>
			<#else>
			<p class="non"><i class="fa fa-times-circle"></i> <@messages key="account.info.auth.nonpass" /></p>
			</#if>
		</div>
		<#if userProp.authName == '10'>
		<div class="check pull-right"><i class="fa fa-check"></i></div>
		<#else>
			<div class="auth-btn pull-right"><button id="authNameBtn" type="button" class="btn btn-info"><@messages key="account.info.auth.name.btn" /></button></div>
			<div id="authIdOk" class="check pull-right hidden"><i class="fa fa-check"></i></div>
		</#if>
	</div>
</div>
</#if>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('[data-auth]').each(function() {
		if ($(this).data().auth === 10) $(this).addClass('block-passed');
	});
	$("#authNameBtn").on("click",function(){
		_elem=$(this);
		$.ajax('${app}/auth/identity', {
			type: 'post',
			dataType: 'html',
			timeout: 10000,
			success: function(data, textStatus, xhr) {
				_elem.hide();
				$(data).hide().appendTo('.identity').show('fast');
			}
		});
	});
	$("#authCellphoneBtn").on("click",function(){
		_elem=$(this);
		$.ajax('${app}/auth/phone', {
			type: 'post',
			dataType: 'html',
			timeout: 10000,
			success: function(data, textStatus, xhr) {
				_elem.hide();
				$(data).hide().appendTo('.authphone').show('fast');
			}
		});
	});
});
//-->
</script>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<style type="text/css">
  table th{text-align:center;}
.logo2{font-family:"STXingkai";font-size: 22px!important;color: #595959!important;line-height: 70px; height: 70px; display: inline-block;}
</style>
</head>
<body class="index">

<#include "../header.ftl" />
<br/>

<div id="content" class="content">
	<div class="u-container row">
		<div class="col-xs-8">
			<img alt="" src="${app.theme}/public/images/sign.jpg">
		</div>
		<div class="col-xs-4">
			<form id="signInForm" method="post" action="#" class="sign-form">
				<h2><@messages key="sign.in.caption" /></h2>
				<p class="error-message"></p>
				<div class="form-group">
					<label for="email" class="sr-only">email</label>
					<input id="email" name="email" type="email"  placeholder="<@messages key="index.sign.in.account.hint" />" class="form-control">
					<p class="help-block"></p>
				</div>
				<div class="form-group">
					<label for="signPassword" class="sr-only">password</label>
					<input id="signPassword" name="signPassword" type="password" placeholder="<@messages key="index.sign.in.password.hint" />" class="form-control">
					<p class="help-block"></p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-block"><@messages key="common.op.sign.in" /></button>
				</div>
				<p class="link">
					<a href="${app}/userIndex/retrivePwd"><@messages key="index.sign.in.forget" /></a><#t>
					&nbsp;&nbsp;|&nbsp;&nbsp;<#t>
					<a href="${app}/userIndex/regNow"><@messages key="index.sign.in.signup" /></a><#t>
				</p>
			</form>
		</div>
	</div>
</div>

<#include "../footer.ftl" />

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 绑定输入框事件
	$('input').on('blur', function() {
		if ($(this).val() === '') {
			$(this).parent().addClass('has-error').find('.help-block').html('<i class="fa fa-times-circle"></i> <@messages key="sign.in.input" />').fadeIn('fast');
		} else {
			$(this).parent().removeClass('has-error').find('.help-block').fadeOut('fast');
		}
	});
	
	// 绑定表单提交事件
	$('#signInForm').submit(function() {
	    var check = false;
	    if($("#email").val()==''){
	       $("#email").parent().addClass('has-error').find('.help-block').html('<i class="fa fa-times-circle"></i> <@messages key="sign.in.input" />').fadeIn('fast');
	       check = true;
	    }
	    if($("#signPassword").val()==''){
	       $("#signPassword").parent().addClass('has-error').find('.help-block').html('<i class="fa fa-times-circle"></i> <@messages key="sign.in.input" />').fadeIn('fast');
	       check = true;
	    }
	    if(check){ return false;}
		// 初始化
		var _elem = $(this);
		
		// 验证输入是否均合法
		if (_elem.find('.has-error').size() > 0) {
			_elem.find('.error-message').html('<i class="fa fa-times-circle"></i> <@messages key="sign.in.error" />').fadeIn('fast'); 
			return false;
		}
		
		// 提交异步请求
		$.ajax('${app}/userIndex/signIn', {
			data: _elem.serialize(),
			type: 'post',
			dataType: 'json',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				if (data.typeName === 'success') {
					window.location.href = '${app}/index';
				} else if (data.typeName === 'warning') {
					window.location.href = '${app}/userIndex/resendMail?email=' + $('#email').val();
				} else {
					_elem.find('.error-message').html('<i class="fa fa-times-circle"></i> ' + data.firstMessage).fadeIn('fast');
				}
			}
		});
		
		// 中断事件
		return false;
	});
});


//-->
</script>

</body>
</html>

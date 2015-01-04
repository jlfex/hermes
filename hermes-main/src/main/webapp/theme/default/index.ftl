<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>
<body class="index">

<#include "/header.ftl" />

<div id="content" class="content">
	<!-- banner & signin -->
	<div class="banner">
		<div class="u-container">
			<#if cuser??>
			<#else>
			<form id="signInForm" method="post" action="#">
				<div class="sign pull-right">
					<h3><@messages key="index.sign.in.quick" /></h3>
					<div class="form-group">
						<label for="email" class="sr-only">email</label>
						<input id="email" name="email" type="email" placeholder="<@messages key="index.sign.in.account.hint" />" class="form-control">
					</div>
					<div class="form-group">
						<label for="signPassword" class="sr-only">password</label>
						<input id="signPassword" name="signPassword" type="password" placeholder="<@messages key="index.sign.in.password.hint" />" class="form-control">
					</div>
					<div class="form-group"><button class="btn btn-primary btn-block"><@messages key="common.op.sign.in" /></button></div>
					<p>
						<a href="${app}/userIndex/retrivePwd"><@messages key="index.sign.in.forget" /></a><#t>
						&nbsp;&nbsp;|&nbsp;&nbsp;<#t>
						<a href="${app}/userIndex/regNow"><@messages key="index.sign.in.signup" /></a><#t>
					</p>
				</div>
			</form>
			</#if>
		</div>
	</div>
	<!-- /banner & signin -->
	<!-- notice -->
	<div class="notice clearfix">
		<div class="u-container">
			<div class="bullhorn pull-left"><i class="fa fa-bullhorn"></i></div>
			<ul id="notice" class="notices pull-left" data-speed="5000">
				<#list notices?sort_by("datetime")?reverse as n>
				<li data-id="${n.id}"><span>${n.datetime?date}</span>&nbsp;<a href="${app}/notice/${n.id}">${n.title}</a></li>
				</#list>
			</ul>
			<div class="pull-right"><a href="${app}/notices"><@messages key="index.notice.more" />&nbsp;<i class="fa fa-angle-double-right"></i></a></div>
		</div>
	</div>
	<!-- /notice -->
	
	<div class="u-container">
		<#include "/index-shortcut.ftl" />
			
		<!-- loan -->
		<div id="loan" class="loans">
			<h3><@messages key="index.loan.caption" /></h3>
			<table class="table table-hover">
				<thead>
					<tr>
						<th width="5%">&nbsp;</th>
						<th width="30%"><@messages key="model.loan.purpose" /></th>
						<th width="10%" class="right"><@messages key="model.loan.rate" /></th>
						<th width="15%" class="right"><@messages key="model.loan.amount" /></th>
						<th width="10%" class="right"><@messages key="model.loan.period" /></th>
						<th width="15%" class="right"><@messages key="model.loan.remain" /></th>
						<th width="15%">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<#list loans as l>
					<tr data-progress="${l.progress}">
						<td class="right"><img alt="" src="${l.avatar!''}" class="avatar"></td>
						<td><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
						<td class="right">${l.rate}</td>
						<td class="right">${l.amount} <@messages key="common.unit.cny" /></td>
						<td class="right">${l.period} <@messages key="common.unit.month" /></td>
						<td class="right">${l.remain} <@messages key="common.unit.cny" /></td>
						<td class="right"><button type="button" class="btn btn-primary" data-id="${l.id}">${l.statusName}</button></td>
					</tr>
					</#list>
				</tbody>
			</table>
			<div class="more"><a href="${app}/invest/index"><@messages key="index.loan.more" />&nbsp;<i class="fa fa-angle-double-down"></i></a></div>
		</div>
		<!-- /loan -->
	</div>
</div>

<#include "/footer.ftl" />

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$('#notice').roll();
	$('#loan').loan();
	$('#loan .btn-primary').click(function() { window.location.href = '${app}/invest/info?loanid=' + $(this).data().id; });
	
	// 提交登录
	$('#signInForm').submit(function() {
		// 初始化
		var _elem = $(this);
		
		// 提交异步请求
		$.ajax('${app}/userIndex/signIn', {
			data: _elem.serialize(),
			type: 'post',
			dataType: 'json',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				if (data.typeName === 'success') {
					window.location.href = '${app}';
				} else if (data.typeName === 'warning') {
					window.location.href = '${app}/userIndex/resendMail?email=' + $('#email').val();
				} else if (data.typeName === 'failure') {
					$('#signPassword').val('').attr('placeholder', data.firstMessage).parent().addClass('has-error');
					$('#email').parent().addClass('has-error');
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

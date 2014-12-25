<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.css}/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/temp.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery-ui.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/approve.js"></script>
</head>
<body>

<#include "../header.ftl" />

<div id="content" class="content">
	<div class="u-container row">
		<div class="row u-row">
			<!-- account menu -->
			<div id="accountMenu" class="col-xs-3 u-col">
				<div class="account-menu">
					<h3><@messages key="nav.main.user" /></h3>
					<div class="item first">
						<h4><@messages key="account.info" /></h4>
						<ul>
							<li><a href="#" class="icon user" data-url="${app}/account/getUserInfo" data-target="main"><@messages key="account.info.user" /></a></li>
							<li><a href="#" class="icon auth" data-url="${app}/account/approve" data-target="main"><@messages key="account.info.auth" /></a></li>
						</ul>
					</div>
					<div class="item">
						<h4><@messages key="account.biz" /></h4>
						<ul>
							<li><a href="#" class="icon loan" data-url="${app}/loan/myloan" data-target="main"><@messages key="account.biz.loan" /></a></li>
							<li><a href="#" class="icon invest" data-url="${app}/invest/myinvest" data-target="main"><@messages key="account.biz.invest" /></a></li>
						</ul>
					</div>
					<div class="item">
						<h4><@messages key="account.fund" /></h4>
						<ul>
							<li><a href="#" class="icon detail" data-url="${app}/account/detail" data-target="main"><@messages key="account.fund.detail" /></a></li>
							<li><a href="#" class="icon charge" data-url="${app}/account/charge" data-target="main"><@messages key="account.fund.charge" /></a></li>
							<li><a href="#" class="icon withdraw" data-url="${app}/account/withdraw" data-target="main"><@messages key="account.fund.withdraw" /></a></li>
						</ul>
					</div>
					<a href="#" class="icon password sr-only" data-url="${app}/account/showModify" data-target="main"></a>
				</div>
			</div>
			<!-- /account menu -->
			
			<div id="main" class="col-xs-9 u-col"></div>
		</div>
	</div>
</div>

<#include "../footer.ftl" />

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 绑定链接点击事件
	$('#accountMenu a').link().on('click', function() {
		$(this).parent().singleClass('active', {container: $('#accountMenu')});
	}).filter('.${type!'user'}').trigger('click');
});
//-->
</script>

</body>
</html>

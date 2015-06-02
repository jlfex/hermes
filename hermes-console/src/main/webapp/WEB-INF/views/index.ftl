<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@messages key="app.title" /></title>
<link rel="shortcut icon" type="image/png" href="${app.img}/favicon.png">
<link rel="stylesheet" href="${app.css}/style.css">
<link rel="stylesheet" href="${app.css}/jquery-ui.css">
<link rel="stylesheet" href="${app.css}/jquery-ui-timepicker-addon.css">
</head>
<body>

<div class="header">
	<div id="bar" class="bar pull-left active"><i class="fa fa-bars"></i></div>
	<div class="logo pull-left"><a href="${app}"><img alt="" src="${app.img}/logo.png"></a></div>
	<div class="user pull-right">
		<span>${today}&nbsp;&nbsp;</span>
		<span>${username}&nbsp;&nbsp;</span>
		<span>
			<a href="${app}/j_spring_security_logout"><i class="fa fa-sign-out fa-fw"></i></a>
		</span>
	</div>
</div>

<div id="menu" class="menu">
	<#list navs as nav>
	<dl class="${nav.code!''}">
		<#if roleResourceList?seq_contains("${nav.code}")>
			<dt><a href="#" data-url="${nav.url}" data-target="${nav.target}" data-id="${nav.id}"><i class="fa fa-${nav.code!''} fa-fw"></i>&nbsp;<span>${nav.name}</span><i class="fa fa-caret-right pull-right"></i></a></dt>
		</#if>
	</dl>
	</#list>
</div>

<div id="main" class="main"></div>

<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap3-validation.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery-ui.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/hermes.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/hermes-page.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/sco.modal.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/sco.confirm.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/i18n/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$.page.index();
});
//-->
</script>

<body>
</html>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>
<body class="index">
<div class="_container">

<#include "../header.ftl" />
<div id="content" class="content">
	<div class="u-container">
		<div class="result result-success clearfix">
			<div class="col-xs-offset-2 col-xs-8">
				<div class="icon pull-left"></div>
				<div class="text pull-left">
					<p><strong><@messages key="common.result.success" /></strong></p>
					<p><@messages key="account.fund.charge.payment.return" /></p>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="push"><!-- not put anything here --></div>
</div>
<#include "../footer.ftl" />
</body>
</html>

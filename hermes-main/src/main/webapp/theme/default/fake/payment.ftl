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
<body>

<#include "../header.ftl" />

<div id="content" class="content">
	<div class="u-container">
		<div class="well">
			<form method="post" action="${returnUrl}" class="form-horizontal">
				<div class="form-group">
					<label class="col-xs-2 control-label">支付金额</label>
					<div class="col-xs-10">
						<input name="amount" value="${amount}" readonly="readonly" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">支付序号</label>
					<div class="col-xs-10">
						<input name="sequence" value="${sequence}" readonly="readonly" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">通知地址</label>
					<div class="col-xs-10">
						<p class="form-control-static">${notifyUrl}</p>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-10 col-xs-offset-2">
						<button class="btn btn-primary">支付</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<#include "../footer.ftl" />

</body>
</html>

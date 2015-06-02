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
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>
<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div id="content" class="content">
	<div class="u-container">
		<div class="articles">
			<h3><@messages key="notices.caption" /></h3>
			<ul class="items">
				<#list notices.content as n>
				<li class="clearfix">
					<a href="${app}/notice/${n.article.id}" target="_blank" class="pull-left"><i class="fa fa-caret-right fa-fw"></i>${n.article.title}</a>
					<span class="pull-right">${n.article.datetime?date}</span>
				</li>
				</#list>
			</ul>
		</div>
	</div>
</div>
<div class="push"><!-- not put anything here --></div>
</div>
<#include "/footer.ftl" />
</body>
</html>

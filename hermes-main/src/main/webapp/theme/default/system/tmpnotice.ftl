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
</style>
</head>
<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div id="content" class="content" >
	<div class="u-container">
		<div class="article">
			<h2>${notice.title!}</h2>
			<div class="info">
				<span><@messages key="notice.datetime" />${notice.updateTime!}</span><#t>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<#t>
			</div>
			<div class="text">${notice.content!'-'}</div>
		</div>
	</div>
</div>
<div class="push"><!-- not put anything here --></div>
</div>
<#include "/footer.ftl" />
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

.content_template{width: 750px;
border: 2px solid #e2e2e2;
height: auto;
background: #FFF;
-moz-border-radius: 5px;
-webkit-border-radius: 5px;
border-radius: 5px;}
.content_template h2.title{ text-align: center; padding: 20px 0; color: #323232; font-size: 22px; border-bottom: 1px dashed #eeeeee; font-weight: 500;}
.content_index .fl_right{ float: right;}
.content_index p{ margin: 20px;}
.tu_images{ text-align: center; margin: 20px 0;}
*{
-webkit-box-sizing: content-box;
-moz-box-sizing: content-box;
box-sizing: content-box;
}
</style>
</head>

<body>

<#include "/header.ftl" />

<div class="sub_main" style="margin-top:133px;">
	<div class="account_center">
		<div class="account_nav_left">
			<div class="titleuser"></div>
			<div class="left_nav">
				<#list nav.children as l>
				<!--信息管理-->
				<div class="information">
					<h2>${l.name}</h2>
					<ul>
						<#list l.children as m>
							<li <#if m.id == sel.id >class="liactivebg"</#if>><a href="${app}/help-center/${m.id}" class="a_01">${m.name}</a></li>
						</#list>
					</ul>
				</div>
				<div class="navline"></div>
				</#list>
			</div>
			<div class="leftbottom"></div>
		</div>
		<div class="account_content_right">
			<div class="content_template">
				<h2 class="title" style="text-align:left;padding-left:20px;">${sel.name}</h2>
				<div class="content_index">
					<ul style="min-height:300px;">
						<#list aeli.content as l>
							<li><a href="${app}/help-center/${sel.id}/${l.id}" style="display: inline-block;
line-height: 35px;
margin-left: 8px;
text-decoration: none;">${(l.articleTitle)!}</a>
<span style="float: right;
margin-right: 10px;
line-height: 35px;
color: #ccc;">${(l.updateTime)!}</span>
</li>
						</#list>
					</ul>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>

<#include "/footer.ftl" />

</body>
</html>
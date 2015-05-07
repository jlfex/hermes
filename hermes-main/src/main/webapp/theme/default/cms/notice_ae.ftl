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
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="shortcut icon" href="${app.theme}/public/images/smallIcon.png">
<link rel="apple-touch-icon-precomposed" href="${app.theme}/public/images/bigIcon.png">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<style type="text/css">
.content_template{width: 980px; margin:0 auto;position:relative;
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
*{-webkit-box-sizing: content-box;-moz-box-sizing: content-box;box-sizing: content-box;}
.notice_title{ margin:0 auto;width: 980px; height:44px; background:#eeeeee; font-size:16px;margin-bottom:30px;position:relative;}
.notice_title span{ line-height:44px; padding-left:15px;}
</style>
</head>
<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="_content">
<div class="sub_main" >
	<div class="notice_title"><span>&nbsp;&nbsp;网站公告</span> </div>
		<div class="content_template">
			<h2 class="title">${(ae.articleTitle)!}</h2>	
			<div class="content_index">
				<div class="clearfix"><p class="fl_right ">【发布时间：${(ae.updateTime)!}】</p>	</div>
				<p>${(ae.content)!}</p>
			</div>
		</div>
</div>
</div>
<#include "/footer.ftl" />
</div>
</body>
</html>
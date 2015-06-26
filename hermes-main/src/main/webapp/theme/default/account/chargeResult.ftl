<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<script type="text/javascript" src="${app.js}/jquery.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>

<body>
<div class="_container">

<#include "/header.ftl" />

<!-- middle start-->
<div class="m_con m_fp m_fp3">
		<div class="m_fp_box">
		<div class="m_fp_s2">
			<img src="${app.theme}/public/other/images/m/icon1/${type}" />
			 <#if naviFlag == 'bindCard'>
			    ${message!''}&nbsp;&nbsp;<a href="${app}/account/index?type=auth1"  class="m_btn3 m_bg1 a_middle">银行卡管理</a>
			 <#elseif naviFlag=='authentic'>
			    ${message!''}&nbsp;&nbsp;<a href="${app}/account/index?type=auth" class="m_btn3 m_bg1 a_middle">认证中心</a>
			 <#else>
			    ${message!''}&nbsp;&nbsp;<span class="lightyellow">${amount}元!</span>&nbsp;&nbsp;<a href="${app}/account/index?type=charge" class="m_btn3 m_bg1 a_middle">继续充值</a>
			 </#if>
			 
			 
		</div>
	</div>
	<p class="a_pa_lt"><a href="javascript:history.go(-1)" class="q_btn1 q_bg1">返回</a></p>
</div>

<div class="push"><!-- not put anything here --></div>
</div>
<#include "/footer.ftl" />
</body>
</html>

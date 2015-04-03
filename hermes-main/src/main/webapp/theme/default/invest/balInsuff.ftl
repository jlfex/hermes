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

<#include "/header.ftl" />


<!-- middle start-->
<div class="m_con m_fp m_fp2">
		<div class="m_fp_box">
		<div class="m_fp_s2">
			<img src="${app.theme}/public/other/images/m/icon1/ico8.png" />
			<span class="lightyellow">您的账户余额不足</span>
		</div>
		<div class="m_fp_s2">
			计划投标金额：<span class="lightyellow">${investAmount}</span>
		</div>
		<div class="m_fp_s2">
			账户可用余额：<span class="lightyellow">${balance}</span>
		</div>
		<div class="m_fp_s2">
			还需支付金额：<span class="lightyellow">${needAmount}</span>
		</div>
	</div>
	<p class="a_pa_lt"><a href="${app}/invest/index" class="q_btn1 q_bg1">确认</a></p>
</div>


<#include "/footer.ftl" />
</body>
</html>

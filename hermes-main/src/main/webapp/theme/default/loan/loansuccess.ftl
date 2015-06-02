<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>

<body>
<div class="_container">

<#include "/header.ftl" />
<!-- middle start-->
<div class="m_con m_fp m_fp3">
	<div class="m_fp_box">
		<div class="m_fp_sq2 clearfix">
            <ul class="ul_02">    
                <li><img src="${app.theme}/public/other/images/m/icon1/ico8.png" /></li>
                <li><span class="sp_wt">您的借款申请已提交！</span><br>
                <span class="sp_wt_rt lightgray">完善资料，有助于提高您的借款速度和成功率。</span></li>
            </ul>
		</div>
	</div>
	<p class="m_im_txt a_pa_lt"><a href="${app}/index" class="q_btn1 q_bg1">返回首页</a><a href="${app}/account/index" class="ck">完善个人信息</a><a href="${app}/account/index?type=loan"  class="ck">查看我的借款</a></p>
</div>

<div class="push"><!-- not put anything here --></div>
</div>
<#include "/footer.ftl" />
</body>
</form>
</html>

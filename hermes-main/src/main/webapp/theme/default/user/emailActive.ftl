<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>

<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="_content">
<!-- middle start-->
<div class="m_con m_fp m_fp2">
	<div class="m_fp_box">
		<div class="m_fp_s2">
			<img src="${app.theme}/public/other/images/m/icon1/ico8.png" />
			${message}
		</div>
	</div>
	<a href="${app}/index" class="m_btn1 m_bg1">进入首页</a>
</div>

</div>
<!-- foot start-->
<#include "/footer.ftl" />
</div>
<div class="theme-popover">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close">×</a>
          <h3>提示：</h3>
     </div>
     <div class="theme-popbod dform">
               建议您使用IE8以上版本IE浏览器或其它类型的浏览器
     </div>
</div>
<div class="theme-popover-mask"></div>
<script type="text/javascript" charset="utf-8">
  jQuery(function($){
    if (navigator.userAgent.indexOf("MSIE") > 0) { //IE
	   		if(/msie 7.0/.test(navigator.userAgent.toLowerCase()) || /msie 8.0/.test(navigator.userAgent.toLowerCase())) {
	        	$('.theme-popover-mask').fadeIn(100);
   			    $('.theme-popover').slideDown(200);
	        }
	}
    //关闭提示层
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	})
  });
</script>
</body>
</html>

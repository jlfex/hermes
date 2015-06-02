<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript">
function mailBoxSkp(email){
	  var url = email.split('@')[1];
	  window.open("http://mail."+url);
}
</script>
</head>
<body>
<div class="_container" >
<#include "/header.ftl" />
<!-- middle start-->
<div class="m_con m_fp m_fp3">
	<h3>找回密码</h3>
	<div class="m_fp_box">
		<div class="m_fp_s2">
			<#if errMsg?? >
				    ${errMsg!''}，请点击  <a href="${app}/userIndex/resendMail?email=${email}" class="m_a1">重新获取Email激活</a>
			 <#else>
			       <img src="${app.theme}/public/other/images/m/icon1/ico8.png" />
				     我们给你的邮箱${email} 邮箱发送了一封密码重置邮件，请前往收信。
		     </#if>
		</div>
	</div>
	<a  class="m_btn1 m_bg1" onclick="mailBoxSkp('${email}')"  target="_blank" >去邮箱查看</a>
	<div class="tip_text">
		<p class="tip_text_t">还没有收到确认邮件？</p>
		<p>1.尝试到广告邮件、垃圾邮件目录里找找看</p>
		<p>2.超过10分钟仍未收到邮件，请点击  <a href="${app}/userIndex/resendMail?email=${email}" class="m_a1">重新获取Email激活</a></p>
		<p>3.邮件地址写错了？请重新填写</p>
	</div>
</div>
<div class="push"><!-- not put anything here --></div>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<script type="text/javascript">
$(document).ready(function(){
	$("#submit").click(function(){
		alert("dd");
		if(checkEqual()){
			$.ajax({
				data: $("#pwdForm").serialize(),
			    url: "${app}/userIndex/resetPwd",
			    type: "POST",
			    dataType: 'html',
			    timeout: 10000,
			    error: function(){
			     	 		
			    },
				success: function(data){
				
				}
			});
		}
	});
	
});
function checkEqual(){
	var sign=$("#signPassword").val();
	var conf=$("#configPassword").val();
	if(sign==conf){
		return true;
	}else{
		return false;
	}
}
 
</script>
</head>

<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="_content">
<!-- middle start-->
<div class="m_con m_fp commonForm">
	<h3>重置密码</h3>
	<div class="m_fp_box">
		<form id="pwdForm" name="pwdForm" action="${app}/userIndex/resetPwd" method="post">
			<input name="id" type="hidden" value="${uuid}"/>
			<div class="m_fp_s1">
				<div class="m_item">
					&nbsp;&nbsp;&nbsp;新密码：
					<input id="signPassword" name="signPassword" type="password" autocomplete = "off"  class="mv_pwd"/> 
					<span class="mv_msg"></span>
				</div>
				<div class="m_item">
					确认密码：
					<input id="configPassword" name="configPassword" type="password" autocomplete = "off"  class="mv_pwdagain" />
					<span class="mv_msg"></span>
				</div>
			</div>
			<img src="${app.theme}/public/other/images/m/icon1/ico7.png" class="m_fp_box_bg2" />
		</form>
	</div>
	<a href="javascript:document.pwdForm.submit();"  class="m_btn1 m_bg1 mv_submit">提交</a>
</div>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
</div>
</body>
</html>
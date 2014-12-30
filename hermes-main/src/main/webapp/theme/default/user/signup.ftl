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
$(document).ready(function(){
  $("input").focus(function(){  
        var inpVal = $(this).val();  
        if (inpVal == this.defaultValue) {  
            $(this).val("");  
        } 
        if($(this).attr('id')=='signPwd'){
        		$(this).hide(); 
						$('#signPassword').show();
						$('#signPassword').focus();
        } 
        if($(this).attr('id')=='configPwd'){
        		$(this).hide(); 
						$('#configPassword').show();
						$('#configPassword').focus();
        } 
    })  
    $("input").blur(function(){  
        var inpVal = $(this).val();  
        if (inpVal == "") {  
            $(this).val(this.defaultValue);
            if($(this).attr('id')=='signPassword'){
            		$(this).hide(); 
					$('#signPwd').show();
					$('#signPwd').val('密码'); 
            }
             if($(this).attr('id')=='configPassword'){
            		$(this).hide(); 
					$('#configPwd').show();
					$('#configPwd').val('确认密码'); 
            }
        }  
    })
});
function checkPassWrodEqual() {
	var signPwd = $("#signPassword").value;
	var confPwd = $("#configPassword").value;
	if (signPwd == confPwd) {
		return true;
	} else {
		return false;
	}
}

var score = 0;
function changeCode() {
	score ++;
    $("#verificationCode").attr("src", "${app}/userIndex/generatorCode?t="+score);
}
</script>
</head>

<body>
<#include "/header.ftl" />


<!-- middle start-->
<div class="m_con m_rg">
	<div class="m_rg_l">
		<img src="${app.theme}/public/other/images/m/image1/img1.jpg" />
	</div>
	<div class="m_rg_r" id="register">
		<form id="regForm" name="regForm" action="${app}/userIndex/signUp" method="post" >
			<div class="m_item">
				<h3><@messages key="sign.up.signup" /><span class="m_color2"> > <@messages key="sign.up.supplementary" /></span></h3>
			</div>
			<#if errMsg??>
			  <div class="m_item"><span class="mv_msg">${errMsg!} </span></div>
			</#if>
			<div class="m_item">
				<input id="email" name="email" type="text" value="<@messages key="sign.up.email" />"  class="mv_email"/>
				  <span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<input id="signPwd" type="text" value="<@messages key="sign.up.signpassword" />" />
				<input id="signPassword" name="signPassword" type="password" style="display:none;"  class="mv_pwd"/>
			    <span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<input id="configPwd" type="text" value="<@messages key="sign.up.configpassword" />" />
				<input id="configPassword" name="configPassword" type="password" style="display:none;"  class="mv_pwdagain"/>
				  <span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<input id="account" name="account" type="text" value="<@messages key="sign.up.nickname" />"  class="mv_name"/>
				 <span class="mv_msg"></span>
			</div>
			<div class="m_item">
	            <input id="realName" name="realName" type="text" value="<@messages key="sign.up.name" />"  class="mv_realname"/>
	             <span class="mv_msg"></span>
	        </div>
			<div class="m_item">
				<input id="cellphone" name="cellphone" type="text" value="<@messages key="sign.up.cellphone" />"  class="mv_mobile"/>
				 <span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<input type="text"name="verificationCode" value="<@messages key="sign.up.verify.code" />" class="wd1 mv_captcha" />
				<img id="verificationCode" src="${app}/userIndex/generatorCode" onClick="changeCode()"  class="m_yzm" />
				<a href="javascript:void(0);" onClick="changeCode()" class="m_a1 m_yzm_c"><@messages key="sign.up.verify.reload" /></a>
				<span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<a href="javascript:document.regForm.submit();"  class="m_btn1 m_bg1 mv_submit"><@messages key="sign.up.agree" /></a> 
				<@messages key="sign.up.account" /><a href="${app}/userIndex/skipSignIn" class="m_a1"><@messages key="sign.up.signin" /></a>
			</div>
			<div class="m_item m_font12">
			<a href="#" class="m_a1"><@messages key="sign.up.terms" /></a>
			</div>
		</form>
	</div>
</div>

<!-- foot start-->
<#include "/footer.ftl" />
</body>
</html>

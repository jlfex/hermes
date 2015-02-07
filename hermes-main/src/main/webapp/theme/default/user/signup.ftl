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
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/email.js"></script>
<style type="text/css">
	 .ml_180{ margin-left:185px;}
     .mr_10{margin-right:10px;}
     .low,.middle,.high{ width:60px; height:10px; display:inline-block; border:1px solid #e4e4e4;}
     .rank_bg{ background:#e68f07;}
</style>
<script type="text/javascript">
$(document).ready(function(){
  
    //邮箱补全
	var inputSuggest = new InputSuggest({
		input: document.getElementById('email'),
		data: ['sina.com','sina.cn','163.com','qq.com','126.com','sohu.com','hotmail.com','gmail.com','139.com','189.com']
	});
	$('#email').keyup(function(){
			var obj = $("#email").val();
			if(obj.indexOf('@')!=-1){
				//TODO 补全email
			}
	}); 

   function openwindow(url,name,iWidth,iHeight)
		{
			var url; 
			var name; 
			var iWidth; 
			var iHeight; 
			var iTop = (window.screen.availHeight-30-iHeight)/2; 
			var iLeft = (window.screen.availWidth-10-iWidth)/2; 
			window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}
	$("#protocol").click(function(){
		openwindow("${app}/userIndex/privacyAndUseProtocol","",1000,800);
				
	});

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


var securityLevel;
$(function(){
	$(".ml_180").hide();
	$("#signPassword").bind('input propertychange', function() {
		$(".ml_180").show();
		if($("#signPassword").val()==""){
			$(".ml_180").hide();
		}
		calLevel();
	});
 });
 	
 function calLevel() {
		var value = $.trim($("#signPassword").val());
		$(".low").css("background-color", "#C8C8C8");
		$(".middle").css("background-color", "#C8C8C8");
		$(".high").css("background-color", "#C8C8C8");
		if (value.length < 8 || countRepeat(value) >= 3) {
			securityLevel = 1;
		} else if (value.length >= 8 && value.length < 12) {
			securityLevel = 2;
		} else if (value.length >= 12) {
			securityLevel = 3;
		}
		switch (securityLevel) {
		case 1:
			$(".low").css("background-color", "#E68F07");
			break;
		case 2:
			$(".low").css("background-color", "#E68F07");
			$(".middle").css("background-color", "#E68F07");
			break;
		case 3:
			$(".low").css("background-color", "#E68F07");
			$(".middle").css("background-color", "#E68F07");
			$(".high").css("background-color", "#E68F07");
			break;
		}
	}
	
	function countRepeat(value) {
		var map = {};
		for ( var i = 0; i < value.length; i++) {
			var c = value.charAt(i);
			if (map[c]) {
				map[c]++;
			} else {
				map[c] = 1;
			}
		}
		var max = 0;
		for ( var v in map) {
			if (max < map[v]) {
				max = map[v];
			}
		}
		return max;
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
				<h3><@messages key="sign.up.signup" /><span class="m_color2"> > </span></h3>
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
			 <div class="ml_180" style="margin-top:-0px;margin-left:12px">
             <span class="low  mr_10" style="background:#C8C8C8;"></span>
             <span class="middle  mr_10" style="background:#C8C8C8;"></span>
             <span class="high" style="background:#C8C8C8;"></span>
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
			<a href="#" id="protocol" class="m_a1"><@messages key="sign.up.terms" /></a>
			</div>
		</form>
	</div>
</div>

<!-- foot start-->
<#include "/footer.ftl" />
</body>
</html>

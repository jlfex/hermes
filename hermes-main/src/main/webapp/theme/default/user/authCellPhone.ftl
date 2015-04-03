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
<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<style type="text/css">
.jy_ml{margin-left: 18px;}
.jy_bg1{background:#b9baba;}
.jy_nobg_notb{border-top: none; background: none;color: #323232;padding: 20px 100px;}
.jy_nobg_notb .jy_info {margin: 20px 0;}
.jy_nobg_notb .jy_info .jy_titlt{width: 90px;display: inline-block;font-size:18px;}
.jy_nobg_notb .jy_info .jy_tip{color: #7e7e7e;font-size:14px;line-height: 25px;}
.jy_nobg_notb .jy_info .jy_tip2{color: #7e7e7e;font-size:14px;margin-left: 101px; line-height: 32px;}
.jy_nobg_notb .jy_info .m_btn3{width: 110px; height: 32px; margin-left: 20px; vertical-align: bottom;}
.jy_nobg_notb .jy_info .jy_alignr{width:80px;display: inline-block;text-align:right;}
.jy_nobg_notb .jy_info .jy_alignl{width:80px;display: inline-block;text-align:left;}
.jy_nobg_notb .jy_info select,.jy_nobg_notb .jy_info input{width: 240px; padding: 7px 10px; border: 1px solid #ccc; box-sizing: border-box; font-size: 14px; color: #777;margin-left: 18px;}
.jy_nobg_notb .jy_info .jy_tel{margin-left: 18px;}
.jy_nobg_notb .jy_info .jy_tel+a{font-size: 16px; text-decoration: underline; color: #018dc8; display: inline-block; margin-left: 166px;} 
.jy_nobg_notb .jy_btnlist{margin-left:83px;}
.jy_nobg_notb .jy_btnlist .m_btn3{margin: 14px 20px;height:30px;width:90px;}
</style>
</head>
<body>
<#include "/header.ftl" />

<!-- middle start-->
<div class="m_con m_fp m_fp2">
	<div class="m_fp_box">
		<div class="m_fp_s2">
			<img src="${app.theme}/public/other/images/m/icon1/ico8.png" />
			恭喜您登录成功！手机号码是您在本平台的重要身份凭证。
		</div>
	</div>
	<div class="m_fp_box jy_nobg_notb">
	<form class="form-horizontal" role="form" id="authPhoneFrm" name="authPhoneFrm" onsubmit="return false">
	    <input type="hidden" value="${userId}" id="userId" name="userId">
	    <input id="email" name="email" type="hidden" value="${email}">	
		<div class="jy_info">
			<span class="jy_titlt">手机认证</span>
			<div class="jy_tip">为确保您的手机可用，请填写您收到的手机动态码。</div>
		</div>
		<div class="jy_info">
			<span class="jy_alignr">手机号码</span>			
			<input type="text" id="cellphone" name="cellphone" value="${cellphone}" class="jy_tel" readonly="readonly" style="border:0px;"  onblur="checkPhone()"/>
			<a href="#" id="changePhoneNumber" style="margin-left:40px;" onclick='changeStyle()'>更换号码</a>
			<label for="cellphone" generated="true" class="error valid" id="label"></label>
			<span class="mv_msg col-xs-4" id="mv_cellphone" style="color:red;width:200px;font-weight:bold;"></span>			
		</div>
		<div class="jy_info">
			<span class="jy_alignr">手机动态码</span>
			<input type="text" id="validateCode" name="validateCode" onblur="checkCode()"/>
			<button id="getValidateCodeBtn" class="m_btn3 m_bg1"  style="color:white">获取验证码</button>
			<span class="mv_msg col-xs-4" id="mv_validateCode" style="color:red;width:200px;font-weight:bold;"></span>			
			<label for="validateCode" generated="true" class="error valid" style="margin-left:100px;"></label>
			<div class="jy_tip2" style="display:none;">动态码已发送至您的手机<span id="changeCellphnoe" style="color:blue;">${cellphone}</span>，请查收</div>
		</div>
		<div class="jy_btnlist">
			<button id="confirmAuthPhoneBtn" type="button" onClick="mysubmit()" class="m_btn3 m_bg1">确认</button>
			<button id="skipAuthPhoneBtn" type="button" class="m_btn3 m_bg2">跳过</button>
		</div>
		</form>
	</div>
	<div class="m_tip_text"></div>
</div>



<!-- foot start-->
<#include "/footer.ftl" />
	<script type="text/javascript" charset="utf-8">
	<!--
	var seconds;
	jQuery(function($) {
		$('#skipAuthPhoneBtn').on('click', function() {
		     window.location.href="${app}/userIndex/authName?email=" + $('#email').val();	   						
		});
		$("#getValidateCodeBtn").on("click",function(){
		    if(!checkPhone()){
		       	document.getElementById("#getValidateCodeBtn").disabled=true;		       
		    }
		    $("#changeCellphnoe").html($("#cellphone").val());
		    $("#changePhoneNumber").removeAttr("href").css("color","#CECED1");
		    document.getElementById("cellphone").disabled = true;
			$('.jy_tip2').show();
			seconds = 60;
			var cellphone = $("#cellphone").val();
            $("#getValidateCodeBtn").attr('disabled',true);
			$.ajax('${app}/auth/sendphoneCode/${userId}/?cellphone='+cellphone, {
				type: 'post',
				timeout: 10000,
				success: function(data, textStatus, xhr) {
					countDown(seconds);
					 if(data.type=="FAILURE"){
					    $('.jy_tip2').remove();
					 	$("#result").removeClass("alert-info").addClass("alert-warning");
					 }else{
					   $("#result").removeClass("alert-warning").addClass("alert-info");
						
					 }
					$("#result").html(data.firstMessage);
				}
			});
		});
	});
	function countDown(){
		if(seconds>0){
		 	$('#getValidateCodeBtn').text(seconds+'s');
		 	seconds=seconds-1;
			setTimeout("countDown(seconds)",1000);
		}else{
			$("#getValidateCodeBtn").removeClass("disabled");
			$('#getValidateCodeBtn').text('获取验证码');
		    $("#changePhoneNumber").css("color","#878DCB");
		    document.getElementById("cellphone").disabled = false;
		    $("#getValidateCodeBtn").removeAttr('disabled');
		}
	}
	
	function subAuthPhone(){
			var validateCode=$("#validateCode").val();
			var cellphone = $("#cellphone").val();
			$.ajax('${app}/auth/authCellphone/${userId}/?cellphone='+cellphone+'&validateCode='+validateCode, {
				type: 'post',
				dataType: 'json',
				timeout: 10000,
				success: function(data, textStatus, xhr) {
					 if(data.type=="FAILURE"){
					 	$(".jy_tip2").remove();
					 	$("#mv_validateCode").html(data.firstMessage);
					 }else if(data.type=="SUCCESS"){	
		               window.location.href="${app}/userIndex/authName?email=" + $('#email').val();
					 }
				}
		});
	}
	
	function changeStyle(){
		$("#cellphone").attr('style','border:2px solid #F1F1F1;background-color:#FFF7B2;');
		document.getElementById("cellphone").readOnly=false;		
	}
	
	function mysubmit(){
		if(checkPhone() && checkCode()){
			subAuthPhone();
		 }
	}
	
	function checkPhone(){	
        var vcellphone = /^((1)+\d{10})$/;
        var cellphone=$("#cellphone").val();
        if(cellphone==""){
			$("#mv_cellphone").html("不能为空");
			return false;
		}else if(!vcellphone.test(cellphone)){
			$("#mv_cellphone").html("手机号码格式错误");
			return false;
		}else{
			$("#mv_cellphone").html("");
		}
     	var _result = false;
		 $.ajax({
	        url: "${app}/userIndex/checkPhone?phone="+cellphone,
	        type: "POST",
	        dataType: 'json',
	        timeout: 10000,
	        async:false,    
			success: function(data, textStatus, xhr) {
				 if(data.type=="FAILURE"){
	               $("#mv_cellphone").html(data.firstMessage);
				 }else if(data.type=="SUCCESS"){
				 	_result = true;
				 }
			}
	    });
    	return _result;
	}
	function checkCode(){
	    var vvalidateCode = /[^\d]/g;
        var validateCode=$("#validateCode").val();
        if(validateCode=="" || validateCode==null){
			$("#mv_validateCode").html("不能为空");
			return false;
		}else if(vvalidateCode.test(validateCode)){
			$("#mv_validateCode").html("只能输入数字");
			return false;
		}else if(validateCode.length != '6'){
			$("#mv_validateCode").html("请输入一个长度为6位数字");
			return false;				
		}else{
			$("#mv_validateCode").html("");
		}
		return true;   	    
	 }
</script>
</body>
</html>

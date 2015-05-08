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
<script type="text/javascript" src="${app.theme}/public/javascripts/jquery.validate.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
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
<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="_content">
<!-- middle start-->
<div class="m_con m_fp m_fp2" style="min-height:500px;">
	<div class="m_fp_box">
		<div class="m_fp_s2">
			<img src="${app.theme}/public/other/images/m/icon1/ico8.png" />
			<#if userProperties.authCellphone =='10'>
			手机认证已成功!为确保您的资金安全，请进行实名认证
			<#else>
			手机尚未认证!为确保您的资金安全，请进行实名认证
			</#if>
		</div>
	</div>
	<div class="m_fp_box jy_nobg_notb">
	<form class="form-horizontal" role="form" id="authIdentityForm">
	<input type="hidden" value="${userId}" id="userId" name="userId"  >
	<input id="email" name="email" type="hidden" value="${email}">	
		<div class="jy_info">
			<span class="jy_titlt">实名认证</span>
			<div class="jy_tip">请填写您本人真实有效的身份信息，一旦认证成功，信息将不可更改。</div>
		</div>
		<div class="jy_info">
			<span class="jy_alignl">真实姓名</span>
			<input type="text" id="realName" name="realName" onblur="verificationInf()">
			<label for="realName" generated="true" class="error valid"></label>
			<span class="mv_msg" id="mv_realName" style="color:red;width:200px"></span>			
		</div>
		<div class="jy_info">
			<span class="jy_alignl">证件类型</span>
			<select id="idType" name="idType">
			    <#list idTypeMap?keys as key> 
					<option value="${key}" <#if userBasic?exists><#if userBasic.idType?exists&&userBasic.idType==key> selected</#if></#if>>${idTypeMap[key]}</option> 
				</#list>
			</select>			
		</div>
		<div class="jy_info">
			<span class="jy_alignl">证件号码</span>
			<input type="text" id="idNumber" name="idNumber" onblur="verificationInf()">
			<label for="idNumber" generated="true" class="error valid"></label>
			<span class="mv_msg" id="mv_idNumber" style="color:red;width:200px"></span>
		</div>
		<div class="jy_btnlist">
			<button id="confirmAuthIdentityBtn" type="button" onClick="mysubmit()" class="m_btn3 m_bg1">确认</button>
			<button id="cancelAuthIdentityBtn" type="button" class="m_btn3 m_bg2">跳过</button>			
		</div>
		</form>
	</div>
	<div class="m_tip_text"></div>
</div>
</div>
<#include "/footer.ftl" />
</div>
<script type="text/javascript" charset="utf-8">
	jQuery(function($) {
		$('#cancelAuthIdentityBtn').on('click', function() {
               window.location.href="${app}/userIndex/authBankCard?email=" + $('#email').val();		
        });
	});
	function verificationInf(){
		var vname = /^[\u4e00-\u9fa5]{2,20}$/;
		var identityId1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{2}[xX\d]$/;
        var identityId2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}[xX\d]$/;
        var passport=/^\w{5,20}$/;
        var realName=$("#realName").val();
        var idType=$("#idType").val();
        var idNumber=$("#idNumber").val();

		if(realName==""){
			$("#mv_realName").html("姓名不能为空");
			return false;
		}else if(!vname.test(realName)){
			$("#mv_realName").html("姓名只能为汉字");
			return false;
		}else{
			$("#mv_realName").html("");
		}
				
		if(idNumber==""){
			$("#mv_idNumber").html("证件号码不能为空");
			return false;
		}else{
			$("#mv_idNumber").html("");
		 }
		
		if(idType=="00"){
			if(idNumber.length==15){
				if(!identityId1.test(idNumber)){
					$("#mv_idNumber").html("身份证号码输入错误");
					return false;
				 }
			 }else if(idNumber.length==18){
			 	if(!identityId2.test(idNumber)){
					$("#mv_idNumber").html("身份证号码输入错误");
					return false;
				 }
			 }else{
			 	$("#mv_idNumber").html("身份证号码输入错误");
			 	return false;
			 }
		 }else if(idType=="01"){
		 		if(!passport.test(idNumber)){
					$("#mv_idNumber").html("护照号码输入错误");
					return false;
				 }
		 }else{
		 	$("#mv_idType").html("证件类型错误");
		 	return false;
		 }
		 
		 return true;
	}
	function mysubmit(){
		if(verificationInf()){
			authIdentity();
		 }
	}
			
	function authIdentity(){
		$.ajax({
				data: $("#authIdentityForm").serialize(),
		        url: "${app}/auth/authId/${userId}",
		        type: "POST",
		        dataType: 'json',
		        timeout: 10000,
		        success: function(data) {
		      	   if(data.type=="SUCCESS"){
                       window.location.href="${app}/userIndex/authBankCard?email=" + $('#email').val();
					}
		        }
		    });
	}
</script>
</body>
</html>
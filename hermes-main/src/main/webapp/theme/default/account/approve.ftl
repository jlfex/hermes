<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.theme}/public/other/javascripts/jquery-1.10.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/approve.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>

<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="sub_main">
	<div class="account_center">
		<div class="account_nav_left">
			<div class="title"></div>
			<div class="left_nav">

				<!--信息管理-->
				<div class="information">
					<h2>信息管理</h2>
					<ul>
						<li><a href="${app}/account/getUserInfo" class="a_01">个人信息</a></li>
						<li class="liactivebg"><a href="${app}/auth/approve" class="a_020">认证中心</a></li>
					</ul>
				</div>
				<div class="navline"></div>
				<div class="information">
					<h2>理财信息</h2>
					<ul>
						<li><a href="accountMyLoan.html" class="a_03">我的借款</a></li>
						<li><a href="accountMyInvest.html" class="a_04">我的理财</a></li>
					</ul>
				</div>
				<div class="navline"></div>

				<div class="information">
					<h2>资金管理</h2>
					<ul>
						<li><a href="accountList.html" class="a_05">资金明细</a></li>
						<li><a href="accountFilled.html" class="a_06">账户充值</a></li>
						<li><a href="accountMoney.html" class="a_07">账户提现</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="account_content_right">
			<div class="account_right">
                <div class="account_right_border">
    				<div class="email_aprove">
                        <div class="aprove_left">
                            <div class="aprove_detail img01">
                                <h4>邮箱认证</h4>
                                <p class="p1"><span>已认证</span>&nbsp;&nbsp;<span>${email}</span><p>
                            </div>
                        </div>
                        <div class="aprove_right"><span><img src="${app.theme}/public/other/images/icon1/icon_right.png"></span></div>
                        <div class="clearfix"></div>
                    </div>
					<#if phoneSwitch==true>
	                    <div class="phone_aprove">
	                        <div class="aprove_left_02">
	                            <div class="aprove_detail img02">
	                                <h4>手机认证</h4>
	                                <#if userPro.authCellphone=='10'>
	                                	<p class="p1"><span>已认证</span><p>
	                                <#else>
	                               		 <p class="p2"><span>未认证</span><p>
	                                </#if>
	                            </div>
	                        </div>
	                        <div class="aprove_right">
	                        		<#if userPro.authCellphone=='10'>
	                                	<span>&nbsp;&nbsp;&nbsp;&nbsp;<img src="${app.theme}/public/other/images/icon1/icon_right.png"></span>
	                                <#else>
	                               		  <p class="p3">申请手机认证</p>
	                                </#if>
	                        </div>
	                        <div class="clearfix"></div>
	                        <div class="formapprove3">
	                            <form id="phoneForm" name="phoneForm" >
	                                <label>手机号码</label> &nbsp;&nbsp;<input id="cellphone" name="cellphone" type="text"><br><br>
	                                <input id="getCode" type="button" value="获取验证码" style="height:25px;" ><br><br>
	                                <label>验证码 &nbsp;</label>&nbsp;&nbsp;<input id="validateCode" name="validateCode" type="text"><br><br>
	                                <div id="phoneerror"></div>
	                                <span class="submitimg"><a href="#" id="authPhone" class="m_btn1 m_bg1">提交</a><span>
	                            </form>
	                        </div>
	                    </div>
					</#if>
					<#if idSwitch==true>
	                    <div class="name_aprove">
	                        <div class="aprove_left">
	                            <div class="aprove_detail img03">
	                                <h4>实名认证</h4>
	                                 <#if userPro.authName=='10'>
	                                	<p class="p1"><span>已认证</span><p>
	                                <#else>
	                               		 <p class="p2"><span>未认证</span><p>
	                                </#if>
	                            </div>
	                        </div>
	                        <div class="aprove_right">
	                        		 <#if userPro.authName=='10'>
	                                	<span>&nbsp;&nbsp;&nbsp;&nbsp;<img src="${app.theme}/public/other/images/icon1/icon_right.png"></span>
	                                <#else>
	                               		  <p class="p4">申请实名认证</p></div>
	                                </#if>
	                        <div class="clearfix"></div>
	                        <div class="formapprove4">
	                            <form id="idForm" name="idForm">
	                                <label>真实姓名</label> &nbsp;&nbsp;<input id="realName" name="realName" type="text"><br><br>
	                                <label>证件类型</label> &nbsp;&nbsp;
	                                <select name="idType" id="idType" class="sty">
			                                            <#list idTypeMap?keys as key> 
										                 <option value="${key}" >${idTypeMap[key]}</option> 
										                </#list>
			                                        </select>  
	                                <br><br>
	                                <label>证件号码</label> &nbsp;&nbsp;<input id="idNumber" name="idNumber" type="text"><br><br>
	                                <div id="iderror"></div>
	                                <span class="submitimg"><a href="#"  id="authId"class="m_btn1 m_bg1">提交</a><span>
	                            </form>
	                        </div>
	                    </div>
                    </#if>
				</div>                    
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>


<div class="push"><!-- not put anything here --></div>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
<script type="text/javascript">
<!--
jQuery(function($) {
	$('#getCode').click(function(){
		var cellphone=$("#cellphone").val();
		$.ajax({
	        url: "${app}/auth/sendPhoneCode/"+cellphone,
	        type: "POST",
	        dataType: 'json',
	        timeout: 10000,
	        success: function(data) {
	            if(data.type=="FAILURE"){
						$("#phoneerror").html(data.firstMessage);
				}
	        }
	    });
	});
	$('#authPhone').click(function(){
		$.ajax({
			data: $("#phoneForm").serialize(),
	        url: "${app}/auth/authCellphone",
	        type: "POST",
	        dataType: 'json',
	        timeout: 10000,
	        success: function(data) {
	            if(data.type=="SUCCESS"){
	            	window.location.href="${app}/auth/approve";
				}else if(data.type=="FAILURE"){
						$("#phoneerror").html(data.firstMessage);
				}
	        }
	    });
	});
	$('#authId').click(function(){
		$.ajax({
			data: $("#idForm").serialize(),
	        url: "${app}/auth/authId",
	        type: "POST",
	        dataType: 'json',
	        timeout: 10000,
	        success: function(data) {
	           if(data.type=="SUCCESS"){
	            	window.location.href="${app}/auth/approve";
				}else if(data.type=="FAILURE"){
						$("#iderror").html(data.firstMessage);
				}
	        }
	    });
	});
});
	
//-->
</script>
</body>
</html>

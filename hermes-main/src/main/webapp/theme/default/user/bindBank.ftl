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
<script type="text/javascript" src="${app.theme}/public/javascripts/mValidate.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/jquery.autocomplete.css">

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
			<#if userProperties.authName =='10'>
			实名认证已成功！投资前，您需要绑定银行卡以便充值和取现。
			<#else>
			尚未实名认证！投资前，您需要绑定银行卡以便充值和取现。
			</#if>
		</div>
	</div>
	<div class="m_fp_box jy_nobg_notb">
		<form class="form-horizontal" role="form" id="authIdentityForm">
		<input type="hidden" value="${userId}" id="userId" name="userId">		
		<div class="jy_info">
			<span class="jy_titlt">银行卡绑定</span>
			<div class="jy_tip">为保证资金安全，请绑定持卡人本人的银行卡。</div>
		</div>
		<div class="jy_info">
			<span class="jy_alignr">持卡人</span>
			<#if userProperties.realName??>
			<input type="text" class="jy_ml" id="realName" value="${realName}" name="realName" style="border:0px;">			
			<#else>
			<input type="text" class="form-control" id="realName" name="realName" onblur="verification()">
			<label for="deposit" generated="true" class="error valid"></label>
			<span class="mv_msg col-xs-6" id="mv_realName" style="color:red;width:200px"></span>						
			</#if>
		</div>
		<div class="jy_info">
			<span class="jy_alignr">银行卡名称</span>
		    <select id="bankId" name="bankId" class="form-control">
		        <option value="">请选择</option>
		        <#list banks as b>
			    <option value="${b.id}" selected = "selected">${b.name}</option>
		        </#list>				    
			</select>
		</div>
		<div class="jy_info">
			<span class="jy_alignr">开户所在地</span>
			<select id="cityId2" name="cityId2"></select>
			<select id="cityId" name="cityId" onchange="reloadBank();"></select>																				
		</div>
		<div class="jy_info">
			<span class="jy_alignr">开户行</span>
			<input type="text" class="form-control" id="deposit" name="deposit" onblur="verificationInf()">
			<label for="deposit" generated="true" class="error valid"></label>
			<span class="mv_msg col-xs-6" id="mv_deposit" style="color:red;width:200px"></span>			
		</div>
		<div class="jy_info">
			<span class="jy_alignr">银行账号</span>
			<input type="text" class="form-control" id="account" name="account" onblur="verificationInf()">
			<label for="account" generated="true" class="error valid"></label>
			<span class="mv_msg col-xs-6" id="mv_account" style="color:red;width:200px"></span>
		</div>
		<div class="jy_btnlist">
			<button id="confirmAuthIdentityBtn" type="button" onClick="mysubmit()" class="m_btn3 m_bg1">确认</button>
			<button id="cancelAuthIdentityBtn" type="button" class="m_btn3 m_bg2">跳过</button>			
		</div>
	</div>
	<div class="m_tip_text"></div>
</div>

<!-- foot start-->
<#include "/footer.ftl" />
<script type="text/javascript" charset="utf-8">
	jQuery(function($) {
		$('#cancelAuthIdentityBtn').on('click', function() {
		     window.location.href="${app}/invest/index?type=auth1";	   						
		});	
        $.area({ data: ${area}, bind: [$('#cityId2'), $('#cityId')] });
	});	
	    //ajax获取后台数据
        function reloadBank(){
	 	    $.ajax({
		        data: $("#authIdentityForm").serialize(),
		        contentType: "application/json",
		        url: "${app}/userIndex/findBranchBankByBankAndCity",
		        dataType: "json",
		        success: function (msg) {
		            if (msg != null) {
		                $("#deposit").autocomplete(msg, {
		                    minChars: 1,                    //最少输入字条
		                    max: 30,
		                    autoFill: false,                //是否选多个,用","分开
		                    mustMatch: false,               //是否全匹配, 如数据中没有此数据,将无法输入
		                    matchContains: true,            //是否全文搜索,否则只是前面作为标准
		                    scrollHeight: 220,
		                    width: 240,
		                    multiple: false,
		                    formatItem: function (row, i, max) {                    //显示格式
		                    	return "<span>" + row.branchBankName + "</span>";
		                    },
		                    formatMatch: function (row, i, max) {               //以什么数据作为搜索关键词,可包括中文,
		                        return row.branchBankName;
		                    },
		                    formatResult: function (row) {                      //返回结果
		                        return row.branchBankName;
		                    }
		                }).result(function (event, data, formatted) {
		                    //alert(data.id);
		                    //根据最终返回的数据做处理
		                    
		                });
		            }		            
		         }
		    });        
        }
	
	function mysubmit(){
		if(verificationInf() && verification()){
			bindBank();
		 }
	}
    function verification(){
        var vrealName = /^[\u4e00-\u9fa5]{2,20}$/;
        var realName=$("#realName").val();      
        if(realName !=""){
            return true;
        }else if(realName==""){
			$("#mv_realName").html("持卡人不能为空");
			return false;
		}else if(!vrealName.test(realName)){
			$("#mv_realName").html("持卡人只能为汉字");
			return false;
		}else{
			$("#mv_realName").html("");
		}
         return true;
    }
	function verificationInf(){
		var vdeposit = /^[\u4e00-\u9fa5]{2,20}$/;		
		var vaccount=/^[0-9]{12,20}$/;
        var deposit=$("#deposit").val();
        var account=$("#account").val();

			
		if(deposit==""){
			$("#mv_deposit").html("开户行不能为空");
			return false;
		}else if(!vdeposit.test(deposit)){
			$("#mv_deposit").html("开户行只能为汉字");
			return false;
		}else{
			$("#mv_deposit").html("");
		}
				
		if(account==""){
			$("#mv_account").html("银行账号不能为空");
			return false;		
		}else if(!vaccount.test(account)){
			$("#mv_account").html("银行账号输入错误，长度12-20位，且只能是数字");
			return false;		
		}else{
			$("#mv_account").html("");
		}		
		 
		return true;
	}

	function bindBank(){
		$.ajax({
				data: $("#authIdentityForm").serialize(),
		        url: "${app}/auth/handerBindBank/${userId}",
		        type: "POST",
		        dataType: 'json',
		        timeout: 10000,
		        success: function(data) {
		      	   if(data.type=="SUCCESS"){
                       window.location.href="${app}/invest/index?type=auth1";
					}else if(data.type=="FAILURE"){
						$("#authIdMessage").removeClass("hidden");	
						$("#authIdResult").html(data.firstMessage);
					}
		        }
		});
	}
</script>
</body>
</html>
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
<script type="text/javascript" src="${app.theme}/public/javascripts/email.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//邮箱补全
	/*var inputSuggest = new InputSuggest({
		input: document.getElementById('email'),
		data: ['sina.com','sina.cn','163.com','qq.com','126.com','sohu.com','hotmail.com','gmail.com','139.com','189.com']
	});
	$('#email').keyup(function(){
			var obj = $("#email").val();
			if(obj.indexOf('@')!=-1){
				//TODO 补全email
			}
	}); */
	document.onkeydown = function(event) {
	    if (navigator.userAgent.indexOf("MSIE") > 0) { //IE
	        if (window.event.keyCode == 13) {
	            signIn();
	        }
	    } else { //firefox&&chrome
	        if (event.keyCode == 13) {
	            signIn();
	        }
	    }
	}
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
    });  
    $("input").blur(function(){  
        var inpVal = $(this).val();  
        if (inpVal == "") {  
            $(this).val(this.defaultValue);
            if($(this).attr('id')=='signPassword'){
            		$(this).hide(); 
								$('#signPwd').show();
								$('#signPwd').val('登录密码'); 
            }
             
        }  
    });
	
});
 function signIn(){
		var email=$("#email").val();
		var pwd=$("#signPassword").val();
		if(email==""||email==null||pwd==""||pwd==null){
			$(".m_item_add").html("<p><@messages key="sign.in.error" /></p>");
			$(".m_item_add").show();
			return false;
		}
		$.ajax({
			data: $("#userForm").serialize(),
		    url: "${app}/userIndex/signIn",
		    type: "POST",
		    dataType: 'json',
		    timeout: 10000,
			success: function(data){
				if(data.type=="SUCCESS"){
					window.location.href="${app}";
				}else if(data.type=="WARNING"){
					$(".m_item_add").html("<p>"+data.firstMessage+"</p>");
					$(".m_item_add").show();
					window.location.href="${app}/userIndex/resendMail?email="+email;
				}else{
					$(".m_item_add").html("<p>"+data.firstMessage+"</p>");
					$(".m_item_add").show();
				}
			}
		});
	}
function clearPwd(){
	$(".m_item_add").hide();
    $("#signPassword").val("");
} 
</script>
</head>

<body class="index">
<div class="_container">
<#include "/header.ftl" />
<div class="_content">
<!-- middle start-->
<div class="m_con m_lg">
	<div class="m_lg_l">
		<img src="${app.theme}/public/other/images/m/image1/img1.jpg" />
	</div>
	<div class="m_lg_r"  id="login">
		<form id="userForm">
			<div class="m_item">
				<h3>登录Hermes</h3>
			</div>
			 <div class="m_item_add"><p>账户或密码错误</p></div>
			<div class="m_item">
				<input id="email" name="email" type="text"  onkeypress="clearPwd()" class="mv_email" />
				 <span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<input id="signPwd" type="text"   value="登录密码" class="register"/>
				<input id="signPassword" name="signPassword" type="password"  class="mv_pwd" style="display:none;"/>
				<span class="mv_msg"></span>
			</div>
			<div class="m_item">
				<a href="#" id="signIn" onclick="signIn();" class="m_btn1 m_bg1 mv_submit">登录</a>
			</div>
			<div class="m_item m_tc">
				<a href="${app}/userIndex/retrivePwd" class="m_a1">找回密码</a> | 
				<a href="${app}/userIndex/regNow" class="m_a1">立即注册</a>
			</div>
		</form>
	</div>
</div>
</div>
<!-- foot start-->
<#include "/footer.ftl" />
</div>
</body>
</html>

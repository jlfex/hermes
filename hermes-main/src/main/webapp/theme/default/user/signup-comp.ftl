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
  
});
function checkAccount(){
	var account=$("#account").val();
	if(account==""||account==null){
		return false;
	}
	 $.ajax({
        url: "${app}/userIndex/checkAccount?account="+account,
        type: "POST",
        dataType: 'json',
        timeout: 10000,
        error: function() {

		},
        success: function(data) {
        }
    });
}
function checkPhone(){
	var phone=$("#cellphone").val();
	if(phone==""||phone==null){
		return false;
	}
	 $.ajax({
        url: "${app}/userIndex/checkCellphone?phone="+phone,
        type: "POST",
        dataType: 'json',
        timeout: 10000,
        error: function() {

		},
        success: function(data) {
        }
    });
}
</script>
</head>

<body>
<#include "/header.ftl" />

<!-- middle start-->
<div class="m_con m_rg" >
	<div class="m_rg_l">
		<img src="${app.theme}/public/other/images/m/image1/img1.jpg" />
	</div>
	<div class="m_rg_r" id="register">
		<form id="signForm" name="signForm" action="${app}/userIndex/supplement" method="post">
			<div class="m_item">
				<h3><span class="m_color2"><@messages key="sign.up.signup" /></span> >  <@messages key="sign.up.supplementary" /></h3>
			</div>
			<input name="id" value="${user.id}" type="hidden"/>
			<input name="email"	value="${user.email}" type="hidden"/>
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
				<a href="javascript:document.signForm.submit();" class="m_btn1 m_bg1 mv_submit"><@messages key="sign.up.signup" /></a>
			</div>
		</form>
	</div>
</div>

<!-- foot start-->
<#include "/footer.ftl" />
</body>
</html>

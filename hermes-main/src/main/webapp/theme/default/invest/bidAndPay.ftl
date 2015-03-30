<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<script type="text/javascript" src="${app.js}/jquery.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript">
jQuery(function($) {
     $('.confirm').click(function(){
        window.location.href="${app}/invest/jlfexBid?investAmount="+'${investAmount!''}'+"&loanId="+'${loanId!''}';
     });
});
</script>
</head>

<body>

<#include "/header.ftl" />


<!-- middle start-->

<div class="m_con m_fp m_fp2">
		<div class="m_fp_box">
		
		投标并支付:
		<#if bankAccount?? && userProperties??> 
			<div class="m_fp_s2">
				关联银行卡： <span class="lightyellow">${bankAccount.account!''}</span>
			</div>
			<div class="m_fp_s2">
				支付金额： <span class="lightyellow">${investAmount}元</span>
			</div>
			<div class="m_fp_s2">
				大写金额： <span class="lightyellow">${investAmountChinese}元</span>
			</div>
			<div class="m_fp_s2">
	             <input type="checkbox" />  我已阅读并同意 <a href="#" id="funanceProtocol" class="blue">《理财协议》</a><span class="mv_msg" data-msg='勾选后方可点击确认！'></label>
			</div>
		    <span class="confirm">
		    <a href="#"  class="m_btn1 bt_red a_middle mv_submit">确认</a>
		</span>
		<#elseif bankAccount??>
		      请先到认证中心进行《实名制认证》，<a href="${app}/account/index" class="ck">认证中心</a>
	    <#elseif userProperties??>
	              请先到认证中心进行《银行卡绑定》，<a href="${app}/account/index" class="ck">认证中心</a>
		</#if>
	
	</div>
	<p class="a_pa_lt"><a href="${app}/invest/index" class="q_btn1 q_bg1">继续投标</a><a href="${app}/account/index?type=invest" class="ck">查看我的理财</a><a href="${app}/invest/info?loanid=${loanId}"  class="ck">返回该借款</a></p>
</div>

<form id="loanDetail" name="loanDetail">
<input id="loanid" name="loanid" type="hidden" value="${loanId}" ></input>	
<input id="investamount" name="investamount" type="hidden" value="${investAmount!''}" >
</form> 
<#include "/footer.ftl" />
</body>
</html>

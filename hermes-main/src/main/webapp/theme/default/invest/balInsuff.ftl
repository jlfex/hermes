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
<style>
	.related-bank{ border:1px solid #d8d8d8; border-radius:3px; margin-top:20px; height:60px; line-height:60px; vertical-align:middle;}
	.related-bank span{ display:inline-block;vertical-align:middle; margin-right:10px;}
	.related-bank span img{display:inline-block;}
	.related-bank span.bank-title{ font-size:16px; vertical-align:middle; margin:0 10px;}
	#bank-pay .block{ margin:20px; border:0;}
	#bank-pay .block label,#bank-pay .block span{ display:inline-block;}
	#bank-pay .block label{ font-size:14px; width:80px;}
	#bank-pay .block a.a_dec{ color:#018dc8; text-decoration:none;}
	.fs_18{ font-size:18px;}
	.fc_orange{ color:#ff4520;}
	.ml_20px{ margin-left:20px;}
	.sweet-tip{ margin:40px 0px; border-top:1px solid #d8d8d8; padding-top:10px; }
	.sweet-tip p{ line-height:25px;color:#727272;}
</style>
<style>
 
   .content{ margin:0 20px;}
   .content p{ line-height:40px; text-align:center;}
   .mtb_30px{ margin:30px 0;}
  .content p img{ display:inline-block; vertical-align:middle;}
</style>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#confirm").click(function() {
			var investAmount  = $("#investAmount").val();
			var needAmount = $("#needAmount").val();
			var loanId = $("#loanId").val();
			var otherRepay = $("#otherRepay").val();
			
			window.location.href="${app}/invest/goBid2Pay?investAmount="+investAmount+"&needAmount="+needAmount+"&loanId="+loanId+"&otherRepay="+otherRepay;
		});
	});
</script>
<body class="index">
<div class="_container">
<#include "/header.ftl" />

<!-- middle start-->
<div class="m_con m_fp">
	<div class="content" style="min-height:500px">
    	<p><img src="${app.theme}/public/other/images/icon2/1.png" width="24" height="24" alt="" /> 您的账户余额不足<p>
     	<p>计划投标金额：<span class="blue fs_18">${investAmount}</span> 元</p>
        <p>账户可用余额：<span class="blue fs_18">${balance}</span> 元</p>
        <p>还需支付金额：<span class="colorred fs_18">${needAmount}</span> 元</p>
        <p class="mtb_30px">
        	<a href="#" class="m_btn3 m_bg1" id="confirm">确认</a>
            <a href="#" class="m_btn3 m_bg2 ml_20px" id="Button2" onclick="window.history.go(-1)">取消</a>
        </p>   
	</div>
</div>
<form id="loanDetail" name="loanDetail">
<input id="investAmount" name="investAmount" type="hidden" value="${investAmount}" ></input>	
<input id="needAmount" name="needAmount" type="hidden" value="${needAmount}" ></input>
<input id="loanId" name="loanId" type="hidden" value="${loanId}" ></input>
<input id="otherRepay" name="otherRepay" type="hidden" value="${otherRepay}" ></input>
</form> 
<div class="push"><!-- not put anything here --></div>
</div>
<#include "/footer.ftl" />
</body>
</html>

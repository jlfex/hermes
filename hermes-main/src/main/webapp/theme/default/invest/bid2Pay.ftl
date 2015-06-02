<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" src="${app.js}/jquery.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
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
<script type="text/javascript">
jQuery(function($) {
	$('#cr').click(function() {
	   var cr = $("#cr").is(':checked');
		if(!cr) {
			$("#err_msg").addClass("mv_error").html('请勾选代扣委托书');
		} else {
			$("#err_msg").removeClass("mv_error");
			$("#err_msg").html('');
		}
	});
     
     $('#confirm').click(function(){
     var cr = $("#cr").is(':checked');
     if(!cr) {
     	 $("#err_msg").addClass("mv_error").html('请勾选代扣委托书');
     	 return;
     }
     
     	$.ajax({
     		data:$("#loanDetail").serialize(),
     		url:"${app}/invest/bid2Pay",
     		type:"POST",
     		dataType:'json',
     		success:function(data) {
     			 window.location.href="${app}/invest/bid2PayResult?message="+data.messages[0]+"&type="+data.type;
     		}
     	});
     });
     
     
    $('#payEntrustProtocol').click(function(){
		openwindow("${app}/loan/payEntrustProtocol","",1000,900);
	});
    function openwindow(url,name,iWidth,iHeight)
	{
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}
});
</script>
</head>
<body class="index">
<div class="_container">
<#include "/header.ftl" />
<!-- middle start-->
<div class="middle_content" style="min-height:500px">
	<#if bankAccount?? && userProperties??>
    <div class="detail">
		<div class="flow">
        	<div id="bank-pay">
                <div class="title">投标并支付</div>
                    <div class="related-bank">
                        <span class="bank-title">关联银行卡</span>
                        <span class="bank-name"><img src="${app.theme}/public/images/bank/${bankAccount.bank.logo!''}" width="142" height="41" /></span>	
                        <span class="bank-num">${bankMask}</span>
                    </div>
                    <div class="block">
                        <label>支付金额</label>
                        <span class="fs_18 fc_orange">${investAmount}</span>元
                    </div>
                    <div class="block">
                        <label>大写金额</label>
                        <span class="fs_18">${investAmountChinese}</span>
                    </div>
                    <div class="block">
                        <label>&nbsp;</label>
                        <input type="checkbox" id="cr"/>我已阅读并同意 <a href="#" id="payEntrustProtocol" class="blue">《支付委托授权书》</a><span id="err_msg"></span>
                    </div>
                    <div class="block">
                        <label>&nbsp;</label>
                        <span>
                            <a href="#" id="confirm" class="m_btn3 m_bg1">确认</a>
                            <a href="#" id="cancel" class="m_btn3 m_bg2 ml_20px" onclick="window.history.go(-1)">取消</a>
                        </span>
                    </div>
                   <div class="sweet-tip">
                   	<div><strong>温馨提示</strong></div>
                    <p>1、请仔细阅读《支付委托授权书》；</p>
                    <p>2、委托代扣的金额直接用于投标项目；</p>
                    <p>3、所有账户金额将由第三方平台托管，平台本身布存放用户的投标资金。</p>
                   </div>
            </div>
		</div>
    </div>
    <#elseif bankAccount>
    	请先到认证中心进行《实名制认证》，<a href="${app}/account/index" class="ck">认证中心</a>
    <#elseif userProperties??>
    	请先到认证中心进行《银行卡绑定》，<a href="${app}/account/index" class="ck">认证中心</a>
    </#if>
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

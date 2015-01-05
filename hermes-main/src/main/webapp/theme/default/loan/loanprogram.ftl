<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mInvestAndLoan.js" charset="utf-8"></script>
<script type="text/javascript">
jQuery(function($) {
	$(".confirm").click(function(){
		$.ajax({
				data: $("#programconfirm").serialize(),
		        url: "${app}/loan/programconfirm",
		        type: "POST",
		        dataType: 'json',
		        cache: false,
		        timeout: 10000,
		        success: function(data) {
		      	   if(data.type=="SUCCESS"){
	            		window.location.href="${app}/loan/loansuccess";
					}else if(data.type=="FAILURE"){
					 	$("#back-info").html("借款发布失败");
					}
		        },
		        error: function(data){
		                $("#back-info").html("借款发布异常");
		        }
		    });
	});
	$('.deal').click(function(){
			openwindow("${app}/loan/deal/0","",1000,800);
			
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

<body>

<#include "/header.ftl" />
<div id="content" class="content">	
<form id="programconfirm" name="programconfirm">
<input id="productId" name="productId" type="hidden" value="${productId}"></input>
<input id="repayId" name="repayId" type="hidden" value="${repayId}"></input>
<input id="purposeId" name="purposeId" type="hidden" value="${purposeId}"></input>
<input id="amount" name="amount" type="hidden" value="${amount}"></input>
<input id="period" name="period" type="hidden" value="${period}"></input>
<input id="rate" name="rate" type="hidden" value="${rate}"></input>
<!-- middle start-->
<div class="m_con m_jkfa">
   <div id="back-info" class="section"></div>
	<h2 class="m">我的借款方案</h2>
	<div class="section">
		<table>
			<tr>
				<td class="wd1" width="40%"><a href="#" class="m_a1">${productName}<span class="m_ico1"></span></a></td>
				<td class="wd2" width="35%"><@messages key="model.loan.period" />：${period}<@messages key="common.unit.month"  /></td>
				<td width="25%"><@messages key="model.loan.amount" />： ${amount}<@messages key="common.unit.cny"  /></td>
			</tr>
			<tr>
				<td><@messages key="model.loan.purpose" />：${purposeName}</td>
				<td><@messages key="model.loan.rate" />：${rate}</td>
				<td><@messages key="model.repay.name" />：${repayName}</td>
			</tr>
		</table>
	</div>
	
	<div class="section">
		<p>偿还时间表</p>
		<table class="m_zebra">
			<tr>
				<th class="wd3" width="5%">期数</th>
				<th class="wd3" width="25%">应还本息/元</th>
				<th class="wd3" width="15%">还款本金/元</th>
				<th class="wd3" width="15%">还款利息/元</th>
				<th class="wd3" width="25%">月缴管理费/元</th>
				<th class="wd3" width="15%">未还本息/元</th>
			</tr>
			<#list repayPlans as rp>  
			 	<#assign repayPlan = 0 />
			 	<#if repayPlan == 0 >
					<tr class="nobg">
				 <#else> 
				 	<tr>
				 </#if>
					<td>
					<#if (rp.sequence)??>
						${rp.sequence}
					<#else> 
					</#if>
					</td>
					<td>
					<#if (rp.amount)??>
						${rp.amount?string('#,##0.00')} 
					<#else> 
					</#if>
					</td>
					<td>
					<#if (rp.principal)??>
						${rp.principal?string('#,##0.00')} 
					<#else> 
					</#if>
					</td>
					<td>
					<#if (rp.interest)??>
						${rp.interest?string('#,##0.00')} 
					<#else> 
					</#if>
					</td>
					
					<td>
					<#if (rp.otherAmount)??>
						${rp.otherAmount?string('#,##0.00')} 
					<#else> 
					</#if>
					</td>
					<td align="right">
					<#if (rp.unRepay)??>
						${rp.unRepay?string('#,##0.00')} 
					<#else> 
					</#if>
					</td>
				</tr>
				 <#assign repayPlan = repayPlan + 1 /> 
			</#list>
			
			
		</table>
	</div>
	<div class="section commonChecked">
	<p>借款描述</p>
		<textarea name="remark" id="remark" maxlength="100" placeholder="可对借款有更详细的描述【限100字以内】"></textarea>
        <p id="jp_text" class="jp_text"><span>还可输入<em>100</em>个字</span></p>
        <br /><br />
		<p>信息完整有助于提高借款效率 &nbsp;&nbsp;<a href="${app}/account/index" class="m_a1">完善个人信息</a>&nbsp;&nbsp;</p>
		
		<p class="mv_checked"><input type="checkbox" />我已阅读并同意&nbsp;&nbsp;
		<span class="deal">
			<a href="#" class="m_a1">《借款协议》</a>
		</span>
		<span class="mv_msg" data-msg='勾选后方可点击确定！'></span></p>
		<div class="btn_list">
			<span class="confirm">
				<a  class="m_btn1 m_bg1 mv_submit">确定</a>
			</span>
			<a href="javascript:history.back()" class="m_btn1 m_bg2">返回</a>
		</div>
		
	</div>
	
</div>
</form>
</div>
<#include "/footer.ftl" />
</body>
</html>

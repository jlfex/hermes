<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="shortcut icon" href="${app.theme}/public/images/smallIcon.png">
<link rel="apple-touch-icon-precomposed" href="${app.theme}/public/images/bigIcon.png">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mInvestAndLoan.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript">
jQuery(function($) {
		//var startAmount =  $('div.m_con.m_jk div.m_progress.progress1 .m_progress_start:visible').text();
		//$('div.m_con.m_jk div.m_progress.progress1 .num:visible').html(startAmount);
		//var startPeriod =  $('div.m_con.m_jk div.m_progress.progress2 .m_progress_start:visible').text();
		//$('div.m_con.m_jk div.m_progress.progress2 .num:visible').html(startPeriod);
		//var startRate=  $('div.m_con.m_jk div.m_progress.progress3 .m_progress_start:visible').text();
		//$('div.m_con.m_jk div.m_progress.progress3 .num:visible').html(startRate);
		//var startAmount =  $('div.m_con.m_jk div.m_progress.progress1 .m_progress_start:visible').text();
		//alert(startAmount);
  	var _twidth = ($("#myTab li").length*193)+50;
  	$("#myTab").css("width",_twidth+"px") ;
  	var _tml = 0;
  	$("#_perv").on("click",function(){
  		if(_tml<0){
  			_tml+=189;
  			$("#myTab").animate({marginLeft:'+=189px'},300);
  		}
  	});
  	$("#_next").on("click",function(){
  		if(_tml>-(($("#myTab li").length-5)*189)){
  		_tml-=189;
  			$("#myTab").animate({marginLeft:'-=189px'},300);
  		}
  	});
  
    var size =  $('#productSize').val();
	for (var i = 1; i <= size; i++) 
	{
	    $('input:radio[name=purpose_'+i+']:first').attr('checked', 'true');
	    $('input:radio[name=repay_'+i+']:first').attr('checked', 'true');
	}
		
	$('.btn_list').click(function(){
		var productName = $('li.active>h3').text();
		var KeyValue=$('li.active>h3').siblings("span").text();
		var pos = KeyValue.indexOf('$');
		var productId = KeyValue.substring(0,pos);
		var productIndex = KeyValue.substring(pos + 1);
		var amount = $('div.m_progress.progress1 .num:visible').text();
		var period = $('div.m_progress.progress2 .num:visible').text();
		var rate = $('div.m_progress.progress3 .num:visible').text();
		var purposeNameKey= 'purpose_'+productIndex;
		var purposeName =$("input[type='radio'][name='"+purposeNameKey+"']:checked").next("span").text();
		var purposeId =$("input[type='radio'][name='"+purposeNameKey+"']:checked").val();
		var repayNameKey= 'repay_'+productIndex;
		var repayName =$("input[type='radio'][name='"+repayNameKey+"']:checked").next("span").text();
		var repayId =$("input[type='radio'][name='"+repayNameKey+"']:checked").val();
	//	alert("productIndex"+productIndex+",productId"+productId+",productName"+productName+",amount"+amount+",period"+period+",rate"+rate+",purposeId"+purposeId+",purposeName"+purposeName+",repayId"+repayId+",repayName"+repayName);
	//	return false;
		$('#productId').val(productId);
		$('#productName').val(productName);
		$('#amount').val(amount);
		$('#period').val(period);
		$('#rate').val(rate);
		$('#purposeName').val(purposeName);
		$('#purposeId').val(purposeId);
		$('#repayName').val(repayName);
		$('#repayId').val(repayId);
		$("form[name='loanDisplay']")[0].submit();  
	});
});

</script>
<style type="text/css">
  table th{text-align:center;}
.logo2{font-family:"STXingkai";font-size: 22px!important;color: #595959!important;line-height: 70px; height: 70px; display: inline-block;}
</style>
</head>

</head>

<body>

<#include "/header.ftl" />

<div id="content" class="content">
<form id="loanDisplay" name="loanDisplay" action="${app}/loan/loanprogram" method="post">
<input id="productSize" name="productSize" type="hidden" value="${productSize}"></input>
<input id="productId" name="productId" type="hidden" ></input>
<input id="productName" name="productName" type="hidden" ></input>
<input id="amount" name="amount" type="hidden" ></input>
<input id="period" name="period" type="hidden" ></input>
<input id="rate" name="rate" type="hidden" ></input>
<input id="purposeName" name="purposeName" type="hidden" ></input>
<input id="purposeId" name="purposeId" type="hidden" ></input>
<input id="repayName" name="repayName" type="hidden" ></input>
<input id="repayId" name="repayId" type="hidden" ></input>
<!-- middle start-->
<div class="m_con m_jk">
	<div id="tab1"  class="commonChecked">
		
		<ul class="m_jk_t clearfix m_tab_t" id="myTab">
			 <#assign name = 0 />
			 <#list products as p>  
			  	 <#if name = 0>
			  	 	<li class="active">
			  	 <#else> 
			  	 	<li>
			  	 </#if>
			 	<#assign name = name + 1 /> 
				 	<h3>${p.name}</h3>
					<p><#if (p.description)??>${p.description}</#if></p>
				 	<span style=" visibility:hidden;">${p.id}$${name}</span>
				</li>
			</#list>
		</ul>
		<div class="m_jk_c m_tab_c">
			 <#assign detail = 0 />
				<#list products as p>  
			  	 <#if detail = 0>
			  	 	<div style="display: block">
			  	 <#else> 
			  	 	<div style="display: none">
			  	 </#if>
			  	<#assign detail = detail + 1 /> 
				 <table>
					<tr>
						<td class="wd1"><span class="m_ico2_1"></span></td>
						<td class="td1"><@messages key="model.loan.amount" /></td>
						<td colspan="4">
							<div class="m_progress progress1">
								<div class="m_progress" data-accuracy="0.01">
									<div class="m_progress_start"><span>${p.minAmount}</span>${p.minAmount}</div>
									<div class="m_progress_range">
										<div class="m_progress_percent_bg">
											<div class="m_progress_percent"></div>
										</div>
										<div class="m_progress_cursor"></div>
										<div class="m_progress_cursor_title">
											<span class="num">${p.minAmount}</span>
											<div class="corner"></div>
										</div>
									</div>
									<div class="m_progress_end"><span>${p.maxAmount}</span>${p.maxAmount}</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="wd1"><span class="m_ico2_2"></span></td>
						<td class="td1"><@messages key="model.loan.period" /></td>
						<td colspan="4">
							<div class="m_progress progress2">
								<div class="m_progress" data-accuracy="1">
									<div class="m_progress_start"><span>${p.minPeriod}</span>${p.minPeriod}<@messages key="common.unit.month"  /></div>
									<div class="m_progress_range">
										<div class="m_progress_percent_bg">
											<div class="m_progress_percent"></div>
										</div>
										<div class="m_progress_cursor"></div>
										<div class="m_progress_cursor_title">
											<span class="num">${p.minPeriod}</span><@messages key="common.unit.month"  />
											<div class="corner"></div>
										</div>
									</div>
									<div class="m_progress_end"><span>${p.maxPeriod}</span>${p.maxPeriod}<@messages key="common.unit.month"  /></div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="wd1"><span class="m_ico2_3"></span></td>
						<td class="td1"><@messages key="model.loan.rate" /></td>
						<td colspan="4">
							<div class="m_progress progress3">
								<div class="m_progress" data-accuracy="10">  <!-- 精确度 小数点二位(100) 小数点一位(10) 个位(1) 十位(0.1) 百位(0.01).... -->
									<div class="m_progress_start"><span>${p.minRate}%</span>${p.minRate}%</div>   <!-- span中的值为实际值，span后面值为显示值 -->
									<div class="m_progress_range">
										<div class="m_progress_percent_bg">
											<div class="m_progress_percent"></div>
										</div>
										<div class="m_progress_cursor"></div>
										<div class="m_progress_cursor_title">
											<span class="num">${p.minRate}</span>%
											<div class="corner"></div>
										</div>
									</div>
									<div class="m_progress_end"><span>${p.maxRate}</span>${p.maxRate}%</div>
								</div>
							</div>
						</td>
					</tr>
					<tr class="mv_checked">
						<td class="wd1"><span class="m_ico2_4"></span></td>
						<td class="td1"><@messages key="model.loan.purpose" /></td>
						 <#assign purpose = 0 />
						<#list p.loanUse as lu>  
								 <#if purpose % 2  = 0>
								 	 <td class="wd2">
									 <input type="radio" name="purpose_${detail}" value=${lu.id} /><span> ${lu.name}</span><br />
								 <#else> 
								 	<input type="radio" name="purpose_${detail}" value=${lu.id} /><span> ${lu.name}</span>
									</td>
								 </#if>
								 <#assign purpose = purpose + 1 /> 
						</#list>
						<span class="mv_msg" data-msg='请选择借款用途！'></span>
					</tr>
					<tr class="mv_checked">
						<td class="wd1"><span class="m_ico2_5"></span></td>
						<td class="td1"><@messages key="model.repay.name"  /></td>
					
						
						 <#assign repaymethod = 0 />
						<#list p.repayMethod as rm>  
								 <#if repaymethod % 2  = 0 >
								 	<td class="wd2">
								  	<input type="radio" name="repay_${detail}" value=${rm.id} /><span> ${rm.name}</span><br />
								 <#else> 
									<input type="radio" name="repay_${detail}" value=${rm.id} /><span> ${rm.name}</span>
									</td>
								 </#if>
								 <#assign repaymethod = repaymethod + 1 /> 
						</#list>
					</tr>
				</table>
				</div>
				</#list>
		</div>
		<div class="btn_list">
			<a href="#" class="m_btn1 m_bg1 mv_submit"><@messages key="loan.generate.loan.program"  /></a>
		</div>
	</div>
</div>
</form>
</div>
<#include "/footer.ftl" />
</body>
</form>
</html>

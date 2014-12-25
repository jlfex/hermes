<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css" />
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/others.css" />
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mInvestAndLoan.js" charset="utf-8"></script>

<script type="text/javascript">
<!--
jQuery(function($) {
			 $('#loanData').fadeOut('fast').load('${app}/invest/search', $('#investForm').serialize(), function(html) {
			 	$(this).fadeIn('fast');
			});
});
//-->
</script>
</head>

<body>
<#include "/header.ftl" />
<div id="content" class="content">
<form id="investForm" method="post" action="#">
<!-- middle start-->
<div class="control">
<div class="middle_content">
    <div class="detail">
		<div class="flow">
			<div class="title"><@messages key="invest.flow.diagram" /></div>
			<div class="flowimg">
				<img src="${app.theme}/public/other/images/icon1/invest_login.jpg">
				<img src="${app.theme}/public/other/images/icon1/invest_account.jpg">
				<img src="${app.theme}/public/other/images/icon1/invest_choose.jpg">
				<img src="${app.theme}/public/other/images/icon1/invest_bid.jpg">
				<img src="${app.theme}/public/other/images/icon1/invest_money.jpg">
			</div>
		</div>
	<div class="allcategory">
			<div class="select_result">
				<h4>所有分类</h4>
				<ul>
					<li><span></span><a href="#"><img src="${app.theme}/public/other/images/icon2/closeicon.jpg"/></a></li>
					<li><span></span><a href="#"><img src="${app.theme}/public/other/images/icon2/closeicon.jpg"/></a></li>
					<li><span></span><a href="#"><img src="${app.theme}/public/other/images/icon2/closeicon.jpg"/></a></li>
					<li><span></span><a href="#"><img src="${app.theme}/public/other/images/icon2/closeicon.jpg"/></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			<input id="purpose" name="purpose" type="hidden" ></input>
			<input id="raterange" name="raterange" type="hidden" ></input>
			<input id="periodrange"  name="periodrange" type="hidden" ></input>
			<input id="repayname" name="repayname" type="hidden" ></input>
			<input id="orderByField" name="orderByField" type="hidden" ></input>
			<input id="orderByDirection" name="orderByDirection" type="hidden" ></input>
			<div class="select_list">
				<ul>
					<li class="select_title"><@messages key="model.loan.purpose" /> :</li>
					<li><a href="#" class="a_lk_after">不限</a></li>
					<#list loanpurposes as lp>  
						<li><a href="#">${lp.name}</a></li>
					</#list>
				</ul>
				<ul>
					<li class="select_title"><@messages key="model.loan.rate" /> :</li>
					<li><a href="#" class="a_lk_after">不限</a></li>
					<li><a href="#">10%<@messages key="invest.loan.rate.under" /></a></li>
					<li><a href="#">10%-12%</a></li>
					<li><a href="#">12%-15%</a></li>
					<li><a href="#">15%<@messages key="invest.loan.rate.above" /></a></li>
				</ul>
				<ul>
					<li class="select_title"><@messages key="model.loan.period" /> :</li>
					<li><a href="#" class="a_lk_after">不限</a></li>
					<li><a href="#">3<@messages key="common.unit.month" /><@messages key="invest.loan.period.within" /></a></li>
					<li><a href="#">3-6<@messages key="common.unit.month" /></a></li>
					<li><a href="#">6-12<@messages key="common.unit.month" /></a></li>
					<li><a href="#">12<@messages key="common.unit.month" /><@messages key="invest.loan.rate.above" /></a></li>
				</ul>
				<ul>
					<li class="select_title"><@messages key="model.repay.name"  /> :</li>
					<li><a href="#" class="a_lk_after">不限</a></li>
					<#list repays as r>  
						<li><a href="#">${r.name}</a></li>
					</#list>
				</ul>
			</div>		
		</div>
		
		<div id="loanData"></div>
		<input id="page" name="page" type="hidden">
	
    </div>
</div>

</div>
</form>

</div>
<#include "/footer.ftl" />

</body>
</html>
	
	
	
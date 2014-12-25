<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>

</head>
<body>

<#include "../header.ftl" />

<div id="content" class="content">
	<div class="u-container">
		<div class="flow">
			<h3>流程简图</h3>
			<div class="imgs">
				<img alt="注册登录" src="${app.theme}/public/other/images/icon1/invest_login.jpg">
				<img alt="账户充值" src="${app.theme}/public/other/images/icon1/invest_account.jpg">
				<img alt="选择借款标" src="${app.theme}/public/other/images/icon1/invest_choose.jpg">
				<img alt="投标" src="${app.theme}/public/other/images/icon1/invest_bid.jpg">
				<img alt="收款" src="${app.theme}/public/other/images/icon1/invest_money.jpg">
			</div>
		</div>
		
		<div class="rich-form">
			<h3>所有分类</h3>
			<form id="searchForm" method="post" action="#" class="form-horizontal">
				<div class="form-group">
					<label class="col-xs-1 u-col control-label"><@messages key="model.loan.purpose" /></label>
					<div class="col-xs-11 u-col">
						<p class="form-control-static">
							<a href="#" class="label label-primary" data-id="purpose-不限">不限</a>
							<#list purposes as p>
							<a href="#" class="label label-default" data-id="purpose-${p.id}">${p.name}</a>
							</#list>
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-1 u-col control-label"><@messages key="model.loan.rate" /></label>
					<div class="col-xs-11 u-col">
						<p class="form-control-static">
							<a href="#" class="label label-primary" data-id="rate-不限">不限</a>
							<a href="#" class="label label-default" data-id="rate-10%<@messages key="invest.loan.rate.under" />">10%以下</a>
							<a href="#" class="label label-default" data-id="rate-10%-12%">10%-12%</a>
							<a href="#" class="label label-default" data-id="rate-12%-15%">12%-15%</a>
							<a href="#" class="label label-default" data-id="rate-15%<@messages key="invest.loan.rate.above" />">15%以上</a>
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-1 u-col control-label"><@messages key="model.loan.period" /></label>
					<div class="col-xs-11 u-col">
						<p class="form-control-static">
							<a href="#" class="label label-primary" data-id="period-不限">不限</a>
							<a href="#" class="label label-default" data-id="period-3<@messages key="common.unit.month" /><@messages key="invest.loan.period.within" />">3<@messages key="common.unit.month" /><@messages key="invest.loan.period.within" /></a>
							<a href="#" class="label label-default" data-id="period-3-6<@messages key="common.unit.month" />">3-6<@messages key="common.unit.month" /></a>
							<a href="#" class="label label-default" data-id="period-6-12<@messages key="common.unit.month" />">6-12<@messages key="common.unit.month" /></a>
							<a href="#" class="label label-default" data-id="period-12<@messages key="common.unit.month" /><@messages key="invest.loan.rate.above" />">12<@messages key="common.unit.month" /><@messages key="invest.loan.rate.above" /></a>
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-1 u-col control-label"><@messages key="model.repay.name"  /></label>
					<div class="col-xs-11 u-col">
						<p class="form-control-static">
							<a href="#" class="label label-primary" data-id="repay-不限">不限</a>
							<#list repays as r>
							<a href="#" class="label label-default" data-id="repay-${r.id}">${r.name}</a>
							</#list>
						</p>
					</div>
				</div>
				<input id="page" name="page" type="hidden">
				<input id="purpose" name="purpose" type="hidden">
				<input id="raterange" name="raterange" type="hidden">
				<input id="periodrange"  name="periodrange" type="hidden">
				<input id="repay" name="repay" type="hidden">
				<input id="orderByField" name="orderByField" type="hidden">
				<input id="orderByDirection" name="orderByDirection" type="hidden">
			</form>
		</div>
		
		<div id="data"></div>
	</div>
</div>

<#include "../footer.ftl" />

<script type="text/javascript" charset="utf-8">
<!--
// 绑定查询事件
$('#searchForm .form-control-static .label').on('click', function() {
	// 初始化
	var _data = $(this).singleClass('label-primary', { replace: 'label-default' }).data();
	
	// 判断并进行处理
	if (_data.id != undefined) {
		// 初始化
		var _pos = _data.id.indexOf('-'), 
			_key = _data.id.substring(0, _pos); 
			_val = _data.id.substring(_pos + 1);
			
		// 根据类型进行处理
		switch (_key) {
		case 'purpose':
			$('#purpose').val(_val);
			break; 
		case 'rate':
			$('#raterange').val(_val);
			break;
		case 'period': 
			$('#periodrange').val(_val);
			break; 
		case 'repay': 
			$('#repay').val(_val);
			break; 
		default:
			break;
		}
	}
	
	// 查询数据
	$('#page').val(0);
	$('#searchForm').trigger('submit');
});

// 绑定表单提交事件
$('#searchForm').on('submit', function() {
	$('#data').fadeOut('fast').load('${app}/invest/indexsearch', $('#searchForm').serialize(), function(html) {
		$(this).fadeIn('fast');
	});
	return false;
}).trigger('submit');
//-->
</script>

</body>
</html>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/other/stylesheets/main.css">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/approve.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<style type="text/css">
  table th{text-align:center;}
</style>
</head>
<body>

<#include "../header.ftl" />

<div id="content" class="content" style="margin-top:133px;">
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
		<div class="loan_list">
			<div class="loan_detail">
				<div id="tab3" class="speciallist">
					<ul class="all_information m_tab_t">
						<li class="active">普通标 <span></span></li>
						<li class="lastnone">债权表 <span></span></li>
					</ul>
					<div class="m_tab_c ad_border" style="width:100%;margin-top:15px;">
						<div style="display: block;">
							<div class="data">
								<table cellpadding="0" cellspacing="0" border="0" style="border-left:1px solid #e2e2e2;border-right:1px solid #e2e2e2;">
									<thead>
										<tr style="background:#e5f9ff;">
											<th class="th_01">借款用途</th>
											<th class="th_02">金额（元）</th>
											<th class="th_03">年利率</th>
											<th class="th_04">期限</th>
											<th class="th_05">进度</th>
											<th class="th_06">剩余金额（元）</th>
											<th class="th_07">操作</th>
										</tr>
									</thead>
									<#list loans.content as l>  
									<tr>
										<td class="td_01"><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
										<td class="td_02">${l.amount} <@messages key="common.unit.cny" /></td>
										<td class="td_03">${l.rate}</td>
										<td class="td_04">${l.period} <@messages key="common.unit.month" /></td>
										<td class="td_05">
				                            <div class="layer_box">
				                                <div class="layer1">${ ((l.amount?replace(',','')?number -(l.remain?replace(',','')?number))/l.amount?replace(',','')?number)?string.percent}</div>
				                                <div class="layer2" style="height: ${ ((l.amount?replace(',','')?number -(l.remain?replace(',','')?number))/l.amount?replace(',','')?number)?string.percent}"></div>
				                            </div>
										</td>
										<td class="td_06">${l.remain} <@messages key="common.unit.cny" /></td>
										<td class="td_07">
												<#if l.status=='10'>
												    <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#"><@messages key="invest.loan.bid" /></a> 
												<#elseif l.status=='11'>
												    <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#"><@messages key="invest.loan.full.scale" /></a> 
												<#elseif l.status=='12'>
												     <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#"><@messages key="invest.loan.full.repayment" /></a> 
												<#elseif l.status=='99'>
												     <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#">完成</a> 
												<#else>
												</#if>
										</td>
									</tr>
									</#list>
									
								</table>
							</div>
							
						</div>
						<div style="display: none;">
							<div class="data">
								 <table cellpadding="0" cellspacing="0" border="0" style="border-left:1px solid #e2e2e2;border-right:1px solid #e2e2e2;">
									<thead>
										<tr style="background:#fff9f9;">
											<th class="th_01">债权名称</th>
											<th class="th_02">剩余本金（元）</th>
											<th class="th_03">年利率</th>
											<th class="th_04">期限</th>
											<th class="th_05">进度</th>
											<th class="th_06">转让价格（元）</th>
											<th class="th_07">操作</th>
										</tr>
									</thead>
									<#list assignLoans.content as l>  
									<tr>
										<td class="td_01"><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
										<td class="td_02">${l.amount} <@messages key="common.unit.cny" /></td>
										<td class="td_03">${l.rate}</td>
										<td class="td_04">${l.period} <@messages key="common.unit.month" /></td>
										<td class="td_05">
											 <div class="layer_box">
				                                <div class="layer1">${ (l.remain?replace(',','')?number/l.amount?replace(',','')?number)?string.percent}</div>
				                                <div class="layer2" style="height: ${(l.remain?replace(',','')?number/l.amount?replace(',','')?number)?string.percent}"></div>
				                            </div>
										</td>
										<td class="td_06">${l.remain} <@messages key="common.unit.cny" /></td>
										<td class="td_07">
												<#if l.status=='10'>
												    <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#"><@messages key="invest.loan.bid" /></a> 
												<#elseif l.status=='11'>
												    <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#"><@messages key="invest.loan.full.scale" /></a> 
												<#elseif l.status=='12'>
												     <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#"><@messages key="invest.loan.full.repayment" /></a> 
												<#elseif l.status=='99'>
												     <a class="i_btn1 i_bg1"  data-id="${l.id}" href="#">完成</a> 
												<#else>
												</#if>
										</td>
									</tr>
									</#list>
								</table>
							</div>
						</div>
					</div>
				</div>	
			</div>
		</div>	


	</div>
</div>

<#include "../footer.ftl" />

<script type="text/javascript" charset="utf-8">

$('.loan_detail .i_btn1.i_bg1').click(function() { window.location.href = '${app}/invest/info?loanid=' + $(this).data().id; });

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
	
});

$('.pagination').each(function() {
		var _elem = $(this).empty(),
			_opts = $.extend({}, _elem.data()),
			_number = _opts.number,
			_pages = _opts.totalPages - 1,
			_begin = ((_number - 3) < 0) ? 0 : (_number - 3),
			_end = ((_number + 3) > _pages) ? _pages : (_number + 3),
			_tag = $('<li />').append($('<a />').attr('href', '#'));
			
		if (_begin > 0) {
			_tag.clone().appendTo(_elem).find('a').attr('data-page', 0).text(1);
			_tag.clone().appendTo(_elem).addClass('disabled').find('a').text('...');
		}
		
		for (var _idx = _begin; _idx <= _end; _idx++) {
			if (_idx === _number) {
				_tag.clone().appendTo(_elem).addClass('active').find('a').text(_idx + 1);
			} else {
				_tag.clone().appendTo(_elem).find('a').attr('data-page', _idx).text(_idx + 1);
			}
		}
		
		if (_end < _pages) {
			_tag.clone().appendTo(_elem).addClass('disabled').find('a').text('...');
			_tag.clone().appendTo(_elem).find('a').attr('data-page', _pages).text(_pages + 1);
		}
		
		_elem.find('a').on('click', function() {
			$('#page').val($(this).data().page);
			$('#searchForm').trigger('submit');
		});
	});
});

</script>

</body>
</html>

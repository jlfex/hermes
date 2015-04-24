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
<link rel="shortcut icon" href="${app.theme}/public/images/smallIcon.png">
<link rel="apple-touch-icon-precomposed" href="${app.theme}/public/images/bigIcon.png">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/approve.js"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mPlugin.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.theme}/public/other/javascripts/mCommon.js" charset="utf-8"></script>
<style type="text/css">
  table th{text-align:center;}
  .loan_list{height:auto;}
  ul.pagination{
  	border:none;
  }
  ul.pagination li{
  	float:none;
  }
</style>
</head>
<body>
<#include "../header.ftl" />
<div id="content" class="content" style="margin-top:133px; min-height:760px;">
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
						<li class="lastnone">债权标 <span></span></li>
					</ul>
					<div class="m_tab_c ad_border" style="width:100%;margin-top:15px;">
						<div style="display: block;">
							<form id="normalLoanForm" method="post" action="#" class="form-horizontal">
							<input id="page" name="page" type="hidden" value="0">
							<div class="data" id="normalData">
								
							</div>
							</form>
							
						</div>
						<div style="display: none;">
							<form id="assignLoanForm" method="post" action="#" class="form-horizontal">
							<input id="page" name="page" type="hidden" value="0">
							<div class="assignData" id="assignData">
								 
							</div>
							</form>
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

</script>



<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$('#normalLoanForm').on('submit', function() {
		$.ajax('${app}/invest/indexnormalloanfgt', {
			data: $(this).serialize(),
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				$('#normalData').fadeOut('fast', function() {
					$(this).html(data).fadeIn('fast');
				});
			}
		});
		return false;
	});
	
	$('#assignLoanForm').on('submit', function() {
		$.ajax('${app}/invest/indexassignloanfgt', {
			data: $(this).serialize(),
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				$('#assignData').fadeOut('fast', function() {
					$(this).html(data).fadeIn('fast');
				});
			}
		});
		return false;
	});
	
	
	$('#normalLoanForm,#assignLoanForm').submit();
});
//-->
</script>
</body>
</html>

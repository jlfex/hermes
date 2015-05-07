<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><@config key="app.title" /></title>
<link rel="stylesheet" type="text/css" href="${app.css}/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${app.css}/bootstrap-theme.css">
<link rel="stylesheet" type="text/css" href="${app.css}/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${app.theme}/public/stylesheets/style.css">
<link rel="shortcut icon" href="${app.theme}/public/images/smallIcon.png">
<link rel="apple-touch-icon-precomposed" href="${app.theme}/public/images/bigIcon.png">
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/jquery-ui.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.js}/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8" src="${app.theme}/public/javascripts/hermes.js"></script>
</head>
<body class="index">
<div class="_container">

<#include "../header.ftl" />
<div class="_content">
<div id="content" class="content">
	<div class="u-container">
		<div id="products" class="products">
			<div class="clearfix">
				<div class="active pull-left"></div>
				<div class="arr arr-prev pull-left"><a href="#"><i class="fa fa-angle-double-left"></i></a></div>
				<div class="items pull-left">
					<ul>
						<#list products as p>
						<li class="pull-left" data-id="${p.id}">
							<h3>${p.name}</h3>
							<p>${p.description}</p>
						</li>
						</#list>
					</ul>
				</div>
				<div class="arr arr-next pull-left"><a href="#"><i class="fa fa-angle-double-right"></i></a></div>
			</div>
			<div class="loan-form">
				<form id="loanForm" method="post" action="#" class="form-horizontal">
					<div class="form-group">
						<label for="amount" class="col-xs-1 u-col control-label control-icon"><i class="fa fa-jpy"></i></label>
						<label for="amount" class="col-xs-1 u-col control-label">借款金额</label>
						<label for="amount" class="col-xs-1 u-col control-label">5,000.<span class="weak">00</span></label>
						<div class="col-xs-6 u-col">
							<div class="form-control-static form-control-slider">
								<div id="amount-slider"></div>
							</div>
						</div>
						<label for="amount" class="col-xs-2 u-col control-label control-icon">20,000.<span class="weak">00</span></label>
						<div class="col-xs-1 u-col"><input id="amount" name="amount" type="text" class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="period" class="col-xs-1 u-col control-label control-icon"><i class="fa fa-calendar"></i></label>
						<label for="period" class="col-xs-1 u-col control-label">期限</label>
						<label for="period" class="col-xs-1 u-col control-label">6<span class="weak">个月</span></label>
						<div class="col-xs-6 u-col">
							<div class="form-control-static form-control-slider">
								<div id="period-slider"></div>
							</div>
						</div>
						<label for="period" class="col-xs-2 u-col control-label control-icon">36<span class="weak">个月</span></label>
						<div class="col-xs-1 u-col"><input id="period" name="period" type="text" readonly="readonly" class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="rate" class="col-xs-1 u-col control-label control-icon"><i class="fa fa-bar-chart-o"></i></label>
						<label for="rate" class="col-xs-1 u-col control-label">年利率</label>
						<label for="rate" class="col-xs-1 u-col control-label">12<span class="weak">%</span></label>
						<div class="col-xs-6 u-col">
							<div class="form-control-static form-control-slider">
								<div id="rate-slider"></div>
							</div>
						</div>
						<label for="rate" class="col-xs-2 u-col control-label control-icon">24<span class="weak">%</span></label>
						<div class="col-xs-1 u-col"><input id="rate" name="rate" type="text" readonly="readonly" class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="purpose" class="col-xs-1 u-col control-label control-icon"><i class="fa fa-tasks"></i></label>
						<label for="purpose" class="col-xs-1 u-col control-label">借款用途</label>
						<div class="col-xs-10 u-col">
							<p class="form-control-static">
								<#list purposes as p>
								<a href="#" class="label label-default" data-id="${p.id}">${p.name}</a>
								</#list>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label for="repay" class="col-xs-1 u-col control-label control-icon"><i class="fa fa-pencil-square-o"></i></label>
						<label for="repay" class="col-xs-1 u-col control-label">还款方式</label>
					</div>
					<div class="form-group">
						<div class="col-xs-offset-2 col-xs-2"><button class="btn btn-primary btn-block">生成借款方法</button></div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</div>
<#include "../footer.ftl" />
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 初始化
	var _prods = $('#products');
	
	// 绑定项目点击事件
	_prods.find('li').on('click', function() {
		// 仅当对象未选中时进行处理
		if (!$(this).hasClass('active')) {
			// 初始化
			var _this = $(this), 
				_this_offset = _this.offset(), 
				_active = _prods.find('div.active'),
				_active_offset = _active.offset();
			
			// 处理虚化样式
			_prods.find('li.active').animate({opacity: 1}, 1000).removeClass('active');
			_this.animate({opacity: 0.25}, 1000).addClass('active');
			
			// 飞入效果
			_this.clone().css({ position: 'absolute', top: _this_offset.top, left: _this_offset.left }).appendTo(_this.parent()).animate({
				opacity: 0,
				left: _active_offset.left
			}, 1000, function() {
				_active.html($(this).html());
				$(this).remove();
			});
		}
	}).filter(':first').trigger('click');
	
	// 金额滑动块
	$('#amount-slider').slider({
		range: 'min',
		min: 5000,
		max: 20000,
		step: 50,
		slide: function(event, ui) {
			$('#amount').val(ui.value);
		}
	});
	$('#amount').change(function() {
		$('#amount-slider').slider('value', $(this).val());
	});
	
	$('#period-slider').slider({
		range: 'min',
		min: 6,
		max: 36,
		step: 1,
		slide: function(event, ui) {
			$('#period').val(ui.value);
		}
	});
	
	$('#rate-slider').slider({
		range: 'min',
		min: 12,
		max: 24,
		step: 0.5,
		slide: function(event, ui) {
			$('#rate').val(ui.value);
		}
	});
});
//-->
</script>

</body>
</html>

<script type="text/javascript">
jQuery(function($) {
	// 绑定链接点击事件
	$('a').link().on('click', function() {
	});
});
</script>
<!-- loan info -->
<div class="block">
	<div class="body-xs">
		<div class="row u-row">
			<div class="col-xs-12 u-col">
				<div class="body">
					<p class="account">
						<@messages key="invest.success.bid" />：&nbsp;<span class="currency">${successCount}</span>&nbsp;<#t>
						&nbsp;&nbsp;<#t>
						<@messages key="invest.all.profit.sum" />：&nbsp;<span class="currency">${allProfitSum}</span><@messages key="common.unit.cny" />&nbsp;<#t>
						&nbsp;&nbsp;<#t>
						利息：&nbsp;<span class="currency">${interestSum}</span><@messages key="common.unit.cny" />&nbsp;<#t>
						&nbsp;&nbsp;<#t>
						罚息：&nbsp;<span class="currency">${overdueInterestSum}</span><@messages key="common.unit.cny" />&nbsp;<#t>
					</p>
					
				</div>
			</div>
		</div>
	</div>
</div>

<div class="block">
	<div class="body-sm">
		<h4>投标记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th><@messages key="model.loan.purpose" /></th>
					<th><@messages key="model.invest.amount" />(<@messages key="common.unit.cny" />)</th>
					<th><@messages key="model.loan.rate" /></th>
					<th><@messages key="model.loan.period" /></th>
					<th><@messages key="invest.should.receive.pi" />(<@messages key="common.unit.cny" />)</th>
					<th><@messages key="invest.received.pi" />(<@messages key="common.unit.cny" />)</th>
					<th><@messages key="invest.wait.receive.pi" />(<@messages key="common.unit.cny" />)</th>
					<th><@messages key="common.status" /></th>
				</tr>
			</thead>
			<tbody>
			  <#list invests.content as i>  
				<tr>
					<td><a href="#" class="icon loan" data-url="${app}/invest/myinvestinfo/${i.id}" data-target="main">${i.purpose!''}</a>
					</td>
					<td class="right">${i.amount}<@messages key="common.unit.cny" /></td>
					<td class="right">${i.rate}</td>
					<td class="right">${i.period}<@messages key="common.unit.month" /></td>
					<td class="right">${i.shouldReceivePI}</td>
					<td class="right">${i.receivedPI}</td>
					<td class="right">${i.waitReceivePI}</td>
					<td class="right">${i.investStatusName}</td>
				</tr>
				</#list>
			</tbody>
		</table>
		<ul class="pagination" data-number="${invests.number}" data-total-pages="${invests.totalPages}"></ul>				
	</div>
</div>
<script type="text/javascript">
jQuery(function($) {
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
			var $form = $(this).parents("form");
			$form.find("#page").val($(this).data().page);
			$form.trigger('submit');
		});
	});
});

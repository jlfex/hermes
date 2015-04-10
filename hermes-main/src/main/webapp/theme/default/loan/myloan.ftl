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
						发布借款笔数：&nbsp;<span class="currency">${loanCount}</span>&nbsp;<#t>
						&nbsp;&nbsp;<#t>
						成功借款笔数：&nbsp;<span class="currency">${loanSuccessCount}</span>&nbsp;<#t>
						&nbsp;&nbsp;<#t>
						共计借入：&nbsp;<span class="currency">${loanAmount}</span>&nbsp;<#t>
					</p>
					
				</div>
			</div>
		</div>
	</div>
</div>

<div class="block">
	<div class="body-sm">
		<h4>借款记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th><@messages key="model.loan.purpose" /></th>
					<th><@messages key="model.loan.amount" />(<@messages key="common.unit.cny" />)</th>
					<th><@messages key="model.loan.rate" /></th>
					<th><@messages key="model.loan.period" /></th>
					<th>偿还本息(<@messages key="common.unit.cny" />)</th>
					<th>已还本息(<@messages key="common.unit.cny" />)</th>
					<th>未还本息(<@messages key="common.unit.cny" />)</th>
					<th><@messages key="common.status" /></th>
				</tr>
			</thead>
			<tbody>
			  	<#list loans.content as l>  
				<tr>
					<td><a href="#" class="icon loan" data-url="${app}/loan/myloaninfo/${l.id}" data-target="main">${l.purpose}</a>
					</td>
					<td class="right">${l.amount}</td>
					<td class="right">${l.rate}</td>
					<td class="right">${l.period}<@messages key="common.unit.month" /></td>
					<td class="right">${l.repayPI}</td>
					<td class="right">${l.repayedPI}</td>
					<td class="right">${l.unRepayPI}</td>
					<td>${l.loanStatusName}</td>
				</tr>
				</#list>
			</tbody>
		</table>
		<ul class="pagination" data-number="${loans.number}" data-total-pages="${loans.totalPages}"></ul>		
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

	// 绑定链接点击事件
	$('a').link().on('click', function() {
	});
});

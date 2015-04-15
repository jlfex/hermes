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
					<td><a href="#" class="icon loan loaninfo" data-id="${l.id}">${l.purpose}</a>
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
<!--
jQuery(function($) {
		$(".loaninfo").on("click",function(){
		    var loanId = $(this).attr("data-id");
			window.location.href="${app}/account/index?type=loaninfo&loanId="+loanId;				
		});
	// 处理分页
	$('.pagination').each(function() {
		// 初始化
		var _elem = $(this).empty(),
			_opts = $.extend({}, _elem.data()),
			_number = _opts.number,
			_pages = _opts.totalPages - 1,
			_begin = ((_number - 3) < 0) ? 0 : (_number - 3),
			_end = ((_number + 3) > _pages) ? _pages : (_number + 3),
			_tag = $('<li />').append($('<a />').attr('href', '#'));
			
		// 当开始页码大于首页时补充首页页码
		if (_begin > 0) {
			_tag.clone().appendTo(_elem).find('a').attr('data-page', 0).text(1);
			_tag.clone().appendTo(_elem).addClass('disabled').find('a').text('...');
		}
		
		// 正常循环生成页码
		for (var _idx = _begin; _idx <= _end; _idx++) {
			if (_idx === _number) {
				_tag.clone().appendTo(_elem).addClass('active').find('a').text(_idx + 1);
			} else {
				_tag.clone().appendTo(_elem).find('a').attr('data-page', _idx).text(_idx + 1);
			}
		}
		
		// 当结束页码小于总页数时补充尾页页码
		if (_end < _pages) {
			_tag.clone().appendTo(_elem).addClass('disabled').find('a').text('...');
			_tag.clone().appendTo(_elem).find('a').attr('data-page', _pages).text(_pages + 1);
		}
		
		// 绑定页码点击事件
		_elem.find('a').on('click', function() {
			$('#page').val($(this).data().page);
			$('#searchForm').trigger('submit');
		});
	});
});
//-->
</script>

<div id="loan" class="loans">
	<table class="table table-hover">
		<thead>
			<tr>
				<th width="5%"></th>
				<th width="15%"><@messages key="model.loan.purpose" /></th>
				<th width="10%" class="right"><@messages key="model.loan.rate" /> <img id="rate" src="${app.theme}/public/other/images/icon2/invest_arrowd_down.png"></th>
				<th width="15%" class="right"><@messages key="model.loan.amount" /></th>
				<th width="10%" class="right"><@messages key="model.loan.period" /> <img id="period" src="${app.theme}/public/other/images/icon2/invest_arrowd_down.png"></th>
				<th width="15%" class="right"><@messages key="model.loan.remain" /></th>
				<th width="15%" class="right"><@messages key="model.repay.name" /></th>
				<th width="15%"></th>
			</tr>
		</thead>
		<tbody>
			<#list loans.content as l>  
			<tr data-progress="${l.progress}">
				<td class="right"><a href="${app}/invest/info?loanid=${l.id}"><img alt="" src="${l.avatar!''}" class="avatar"></a></td>
				<td><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
				<td class="right">${l.rate}</td>
				<td class="right">${l.amount} <@messages key="common.unit.cny" /></td>
				<td class="right">${l.period} <@messages key="common.unit.month" /></td>
				<td class="right">${l.remain} <@messages key="common.unit.cny" /></td>
				<td class="right">${l.repayName}</td>
				<td class="right">
					<#if l.status=='10'>
					<button type="button" class="btn btn-primary" data-id="${l.id}"><@messages key="invest.loan.bid" /></button>
					<#elseif l.status=='11'>
					<button type="button" class="btn btn-primary" data-id="${l.id}"><@messages key="invest.loan.full.scale" /></button>
					<#elseif l.status=='12'>
					<button type="button" class="btn btn-primary" data-id="${l.id}"><@messages key="invest.loan.full.repayment" /></button>
					<#elseif l.status=='99'>
					<button type="button" class="btn btn-primary" data-id="${l.id}">完成</button>
					<#else>
					</#if>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>

<ul class="pagination" data-number="${loans.number}" data-total-pages="${loans.totalPages}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('#loan').loan();
	$('#loan .btn-primary').on('click', function() { window.location.href = '${app}/invest/info?loanid=' + $(this).data().id; });
	// 表格升降排序图标切换
    $('.table.table-hover').find('th').click(function(){
		var img = $(this).find('img');
		if(img.attr('src') && img.attr('src').lastIndexOf('down') > 0) {
			var idValue=img.attr('id');
			if(idValue=='rate')
			{
	        	$('#orderByField').val('rate');
				$('#orderByDirection').val('asc');
			}
			else if(idValue=='period')
			{
	        	$('#orderByField').val('period');
				$('#orderByDirection').val('asc');
			}
		   $('#data').fadeOut('fast').load('${app}/invest/indexsearch', $('#searchForm').serialize(), function(html) {
			 	$(this).fadeIn('fast');
				var rateimg =    $('#rate');
				var periodimg =    $('#period');
				if(idValue=='rate')
				{
					rateimg.attr('src','${app.theme}/public/other/images/icon2/invest_arrowd_up.png');
				}
				else if(idValue=='period')
				{
					periodimg.attr('src','${app.theme}/public/other/images/icon2/invest_arrowd_up.png');
				}
			});
		
		}else {
			var idValue=img.attr('id');
			if(idValue=='rate')
			{
	        	$('#orderByField').val('rate');
				$('#orderByDirection').val('desc');
			}
			else if(idValue=='period')
			{
	        	$('#orderByField').val('period');
				$('#orderByDirection').val('desc');
			}
			$('#searchForm').trigger('submit');
			
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
//-->
</script>

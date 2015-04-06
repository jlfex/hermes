<table cellpadding="0" cellspacing="0" border="0" style="border-left:1px solid #e2e2e2;border-right:1px solid #e2e2e2;">
	<thead>
		<tr style="background:#fff9f9;">
			<th class="th_04">出借编号</th>		
			<th class="th_04">债权名称</th>
			<th class="th_04">年利率</th>
			<th class="th_04">期限</th>
			<th class="th_04">进度</th>
			<th class="th_04">剩余金额（元）</th>
			<th class="th_04">操作</th>
		</tr>
	</thead>
	<#list assignLoan.content as l>  
	<tr>
	    <td class="td_04">${l.applicationNo!'-'}</td>
		<td class="th_04"><a href="${app}/invest/info?loanid=${l.id}">${l.purpose!'-'}</a></td>
		<td class="th_04">${l.rate}</td>
		<td class="th_04">${l.period}天</td>
		<td class="th_04">
			<div class="layer_box">
                <div class="layer1"><@percent total="${l.amount!'0'}" remain="${l.remain!'0'}"/></div>
                <div class="layer2" style="height:<@percent total="${l.amount!'0'}" remain="${l.remain!'0'}"/>"></div>
            </div>
		</td>
		<td class="th_04">${l.remain} <@messages key="common.unit.cny" /></td>
		<td class="th_04">
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

<ul class="pagination" data-number="${assignLoan.number}" data-total-pages="${assignLoan.totalPages}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
$('.loan_detail .i_btn1.i_bg1').click(function() { window.location.href = '${app}/invest/info?loanid=' + $(this).data().id; });

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
//-->
</script>
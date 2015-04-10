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
	
		<table class="table table-hover" style="font-size:12px;">
			<thead>
				<tr>
					<th class="center" style="width:65px;">债权名称</th>
					<th class="center" style="width:85px;">投标金额(<@messages key="common.unit.cny" />)</th>
					<th class="center" style="width:60px;">年利率</th>
					<th class="center" style="width:60px;">期限(天)</th>
					<th class="center" style="width:85px;">应收本息(<@messages key="common.unit.cny" />)</th>
					<th class="center" style="width:70px;">已收本息(<@messages key="common.unit.cny" />)</th>
					<th class="center" style="width:70px;">待收本息(<@messages key="common.unit.cny" />)</th>
					<th class="center" style="width:70px;">状态</th>
					<th class="center" style="width:70px;">协议</th>
				</tr>
			</thead>
			<tbody>
			  <#list invests.content as i>  
				<tr>
					<td class="center" style="width:65px;"><a href="#" class="icon loan" data-url="${app}/invest/myinvestinfo/${i.id}" data-target="main">${i.purpose}</a>
					</td>
					<td class="center">${i.amount}<@messages key="common.unit.cny" /></td>
					<td class="center" style="width:60px;">${i.rate}</td>
					<td class="center" style="width:60px;">${i.period}</td>
					<td class="center">${i.shouldReceivePI}</td>
					<td class="center">${i.receivedPI}</td>
					<td class="center">${i.waitReceivePI}</td>
					<td class="center" style="width:65px;">${i.investStatusName}</td>
					<#if i.loanPdfId?? && i.guaranteePdfId??>									
				        <td class="center" style="width:120px;">
				            <a href="${app}/invest/queryFile/${i.loanPdfId}" class="icon loan" target="_Blank">《债权转让协议》</br></a>
                            <a href="${app}/invest/queryFile/${i.guaranteePdfId}" class="icon loan" target="_Blank">《担保函》</a>
				        </td>
				    <#else>		
				    	<td class="center" style="width:120px;"></td>
				    </#if>	
				    <input id="page" name="page" type="hidden" value="0"/>				    									        										
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

	// 绑定链接点击事件
	$('a').link().on('click', function() {
	});
});


<!-- invest info -->
<div class="block">
	<div class="body-xs">
		<div class="row u-row">
			<div class="col-xs-12 u-col">
				<div class="body">
					<p class="account">
						<div class="row hm-row">
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.purpose" />：&nbsp;${purpose}&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.product.name" />：&nbsp;${product.name}&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.amount" />：&nbsp;<span class="currency">${loan.amount}</span>元&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.basic.account" />：&nbsp;${user.account}&nbsp;<#t>
							</div>
						</div>
					</p>
					<p class="account">
						<div class="row hm-row">
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.rate" />：&nbsp;${loan.rate*100}%&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.repay" />：&nbsp;${repay.name}&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
								<@messages key="model.loan.period" />：&nbsp;
								${loan.period}<#if loan.loanKind=='00'>个月 <#else>天</#if>&nbsp;<#t>
							</div>
							<div class="col-xs-3 hm-col form-group">
							       编号：&nbsp;${loan.loanNo}&nbsp;<#t>
							</div>
						</div>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="block">
	<div class="body-sm">
		<h4>回款记录</h4>
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>当期/总期</th>
					<th>应收日期</th>
					<th>待收本金(<@messages key="common.unit.cny" />)</th>
					<th>待收利息(<@messages key="common.unit.cny" />)</th>
					<th>待收总额(<@messages key="common.unit.cny" />)</th>
					<th>罚息(<@messages key="common.unit.cny" />)</th>
					<th>逾期天数</th>
					<th>回款状态</th>
				</tr>
			</thead>
			<tbody>
			  <#list investprofitinfos.content as i>  
	                <tr>
	                    <td class="th_00">${i.sequence}/${i.period}</td>
                        <td>${i.planDatetime?string('yyyy-MM-dd')}</td>
                    	 <td class="right">${i.principal}</td>
                    	 <td class="right">${i.interest}</td>
                    	 <td class="right">${i.amount}</td>
                    	 <td class="right">${i.overdueInterest}</td>
                    	 <td class="right">${i.overdueDays}</td>
                    	 <td>${i.statusName}</td>
	                </tr>
                </#list>
			</tbody>
		</table>
		<ul class="pagination" data-number="${investprofitinfos.number}" data-total-pages="${investprofitinfos.totalPages}"></ul>				
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

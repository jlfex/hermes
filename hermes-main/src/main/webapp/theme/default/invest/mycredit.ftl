<form id="searchForm" method="post" action="#" class="form-horizontal">
<input id="page" name="page" type="hidden" value="0">
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
</form>
<div id="data"></div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	// 绑定表单提交事件
	$('#searchForm').on('submit', function() {
		$.ajax('${app}/invest/myCredit/table', {
			data: $(this).serialize(),
			type: 'post',
			dataType: 'html',
			timeout: 5000,
			success: function(data, textStatus, xhr) {
				$('#data').fadeOut('fast', function() {
					$(this).html(data).fadeIn('fast');
				});
			}
		});
		return false;
	});	
	
	$('#searchForm').submit();
});
//-->
</script>

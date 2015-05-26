<form id="searchForm" method="post" action="#" class="form-horizontal">
<input id="page" name="page" type="hidden" value="0">
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
</form>
<div id="data"></div>
<script type="text/javascript">
<!--
jQuery(function($) {
	// 绑定表单提交事件
	$('#searchForm').on('submit', function() {
		$.ajax('${app}/loan/myloan/table', {
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

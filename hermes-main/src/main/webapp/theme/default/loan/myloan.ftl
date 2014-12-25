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
			  	<#list loans as l>  
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
	</div>
</div>
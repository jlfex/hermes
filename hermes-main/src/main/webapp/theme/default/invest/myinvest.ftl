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
			  <#list invests as i>  
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
	</div>
</div>
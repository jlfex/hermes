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
	
		<table class="table table-hover" style="font-size:12px;">
			<thead>
				<tr>
					<th style="width:65px;">债权名称</th>
					<th class="center" style="width:85px;">投标金额(<@messages key="common.unit.cny" />)</th>
					<th style="width:60px;">年利率</th>
					<th style="width:60px;">期限(天)</th>
					<th style="width:85px;">应收本息(<@messages key="common.unit.cny" />)</th>
					<th style="width:70px;">已收本息(<@messages key="common.unit.cny" />)</th>
					<th style="width:70px;">待收本息(<@messages key="common.unit.cny" />)</th>
					<th style="width:70px;">状态</th>
					<th style="width:70px;">协议</th>
				</tr>
			</thead>
			<tbody>
			  <#list invests as i>  
				<tr>
					<td style="width:65px;"><a href="#" class="icon loan" data-url="${app}/invest/myinvestinfo/${i.id}" data-target="main">${i.purpose}</a>
					</td>
					<td class="right">${i.amount}<@messages key="common.unit.cny" /></td>
					<td class="right" style="width:60px;">${i.rate}</td>
					<td class="right" style="width:60px;">${i.period}</td>
					<td class="right">${i.shouldReceivePI}</td>
					<td class="right">${i.receivedPI}</td>
					<td class="right">${i.waitReceivePI}</td>
					<td class="center" style="width:65px;">${i.investStatusName}</td>
					<#list jlfexOrders as j> 
					<td class="center" style="width:120px;">${(j.loanPdfId)!'-'}</br>${(j.guaranteePdfId)!'-'}</td>
					</#list>
				</tr>
				</#list>
			</tbody>
		</table>
	</div>
</div>
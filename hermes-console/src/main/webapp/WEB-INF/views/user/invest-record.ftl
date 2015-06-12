<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="name"><@messages key="invest.id" /></th>
			<th data-column="datetime"><@messages key="invest.datetime" /></th>
			<th data-toggle="amount" class="align-right"><@messages key="invest.amount" /></th>
			<th data-toggle="rate"><@messages key="invest.rate" /></th>
			<th data-toggle="period"><@messages key="invest.period" /></th>
			<th data-toggle="profit" class="align-right"><@messages key="invest.profit" /></th>
			<th data-toggle="status"><@messages key="invest.status" /></th>
		</tr>
	</thead>
	<tbody>
		<#if invests.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list invests.content as invest>
		<tr>
			<td>${invest.applicationNo}</td>
			<td>${invest.datetime}</td>
			<td class="align-right">${invest.amount}</td>
			<td>${invest.rate}</td>
			<td>${invest.period}</td>
			<td class="align-right">${invest.expectProfit}</td>
			<td>${invest.status!''}</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>



<script type="text/javascript">
<!--
jQuery(function($) {
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
		}
	});
});
//-->
</script>
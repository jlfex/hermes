<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="name"><@messages key="model.loan.loanNo" /></th>
			<th data-column="datetime"><@messages key="model.loan.datetime" /></th>
			<th data-toggle="amount" class="align-right"><@messages key="model.loan.amount" /></th>
			<th data-toggle="period"><@messages key="model.loan.period" /></th>
			<th data-toggle="rate"><@messages key="model.loan.rate" /></th>
			<th data-toggle="repamount" class="align-right"><@messages key="model.loan.repay.amount" /></th>
			<th data-toggle="repay"><@messages key="model.loan.repay" /></th>
			<th data-toggle="status"><@messages key="model.loan.status" /></th>
		</tr>
	</thead>
	<tbody>
		<#if loans.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list loans.content as loan>
		<tr>
			<td>${loan.applicationNo}</td>
			<td>${loan.datetime}</td>
			<td class="align-right">${loan.amount}</td>
			<td>${loan.period}</td>
			<td>${loan.rate}</td>
			<td class="align-right">${loan.repayAmount}</td>
			<td>${loan.repay}</td>
			<td>${loan.status}</td>
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
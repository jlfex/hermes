<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="deposit"><@messages key="model.bank.account.deposit" /></th>
			<th data-column="city"><@messages key="model.bank.account.city" /></th>
			<th data-toggle="account"><@messages key="model.bank.account.account" /></th>
		</tr>
	</thead>
	<tbody>
		<#if accounts.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list accounts.content as account>
		<tr>
			<td>${account.deposit}</td>
			<td>${account.city.name}</td>
			<td>${account.account}</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>

<ul class="pagination" data-number="${accounts.number}" data-total-pages="${accounts.totalPages}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchBtn').trigger('click');
		}
	});
});
//-->
</script>
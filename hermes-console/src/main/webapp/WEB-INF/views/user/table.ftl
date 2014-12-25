<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th></th>
			<th data-column="account"><@messages key="user.nickname" /></th>
			<th data-column="cellphone"><@messages key="user.cellphone" /></th>
			<th data-toggle="realName"><@messages key="user.realname" /></th>
			<th data-toggle="total" class="align-right"><@messages key="user.total.balance" /></th>
			<th data-toggle="freeze" class="align-right"><@messages key="user.freeze.balance" /></th>
			<th data-toggle="free" class="align-right"><@messages key="user.free.balance" /></th>
			<th data-toggle="operate" class="align-center"><@messages key="common.op" /></th>
		</tr>
	</thead>
	<tbody>
		<#if users.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list users.content as user>
		<tr>
			<td><input name="id" type="hidden" value="${user.id}"></td>
			<td>${user.account!''}</td>
			<td>${user.cellphone!''}</td>
			<td>${user.realName!''}</td>
			<td class="align-right">${user.total!''}</td>
			<td class="align-right">${user.freeze!''}</td>
			<td class="align-right">${user.free!''}</td>
			<td class="align-center"><a href="#"  class="user-table" data-url="${app}${_url}${user.id}" data-target="main"><@messages key="common.op.deal" /></a></td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>

<ul class="pagination" data-number="${users.number}" data-total-pages="${users.totalPages}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
	$(".user-table").link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
//-->
</script>
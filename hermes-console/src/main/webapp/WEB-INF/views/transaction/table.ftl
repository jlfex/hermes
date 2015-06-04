<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
			<th width="20%">账户</th>
			<th width="10%" >金额</th>
			<th width="10%" >充值前金额</th>
			<th width="20%">充值后金额</th>
			<th width="20%">充值时间</th>
			<th width="20%">备注</th>
		</tr>
	</thead>
	<tbody>
		<#if rechargeList.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list rechargeList.content as wd>
		<tr>
			<td>${wd.sourceUserAccount.user.email!''}</td>
			<td >${wd.amount}</td>
			<td >${wd.sourceBeforeBalance}</td>
			<td>${wd.sourceAfterBalance}</td>
			<td>${wd.datetime}</td>
			<td>${wd.remark!""}</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>

<ul class="pagination" data-number="${rechargeList.number}" data-total-pages="${rechargeList.totalPages}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
//-->
</script>

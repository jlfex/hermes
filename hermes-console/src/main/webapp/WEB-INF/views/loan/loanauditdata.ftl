<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
			<th width="30%"><@messages key="model.loan.loanNo" /></th>
			<th width="10%"><@messages key="model.basic.account" /> </th>
			<th width="10%" class="align-right"><@messages key="model.loan.amount" />(元)</th>
			<th width="30%" class="align-center"><@messages key="model.loan.datetime" /></th>
			<th width="10%"><@messages key="model.loan.status" /></th>
			<th width="10%" class="align-center"></th>
		</tr>
	</thead>
	<#if loanaudit??>
	  <#if loanaudit.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list loanaudit.content as l>
		<tr>
			<td>${l.loanNo}</td>
			<td>${l.user.account!''}</td>
			<td class="align-right">${l.amount}</td>
			<td class="align-center">${l.datetime}</td>
			<td>${l.statusName}</td>
			<td>
			<#if l.status=='00'>
				<a href="#" data-url="${app}/loan/firstauditdetail/${l.id}" data-target="data">初审处理</a>
			<#elseif l.status=='01'>
				<a href="#" data-url="${app}/loan/finalauditdetail/${l.id}" data-target="data">终审处理</a>
			</#if>
			</td>
		</tr>
		</#list>
		</#if>
    <#else>
        <tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
    </#if>
	<tbody>
</table>

<ul class="pagination" data-number="${(loanaudit.number)!'0'}" data-total-pages="${(loanaudit.totalPages)!'0'}"></ul>

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

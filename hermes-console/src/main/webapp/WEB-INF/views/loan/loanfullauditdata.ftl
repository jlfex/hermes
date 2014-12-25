<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
			<th width="30%"><@messages key="model.loan.loanNo" /></th>
			<th width="20%"><@messages key="model.basic.account" /> </th>
			<th width="10%" class="align-right"><@messages key="model.loan.amount" />(<@messages key="common.unit.cny" />)</th>
			<th width="30%" class="align-center">满标时间</th>
			<th width="10%" class="align-center"></th>
		</tr>
	</thead>
	<#if loanfullaudit.numberOfElements == 0>
	<tr>
		<td colspan="5" class="align-center"><@messages key="common.table.empty" /></td>
	</tr>
	<#else>
	<#list loanfullaudit.content as l>
	<tr>
		<td>${l.loanNo}</td>
		<td>${l.account!''}</td>
		<td class="align-right">${l.amount!''}</td>
		<td class="align-center">${l.fullDateTime!''}</td>
		<td>
			<a href="#" data-url="${app}/loan/fullauditdetail/${l.id}" data-target="data">满标处理</a>
		</td>
	</tr>
	</#list>
	</#if>
</table>
<ul class="pagination" data-number="${loanfullaudit.number}" data-total-pages="${loanfullaudit.totalPages}"></ul>

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



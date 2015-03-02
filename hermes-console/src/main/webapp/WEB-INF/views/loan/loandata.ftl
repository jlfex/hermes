<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th width="20%"><@messages key="model.loan.loanNo" /></th>
				<th width="10%"><@messages key="model.basic.realName" /></th>
				<th width="10%"><@messages key="model.basic.cellphone" /></th>
				<th width="10%" class="align-right"><@messages key="model.loan.amount" />(<@messages key="common.unit.cny" />)</th>
				<th width="10%" class="align-center"><@messages key="model.loan.rate" /></th>
				<th width="10%"><@messages key="model.loan.period" /></th>
				<th width="10%"><@messages key="model.loan.datetime" /></th>
				<th width="10%"><@messages key="model.loan.status" /></th>
				<th width="10%"></th>
		</tr>
	</thead>
	<#if loan.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list loan.content as l>
		<tr>
			<td>${l.loanNo}</td>
			<td>${l.realName!''}</td>
			<td>${l.cellphone!''}</td>
			<td class="align-right">${l.amount}</td>
			<td class="align-center">${l.rate}</td>
			<td>
			  <#if (l.loanKind)?? && l.loanKind == '00'>
			      ${l.period}<@messages key="common.unit.month" />
			   <#else>
			      ${l.period}天
			  </#if>
			</td>
			<td>${l.datetime?string('yyyy-MM-dd hh:mm:ss')}</td>
			<td>${l.statusName}</td>
			<td>
				 <a href="#" data-url="${app}/loan/loandetail/${l.id}" data-target="data">查看</a>
			</td>
		</tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${loan.number}" data-total-pages="${loan.totalPages}"></ul>

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

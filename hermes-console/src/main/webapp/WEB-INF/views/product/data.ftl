<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th width="10%"><@messages key="model.product.code" /></th>
				<th width="10%"><@messages key="model.product.name" /></th>
				<th width="10%"><@messages key="model.product.amount" /></th>
				<th width="10%"><@messages key="model.product.period" /></th>
				<th width="10%"><@messages key="model.product.rate" /></th>
				<th width="10%"><@messages key="model.product.repay" /></th>
				<th width="10%"><@messages key="model.product.guarantee" /></th>
				<th width="10%"><@messages key="model.product.startingamt" /></th>
				<th width="10%"><@messages key="model.product.purpose" /></th>
				<th width="5%"><@messages key="model.product.status" /></th>
				<th width="5%"><@messages key="model.product.operation" /></th>
		</tr>
	</thead>
	<#if data.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list data.content as l>
		<tr>
			<td>${l.code}</td>
			<td>${l.name!''}</td>
			<td>${l.amount?replace(',', '-')}</td>
			<td>${l.period?replace(',', '-')}${l.periodType!}</td>
			<td>${l.rate?replace(',', '-')}%</td>
			<td>${(l.repay.name)!}</td>
			<td>${(l.guarantee.name)!}</td>
			<td>${l.startingAmt!}</td>
			<td>${(l.purpose.name)!}</td>
			<td><#if (l.status)?? && l.status == '00'>启用<#else>禁用</#if></td>
			<td>
				<a href="#" data-url="${app}/product/detail/${l.id}" data-target="main">查看</a>
			</td>
		</tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${data.number}" data-total-pages="${data.totalPages}"></ul>

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
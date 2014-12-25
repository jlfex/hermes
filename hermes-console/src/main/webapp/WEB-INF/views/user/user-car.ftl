<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="brand"><@messages key="model.car.brand" /></th>
			<th data-column="purchaseYear"><@messages key="model.car.purchaseYear" /></th>
			<th data-toggle="purchaseAmount" class="align-right"><@messages key="model.car.purchaseAmount" /></th>
			<th data-toggle="mortgage"><@messages key="model.car.mortgage" /></th>
			<th data-toggle="licencePlate"><@messages key="model.car.licencePlate" /></th>
		</tr>
	</thead>
	<tbody>
		<#if cars.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list cars.content as car>
		<tr>
			<td>${car.brand!''}</td>
			<td>${car.purchaseYear!''}</td>
			<td class="align-right">${car.purchaseAmount!''}</td>
			<td>
				<#list mortgageMap?keys as key> 
					<#if car.mortgage?exists&&car.mortgage==key> ${mortgageMap[key]} </#if>
				</#list>
			</td>
			<td>${car.licencePlate!''}</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>

<ul class="pagination" data-number="${cars.number}" data-total-pages="${cars.totalPages}"></ul>

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
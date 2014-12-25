<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="area"><@messages key="model.house.area" /></th>
			<th data-column="certificate"><@messages key="model.house.certificate" /></th>
			<th data-toggle="year"><@messages key="model.house.year" /></th>
			<th data-toggle="mortgage"><@messages key="model.house.mortgage" /></th>
			<th data-toggle="address"><@messages key="model.house.address" /></th>
		</tr>
	</thead>
	<tbody>
		<#if houses?size==0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list houses as house>
		<tr>
			<td>${house.area!''}</td>
			<td>${house.certificate!''}</td>
			<td>${house.year!''}</td>
			<td>
				<#list mortgageMap?keys as key> 
					<#if house?exists><#if house.mortgage?exists&&house.mortgage==key> ${mortgageMap[key]} </#if></#if>
				</#list>
			</td>
			<td>${house.addressDetail!''}</td>
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
			$('#searchBtn').trigger('click');
		}
	});
});
//-->
</script>
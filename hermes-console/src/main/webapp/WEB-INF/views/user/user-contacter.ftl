<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="name"><@messages key="model.contacter.name" /></th>
			<th data-column="relationship"><@messages key="model.contacter.relationship" /></th>
			<th data-toggle="phone"><@messages key="model.contacter.phone" /></th>
			<th data-toggle="address"><@messages key="model.contacter.address" /></th>
		</tr>
	</thead>
	<tbody>
		<#if contacters.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list contacters.content as contacter>
		<tr>
			<td>${contacter.name!''}</td>
			<td>${contacter.relationship!''}</td>
			<td>${contacter.phone!''}</td>
			<td>${contacter.address!''}</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>

<ul class="pagination" data-number="${contacters.number}" data-total-pages="${contacters.totalPages}"></ul>

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
<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th data-column="area"><@messages key="model.job.company.name" /></th>
			<th data-column="certificate"><@messages key="model.job.company.properties" /></th>
			<th data-toggle="year"><@messages key="model.job.company.scale" /></th>
			<th data-toggle="mortgage"><@messages key="model.job.company.address" /></th>
			<th data-toggle="address"><@messages key="model.job.company.phone" /></th>
			<th data-toggle="address"><@messages key="model.job.position" /></th>
			<th data-toggle="address" class="align-right"><@messages key="model.job.annualSalary" /></th>
			<th data-toggle="address"><@messages key="model.job.type" /></th>
			<th data-toggle="address" class="align-right"><@messages key="model.job.company.registeredCapital" /></th>
			<th data-toggle="address"><@messages key="model.job.company.license" /></th>
		</tr>
	</thead>
	<tbody>
		<#if jobs.numberOfElements == 0>
		<tr>
			<td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list jobs.content as job>
		<tr>
			<td>${job.name!''}</td>
			<td>
				<#list enterpriseMap?keys as key> 
					<#if job.properties?exists&&job.properties==key> ${enterpriseMap[key]}</#if>
				</#list>
			</td>
			<td>
				<#list typeMap?keys as key> 
						<#if job.scale?exists&&job.type==key> ${typeMap[key]} </#if>
				</#list>
			</td>
			<td>${job.address!''}</td>
			<td>${job.phone!''}</td>
			<td>${job.position!''}</td>
			<td class="align-right">${job.annualSalary!''}</td>
			<td>
				<#list typeMap?keys as key> 
						<#if job.type?exists&&job.type==key> ${typeMap[key]} </#if>
				</#list>
			</td>
			<td class="align-right">${job.registeredCapital!''}</td>
			<td>${job.license!''}</td>
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
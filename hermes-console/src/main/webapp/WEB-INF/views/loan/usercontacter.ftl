<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th>姓名 </th>
			<th>关系</th>
			<th>电话</th>
			<th>联系地址</th>
		
		</tr>
	</thead>
	<tbody>
		<#if userContacters?size == 0>
			<tr>
				<td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
			</tr>
		<#else>
			<#list userContacters as contacter>
			<tr>
				<td>${contacter.name!''}</td>
				<td>${contacter.relationship!''}</td>
				<td>${contacter.phine!''}</td>
				<td>${contacter.address!''}</td>
			</tr>
			</#list>
		</#if>
		
		
	</tbody>
</table>